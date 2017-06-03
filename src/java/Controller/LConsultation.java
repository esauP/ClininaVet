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
import java.util.StringTokenizer;
import pojo.*;

/**
 *
 * @author neuhaus
 */
public class LConsultation {

    public List<Consultation> getConsultation() throws SQLException {
        List<Consultation> listaconsultas = new ArrayList<Consultation>();
        ConexionDB conn = new ConexionDB();

        try {
            String sql = "Select idcons, idpet, date_consultation, reason, diagnosis, treatment, observation from consultation";
            PreparedStatement ps = conn.getConexion().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String fecha = rs.getString(3);
                String day = "", mon = "", year = "", fecha_cons = "";//creamos las variables necesarias para el conversor de fechas
                StringTokenizer g = new StringTokenizer(fecha, "-");//pasamos el stringTokenizer para separar los tres tokens 

                year = g.nextToken();
                mon = g.nextToken();
                day = g.nextToken();// como sabemos que hay 3 tokens no necesitamos ninguna estructura reiterativa
                fecha_cons = day + "-" + mon + "-" + year;//asi cambiamos el formato de fecha      

                Consultation aux = new Consultation();
                aux.setIdcons(rs.getInt(1));
                aux.setIdpets(rs.getInt(2));
                aux.setDate(fecha_cons);
                aux.setReason(rs.getString(4));
                aux.setDiagnosis(rs.getString(5));
                aux.setTreatment(rs.getString(6));
                aux.setObservation(rs.getString(7));
                listaconsultas.add(aux);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            conn.desconectar();
        }
        return listaconsultas;
    }

    public boolean addConsultation(int idpet, String date, String reason, String diagnosis, String treatment, String observation) {
        ConexionDB conn = new ConexionDB();
        boolean success = false;
        try {

            String day, mon, year, fecha_cons;//creamos las variables necesarias
            StringTokenizer g = new StringTokenizer(date, "-");//pasamos el stringTokenizer para separar los tres tokens 
            day = g.nextToken();
            mon = g.nextToken();
            year = g.nextToken();// como sabemos que hay 3 tokens no necesitamos ninguna estructura reiterativa
            fecha_cons = year + "-" + mon + "-" + day;//asi cambiamos el formato de fecha     

            //Llamada a la funcion
            String sql = "{ ? = call addConsultation (?,?,?,?,?,?) }";
            java.sql.CallableStatement cStmt = conn.getConexion().prepareCall(sql);
            //establezco la salida de la funcion
            cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
            //establezco los parámetros de entrada
            cStmt.setInt(2, idpet);
            cStmt.setString(3, fecha_cons);
            cStmt.setString(4, reason);
            cStmt.setString(5, diagnosis);
            cStmt.setString(6, treatment);
            cStmt.setString(7, observation);
            //se ejecuta la funcion
            cStmt.execute();
            if (cStmt.getInt(1) == 0) {
                success = true;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return success;
    }

    public boolean updateConsultation(int idcons, int idpet, String date, String reason, String diagnosis, String treatment, String observation) {
        boolean success = false;
        ConexionDB conn = new ConexionDB();
        try {
            String day, mon, year, fecha_cons;//creamos las variables necesarias
            StringTokenizer g = new StringTokenizer(date, "-");//pasamos el stringTokenizer para separar los tres tokens 
            day = g.nextToken();
            mon = g.nextToken();
            year = g.nextToken();// como sabemos que hay 3 tokens no necesitamos ninguna estructura reiterativa
            fecha_cons = year + "-" + mon + "-" + day;//asi cambiamos el formato de fecha     

            //Llamada a la funcion
            String sql = "{ ? = call updateConsultation (?,?,?,?,?,?,?) }";
            CallableStatement cStmt = conn.getConexion().prepareCall(sql);
            //establezco la salida de la funcion
            cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
            //establezco los parámetros de entrada
            cStmt.setInt(2, idcons);
            cStmt.setInt(3, idpet);
            cStmt.setString(4, fecha_cons);
            cStmt.setString(5, reason);
            cStmt.setString(6, diagnosis);
            cStmt.setString(7, treatment);
            cStmt.setString(8, observation);
            //se ejecuta la funcion
            cStmt.execute();
            if (cStmt.getInt(1) == 0) {
                success = true;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return success;
    }

    public static void deleteConsultation(int idcons) {
        boolean success = false;
        ConexionDB conn = new ConexionDB();
        try {
            //Llamada a la funcion
            String sql = "{ ? = call deleteConsultation (?) }";
            CallableStatement cStmt = conn.getConexion().prepareCall(sql);
            //establezco la salida de la funcion
            cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
            //establezco los parámetros de entrada
            cStmt.setInt(2, idcons);
            //se ejecuta la funcion
            cStmt.execute();
            if (cStmt.getInt(1) == 0) {
                success = true;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public List<String> getidConsult() throws SQLException {
        List<String> listconsult = new ArrayList<String>();
        ConexionDB conn = new ConexionDB();
        try {
            String sql = "SELECT idcons FROM consultation ORDER BY idcons ASC";
            PreparedStatement ps = conn.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String text;
                text = String.valueOf(rs.getInt("idcons"));
                listconsult.add(text);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            conn.desconectar();
        }
        return listconsult;
    }

    public List<Consultation> getConsultationId(int idpet) throws SQLException {
        List<Consultation> listaconsultas = new ArrayList<Consultation>();
        ConexionDB conn = new ConexionDB();
        try {
            String sql = "Select idcons, idpet, date_consultation, reason, diagnosis, treatment, observation from consultation where idpet=" + idpet + ";";
            PreparedStatement ps = conn.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String fecha = rs.getString(3);
                String day = "", mon = "", year = "", fecha_cons = "";//creamos las variables necesarias para el conversor de fechas
                StringTokenizer g = new StringTokenizer(fecha, "-");//pasamos el stringTokenizer para separar los tres tokens 

                year = g.nextToken();
                mon = g.nextToken();
                day = g.nextToken();// como sabemos que hay 3 tokens no necesitamos ninguna estructura reiterativa
                fecha_cons = day + "-" + mon + "-" + year;//asi cambiamos el formato de fecha      

                Consultation aux = new Consultation();
                aux.setIdcons(rs.getInt(1));
                aux.setIdpets(rs.getInt(2));
                aux.setDate(fecha_cons);
                aux.setReason(rs.getString(4));
                aux.setDiagnosis(rs.getString(5));
                aux.setTreatment(rs.getString(6));
                aux.setObservation(rs.getString(7));
                listaconsultas.add(aux);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            conn.desconectar();
        }
        return listaconsultas;
    }

}
