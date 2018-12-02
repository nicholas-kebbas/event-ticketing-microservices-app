package cs601.project4.eventservice;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs601.project4.server.CS601Handler;
/**
 * Update number of available tickets after ticket is purchased.
 * This will be contacted by servlet in user when ticket is purchased.
 * Get the eventId and the number of tickets from the user handler.
 * @author nkebbas
 *
 */
public class UpdateTicketAvailabilityHandler extends CS601Handler {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("do get available tickets");
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("do post available tickets");
		response.setStatus(HttpServletResponse.SC_OK);
	}

}
