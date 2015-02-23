package sorter;

import io.FileReader;
import io.FileWriter;

import java.io.File;
import java.io.IOException;

import sorter.model.Configuration;
import sorter.model.ContestantFactory;
import sorter.model.Database;

public class SorterMain {

	public static void main(String[] args) throws IOException { // TODO SorterMain; Testa den här klassen?
		
		// Set the working directory of the program to the folder containing the program.
		// If you double-click a jar-file in linux the working directory is set to the user home by default.
		// We want it to be set to the folder of the program, therefore these lines are necessary
		File path = new File(SorterMain.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		System.setProperty("user.dir", path.getParent());

		Configuration config = new Configuration();
		
		File nameFile = new File(config.getProperty(Configuration.KEY_NAME_FILE_PATH));
		File startTimeFolder = new File(config.getProperty(Configuration.KEY_START_TIME_FOLDER_PATH));
		File finishTimeFolder = new File(config.getProperty(Configuration.KEY_FINISH_TIME_FOLDER_PATH));
		File resultFile = new File(config.getProperty(Configuration.KEY_RESULT_FILE_PATH));

		boolean sortResults = Boolean.parseBoolean(config.getProperty(Configuration.KEY_RESULT_SORTED, "false"));
		if (sortResults) {
			sort(config, nameFile, startTimeFolder, finishTimeFolder);
		} else {
			noSort(config, nameFile, startTimeFolder, finishTimeFolder, resultFile);
		}
	}

	private static void sort(Configuration config, File nameFile,
			File startTimeFolder, File finishTimeFolder) throws IOException {
		Database db = new Database();
		Sorter sorter = new Sorter(db, config);
		sorter.sort(nameFile, startTimeFolder.listFiles(), finishTimeFolder.listFiles());
	}

	private static void noSort(Configuration config, File nameFile,
			File startTimeFolder, File finishTimeFolder, File resultFile) throws IOException {

		Database db = new Database();
		ContestantFactory cf = new ContestantFactory(config);
		FileReader reader = new FileReader(cf);
		reader.readNames(nameFile, db);
		if (startTimeFolder.isDirectory()) {
			for (File file : startTimeFolder.listFiles()) {
				reader.readStartTime(file, db);
			}
		}
		if (finishTimeFolder.isDirectory()) {
			for (File file : finishTimeFolder.listFiles()) {
				reader.readFinishTime(file, db);
			}
		}
		
		FileWriter writer = new FileWriter(resultFile);
		writer.writeResults(config, db);
	}
}
