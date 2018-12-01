package cs601.project4.userservice;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs601.project4.server.CS601Handler;

public class TransferTicketHandler extends CS601Handler {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("ticket transfer doget");
		
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
