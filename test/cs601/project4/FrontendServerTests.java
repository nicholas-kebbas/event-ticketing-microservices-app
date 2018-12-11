package cs601.project4;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

import cs601.project4.server.Constants;

public class FrontendServerTests {
	
	
	/**
	 * Users Tests
	 */
	@Test
	public void testURLDoesNotExist() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.FRONTEND_URL + "/asd/" + "1" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		
		connect.setDoOutput(true);
        connect.setRequestMethod("GET");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
        connect.connect();
        connect.getResponseCode();
        
        /* Confirm the response is as expected */
        assertEquals(400, connect.getResponseCode());
	}
	
	@Test
	public void testGetUserSuccess() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.FRONTEND_URL + "/users/1");
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		
		connect.setDoOutput(true);
        connect.setRequestMethod("GET");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
        connect.connect();
        connect.getResponseCode();
        
        /* Confirm the response is as expected */
        assertEquals(200, connect.getResponseCode());
	}
	
	@Test
	public void testUserDoesNotExist() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.FRONTEND_URL + "/users/-1");
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("GET");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
        connect.connect();
        connect.getResponseCode();
        
        /* Confirm the response is as expected */
        assertEquals(400, connect.getResponseCode());
	}
	
	@Test
	public void testCreateUserCorrectInput() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.FRONTEND_URL + "/users/create" );
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
	public void testUserCreateMalformedJsonInput() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.FRONTEND_URL + "/users/create" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"username\": \"Test User\",\n" + 
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
		URL url = new URL(Constants.HOST + Constants.FRONTEND_URL + "/users/create" );
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
		URL url = new URL(Constants.HOST + Constants.FRONTEND_URL + "/users/create" );
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
	
	
	/**
	 * Event Tests
	 */
	
	@Test
	public void testGetEventSuccess() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.FRONTEND_URL + "/events/1");
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		
		connect.setDoOutput(true);
        connect.setRequestMethod("GET");
		connect.setRequestProperty("Content-Type", "application/json");
		connect.setRequestProperty("charset", "utf-8");
        connect.connect();
        connect.getResponseCode();
        
        /* Confirm the response is as expected */
        assertEquals(200, connect.getResponseCode());
	}
	
	@Test
	public void testEventDoesNotExist() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.FRONTEND_URL + "/events/-1");
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		
		connect.setDoOutput(true);
        connect.setRequestMethod("GET");
		connect.setRequestProperty("Content-Type", "application/json");
		connect.setRequestProperty("charset", "utf-8");
        connect.connect();
        connect.getResponseCode();
        
        /* Confirm the response is as expected */
        assertEquals(400, connect.getResponseCode());
	}
	
	@Test
	public void testCreateEventCorrectInput() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.FRONTEND_URL + "/events/create" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json");
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"userid\": 1,\n" + 
				"	\"eventname\": \"Test Event\",\n" + 
				"	\"numtickets\": 0\n" + 
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
	public void testCreateEventUserDoesNotExist() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.FRONTEND_URL + "/events/create" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json");
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"userid\": -1,\n" + 
				"	\"eventname\": \"Test Event\",\n" + 
				"	\"numtickets\": 0\n" + 
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

        /* Confirm the response body text is formatted correctly */
	}
	
	@Test
	public void testCreateMalformedJsonInput() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.EVENTS_URL + "/create" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json");
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"userid\": 0,\n" + 
				"	\"eventname\": \"string\",\n" + 
				"	\"numtickets\": 0\n" + 
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
	public void testCreateNoEventNameInput() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.FRONTEND_URL + "/events/create" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json");
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"userid\": 0,\n" + 
				"	\"eventnam\": \"string\",\n" + 
				"	\"numtickets\": 0\n" + 
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
	public void testCreateNoUserIdInput() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.FRONTEND_URL + "/events/create" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json");
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"eventname\": \"string\",\n" + 
				"	\"numtickets\": 0\n" + 
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
	public void testCreateNoTicketsInput() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.FRONTEND_URL + "/events/create" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json");
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"userid\": 0,\n" + 
				"	\"eventname\": \"string\",\n" + 
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
	
	/**
	 * Tickets Tests
	 */
	
	@Test
	public void purchaseTicketSuccess() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.FRONTEND_URL + "/events/1/purchase/1" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
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
	public void purchaseTicketErrorEventDoesNotExist() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.FRONTEND_URL + "/events/-1/purchase/1" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
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
	public void purchaseTicketErrorUserDoesNotExist() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.FRONTEND_URL + "/events/1/purchase/-1" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
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
	public void purchaseTicketErrorNoTicketParameter() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.FRONTEND_URL + "/events/2/purchase/1" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"ticets\": 999999999\n" + 
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
	public void purchaseTicketErrorNotEnoughAvailableTickets() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.FRONTEND_URL + "/events/2/purchase/1" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"tickets\": 999999999\n" + 
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
	
	/**
	 * Transfer Ticket Test Cases
	 * /users/{userid}/tickets/transfer
	 */
	
	@Test
	public void transferTicketSuccess() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.FRONTEND_URL + "/users/1/tickets/transfer");
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
	public void transferTicketFromUserDoesNotExist() throws IOException {
		URL url = new URL(Constants.HOST + Constants.FRONTEND_URL + "/users/-1/tickets/transfer" );
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
	public void transferTicketToUserDoesNotExist() throws IOException {
		URL url = new URL(Constants.HOST + Constants.FRONTEND_URL + "/users/1/tickets/transfer" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"eventid\": 1,\n" + 
				"	\"tickets\": 1,\n" + 
				"	\"targetuser\": -1\n" + 
				"}";
		
		connect.setRequestProperty("Content-Length", Integer.toString(postData.length()));
		/* Then get response and write that */
		try (DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
			wr.writeBytes(postData);
		}
		
        connect.connect();
        connect.getResponseCode();
        
        /* Confirm the response is as expected */
        assertEquals(400, connect.getResponseCode());
		
	}
	
	@Test
	public void transferTicketEventDoesNotExist() throws IOException {
		URL url = new URL(Constants.HOST + Constants.FRONTEND_URL + "/users/1/tickets/transfer" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"eventid\": -1,\n" + 
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
	public void transferTicketMalformedJson() throws IOException {
		URL url = new URL(Constants.HOST + Constants.FRONTEND_URL + "/users/1/tickets/transfer" );
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
	public void transferTicketNotEnoughTickets() throws IOException {
		URL url = new URL(Constants.HOST + Constants.FRONTEND_URL + "/users/2/tickets/transfer" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"eventid\": 1,\n" + 
				"	\"tickets\": 99999999999,\n" + 
				"	\"targetuser\": 1\n" + 
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
