package Server.anaptDiktua.Requests;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.glassfish.jersey.media.sse.SseFeature;
import org.json.JSONObject;

@Path("/")
public class BroadcasterToClient {

	static final Map<String, SseBroadcaster> CLIENT_CONNECTED = new ConcurrentHashMap<String, SseBroadcaster>();
	static final Map<String, SseBroadcaster> SA_CONNECTED = new ConcurrentHashMap<String, SseBroadcaster>();
	static final Map<String, SseBroadcaster> AC_CONNECTED = new ConcurrentHashMap<String, SseBroadcaster>();

	@GET
	@Path("updatestate/{hashID}")
	@Produces(SseFeature.SERVER_SENT_EVENTS)
	public EventOutput updateState(@PathParam("hashID") String hashID) {
	    EventOutput eo = new EventOutput();
	    CLIENT_CONNECTED.get(hashID).add(eo);

	    return eo;
	}

	public static void SendToClient(String hashID,JSONObject data ) {
		System.out.println("Send to client "+hashID);
	    CLIENT_CONNECTED.get(hashID).broadcast(buildEvent(hashID,data));
	}

	public static void SendToClient(String hashID,String data ) {
		System.out.println("Send to client "+hashID);
	    SA_CONNECTED.get(hashID).broadcast(buildEvent(hashID,data));
	}
	public static void SendToAndroidClient(String username,String data ) {
		System.out.println("Send to Android client "+username);
	    SA_CONNECTED.get(username).broadcast(buildEvent(username,data));
	}

	@GET
	@Path("registerfornmaps/{hashID}")
	@Produces(SseFeature.SERVER_SENT_EVENTS)
	public EventOutput registerState(@PathParam("hashID") String hashID) {

		CLIENT_CONNECTED.put(hashID, new SseBroadcaster());

	    EventOutput eo = new EventOutput();
	    CLIENT_CONNECTED.get(hashID).add(eo);

	    System.out.println("Server receive a connection");
	    return eo;
	}
	@GET
	@Path("register/{hashID}")
	@Produces(SseFeature.SERVER_SENT_EVENTS)
	public EventOutput register(@PathParam("hashID") String hashID) {
	    SA_CONNECTED.put(hashID, new SseBroadcaster());

	    EventOutput eo = new EventOutput();
	    SA_CONNECTED.get(hashID).add(eo);

	    System.out.println("Server receive a connection");

	    return eo;
	}
	@GET
	@Path("androidregister/{androidUsername}")
	@Produces(SseFeature.SERVER_SENT_EVENTS)
	public EventOutput androidregister(@PathParam("androidUsername") String androidUsername) {
	    AC_CONNECTED.put(androidUsername, new SseBroadcaster());

	    EventOutput eo = new EventOutput();
	    AC_CONNECTED.get(androidUsername).add(eo);

	    System.out.println("Server receive a android connection");

	    return eo;
	}	
	private static OutboundEvent buildEvent(String hashID,JSONObject data) {
	    OutboundEvent.Builder builder = new OutboundEvent.Builder();
	    OutboundEvent event = builder.name(hashID).data(String.class, data.toString()).build();

	    System.out.println("buildEvent data : "+data.toString());
	    return event;
	}

	private static OutboundEvent buildEvent(String hashID,String data) {
	    OutboundEvent.Builder builder = new OutboundEvent.Builder();
	    OutboundEvent event = builder.name(hashID).data(String.class, data.toString()).build();

	    System.out.println("buildEvent data : "+data.toString());
	    return event;
	}
}
