/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author esaup
 */
public class ConexionDB {
    
    
            
    private static Connection conexion;
    private String user = "dam43";
    private String bd = "dam43_clinica";
    private String pass = "salesianas";
    //192.168.28.3
    //79.148.236.236
    private String host = "79.148.236.236";
    private String server = "jdbc:mysql://" + host + "/" + bd;

    public ConexionDB() {

        try {
            System.out.println(bd);
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println(server);
            conexion = (Connection) DriverManager.getConnection(this.server, this.user, this.pass);
            //conector de la base de datos, por el cual pasamos los datos por parametros.
            System.out.println("Conexion a base de datos " + this.server + " ...OK");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Metodo para llamar a la conexion desde las distintas partes de nuestro
     * programa
     *
     * @return una conexion a la base de datos.
     */
    public Connection getConexion() {
        return this.conexion;
    }

    public void desconectar() throws SQLException {
        if (conexion != null) {
            conexion.close();
        }
    }

}
