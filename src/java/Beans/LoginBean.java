
package Beans;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import pojo.Person;
import Model.Login;
import javax.faces.bean.ManagedBean;

//Anotaciones para que el xhtml pueda operar con ella
@Named(value = "loginBean")
@ManagedBean
public class LoginBean implements Serializable {

    private String username;

    private String password;

    private int role;

    public LoginBean() {
    }

    /**
     * Metodo para acceder al servicio
     *
     * @param event
     */
    public void logear(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext fcontext = FacesContext.getCurrentInstance();

        FacesMessage message = null;
        boolean loggedIn = false;
        Person pers = new Person();
        try {
            pers = Login.getConectado(username, password);

            if (pers == null) {
                fcontext.getExternalContext().redirect("error.html");
                message = new FacesMessage("Error en la autentificación");

            } else {
                if (pers.getIdperson().equals(username) && pers.getPassword().equals(password)) {
                    fcontext.getExternalContext().getSessionMap().put("usuario", pers);
                    message = new FacesMessage("Exito en la autentificación");
                    fcontext.getExternalContext().redirect("faces/adminHome.xhtml");
                } else {
                    fcontext.getExternalContext().redirect("error.html");
                    message = new FacesMessage("Error en la autentificación");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("Logeado", loggedIn);
    }

    /**
     * Método para controlar la acción de deslogarse
     * @param event 
     */
    public void desloguear(ActionEvent event) {
        FacesContext fcontext = FacesContext.getCurrentInstance();
        Person pers = new Person();
        try {
            pers = null;
            fcontext.getExternalContext().getSessionMap().put("usuario", pers);
            fcontext.getExternalContext().redirect("faces/index.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Método para validar la sesión e impedir accesos no autorizados
     */
    public void validarSesionAdminHome() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Person pers = (Person) context.getExternalContext().getSessionMap().get("usuario");

            if (pers == null) {
                context.getExternalContext().redirect("error.html");
            }
            if (pers.getRole() != 1) {
                if (pers.getRole() == 2) {
                    context.getExternalContext().redirect("nopermission.html");
                }
                if (pers.getRole() == 3) {
                    context.getExternalContext().redirect("nopermission.hmtl");
                }
                if (pers.getRole() == 4) {
                    context.getExternalContext().redirect("nopermission.html");
                }
            }
        } catch (Exception e) {

        }
    }
    /**
     * Método para validar la sesión de facturas
     */
    public void validarSesionBills() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Person pers = (Person) context.getExternalContext().getSessionMap().get("usuario");
            if (pers == null) {
                context.getExternalContext().redirect("error.html");
            }
            if (pers.getRole() != 1) {
                if (pers.getRole() == 2) {
                    context.getExternalContext().redirect("");
                }
                if (pers.getRole() == 3) {
                    context.getExternalContext().redirect("");
                }
                if (pers.getRole() == 4) {
                    context.getExternalContext().redirect("nopermission.html");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Método custome para valdiar la sesión de mascotas
     */
    public void validarSesionPets() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Person pers = (Person) context.getExternalContext().getSessionMap().get("usuario");
            if (pers == null) {
                context.getExternalContext().redirect("error.html");
            }
            if (pers.getRole() != 1) {
                if (pers.getRole() == 2) {
                    context.getExternalContext().redirect("");
                }
                if (pers.getRole() == 3) {
                    context.getExternalContext().redirect("");
                }
                if (pers.getRole() == 4) {
                    context.getExternalContext().redirect("nopermission.html");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método custome para valdiar la sesión de consultas
     */
    public void validarSesionConsultation() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Person pers = (Person) context.getExternalContext().getSessionMap().get("usuario");
            if (pers == null) {
                context.getExternalContext().redirect("error.html");
            }
            if (pers.getRole() != 1) {
                if (pers.getRole() == 3) {
                    context.getExternalContext().redirect("nopermission.html");
                }
                if (pers.getRole() == 4) {
                    context.getExternalContext().redirect("nopermission.html");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Método custome para valdiar la sesión de citas
     */
    public void validarSesionDates() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Person pers = (Person) context.getExternalContext().getSessionMap().get("usuario");
            if (pers == null) {
                context.getExternalContext().redirect("error.html");
            }
            if (pers.getRole() != 1) {
                if (pers.getRole() == 2) {
                    context.getExternalContext().redirect("");
                }
                if (pers.getRole() == 3) {
                    context.getExternalContext().redirect("");
                }
                if (pers.getRole() == 4) {
                    context.getExternalContext().redirect("nopermission.html");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

}
