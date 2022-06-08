/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcg.jsfOCMBeans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import models.UserModel;
import org.apache.log4j.Logger;
/**
 *
 * @author Ahmed Faisal
 */
@ManagedBean(name="LoginBean")
@SessionScoped
public class LoginBean
        implements Serializable {

    private String username;
    private String usernameWelcome;
    private String password;
    private UserModel user;
    private int activeUser;
    private String errorMessages = "";
    public static final Logger logger = Logger.getLogger(LoginBean.class);

    public String login() {
        
        String forwardPage = "login.xhtml";
        String chargingPage = "charging.xhtml";
        String mediationPage = "mediation.xhtml";
        if (!username.equals("") && !password.equals("")) {
           
            if (username.equals("charging") && password.equals("chargingpass")) {
                System.out.println(username);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("LoggedUser", username);
                usernameWelcome = "Charging";
                logger.info("New Successful login with account: "+username);
                return chargingPage + "?faces-redirect=true";
            } else if (username.equals("mediation") && password.equals("masterpass")) { 
                System.out.println(username);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("LoggedUser", username);
                usernameWelcome = "Mediation";
                logger.info("New Successful login with account: "+username);
                return mediationPage + "?faces-redirect=true";
            
            } else {
                
                logger.debug("Wrong login trial with account: "+username);
                errorMessages = "Invalid Account !";
                return forwardPage + "?faces-redirect=true";

            }
        } else {
            username = "";
            password = "";
            errorMessages = "Please insert Username / Password !";
            return forwardPage + "?faces-redirect=true";
        }

    }

    public String logout() {
        String forwardPage = "";
        logger.debug("New Successful logout with account: "+username);
        username = "";
        password = "";
        errorMessages = "";
        try {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("LoggedUser", null);
            forwardPage = "login";
        } catch (Exception e) {
            this.errorMessages = e.getMessage();
        }
        return forwardPage;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getErrorMessages() {
        return this.errorMessages;
    }

    public void setErrorMessages(String errorMessages) {
        this.errorMessages = errorMessages;
    }

    public UserModel getUser() {
        return this.user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getUsernameWelcome() {
        return usernameWelcome;
    }

    public void setUsernameWelcome(String usernameWelcome) {
        this.usernameWelcome = usernameWelcome;
    }

    public int getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(int activeUser) {
        this.activeUser = activeUser;
    }

}
