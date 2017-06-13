package Beans;

import Controller.LProducts;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;
import pojo.Products;

//Anotaciones para que el xhtml pueda operar con ella
@Named(value = "productBean")
@ManagedBean
@RequestScoped

public class ProductBean {

    private List<Products> listproducts;
    private List<String> listamaestra;
    private int idproducts;
    private String name;
    private double price;
    private int taxes;
    private Products prod = new Products();

    public ProductBean() throws SQLException {
        LProducts lp = new LProducts();
        listproducts = lp.getProducts();
        listamaestra = lp.getListProducts();
    }

    /**
     * Método para lista productos
     * @return
     * @throws SQLException 
     */
    public List listar() throws SQLException {
        LProducts lp = new LProducts();
        return lp.getProducts();
    }

    /**
     * Método para añadir un nuevo producto
     * @throws SQLException
     * @throws IOException 
     */
    public void addProduct() throws SQLException, IOException {
        LProducts lp = new LProducts();
        lp.addProduct(prod.getName(), prod.getPrice(), prod.getTaxes());
        FacesContext.getCurrentInstance().getExternalContext().redirect("faces/administration.xhtml");

    }

    /**
     * 
     * @param event
     * @throws SQLException 
     */
    public void onRowEdit(RowEditEvent event) throws SQLException {
        LProducts lv = new LProducts();
        Products producto = (Products) event.getObject();
        FacesMessage msg = new FacesMessage("Producto Editado", String.valueOf(producto.getIdproducts()));
        lv.updateProduct(producto.getIdproducts(), producto.getName(), producto.getPrice(), producto.getTaxes());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", String.valueOf(((Products) event.getObject()).getIdproducts())); //Se ha casteado el id que estaba en integer
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deleteProduct(int idprod) throws SQLException {
        LProducts lp = new LProducts();
        lp.deleteProduct(idprod);
    }

    public List<Products> getListproducts() {
        return listproducts;
    }

    public void setListproducts(List<Products> listproducts) {
        this.listproducts = listproducts;
    }

    public List<String> getListamaestra() {
        return listamaestra;
    }

    public void setListamaestra(List<String> listamaestra) {
        this.listamaestra = listamaestra;
    }

    public Products getProd() {
        return prod;
    }

    public void setProd(Products prod) {
        this.prod = prod;
    }

    public int getIdproducts() {
        return idproducts;
    }

    public void setIdproducts(int idproducts) {
        this.idproducts = idproducts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTaxes() {
        return taxes;
    }

    public void setTaxes(int taxes) {
        this.taxes = taxes;
    }

    public List<String> AutocompletarNameProd(String text) {
        // Assumed search using the startsWith
        List<String> queried = new ArrayList<>();
        for (int i = 0; i < this.listamaestra.size(); i++) {
            String nameProd = this.listamaestra.get(i);
            if (nameProd.toLowerCase().startsWith(text) || nameProd.startsWith(text)) {
                queried.add(nameProd);
            }
        }
        return queried;
    }

}
