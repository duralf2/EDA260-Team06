package sorter.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import sorter.SorterMain;

/**
 * Contains standard keys for different settings.
 */
public class Configuration extends Properties {

	public static final String VALUE_RACE_MARATHON = "marathon";
	public static final String VALUE_RACE_LAPS = "laps";

	public static final String VALUE_FORMAT_CSV = "csv";
	public static final String VALUE_FORMAT_HTML = "html";
	
	private static final String DEFAULT_PROPERTIES_FILE = "data/config.ini"; 

	 // TODO Configuration; Ändra så att startTimeFilePath och finishTimeFilePath har Folder istället för File i namnet
	 // ALLA MÅSTE TA BORT SINA CONFIG-FILER OM DETTA ÄNDRAS! Uppdatera även filerna i testfiles/config/
	public static final String KEY_RACE_TYPE = "raceType";	
	public static final String KEY_START_TIME_FOLDER_PATH = "startTimeFilePath";
	public static final String KEY_FINISH_TIME_FOLDER_PATH = "finishTimeFilePath";
	public static final String KEY_RESULT_FILE_PATH = "resultFilePath";
	public static final String KEY_NAME_FILE_PATH = "nameFilePath";
	
	public static final String KEY_SHORTEST_POSSIBLE_TIME = "shortestPossibleTime";
	public static final String KEY_START_TIME_LIMIT = "startTimeLimit";
	
	public static final String KEY_TIME_FILE_PATH = "timeFilePath";
	public static final String KEY_GUI_TITLE = "guiTitle";
	
//	public static final String KEY_STAGE_AMOUNT = "stageAmount";
//	public static final String KEY_STAGE_NAMES = "stageNames"; //stageNames=etapp1,etapp2,etapp3
//	public static final String KEY_SPECIAL_STAGE_NAMES = "specialStageNames"; //specialStageNames=etapp1*2
	
	public static final String KEY_RESULT_FORMAT = "resultFormat"; //CSV or HTML
	public static final String KEY_RESULT_SORTED = "resultSorted"; //true or false
	
	public Configuration() throws IOException {
		// Set the working directory of the program to the folder containing the program.
		// If you double-click a jar-file in linux the working directory is set to the user home by default.
		// We want it to be set to the folder of the program, therefore these lines are necessary
		File workingDirectory = new File(SorterMain.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile();
		File defaultProperties = new File(workingDirectory, DEFAULT_PROPERTIES_FILE);
		
		init(defaultProperties);
	}
	/**
	 * Creates a new Properties while passing the path to the config file as a parameter.
	 * if the config file exists it will read the contents, otherwise it will create a new file
	 * and add some default configuration values.
	 * @param propertiesFile the absolute pathname to the config file
	 * @throws IOException if the config file could not be read.
	 */
	public Configuration(File propertiesFile) throws IOException {
		init(propertiesFile);
	}
	private void init(File propertiesFile) throws FileNotFoundException,
			IOException {
		File parentFile = propertiesFile.getParentFile();
		if(parentFile!=null){
			propertiesFile.getParentFile().mkdirs();
		}
		if (propertiesFile.exists())
		{
			FileInputStream in = new FileInputStream(propertiesFile);
			load(in);
			in.close();
		}
		else
		{
			put(KEY_NAME_FILE_PATH, "data/namn.txt");
			put(KEY_START_TIME_FOLDER_PATH, "data/starttimes/");
			put(KEY_FINISH_TIME_FOLDER_PATH, "data/finishtimes/");
			put(KEY_RESULT_FILE_PATH, "data/result.txt");
			
			put(KEY_RACE_TYPE, VALUE_RACE_MARATHON);
			put(KEY_SHORTEST_POSSIBLE_TIME, "00.15.00");
			put(KEY_START_TIME_LIMIT, "01.00.00");
			
			put(KEY_RESULT_FORMAT, VALUE_FORMAT_CSV);
			put(KEY_RESULT_SORTED, "false");
			
			FileOutputStream out = new FileOutputStream(propertiesFile);
			store(out, generateComment());
			out.close();
		}
	}
	

	private String generateComment() {
		return "Enduro config file\n\n"
				+ "Valid race types:\n"
				+ " - " + VALUE_RACE_MARATHON + "\n"
				+ " - " + VALUE_RACE_LAPS + "\n"
				+ "\n"
				+ "Valid result formats:\n"
				+ " - " + VALUE_FORMAT_CSV + "\n"
				+ " - " + VALUE_FORMAT_HTML + " (not supported yet)\n";
	}
}
