/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

/**
 *
 * @author neuhaus
 */
class ExecDemo {

    public static void viewPhoto(String namefile) {
        Runtime r = Runtime.getRuntime();
        Process p = null;
        try {
            p = r.exec("explorer.exe C:\\Documents\\"+namefile);
        } catch (Exception e) {
            System.out.println("Error al ejecutar");
        }
    }

    public static void main(String args[]) {
        viewPhoto("tomamoreno.gif");
    }
}
