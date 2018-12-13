package cs601.project4;

import static org.junit.Assert.assertEquals;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

import cs601.project4.server.Constants;
import cs601.project4.utility.ConnectionHelper;

public class UsersServerTests {

	@Test
	public void testURLDoesNotExist() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.USERS_URL + "/asd/" + "1" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect = ConnectionHelper.tryGetConnection(connect);
        connect.connect();
        connect.getResponseCode();
        
        /* Confirm the response is as expected */
        assertEquals(400, connect.getResponseCode());
	}
	
	@Test
	public void testUserDoesNotExist() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.USERS_URL + "/-1");
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect = ConnectionHelper.tryGetConnection(connect);
        connect.connect();
        connect.getResponseCode();
        
        /* Confirm the response is as expected */
        assertEquals(400, connect.getResponseCode());
	}

	@Test
	public void testGetUserCorrectInput() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.USERS_URL + "/" + "1" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect = ConnectionHelper.tryGetConnection(connect);
        connect.connect();
        connect.getResponseCode();
        
        /* Confirm the response is as expected */
        assertEquals(200, connect.getResponseCode());
	}
	
	@Test
	public void testCreateUserCorrectInput() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.USERS_URL + "/create" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"username\": \"Test User\"\n" + 
				"}";
		
		connect.setRequestProperty("Content-Length", Integer.toString(postData.length()));
		/* Then get response and write that */
		try(DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
			wr.writeBytes(postData);
		}
        connect.connect();
        connect.getResponseCode();
        
        /* Confirm the response is as expected */
        assertEquals(200, connect.getResponseCode());

        /* Confirm the response body text is formatted correctly */
	}
	
	@Test
	public void testCreateMalformedJsonInput() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.USERS_URL + "/create" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"username\": \"Test User\"\n" + 
				"";
		
		connect.setRequestProperty("Content-Length", Integer.toString(postData.length()));
		/* Then get response and write that */
		try(DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
			wr.writeBytes(postData);
		}
        connect.connect();
        connect.getResponseCode();
        
        /* Confirm the response is as expected */
        assertEquals(400, connect.getResponseCode());
	}
	
	@Test
	public void testCreateNoNameInput() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.USERS_URL + "/create" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"usernam\": \"Test User\"\n" + 
				"}";
		
		connect.setRequestProperty("Content-Length", Integer.toString(postData.length()));
		/* Then get response and write that */
		try(DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
			wr.writeBytes(postData);
		}
        connect.connect();
        connect.getResponseCode();
        
        /* Confirm the response is as expected */
        assertEquals(400, connect.getResponseCode());
	}
	
	@Test
	public void testCreateNoBodyInput() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.USERS_URL + "/create" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "";
		
		connect.setRequestProperty("Content-Length", Integer.toString(postData.length()));
		/* Then get response and write that */
		try(DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
			wr.writeBytes(postData);
		}
        connect.connect();
        connect.getResponseCode();
        
        /* Confirm the response is as expected */
        assertEquals(400, connect.getResponseCode());
	}

	
	@Test
	public void testAddTicketSuccess() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.USERS_URL + "/1/tickets/add" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"eventid\": 1,\n" + 
				"	\"tickets\": 1\n" + 
				"}";
		
		connect.setRequestProperty("Content-Length", Integer.toString(postData.length()));
		/* Then get response and write that */
		try(DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
			wr.writeBytes(postData);
		}
        connect.connect();
        connect.getResponseCode();
        
        /* Confirm the response is as expected */
        assertEquals(200, connect.getResponseCode());
	}
	
	@Test
	public void testAddTicketIncorrectEventId() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.USERS_URL + "/1/tickets/add" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"eventid\": -1,\n" + 
				"	\"tickets\": 1\n" + 
				"}";
		
		connect.setRequestProperty("Content-Length", Integer.toString(postData.length()));
		/* Then get response and write that */
		try(DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
			wr.writeBytes(postData);
		}
        connect.connect();
        connect.getResponseCode();
        
        /* Confirm the response is as expected */
        assertEquals(400, connect.getResponseCode());
	}
	
	@Test
	public void testPurchaseEventTicketMalformedJson() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.USERS_URL + "/1/tickets/add" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"eventid\": 1,\n" + 
				"	\"tickets\": 1\n" + 
				"";
		connect.setRequestProperty("Content-Length", Integer.toString(postData.length()));
		/* Then get response and write that */
		try(DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
			wr.writeBytes(postData);
		}
        connect.connect();
        connect.getResponseCode();
        
        /* Confirm the response is as expected */
        assertEquals(400, connect.getResponseCode());
	}
	
	@Test
	public void testPurchaseEventTicketNoEvent() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.USERS_URL + "/1/tickets/add" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"eventid\": -1,\n" + 
				"	\"tickets\": 10\n" + 
				"}";
		connect.setRequestProperty("Content-Length", Integer.toString(postData.length()));
		/* Then get response and write that */
		try(DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
			wr.writeBytes(postData);
		}
        connect.connect();
        connect.getResponseCode();
        
        /* Confirm the response is as expected */
        assertEquals(400, connect.getResponseCode());
	}
	
	@Test
	public void testPurchaseEventTicketNotEnoughTickets() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.EVENTS_URL + "/1/tickets/add" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"eventid\": 2,\n" + 
				"	\"tickets\": 10000000\n" + 
				"}";
		connect.setRequestProperty("Content-Length", Integer.toString(postData.length()));
		/* Then get response and write that */
		try(DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
			wr.writeBytes(postData);
		}
        connect.connect();
        connect.getResponseCode();
        
        /* Confirm the response is as expected */
        assertEquals(400, connect.getResponseCode());
	}
	
	@Test
	public void testPurchaseEventTicketUserDoesNotExist() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.USERS_URL + "/-1/tickets/add" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"eventid\": 1,\n" + 
				"	\"tickets\": 1\n" + 
				"}";
		
		connect.setRequestProperty("Content-Length", Integer.toString(postData.length()));
		/* Then get response and write that */
		try(DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
			wr.writeBytes(postData);
		}
        connect.connect();
        connect.getResponseCode();
        
        /* Confirm the response is as expected */
        assertEquals(400, connect.getResponseCode());
	}
	
	@Test
	public void testTransferTicketSuccessful() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.USERS_URL + "/1/tickets/transfer" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"eventid\": 1,\n" + 
				"	\"tickets\": 1,\n" + 
				"	\"targetuser\": 2\n" +
				"}";
		
		connect.setRequestProperty("Content-Length", Integer.toString(postData.length()));
		/* Then get response and write that */
		try(DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
			wr.writeBytes(postData);
		}
        connect.connect();
        connect.getResponseCode();
        
        /* Confirm the response is as expected */
        assertEquals(200, connect.getResponseCode());
	}
	
	@Test
	public void testTransferTicketOriginUserDoesNotExist() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.USERS_URL + "/-1/tickets/transfer" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"eventid\": 1,\n" + 
				"	\"tickets\": 1,\n" + 
				"	\"targetuser\": 2\n" +
				"}";
		
		connect.setRequestProperty("Content-Length", Integer.toString(postData.length()));
		/* Then get response and write that */
		try(DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
			wr.writeBytes(postData);
		}
        connect.connect();
        connect.getResponseCode();
        
        /* Confirm the response is as expected */
        assertEquals(400, connect.getResponseCode());
	}
	
	@Test
	public void testTransferTicketTargetUserDoesNotExist() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.USERS_URL + "/1/tickets/transfer" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"eventid\": 1,\n" + 
				"	\"tickets\": 1\n" + 
				"	\"targetuser\": -1\n" +
				"}";
		
		connect.setRequestProperty("Content-Length", Integer.toString(postData.length()));
		/* Then get response and write that */
		try(DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
			wr.writeBytes(postData);
		}
        connect.connect();
        connect.getResponseCode();
        
        /* Confirm the response is as expected */
        assertEquals(400, connect.getResponseCode());
	}
	
	@Test
	public void testTransferTicketEventIsNotOwned() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.USERS_URL + "/1/tickets/transfer" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"eventid\": 2,\n" + 
				"	\"tickets\": 1\n" + 
				"	\"targetuser\": 2\n" +
				"}";
		
		connect.setRequestProperty("Content-Length", Integer.toString(postData.length()));
		/* Then get response and write that */
		try(DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
			wr.writeBytes(postData);
		}
        connect.connect();
        connect.getResponseCode();
        
        /* Confirm the response is as expected */
        assertEquals(400, connect.getResponseCode());
	}
}
