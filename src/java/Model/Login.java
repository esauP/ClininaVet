/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import pojo.Person;

/**
 *
 * @author esaup
 */
public class Login {

    public static Person getConectado(String dni, String password) {
        ConexionDB d = new ConexionDB();
        Person ps = new Person();

        try {
            PreparedStatement pstm = d.getConexion().prepareStatement("SELECT * FROM person WHERE idperson='" + dni
                    + "' AND password='" + password + "'");
            ResultSet res = pstm.executeQuery();
            while (res.next()) {
                ps.setIdperson(res.getString("idperson"));
                ps.setPassword(res.getString("password"));
                ps.setRole(res.getInt("role"));
            }
            d.desconectar();

        } catch (SQLException e) {
            System.err.println(e.getMessage());

        }
        return ps;
    }

}
