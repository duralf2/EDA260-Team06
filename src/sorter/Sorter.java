package sorter;

import io.ReadFile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

import register.model.Contestant;
import register.model.DataStructure;
import register.model.Time;

/**
 * This class is responsible for loading the data collected during the contests
 *  and compile it into sorted result files.
 */
public class Sorter {
	DataStructure ds;

	public Sorter(DataStructure ds) {
		this.ds = ds;
	}

	/**
	 * Reads and sorts the collected data. After the data is sorted it is printed
	 *  to a results file.  
	 * @param files All the files containing finish times
	 * @throws IOException If any of the files doesn't exist or couldn't be closed
	 */
	public void sortTime(File[] files) throws IOException {
		TreeMap<Time, Contestant> results = new TreeMap<Time, Contestant>();
		
		ds.clearContestantEntries();
		
		ReadFile.readNames(new File("testfiles/namn.txt"), ds);
		for (File file : files) {
			ReadFile.readFinishTime(file, ds);
		}
		
		Map<String,Contestant> contestants = ds.getAllContestantEntries();
		for(String startNumber : contestants.keySet()){
			Contestant con = contestants.get(startNumber);
			results.put(con.getFinishTime(), con);
		}
		
		int i = 1;
		for(Time t : results.keySet()){
			System.out.println(i + ") " + t.toString() + " : " + results.get(t).getName());
			i++;
		}
		//TODO: change this file to be a parameter for the function
		File resultFile = new File("testfiles/results.txt");
		writeToFile(results, resultFile);
	}
	
	//private method for writing to file
	private void writeToFile(TreeMap<Time, Contestant> result, File resultFile) {
		try {
			if(! resultFile.exists()){
				resultFile.createNewFile();
			}
			PrintWriter pw = new PrintWriter(resultFile);
			for (Time t: result.keySet()) {
				pw.write(result.get(t).getName() + "; " + t.toString() + "\n");
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
