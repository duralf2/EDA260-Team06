package sorter;

import register.gui.RegistrationGUI;
import register.logic.Register;
import register.model.DataStructure;

public class SorterMain {

	public static void main(String[] args) {
		new RegistrationGUI("", new Register(new DataStructure()));
	}

}
