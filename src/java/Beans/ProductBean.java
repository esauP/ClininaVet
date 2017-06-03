/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Controller.LProducts;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import pojo.Products;

/**
 *
 * @author macarena jbenitez
 */
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
    
    
    public ProductBean() throws SQLException{
        LProducts lp = new LProducts();
        listproducts = lp.getProducts();
        listamaestra = lp.getListProducts();
    }
    
    public List listar() throws SQLException {
        LProducts lp = new LProducts();
        return lp.getProducts();
    }
    
    public void addProduct() throws SQLException {
        LProducts lp = new LProducts();
        lp.addProduct(prod.getName(), prod.getPrice(), prod.getTaxes());
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
