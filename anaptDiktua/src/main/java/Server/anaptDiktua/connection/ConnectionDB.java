package Server.anaptDiktua.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionDB {

	String URL = "jdbc:mysql://localhost:3306/mydb?useUnicode=yes&characterEncoding=UTF-8";
    String dbuser = "root";
    String dbpass = "134711Kk";
    Connection con = null;
    PreparedStatement state = null;
    ResultSet set = null;

	public ConnectionDB()
	{
		try
	    {
	        Class.forName("com.mysql.jdbc.Driver");

	        con = DriverManager.getConnection(URL, dbuser, dbpass);

	    }
	    catch(SQLException ex)
	    {
	    	ex.printStackTrace();
	    }
	    catch(ClassNotFoundException ex)
	    {
	    	ex.printStackTrace();
	    }
	}

	public PreparedStatement GetState()
	{
		return state;
	}

	public Connection GetCon()
	{
		return con;
	}

	public void Close_connection() throws SQLException
	{
		con.close();
	}

	public void Close_statement() throws SQLException
	{
		state.close();
	}

	public void Close_resultset() throws SQLException
	{
		set.close();
	}

}
