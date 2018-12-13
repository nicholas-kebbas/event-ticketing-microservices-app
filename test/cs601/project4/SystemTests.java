package cs601.project4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.junit.Test;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cs601.project4.server.Constants;
import cs601.project4.utility.ConnectionHelper;

public class SystemTests {
	
	@Test
	public void testGetUserOutput() throws IOException {
		/* Make connection to URL */
		URL url = new URL (Constants.HOST + Constants.USERS_URL + "/" + 1);
        HttpURLConnection connect = (HttpURLConnection) url.openConnection();
        connect = ConnectionHelper.tryGetConnection(connect);
		/* Get response from User server */
		BufferedReader in = new BufferedReader(
	             new InputStreamReader(connect.getInputStream()));
	     String inputLine;
	     StringBuffer responseString = new StringBuffer();
	     while ((inputLine = in.readLine()) != null) {
	     	responseString.append(inputLine);
	     }
	     in.close();
	     String expectedResult = "{" + 
		     		"	\"userid\": 1," + 
		     		"	\"username\": \"admin\"," + 
		     		"	\"tickets\": [" + 
		     		"		{" + 
		     		"			\"eventid\": 1" + 
		     		"		}" + 
		     		"	]" + 
		     		"}";
	     assertEquals(expectedResult.trim().replaceAll("\\s+", ""), responseString.toString().trim().replaceAll("\\s+", "")); 
	}
	
	@Test
	public void testGetEventOutput() throws IOException {
		/* Make connection to URL */
		URL url = new URL (Constants.HOST + Constants.EVENTS_URL + "/" + 2);
        HttpURLConnection connect = (HttpURLConnection) url.openConnection();
        connect = ConnectionHelper.tryGetConnection(connect);
		/* Get response from User server */
		BufferedReader in = new BufferedReader(
	             new InputStreamReader(connect.getInputStream()));
	     String inputLine;
	     StringBuffer responseString = new StringBuffer();
	     while ((inputLine = in.readLine()) != null) {
	     	responseString.append(inputLine);
	     }
	     in.close();
	     
	     String expectedResult = "{\n" + 
	     		"	\"eventid\": 2, \n" + 
	     		"	\"eventname\": \"AdminEventNoTickets\", \n" + 
	     		"	\"userid\": 1,		\n" + 
	     		"	\"avail\": 0, \n" + 
	     		"	\"purchased\": 0\n" + 
	     		"}";
	     
	     assertEquals(expectedResult.trim().replaceAll("\\s+", ""), responseString.toString().trim().replaceAll("\\s+", "")); 
	}
	
	@Test
	public void testCreateUserOutput() throws IOException {
		String getBody = "{" + 
				"	\"username\": \"TestUser\"\n" + 
				"}";
		byte[] postData = getBody.getBytes( StandardCharsets.UTF_8 );
		URL url = new URL(Constants.HOST + Constants.USERS_URL + "/create");
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect = ConnectionHelper.tryPostConnection(connect, postData);
        connect.connect();
        
        /* Get Actual Response */
		BufferedReader input = new BufferedReader(new InputStreamReader(connect.getInputStream()));
		String inputLine;
		StringBuffer responseString = new StringBuffer();
		while ((inputLine = input.readLine()) != null) {
			responseString.append(inputLine);
		}
		input.close();
        connect.connect();
        
        JsonParser parser = new JsonParser();
        JsonObject responseObject = (JsonObject) parser.parse(responseString.toString());
        int id = responseObject.get("userid").getAsInt();
        String expectedResponse ="{\n" + 
        		"	\"userid\":" + id + 
        		"}";
       assertEquals(expectedResponse.trim().replaceAll("\\s+", ""), responseString.toString().trim().replaceAll("\\s+", ""));
	}
	
	@Test
	public void testCreateEventOutput() throws IOException {
		String getBody = "{\n" + 
				"	\"userid\": 1," +
				"	\"eventname\": \"Test Event\",\n" + 
				"	\"numtickets\": 0" +
				"}";
		byte[] postData = getBody.getBytes( StandardCharsets.UTF_8 );
		URL url = new URL(Constants.HOST + Constants.EVENTS_URL + "/create");
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect = ConnectionHelper.tryPostConnection(connect, postData);
        
        /* Get Actual Response and compare*/
		BufferedReader input = new BufferedReader(new InputStreamReader(connect.getInputStream()));
		String inputLine;
		StringBuffer responseString = new StringBuffer();
		while ((inputLine = input.readLine()) != null) {
			responseString.append(inputLine);
		}
		
		input.close();
        connect.connect();
        
        JsonParser parser = new JsonParser();
        JsonObject responseObject = (JsonObject) parser.parse(responseString.toString());
        int id = responseObject.get("eventid").getAsInt();
        
        String expectedResponse ="{" + 
        		"	\"eventid\":" + id + 
        		"}";
       assertEquals(expectedResponse.toString().trim().replaceAll("\\s+", ""), responseString.toString().trim().replaceAll("\\s+", ""));
	}
	
	/* Confirm that get List is a JsonArray that contains both events I know to be there */
	@Test
	public void testGetEventsList() throws IOException {
			/* Make connection to URL */
			URL url = new URL (Constants.HOST + Constants.EVENTS_URL + "/list");
	        HttpURLConnection connect = (HttpURLConnection) url.openConnection();
	        connect = ConnectionHelper.tryGetConnection(connect);
			/* Get response from User server */
			BufferedReader in = new BufferedReader(
		             new InputStreamReader(connect.getInputStream()));
		     String inputLine;
		     StringBuffer responseString = new StringBuffer();
		     while ((inputLine = in.readLine()) != null) {
		     	responseString.append(inputLine);
		     }
		     in.close();
		     
		     String expectedResult = "{\n" + 
		     		"	\"eventid\": 2, \n" + 
		     		"	\"eventname\": \"AdminEventNoTickets\", \n" + 
		     		"	\"userid\": 1,		\n" + 
		     		"	\"avail\": 0, \n" + 
		     		"	\"purchased\": 0\n" + 
		     		"}";
	         JsonParser parser = new JsonParser();
	         JsonArray responseObject = (JsonArray) parser.parse(responseString.toString());
			 expectedResult = expectedResult.trim().replaceAll("\\s+", "");
			 responseString.toString().trim().replaceAll("\\s+", "");
			 assertTrue(responseString.toString().contains(expectedResult)); 
	}
	/** Frontend System Tests **/
	@Test
	public void testGetUserOutputFrontend() throws IOException {
		/* Make connection to URL */
		URL url = new URL (Constants.HOST + Constants.FRONTEND_URL + "/users/" + 1);
        HttpURLConnection connect = (HttpURLConnection) url.openConnection();
        connect = ConnectionHelper.tryGetConnection(connect);
		/* Get response from User server */
		BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
		String inputLine;
		StringBuffer responseString = new StringBuffer();
		
		while ((inputLine = in.readLine()) != null) {
			responseString.append(inputLine);
		}
		
		in.close();
	     
	     String expectedResult = "{" + 
		     		"	\"userid\": 1," + 
		     		"	\"username\": \"admin\"," + 
		     		"	\"tickets\": [" + 
		     		"		{" + 
		     		"			\"eventid\": 1," + 
		     		"			\"eventname\": \"AdminEventTickets\"," + 
		     		"			\"userid\": 1," + 
		     		"			\"avail\": 999690," + 
		     		"			\"purchased\": 309" + 
		     		"		}" + 
		     		"	]" + 
		     		"}";
	     
	     assertEquals(expectedResult.trim().replaceAll("\\s+", ""), responseString.toString().trim().replaceAll("\\s+", "")); 
	}
	
	@Test
	public void testGetEventOutputFrontend() throws IOException {
		/* Make connection to URL */
		URL url = new URL (Constants.HOST + Constants.FRONTEND_URL + "/events/" + 2);
        HttpURLConnection connect = (HttpURLConnection) url.openConnection();
        connect = ConnectionHelper.tryGetConnection(connect);
		/* Get response from User server */
		BufferedReader in = new BufferedReader(
	             new InputStreamReader(connect.getInputStream()));
	     String inputLine;
	     StringBuffer responseString = new StringBuffer();
	     while ((inputLine = in.readLine()) != null) {
	     	responseString.append(inputLine);
	     }
	     in.close();
	     
	     String expectedResult = "{\n" + 
	     		"	\"eventid\": 2, \n" + 
	     		"	\"eventname\": \"AdminEventNoTickets\", \n" + 
	     		"	\"userid\": 1,		\n" + 
	     		"	\"avail\": 0, \n" + 
	     		"	\"purchased\": 0\n" + 
	     		"}";
	     
	     assertEquals(expectedResult.trim().replaceAll("\\s+", ""), responseString.toString().trim().replaceAll("\\s+", "")); 
	}
	
	@Test
	public void testCreateUserOutputFrontend() throws IOException {
		String getBody = "{\n" + 
				"	\"username\": \"TestUser\"\n" + 
				"}";
		byte[] postData = getBody.getBytes( StandardCharsets.UTF_8 );
		URL url = new URL(Constants.HOST + Constants.FRONTEND_URL + "/users/create");
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect = ConnectionHelper.tryPostConnection(connect, postData);
        
        /* Get Actual Response and compare*/
		BufferedReader input = new BufferedReader(new InputStreamReader(connect.getInputStream()));
		String inputLine;
		StringBuffer responseString = new StringBuffer();
		while ((inputLine = input.readLine()) != null) {
			responseString.append(inputLine);
		}
		
		input.close();
        connect.connect();
        
        JsonParser parser = new JsonParser();
        JsonObject responseObject = (JsonObject) parser.parse(responseString.toString());
        int id = responseObject.get("userid").getAsInt();
        String expectedResponse ="{\n" + 
        		"	\"userid\":" + id + 
        		"}";
       assertEquals(responseString.toString().trim().replaceAll("\\s+", ""), expectedResponse.toString().trim().replaceAll("\\s+", ""));
	}
	
	@Test
	public void testCreateEventOutputFrontend() throws IOException {
		String getBody = "{\n" + 
				"	\"userid\": 1," +
				"	\"eventname\": \"Test Event\",\n" + 
				"	\"numtickets\": 0" +
				"}";
		byte[] postData = getBody.getBytes( StandardCharsets.UTF_8 );
		URL url = new URL(Constants.HOST + Constants.FRONTEND_URL + "/events/create");
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect = ConnectionHelper.tryPostConnection(connect, postData);
        connect.connect();
        
        /* Get Actual Response and compare*/
		BufferedReader input = new BufferedReader(new InputStreamReader(connect.getInputStream()));
		String inputLine;
		StringBuffer responseString = new StringBuffer();
		while ((inputLine = input.readLine()) != null) {
			responseString.append(inputLine);
		}
		
		input.close();
        connect.connect();
        
        JsonParser parser = new JsonParser();
        JsonObject responseObject = (JsonObject) parser.parse(responseString.toString());
        int id = responseObject.get("eventid").getAsInt();
        
        String expectedResponse ="{" + 
        		"	\"eventid\":" + id + 
        		"}";
       assertEquals(responseString.toString().trim().replaceAll("\\s+", ""), expectedResponse.toString().trim().replaceAll("\\s+", ""));
	}
	
	/* Confirm that get List is a JsonArray that contains both events I know to be there */
	@Test
	public void testGetEventsListFrontend() throws IOException {
			/* Make connection to URL */
			URL url = new URL (Constants.HOST + Constants.FRONTEND_URL + "/events");
	        HttpURLConnection connect = (HttpURLConnection) url.openConnection();
	        connect = ConnectionHelper.tryGetConnection(connect);
			/* Get response from User server */
			BufferedReader in = new BufferedReader(
		             new InputStreamReader(connect.getInputStream()));
		     String inputLine;
		     StringBuffer responseString = new StringBuffer();
		     while ((inputLine = in.readLine()) != null) {
		     	responseString.append(inputLine);
		     }
		     in.close();
		     
		     String expectedResult = "{\n" + 
		     		"	\"eventid\": 2, \n" + 
		     		"	\"eventname\": \"AdminEventNoTickets\", \n" + 
		     		"	\"userid\": 1,		\n" + 
		     		"	\"avail\": 0, \n" + 
		     		"	\"purchased\": 0\n" + 
		     		"}";
	         JsonParser parser = new JsonParser();
	         JsonArray responseObject = (JsonArray) parser.parse(responseString.toString());
			 expectedResult = expectedResult.trim().replaceAll("\\s+", "");
			 responseString.toString().trim().replaceAll("\\s+", "");
			 assertTrue(responseString.toString().contains(expectedResult)); 
	}
	/* Helper Methods to connect to another API 
	 */
}
