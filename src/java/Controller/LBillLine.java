
package Controller;

import Model.ConexionDB;
import java.sql.CallableStatement;
import java.sql.SQLException;

public class LBillLine extends ConexionDB {

    /**
     * Método para llamada a una rutina PLSQL que añade una nueva línea en una factura
     * @param idBill
     * @param idProd
     * @param quantity
     * @param price
     * @param taxes
     * @param discount
     * @param idPet
     * @param observations
     * @return
     * @throws SQLException 
     */
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

    /**
     * Método para eliminar una línea de una factura según el id de la línea
     * @param idbillLine
     * @return
     * @throws SQLException 
     */
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
