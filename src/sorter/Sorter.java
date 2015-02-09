package sorter;

import io.ReadFile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import register.model.Contestant;
import register.model.DataStructure;
import register.model.Time;

/**
 * This class is responsible for loading the data collected during the contests
 * and compile it into sorted result files.
 */
public class Sorter {
	DataStructure ds;

	public Sorter(DataStructure ds) {
		this.ds = ds;
	}

	/**
	 * Reads and sorts the collected data. After the data is sorted it is
	 * printed to a results file.
	 * 
	 * @param files
	 *            All the files containing finish times
	 * @param nameFile
	 *            TODO
	 * @throws IOException
	 *             If any of the files doesn't exist or couldn't be closed
	 */
	public void sortTime(File[] files, File nameFile) throws IOException {
		TreeMap<Time, Contestant> results = new TreeMap<Time, Contestant>();

		ds.clearContestantEntries();
		if (nameFile == null)
			ReadFile.readNames(new File("testfiles/namn.txt"), ds);
		else
			ReadFile.readNames(nameFile, ds);
		ReadFile.readStartTime(files[0], ds);
		ReadFile.readFinishTime(files[1], ds);

		Map<String, Contestant> contestants = ds.getAllContestantEntries();
		for (String startNumber : contestants.keySet()) {
			Contestant con = contestants.get(startNumber);
			results.put(new Time(con.getTotalTime()), con);
		}
		// TODO: change this file to be a parameter for the function
		File resultFile = new File("data/results.txt");
		writeToFile(results, resultFile, nameFile);
	}

	// private method for writing to file
	private void writeToFile(TreeMap<Time, Contestant> result, File resultFile,
			File nameFile) {
		try {
			resultFile.createNewFile();
			PrintWriter pw = new PrintWriter(resultFile);
			int i = 1;
			List<String[]> startNumbers;
			if (nameFile != null)
				startNumbers = ReadFile.readCSV(nameFile);
			else
				startNumbers = ReadFile.readCSV(new File("testfiles/namn.txt"));
			pw.write("Placering; StartNr; Namn; Totaltid\n");
			for (Time t : result.keySet()) {
				String s = "";
				for (String[] s1 : startNumbers) {
					if (s1[1].trim().equalsIgnoreCase(result.get(t).getName()))
						s = s1[0];
				}
				pw.write(i + "; " + s + "; " + result.get(t).getName() + "; "
						+ t.toString() + "\n");
				i++;
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// TODO Skriv ut det i fönstret, eller i fil.
	// 1) clear ds
	// 2) LADDA IN FILER
	// 3) LÄGG IN I TREE MAP
	// 4) SKRIV UT TREE MAP
}
