package sorter;

import java.io.IOException;

import register.gui.RegistrationGUI;
import register.logic.Register;
import register.model.DataStructure;

/**
 * This class is the entry point of the program, currently it only starts the gui.
 */
public class SorterMain { // TODO SorterMain should start the sorter and not the gui?

	public static void main(String[] args) throws IOException {
		new RegistrationGUI("Registration GUI", new Register(new DataStructure()));
//		Sorter s = new Sorter(new DataStructure());
//		File[] files = {new File("testfiles/test1.txt"),new File("testfiles/test2.txt")};
//		s.sortTime(files);
	}

}
