package Server.anaptDiktua.Requests;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
@Path("/registerac")
public class ReceiveAndroidClients {

	String username;
	String password;
	String email;

	final static Map<String,ArrayList<String>> ACmap = new ConcurrentHashMap<String,ArrayList<String>>();
	int SA_counter = 0;

	public static Map<String,ArrayList<String>>  getACmap(){return ACmap;}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/plain")
	public Response nmapJobsREST(InputStream inData)
	{
		StringBuilder receiver = new StringBuilder();
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(inData));
			String line = null;
			while ((line = in.readLine()) != null)
			{
				receiver.append(line);
			}
		}
		catch (Exception e)
		{
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Results: " + receiver.toString());
		JSONObject jsonObject = new JSONObject(receiver.toString());



	//	ImportUpdateDB dbObj = new ImportUpdateDB();

		username =  jsonObject.getString("name");
		if (username)
			BroadcasterToClient.SendToAndroidClient(username,username);
		password = jsonObject.getString("mac_address");
		email = jsonObject.getString("ip");

		ArrayList<String> list = new ArrayList<String>();
		list.add(username);
		list.add(password);
		list.add(email);

		ACmap.put(username, list);


	//	dbObj.importSA(name,mac_address, ip, nmap_version, hash, os);



        return Response.status(200).entity("").build();
	}

}
