/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Controller.LPerson;
import Controller.LPets;
import java.io.IOException;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import pojo.Consultation;
import pojo.Person;
import pojo.Pets;

/**
 *
 * @author macarena jbenitez
 */
@Named(value = "petsBean")
@ManagedBean
@RequestScoped
public class PetsBean implements Serializable {

    private Person person;
    private Pets petM = new Pets();

    private String IdPerson;
    private String namePer;
    private String address;
    private String phone;
    private String email;
    private String password;
    private int role;

    private final List<Pets> listamascotas;
    private List<Person> listapersonas;
    private List<Consultation> listconsult;
    private List<Pets> listamascotasPers;
    private List<Pets> listNamePets;
    private List<String> listamaestra;

    /**
     * Creates a new instance of PetsBean
     *
     * @throws java.sql.SQLException
     */
    public PetsBean() throws SQLException {
        listamascotas = LPets.getPets();
        listapersonas = LPerson.getPeople();
        listNamePets = LPets.getPets();
        listamaestra = LPets.getListNamePets();
    }

    public List listPets() throws SQLException {
        LPets lp = new LPets();
        return lp.getPets();
    }

    /**
     * Metodo para insertar mascota
     *
     * @throws SQLException
     * @throws java.io.IOException
     */
    public void addPet() throws SQLException, IOException {
        Boolean res;
        FacesMessage msg;
        res = LPets.addPet(petM.getNamepet(), petM.getAnimal(), petM.getGender(), petM.getRace(), petM.getColour(), petM.getBirthDate(), IdPerson);
        if (res) {
            msg = new FacesMessage("Mascota Insertada: " + petM.getNamepet());
        } else {
            msg = new FacesMessage("Mascota No Insertada: " + petM.getNamepet() + " Ha surgido un problema");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/pets.xhtml");

    }

    /**
     * Metodo para borrar una mascota
     *
     * @param idpet
     * @throws SQLException
     * @throws java.io.IOException
     */
    public void deletePet(int idpet) throws SQLException, IOException {
        Boolean res = false;
        FacesMessage msg;
        res = LPets.deletePet(idpet);
        if (res) {
            msg = new FacesMessage("Mascota Eliminada: " + petM.getNamepet());
        } else {
            msg = new FacesMessage("Mascota No Eliminada: " + petM.getNamepet() + " Ha surgido un problema");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/pets.xhtml");

    }
    
    /**
     * Método que devolverá una lista de los objetos mascota por cada persona
     * @param IdPersona
     * @throws SQLException 
     */
     public void BuscaMascotas(String IdPersona) throws SQLException {
         listamascotasPers = LPets.getPets(IdPersona);
     }

    /**
     * Metodo para actualizar mascotas
     *
     * @param event
     * @throws SQLException
     */
    public void onRowEdit(RowEditEvent event) throws SQLException {
        Boolean res = false;
        FacesMessage msg;
        Pets petMo = (Pets) event.getObject();
        res = LPets.updatePet(petMo.getIdpets(), petMo.getNamepet(), petMo.getAnimal(), petMo.getGender(), petMo.getRace(), petMo.getColour(), petMo.getBirthDate());

        if (res) {
            msg = new FacesMessage("Mascota Editada: " + petMo.getNamepet());
        } else {
            msg = new FacesMessage("Mascota No Editada: " + petMo.getNamepet() + " Ha surgido un problema");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

     /**
     * Método para cancelar la edición desde la celda de la tabla
     * @param event 
     */
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada: " + ((Pets) event.getObject()).getNamepet());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Método que transfiere los datos de una mascota de una página a otra
     * @param idpet 
     */
    public void PasarVariable(int idpet) {
        FacesContext fcontext = FacesContext.getCurrentInstance();

        FacesMessage message = null;
        Pets pet = new Pets();
        try {
            pet = LPets.getPet(idpet);
            if (pet == null) {
                fcontext.getExternalContext().redirect("error.html");

            } else {
                fcontext.getExternalContext().getSessionMap().put("mascotaFactura", pet);
                fcontext.getExternalContext().redirect("faces/bills.xhtml");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Método que recoge el identificador de la mascota
     * @return 
     */
    public int recogeId() {
        FacesContext fcontext = FacesContext.getCurrentInstance();
        Pets aux = new Pets();
        aux = (Pets) fcontext.getExternalContext().getSessionMap().get("mascotaFactura");
        return aux.getIdpets();
    }

    /**
     * Método que recoge el nombre de la mascota
     * @return 
     */
    public String recogeNombre() {
        FacesContext fcontext = FacesContext.getCurrentInstance();
        Pets aux = new Pets();
        aux = (Pets) fcontext.getExternalContext().getSessionMap().get("mascotaFactura");
        return aux.getNamepet();
    }
    
    /**
     * Método que nos realizará el autocompletado dentro del comboBox con el listado de los nombres de las mascotas
     * @param text
     * @return 
     */
    public List<String> AutoCompletePetName(String text) {
        // Assumed search using the startsWith
        List<String> queried = new ArrayList<>();
        for (int i = 0; i < this.listamaestra.size(); i++) {
            String namePet = this.listamaestra.get(i);
            if (namePet.toLowerCase().startsWith(text) || namePet.startsWith(text)) {
                queried.add(namePet);
             }
         }
         return queried;
    }

    /**
     * Metodo obtenido de internet para hacer que el filtrado no distinga entre
     * mayusculas o minusculas
     *
     * @param value
     * @param filter
     * @param locale
     * @return
     */
    public boolean filterCase(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }

        String namePetC = value.toString().toUpperCase();
        filterText = filterText.toUpperCase();

        if (namePetC.contains(filterText)) {
            return true;
        } else {
            return false;
        }
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

    public Person getPerson(String dni) {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Pets getPetM() {
        return petM;
    }

    public void setPetM(Pets pet) {
        this.petM = pet;
    }

    public String getIdPerson() {
        return IdPerson;
    }

    public void setIdPerson(String IdPerson) {
        this.IdPerson = IdPerson;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public List<Pets> getListamascotas() {
        return listamascotas;
    }

    public List<Person> getListapersonas() {
        return listapersonas;
    }

    public void setListapersonas(List<Person> listapersonas) {
        this.listapersonas = listapersonas;
    }

    public List<Consultation> getListconsult() {
        return listconsult;
    }

    public void setListconsult(List<Consultation> listconsult) {
        this.listconsult = listconsult;
    }

     public List<Pets> getListNamePets() {
        return listNamePets;
    }

    public void setListNamePets(List<Pets> listNamePets) {
        this.listNamePets = listNamePets;
    }

    public List<String> getListamaestra() {
        return listamaestra;
    }

    public void setListamaestra(List<String> listamaestra) {
        this.listamaestra = listamaestra;
    }
    public List<Pets> getListamascotasPers() {
        return listamascotasPers;
    }

    public void setListamascotasPers(List<Pets> listamascotasPers) {
        this.listamascotasPers = listamascotasPers;
    }
 
}
