package register.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class RaceProperties extends Properties {

	private static final File PROPERTIES_FILE = new File("data/config.ini"); 

	public static final String RACE_TYPE = "raceType";	
	public static final String GUI_OUTPUT_FILE_PATH = "guiOutputFilePath";
	public static final String RESULT_FILE_PATH = "resultFilePath";
	public static final String NAME_FILE_PATH = "nameFilePath";
	
	public RaceProperties() throws IOException {
		if (PROPERTIES_FILE.exists())
		{
			FileInputStream in = new FileInputStream(PROPERTIES_FILE);
			load(in);
			in.close();
		}
		else
		{
			put(NAME_FILE_PATH, "data/namn.txt");
			put(RESULT_FILE_PATH, "data/result.txt");
			put(GUI_OUTPUT_FILE_PATH, "data/utdata.txt");
			put(RACE_TYPE, "marathon");
			
			FileOutputStream out = new FileOutputStream(PROPERTIES_FILE);
			store(out, "Enduro config file\n");
			out.close();
		}
	}
}
