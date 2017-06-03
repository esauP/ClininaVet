/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ConexionDB;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import pojo.*;

/**
 *
 * @author neuhaus
 */
public class LDocument extends ConexionDB {

    public List<Doc> getDocuments() throws SQLException {
        List<Doc> listadoc = new ArrayList<Doc>();
        ConexionDB conn = new ConexionDB();
        try {
            String sql = "Select iddoc, idcons, date_doc, description, fileattached from doc";
            PreparedStatement ps = conn.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                String fecha = rs.getString("date_doc");
                String day = "", mon = "", year = "", fecha_doc = "";//creamos las variables necesarias para el conversor de fechas
                StringTokenizer g = new StringTokenizer(fecha, "-");//pasamos el stringTokenizer para separar los tres tokens 

                year = g.nextToken();
                mon = g.nextToken();
                day = g.nextToken();// como sabemos que hay 3 tokens no necesitamos ninguna estructura reiterativa
                fecha_doc = day + "-" + mon + "-" + year;//asi cambiamos el formato de fecha      

                Doc aux = new Doc();
                aux.setIddoc(rs.getInt("iddoc"));
                aux.setIdcons(rs.getInt("idcons"));
                aux.setDate_doc(fecha_doc);
                aux.setDescription(rs.getString("description"));
                aux.setFileattached(rs.getString("fileattached"));
                listadoc.add(aux);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            conn.desconectar();
        }
        return listadoc;
    }

    public boolean addDoc(int idcon, String date_doc, String description, String fileattached) throws SQLException {
        ConexionDB conn = new ConexionDB();
        boolean success = false;
        try {
            String day, mon, year, fecha_doc;//creamos las variables necesarias
            StringTokenizer g = new StringTokenizer(date_doc, "-");//pasamos el stringTokenizer para separar los tres tokens 
            day = g.nextToken();
            mon = g.nextToken();
            year = g.nextToken();// como sabemos que hay 3 tokens no necesitamos ninguna estructura reiterativa
            fecha_doc = year + "-" + mon + "-" + day;//asi cambiamos el formato de fecha     

            //Llamada a la funcion
            String sql = "{ ? = call addDoc (?,?,?,?) }";
            CallableStatement cStmt = this.getConexion().prepareCall(sql);
            //establezco la salida de la funcion
            cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
            //establezco los parámetros de entrada
            cStmt.setInt(2, idcon);
            cStmt.setString(3, fecha_doc);
            cStmt.setString(4, description);
            cStmt.setString(5, fileattached);
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

    public boolean updateDoc(int iddoc, int idcon, String date_doc, String description, String fileattached) throws SQLException {
        ConexionDB conn = new ConexionDB();
        boolean success = false;
        try {

            String day, mon, year, fecha_doc;//creamos las variables necesarias
            StringTokenizer g = new StringTokenizer(date_doc, "-");//pasamos el stringTokenizer para separar los tres tokens 
            day = g.nextToken();
            mon = g.nextToken();
            year = g.nextToken();// como sabemos que hay 3 tokens no necesitamos ninguna estructura reiterativa
            fecha_doc = year + "-" + mon + "-" + day;//asi cambiamos el formato de fecha     

            //Llamada a la funcion
            String sql = "{ ? = call updateDoc (?,?,?,?,?) }";
            CallableStatement cStmt = this.getConexion().prepareCall(sql);
            //establezco la salida de la funcion
            cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
            //establezco los parámetros de entrada
            cStmt.setInt(2, iddoc);
            cStmt.setInt(3, idcon);
            cStmt.setString(4, fecha_doc);
            cStmt.setString(5, description);
            cStmt.setString(6, fileattached);
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

    public boolean deleteDoc(int iddoc) throws SQLException {
        ConexionDB conn = new ConexionDB();
        boolean success = false;
        try {
            //Llamada a la funcion
            String sql = "{ ? = call deleteDoc (?) }";
            CallableStatement cStmt = this.getConexion().prepareCall(sql);
            //establezco la salida de la funcion
            cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
            //establezco los parámetros de entrada
            cStmt.setInt(2, iddoc);
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
