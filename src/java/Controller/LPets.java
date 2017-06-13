
package Controller;

import Model.ConexionDB;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import pojo.Consultation;
import pojo.Person;
import pojo.Pets;


public class LPets extends ConexionDB {

    /**
     * Método que nos devuelve los datos de personas y sus mascotas para rellenar la tabla
     * @return
     * @throws SQLException 
     */
    public static List<Pets> getPets() throws SQLException {
        List<Pets> listamascotas = new ArrayList<Pets>();
        ConexionDB conn = new ConexionDB();

        try {
            String sql = "Select * from pets join person where pets.idper = person.idperson";
            PreparedStatement ps = conn.getConexion().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String fecha = rs.getString(7);
                String day = "", mon = "", year = "", birth_pet = "";//creamos las variables necesarias para el conversor de fechas
                StringTokenizer g = new StringTokenizer(fecha, "-");//pasamos el stringTokenizer para separar los tres tokens 

                year = g.nextToken();
                mon = g.nextToken();
                day = g.nextToken();// como sabemos que hay 3 tokens no necesitamos ninguna estructura reiterativa
                birth_pet = day + "-" + mon + "-" + year;//asi cambiamos el formato de fecha      

                Pets mascota = new Pets();
                Person persona = new Person();

                mascota.setIdpets(rs.getInt(1));
                mascota.setNamepet(rs.getString(2));
                mascota.setAnimal(rs.getString(3));
                mascota.setGender(rs.getInt(4));
                mascota.setRace(rs.getString(5));
                mascota.setColour(rs.getString(6));
                mascota.setBirthDate(birth_pet);
                persona.setIdperson(rs.getString(8));
                persona.setNamePer(rs.getString(10));
                persona.setAddress(rs.getString(11));
                persona.setPhone(rs.getString(12));
                persona.setEmail(rs.getString(13));
                persona.setPassword(rs.getString(14));
                persona.setRole(rs.getInt(15));
                mascota.setPerson(persona);

                listamascotas.add(mascota);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println(e.getSQLState());
            e.printStackTrace();

        } finally {
            conn.desconectar();
        }
        return listamascotas;
    }

    /**
     * Metodo que devuelve la lista de mascotas asociadas a un dueño
     *
     * @param idperson
     * @return List Pets
     * @throws SQLException
     */
    public static List<Pets> getPets(String idperson) throws SQLException {
        List<Pets> listamascotas = new ArrayList<>();
        ConexionDB conn = new ConexionDB();

        try {
            String sql = "select * from pets where idper = '" + idperson + "'";
            PreparedStatement ps = conn.getConexion().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String fecha = rs.getString(7);
                String day = "", mon = "", year = "", birth_pet = "";//creamos las variables necesarias para el conversor de fechas
                StringTokenizer g = new StringTokenizer(fecha, "-");//pasamos el stringTokenizer para separar los tres tokens 

                year = g.nextToken();
                mon = g.nextToken();
                day = g.nextToken();// como sabemos que hay 3 tokens no necesitamos ninguna estructura reiterativa
                birth_pet = day + "-" + mon + "-" + year;//asi cambiamos el formato de fecha      

                Pets mascota = new Pets();

                mascota.setIdpets(rs.getInt(1));
                mascota.setNamepet(rs.getString(2));
                mascota.setAnimal(rs.getString(3));
                mascota.setGender(rs.getInt(4));
                mascota.setRace(rs.getString(5));
                mascota.setColour(rs.getString(6));
                mascota.setBirthDate(birth_pet);
                

                listamascotas.add(mascota);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println(e.getSQLState());

        } finally {
            conn.desconectar();
        }
        return listamascotas;
    }

    /**
     * Método que añadirá una mascota a nuestra base de datos
     * @param name_pt
     * @param animal_pt
     * @param gender_pt
     * @param race_pt
     * @param colour_pt
     * @param birth_pt
     * @param idper_pt
     * @return Devuelve un boolean de control para saber si el método se ha realizado con éxito
     * @throws SQLException 
     */
    public static boolean addPet(String name_pt, String animal_pt, int gender_pt, String race_pt, String colour_pt, String birth_pt, String idper_pt) throws SQLException {
        boolean success = false;
        ConexionDB conn = new ConexionDB();
        try {
            String day, mon, year, birth_pet;//creamos las variables necesarias
            StringTokenizer g = new StringTokenizer(birth_pt, "-");//pasamos el stringTokenizer para separar los tres tokens 
            day = g.nextToken();
            mon = g.nextToken();
            year = g.nextToken();// como sabemos que hay 3 tokens no necesitamos ninguna estructura reiterativa
            birth_pet = year + "-" + mon + "-" + day;//asi cambiamos el formato de fecha                

            //Llamada a la funcion
            String sql = "{ ? = call addPet (?,?,?,?,?,?,?) }";
            CallableStatement cStmt = conn.getConexion().prepareCall(sql);
            //establezco la salida de la funcion
            cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
            //establezco los parámetros de entrada
            cStmt.setString(2, name_pt);
            cStmt.setString(3, animal_pt);
            cStmt.setInt(4, gender_pt);
            cStmt.setString(5, race_pt);
            cStmt.setString(6, colour_pt);
            cStmt.setString(7, birth_pet);
            cStmt.setString(8, idper_pt);
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
     * Método que modifica los datos de una mascota
     * @param idpets
     * @param name_pt
     * @param animal_pt
     * @param gender_pt
     * @param race_pt
     * @param colour_pt
     * @param birth_pt
     * @return Devuelve un boolean de control para saber si el método se ha realizado con éxito
     * @throws SQLException 
     */
    public static boolean updatePet(int idpets, String name_pt, String animal_pt, int gender_pt, String race_pt, String colour_pt, String birth_pt) throws SQLException {
        boolean success = false;
        ConexionDB conn = new ConexionDB();
        try {
            String day, mon, year, birth_pet;//creamos las variables necesarias
            StringTokenizer g = new StringTokenizer(birth_pt, "-");//pasamos el stringTokenizer para separar los tres tokens 
            day = g.nextToken();
            mon = g.nextToken();
            year = g.nextToken();// como sabemos que hay 3 tokens no necesitamos ninguna estructura reiterativa
            birth_pet = year + "-" + mon + "-" + day;//asi cambiamos el formato de fecha     
            //Llamada a la funcion
            String sql = "{ ? = call updatePet (?,?,?,?,?,?,?) }";
            CallableStatement cStmt = conn.getConexion().prepareCall(sql);
            //establezco la salida de la funcion
            cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
            //establezco los parámetros de entrada
            cStmt.setInt(2, idpets);
            cStmt.setString(3, name_pt);
            cStmt.setString(4, animal_pt);
            cStmt.setInt(5, gender_pt);
            cStmt.setString(6, race_pt);
            cStmt.setString(7, colour_pt);
            cStmt.setString(8, birth_pet);
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
     * Método que borra una mascota de nuestra base de datos
     * @param idpets
     * @return Devuelve un boolean de control para saber si el método se ha realizado con éxito
     * @throws SQLException 
     */
    public static boolean deletePet(int idpets) throws SQLException {
        boolean success = false;
        ConexionDB conn = new ConexionDB();
        try {
            //Llamada a la funcion
            String sql = "{ ? = call deletePet (?) }";
            CallableStatement cStmt = conn.getConexion().prepareCall(sql);
            //establezco la salida de la funcion
            cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
            //establezco los parámetros de entrada
            cStmt.setInt(2, idpets);
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
     * Método que nos devuelve una lista de los identificadores de mascotas
     * @return
     * @throws SQLException 
     */
    public List<String> getIdPets() throws SQLException {
        List<String> listapets = new ArrayList<String>();
        try {
            String sql = "SELECT idpets FROM pets ORDER BY idpets ASC";
            PreparedStatement ps = this.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String text;
                text = String.valueOf(rs.getInt("idpets"));
                listapets.add(text);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            this.desconectar();
        }
        return listapets;
    }
    
     /**
     * Método que nos devuelve un listado de los nombres de las mascotas almacenadas en la base de datos
     * @return
     * @throws SQLException 
     */
    public static List<String> getListNamePets() throws SQLException {
        List<String> listamaestra = new ArrayList<String>();
        ConexionDB conn = new ConexionDB();
        try {
            String sql = "SELECT name FROM pets ORDER BY name ASC";
            PreparedStatement ps = conn.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String text;
                text = rs.getString("name");
                listamaestra.add(text);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            conn.desconectar();
        }
        return listamaestra;
    }

    /**
     * Metodo que nos devolvera la mascota dado su id
     * @param idpet
     * @return Pets
     */
    public static Pets getPet(int idpet) {
        Pets pet = new Pets();
        ConexionDB conn = new ConexionDB();

        try {
            String sql = "Select * from pets where idpets = " + idpet;
            PreparedStatement ps = conn.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pet.setIdpets(rs.getInt("idpets"));
                pet.setNamepet(rs.getString("name"));
                pet.setAnimal(rs.getString("animal"));
                pet.setGender(rs.getInt("gender"));
                pet.setRace(rs.getString("race"));
                pet.setColour(rs.getString("colour"));
                pet.setBirthDate(rs.getString("birth_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pet;
    }
    /**
     * Metodo que devolvera la mascota dado su nombre
     * @param name
     * @return 
     */
     public static Pets getPet(String name) {
        Pets pet = new Pets();
        ConexionDB conn = new ConexionDB();

        try {
            String sql = "Select * from pets where name = " + name;
            PreparedStatement ps = conn.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pet.setIdpets(rs.getInt("idpets"));
                pet.setNamepet(rs.getString("name"));
                pet.setAnimal(rs.getString("animal"));
                pet.setGender(rs.getInt("gender"));
                pet.setRace(rs.getString("race"));
                pet.setColour(rs.getString("colour"));
                pet.setBirthDate(rs.getString("birth_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pet;
    }

}
