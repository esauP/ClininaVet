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
import pojo.Vaccines;

/**
 *
 * @author neuhaus
 */
public class LVaccines extends ConexionDB {

    public List<Vaccines> getVaccines() throws SQLException {
        List<Vaccines> listavacunas = new ArrayList<Vaccines>();
        try {
            String sql = "SELECT idvac, idpet, date_vaccine, observations, name FROM vaccines";
            PreparedStatement ps = this.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                String fecha = rs.getString("date_vaccine");
                String day = "", mon = "", year = "", fecha_vaccine = "";//creamos las variables necesarias para el conversor de fechas
                StringTokenizer g = new StringTokenizer(fecha, "-");//pasamos el stringTokenizer para separar los tres tokens 

                year = g.nextToken();
                mon = g.nextToken();
                day = g.nextToken();// como sabemos que hay 3 tokens no necesitamos ninguna estructura reiterativa
                fecha_vaccine = day + "-" + mon + "-" + year;//asi cambiamos el formato de fecha      

                Vaccines aux = new Vaccines();
                aux.setIdvac(rs.getInt("idvac"));
                aux.setIdpet(rs.getInt("idpet"));
                aux.setDate_vaccine(fecha_vaccine);
                aux.setObservaciones(rs.getString("observations"));
                aux.setName_vaccine(rs.getString("name"));
                listavacunas.add(aux);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.desconectar();
        }
        return listavacunas;
    }

    public boolean addVacines(int idpet, String date, String observations, String name) throws SQLException {
        boolean success = false;
        try {

            String day, mon, year, fecha_vaccine;//creamos las variables necesarias
            StringTokenizer g = new StringTokenizer(date, "-");//pasamos el stringTokenizer para separar los tres tokens 
            day = g.nextToken();
            mon = g.nextToken();
            year = g.nextToken();// como sabemos que hay 3 tokens no necesitamos ninguna estructura reiterativa
            fecha_vaccine = year + "-" + mon + "-" + day;//asi cambiamos el formato de fecha               

            //Llamada a la funcion
            String sql = "{ ? = call addVacines (?,?,?,?) }";
            CallableStatement cStmt = this.getConexion().prepareCall(sql);
            //establezco la salida de la funcion
            cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
            //establezco los parámetros de entrada
            cStmt.setInt(2, idpet);
            cStmt.setString(3, fecha_vaccine);
            cStmt.setString(4, observations);
            cStmt.setString(5, name);
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

    public boolean updateVacines(int idvac, int idpet, String date, String observations, String name) throws SQLException {
        boolean success = false;
        try {

            String day, mon, year, fecha_vaccine;//creamos las variables necesarias
            StringTokenizer g = new StringTokenizer(date, "-");//pasamos el stringTokenizer para separar los tres tokens 
            day = g.nextToken();
            mon = g.nextToken();
            year = g.nextToken();// como sabemos que hay 3 tokens no necesitamos ninguna estructura reiterativa
            fecha_vaccine = year + "-" + mon + "-" + day;//asi cambiamos el formato de fecha     

            //Llamada a la funcion
            String sql = "{ ? = call updateVacines (?,?,?,?,?) }";
            CallableStatement cStmt = this.getConexion().prepareCall(sql);
            //establezco la salida de la funcion
            cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
            //establezco los parámetros de entrada
            cStmt.setInt(2, idvac);
            cStmt.setInt(3, idpet);
            cStmt.setString(4, fecha_vaccine);
            cStmt.setString(5, observations);
            cStmt.setString(6, name);
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

    public boolean deleteVacines(int idvac) throws SQLException {
        boolean success = false;
        try {
            //Llamada a la funcion
            String sql = "{ ? = call deleteVacines (?) }";
            CallableStatement cStmt = this.getConexion().prepareCall(sql);
            //establezco la salida de la funcion
            cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
            //establezco los parámetros de entrada
            cStmt.setInt(2, idvac);
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

//    public static void main(String[] args) {
//        LVaccines d = new LVaccines();
//        try {
//            if (d.addVacines(1, "2020-01-01", "21:19", "Polivalente") == true) {
//                System.out.println("bien");
//            } else {
//                System.out.println("error");
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//
//        }
//    }
}
