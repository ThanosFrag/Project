package Server.anaptDiktua.import_updateDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Server.anaptDiktua.connection.ConnectionDB;


public class ImportUpdateDB
{
	PreparedStatement state;
	ConnectionDB connection;
	java.util.Date date;
	long resultCount;
	long periodCount;
	
	public ImportUpdateDB()
	{
		try
        {

        	connection = new ConnectionDB();
            state = connection.GetState();

        }
        catch (Exception ex)
        {
        	ex.printStackTrace();
        }

	}

	public long askLastResultID()
	{
		long resultID = -1;  //error
		try
        {
			state = (connection.GetCon()).prepareStatement("SELECT count(*) AS num FROM mydb.Result");

	        ResultSet set = state.executeQuery();

	        if(set.next())
	        {
	        	resultID = set.getInt("num");
	        }

	        resultID++;

        }
		catch (Exception ex)
        {
        	ex.printStackTrace();
        }

		return resultID;
	}
	public long askLastPeriodID()
	{
		long resultID = -1;  //error
		try
        {
			state = (connection.GetCon()).prepareStatement("SELECT count(*) AS num FROM mydb.Periodic_Job");

	        ResultSet set = state.executeQuery();

	        if(set.next())
	        {
	        	resultID = set.getInt("num");
	        }

	        resultID++;

        }
		catch (Exception ex)
        {
        	ex.printStackTrace();
        }

		return resultID;
	}	
	public ResultSet getAllResults(){
		String query = "Select * From Result";
		ResultSet result = null;
		try {
			state = (connection.GetCon()).prepareStatement(query);
			result = state.executeQuery();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;		
	}
	
	public ResultSet getAllResults (String SA){
		ResultSet result = null;
		String query = "Select idResult,id_command,Address,Status,Hostname,Port,Uptime,Insert_time From Result where Software_Agent_idSoftware_Agent = ? " ;
		try {
			
			state = (connection.GetCon()).prepareStatement(query);
			state.setString(1,SA);
			result = state.executeQuery();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
		
	}
	
	public ResultSet getPeriodic(String Software_Agent_idSoftware_Agent){
		ResultSet result = null;
		String query = "Select * From Periodic_Job where Software_Agent_idSoftware_Agent = ? ";
		try {
			
			state = (connection.GetCon()).prepareStatement(query);
			state.setString(1,Software_Agent_idSoftware_Agent);
			result = state.executeQuery();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
		
	}
	
	public void importPeriodic(String Software_Agent_idSoftware_Agent,String job,int pediod ){
		periodCount = this.askLastPeriodID();
		try
        {
			
			state = (connection.GetCon()).prepareStatement("INSERT INTO mydb.Periodic_Job VALUES "
															+ "(?,?,?,?)");

	        state.setInt(1,(int) periodCount);
	        state.setString(2,Software_Agent_idSoftware_Agent);
	        if(!job.equals(" ") && job != null) 
	        	state.setString(3,job);
	        state.setInt(4, pediod);

	        state.executeUpdate();
	        connection.Close_connection();

        }
		catch (Exception ex)
        {
        	ex.printStackTrace();
        }
		
		
	}
	
	public int adminLogin(String Username,String password){
		try {
			state = (connection.GetCon()).prepareStatement("select * from Admin where Username = ? and Password = AES_ENCRYPT(?, SHA2('password',512))");	
			state.setString(1,Username);
			state.setString(2,password);
			int success = 0 ;
			ResultSet result;
		
			result = state.executeQuery();
			if (result.next() == true)	
				success =1;
			else
				success = 0 ;
			System.out.println(success);
			connection.Close_connection();	
			return success;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public void importResult(String command_id,String hashkey,String address,String status,String hostname,String port,String uptime)
	{
		resultCount = this.askLastResultID();
		try
        {
			System.out.println("***** Start Import Results " +resultCount+" Address : "+ address + " ******");
			state = (connection.GetCon()).prepareStatement("INSERT INTO mydb.Result VALUES "
															+ "(?,?,?,?,?,?,?,?,?)");

	        state.setInt(1,(int) resultCount);
	        state.setString(2,command_id);
	        if(!address.equals(" ") && address != null) state.setString(3,address);
	        else state.setString(3,null);
	        if(!status.equals(" ") && status != null) state.setString(4,status);
	        else state.setString(4,null);
	        if(!hostname.equals(" ") && hostname != null) state.setString(5,hostname);
	        else state.setString(5,null);
	        if(!port.equals(" ") && port != null) state.setString(6,port);
	        else state.setString(6,null);
	        if(!uptime.equals(" ") && uptime != null) state.setString(7,uptime);
	        else state.setString(7,null);
	        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd hh:mm");////////////////////////////////////////////////////////////////
        	date = new Date();
        	java.sql.Timestamp start_date_sql = new java.sql.Timestamp(date.getTime());
	        state.setTimestamp(8, start_date_sql);
	        if(!hashkey.equals(" ") && hashkey != null) state.setString(9,hashkey);
	        else state.setString(9,null);



	        state.executeUpdate();
	        connection.Close_connection();

        }
		catch (Exception ex)
        {
        	ex.printStackTrace();
        }

	}

	public void importSA(String name,String mac,String ip,String nmap_version,String hashKey,String os)
	{

		try
        {
			state = (connection.GetCon()).prepareStatement("SELECT * from mydb.Software_Agent where ? = idSoftware_Agent");	
			state.setString(1,hashKey);
			ResultSet result = state.executeQuery();
			if (result.next() == true){
				connection.Close_connection();
				return;
			}
			
			state = (connection.GetCon()).prepareStatement("INSERT INTO mydb.Software_Agent VALUES "
															+ "(?,?,?,?,?,?,?)");

        	state.setString(1,hashKey);
        	state.setString(2,name);
        	state.setString(3,ip);
        	state.setString(4,mac);
        	state.setString(5,os);
        	state.setString(6,nmap_version);
        	state.setInt(7,0);
	       
	        state.executeUpdate();
	        connection.Close_connection();

        }
		catch (Exception ex)
        {
        	ex.printStackTrace();
        }

	}

	public void changeClientStatus(String hashkey)
	{
		try
		{
			state = (connection.GetCon()).prepareStatement("UPDATE mydb.Software_Agent SET Status=? WHERE idSoftware_Agent=?");

			state.setInt(1,1);
			state.setString(2,hashkey);

			state.executeUpdate();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}



}