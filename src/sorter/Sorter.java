package sorter;

import io.FileReader;
import io.FileWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;

import register.model.AbstractContestant;
import register.model.Configuration;
import register.model.ContestantFactory;
import register.model.Database;

/**
 * This class is responsible for loading the data collected during the contests
 * and compile it into sorted result files.
 */
public class Sorter {
	private Database db;
	private Configuration conf;
	private FileWriter fileWriter;
	private Map<String, AbstractContestant> contestants;

	public Sorter(Database db) throws IOException {
		this.db = db;
		conf = new Configuration();
	}

	public Sorter(Database db, Configuration conf) throws IOException {
		this.db = db;
		this.conf = conf;
		fileWriter = new FileWriter(
				conf.getProperty(Configuration.KEY_RESULT_FILE_PATH));
	}

	public void sortLapTimes(String nameFile, File startTime, File[] finishTimes) throws IOException {
		setUp(nameFile, startTime, finishTimes);
		
		ArrayList<AbstractContestant> list = new ArrayList<AbstractContestant>();
		for( AbstractContestant c : contestants.values()) {
			list.add(c);
		}
		fileWriter.writeSortedResult(list, conf, db);
	}
	
	

	public void setUp(String nameFile, File startTime, File[] finishTimes)
			throws IOException {
		if (!new File("data").isDirectory())
			// Create the data directory if it doesn't exist
			new File("data").mkdir();
		
		db.clearContestantEntries();

		ContestantFactory factory = new ContestantFactory(conf);
		
		FileReader read = new FileReader(factory);

		read.readNames(new File(nameFile), db);
		
		read.readStartTime(startTime, db);
		for (int i = 0; i < finishTimes.length; i++) {
			read.readFinishTime(finishTimes[i], db);
		}

		 contestants = db.getAllContestantEntries();
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
	public void sort(String nameFile, File startTime, File[] finishTimes)
			throws IOException {
		
		setUp(nameFile, startTime, finishTimes);


		LinkedList<AbstractContestant> sortedContestants = new LinkedList<AbstractContestant>();
		for (AbstractContestant c : contestants.values()) {
			sortedContestants.add(c);
		}
		Collections.sort(sortedContestants);

		
		fileWriter.writeResultList(sortedContestants, conf, db);
	}
	
//	public void sortTime(File[] files, File nameFile) throws IOException {
//		ds.clearContestantEntries();
//
//        ReadFile.readStartTime(files[0], ds);
//        for(int i=1; i < files.length; i++) {
//            ReadFile.readFinishTime(files[i], ds, new Time("00.55.00"));
//        }
//        
//        ReadFile.readNames(nameFile, ds);
//
//		// TODO: change this file to be a parameter for the function
//		if (!new File("data").isDirectory())
//            // Create the data directory if it doesn't exist
//			new File("data").mkdir();
//
//		File resultFile = new File("data/results.txt");
//		writeToFile(resultFile);
//	}


//    private Map<String, ArrayList<Contestant>> groupByClass(DataStructure ds) {
//        HashMap<String, ArrayList<Contestant>> m = new HashMap<String, ArrayList<Contestant>>();
//
//        for(Contestant c : ds.getAllContestantEntries().values()) {
//            if (!m.containsKey(c.getClassName())) {
//                m.put(c.getClassName(), new ArrayList<Contestant>());
//            }
//
//            m.get(c.getClassName()).add(c);
//        }
//
//        return m;
//    }
//    
//    private List<Contestant> sortContestantsByTotal(List<Contestant> contestants) {
//        Collections.sort(contestants,
//                new Comparator<Contestant>() {
//                    @Override
//                    public int compare(Contestant c1, Contestant c2) {
//                        return c1.getTotalTime().compareTo(c2.getTotalTime());
//                    }
//                });
//
//        return contestants;
//    }
//
//    private List<Contestant> sortContestantsByLaps(List<Contestant> contestants) {
//        // This should be used in another public sort method
//        List<List<Contestant>> al = new ArrayList<List<Contestant>>();
//        for(int i=0; i <= ds.getMaxLaps(); i++) {
//            al.add(new ArrayList<Contestant>());
//        }
//
//        for(Contestant c : contestants) {
//            al.get(c.getLapsCompleted()).add(c);
//        }
//        
//        List<Contestant> result = new ArrayList<Contestant>();
//        for(int i=al.size()-1; i>=0; i--) {
//            List<Contestant> a = al.get(i);
//            a = sortContestantsByTotal(a);
//            result.addAll(a);
//        }
//
//        return result;
//    }
//
//	// private method for writing to file
//	private void writeToFile(File resultFile) {
//		try {
//			if (!new File("data").isDirectory())
//				new File("data").mkdir();//create the data directory if not exists
//			resultFile.createNewFile();
//
//			PrintWriter pw = new PrintWriter(resultFile);
//
//            List<Contestant> contestants;
//            Map<String, ArrayList<Contestant>> groupedContestants = groupByClass(ds);
//            for(String cls : groupedContestants.keySet()) {
//                if(hasClasses(ds)) {
//                    pw.write(cls + "\n");
//                }
//                contestants = sortContestantsByLaps(groupedContestants.get(cls));
//                writeContestantRows(contestants, pw);
//            }
//            pw.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//    private void writeHeader(PrintWriter pw) {
//        String colhead = "Placering; StartNr; Namn; #Varv; Totaltid; Starttid; ";
//        for(int i=1; i<ds.getMaxLaps(); i++) {
//            colhead += "Varv" + i + "; ";
//        }
//        if (ds.getMaxLaps() > 1) {
//            colhead += "Varv" + ds.getMaxLaps() + "\n";
//        } else {
//            colhead += "Måltid\n";
//        }
//        pw.write(colhead);
//    }
//
//    private void writeContestantRows(List<Contestant> result, PrintWriter pw) {
//        writeHeader(pw);
//        int pos = 1;
//        for(Contestant c : result) {
//            writeContestantRow(c, pw, pos);
//            pos++;
//        }
//    }
//
//    private void writeContestantRow(Contestant c, PrintWriter pw, int position) {
//        String pos = c.getFinishTimes().size() > 0 ? Integer.toString(position) : "";
//        String out = pos + ";" + c.getStartNumber() + ";" + c.getName() + ";"
//                + c.getLapsCompleted() + ";" + c.getTotalTime() + ";";
//
//        List<Time> lapTimes = c.getLapTimes();
//        Collections.sort(lapTimes, new Comparator<Time>() {
//            @Override
//            public int compare(Time t1, Time t2) {
//                return t1.compareTo(t2);
//            }
//        });
//        for(int lap=0; lap < ds.getMaxLaps(); lap++) {
//            if(lap <= c.getLapsCompleted()) {
//                Time startLapTime = lap - 1 >= 0 ? lapTimes.get(lap - 1) : c.getStartTime();
//                Time endLapTime = lap < lapTimes.size() ? lapTimes.get(lap) : c.getFinishTime();
//                if(!endLapTime.equals(new Time("00.00.00"))) {
//                    out += Time.getTotalTime(startLapTime, endLapTime).toString();
//                }
//            }
//            out += ";";
//        }
//        out = out.substring(0, out.length()-1);
//        out += "\n";
//        pw.write(out.replace(";", "; "));
//    }
//
//    // TODO: Move to DataStructure/Contest, make public
//    private boolean hasClasses(DataStructure ds) {
//        boolean hasClass = false;
//        for(Contestant c : ds.getAllContestantEntries().values()) {
//            if(!c.getClassName().equals("")) {
//                hasClass = true;
//                break;
//            }
//        }
//        return hasClass;
//    }
}
