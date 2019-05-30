package de.hdm.subscriptionManager.server.db;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.hdm.subscriptionManager.shared.bo.SubscriptionSubscriptionGroup;

public class SubscriptionSubscriptionGroupMapper {

    private static SubscriptionSubscriptionGroupMapper subscriptionSubscriptionGroupMapper = null;

    protected SubscriptionSubscriptionGroupMapper() {
    }

    public static SubscriptionSubscriptionGroupMapper subscriptionSubscriptionGroupMapper() {
	if(subscriptionSubscriptionGroupMapper == null) {
	    subscriptionSubscriptionGroupMapper = new SubscriptionSubscriptionGroupMapper();
	}
	return subscriptionSubscriptionGroupMapper;
    }

    public SubscriptionSubscriptionGroup createSubscriptionSubscriptionGroup(SubscriptionSubscriptionGroup subSubGroup) {

	Connection con = DBConnection.connection();

	try {
	    Statement stmt = con.createStatement();

	    PreparedStatement stmt1 = con.prepareStatement("INSERT INTO subscriptionmapping(userid, subscriptionid, groupid) "
		    + "VALUES(?,?,?)",

		    Statement.RETURN_GENERATED_KEYS);
	    stmt1.setInt(1,subSubGroup.getUserID());
	    stmt1.setInt(2, subSubGroup.getSubscriptionID());
	    stmt1.setInt(3, subSubGroup.getSubscriptionGroupID());

	    stmt1.executeUpdate();
	    System.out.println("Subscription added to Group");
	}
	catch(SQLException e2) {
	    e2.printStackTrace();
	}
	//	finally {
	//	    if(con!=null) {
	//		try {
	//		    con.close();
	//		}
	//		catch(SQLException e ) {
	//		    e.printStackTrace();
	//		}
	//	    }
	//	}
	return subSubGroup;
    }
    
    public void removeSubscriptionSubscriptionGroup(SubscriptionSubscriptionGroup subSubGroup) {
	
	Connection con = DBConnection.connection();
	
	try {
	    
	    PreparedStatement stmt = con.prepareStatement("DELETE FROM subscriptionmapping WHERE subscriptionid=? AND groupid=?");
	
	    stmt.setInt(1, subSubGroup.getSubscriptionID());
	    stmt.setInt(2, subSubGroup.getSubscriptionGroupID());
	    
	    stmt.executeUpdate();
	    
	    System.out.println("Subscription removed from Group");
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
}
