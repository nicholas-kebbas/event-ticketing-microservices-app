package cs601.project4.utility;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

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
