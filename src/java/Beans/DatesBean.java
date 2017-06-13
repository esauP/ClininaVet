
package Beans;

import Controller.LPerson;
import Controller.LPets;
import Controller.Ldate;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import pojo.Dates;
import pojo.Person;
import pojo.Pets;

//Anotaciones para que el xhtml pueda operar con ella
@Named(value = "datesBean")
@ManagedBean
@RequestScoped

public class DatesBean implements Serializable{

    private int id;
    private String date;
    private Date hour;
    private String observations;
    private String nameper;
    private String namepet;
    private List<Dates> listacitas;
    private String idper;
    private Dates dateD = new Dates();
    private List<Pets> listamascotasPers;
    private List<Person> listapersonas;
       
    /**
     * Creates a new instance of PetsBean
     *
     * @throws java.sql.SQLException
     */
    public DatesBean() throws SQLException {
        listapersonas = LPerson.getPeople();
        this.listacitas = Ldate.getDatesDay();

    }
    
    /**
     * Método que filtra las citas según el día
     * @param value
     * @param filter
     * @param locale
     * @return 
     */
    public boolean filterByDay(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }

        String dayDate = value.toString().toUpperCase();
        filterText = filterText.toUpperCase();

        if (dayDate.contains(filterText)) {
            return true;
        } else {
            return false;
        }
    }
    
    public void KeepSelection(String idpers) {

        FacesContext fcontext = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage("Mascota Fijada");
        Pets petk;
        try {

            petk = LPets.getPet(this.namepet);

            if ((petk.getIdpets()) != 0) {
                fcontext.getExternalContext().getSessionMap().put("mascotaFac", petk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
        System.out.println("Fijar Mascota");
    }
    
    /**
     * Metodo para eliminar una cita
     *
     * @param idDate
     * @throws SQLException
     * @throws IOException
     */
    public void deleteDate(int idDate) throws SQLException, IOException {

        Boolean res;
        FacesMessage msg;
        res = Ldate.deleteDate(idDate);
        if (res) {
            msg = new FacesMessage("Cita eliminada para: " + dateD.getNamepet());
        } else {
            msg = new FacesMessage("Cita no eliminada para: " + dateD.getNamepet()+ " Ha surgido un problema");
        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/dates.xhtml");

    }
    
    /**
     * Metodo para autocompletar los id de las personas
     *
     * @param text
     * @return List String
     */
    public List<String> AutocompletarID(String text) {
        // Assumed search using the startsWith
        List<String> queried = new ArrayList<>();
        for (Person person : this.listapersonas) {
            String namePer = person.getNamePer();
            if (namePer.toLowerCase().startsWith(text) || namePer.startsWith(text)) {
                queried.add(person.getIdperson());
            }
        }
        return queried;
    }
    
      /**
     * Método para carga un listado de mascotas asociados al id de una persona
     * @param IdPersona
     * @throws SQLException 
     */
    public void BuscaMascotas(String IdPersona) throws SQLException {
        listamascotasPers = LPets.getPets(IdPersona);
    }

    public String getIdper() {
        return idper;
    }

    public void setIdper(String idper) {
        this.idper = idper;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Date getHour() {
        return hour;
    }

    public void setHour(Date hour) {
        this.hour = hour;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getNameper() {
        return nameper;
    }

    public void setNameper(String nameper) {
        this.nameper = nameper;
    }

    public String getNamepet() {
        return namepet;
    }

    public void setNamepet(String namepet) {
        this.namepet = namepet;
    }

    public List<Dates> getListacitas() {
        return listacitas;
    }

    public void setListacitas(List<Dates> listacitas) {
        this.listacitas = listacitas;
    }
  
    public Dates getDateD() {
        return dateD;
    }

    public void setDateD(Dates dateD) {
        this.dateD = dateD;
    }

    public List<Pets> getListamascotasPers() {
        return listamascotasPers;
    }

    public void setListamascotasPers(List<Pets> listamascotasPers) {
        this.listamascotasPers = listamascotasPers;
    }
    
    public List<Person> getListapersonas() {
        return listapersonas;
    }

    public void setListapersonas(List<Person> listapersonas) {
        this.listapersonas = listapersonas;
    }

}
