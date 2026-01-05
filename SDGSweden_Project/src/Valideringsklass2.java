/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import oru.inf.InfException;
import oru.inf.InfDB;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
    public static void kontrolleraInlogg(InfDB idb, String ePost, String losen, JFrame inloggningsFonster, JLabel felLable){ 
    
 try {
            String sqlFraga = "SELECT losenord FROM anstalld WHERE epost = '" + ePost + "'";
            System.out.println(sqlFraga);
            String dbLosen = idb.fetchSingle(sqlFraga);
                        if (dbLosen !=null &&losen.equals(dbLosen)) {
                String aid = idb.fetchSingle("SELECT aid FROM anstalld WHERE epost= '" + ePost + "'");
                String isAdministrator = idb.fetchSingle("SELECT aid FROM admin WHERE aid = " + aid);
                String isProjektChef = idb.fetchSingle("SELECT projektchef FROM projekt WHERE projektchef = " + aid + " LIMIT 1");

                if (isAdministrator != null) {
                    new Administratör(idb, ePost).setVisible(true);
                }
                else if (isProjektChef != null) {
                    new MenyProjektChef(idb, ePost).setVisible(true);
                }
                else {
                    new MenyHandläggare(idb, ePost).setVisible(true);
                }

                inloggningsFonster.dispose();
            }
            else {
                felLable.setVisible(true);
            }

    
     
        }catch (InfException ex){ 
            System.out.println(ex.getMessage());
    }                                          
    
    }
}



    // Variables declaration - do not modify                     
    // End of variables declaration                   



