/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author elinjugas
 */
import oru.inf.InfDB;
import oru.inf.InfException;

public class ProjektchefStatistikLand extends javax.swing.JFrame {

    private InfDB idb;
    private String inloggadEpost;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ProjektchefStatistikLand.class.getName());

    /**
     * Creates new form ProjektchefStatistikLand
     */
    public ProjektchefStatistikLand(InfDB idb, String inloggadEpost) {
        initComponents();
        this.idb = idb;
        this.inloggadEpost = inloggadEpost; //Sparas för att kunna skickas tillbaka till meny

        //Fyller rullistan med länder direkt vid start
        fyllLandVäljare();
        //Gör att  kolumnerna i fönstret ska ligga rakt
        txtStatistik.setFont(new java.awt.Font("Monospaced", 0, 12));
    }

    //Hämtar alla länder från databasen och lägger de i ComboBoxen
    private void fyllLandVäljare() {
        try {
            //Tömmer gamla items, items 1, itmes 2 osv
            cbLand.removeAllItems();

            //Hämtar endast de namnen på länderna i bokstavsordning
            String fraga = "SELECT namn FROM land ORDER BY namn";
            java.util.ArrayList<String> lander = idb.fetchColumn(fraga);

            //Kontrollera att listan inte är tom 
            if (lander != null) {
                for (String land : lander) {
                    cbLand.addItem(land);
                }
            }
        } catch (oru.inf.InfException ex) {
            //Loggar felet och skickar ut felmeddleande
            System.out.println("Fel vid hämtning av länder: " + ex.getMessage());
        }
    }

    private void visaLandStatistik() {
        //Anropar extern valideringsklass för att se att användaren valt ett land
        if (!Valideringsklass2.comboHarValtVarde(cbLand, "land")) {
            return;
        }

        try {
            String valtLand = (String) cbLand.getSelectedItem();
            txtStatistik.setText("");

            //Använder Join för att koppla ihop projekttabellen med land
            //p.land används tillsammans med l.led
            String sql = "SELECT p.projektnamn, p.kostnad FROM projekt p "
                    + "JOIN land l ON p.land = l.lid "
                    + "WHERE l.namn = '" + valtLand + "'";

            java.util.ArrayList<java.util.HashMap<String, String>> resultat = idb.fetchRows(sql);

            // Om landet inte har några projekt skickas felmeddlenade ut
            if (resultat == null || resultat.isEmpty()) {
                txtStatistik.setText("Inga projekt hittades för " + valtLand + ".");
                return;
            }

            // Skapat en mall av en rapport, StringBuilder
            StringBuilder rapport = new StringBuilder();
            rapport.append("KOSTNADSRAPPORT: ").append(valtLand.toUpperCase()).append("\n");
            rapport.append("==========================================\n");
            //%-25s betyder "en sträng på 25 tecken, vänsterställd"
            rapport.append(String.format("%-25s %-15s\n", "Projekt", "Kostnad"));
            rapport.append("------------------------------------------\n");

            double totalSumma = 0;

            //Loopat genom varje projekt vi hittat
            for (java.util.HashMap<String, String> rad : resultat) {
                String namn = rad.get("projektnamn");
                // Säkerställ att kostnad inte är null
                String kostnadStr = rad.get("kostnad");
                //Omvandlar text till siffror för att kunna addera dem
                //Om kostnad saknas sätts de till 0 
                double kostnad = (kostnadStr != null) ? Double.parseDouble(kostnadStr) : 0;

                totalSumma += kostnad; // Adderar till den totala kostnad för landet
                rapport.append(String.format("%-25s %,.0f kr\n", namn, kostnad));
            }

            rapport.append("------------------------------------------\n");
            //%,.0f formaterar siffran med tusentalsavgränsare
            rapport.append(String.format("%-25s %,.0f kr\n", "TOTAL SUMMA:", totalSumma));

            //Skickar den färdiga strängen
            txtStatistik.setText(rapport.toString());

        } catch (InfException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Databasfel: " + ex.getMessage());
            //Ifall SQL frågan är felaktig
        } catch (Exception ex) {
            //Fångar upp andra fel
            javax.swing.JOptionPane.showMessageDialog(this, "Ett oväntat fel uppstod.");
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {

        new MenyProjektChef(idb, inloggadEpost).setVisible(true);
        this.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtStatistik = new javax.swing.JTextArea();
        cbLand = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        btnVisaStatistik = new javax.swing.JButton();
        btnTillbaka = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(53, 152, 242));

        jPanel1.setBackground(new java.awt.Color(53, 152, 242));

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Kostnadsstatistik per land");
        jLabel1.setToolTipText("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        txtStatistik.setColumns(20);
        txtStatistik.setRows(5);
        jScrollPane1.setViewportView(txtStatistik);

        jScrollPane2.setViewportView(jScrollPane1);

        cbLand.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Välj land:");

        btnVisaStatistik.setText("Visa statistik");
        btnVisaStatistik.addActionListener(this::btnVisaStatistikActionPerformed);

        btnTillbaka.setText("Tillbaka");
        btnTillbaka.addActionListener(this::btnTillbakaActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel2)
                        .addGap(47, 47, 47)
                        .addComponent(cbLand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(btnVisaStatistik))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnTillbaka)
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbLand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(58, 58, 58))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnVisaStatistik)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(btnTillbaka)
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Här körs logiken
    private void btnVisaStatistikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisaStatistikActionPerformed
        visaLandStatistik();
    }//GEN-LAST:event_btnVisaStatistikActionPerformed

    //Går tillbaka till projektchefsmenyn
    private void btnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaActionPerformed
        new MenyProjektChef(idb, inloggadEpost).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnTillbakaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JButton btnVisaStatistik;
    private javax.swing.JComboBox<String> cbLand;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea txtStatistik;
    // End of variables declaration//GEN-END:variables
}
