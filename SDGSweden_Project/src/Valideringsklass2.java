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

    //Hanterar inloggningslogik
    public static void kontrolleraInlogg(InfDB idb, String ePost, String losen, JFrame inloggningsFonster, JLabel felLable) {
        try {
            String sqlFraga = "SELECT losenord FROM anstalld WHERE epost = '" + ePost + "'";
            String dbLosen = idb.fetchSingle(sqlFraga);

            if (dbLosen != null && losen.equals(dbLosen)) {
                String aid = idb.fetchSingle("SELECT aid FROM anstalld WHERE epost= '" + ePost + "'");
                
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

    // Hanterar datumvalidering
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

    // Din ifyllt-kontroll
    public static boolean ifylltTxtFalt(javax.swing.JTextField faltAttTesta){
        boolean resultat = true;
        if (faltAttTesta.getText().trim().isEmpty()){
            resultat = false;
        }
        return resultat; 
    }
    
 
    public static boolean faltHarVarde(JTextField faltAttTesta, String namn) {
        if (faltAttTesta.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fältet " + namn + " får inte vara tomt.");
            faltAttTesta.requestFocus();
            return false;
        }
        return true;
    }

    

    

    //  Telefonnummer kontroll (Regex)
    public static boolean arGiltigtTelefonnummer(JTextField ettFalt) {
    String tel = ettFalt.getText().trim();
    // Tillåter siffror, mellanslag, bindestreck och plustecken
    if (!tel.matches("^[0-9\\s+-]{5,20}$")) {
        JOptionPane.showMessageDialog(null, "Ange ett giltigt telefonnummer (t.ex. +3322114453).");
        ettFalt.requestFocus();
        return false;
    }
    return true;
}
    
    // Kontrollera täxtfält
     public static boolean textfaltHarVarde(javax.swing.JTextField faltAttTesta, String namn) {
    if (faltAttTesta.getText().trim().isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(null, "Fältet för " + namn + " får inte vara tomt.");
        return false;
    }
    return true;
}
     // E-postvalidering
    public static boolean arGiltigEpost(javax.swing.JTextField faltAttTesta) {
String epostRegEx = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
    if (!faltAttTesta.getText().trim().matches(epostRegEx)) {
        javax.swing.JOptionPane.showMessageDialog(null, "E-postadressen har ett ogiltigt format (exempel@doman.se).");
        faltAttTesta.requestFocus();
        return false;
    }
    return true;
}
    // Heltalskontroll
    public static boolean arHeltal(javax.swing.JTextField faltAttTesta, String namn) {
    try {
        Integer.parseInt(faltAttTesta.getText().trim());
        return true;
    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(null, "Fältet för " + namn + " får bara innehålla siffror.");
        return false;
    }
    }
    
    // Validering av val i lista
    public static boolean listidArValt(Object valtVarde, String listNamn) {
        if (valtVarde == null) {
            JOptionPane.showMessageDialog(null, "Vänligen välj en " + listNamn + " i listan.");
            return false;
        }
        return true;
    }

    // Kontroll av ID-format
     public static boolean arGiltigtId(String id) {
        if (id == null || !id.matches("^[0-9]+$")) {
            JOptionPane.showMessageDialog(null, "Ett tekniskt fel uppstod: Ogiltigt ID-format.");
            return false;
        }
        return true;
    }
    
     // Kontroll av Combobox
     public static boolean comboValt(Object valtVarde, String namn) {
        if (valtVarde == null) {
            JOptionPane.showMessageDialog(null, "Vänligen välj ett " + namn + " i listan.");
            return false;
        }
        return true;
    }
     // Kontroll av sökstatus
     public static boolean idArSatt(String id, String typ) {
        if (id == null || id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Du måste söka fram en " + typ + " först.");
            return false;
        }
        return true;
    }


 
  // Säker sökning

    public static boolean isSafeSearch(JTextField fältAttTesta) {
        String text = fältAttTesta.getText().trim();
        
        if (!text.matches("^[a-zA-Z0-9åäöÅÄÖ\\s@.]+$")) {
            JOptionPane.showMessageDialog(null, "Sökningen innehåller ogiltiga tecken. Använd endast bokstäver och siffror.");
            fältAttTesta.requestFocus();
            return false;
        }
        return true;
    }
   //  Validering av ren text
   public static boolean arGiltigText(javax.swing.JTextField faltAttTesta, String namn) {

    if (!faltAttTesta.getText().trim().matches("^[a-zA-ZåäöÅÄÖ\\s-]+$")) {
        javax.swing.JOptionPane.showMessageDialog(null, "Fältet för " + namn + " får endast innehålla bokstäver.");
        faltAttTesta.requestFocus();
        return false;
    }
    return true;
}
   // Sessionskontroll
   public static boolean arGiltigInloggadAnvandare(String epost) {
    if (epost == null || epost.trim().isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(null, "Sessionen är ogiltig. Logga in igen.");
        return false;
    }
  
    if (!epost.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
        javax.swing.JOptionPane.showMessageDialog(null, "Användarens identitet är i fel format.");
        return false;
    }
    return true;
}
   // Kontroll av hittad data
   public static boolean hittadeData(java.util.ArrayList enLista, String informationstyp) {
    if (enLista == null || enLista.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(null, "Ingen " + informationstyp + " hittades för din avdelning.");
        return false;
    }
    return true;
    
   }
   


  //  Datumintervallsvalidering

public static boolean arGiltigtDatumIntervall(JTextField startFalt, JTextField slutFalt) {
    try {
        LocalDate start = LocalDate.parse(startFalt.getText().trim());
        LocalDate slut = LocalDate.parse(slutFalt.getText().trim());
        
        if (slut.isBefore(start)) {
            JOptionPane.showMessageDialog(null, "Slutdatumet kan inte vara före startdatumet.");
            return false;
        }
        return true;
    } catch (Exception e) {
      
        return false;
    }
} 
// Valutaformat
public static boolean comboHarValtVarde(javax.swing.JComboBox enBox, String namn) {
    if (enBox.getSelectedItem() == null || enBox.getSelectedIndex() == -1) {
        JOptionPane.showMessageDialog(null, "Vänligen välj en " + namn + " i listan först.");
        return false;
    }
    return true;
}


// Decimalformskontroll
public static String formateraValuta(String varde) {
    if (varde == null || varde.equals("null")) {
        return "0,00 kr";
    }
    try {
        double belopp = Double.parseDouble(varde);
        return String.format("%.2f kr", belopp);
    } catch (NumberFormatException e) {
        return "0,00 kr";
    }
}




//Statusvalidering

public static boolean kontrolleraHittadData(java.util.ArrayList lista, String meddelande) {
    if (lista == null || lista.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Information: " + meddelande);
        return false;
    }
    return true;
}




// SQL rensning

public static boolean arGiltigDecimal(JTextField faltAttTesta, String namn) {
    try {
        String varde = faltAttTesta.getText().trim().replace(',', '.'); // Hanterar både komma och punkt
        Double.parseDouble(varde);
        return true;
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Fältet " + namn + " måste vara ett tal (t.ex. 5000.50).");
        faltAttTesta.requestFocus();
        return false;
    }
}

// Statusvalidering
public static boolean arGiltigStatus(JTextField faltAttTesta) {
    String status = faltAttTesta.getText().trim().toLowerCase();
    String[] giltiga = {"pågående", "planerat", "pausat", "avslutat"};
    for (String g : giltiga) {
        if (status.equals(g)) return true;
    }
    JOptionPane.showMessageDialog(null, "Ogiltig status. Välj mellan: Pågående, Planerat, Pausat eller Avslutat.");
    return false;
}
// sql escape metod för att säkerställa att sql syntaxen inte bryts och att den returneranr rensad String. 

public static String escapeSql(String text) {
    if (text == null) {
        return "";
    }
    return text.replace("'", "''");
}

// Kollar om sessionen är giltig

public static boolean harGiltigSession(String epost) {
    if (epost == null || epost.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Sessionen är ogiltig. Logga in igen.");
        return false;
    }
    return true;
}

// returnerar om resultatet hittats. 

public static boolean hittadeResultat(java.util.List lista, String meddelande) {
    if (lista == null || lista.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Ingen data hittades: " + meddelande);
        return false;
    }
    return true;
}

     
}


                  



