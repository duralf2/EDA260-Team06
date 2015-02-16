package sorter;

import io.ReadFile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import register.model.Contestant;
import register.model.DataStructure;
import register.model.Time;
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
            ReadFile.readFinishTime(files[i], ds, new Time("00.55.00"));
        }
        
        ReadFile.readNames(nameFile, ds);

		// TODO: change this file to be a parameter for the function
		if (!new File("data").isDirectory())
            // Create the data directory if it doesn't exist
			new File("data").mkdir();

		File resultFile = new File("data/results.txt");
		writeToFile(resultFile);
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
    
    private List<Contestant> sortContestantsByTotal(List<Contestant> contestants) {
        Collections.sort(contestants,
                new Comparator<Contestant>() {
                    @Override
                    public int compare(Contestant c1, Contestant c2) {
                        return c1.getTotalTime().compareTo(c2.getTotalTime());
                    }
                });

        return contestants;
    }

    private List<Contestant> sortContestantsByLaps(List<Contestant> contestants) {
        // This should be used in another public sort method
        List<List<Contestant>> al = new ArrayList<List<Contestant>>();
        for(int i=0; i <= ds.getMaxLaps(); i++) {
            al.add(new ArrayList<Contestant>());
        }

        for(Contestant c : contestants) {
            al.get(c.getLapsCompleted()).add(c);
        }
        
        List<Contestant> result = new ArrayList<Contestant>();
        for(int i=al.size()-1; i>=0; i--) {
            List<Contestant> a = al.get(i);
            a = sortContestantsByTotal(a);
            result.addAll(a);
        }

        return result;
    }

	// private method for writing to file
	private void writeToFile(File resultFile) {
		try {
			if (!new File("data").isDirectory())
				new File("data").mkdir();//create the data directory if not exists
			resultFile.createNewFile();

			PrintWriter pw = new PrintWriter(resultFile);

            List<Contestant> contestants;
            Map<String, ArrayList<Contestant>> groupedContestants = groupByClass(ds);
            for(String cls : groupedContestants.keySet()) {
                if(hasClasses(ds)) {
                    pw.write(cls + "\n");
                }
                contestants = sortContestantsByLaps(groupedContestants.get(cls));
                writeContestantRows(contestants, pw);
            }
            pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    private void writeHeader(PrintWriter pw) {
        String colhead = "Placering; StartNr; Namn; Totaltid; Starttid; ";
        for(int i=1; i<ds.getMaxLaps(); i++) {
            colhead += "Varv" + i + "; ";
        }
        colhead += "Måltid\n";
        pw.write(colhead);
    }

    private void writeContestantRows(List<Contestant> result, PrintWriter pw) {
        writeHeader(pw);
        int pos = 1;
        for(Contestant c : result) {
            writeContestantRow(c, pw, pos);
            pos++;
        }
    }

    private void writeContestantRow(Contestant c, PrintWriter pw, int position) {
        String pos = c.getFinishTimes().size() > 0 ? "" + position : "";
        String out = pos + ";" + c.getStartNumber() + ";" + c.getName() + ";"
                + c.getTotalTime() + ";" + c.getStartTime() + ";";

        List<Time> lapTimes = c.getLapTimes();
        for(int lap=0; lap < ds.getMaxLaps()-1; lap++) {
            if(lap < c.getLapsCompleted()) {
                out += lapTimes.get(lap).toString();
            }
            out += ";";
        }
        out += c.getFinishTime() + "\n";
        pw.write(out.replace(";", "; "));
    }

    // TODO: Move to DataStructure/Contest, make public
    private boolean hasClasses(DataStructure ds) {
        boolean hasClass = false;
        for(Contestant c : ds.getAllContestantEntries().values()) {
            if(!c.getClassName().equals("")) {
                hasClass = true;
                break;
            }
        }
        return hasClass;
    }
}
