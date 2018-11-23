package cs601.project4.frontend;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cs601.project4.eventservice.CreateEventHandler;
import cs601.project4.server.Constants;
/**
 * This will hit the create event API on the event server. Should create a config file
 * so that 
 * @author nkebbas
 *
 */
public class FrontendCreateEventHandler extends HttpServlet {
	/* So this needs to hit the Event API. That URL will need to be added to the config file. */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println(request == null);
		String getBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		System.out.println(request.getAttribute("userid"));
		JsonParser parser = new JsonParser();
        JsonObject jsonBody = (JsonObject) parser.parse(getBody);
        System.out.println("here");
        /* Pass this to the Events API, so need to open a connection */
        URL url = new URL (Constants.EVENTS_URL + "/create");
        CreateEventHandler handler = new CreateEventHandler();
        handler.doPost(request, response);
        System.out.println("here2");
        HttpURLConnection connect = (HttpURLConnection) url.openConnection();
        connect.setDoOutput(true);
        byte[] outputInBytes = getBody.getBytes("UTF-8");
        InputStream input = connect.getInputStream();
        processString(input);
        input.read(outputInBytes);    
        int userId = jsonBody.get("userid").getAsInt();
        String eventName = jsonBody.get("eventname").getAsString(); 
        int numTickets = jsonBody.get("numtickets").getAsInt();
        System.out.println("here3");
        connect.connect();
		//int read = inStream.read(bytes);
		/* figure out what this does */
//		if (getBody.length() > 0) {
//			while (read < getBody.length()) {
//				read += inStream.read(bytes, read, (bytes.length-read));
//			}
//		}
//		System.out.println(read);
        
	}
	
    private static String oneLine(InputStream instream) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte b = (byte) instream.read();
        while (b != '\n' && b != -1) {
            bout.write(b);
            b = (byte) instream.read();
        }
        return new String(bout.toByteArray());
    }
    
    private static String processString (InputStream instream) throws IOException {
    		String message = "";
    		if (instream != null) {
			String requestLine = oneLine(instream);
			String line = oneLine(instream);
			int length = 0;
			while(line != null && !line.trim().isEmpty()) {
				message += line + "\n";
				line = oneLine(instream);		
				System.out.println(message);
			}
			
			byte[] bytes = new byte[length];
			int read = instream.read(bytes);
			/* figure out what this does */
			if (length > 0) {
	    			while (read < length) {
	    				read += instream.read(bytes, read, (bytes.length-read));
	    			}
			}
			return message;
    		}
    		return message;
    }
    
    
}
