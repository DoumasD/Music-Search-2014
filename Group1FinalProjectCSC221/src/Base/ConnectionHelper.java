package Base;
import java.sql.Connection;
import java.sql.DriverManager;




public class ConnectionHelper {

	static Connection  connection=null;
	
	public static Connection getConnection(){
		
		if(connection==null){
		try{
		// this will load the MySQL driver, each DB has its own driver
	      Class.forName("com.mysql.jdbc.Driver");
	      // setup the connection with the DB.
	      connection = DriverManager.getConnection("jdbc:mysql://localhost/music?user=root&password=musicsearch");
		}catch(Exception e){
			e.printStackTrace();
		}
		}
		return connection;
	}
}
