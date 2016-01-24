
package Server.anaptDiktua;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.json.JSONObject;

import Server.anaptDiktua.Requests.BroadcasterToClient;
import Server.anaptDiktua.Requests.ReceiveSoftwareAgents;
import Server.anaptDiktua.graphics.Login;
import Server.anaptDiktua.import_updateDB.ImportUpdateDB;

import javax.ws.rs.core.UriBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class Main {

    private static int getPort(int defaultPort) {
        //grab port from environment, otherwise fall back to default port 9998
        String httpPort = System.getProperty("jersey.test.port");
        if (null != httpPort) {
            try {
                return Integer.parseInt(httpPort);
            } catch (NumberFormatException e) {
            }
        }
        return defaultPort;
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost/").port(getPort(9998)).build();
    }

    public static final URI BASE_URI = getBaseURI();

    protected static HttpServer startServer() throws IOException {
        ResourceConfig resourceConfig = new ResourceConfig().packages("Server.anaptDiktua");

        System.out.println("Starting grizzly2...");
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig);
    }

    @SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
        // Grizzly 2 initialization
        HttpServer httpServer = startServer();
        System.out.println(String.format("Jersey app  started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...",
                BASE_URI));
        new Login().runLogin();
    	System.out.println("Accept or not: ");
    	BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));

        while(true)
        {
	        String act = getAct();
	        if(act.equals("1"))
	    	{
	        	JSONObject data = new JSONObject();
	        	Collection<String> commands = new LinkedList<String>();
	        	commands.add("1,-A -oX - 192.168.0.3,true,5");
	        	commands.add("2,-O -oX - 192.168.0.4,true,0");
	        	commands.add("3,-O -oX - 192.168.0.5,false,0");
	        	data.put("commands",commands);
	        	BroadcasterToClient.SendToClient("123456",data);
	    	}
	        else if(act.equals("2"))
	    	{
	        	JSONObject data = new JSONObject();
	        	Collection<String> commands = new LinkedList<String>();
	        	commands.add("4,-O -oX - 192.168.0.3,false,0");
	        	commands.add("5,-O -oX - 192.168.0.4,true,0");
	        	commands.add("6,-O -oX - 192.168.0.5,false,0");
	        	data.put("commands",commands);
	        	BroadcasterToClient.SendToClient("123456",data);
	    	}
	        else if(act.equals("3"))
	    	{
	        	JSONObject data = new JSONObject();
	        	Collection<String> commands = new LinkedList<String>();
	        	commands.add("5,Stop,true,periodic");
	        	data.put("commands",commands);
	        	BroadcasterToClient.SendToClient("123456",data);
	    	}
	        else if(act.equals("4"))
	    	{
	        	JSONObject data = new JSONObject();
	        	Collection<String> commands = new LinkedList<String>();
	        	commands.add("-1,exit(0),true,-1");
	        	data.put("commands",commands);
	        	BroadcasterToClient.SendToClient("123456",data);
	    	}
	        else if(act.equals("5"))
	    	{
	        	String answer ="";
	            try {
	            	answer = bReader.readLine();
	    			if (answer.equals("yes")){
	    				Map<String,ArrayList<String>> map = ReceiveSoftwareAgents.getSAmap();
	    				ArrayList<String> list = map.get("root00-0C-29-38-01-F8123.2133.19.0-25-generic6.40");
	    				ImportUpdateDB dbObj = new ImportUpdateDB();
	    				dbObj.importSA(list.get(0),list.get(1), list.get(2), list.get(4), "root00-0C-29-38-01-F8123.2133.19.0-25-generic6.40", list.get(3));
	    			}


	    		} catch (IOException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	            BroadcasterToClient.SendToClient("root00-0C-29-38-01-F8123.2133.19.0-25-generic6.40",answer);

	    	}
	        else
        	{
	        	httpServer.stop();
	        	break;
        	}
        }


    }


    private static String getAct() throws IOException {

        String act = "";

        System.out.print("Please enter a number: ");
        BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
        act = bReader.readLine();

        while(act.length() < 1)
        {
        	System.out.print("Please enter a number: ");
        	bReader = new BufferedReader(new InputStreamReader(System.in));
            act = bReader.readLine();
        }

        return act;
    }
}
