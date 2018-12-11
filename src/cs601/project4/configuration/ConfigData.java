package cs601.project4.configuration;

import java.io.File;
import java.util.ArrayList;


/** Data read from configuration file
 * 
 * 
 * @author nkebbas
 *
 */
public class ConfigData {
	private ArrayList<String> inputFiles;
	private String type;
	private int size;
	private int queueSize;
	private String outputDirectory;
	private ArrayList<String> outputFiles;
	private int timebreak;
	private int pollTime;
	private boolean testOrder;

	public ConfigData() {
		inputFiles = new ArrayList<String>();
		outputFiles = new ArrayList<String>();
	}
	
	public void add(String input, ArrayList<String> fileStrings) {
		fileStrings.add(input);
		convertStringToFile(input);
	}
	
	private File convertStringToFile(String input) {
		File file = new File (input);
		return file;
	}
	
	public ArrayList<String> getInputFiles() {
		return inputFiles;
	}

	public void setInputFiles(ArrayList<String> inputFiles) {
		this.inputFiles = inputFiles;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getOutputDirectory() {
		return outputDirectory;
	}

	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	public int getTimebreak() {
		return timebreak;
	}

	public void setTimebreak(int timebreak) {
		this.timebreak = timebreak;
	}

	public boolean isTestOrder() {
		return testOrder;
	}

	public void setTestOrder(boolean testOrder) {
		this.testOrder = testOrder;
	}

	public int getQueueSize() {
		return queueSize;
	}

	public void setQueueSize(int threadPoolSize) {
		this.queueSize = threadPoolSize;
	}

	public ArrayList<String> getOutputFiles() {
		return outputFiles;
	}

	public void setOutputFiles(ArrayList<String> outputFiles) {
		this.outputFiles = outputFiles;
	}

	public int getPollTime() {
		return pollTime;
	}

	public void setPollTime(int pollTime) {
		this.pollTime = pollTime;
	}


}
