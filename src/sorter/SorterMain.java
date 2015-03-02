package sorter;

import io.FileReader;
import io.FileWriter;
import io.WorkingDirectory;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import sorter.model.Configuration;
import sorter.model.ContestantFactory;
import sorter.model.Database;

/**
 * Handles the sorting process.
 */
public class SorterMain {

	public static void main(String[] args) { // TODO SorterMain; Testa den här
												// klassen?

		boolean succeeded = true;
		try {
			runProgram();
		} catch (Throwable e) {
			succeeded = false;
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Exception occured: " + e.getMessage(),
					"Exception occured", JOptionPane.ERROR_MESSAGE);
		}

		if (succeeded)
			JOptionPane.showMessageDialog(null, "Result file generated!",
					"Done", JOptionPane.INFORMATION_MESSAGE);
	}

	private static void runProgram() throws IOException {
		File workingDirectory = new WorkingDirectory().getDirectory();

		Configuration config = new Configuration();

		File nameFile = new File(workingDirectory,
				config.getProperty(Configuration.KEY_NAME_FILE_PATH));
		File startTimeFolder = new File(workingDirectory,
				config.getProperty(Configuration.KEY_START_TIME_FOLDER_PATH));
		File finishTimeFolder = new File(workingDirectory,
				config.getProperty(Configuration.KEY_FINISH_TIME_FOLDER_PATH));
		File resultFile = new File(workingDirectory,
				config.getProperty(Configuration.KEY_RESULT_FILE_PATH));

		boolean sortResults = Boolean.parseBoolean(config.getProperty(
				Configuration.KEY_RESULT_SORTED, "false"));
		if (sortResults) {
			sort(config, nameFile, startTimeFolder, finishTimeFolder);
		} else {
			noSort(config, nameFile, startTimeFolder, finishTimeFolder,
					resultFile);
		}
	}

	private static void sort(Configuration config, File nameFile,
			File startTimeFolder, File finishTimeFolder) throws IOException {
		Database db = new Database();
		SorterSetUp sorter = new SorterSetUp(db, config);
		sorter.sort(nameFile, getFilesInDirectory(startTimeFolder),
				getFilesInDirectory(finishTimeFolder));
	}

	// used in SorterMainTest
	public static void noSort(Configuration config, File nameFile,
			File startTimeFolder, File finishTimeFolder, File resultFile)
			throws IOException {

		Database db = new Database();
		ContestantFactory cf = new ContestantFactory(config);
		FileReader reader = new FileReader(cf);
		reader.readNames(nameFile, db);
		File[] startTimeFiles = getFilesInDirectory(startTimeFolder);
		for (File f : startTimeFiles) {
			reader.readStartTime(f, db);
		}
		File[] finishTimeFiles = getFilesInDirectory(finishTimeFolder);
		for (File f : finishTimeFiles) {
			reader.readFinishTime(f, db);
		}
		FileWriter writer = new FileWriter(resultFile);
		writer.writeResults(config, db, false);
	}

	private static File[] getFilesInDirectory(File timeFileDirectory) {
		if (timeFileDirectory.isDirectory()) {
			LinkedList<File> files = new LinkedList<File>();
			for (File file : timeFileDirectory.listFiles()) {
				if (file.getName().charAt(0) != '.') {
					files.add(file);
				}
			}
			return files.toArray(new File[files.size()]);
		}
		return new File[0];
	}
}
