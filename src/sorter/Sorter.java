package sorter;

import io.ReadFile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

import register.model.AbstractContestant;
import register.model.Contestant;
import register.model.Database;
import register.model.Time;

/**
 * This class is responsible for loading the data collected during the contests
 *  and compile it into sorted result files.
 */
public class Sorter {
	Database db;

	public Sorter(Database db) {
		this.db = db;
	}

	/**
	 * Reads and sorts the collected data. After the data is sorted it is printed
	 *  to a results file.  
	 * @param files All the files containing finish times
	 * @throws IOException If any of the files doesn't exist or couldn't be closed
	 */
	public void sortTime(File[] files) throws IOException {
		TreeMap<Time, AbstractContestant> results = new TreeMap<Time, AbstractContestant>();
		
		db.clearContestantEntries();
		
		ReadFile.readNames(new File("testfiles/namn.txt"), db);
		ReadFile.readStartTime(files[0], db);
		ReadFile.readFinishTime(files[1], db);
		
		Map<String,AbstractContestant> contestants = db.getAllContestantEntries();
		for(String startNumber : contestants.keySet()){
			AbstractContestant con = contestants.get(startNumber);
			results.put(con.getTotalTime(), con);
		}
		
		int i = 1;
		for(Time t : results.keySet()){
			System.out.println(i + ") " + t.toString() + " : " + results.get(t).getInformation("Namn"));
			i++;
		}
		//TODO: change this file to be a parameter for the function
		File resultFile = new File("testfiles/results.txt");
		writeToFile(results, resultFile);
	}
	
	//private method for writing to file
	private void writeToFile(TreeMap<Time, AbstractContestant> result, File resultFile) {
		try {
			if(! resultFile.exists()){
				resultFile.createNewFile();
			}
			PrintWriter pw = new PrintWriter(resultFile);
			for (Time t: result.keySet()) {
				pw.write(result.get(t).getInformation("Namn") + "; " + t.toString() + "\n");
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
		//TODO Skriv ut det i fönstret, eller i fil. 
		// 1) clear ds
		// 2) LADDA IN FILER
		// 3) LÄGG IN I TREE MAP
		// 4) SKRIV UT TREE MAP
}
