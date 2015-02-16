package sorter;

import io.ReadFile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import register.model.Contestant;
import register.model.DataStructure;
import tests.ContestantTest;

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
        
        ReadFile.readNames(nameFile, ds);

		// TODO: change this file to be a parameter for the function
		if (!new File("data").isDirectory())
            // Create the data directory if it doesn't exist
			new File("data").mkdir();


		ArrayList<Contestant> result = sortContestantsByTotal(ds);
		File resultFile = new File("data/results.txt");
		writeToFile(result, resultFile);
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
    
    private ArrayList<Contestant> sortContestantsByTotal(DataStructure ds) {
        final Map<String, Contestant> contestants = ds.getAllContestantEntries();

        ArrayList<Contestant> result = new ArrayList<Contestant>(contestants.values());
        Collections.sort(result,
                new Comparator<Contestant>() {
                    @Override
                    public int compare(Contestant c1, Contestant c2)
                    {
                        return  c1.getTotalTime().compareTo(c2.getTotalTime());
                    }
                });

        return result;
    }

    private ArrayList<Contestant> sortContestantsByLaps(DataStructure ds) {
        // This should be used in another public sort method
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
	private void writeToFile(ArrayList<Contestant> result, File resultFile) {
		try {
			if (!new File("data").isDirectory())
				new File("data").mkdir();//create the data directory if not exists
			resultFile.createNewFile();

			PrintWriter pw = new PrintWriter(resultFile);
			int position = 1;

			pw.write("Placering; StartNr; Namn; Totaltid; Starttid; Måltid\n");
			for (Contestant c : result) {
				String out = position + ";" + c.getStartNumber() + ";" + c.getName() + ";"
						+ c.getTotalTime() + ";" + c.getStartTime()
						+ ";" + c.getFinishTime() + "\n";
                pw.write(out.replace(";", "; "));
				position++;
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
