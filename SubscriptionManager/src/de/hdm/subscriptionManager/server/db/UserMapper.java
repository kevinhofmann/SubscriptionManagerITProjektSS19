package de.hdm.subscriptionManager.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.subscriptionManager.shared.bo.User;

public class UserMapper {

    private static UserMapper userMapper = null;
    
    protected UserMapper() {
    };
    
    public static UserMapper userMapper() {
	if(userMapper == null) {
	    userMapper = new UserMapper();
	}
	return userMapper;
    }
    
    public User createUser(User user) {
	
	Connection con = DBConnection.connection();
	
	int id = 0;
	
	try {
	    Statement stmt = con.createStatement();
	    
	    ResultSet rs = stmt.executeQuery("SELECT MAX(userid) AS maxid FROM user");
	    
	    if(rs.next()) {
		user.setId(rs.getInt("maxid") + 1);
		id = user.getId();
		
			PreparedStatement stmt1 = con.prepareStatement(
				"INSERT INTO user(userid, firstname, lastname, email) VALUES(?,?,?,?)",
				
				Statement.RETURN_GENERATED_KEYS);
				stmt1.setInt(1, user.getId());
				stmt1.setString(2, user.getFirstName());
				stmt1.setString(3, user.getLastName());
				stmt1.setString(4, user.getMail());
				
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
	return user;

    }
}
