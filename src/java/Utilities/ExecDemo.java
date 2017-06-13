
package Utilities;

import java.io.File;
import java.io.FileReader;
import javax.swing.JFileChooser;


class ExecDemo {

    /**
     * Método estático para cargar el explorador de archivos de windows
     * @param namefile 
     */
    public static void viewPhoto(String namefile) {
        Runtime r = Runtime.getRuntime();
        Process p = null;
        try {
            p = r.exec("explorer.exe C:\\Documents\\" + namefile);
        } catch (Exception e) {
            System.out.println("Error al ejecutar");
        }
    }

    /**
     * Método para abrir un filechooser
     * @return 
     */
    public static String selectFile() {
        String aux = "";
        //Creamos el objeto JFileChooser
        JFileChooser fc = new JFileChooser();
        //Abrimos la ventana, guardamos la opcion seleccionada por el usuario
        int seleccion = fc.showDialog(null, "Holi");
        //Si el usuario, pincha en aceptar
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            //Seleccionamos el fichero
            File fichero = fc.getSelectedFile();
            aux = fichero.getName();
        }
        return aux;
    }

    public static void main(String args[]) {
        System.out.println("Resultado final: "+selectFile());
                
    }
}
