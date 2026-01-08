/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import oru.inf.InfException;
import oru.inf.InfDB;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.time.LocalDate;
/**
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author User
 */
public class Valideringsklass2 {
    // Metod 1: Hanterar inloggningslogik
    public static void kontrolleraInlogg(InfDB idb, String ePost, String losen, JFrame inloggningsFonster, JLabel felLable) {
        try {
            String sqlFraga = "SELECT losenord FROM anstalld WHERE epost = '" + ePost + "'";
            String dbLosen = idb.fetchSingle(sqlFraga);

            if (dbLosen != null && losen.equals(dbLosen)) {
                String aid = idb.fetchSingle("SELECT aid FROM anstalld WHERE epost= '" + ePost + "'");
                
                // Kontrollera roller
                String isAdministrator = idb.fetchSingle("SELECT aid FROM admin WHERE aid = " + aid);
                String isProjektChef = idb.fetchSingle("SELECT projektchef FROM projekt WHERE projektchef = " + aid + " LIMIT 1");

                if (isAdministrator != null) {
                    new Administratör(idb, ePost).setVisible(true);
                } else if (isProjektChef != null) {
                    new MenyProjektChef(idb, ePost).setVisible(true);
                } else {
                    new MenyHandläggare(idb, ePost).setVisible(true);
                }

                inloggningsFonster.dispose();
            } else {
                felLable.setVisible(true);
            }
        } catch (InfException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Metod 2: Hanterar datumvalidering för sökfunktionen
    public static boolean arGiltigtDatum(JTextField ettFalt) {
        String datumString = ettFalt.getText().trim();

        if (datumString.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Datumfältet får inte vara tomt.");
            return false;
        }

        try {
            LocalDate.parse(datumString);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ogiltigt datumformat. Använd YYYY-MM-DD.");
            return false;
        }
    }
    public static boolean ifylltTxtFalt(javax.swing.JTextField faltAttTesta){
        boolean resultat = true;
        if (faltAttTesta.getText().trim().isEmpty()){
            resultat = false;
            
        }
        return resultat; 
    }
    
    // Kontrollerar om ett fält är tomt och visar felmeddelande
    public static boolean faltHarVärde(JTextField faltAttTesta, String faltNamn) {
        if (faltAttTesta.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fältet " + faltNamn + " får inte vara tomt.");
            faltAttTesta.requestFocus(); // Sätter markören i det tomma fältet
            return false;
        }
        return true;
    }

    // Kontrollerar om texten är ett giltigt heltal (t.ex. för telefonnummer eller ID)
    public static boolean arHeltal(JTextField faltAttTesta) {
        try {
            Integer.parseInt(faltAttTesta.getText().trim());
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Var god ange ett giltigt nummer.");
            faltAttTesta.requestFocus();
            return false;
        }
    }

    // Kontrollerar om e-posten innehåller ett @ (enkel validering)
    public static boolean arGiltigEpost(JTextField faltAttTesta) {
        String epost = faltAttTesta.getText().trim();
        if (!epost.contains("@") || !epost.contains(".")) {
            JOptionPane.showMessageDialog(null, "Ange en giltig e-postadress.");
            faltAttTesta.requestFocus();
            return false;
        }
        return true;
    }
}



    // Variables declaration - do not modify                     
    // End of variables declaration                   



