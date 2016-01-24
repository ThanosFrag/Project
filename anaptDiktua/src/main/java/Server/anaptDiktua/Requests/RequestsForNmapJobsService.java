package Server.anaptDiktua.Requests;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.json.JSONObject;

import Server.anaptDiktua.import_updateDB.ImportUpdateDB;

//@Path("/")
@Path("/nmapjobsrequest")
public class RequestsForNmapJobsService {
	static final Map<String, Long> OnOffClients = new ConcurrentHashMap<String, Long>();   //hash id , current time millisec

	public static Map<String, Long> getClientsOnOff() {return OnOffClients;}
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void nmapJobsREST(InputStream inData)  //Response
	{
		StringBuilder receiver = new StringBuilder();
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(inData));
			String line = null;
			while ((line = in.readLine()) != null)
			{
				receiver.append(line);
				System.out.println(line);
			}
		}
		catch (Exception e)
		{
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Hashkey: " + receiver.toString());
		JSONObject jsonObject = new JSONObject(receiver.toString());

		String hashkey = jsonObject.getString("id");

		OnOffClients.put(hashkey, System.currentTimeMillis());
		//ImportUpdateDB dbObj = new ImportUpdateDB();
		//dbObj.changeClientStatus(hashkey);

		System.out.println("ID : "+hashkey);


		/////responce ti tha kanei



		// return HTTP response 200 in case of success
		//return Response.status(200).entity(receiver.toString()).build();
	}


}
