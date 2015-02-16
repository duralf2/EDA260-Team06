package register.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration extends Properties {

	public static final String VALUE_RACE_MARATHON = "marathon";
	public static final String VALUE_RACE_LAPS = "laps";
	
	private static final File DEFAULT_PROPERTIES_FILE = new File("data/config.ini"); 

	public static final String KEY_RACE_TYPE = "raceType";	
	public static final String KEY_GUI_OUTPUT_FILE_PATH = "guiOutputFilePath";
	public static final String KEY_RESULT_FILE_PATH = "resultFilePath";
	public static final String KEY_NAME_FILE_PATH = "nameFilePath";
	public static final String KEY_MINIMUM_RACE_DURATION = "minimumRaceDuration";
	
	public Configuration() throws IOException {
		this (DEFAULT_PROPERTIES_FILE);
	}
	public Configuration(File propertiesFile) throws IOException {
		propertiesFile.getParentFile().mkdirs();
		if (propertiesFile.exists())
		{
			FileInputStream in = new FileInputStream(propertiesFile);
			load(in);
			in.close();
		}
		else
		{
			put(KEY_NAME_FILE_PATH, "data/namn.txt");
			put(KEY_RESULT_FILE_PATH, "data/result.txt");
			put(KEY_GUI_OUTPUT_FILE_PATH, "data/utdata.txt");
			put(KEY_RACE_TYPE, VALUE_RACE_MARATHON);
			put(KEY_MINIMUM_RACE_DURATION, "00.00.00");
			
			FileOutputStream out = new FileOutputStream(propertiesFile);
			store(out, generateComment());
			out.close();
		}
	}

	private String generateComment() {
		return "Enduro config file\n\n"
				+ "Valid race types:\n"
				+ " - " + VALUE_RACE_MARATHON + "\n"
				+ " - " + VALUE_RACE_LAPS + "\n";
	}
}
