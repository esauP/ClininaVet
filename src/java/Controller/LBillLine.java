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
    
     public static List<BillLines> getList() throws SQLException {
        List<BillLines> listalineasfac = new ArrayList<BillLines>();
        
        ConexionDB conn = new ConexionDB();

//        try {
//            String sql = "select bl.idbill_lines, b.*, p.idpets, p.name, p.animal, p.gender, p.race, p.colour, p.birth_date, pr.* , bl.quantity, bl.taxes, bl.discount, bl.observations from bill b, bill_lines bl, pets p, products pr where b.idbill = bl.idbill_lines and bl.idbill_lines = p.idpets and bl.idbill_lines = pr.idproducts ";
//            PreparedStatement ps = conn.getConexion().prepareStatement(sql);
//
//            ResultSet rs = ps.executeQuery();
//           
//            while (rs.next()) {
//                BillLines aux = new BillLines();
//                Bill auxBill = new Bill();
//                Pets auxPet = new Pets();
//                Products auxProd = new Products();
//                
//                aux.setId(rs.getInt(1));
//                    auxBill.setIdbill(rs.getInt(2));
//                    auxBill.setIdper(rs.getString(3));
//                    auxBill.setDate(rs.getString(4));
//                    auxBill.setObservations(rs.getString(5));
//                        auxPet.setIdpets(rs.getInt(6));
//                        auxPet.setNamepet(rs.getString(7));
//                        auxPet.setAnimal(rs.getString(8));
//                        auxPet.setGender(rs.getInt(9));
//                        auxPet.setRace(rs.getString(10));
//                        auxPet.setColour(rs.getString(11));
//                        auxPet.setBirthDate(rs.getString(12));
//                            auxProd.setIdproducts(rs.getInt(13));
//                            auxProd.setName(rs.getString(14));
//                            auxProd.setPrice(rs.getDouble(15));
//                            auxProd.setTaxes(rs.getInt(16));
//                aux.setQuantity(rs.getInt(17));
//                aux.setTaxes(rs.getInt(18));
//                aux.setDiscount(rs.getInt(19));
//                aux.setObservations(rs.getString(20));
//                listalineasfac.add(aux);
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//
//        } finally {
//            conn.desconectar();
//        }
        return listalineasfac;
    }
     
     public static boolean addLinea(int idBill, int idProd, int quantity, double price, int taxes, int discount, int idPet, String observations) throws SQLException {
       boolean success = false;
        ConexionDB conn = new ConexionDB();
        try {
            //Llamada a la funcion
            String sql = "{ ? = call addBillLines (?,?,?,?,?,?,?, ?, ?) }";
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
