package sorter;

import java.io.File;
import java.io.IOException;

import register.model.Database;

public class SorterMain {

	public static void main(String[] args) throws IOException {
		// TODO Refactor; Anropa sortering i main
		Sorter s = new Sorter(new Database());
		File[] files = {new File("testfiles/acceptanstest/Iteration2/acceptanstest18/starttider.txt"),
                        new File("testfiles/acceptanstest/Iteration2/acceptanstest18/maltider1.txt"),
                        new File("testfiles/acceptanstest/Iteration2/acceptanstest18/maltider2.txt")};
		//s.sort(files, new File("testfiles/acceptanstest/Iteration2/acceptanstest18/namnfil.txt"));
	}

}
