/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Controller.Ldate;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import pojo.Dates;

/**
 *
 * @author esaup
 */
@Named(value = "datesBean")
@RequestScoped
public class DatesBean {

    private int id;
    private String date;
    private Date hour;
    private String observations;
    private String nameper;
    private String namepet;
    private List<Dates> listacitas;

    public DatesBean() {
        this.listacitas = Ldate.getDatesDay();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Date getHour() {
        return hour;
    }

    public void setHour(Date hour) {
        this.hour = hour;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getNameper() {
        return nameper;
    }

    public void setNameper(String nameper) {
        this.nameper = nameper;
    }

    public String getNamepet() {
        return namepet;
    }

    public void setNamepet(String namepet) {
        this.namepet = namepet;
    }

    public List<Dates> getListacitas() {
        return listacitas;
    }

    public void setListacitas(List<Dates> listacitas) {
        this.listacitas = listacitas;
    }

}
