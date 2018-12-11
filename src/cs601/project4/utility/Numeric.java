package cs601.project4.utility;

public class Numeric {
	public static boolean isNumeric(String s) {  
		  try {  
		    int num = Integer.parseInt(s);
		  }	catch (NumberFormatException nfe) {  
		    return false;  
		  } return true;  
	}
}
