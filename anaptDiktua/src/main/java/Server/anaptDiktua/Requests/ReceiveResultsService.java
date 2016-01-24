package Server.anaptDiktua.Requests;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import Server.anaptDiktua.import_updateDB.ImportUpdateDB;

//@Path("/")
@Path("/receiveresults")
public class ReceiveResultsService {


	public ReceiveResultsService() {
		// TODO Auto-generated constructor stub
	}


	@POST
	@Consumes(MediaType.APPLICATION_JSON)
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

		String hashkey = jsonObject.getString("id");
		JSONArray jsonResultList = jsonObject.getJSONArray("results");

		RequestsForNmapJobsService.OnOffClients.put(hashkey, System.currentTimeMillis());


		ImportUpdateDB dbObj = new ImportUpdateDB();
		for(int i=0;i<jsonResultList.length();i++)
		{
			String command_id = ((JSONObject) jsonResultList.get(i)).getString("command_id");
			String address = ((JSONObject) jsonResultList.get(i)).getString("address");
			String status = ((JSONObject) jsonResultList.get(i)).getString("status");
			String hostname = ((JSONObject) jsonResultList.get(i)).getString("hostname");
			String port = ((JSONObject) jsonResultList.get(i)).getString("port");
			String uptime = ((JSONObject) jsonResultList.get(i)).getString("uptime");

			System.out.println(command_id+";"+address+";"+status+";"+hostname+";"+port+";"+uptime);

			dbObj.importResult(command_id,hashkey, address, status, hostname, port, uptime);
		}


		return Response.status(200).entity(receiver.toString()).build();
	}

}
