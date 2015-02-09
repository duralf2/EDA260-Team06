package register.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class RaceProperties extends Properties {

	public static final String VALUE_RACE_MARATHON = "marathon";
	public static final String VALUE_RACE_LAPS = "laps";

	private static final File PROPERTIES_FILE = new File("data/config.ini"); 

	public static final String KEY_RACE_TYPE = "raceType";	
	public static final String KEY_GUI_OUTPUT_FILE_PATH = "guiOutputFilePath";
	public static final String KEY_RESULT_FILE_PATH = "resultFilePath";
	public static final String KEY_NAME_FILE_PATH = "nameFilePath";
	
	public RaceProperties() throws IOException {
		PROPERTIES_FILE.getParentFile().mkdirs();
		if (PROPERTIES_FILE.exists())
		{
			FileInputStream in = new FileInputStream(PROPERTIES_FILE);
			load(in);
			in.close();
		}
		else
		{
			put(KEY_NAME_FILE_PATH, "data/namn.txt");
			put(KEY_RESULT_FILE_PATH, "data/result.txt");
			put(KEY_GUI_OUTPUT_FILE_PATH, "data/utdata.txt");
			put(KEY_RACE_TYPE, VALUE_RACE_MARATHON);
			
			FileOutputStream out = new FileOutputStream(PROPERTIES_FILE);
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
