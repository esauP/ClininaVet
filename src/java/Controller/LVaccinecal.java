/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ConexionDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author neuhaus
 */
public class LVaccinecal extends ConexionDB {

    public List<String> getVaccines() throws SQLException {
        List<String> listamaestra = new ArrayList<String>();
        try {
            String sql = "SELECT DISTINCT name FROM vaccinecal ORDER BY name ASC";
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
    


}
