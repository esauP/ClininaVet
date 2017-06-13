
package Beans;

import Controller.LBill;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Named;
import pojo.Products;
import pojo.BillLines;
import Controller.LBillLine;
import Controller.LPets;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;
import pojo.Pets;

//Anotaciones para que el xhtml pueda operar con ella
@Named(value = "billLineBeans")
@RequestScoped
@SessionScoped
public class BillLineBeans {

    private int id;
    private int idbill;
    private int quantity = 1;
    private int taxes = 21;
    private int discount;
    private double price;
    private String observations;
    private String prod;
    private int idprod;
    private String nameprod;
    private String pet;
    private int idpet;
    private String namepet;
    private Double totpagar = 0.0;
    private List<BillLines> listalineasfac = new ArrayList<>();
    private List<Pets> listamascotasPers;

    private BillLines blines = new BillLines();

    public BillLineBeans() throws SQLException {
        FacesContext fcontext = FacesContext.getCurrentInstance();

        try {
            Pets petf = (Pets) fcontext.getExternalContext().getSessionMap().get("mascotaFactura");
            this.idpet = petf.getIdpets();
            this.namepet = petf.getNamepet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para carga un listado de mascotas asociados al id de una persona
     * @param IdPersona
     * @throws SQLException 
     */
    public void BuscaMascotas(String IdPersona) throws SQLException {
        listamascotasPers = LPets.getPets(IdPersona);
    }

    /**
     * Método de primefaces para la modificación de valores en la tabla
     * @param event
     * @throws SQLException 
     */
    public void onRowEdit(RowEditEvent event) throws SQLException {
        FacesMessage msg = new FacesMessage("Precio Editado");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Método para cancelar la edición de datos en la tabla
     * @param event 
     */
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", ((Products) event.getObject()).getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Método para eliminar una línea
     * @param idprod
     * @param idpet
     * @throws SQLException 
     */
    public void DeleteLine(int idprod, int idpet) throws SQLException {
        List<BillLines> listafac = new ArrayList<>();
        FacesContext fcontext = FacesContext.getCurrentInstance();

        listafac = (List<BillLines>) fcontext.getExternalContext().getSessionMap().get("listafac");
        if (listafac != null) {
            this.listalineasfac = listafac;
        }

        for (BillLines line : this.listalineasfac) {
            if (line.getIdpet() == idpet && line.getIdprod() == idprod) {
                listalineasfac.remove(line);
            }
        }
        FacesMessage msg = new FacesMessage("Línea Eliminada");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    /**
     * Método para añadir productos al carrito
     */
    public void Addcart() {

        FacesContext fcontext = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage("Producto añadido.");
        List<BillLines> listafac = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(this.prod, "-");
        this.idprod = Integer.parseInt(st.nextToken());
        this.nameprod = st.nextToken();

        try {
            Pets petf = (Pets) fcontext.getExternalContext().getSessionMap().get("mascotaFactura");
            this.idpet = petf.getIdpets();
            this.namepet = petf.getNamepet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        listafac = (List<BillLines>) fcontext.getExternalContext().getSessionMap().get("listafac");
        if (listafac != null) {
            this.listalineasfac = listafac;
        }
        BillLines fact = new BillLines(this.idpet, this.namepet, this.idprod, this.nameprod, this.quantity, this.price, this.taxes, this.discount);
        this.listalineasfac.add(fact);

        fcontext.getExternalContext().getSessionMap().put("listafac", this.listalineasfac);

        if (this.listalineasfac != null) {
            for (BillLines line : this.listalineasfac) {
                double precio = line.getPrice();
                int tax = line.getTaxes();
                float impuesto;
                impuesto = (float) tax / 100;
                double pretax = precio * impuesto;
                double precto = precio + pretax;
                DecimalFormat df = new DecimalFormat("#.00");
                String preciotot = df.format(precto);

                this.totpagar += Double.parseDouble(preciotot.replace(",", "."));
            }
        }

        fcontext.addMessage(null, message);
    }
    /**
     * Método para finalizar la grabación de líneas y crear finalmente la factura
     * @param idbillfac
     * @param idperfac
     * @param date_billfac
     * @param observationsfac
     * @throws SQLException 
     */
    public void Facturar(int idbillfac, String idperfac, String date_billfac, String observationsfac) throws SQLException {
        FacesContext fcontext = FacesContext.getCurrentInstance();
        List<BillLines> listafac = new ArrayList<>();
        listafac = (List<BillLines>) fcontext.getExternalContext().getSessionMap().get("listafac");

        Boolean facAdd = false;
        FacesMessage msg;
        facAdd = LBill.addBill(idperfac, date_billfac, observationsfac);
        if (listafac != null) {

            if (facAdd) {
                for (BillLines line : listafac) {
                    LBillLine.addLinea(idbillfac, line.getIdprod(),
                            line.getQuantity(), line.getPrice(), line.getTaxes(), line.getDiscount(), line.getIdpet(), line.getObservations());
                }
                msg = new FacesMessage("Línea Insertada");
            } else {
                msg = new FacesMessage("Ha ocurrido un error");
            }
        } else {
            msg = new FacesMessage("Ha ocurrido un error");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    /**
     * Método para cancelar la operación
     * @throws IOException 
     */
    public void Cancelar() throws IOException {
        List<BillLines> listafac = new ArrayList<>();
        FacesContext fcontext = FacesContext.getCurrentInstance();

        listafac = (List<BillLines>) fcontext.getExternalContext().getSessionMap().get("listafac");
        this.listalineasfac = listafac;
        this.listalineasfac.clear();
        fcontext.getExternalContext().getSessionMap().put("listafac", this.listalineasfac);
        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/pets.xhtml");

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

    public Double getTotpagar() {
        return totpagar;
    }

    public void setTotpagar(Double totpagar) {
        this.totpagar = totpagar;
    }

}
