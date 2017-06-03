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
import pojo.Products;

/**
 *
 * @author macarena jbenitez
 */
public class LProducts extends ConexionDB{
    
    public List<Products> getProducts() throws SQLException {
        List<Products> listaProductos = new ArrayList<Products>();
        try {
            String sql = "SELECT idproducts, name, price, taxes FROM products";
            PreparedStatement ps = this.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Products aux = new Products();
                aux.setIdproducts(rs.getInt("idproducts"));
                aux.setName(rs.getString("name"));
                aux.setPrice(rs.getDouble("price"));
                aux.setTaxes(rs.getInt("taxes"));
                listaProductos.add(aux);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.desconectar();
        }
        return listaProductos;
    }

    public boolean addProduct(String name, double price, int taxes) throws SQLException {
        boolean success = false;
        try {
            //Llamada a la funcion
            String sql = "{ ? = call addProducts (?,?,?) }";
            CallableStatement cStmt = this.getConexion().prepareCall(sql);
            //establezco la salida de la funcion
            cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
            //establezco los parámetros de entrada
            cStmt.setString(2, name);
            cStmt.setDouble(3, price);
            cStmt.setInt(4, taxes);
            //se ejecuta la funcion
            cStmt.execute();
            if (cStmt.getInt(1) == 0) {
                success = true;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            this.desconectar();
        }
        return success;
    }

    public boolean updateProduct(int idproducts, String name, double price, int taxes) throws SQLException {
        boolean success = false;
        try {
            //Llamada a la funcion
            String sql = "{ ? = call updateProducts (?,?,?,?,?) }";
            CallableStatement cStmt = this.getConexion().prepareCall(sql);
            //establezco la salida de la funcion
            cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
            //establezco los parámetros de entrada
            cStmt.setInt(2, idproducts);
            cStmt.setString(3, name);
            cStmt.setDouble(4, price);
            cStmt.setInt(5, taxes);
            //se ejecuta la funcion
            cStmt.execute();
            if (cStmt.getInt(1) == 0) {
                success = true;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            this.desconectar();
        }
        return success;
    }

    public boolean deleteProduct(int idproducts) throws SQLException {
        boolean success = false;
        try {
            //Llamada a la funcion
            String sql = "{ ? = call deleteProducts (?) }";
            CallableStatement cStmt = this.getConexion().prepareCall(sql);
            //establezco la salida de la funcion
            cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
            //establezco los parámetros de entrada
            cStmt.setInt(2, idproducts);
            //se ejecuta la funcion
            cStmt.execute();
            if (cStmt.getInt(1) == 0) {
                success = true;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            this.desconectar();
        }
        return success;
    }

    public List<String> getListProducts() throws SQLException {
        List<String> listamaestra = new ArrayList<String>();
        try {
            String sql = "SELECT DISTINCT name FROM products ORDER BY name ASC";
            PreparedStatement ps = this.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String text;
                text = rs.getString("name");
                listamaestra.add(text);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.desconectar();
        }
        return listamaestra;
    }
    
    public Products getProduct(int id) throws SQLException{
        Products prod = new Products();
        
        try {
            String sql = "Select * from products where idproducts = "+id;
            PreparedStatement ps = this.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                prod.setIdproducts(rs.getInt("idproducts"));
                prod.setName(rs.getString("name"));
                prod.setPrice(rs.getDouble("price"));
                prod.setTaxes(rs.getInt("taxes"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.desconectar();
        }
        return prod;
    }
    
}
