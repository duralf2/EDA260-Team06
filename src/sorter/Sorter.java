package sorter;

import io.ReadFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import register.model.Contestant;
import register.model.DataStructure;
import register.model.Time;

public class Sorter {
	DataStructure ds;

	public Sorter(DataStructure ds) {
		this.ds = ds;
	}

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
		
		
	}
		
		//TODO Skriv ut det i fönstret, eller i fil. 
		// 1) clear ds
		// 2) LADDA IN FILER
		// 3) LÄGG IN I TREE MAP
		// 4) SKRIV UT TREE MAP
		

}
