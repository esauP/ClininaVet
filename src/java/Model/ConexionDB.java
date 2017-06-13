
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para gestionar la conexión con la base de datos
 */
public class ConexionDB {
    
    
            
    private static Connection conexion;
    //dam43
    //admin
    private String user = "dam43";
    //dam43_clinica
    //clinica_local
    private String bd = "dam43_clinica";
    //salesianas
    //admin
    private String pass = "salesianas";
    //192.168.28.3
    //79.148.236.236
    //localhost
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
