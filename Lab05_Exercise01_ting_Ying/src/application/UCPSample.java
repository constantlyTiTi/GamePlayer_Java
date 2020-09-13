package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import oracle.ucp.jdbc.PoolDataSourceFactory;
import oracle.ucp.jdbc.PoolDataSource;

public class UCPSample {
	// The recommended format of a connection URL is the long format with the
	  // connection descriptor.
//	  final static String DB_URL= "jdbc:oracle:thin:@adb.ca-toronto-1.oraclecloud.com:1522/rbxfd1ce7llmhuv_comp228lab05_high.atp.oraclecloud.com";
	  // For ATP and ADW - use the TNS Alias name along with the TNS_ADMIN when using 18.3 JDBC driver
	   final static String DB_URL="jdbc:oracle:thin:@COMP228LAB05_MEDIUM?TNS_ADMIN=/Users/tingli/Documents/Wallet_COMP228LAB05/";
	  // In case of windows, use the following URL 
	  // final static String DB_URL="jdbc:oracle:thin:@wallet_dbname?TNS_ADMIN=C:\\Users\\test\\wallet_dbname";
	  final static String DB_USER = "admin";
	  final static String DB_PASSWORD = "32Mbirthday@1986";
	  final static String CONN_FACTORY_CLASS_NAME="oracle.jdbc.pool.OracleDataSource";

	  /*
	   * The sample demonstrates UCP as client side connection pool.
	   */
	  public static void main(String args[]) throws Exception {
	    // Get the PoolDataSource for UCP
	    PoolDataSource pds = PoolDataSourceFactory.getPoolDataSource();

	    // Set the connection factory first before all other properties
	    pds.setConnectionFactoryClassName(CONN_FACTORY_CLASS_NAME);
	    pds.setURL(DB_URL);
	    pds.setUser(DB_USER);
	    pds.setPassword(DB_PASSWORD);
	    pds.setConnectionPoolName("JDBC_UCP_POOL");

	    // Default is 0. Set the initial number of connections to be created
	    // when UCP is started.
	    pds.setInitialPoolSize(5);

	    // Default is 0. Set the minimum number of connections
	    // that is maintained by UCP at runtime.
	    pds.setMinPoolSize(5);

	    // Default is Integer.MAX_VALUE (2147483647). Set the maximum number of
	    // connections allowed on the connection pool.
	    pds.setMaxPoolSize(20);

	    // Default is 30secs. Set the frequency in seconds to enforce the timeout
	    // properties. Applies to inactiveConnectionTimeout(int secs),
	    // AbandonedConnectionTimeout(secs)& TimeToLiveConnectionTimeout(int secs).
	    // Range of valid values is 0 to Integer.MAX_VALUE. .
	    pds.setTimeoutCheckInterval(5);

	    // Default is 0. Set the maximum time, in seconds, that a
	    // connection remains available in the connection pool.
	    pds.setInactiveConnectionTimeout(10);

	    // Get the database connection from UCP.
	    try (Connection conn = pds.getConnection()) {
	      System.out.println("Available connections after checkout: "
	          + pds.getAvailableConnectionsCount());
	      System.out.println("Borrowed connections after checkout: "
	          + pds.getBorrowedConnectionsCount());
	      // Perform a database operation
//	      doSQLWork(conn);
//	      printEmployees(conn);
	      lab05_player(conn);
	    }
	    catch (SQLException e) {
	      System.out.println("UCPSample - " + "SQLException occurred : "
	          + e.getMessage());
	    }
	    System.out.println("Available connections after checkin: "
	        + pds.getAvailableConnectionsCount());
	    System.out.println("Borrowed connections after checkin: "
	        + pds.getBorrowedConnectionsCount());
	  }

	  /*
	   * Creates an EMP table and does an insert, update and select operations on
	   * the new table created.
	   */
	  public static void doSQLWork(Connection conn) {
	    try {
//	      conn.setAutoCommit(false);
	      // Prepare a statement to execute the SQL Queries.
	      Statement statement = conn.createStatement();
	      // Create table EMP
//	      statement.executeUpdate("create table EMP(EMPLOYEEID NUMBER,"
//	          + "EMPLOYEENAME VARCHAR2 (20))");
//	      System.out.println("New table EMP is created");
	      // Insert some records into the table EMP
	      statement.executeUpdate("insert into EMP values(1, 'Jennifer Jones')");
	      statement.executeUpdate("insert into EMP values(2, 'Alex Debouir')");
	      System.out.println("Two records are inserted.");

	      // Update a record on EMP table.
	      statement.executeUpdate("update EMP set EMPLOYEENAME='Alex Deborie'"
	          + " where EMPLOYEEID=2");
	      System.out.println("One record is updated.");
	      // Verify the table EMP
	      ResultSet resultSet = statement.executeQuery("select * from EMP");
	      System.out.println("\nNew table EMP contains:");
	      System.out.println("EMPLOYEEID" + " " + "EMPLOYEENAME");
	      System.out.println("--------------------------");
	      while (resultSet.next()) {
	        System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2));
	      }
	      System.out.println("\nSuccessfully tested a connection from UCP");
	    }
	    catch (SQLException e) {
	      System.out.println("UCPSample - "
	          + "doSQLWork()- SQLException occurred : " + e.getMessage());
	    }
	    finally {
	      // Clean-up after everything
//	      try (Statement statement = conn.createStatement()) {
//	        statement.execute("drop table EMP");
//	      }
//	      catch (SQLException e) {
//	        System.out.println("UCPSample - "
//	            + "doSQLWork()- SQLException occurred : " + e.getMessage());
//	      }
	    }
	  }

	  public static void lab05_player(Connection connection) throws SQLException {
		    // Statement and ResultSet are AutoCloseable and closed automatically. 
		    try (Statement statement = connection.createStatement()) { 
//		    	statement.executeUpdate("INSERT INTO LAB05_PLAYER values(2006,'JIM','GREEN','101 HAPPY AVE','ABC123','ON','6474748933'");
		      try (ResultSet resultSet = statement
		          .executeQuery("select * from LAB05_PLAYER")) {
		        System.out.println("FIRST_NAME" + "  " + "LAST_NAME");
		        System.out.println("---------------------");
		        while (resultSet.next())
		          System.out.println(resultSet.getString(1) + " "
		              + resultSet.getString(2) + " "+ resultSet.getString(3) + " ");       
		      }
		    }   
		  } 
		  public static void printEmployees(Connection connection) throws SQLException {
			    // Statement and ResultSet are AutoCloseable and closed automatically. 
			    try (Statement statement = connection.createStatement()) {      
			      try (ResultSet resultSet = statement
			          .executeQuery("select first_name, last_name from employees")) {
			        System.out.println("FIRST_NAME" + "  " + "LAST_NAME");
			        System.out.println("---------------------");
			        while (resultSet.next())
			          System.out.println(resultSet.getString(1) + " "
			              + resultSet.getString(2) + " ");       
			      }
			    }   
			  } 
}
