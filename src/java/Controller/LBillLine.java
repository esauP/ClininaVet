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
import pojo.BillLines;
import pojo.Pets;
import pojo.Products;

/**
 *
 * @author macarena jbenitez
 */
public class LBillLine extends ConexionDB {

    
    public static boolean addLinea(int idBill, int idProd, int quantity, double price, int taxes, int discount, int idPet, String observations) throws SQLException {
        boolean success = false;
        ConexionDB conn = new ConexionDB();
        try {
            //Llamada a la funcion
            String sql = "{ ? = call addBillLines (?,?,?,?,?,?,?,?) }";
            CallableStatement cStmt = conn.getConexion().prepareCall(sql);
            //establezco la salida de la funcion
            cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
            //establezco los parámetros de entrada
            cStmt.setInt(2, idBill);
            cStmt.setInt(3, idProd);
            cStmt.setInt(4, quantity);
            cStmt.setDouble(5, price);
            cStmt.setInt(6, taxes);
            cStmt.setInt(7, discount);
            cStmt.setInt(8, idPet);
            cStmt.setString(9, observations);
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

    public static boolean deleteBillLine(int idbillLine) throws SQLException {
        boolean success = false;
        ConexionDB conn = new ConexionDB();
        try {
            //Llamada a la funcion
            String sql = "{ ? = call deleteBillLines (?) }";
            CallableStatement cStmt = conn.getConexion().prepareCall(sql);
            //establezco la salida de la funcion
            cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
            //establezco los parámetros de entrada
            cStmt.setInt(2, idbillLine);
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
