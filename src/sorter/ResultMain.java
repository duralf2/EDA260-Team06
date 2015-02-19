package sorter;

import java.io.File;

public class ResultMain {
	
	public static void main(String arg[]) { // TODO Refactor; Implementera main-metod
		if(arg.length < 3)
			throw new IllegalArgumentException("Need 3 files to read");
		File nameFile = new File(arg[0]);
		File startTimeFile = new File(arg[1]);
		File finishTimeFile = new File(arg[2]);
		/*Sorter sort = new Sorter(new Database());
		try {
			sort.sort(new File[]{startTimeFile, finishTimeFile}, nameFile);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
}
