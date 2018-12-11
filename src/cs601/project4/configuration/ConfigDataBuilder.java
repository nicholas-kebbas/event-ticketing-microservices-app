package cs601.project4.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
 * Builder class to build ConfigData Object
 * 
 * @author nkebbas
 *
 */
public class ConfigDataBuilder {
	private ConfigData d;
	
	public ConfigDataBuilder() {
		d = new ConfigData();
	}
	
	/** 
	 * Read configuration file to get paths to input files
	 * @param input
	 */
	public ConfigData buildConfigData(String input) {
		Path path = Paths.get(input);
	    Charset charset = java.nio.charset.StandardCharsets.ISO_8859_1;
	    if ((!input.endsWith(".json"))){
			System.out.println("File does not end in json. Output may be unexpected.");
		}
		try (BufferedReader br = Files.newBufferedReader(path, charset)) {
			String line;
			while ((line = br.readLine()) != null) {
			    JsonElement jelement = new JsonParser().parse(line);
			    JsonObject  jobject = jelement.getAsJsonObject();
			    JsonArray inputFiles = jobject.getAsJsonArray("input");
			    JsonArray outputFiles = jobject.getAsJsonArray("outputFiles");
			    JsonElement outputDirectory = jobject.get("outputDirectory");
			    JsonElement type = jobject.get("type");
			    JsonElement threads = jobject.get("threads");
			    JsonElement queueSize = jobject.get("queueSize");
			    JsonElement pollTime = jobject.get("pollTime");
			    JsonElement timebreak = jobject.get("timebreak");
			    JsonElement testOrder = jobject.get("testOrder");
			    
			    if (type != null) {
			    		this.d.setType(type.getAsString());
			    }
			    if (threads != null) {
			    		this.d.setSize(threads.getAsInt());
			    }
			    if (outputDirectory != null) {
			    		this.d.setOutputDirectory(outputDirectory.getAsString());
			    		File directory = new File (this.d.getOutputDirectory());
			    		boolean exists = directory.exists();
			    		if (!exists) {
			    			new File(this.d.getOutputDirectory()).mkdirs();
			    		}
			    }
			    if (timebreak != null) {
			    		this.d.setTimebreak(timebreak.getAsInt());
			    }
			    if (testOrder != null) {
			    		this.d.setTestOrder(testOrder.getAsBoolean());
			    }
			    if (queueSize != null) {
		    			this.d.setQueueSize(queueSize.getAsInt());
			    }
			    if (pollTime != null) {
			    		this.d.setPollTime(pollTime.getAsInt());
			    }
			    for (JsonElement e : inputFiles) {
			    		this.d.add(e.getAsString(), this.d.getInputFiles());
			    }
			    for (JsonElement e : outputFiles) {
		    			this.d.add(e.getAsString(), this.d.getOutputFiles());
			    }
            }
			
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
		return d;
	}
}
