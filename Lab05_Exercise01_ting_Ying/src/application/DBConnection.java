package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
private Connection _connection=null;
//public Connection connection;

//public Connection getConnection()
//{
//	return _connection;
//}
	
	
	public Connection getDBConnection()
	{
		try{
			
			Class.forName("oracle.jdbc.OracleDriver");
			_connection = DriverManager.getConnection("jdbc:oracle:thin:@oracle1.centennialcollege.ca:1521:SQLD", "comp214_f19_zor_9","password");
			System.out.println("Successfull connection");
			
			
			
		}//end of try
		catch(ClassNotFoundException e) {
	        System.err.println("ERROR! oracle.jdbc.OracleDriver not found.");
			}
			catch(SQLException e) {

				System.err.println("ERROR! Couldn't connect to the DB: " + e.getMessage());
			}

			return this._connection;



	}

}
