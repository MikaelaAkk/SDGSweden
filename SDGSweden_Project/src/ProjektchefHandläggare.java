/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 * Klass för att hantera handläggare i projekt.
 * Man kan söka och ta bort kopling av personal i projekt
 * Man kan även se handläggarens nuvarande projekt
 * Tilldelning och borttagning av personal i egna projekt
 *
 * @author elinjugas
 */
import oru.inf.InfDB;
import oru.inf.InfException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class ProjektchefHandläggare extends javax.swing.JFrame {

    private InfDB idb; //databaskopplingen
    private String inloggadEpost; //Sparar e-post för den som är inloggad
    private String valdAnstalldAid; //Sparar ID på den handläggare man sökt fram

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ProjektchefHandläggare.class.getName());

    /**
     * Creates new form ProjektchefHandläggare
     */
    public ProjektchefHandläggare(InfDB idb, String inloggadEpost) {
        initComponents();
        this.idb = idb;
        this.inloggadEpost = inloggadEpost;

        // Initiering av gränssnitt
        txtSok.setText(""); //Rensar sökfältet vid start
        fyllMinaProjektCombo(); // Fyller menyn med projekt som användaren är chef för
    }

    //Fyller comboboxen med projektnamn där den inloggade är projektchef.
    //Säkerställer att användaren endast kan administrera egna projekt
    private void fyllMinaProjektCombo() {
        try {
            cbMinaProjekt.removeAllItems();
            //Hämtar projektnamn genom att söka på aid via den inloggades e-post
            String fraga = "SELECT projektnamn FROM projekt WHERE projektchef = "
                    + "(SELECT aid FROM anstalld WHERE epost = '" + inloggadEpost + "')";
            ArrayList<String> projekt = idb.fetchColumn(fraga);
            if (projekt != null) {
                for (String p : projekt) {
                    cbMinaProjekt.addItem(p);
                }
            }
        } catch (InfException ex) {
            System.out.println("Kunde inte ladda projekt: " + ex.getMessage());
        }
    }

    //Uppdaterar Jlist med de projekt som den sökta handläggaren jobbar i 
    private void uppdateraProjektLista() {
        try {
            DefaultListModel<String> model = new DefaultListModel<>();
            // Joinar projekt och ans_proj för att se vilka projekt ett specifikt AID är kopplat till
            String fraga = "SELECT projektnamn FROM projekt "
                    + "JOIN ans_proj ON projekt.pid = ans_proj.pid "
                    + "WHERE ans_proj.aid = " + valdAnstalldAid;

            ArrayList<String> resultat = idb.fetchColumn(fraga);
            if (resultat != null) {
                for (String p : resultat) {
                    model.addElement(p);
                }
            }
            lstPersonensProjekt.setModel(model);
        } catch (InfException ex) {
            System.out.println("Kunde inte hämta personens projekt: " + ex.getMessage());
        }
    }

    // Hanterar söklohiken. Validerar data för att få fram informaiton om handläggare
    private void utforSokning() {
        txtResultat.setText("");
        // Validera att sökfältet inte är tomt och följer RegEx
        if (!Valideringsklass2.textfaltHarVarde(txtSok, "sökfältet") || !Valideringsklass2.isSafeSearch(txtSok)) {
            return;
        }

        try {
            //Hämtar personuppgifter och avdelningsnamn via en Join
            String sokText = txtSok.getText().trim();
            String sql = "SELECT a.aid, a.fornamn, a.efternamn, a.epost, avd.namn FROM anstalld a "
                    + "JOIN avdelning avd ON a.avdelning = avd.avdid "
                    + "WHERE a.fornamn LIKE '%" + sokText + "%' OR a.epost LIKE '%" + sokText + "%'";

            ArrayList<HashMap<String, String>> resultat = idb.fetchRows(sql);

            if (resultat == null || resultat.isEmpty()) {
                txtResultat.append("Ingen handläggare hittades med det namnet/eposten.");
                valdAnstalldAid = null;
            } else {
                // Sätter "Vald" för att kunna lägga till i projektet
                HashMap<String, String> person = resultat.get(0);
                valdAnstalldAid = person.get("aid");

                //Loopar igenom resultaten och skriver ut de som information
                for (HashMap<String, String> rad : resultat) {
                    txtResultat.append("Namn: " + rad.get("fornamn") + " " + rad.get("efternamn") + "\n");
                    txtResultat.append("E-post: " + rad.get("epost") + "\n");
                    txtResultat.append("Avdelning: " + rad.get("namn") + "\n");
                    txtResultat.append("--------------------------------------\n");
                }
                //Visar handläggarens nuvarande projekt i listan bredvid
                uppdateraProjektLista();
            }
        } catch (InfException ex) {
            JOptionPane.showMessageDialog(this, "Sökningen misslyckades: " + ex.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlRubrik = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pnlSokochInfo = new javax.swing.JPanel();
        pnlSokHandlaggare = new javax.swing.JLabel();
        txtSok = new javax.swing.JTextField();
        btnSok = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtResultat = new javax.swing.JTextArea();
        pnlHandlaggareOchProjekt = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstPersonensProjekt = new javax.swing.JList<>();
        lblHandlaggareProjekt = new javax.swing.JLabel();
        cbMinaProjekt = new javax.swing.JComboBox<>();
        lblMinaProjekt = new javax.swing.JLabel();
        btnLaggTill = new javax.swing.JButton();
        btnTaBort = new javax.swing.JButton();
        btnTillbaka = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(52, 153, 242));

        pnlRubrik.setBackground(new java.awt.Color(52, 153, 242));

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Hantera handläggare");
        jLabel1.setToolTipText("");

        javax.swing.GroupLayout pnlRubrikLayout = new javax.swing.GroupLayout(pnlRubrik);
        pnlRubrik.setLayout(pnlRubrikLayout);
        pnlRubrikLayout.setHorizontalGroup(
            pnlRubrikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRubrikLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlRubrikLayout.setVerticalGroup(
            pnlRubrikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRubrikLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pnlSokHandlaggare.setText("Sök efter handläggare (Namn/Epost)");
        pnlSokHandlaggare.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pnlSokHandlaggareKeyReleased(evt);
            }
        });

        txtSok.setText("jTextField1");

        btnSok.setText("Sök");
        btnSok.addActionListener(this::btnSokActionPerformed);

        txtResultat.setColumns(20);
        txtResultat.setRows(5);
        jScrollPane1.setViewportView(txtResultat);

        javax.swing.GroupLayout pnlSokochInfoLayout = new javax.swing.GroupLayout(pnlSokochInfo);
        pnlSokochInfo.setLayout(pnlSokochInfoLayout);
        pnlSokochInfoLayout.setHorizontalGroup(
            pnlSokochInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSokochInfoLayout.createSequentialGroup()
                .addGroup(pnlSokochInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSokochInfoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlSokHandlaggare))
                    .addGroup(pnlSokochInfoLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(pnlSokochInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlSokochInfoLayout.createSequentialGroup()
                                .addComponent(txtSok, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSok)))))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        pnlSokochInfoLayout.setVerticalGroup(
            pnlSokochInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSokochInfoLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(pnlSokHandlaggare)
                .addGap(18, 18, 18)
                .addGroup(pnlSokochInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSok))
                .addGap(52, 52, 52)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        lstPersonensProjekt.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(lstPersonensProjekt);

        lblHandlaggareProjekt.setText("Handläggarens projekt:");

        cbMinaProjekt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblMinaProjekt.setText("Mina projekt:");

        btnLaggTill.setText("Lägg till i mitt projekt");
        btnLaggTill.addActionListener(this::btnLaggTillActionPerformed);

        btnTaBort.setText("Ta bort från mitt projekt:");
        btnTaBort.addActionListener(this::btnTaBortActionPerformed);

        btnTillbaka.setText("Tillbaka");
        btnTillbaka.addActionListener(this::btnTillbakaActionPerformed);

        javax.swing.GroupLayout pnlHandlaggareOchProjektLayout = new javax.swing.GroupLayout(pnlHandlaggareOchProjekt);
        pnlHandlaggareOchProjekt.setLayout(pnlHandlaggareOchProjektLayout);
        pnlHandlaggareOchProjektLayout.setHorizontalGroup(
            pnlHandlaggareOchProjektLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHandlaggareOchProjektLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlHandlaggareOchProjektLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnTillbaka)
                    .addGroup(pnlHandlaggareOchProjektLayout.createSequentialGroup()
                        .addGroup(pnlHandlaggareOchProjektLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnLaggTill)
                            .addComponent(lblHandlaggareProjekt)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnlHandlaggareOchProjektLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlHandlaggareOchProjektLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(btnTaBort))
                            .addGroup(pnlHandlaggareOchProjektLayout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addGroup(pnlHandlaggareOchProjektLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cbMinaProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblMinaProjekt))))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        pnlHandlaggareOchProjektLayout.setVerticalGroup(
            pnlHandlaggareOchProjektLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHandlaggareOchProjektLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnlHandlaggareOchProjektLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHandlaggareProjekt)
                    .addComponent(lblMinaProjekt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlHandlaggareOchProjektLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbMinaProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                .addGroup(pnlHandlaggareOchProjektLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLaggTill)
                    .addComponent(btnTaBort))
                .addGap(18, 18, 18)
                .addComponent(btnTillbaka))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlRubrik, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlSokochInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlHandlaggareOchProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlRubrik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlHandlaggareOchProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlSokochInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Knapp: Lägg till den framsökta handläggaren i valt projekt
    private void btnLaggTillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaggTillActionPerformed

        try {
            // Kontrollerar att både en person är sökt och ett projekt är valt

            if (Valideringsklass2.idArSatt(valdAnstalldAid, "handläggare")
                    && Valideringsklass2.comboValt(cbMinaProjekt.getSelectedItem(), "projekt")) {

                // Hämtar PID för det valda projektnamnet
                String valtProjekt = cbMinaProjekt.getSelectedItem().toString();
                String pid = idb.fetchSingle("SELECT pid FROM projekt WHERE projektnamn = '" + valtProjekt + "'");

                if (Valideringsklass2.arGiltigtId(pid) && Valideringsklass2.arGiltigtId(valdAnstalldAid)) {
                    // Skapar en ny koppling
                    String insertSql = "INSERT INTO ans_proj (pid, aid) VALUES (" + pid + ", " + valdAnstalldAid + ")";
                    idb.insert(insertSql);
                    JOptionPane.showMessageDialog(this, "Handläggare tillagd i projektet!");
                    uppdateraProjektLista();
                }
            }
        } catch (InfException ex) {
            //Hanterar när en person redan är kopplad till projektet ochs skriver ut felmeddelande
            if (ex.getMessage().contains("Duplicate")) {
                JOptionPane.showMessageDialog(this, "Handläggaren är redan med i detta projekt.");
            } else {
                JOptionPane.showMessageDialog(this, "Ett tekniskt fel uppstod: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_btnLaggTillActionPerformed

    //Tar bort kopplingen mellan en handläggare och ett projekt. 
    private void btnTaBortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaBortActionPerformed
        try {
            if (Valideringsklass2.idArSatt(valdAnstalldAid, "handläggare")
                    && Valideringsklass2.comboValt(cbMinaProjekt.getSelectedItem(), "projekt")) {

                String valtProjektNamn = (String) cbMinaProjekt.getSelectedItem();
                String pid = idb.fetchSingle("SELECT pid FROM projekt WHERE projektnamn = '" + valtProjektNamn + "'");

                // Bekräftelse innan borttagning 
                int svar = JOptionPane.showConfirmDialog(this, "Är du säker på att du vill ta bort handläggaren från projektet?", "Bekräfta", JOptionPane.YES_NO_OPTION);

                if (svar == JOptionPane.YES_OPTION && Valideringsklass2.arGiltigtId(pid)) {
                    String deleteSql = "DELETE FROM ans_proj WHERE pid = " + pid + " AND aid = " + valdAnstalldAid;
                    idb.delete(deleteSql);
                    JOptionPane.showMessageDialog(this, "Handläggaren har tagits bort från projektet.");
                    uppdateraProjektLista();
                }
            }
        } catch (InfException ex) {
            JOptionPane.showMessageDialog(this, "Ett fel uppstod vid borttagning: " + ex.getMessage());
        }

    }//GEN-LAST:event_btnTaBortActionPerformed

    private void btnSokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSokActionPerformed
        if (Valideringsklass2.textfaltHarVarde(txtSok, "sökfältet")) {
            utforSokning();
        }
    }//GEN-LAST:event_btnSokActionPerformed

    private void pnlSokHandlaggareKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pnlSokHandlaggareKeyReleased

    }//GEN-LAST:event_pnlSokHandlaggareKeyReleased

    private void btnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaActionPerformed
        new MenyProjektChef(idb, inloggadEpost).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnTillbakaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLaggTill;
    private javax.swing.JButton btnSok;
    private javax.swing.JButton btnTaBort;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JComboBox<String> cbMinaProjekt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblHandlaggareProjekt;
    private javax.swing.JLabel lblMinaProjekt;
    private javax.swing.JList<String> lstPersonensProjekt;
    private javax.swing.JPanel pnlHandlaggareOchProjekt;
    private javax.swing.JPanel pnlRubrik;
    private javax.swing.JLabel pnlSokHandlaggare;
    private javax.swing.JPanel pnlSokochInfo;
    private javax.swing.JTextArea txtResultat;
    private javax.swing.JTextField txtSok;
    // End of variables declaration//GEN-END:variables
}
