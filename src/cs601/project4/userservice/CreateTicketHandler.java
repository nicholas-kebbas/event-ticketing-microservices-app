package cs601.project4.userservice;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs601.project4.server.CS601Handler;

public class CreateTicketHandler extends CS601Handler {
	
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("createticket doget");
	}
	
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException {	
		
	}
}
