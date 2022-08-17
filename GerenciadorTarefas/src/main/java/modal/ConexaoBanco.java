package modal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {
	
	public Connection conectaBD() throws SQLException {
		String jdbcURL="jdbc:postgresql://localhost:5432/Self_Project";
		String username = "postgres";
		String password = "1234";
		
		Connection connection = DriverManager.getConnection(jdbcURL, username, password);
			
		return connection;
	}

}