/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Controller.LPerson;
import Controller.LPets;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
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

    /**
     * Creates a new instance of PetsBean
     *
     * @throws java.sql.SQLException
     */
    public PetsBean() throws SQLException {
        listamascotas = LPets.getPets();
        listapersonas = LPerson.getPeople();

    }

    public void BuscaMascotas(String IdPersona) throws SQLException {
                listamascotasPers = LPets.getPets(IdPersona);
    }

    /**
     * Metodo para insertar mascota
     *
     * @throws SQLException
     */
    public void addPet() throws SQLException {
        LPets.addPet(petM.getNamepet(), petM.getAnimal(), petM.getGender(), petM.getRace(), petM.getColour(), petM.getBirthDate(), IdPerson);
        FacesMessage msg = new FacesMessage("Mascota Insertada", petM.getNamepet());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Metodo para actualizar mascota
     *
     * @throws SQLException
     */
    public void updatePet() throws SQLException {
        LPets.updatePet(petM.getIdpets(), petM.getNamepet(), petM.getAnimal(), petM.getGender(), petM.getRace(), petM.getColour(), petM.getBirthDate());
        FacesMessage msg = new FacesMessage("Mascota Actualizada", petM.getNamepet());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Metodo para borrar una mascota
     *
     * @throws SQLException
     */
    public void deletePet() throws SQLException {
        LPets.deletePet(petM.getIdpets());
        FacesMessage msg = new FacesMessage("Mascota Eliminada", petM.getNamepet());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowEdit(RowEditEvent event) throws SQLException {
        Pets petMo = (Pets) event.getObject();
        FacesMessage msg = new FacesMessage("Persona Editada", petMo.getNamepet());
        LPets.updatePet(petM.getIdpets(), petM.getNamepet(), petM.getAnimal(), petM.getGender(), petM.getRace(), petM.getColour(), petM.getBirthDate());
        LPerson.updatePersonPets(IdPerson, namePer, address, phone, email, 4);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancellada", ((Pets) event.getObject()).getNamepet());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void getconsultationidPet(Integer idpet) throws SQLException {
        List<Consultation> aux = new ArrayList<Consultation>();
        aux = LPets.getHistorial(idpet);
        this.listconsult = aux;
    }

    public void PasarVariable(int idpet) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fcontext = FacesContext.getCurrentInstance();

        FacesMessage message = null;
        boolean loggedIn = false;
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
        context.addCallbackParam("Logeado", loggedIn);
    }

    public int recogeId() {
//        FacesContext fcontext = FacesContext.getCurrentInstance();
//        Pets aux = new Pets();
//        aux = (Pets) fcontext.getExternalContext().getSessionMap().get("mascotaFactura");
//        return aux.getIdpets();
        int var = 0;
        return var;
    }

    public String recogeNombre() {
//        FacesContext fcontext = FacesContext.getCurrentInstance();
//        Pets aux = new Pets();
//        aux = (Pets) fcontext.getExternalContext().getSessionMap().get("mascotaFactura");
//        return aux.getName();
        String var = "hola";
        return var;
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

    public List<Pets> getListamascotasPers() {
        return listamascotasPers;
    }

    public void setListamascotasPers(List<Pets> listamascotasPers) {
        this.listamascotasPers = listamascotasPers;
    }
}
