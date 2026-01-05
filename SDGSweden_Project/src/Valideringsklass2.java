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
}

    // Variables declaration - do not modify                     
    // End of variables declaration                   



