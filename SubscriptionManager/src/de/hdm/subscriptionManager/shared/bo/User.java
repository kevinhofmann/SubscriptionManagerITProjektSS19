package de.hdm.subscriptionManager.shared.bo;

public class User extends BusinessObject {
    
    private static final long serialVersionUID = 1L;
    
    private String firstName = "";
    
    private String lastName = "";
    
    private String mail = "";
    
    private int userID = 1;

    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
    
    

}
