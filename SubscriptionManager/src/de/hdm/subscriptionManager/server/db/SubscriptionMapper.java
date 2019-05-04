package de.hdm.subscriptionManager.server.db;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection;

import de.hdm.subscriptionManager.shared.bo.Subscription;
import de.hdm.subscriptionManager.shared.bo.SubscriptionGroup;

public class SubscriptionMapper {

    
    private static SubscriptionMapper subscriptionMapper = null;
    
    protected SubscriptionMapper() {
    }
    
    public static SubscriptionMapper subscriptionMapper() {
	if(subscriptionMapper == null) {
	    subscriptionMapper = new SubscriptionMapper();
	}
	return subscriptionMapper;
    }
    
    
    /*
     * Methode zum Anlegen einer Subscription in der Datenbank
     */
    public Subscription createSubscription(Subscription subscription) {
	
	Connection con = DBConnection.connection();
	
	int id = 0;
	
	try {
	    Statement stmt = con.createStatement();
	    
	    ResultSet rs = stmt.executeQuery("SELECT MAX(subscriptionid) AS maxid FROM subscription");
	    
	    if(rs.next()) {
		subscription.setId(rs.getInt("maxid") + 1);
		id = subscription.getId();
		
			PreparedStatement stmt1 = con.prepareStatement(
				"INSERT INTO subscription(subscriptionid, name, price, note, startdate, cancellationrelevance"
				+ "userid) VALUES(?,?,?,?,?,?)",
				
				Statement.RETURN_GENERATED_KEYS);
				stmt1.setInt(1, subscription.getId());
				stmt1.setString(2, subscription.getName());
				stmt1.setFloat(3, subscription.getPrice());
				stmt1.setString(4, subscription.getNote());
				stmt1.setBoolean(5, subscription.getCancellationRelevance());
				stmt1.setInt(6, subscription.getUserID());
				
				System.out.println(stmt);
				stmt1.executeUpdate();
	    }
	}
	catch(SQLException e2) {
		e2.printStackTrace();
	}
	finally {	
		if (con!=null) 
			try {
				con.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
	return subscription;
}
    
    
    /*
     * Methode zum Aktualisieren einer Subscription
     */
    public Subscription updateSubscription(Subscription subscription) {
	
	String sql = "UPDATE subscription SET name=?, price=?, note=?, startdate=?, cancellationrelevance=? WHERE id=?";
	
	Connection con = DBConnection.connection();
	
	try {
	    PreparedStatement stmt = con.prepareStatement(sql);
	    
	    stmt.setString(1, subscription.getName());
	    stmt.setFloat(2, subscription.getPrice());
	    stmt.setString(3, subscription.getNote());
	    stmt.setDate(4, subscription.getStartMonth());
	    stmt.setBoolean(5, subscription.getCancellationRelevance());
	    stmt.setInt(6, subscription.getId());
	    
	    stmt.executeUpdate();
	    
	    System.out.println("Update complete");
	}
	catch(SQLException e2) {
	    e2.printStackTrace();
	}
	finally {	
		if (con!=null) 
			try {
				con.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
	return subscription;
    }
    
    
    /*
     * Methode zum Löschen einer Subscription
     */
    public void deleteSubscription(Subscription subscription) {
	
	Connection con = DBConnection.connection();
	
	try {
	    PreparedStatement stmt = con.prepareStatement("DELETE FROM subscription WHERE subscriptionid=?");
	    
	    stmt.setInt(1, subscription.getId());
	    stmt.executeUpdate();
	}
	catch(SQLException e2) {
	    e2.printStackTrace();
	}
	finally {	
	if (con!=null) 
		try {
			con.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
    
}
