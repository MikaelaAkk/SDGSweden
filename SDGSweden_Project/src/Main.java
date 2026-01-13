/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author Mikaela ak, Olivia Collin, Lucy Alp, Elin Jugås
 */
public class Main {

    private static InfDB idb;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            idb = new InfDB("sdgsweden", "3306", "dbAdmin2024", "dbAdmin2024PW");
            new Inlogg(idb).setVisible(true);
        } catch (InfException ex) {
            System.out.println(ex.getMessage());

        }
    }

}
