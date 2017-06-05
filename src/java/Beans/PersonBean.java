/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.TableColumn.CellEditEvent;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;
import pojo.Person;
import Controller.LPerson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author esaup
 */
@Named(value = "personBean")
@ManagedBean
@RequestScoped
public class PersonBean implements Serializable {

    private String idperson;
    private String namePer;
    private String address;
    private String phone;
    private String email;
    private List<Person> listapersonas;
    private Person pers = new Person();

    public PersonBean() throws SQLException {
        listapersonas = LPerson.getPeople();
    }

    /**
     * Metodo para insertar un nuevo Cliente
     *
     * @throws SQLException
     * @throws java.io.IOException
     */
    public void AddPerson() throws SQLException, IOException {
        LPerson.addPerson(pers.getIdperson(), pers.getNamePer(), pers.getAddress(), pers.getPhone(), pers.getEmail(), pers.getIdperson(), 4);
        FacesMessage msg = new FacesMessage("Persona Insertada", pers.getNamePer());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/pets.xhtml");

    }

    /**
     * Metodo para actualizar usuario
     *
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    public void UpdatePerson(RowEditEvent event) throws SQLException, IOException {
        LPerson.updatePerson(pers.getIdperson(), pers.getNamePer(), pers.getAddress(), pers.getPhone(), pers.getEmail(), pers.getPassword(), 4);
        FacesMessage msg = new FacesMessage("Persona Actualizada", ((PersonBean) event.getObject()).getNamePer());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/pets.xhtml");

    }

    /**
     * Metodo para eliminar usuario
     *
     * @param idpers
     * @throws SQLException
     * @throws IOException
     */
    public void DeletePerson(String idpers) throws SQLException, IOException {
        LPerson.deletePerson(idpers);
        FacesMessage msg = new FacesMessage("Persona Eliminada", pers.getNamePer());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/pets.xhtml");

    }

    public void onRowEdit(RowEditEvent event) throws SQLException {
        Person personaM = (Person) event.getObject();
        FacesMessage msg = new FacesMessage("Persona Editada", personaM.getNamePer());
        LPerson.updatePerson(personaM.getIdperson(), personaM.getNamePer(), personaM.getAddress(), personaM.getPhone(), personaM.getEmail(), personaM.getPassword(), 4);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancellada", ((Person) event.getObject()).getIdperson());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Celda acutalizada", "Antiguo: " + oldValue + ", Nuevo:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /**
     * Metodo para autocompletar los nombres
     *
     * @param text
     * @return list String
     */
    public List<Person> AutocompletarNombre(String text) {
        // Assumed search using the startsWith
        List<Person> queried = new ArrayList<>();
        for (Person person : this.listapersonas) {
            String name = person.getNamePer();
            if (name.toLowerCase().startsWith(text) || name.startsWith(text)) {
                queried.add(person);
            }
        }
        return queried;
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

    public boolean filterByName(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }

        String namePerC = value.toString().toUpperCase();
        filterText = filterText.toUpperCase();

        if (namePerC.contains(filterText)) {
            return true;
        } else {
            return false;
        }
    }

    public List<Person> getListapersonas() {
        return listapersonas;
    }

    public void setListapersonas(List<Person> listapersonas) {
        this.listapersonas = listapersonas;
    }

    public String getIdperson() {
        return idperson;
    }

    public void setIdperson(String idperson) {
        this.idperson = idperson;
    }

    public String getNamePer() {
        return namePer;
    }

    public void setNamePer(String namePer) {
        this.namePer = namePer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Person getPers() {
        return pers;
    }

    public void setPers(Person pers) {
        this.pers = pers;
    }
}
