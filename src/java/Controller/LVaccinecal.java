package Controller;

import Model.ConexionDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class LVaccinecal extends ConexionDB {

    /**
     * Método que carga y parse los datos de vacunas de la base de datos en
     * un list de objetos de tipo vacunación
     * @return
     * @throws SQLException 
     */
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
