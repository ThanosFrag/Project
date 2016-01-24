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
@Path("/registersa")
public class ReceiveSoftwareAgents {

	String name;
	String ip;
	String os;
	String nmap;
	String mac;
	String hashKey;
	final static Map<String,ArrayList<String>> SAmap = new ConcurrentHashMap<String,ArrayList<String>>();
	int SA_counter = 0;

	public static Map<String,ArrayList<String>>  getSAmap(){return SAmap;}

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
		hashKey = "";
		name =  jsonObject.getString("name");
		mac = jsonObject.getString("mac_address");
		ip = jsonObject.getString("ip");
		os = jsonObject.getString("os");
		nmap = jsonObject.getString("nmap_version");
		hashKey = name + mac + ip + os + nmap;
		ArrayList<String> list = new ArrayList<String>();
		list.add(name);
		list.add(mac);
		list.add(ip);
		list.add(os);
		list.add(nmap);
		SAmap.put(hashKey, list);
		System.out.println(hashKey);

	//	dbObj.importSA(name,mac_address, ip, nmap_version, hash, os);



        return Response.status(200).entity("").build();
	}

}
