package de.hdm.subscriptionManager.client;

import java.io.Serializable;

public class LoginInfo implements Serializable {


    private static final long serialVersionUID = 1L;

    private boolean loggedIn = false;
    
    private String loginUrl = "";
    
    private String logoutUrl = "";
    
    private String firstName = "";
    
    private String lastName = "";
    
    private String emailAddresss = "";
    

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddresss() {
        return emailAddresss;
    }

    public void setEmailAddresss(String emailAddresss) {
        this.emailAddresss = emailAddresss;
    }
}
