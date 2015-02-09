package sorter;

import io.FileWriter;
import io.ReadFile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

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
		ds.clearContestantEntries();

        ReadFile.readStartTime(files[0], ds);
        for(int i=1; i < files.length; i++) {
            ReadFile.readFinishTime(files[i], ds);
        }

		// TODO: change this file to be a parameter for the function
		if (!new File("data").isDirectory())
            // Create the data directory if it doesn't exist
			new File("data").mkdir();


		ArrayList<Contestant> result = sortContestants(ds);
		File resultFile = new File("data/results.txt");
		writeToFile(result, resultFile, nameFile);
	}

    private Map<String, ArrayList<Contestant>> groupByClass(DataStructure ds) {
        HashMap<String, ArrayList<Contestant>> m = new HashMap<String, ArrayList<Contestant>>();

        for(Contestant c : ds.getAllContestantEntries().values()) {
            if (!m.containsKey(c.getClassName())) {
                m.put(c.getClassName(), new ArrayList<Contestant>());
            }

            m.get(c.getClassName()).add(c);
        }

        return m;
    }

    private ArrayList<Contestant> sortContestants(DataStructure ds) {
        Map<String, Contestant> contestants = ds.getAllContestantEntries();

        ArrayList<ArrayList<Contestant>> al = new ArrayList<ArrayList<Contestant>>();
        for(int i=0; i <= ds.getMaxLaps(); i++) {
            al.add(new ArrayList<Contestant>());
        }

        for(Contestant c : contestants.values()) {
            al.get(c.getLapsCompleted()).add(c);
        }
        ArrayList<Contestant> result = new ArrayList<Contestant>();
        for(ArrayList<Contestant> a : al) {
            result.addAll(a);
        }

        return result;
    }

	// private method for writing to file
	private void writeToFile(ArrayList<Contestant> result, File resultFile,
			File nameFile) {
		try {
			if (!new File("data").isDirectory())
				new File("data").mkdir();//create the data directory if not exists
			resultFile.createNewFile();

			PrintWriter pw = new PrintWriter(resultFile);
			int i = 1;
			List<String[]> startNumbers = ReadFile.readCSV(nameFile);
            for(String[] s : startNumbers) {
                System.out.println(s[0]);
            }

			pw.write("Placering; StartNr; Namn; Totaltid; Starttid; Måltid\n");
			for (Contestant c : result) {
				String s = "";
				for (String[] s1 : startNumbers) {
					if (s1[1].trim().equalsIgnoreCase(c.getName()))
						s = s1[0];
				}
				String out = i + ";" + s + ";" + c.getName() + ";"
						+ c.getTotalTime() + ";" + c.getStartTime()
						+ ";" + c.getFinishTime() + "\n";
                pw.write(out.replace(";", "; "));
				i++;
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
