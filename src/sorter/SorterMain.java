package sorter;

import java.io.File;
import java.io.IOException;

import register.gui.RegistrationGUI;
import register.logic.Register;
import register.model.DataStructure;

public class SorterMain {

	public static void main(String[] args) throws IOException {
		new RegistrationGUI("Registration GUI", new Register(new DataStructure()));
//		Sorter s = new Sorter(new DataStructure());
//		File[] files = {new File("testfiles/test1.txt"),new File("testfiles/test2.txt")};
//		s.sortTime(files);
	}

}
