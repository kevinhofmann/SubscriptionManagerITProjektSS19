package de.hdm.subscriptionManager.server.db;

import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.Date;

//import de.hdm.partnerboerse.server.db.DBConnection;
//import de.hdm.partnerboerse.shared.bo.Info;
import de.hdm.subscriptionManager.shared.bo.Subscription;
import de.hdm.subscriptionManager.shared.bo.SubscriptionGroup;

public class SubscriptionMapper {

    
    private static SubscriptionMapper subscriptionMapper = null;
    
    protected SubscriptionMapper() {
    };
    
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
	    
	    ResultSet rs = stmt.executeQuery("SELECT MAX(SubscriptionID) AS maxid FROM subscription");
	    
	    if(rs.next()) {
		subscription.setId(rs.getInt("maxid") + 1);
		id = subscription.getId();
		
			PreparedStatement stmt1 = con.prepareStatement(
				"INSERT INTO subscription(SubscriptionID, name, price, note, startdate, cancellationrelevance,"
				+ "userid) VALUES(?,?,?,?,?,?,?)",
				
				Statement.RETURN_GENERATED_KEYS);
				stmt1.setInt(1, id);
				stmt1.setString(2, subscription.getName());
				stmt1.setFloat(3, subscription.getPrice());
				stmt1.setString(4, subscription.getNote());
				stmt1.setDate(5, (Date) subscription.getStartDate());
				stmt1.setBoolean(6, subscription.getCancellationRelevance());
				stmt1.setInt(7, 1);
				
				System.out.println(stmt);
				stmt1.executeUpdate();
	    }
	}
	catch(SQLException e2) {
		e2.printStackTrace();
	}
	return subscription;
}
    
    
    /*
     * Methode zum Aktualisieren einer Subscription
     */
    public Subscription updateSubscription(Subscription subscription) {
	
	String sql = "UPDATE subscription SET name=?, price=?, note=?, startdate=?, cancellationrelevance=? WHERE subscriptionid=?";
	
	Connection con = DBConnection.connection();
	
	try {
	    PreparedStatement stmt = con.prepareStatement(sql);
	    
	    stmt.setString(1, subscription.getName());
	    stmt.setFloat(2, subscription.getPrice());
	    stmt.setString(3, subscription.getNote());
	    stmt.setDate(4, (Date) subscription.getStartDate());
	    stmt.setBoolean(5, subscription.getCancellationRelevance());
	    stmt.setInt(6, subscription.getId());
	    
	    stmt.executeUpdate();
	    
	    System.out.println("Subscription Update complete");
	}
	catch(SQLException e2) {
	    e2.printStackTrace();
//	}
//	finally {	
//		if (con!=null) 
//			try {
//				con.close();
//			}
//			catch(SQLException e) {
//				e.printStackTrace();
//			}
		}
	return subscription;
    }
    
    
    /*
     * Methode zum L�schen einer Subscription
     */
    public void deleteSubscription(Subscription subscription) {
	
	Connection con = DBConnection.connection();
	
	try {
	    PreparedStatement stmt = con.prepareStatement("DELETE FROM subscription WHERE subscriptionid=?");
	    
	    stmt.setInt(1, subscription.getId());
	    stmt.executeUpdate();
	    
	    System.out.println("Subscription deleted");
	}
	catch(SQLException e2) {
	    e2.printStackTrace();
	}
//	finally {	
//	if (con!=null) 
//		try {
//			con.close();
//		}
//		catch(SQLException e) {
//			e.printStackTrace();
//		}
//	}
    }
    
    

    public ArrayList<Subscription> getAllSubscriptions(int userId) {
	
	Connection con = DBConnection.connection();
	
	ArrayList<Subscription> subscriptionArrayList = new ArrayList<Subscription>();
	
	try {
	    PreparedStatement stmt = con.prepareStatement("SELECT * FROM subscription WHERE userid= ?");
	
	    stmt.setInt(1, userId);
	    
	    ResultSet rs = stmt.executeQuery();
	    
	    /*
	     * F�r jeden Tupel eine neues Subscription Objekt erstellen und
	     * in die ArrayList packen
	     */
	    while(rs.next()) {
		Subscription sub = new Subscription();
		
		sub.setId(rs.getInt("subscriptionid"));
		sub.setName(rs.getString("name"));
		sub.setPrice(rs.getFloat("price"));
		sub.setNote(rs.getString("note"));
		sub.setStartDate(rs.getDate("startdate"));
		sub.setCancellationRelevance(rs.getBoolean("cancellationrelevance"));
		sub.setUserID(userId);
		
		subscriptionArrayList.add(sub);
	    }
	}
	catch(SQLException e2) {
		e2.printStackTrace();
	}
	return subscriptionArrayList;
    }


    /*
     * Methode zum Anzeigen aller Subscription. 
     */
    public ArrayList<Subscription> findAll() {
		ArrayList<Subscription> result = new ArrayList<>();

		Connection con = DBConnection.connection();
		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM info ORDER BY id");

			while (rs.next()) {
				Subscription s = new Subscription();

				s.setId(rs.getInt("id"));
				s.setPrice(rs.getInt("price"));
				s.setNote(rs.getString("note"));
				s.setStartDate(rs.getDate("startDate"));
				s.setCancellationRelevance(rs.getBoolean("cancellationRelevance"));
				s.setUserID(rs.getInt("userId"));

				result.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
    
    public ArrayList<Subscription> getAllSubscriptionsWithinGroup(int groupID) {
	
	ArrayList<Subscription> result = new ArrayList<>();
	
	Connection con = DBConnection.connection();
	try {
	    
	    PreparedStatement stmt = con.prepareStatement("SELECT `subscription`.`SubscriptionID`, `subscription`.`Name`, `subscription`.`Price`, `subscription`.`Note`, `subscription`.`StartDate`, `subscription`.`CancellationRelevance`, `subscription`.`UserID`, `subscriptiongroup`.`groupID`, `cancellation`.`expirationdate` "
		    					+ "FROM `subscriptiongroup` INNER JOIN `subscriptionmapping` "
		    					+ "ON `subscriptionmapping`.`groupid` = `subscriptiongroup`.`groupid` INNER JOIN `subscription` "
		    					+ "ON `subscriptionmapping`.`subscriptionid` = `subscription`.`subscriptionid` LEFT JOIN `cancellation` "
		    					+ "ON `cancellation`.`subscriptionid` = `subscription`.`subscriptionid` WHERE `subscriptiongroup`.`groupid` = ? ORDER BY name ASC ");
	    
	    stmt.setInt(1, groupID);
	    
	    ResultSet rs = stmt.executeQuery();
	    
	    while(rs.next()) {
		Subscription sub = new Subscription();
		sub.setId(rs.getInt("subscriptionid"));
		sub.setName(rs.getString("name"));
		sub.setPrice(rs.getInt("price"));
		sub.setNote(rs.getString("note"));
		sub.setStartDate(rs.getDate("startDate"));
		sub.setCancellationRelevance(rs.getBoolean("cancellationRelevance"));
		sub.setUserID(rs.getInt("userId"));
		sub.setEndDate(rs.getDate("expirationDate"));
		
		result.add(sub);
	    }
	    System.out.println("Catched all Subs within Group");
	
	} catch(SQLException e) {
	    e.printStackTrace();
	}
	return result;
    }
    
    public Subscription getSubscriptionBySubscriptionID(int subscriptionID) {
	
	Subscription sub = new Subscription();
	    
	Connection con = DBConnection.connection();
	
	try {
	    
	    PreparedStatement stmt = con.prepareStatement("SELECT * FROM subscription WHERE subscriptionid = ? ");
	
	    stmt.setInt(1, subscriptionID);
	    
	    ResultSet rs = stmt.executeQuery();
	    
	    while(rs.next()) {
		Subscription subscription = new Subscription();
		subscription.setId(rs.getInt("subscriptionid"));
		subscription.setName(rs.getString("name"));
		subscription.setPrice(rs.getFloat("price"));
		subscription.setNote(rs.getString("note"));
		subscription.setStartDate(rs.getDate("startdate"));
		subscription.setCancellationRelevance(rs.getBoolean("cancellationrelevance"));
		subscription.setUserID(rs.getInt("userid"));
		
		sub = subscription;
		
		System.out.println("Subscription catched successfully");
	    }

	} catch(SQLException e) {
	    e.printStackTrace();
	}
	return sub;
    }
}