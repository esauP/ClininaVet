/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ConexionDB;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pojo.Bill;

/**
 *
 * @author macarena jbenitez
 */
public class LBill {
    
    public static List<Bill> getList() throws SQLException {
        List<Bill> listafacturas = new ArrayList<Bill>();
        ConexionDB conn = new ConexionDB();

        try {
            String sql = "Select idbill, idper, date_bill, observations from bill";
            PreparedStatement ps = conn.getConexion().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Bill aux = new Bill();
                aux.setIdbill(rs.getInt(1));
                aux.setIdper(rs.getString(2));
                aux.setDate(rs.getString(3));
                aux.setObservations(rs.getString(4));
                listafacturas.add(aux);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            conn.desconectar();
        }
        return listafacturas;
    }
    
        /**
     * Método para añadir una nueva factura
     * @param idBill
     * @param idPer
     * @param date
     * @param obser
     * @return
     * @throws SQLException 
     */
    public static boolean addBill(String idPer, String date, String obser) throws SQLException {
        boolean success = false;
        ConexionDB conn = new ConexionDB();
        try {
            //Llamada a la funcion
            String sql = "{ ? = call addBill (?,?,?,?) }";
            CallableStatement cStmt = conn.getConexion().prepareCall(sql);
            //establezco la salida de la funcion
            cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
            //establezco los parámetros de entrada
            cStmt.setString(2, idPer);
            cStmt.setString(3, date);
            cStmt.setString(4, obser);
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
     * Método para eliminar una factura de la base de datos
     *
     * @param idBill
     * @return
     */
    public static boolean deleteBill(int idBill) throws SQLException {
        boolean success = false;
        ConexionDB conn = new ConexionDB();
        try {
            //Llamada a la funcion
            String sql = "{ ? = call deleteBill (?) }";
            CallableStatement cStmt = conn.getConexion().prepareCall(sql);
            //establezco la salida de la funcion
            cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
            //establezco los parámetros de entrada
            cStmt.setInt(2, idBill);
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
     * Lista de los nombres de las mascotas para incluir en un comboBox
     * @return
     * @throws SQLException 
     */
    public static boolean listPets() throws SQLException {
        boolean success = false;
        ConexionDB conn = new ConexionDB();
        try {
            //Llamada al procedimiento
            String sql = "{ ? = call listarMascotas () }";
            CallableStatement cStmt = conn.getConexion().prepareCall(sql);
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
     * Lista de los nombres de los productos para incluir en un comboBox
     * @return
     * @throws SQLException 
     */
    public static boolean listProd() throws SQLException {
        boolean success = false;
        ConexionDB conn = new ConexionDB();
        try {
            //Llamada al procedimiento
            String sql = "{ ? = call listarProductos () }";
            CallableStatement cStmt = conn.getConexion().prepareCall(sql);
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
    
    public static int GetNextIdBill() throws SQLException{
        ConexionDB conn = new ConexionDB();
        int id = 0;

        try {
            String sql = "SHOW TABLE STATUS WHERE `Name` = 'bill'";
            PreparedStatement ps = conn.getConexion().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                      id = rs.getInt("Auto_increment");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            conn.desconectar();
        }
        return id;
    }
}
