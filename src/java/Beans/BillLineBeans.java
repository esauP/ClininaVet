/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.sql.SQLException;
import java.util.List;
import javax.inject.Named;
import pojo.Products;
import pojo.BillLines;
import Controller.LBillLine;
import Controller.LPets;
import java.util.StringTokenizer;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;
import pojo.Pets;

/**
 *
 * @author macarena jbenitez
 */
@Named(value = "billLineBeans")

@RequestScoped
@SessionScoped
public class BillLineBeans {

    private int id;
    private int idbill;
    private int quantity;
    private int taxes;
    private int discount;
    private double price;
    private String observations;
    private String prod;
    private int idprod;
    private String nameprod;
    private String pet;
    private int idpet;
    private String namepet;
    private List<BillLines> listalineasfac;
    private List<Pets> listamascotasPers;

    private BillLines blines = new BillLines();

    public BillLineBeans() throws SQLException {
        listalineasfac = LBillLine.getList();
    }

    public void BuscaMascotas(String IdPersona) throws SQLException {
        listamascotasPers = LPets.getPets(IdPersona);
    }

    public void onRowEdit(RowEditEvent event) throws SQLException {
        FacesMessage msg = new FacesMessage("Precio Editado");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", ((Products) event.getObject()).getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void AddLine() throws SQLException {

        for (BillLines line : listalineasfac) {
            LBillLine.addLinea(idbill, line.getIdprod(),
                    line.getQuantity(), line.getPrice(), line.getTaxes(), line.getDiscount(), line.getIdpet(), line.getObservations());
        }
        FacesMessage msg = new FacesMessage("Línea Insertada");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void DeleteLine(int idprod, int idpet) throws SQLException {

        for (BillLines line : listalineasfac) {
            if (line.getIdpet() == idpet && line.getIdprod() == idprod) {
                listalineasfac.remove(line);
            }
        }
        FacesMessage msg = new FacesMessage("Línea Eliminada");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void Addcart() {

        FacesContext fcontext = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage("Producto añadido.");

        StringTokenizer st = new StringTokenizer(this.prod, "-");
        this.idprod = Integer.parseInt(st.nextToken());
        this.nameprod = st.nextToken();

        StringTokenizer sp = new StringTokenizer(this.pet, "-");
        this.idpet = Integer.parseInt(sp.nextToken());
        this.namepet = sp.nextToken();

//        try {
//            Pets petf = (Pets) fcontext.getExternalContext().getSessionMap().get("mascotaFac");
//            this.idpet = petf.getIdpets();
//            this.namepet = petf.getNamepet();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        BillLines fact = new BillLines(this.idpet, this.namepet, this.idprod, this.nameprod, this.quantity, this.price, this.taxes, this.discount);
        this.listalineasfac.add(fact);
        fcontext.addMessage(null, message);
        System.out.println("Añadir carrito");
    }

    public void KeepSelection(String idpers) {

        FacesContext fcontext = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage("Mascota Fijada");
        Pets petk;
        try {

            petk = LPets.getPet(this.idpet);

            if ((petk.getIdpets()) != 0) {
                fcontext.getExternalContext().getSessionMap().put("mascotaFac", petk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
        System.out.println("Fijar Mascota");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdbill() {
        return idbill;
    }

    public void setIdbill(int idbill) {
        this.idbill = idbill;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTaxes() {
        return taxes;
    }

    public void setTaxes(int taxes) {
        this.taxes = taxes;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getProd() {
        return prod;
    }

    public void setProd(String prod) {
        this.prod = prod;
    }

    public int getIdprod() {
        return idprod;
    }

    public void setIdprod(int idprod) {
        this.idprod = idprod;
    }

    public String getNameprod() {
        return nameprod;
    }

    public void setNameprod(String nameprod) {
        this.nameprod = nameprod;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    public int getIdpet() {
        return idpet;
    }

    public void setIdpet(int idpet) {
        this.idpet = idpet;
    }

    public String getNamepet() {
        return namepet;
    }

    public void setNamepet(String namepet) {
        this.namepet = namepet;
    }

    public List<BillLines> getListalineasfac() {
        return listalineasfac;
    }

    public void setListalineasfac(List<BillLines> listalineasfac) {
        this.listalineasfac = listalineasfac;
    }

    public List<Pets> getListamascotasPers() {
        return listamascotasPers;
    }

    public void setListamascotasPers(List<Pets> listamascotasPers) {
        this.listamascotasPers = listamascotasPers;
    }

    public BillLines getBlines() {
        return blines;
    }

    public void setBlines(BillLines blines) {
        this.blines = blines;
    }
}
