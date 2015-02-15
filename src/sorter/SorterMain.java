package sorter;

import java.io.File;
import java.io.IOException;

import register.model.Database;

/**
 * This class is the entry point of the program, currently it only starts the gui.
 */
public class SorterMain { // TODO SorterMain should start the sorter and not the gui? Or maybe be called GUIMain?

	public static void main(String[] args) throws IOException {
		Sorter s = new Sorter(new Database());
		File[] files = {new File("testfiles/acceptanstest/Iteration2/acceptanstest18/starttider.txt"),
                        new File("testfiles/acceptanstest/Iteration2/acceptanstest18/maltider1.txt"),
                        new File("testfiles/acceptanstest/Iteration2/acceptanstest18/maltider2.txt")};
		s.sortTime(files, new File("testfiles/acceptanstest/Iteration2/acceptanstest18/namnfil.txt"));
	}

}
