/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Controller.LConsultation;
import Controller.LPets;
import Model.ConexionDB;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.TableColumn;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;
import pojo.Consultation;

/**
 *
 * @author neuhaus
 */
@Named(value = "consultationBean")
@ManagedBean
@RequestScoped
public class ConsultationBean {

    private int idcons;
    private int idpet;
    private String date_consultation;
    private String reason;
    private String diagnosis;
    private String treatment;
    private String observation;

    private List<Consultation> listaconsultas;
    private Consultation consult = new Consultation();
    private List<String> listapets;

    public ConsultationBean() throws SQLException {
        //instanciamos una clase LConsultation que ataca a la base de datos
        LConsultation dao = new LConsultation();
        //rellenamos nuestra List<> con el método de la clase anterior que devuelve todos los resultados
        listaconsultas = dao.getConsultation();
        LPets lp = new LPets();
        listapets = lp.getIdPets();
    }

    /**
     * Método para la editar el valor de una fila en el xhtml
     * @param event 
     */
    public void onRowEdit(RowEditEvent event) {
        LConsultation dao = new LConsultation();
        Consultation consulta = (Consultation) event.getObject();
        FacesMessage msg = new FacesMessage("Consulta Editada", consulta.getIdcons().toString());
        dao.updateConsultation(consulta.getIdcons(), consulta.getIdpets(), consulta.getDate(), consulta.getReason(), consulta.getDiagnosis(), consulta.getTreatment(), consulta.getObservation());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", String.valueOf(((Consultation) event.getObject()).getIdcons())); //Se ha casteado el id que estaba en integer
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

    public void AddConsultation() throws SQLException {
        LConsultation dao = new LConsultation();
        dao.addConsultation(consult.getIdpets(), consult.getDate(), consult.getReason(), consult.getDiagnosis(),
                consult.getTreatment(), consult.getObservation());
    }

    public void eliminarConsultation(int getIdcons) throws SQLException {
        System.out.println("Id de la consulta: " + getIdcons);
        LConsultation.deleteConsultation(getIdcons);
    }

    public void eliminarConsultation2(int getIdcons) throws SQLException {
        boolean success = false;
        ConexionDB conn = new ConexionDB();
        try {
            //Llamada a la funcion
            String sql = "{ ? = call deleteConsultation (?) }";
            java.sql.CallableStatement cStmt = conn.getConexion().prepareCall(sql);
            //establezco la salida de la funcion
            cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
            //establezco los parámetros de entrada
            cStmt.setInt(2, getIdcons);
            //se ejecuta la funcion
            cStmt.execute();
            if (cStmt.getInt(1) == 0) {
                success = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
 
    public List listar() throws SQLException {
        LConsultation dao = new LConsultation();
        return dao.getConsultation();
    }

    public List<Consultation> getListaconsultas() {
        return listaconsultas;
    }

    public void setListaconsultas(List<Consultation> listaconsultas) {
        this.listaconsultas = listaconsultas;
    }

    public Consultation getConsult() {
        return consult;
    }

    public void setConsult(Consultation consult) {
        this.consult = consult;
    }

    public int getIdcons() {
        return idcons;
    }

    public void setIdcons(int idcons) {
        this.idcons = idcons;
    }

    public int getIdpet() {
        return idpet;
    }

    public void setIdpet(int idpet) {
        this.idpet = idpet;
    }

    public String getDate_consultation() {
        return date_consultation;
    }

    public void setDate_consultation(String date_consultation) {
        this.date_consultation = date_consultation;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public List<String> getListapets() {
        return listapets;
    }

    public void setListapets(List<String> listapets) {
        this.listapets = listapets;
    }

//          public void AddConsultation2() throws SQLException {
//        ConexionDB conn = new ConexionDB();
//        boolean success = false;
//        try {
//            //Llamada a la funcion
//            String sql = "{ ? = call addConsultation (?,?,?,?,?,?) }";
//            java.sql.CallableStatement cStmt = conn.getConexion().prepareCall(sql);
//            //establezco la salida de la funcion
//            cStmt.registerOutParameter(1, java.sql.Types.INTEGER);
//            //establezco los parámetros de entrada
//            cStmt.setInt(2, consult.getIdpets());
//            cStmt.setString(3, consult.getDate());
//            cStmt.setString(4, consult.getReason());
//            cStmt.setString(5, consult.getDiagnosis());
//            cStmt.setString(6, consult.getTreatment());
//            cStmt.setString(7, consult.getObservation());
//            //se ejecuta la funcion
//            cStmt.execute();
//            if (cStmt.getInt(1) == 0) {
//                success = true;
//            }
//        } catch (SQLException e) {
//            System.err.println(e.getMessage());
//        }
//    }
}