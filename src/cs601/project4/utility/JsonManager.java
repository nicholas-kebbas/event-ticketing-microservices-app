package cs601.project4.utility;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

/**
 * Helper class with method that takes string as input and confirms the JSON is valid
 * @author nkebbas
 *
 */

public class JsonManager {
	public static JsonObject validateJsonString(String input) {
		JsonParser parser = new JsonParser();
		JsonObject jsonBody = new JsonObject();
		try {
			parser.parse(input);
		} catch (JsonParseException j) {
	    		return null;
		}
		if (parser.parse(input).isJsonNull()) {
			return null;
		}
		jsonBody = (JsonObject) parser.parse(input);
		return jsonBody;
	}
	
	
}
