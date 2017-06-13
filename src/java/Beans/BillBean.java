
package Beans;

import Controller.LBill;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import pojo.Bill;
import pojo.Person;
import pojo.Pets;

//Anotaciones para que el xhtml pueda operar con ella
@Named(value = "billBean")
@ManagedBean
@RequestScoped
public class BillBean implements Serializable {

    private int idbill;
    private String idper;
    private String nameper;
    private String date;
    private String observations;
    private List<Bill> listafacturas;
    private Bill fac = new Bill();

    public BillBean() throws SQLException {

        this.date = getFechaActual();
        this.idbill = LBill.GetNextIdBill();

        FacesContext fcontext = FacesContext.getCurrentInstance();

        try {
            Person perf = (Person) fcontext.getExternalContext().getSessionMap().get("personaFactura");
            this.idper = perf.getIdperson();
            this.nameper = perf.getNamePer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para insertar una nueva factura
     *
     * @throws SQLException
     */
    public void AddBill() throws SQLException {
        LBill.addBill(fac.getIdper(), fac.getDate(), fac.getObservations());
        FacesMessage msg = new FacesMessage("Factura Insertada", fac.getIdbill().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Metodo para eliminar una factura
     *
     * @throws SQLException
     */
    public void DeleteBill() throws SQLException {
        LBill.deleteBill(fac.getIdbill());
        FacesMessage msg = new FacesMessage("Factura Eliminada", fac.getIdbill().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Metodo que nos devuelve la fecha actual en formato String
     *
     * @return String fecha actual
     */
    public static String getFechaActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        return formateador.format(ahora);
    }

    public int getIdbill() {
        return idbill;
    }

    public void setIdbill(int idbill) {
        this.idbill = idbill;
    }

    public String getIdper() {
        return idper;
    }

    public void setIdper(String idper) {
        this.idper = idper;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public List<Bill> getListafacturas() {
        return listafacturas;
    }

    public void setListafacturas(List<Bill> listafacturas) {
        this.listafacturas = listafacturas;
    }

    public Bill getFac() {
        return fac;
    }

    public void setFac(Bill fac) {
        this.fac = fac;
    }

    public String getNameper() {
        return nameper;
    }

    public void setNameper(String nameper) {
        this.nameper = nameper;
    }

}
