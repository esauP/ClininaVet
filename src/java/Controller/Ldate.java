/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.ConexionDB;
import java.util.ArrayList;
import pojo.Dates;
import java.text.SimpleDateFormat;

/**
 *
 * @author esaup
 */
public class Ldate extends ConexionDB {

    public ArrayList<Dates> getDates() {
        ArrayList<Dates> lista = new ArrayList<>();

        String sql = "Select * from dates";
        try {
            PreparedStatement ps = this.getConexion().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Dates dd = new Dates();

                dd.setId(rs.getInt("iddate"));
                String time = new SimpleDateFormat("dd-MM-yyyy").format(rs.getDate("date"));
                dd.setDate(time);
                dd.setHour(rs.getTime("hour"));
                dd.setPerson(rs.getString("idperson"));
                dd.setType(rs.getInt("type"));
                dd.setNameper(rs.getString("nameper"));
                dd.setNamepet(rs.getString("namepet"));
                if (rs.getString("observations") == null) {
                    dd.setObservations("");
                } else {
                    dd.setObservations(rs.getString("observations"));
                }

                lista.add(dd);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }

    public static ArrayList<Dates> getDatesDay() {
        ArrayList<Dates> lista = new ArrayList<>();
        ConexionDB conn = new ConexionDB();

        String sql = "select * from dates d where d.date=CURDATE() order by d.hour ASC;";
        try {
            PreparedStatement ps = conn.getConexion().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Dates dd = new Dates();

                dd.setId(rs.getInt("iddate"));
                String time = new SimpleDateFormat("dd-MM-yyyy").format(rs.getDate("date"));
                dd.setDate(time);
                dd.setHour(rs.getTime("hour"));
                dd.setPerson(rs.getString("idperson"));
                dd.setType(rs.getInt("type"));
                dd.setNameper(rs.getString("nameper"));
                dd.setNamepet(rs.getString("namepet"));
                if (rs.getString("observations") == null) {
                    dd.setObservations("");
                } else {
                    dd.setObservations(rs.getString("observations"));
                }

                lista.add(dd);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }
}
