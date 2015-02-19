package register.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

import register.logic.TimeRegistrationHandler;
import register.model.ContestantTimes;

/**
 * This class starts the registration process.
 */
public class RegistrationStarter {
	private static final String DEFAULT_REGISTRATION_PROPERTIES = "RegistrationData/registration.properties";
	private Properties defaultProperties;

	/**
	 * Main method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new RegistrationStarter();
	}

	/**
	 * Constructor for <code>RegistrationStarter</code>.
	 */
	public RegistrationStarter() {
		loadProperties();
		startRegistration();
	}

	private void loadProperties() {
		defaultProperties = new Properties();
		try (FileInputStream in = new FileInputStream(
				DEFAULT_REGISTRATION_PROPERTIES);) {
			defaultProperties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Could not find properties file: "
							+ DEFAULT_REGISTRATION_PROPERTIES
							+ "\nClosing registration system", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	private void startRegistration() {
		File nameFile = new File(defaultProperties.getProperty("nameFile"));
		File timeFile = new File(defaultProperties.getProperty("timeFile"));
		String title = defaultProperties.getProperty("title");
		try {
			createFilesAndDirectories(nameFile, timeFile);
			ContestantTimes times = new ContestantTimes(nameFile, timeFile);
			TimeRegistrationHandler registrationHandler = new TimeRegistrationHandler(
					times);
			new RegistrationGUI(title, registrationHandler);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Could not load files:"
					+ "\nClosing registration system", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	// /**
	// * Creates all necessary files and directories to use the time and name
	// * files specified in the properties file.
	// *
	// * @param nameFile The file with names and startnumbers of
	// * <code>contestants</code>
	// * @param timeFile The file with the times belonging to the
	// * <code>contestants</code>
	// * @throws IOException
	// */
	// TODO Javadoc för privata methoder?
	private void createFilesAndDirectories(File nameFile, File timeFile)
			throws IOException {
		nameFile.getParentFile().mkdirs();
		nameFile.createNewFile();
		timeFile.getParentFile().mkdirs();
		timeFile.createNewFile();

	}

}
