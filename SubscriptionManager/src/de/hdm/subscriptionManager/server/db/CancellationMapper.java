package de.hdm.subscriptionManager.server.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.subscriptionManager.shared.bo.Cancellation;

public class CancellationMapper {
    
    public static CancellationMapper cancellationMapper = null;
    
    protected CancellationMapper() {
    };
    
    public static CancellationMapper cancellationMapper() {
	if(cancellationMapper == null) {
	    cancellationMapper = new CancellationMapper();
	}
	return cancellationMapper;
    }

    
    public Cancellation createCancellation(Cancellation cancellation) {
	
	Connection con = DBConnection.connection();
	
	int id = 0;
	
	try {
	    Statement stmt = con.createStatement();
	    
	    ResultSet rs = stmt.executeQuery("SELECT MAX(cancellationid) AS maxcanid FROM cancellation");
	    
	    if(rs.next()) {
		cancellation.setId(rs.getInt("maxcanid") +1);
		id = cancellation.getId();
		
		PreparedStatement stmt1 = con.prepareStatement("INSERT INTO cancellation(cancellationid, ExpirationDate,"
			+ "cancellationdate, cancellationperiod, subscriptionid) VALUES(?,?,?,?,?)",
			
			Statement.RETURN_GENERATED_KEYS);
		stmt1.setInt(1, cancellation.getId());
		stmt1.setDate(2, cancellation.getExpirationDate());
		stmt1.setDate(3, cancellation.getCancellationDate());		
		stmt1.setInt(4, cancellation.getCancellationPeriod());
		stmt1.setInt(5, cancellation.getSubscriptionID());
		
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
	return cancellation;
    }
}
