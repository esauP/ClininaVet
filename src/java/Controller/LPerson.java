/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ConexionDB;
import java.sql.CallableStatement;
import pojo.Person;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author esaup
 */
public class LPerson {

    public static List<Person> getPeople() throws SQLException {
        List<Person> listapersona = new ArrayList<Person>();
        ConexionDB conn = new ConexionDB();

        try {
            String sql = "Select idperson, name_per, address, phone, email from person ";
            PreparedStatement ps = conn.getConexion().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Person aux = new Person();
                aux.setIdperson(rs.getString(1));
                aux.setNamePer(rs.getString(2));
                aux.setAddress(rs.getString(3));
                aux.setPhone(rs.getString(4));
                aux.setEmail(rs.getString(5));
                listapersona.add(aux);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            conn.desconectar();
        }
        return listapersona;
    }

    /**
     * Método para añadir un nueva persona
     *
     * @param idperson id
     * @param name nombre de la persona
     * @param address dirección
     * @param phone teléfono
     * @param email email
     * @param password contraseña
     * @param role rol de la persona
     * @return
     */
    public static boolean addPerson(String idperson, String name, String address, String phone, String email, String password, int role) throws SQLException {
        boolean success = false;
        ConexionDB conn = new ConexionDB();
        try {
            //Llamada a la funcion
            String sql = "{ ? = call addPerson (?,?,?,?,?,?,?) }";
            CallableStatement cStmt = conn.getConexion().prepareCall(sql);
            //establezco la salida de la funcion
            cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
            //establezco los parámetros de entrada
            cStmt.setString(2, idperson);
            cStmt.setString(3, name);
            cStmt.setString(4, address);
            cStmt.setString(5, phone);
            cStmt.setString(6, email);
            cStmt.setString(7, password);
            cStmt.setInt(8, role);
            //se ejecuta la funcion
            cStmt.execute();

            if (cStmt.getInt(1) == 0) {
                //System.out.println(cStmt.getInt(1));
                success = true;
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            conn.desconectar();
        }
        return success;
    }

    /**
     * Método para modificar una persona
     *
     * @param idperson
     * @param name
     * @param address
     * @param phone
     * @param email
     * @param password
     * @param role
     * @return
     * @throws java.sql.SQLException
     */
    public static boolean updatePerson(String idperson, String name, String address, String phone, String email, String password, int role) throws SQLException {
        boolean success = false;
        ConexionDB conn = new ConexionDB();
        try {
            //Llamada a la funcion
            String sql = "{ call updatePerson (?,?,?,?,?,?,?) }";
            CallableStatement cStmt = conn.getConexion().prepareCall(sql);
            //establezco los parámetros de entrada
            cStmt.setString(1, idperson);
            cStmt.setString(2, name);
            cStmt.setString(3, address);
            cStmt.setString(4, phone);
            cStmt.setString(5, email);
            cStmt.setString(6, password);
            cStmt.setInt(7, role);
            //se ejecuta la funcion
            cStmt.execute();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            conn.desconectar();
        }
        return success;
    }

    /**
     * Metodo para acutalizar a una persona, pero sin la posibilidad de cambiar
     * el password
     *
     * @param idperson
     * @param name
     * @param address
     * @param phone
     * @param email
     * @param role
     * @return
     * @throws SQLException
     */
    public static boolean updatePersonPets(String idperson, String name, String address, String phone, String email, int role) throws SQLException {
        boolean success = false;
        ConexionDB conn = new ConexionDB();
        try {
            //Llamada a la funcion
            String sql = "{ ? = call updatePersonPets (?,?,?,?,?,?) }";
            CallableStatement cStmt = conn.getConexion().prepareCall(sql);
            //establezco la salida de la funcion
            cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
            //establezco los parámetros de entrada
            cStmt.setString(2, idperson);
            cStmt.setString(3, name);
            cStmt.setString(4, address);
            cStmt.setString(5, phone);
            cStmt.setString(6, email);
            cStmt.setInt(7, role);
            //se ejecuta la funcion
            cStmt.execute();

            if (cStmt.getInt(1) == 0) {
                success = true;
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            conn.desconectar();
        }
        return success;
    }

    /**
     * Método para eliminar una persona de la base de datos
     *
     * @param idperson
     * @return
     */
    public static boolean deletePerson(String idperson) throws SQLException {
        boolean success = false;
        ConexionDB conn = new ConexionDB();
        try {
            //Llamada a la funcion
            String sql = "{ ? = call deletePerson (?) }";
            CallableStatement cStmt = conn.getConexion().prepareCall(sql);
            //establezco la salida de la funcion
            cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
            //establezco los parámetros de entrada
            cStmt.setString(2, idperson);
            //se ejecuta la funcion
            cStmt.execute();
            if (cStmt.getInt(1) == 0) {
                success = true;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            conn.desconectar();
        }
        return success;
    }
}
