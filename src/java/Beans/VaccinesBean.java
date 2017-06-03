/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Controller.LVaccinecal;
import Controller.LVaccines;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TableColumn;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItemGroup;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;
import pojo.Person;
import pojo.Vaccinecal;
import pojo.Vaccines;

/**
 *
 * @author neuhaus
 */
@Named(value = "vaccinesBean")
@ManagedBean
@RequestScoped
public class VaccinesBean {

    private List<Vaccines> listvaccines;
    private List<String> listamaestra;
    private Vaccines vacuna = new Vaccines();
    private Vaccinecal calendariovacunas = new Vaccinecal();

    public VaccinesBean() throws SQLException {
        LVaccines lv = new LVaccines();
        listvaccines = lv.getVaccines();
        LVaccinecal lvcal = new LVaccinecal();
        listamaestra = lvcal.getVaccines();
    }

    public List listar() throws SQLException {
        LVaccines lv = new LVaccines();
        return lv.getVaccines();
    }

    public void addVaccine() throws SQLException {
        LVaccines lv = new LVaccines();
        lv.addVacines(vacuna.getIdpet(), vacuna.getDate_vaccine(), vacuna.getObservaciones(), vacuna.getName_vaccine());
    }

    public void deleteVaccine(int idvac) throws SQLException {
        LVaccines lv = new LVaccines();
        lv.deleteVacines(idvac);
    }

    public void onRowEdit(RowEditEvent event) throws SQLException {
        LVaccines lv = new LVaccines();
        Vaccines vacuna = (Vaccines) event.getObject();
        FacesMessage msg = new FacesMessage("Consulta Editada", String.valueOf(vacuna.getIdvac()));
        lv.updateVacines(vacuna.getIdvac(), vacuna.getIdpet(), vacuna.getDate_vaccine(), vacuna.getObservaciones(), vacuna.getName_vaccine());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", String.valueOf(((Vaccines) event.getObject()).getIdvac())); //Se ha casteado el id que estaba en integer
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(TableColumn.CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Celda acutalizada", "Antiguo: " + oldValue + ", Nuevo:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /// getter and setters
    public List<Vaccines> getListvaccines() {
        return listvaccines;
    }

    public void setListvaccines(List<Vaccines> listvaccines) {
        this.listvaccines = listvaccines;
    }

    public List<String> getListlistamaestra() {
        return listamaestra;
    }

    public void setListlistamaestra(List<String> listamaestra) {
        this.listamaestra = listamaestra;
    }

    public Vaccines getVacuna() {
        return vacuna;
    }

    public void setVacuna(Vaccines vacuna) {
        this.vacuna = vacuna;
    }

    public List<String> AutocompletarNameVaccine(String text) {
        // Assumed search using the startsWith
        List<String> queried = new ArrayList<>();
        for (int i = 0; i < this.listamaestra.size(); i++) {
            String nameVac = this.listamaestra.get(i);
            if (nameVac.toLowerCase().startsWith(text) || nameVac.startsWith(text)) {
                queried.add(nameVac);
            }
        }
        return queried;
    }

}
