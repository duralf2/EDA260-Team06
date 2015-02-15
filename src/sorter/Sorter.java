package sorter;

import io.FileWriter;
import io.ReadFile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import register.model.AbstractContestant;
import register.model.Database;
import register.model.Time;

/**
 * This class is responsible for loading the data collected during the contests
 * and compile it into sorted result files.
 */
public class Sorter {
	Database db;

	public Sorter(Database db) {
		this.db = db;
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
		db.clearContestantEntries();

        ReadFile.readStartTime(files[0], db);
        for(int i=1; i < files.length; i++) {
            ReadFile.readFinishTime(files[i], db);
        }

		// TODO: change this file to be a parameter for the function
		if (!new File("data").isDirectory())
            // Create the data directory if it doesn't exist
			new File("data").mkdir();


		ArrayList<AbstractContestant> result = sortContestants(db);
		File resultFile = new File("data/results.txt");
		writeToFile(result, resultFile, nameFile);
	}

    private Map<String, ArrayList<AbstractContestant>> groupByClass(Database db) {
        HashMap<String, ArrayList<AbstractContestant>> m = new HashMap<String, ArrayList<AbstractContestant>>();

        for(AbstractContestant c : db.getAllContestantEntries().values()) {
            if (!m.containsKey(c.getClassName())) {
                m.put(c.getClassName(), new ArrayList<AbstractContestant>());
            }

            m.get(c.getClassName()).add(c);
        }

        return m;
    }

    private ArrayList<AbstractContestant> sortContestants(Database db) {
        Map<String, AbstractContestant> contestants = db.getAllContestantEntries();

        ArrayList<ArrayList<AbstractContestant>> al = new ArrayList<ArrayList<AbstractContestant>>();
        for(int i=0; i <= db.getMaxLaps(); i++) {
            al.add(new ArrayList<AbstractContestant>());
        }

        for(AbstractContestant c : contestants.values()) {
            al.get(c.getLapsCompleted()).add(c);
        }
        ArrayList<AbstractContestant> result = new ArrayList<AbstractContestant>();
        for(ArrayList<AbstractContestant> a : al) {
            result.addAll(a);
        }

        return result;
    }

	// private method for writing to file
	private void writeToFile(ArrayList<AbstractContestant> result, File resultFile,
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
			for (AbstractContestant c : result) {
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
