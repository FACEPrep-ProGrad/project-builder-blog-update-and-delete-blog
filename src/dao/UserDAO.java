package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;
import utility.ConnectionManager;

public class UserDAO implements UserDaoInterface {

	public int signUp(User user) {
		
	
		try
		{
			Connection connection = ConnectionManager.getConnection();
			int result = 0;
			String INSERT_USERS_SQL = "INSERT INTO USERS(email, password)VALUES(?,?)";

			// Step 2:Create a statement using connection object
			PreparedStatement st = connection.prepareStatement(INSERT_USERS_SQL);
			st.setString(1,user.getEmail());
			st.setString(2,user.getPassword());
			System.out.println(st);
			// Step 3: Execute the query or update query
			result = st.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		return 0;
	}
	
	public boolean loginUser(User user) {
		
		try{
			Connection connection = ConnectionManager.getConnection();
			boolean status = false;
				// Step 2:Create a statement using connection object
		PreparedStatement st = connection.prepareStatement("select * from users where email = ? and password = ? ");
		
			st.setString(1, user.getEmail());
			st.setString(2, user.getPassword());

			System.out.println(st);
			ResultSet rs = st.executeQuery();
			status = rs.next();

		} catch (Exception e) {
			// process sql exception
			System.out.println(e);
		}
		return false;
	}

}
