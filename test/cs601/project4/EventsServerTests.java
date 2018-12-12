package cs601.project4;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

import cs601.project4.server.Constants;

public class EventsServerTests {
	
	public EventsServerTests() {
		
	}
	
	@Test
	public void testURLDoesNotExist() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.EVENTS_URL + "/asd/" + "1" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		
		connect.setDoOutput(true);
        connect.setRequestMethod("GET");
		connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		connect.setRequestProperty("charset", "utf-8");
        connect.connect();
        connect.getResponseCode();
        
        /* Confirm the response is as expected */
        assertEquals(400, connect.getResponseCode());
	}
	
	@Test
	public void testEventDoesNotExist() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.EVENTS_URL + "/-1");
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		
		connect.setDoOutput(true);
        connect.setRequestMethod("GET");
		connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		connect.setRequestProperty("charset", "utf-8");
        connect.connect();
        connect.getResponseCode();
        
        /* Confirm the response is as expected */
        assertEquals(400, connect.getResponseCode());
	}

	@Test
	public void testGetEventCorrectInput() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.EVENTS_URL + "/" + "1" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		
		connect.setDoOutput(true);
        connect.setRequestMethod("GET");
		connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		connect.setRequestProperty("charset", "utf-8");
        connect.connect();
        connect.getResponseCode();
        
        /* Confirm the response is as expected */
        assertEquals(200, connect.getResponseCode());
	}
	
	@Test
	public void testCreateEventCorrectInput() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.EVENTS_URL + "/create" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"userid\": 1,\n" + 
				"	\"eventname\": \"Test Event\",\n" + 
				"	\"numtickets\": 10\n" + 
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
		URL url = new URL(Constants.HOST + Constants.EVENTS_URL + "/create" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"userid\": -1,\n" + 
				"	\"eventname\": \"Test Event Should Not In DB\",\n" + 
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
		connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"userid\": 0,\n" + 
				"	\"eventname\": \"Test Event Should Not In DB\",\n" + 
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
		URL url = new URL(Constants.HOST + Constants.EVENTS_URL + "/create" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"userid\": 0,\n" + 
				"	\"evenname\": \"Test Event Should Not In DB\",\n" + 
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
		URL url = new URL(Constants.HOST + Constants.EVENTS_URL + "/create" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"eventname\": \"Test Event Should Not In DB\",\n" + 
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
		URL url = new URL(Constants.HOST + Constants.EVENTS_URL + "/create" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"userid\": 0,\n" + 
				"	\"eventname\": \"Test Event Should Not In DB\",\n" + 
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
	public void testGetListSuccess() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.EVENTS_URL + "/list" );
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
	
	/**
	 * Need to figure out how to make no events display
	 * @throws IOException
	 */

	//	@Test
//	public void testGetListNoEvents() throws IOException {
//		/* Make connection to URL */
//		URL url = new URL(Constants.HOST + Constants.EVENTS_URL + "/list" );
//		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
//		connect.setDoOutput(true);
//        
//        connect.connect();
//        connect.getResponseCode();
//        
//        /* Confirm the response is as expected */
//        assertEquals(400, connect.getResponseCode());
//	}
	
	@Test
	public void testPurchaseEventTicketSuccess() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.EVENTS_URL + "/purchase/1" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"userid\": 1,\n" + 
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
	public void testPurchaseEventTicketNotEnoughTickets() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.EVENTS_URL + "/purchase/2" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"userid\": 1,\n" + 
				"	\"eventid\": 2,\n" + 
				"	\"tickets\": 10000\n" + 
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
	public void testPurchaseEventTicketEventDoesNotExist() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.EVENTS_URL + "/purchase/-1" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"userid\": 1,\n" + 
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
	public void testPurchaseEventTicketEventIdDifferentThanUrl1() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.EVENTS_URL + "/purchase/1" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"userid\": 1,\n" + 
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
	public void testPurchaseEventTicketEventIdDifferentThanUrl2() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.EVENTS_URL + "/purchase/-1" );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"userid\": 1,\n" + 
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
	public void testPurchaseEventTicketUserDoesNotExist() throws IOException {
		/* Make connection to URL */
		URL url = new URL(Constants.HOST + Constants.EVENTS_URL + "/purchase/" + 1 );
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		connect.setDoOutput(true);
        connect.setRequestMethod("POST");
		connect.setRequestProperty("Content-Type", "application/json"); 
		connect.setRequestProperty("charset", "utf-8");
		/* Write the body of the request */
		String postData = "{\n" + 
				"	\"userid\": -1,\n" + 
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
	
}
