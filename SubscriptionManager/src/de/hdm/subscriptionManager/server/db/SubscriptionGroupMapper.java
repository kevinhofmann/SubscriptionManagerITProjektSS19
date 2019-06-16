package de.hdm.subscriptionManager.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.subscriptionManager.shared.bo.Subscription;
import de.hdm.subscriptionManager.shared.bo.SubscriptionGroup;

public class SubscriptionGroupMapper {
    
    public static SubscriptionGroupMapper subGroupMapper = null;
    
    protected SubscriptionGroupMapper() {
    };
    
    public static SubscriptionGroupMapper subGroupMapper() {
	if(subGroupMapper == null ) {
	    subGroupMapper = new SubscriptionGroupMapper();
	}
	return subGroupMapper;
    }
    
    
    public SubscriptionGroup createSubscriptionGroup(SubscriptionGroup subGroup) {
	
	Connection con = DBConnection.connection();
	
	int id = 0;
	
	try {
	    Statement stmt = con.createStatement();
	    
	    ResultSet rs = stmt.executeQuery("SELECT MAX(groupid) AS maxgroupid FROM subscriptiongroup");
	    
	    if(rs.next()) {
		subGroup.setId(rs.getInt("maxgroupid") + 1);
		id = subGroup.getId();
	    
		PreparedStatement stmt1 = con.prepareStatement("INSERT INTO subscriptiongroup(groupid, name, userid) "
			+ "VALUES(?, ?, ?)",
			
		Statement.RETURN_GENERATED_KEYS);
		stmt1.setInt(1,  subGroup.getId());
		stmt1.setString(2, subGroup.getName());
		stmt1.setInt(3, subGroup.getUserID());
		
		System.out.println(stmt);
		stmt1.executeUpdate();
	    }
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
	return subGroup;
    }

    
    /*
     * Methode zum Aktualisieren einer SubscriptionGroup
     */
    public SubscriptionGroup updateSubscriptionGroup(SubscriptionGroup subscriptionGroup) {
	
	String sql = "UPDATE subscriptiongroup SET name=? WHERE groupid=?";
	
	Connection con = DBConnection.connection();
	
	try {
	    PreparedStatement stmt = con.prepareStatement(sql);
	    
	    stmt.setString(1, subscriptionGroup.getName());
	    stmt.setInt(2, subscriptionGroup.getId());
	    
	    stmt.executeUpdate();
	    
	    System.out.println("Update complete");
	}
	catch(SQLException e2) {
	    e2.printStackTrace();
	}
//	finally {	
//		if (con!=null) 
//			try {
//				con.close();
//			}
//			catch(SQLException e) {
//				e.printStackTrace();
//			}
//		}
	return subscriptionGroup;
    }
    
    
    /*
     * Methode zum L�schen einer SubscriptionGroup
     */
    public void deleteSubscriptionGroup(SubscriptionGroup subscriptionGroup) {
	
	Connection con = DBConnection.connection();
	
	try {
	    PreparedStatement stmt = con.prepareStatement("DELETE FROM subscriptiongroup WHERE groupid=?");
	    
	    stmt.setInt(1, subscriptionGroup.getId());
	    stmt.executeUpdate();
	}
	catch(SQLException e2) {
	    e2.printStackTrace();
	}
//	finally {	
//		if (con!=null) 
//			try {
//				con.close();
//			}
//			catch(SQLException e) {
//				e.printStackTrace();
//			}
//		}
    }
    
    /*
     * Methode zum Anzeigen aller Subscription Groups. 
     */
    public ArrayList<SubscriptionGroup> findAll(int userId) {
		ArrayList<SubscriptionGroup> result = new ArrayList<>();

		Connection con = DBConnection.connection();
		try {

			PreparedStatement stmt = con.prepareStatement("SELECT * FROM subscriptiongroup WHERE userid=? ORDER BY groupid");

			stmt.setInt(1, userId);
			
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				SubscriptionGroup subgroup = new SubscriptionGroup();

				subgroup.setId(rs.getInt("groupid"));
				subgroup.setName(rs.getString("Name"));
				subgroup.setUserID(rs.getInt("userId"));

				result.add(subgroup);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}

}
