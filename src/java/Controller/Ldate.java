
package Controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.ConexionDB;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import pojo.Dates;
import java.text.SimpleDateFormat;


public class Ldate extends ConexionDB {

    /**
     * Método para cargar todas las citas de la base de datos y cargarlas como
     * objetos en una ArrayList, además realiza el parseo de formato de fecha
     * @return 
     */
    public ArrayList<Dates> getDates() {
        ArrayList<Dates> listaCitas = new ArrayList<>();

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
                dd.setNamepet(rs.getString("namepet"));
                if (rs.getString("observations") == null) {
                    dd.setObservations("");
                } else {
                    dd.setObservations(rs.getString("observations"));
                }

                listaCitas.add(dd);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listaCitas;
    }
    
    /**
     * Método para añadir una nueva cita
     * @param dDate
     * @param hour
     * @param idPerson
     * @param namePet
     * @param observations
     * @return
     * @throws SQLException 
     */
    public boolean addDate(Date dDate, Time hour, String idPerson, String namePet, String observations) throws SQLException {
        boolean success = false;
        try {
            //Llamada a la funcion
            String sql = "{ ? = call addDate (?,?,?, ?, ?) }";
            CallableStatement cStmt = this.getConexion().prepareCall(sql);
            //establezco la salida de la funcion
            cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
            //establezco los parÃ¡metros de entrada
            cStmt.setDate(2, dDate);
            cStmt.setTime(3, hour);
            cStmt.setString(4, idPerson);
            cStmt.setString(5, namePet);
            cStmt.setString(6, observations);
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

    /**
     * Método para eliminar una cita
     * @param idDate
     * @return
     * @throws SQLException 
     */
    public static boolean deleteDate(int idDate) throws SQLException {
       boolean success = false;
        ConexionDB conn = new ConexionDB();
        try {
            //Llamada a la funcion
            String sql = "{ ? = call deleteDate (?) }";
            CallableStatement cStmt = conn.getConexion().prepareCall(sql);
            //establezco la salida de la funcion
            cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
            //establezco los parÃ¡metros de entrada
            cStmt.setInt(2, idDate);
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
     * Método para obtener todas las citas del día presente y almacenarlas como objetos
     * en una ArrayList
     * @return 
     */
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
