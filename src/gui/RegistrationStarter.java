package gui;

import gui.model.ContestantTimes;
import gui.model.TimeRegistrationHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Properties;

import javax.swing.JOptionPane;


/**
 * This class starts the registration process.
 */
public class RegistrationStarter {
	private static final String DEFAULT_REGISTRATION_DIRECTORY = "RegistrationData";
	private static final String DEFAULT_REGISTRATION_PROPERTIES = DEFAULT_REGISTRATION_DIRECTORY + "/registration.properties";
	private Properties defaultProperties;

	public static void main(String[] args) {
		new RegistrationStarter();
	}

	public RegistrationStarter() {
		loadProperties();
		startRegistration();
	}

	private void loadProperties() {
		defaultProperties = new Properties();
		File path = new File(DEFAULT_REGISTRATION_DIRECTORY);
		File props = new File(DEFAULT_REGISTRATION_PROPERTIES);
		System.out.println(path.exists() + " " + props.exists());
		
		if (!path.exists()) {
			path.mkdir();
		}
		if (!props.exists()) {
			try {
				props.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,
						"Could not create properties file: "
								+ DEFAULT_REGISTRATION_PROPERTIES
								+ "\nClosing registration system", "Error",
						JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
		}
		
		try {
			FileInputStream in = new FileInputStream(DEFAULT_REGISTRATION_PROPERTIES);
			defaultProperties.load(in);
			if(defaultProperties.getProperty("title") == null) {
				System.out.println("null1");
				defaultProperties.setProperty("title", "Enduro\\ Start/Finish\\ Time\\ Registration");
			}
			if(defaultProperties.getProperty("nameFile") == null) {
				defaultProperties.setProperty("nameFile", "RegistrationData/namn.txt");
			}
			if(defaultProperties.getProperty("timeFile") == null) {
				defaultProperties.setProperty("timeFile", "RegistrationData/times.txt");
			}
			defaultProperties.store(new FileOutputStream(props), null);
			in.close();
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

	/**
	 * Creates all necessary files and directories to use the time and name
	 * files specified in the properties file.
	 * 
	 * @param nameFile
	 * @param timeFile
	 * @throws IOException
	 */
	private void createFilesAndDirectories(File nameFile, File timeFile)
			throws IOException {
		nameFile.getParentFile().mkdirs();
		nameFile.createNewFile();
		timeFile.getParentFile().mkdirs();
		timeFile.createNewFile();
	}
}
