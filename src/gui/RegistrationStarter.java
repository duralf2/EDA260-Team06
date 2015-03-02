package gui;

import gui.model.ContestantTimes;
import gui.model.FormatErrorHandler;
import gui.model.TimeRegistrationHandler;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

import sorter.model.Configuration;


/**
 * This class starts the registration process.
 */
public class RegistrationStarter {
	private static final String DEFAULT_REGISTRATION_DIRECTORY = "RegistrationData";
	private static final String DEFAULT_REGISTRATION_PROPERTIES = DEFAULT_REGISTRATION_DIRECTORY + "/registration.properties";
	private Properties defaultProperties;
	private File workingDirectory;

	public static void main(String[] args) throws URISyntaxException {
		new RegistrationStarter();
	}

	public RegistrationStarter() throws URISyntaxException {
		// Set the working directory of the program to the folder containing the program.
		// If you double-click a jar-file in linux the working directory is set to the user home by default.
		// We want it to be set to the folder of the program, therefore these lines are necessary
		URL path = RegistrationStarter.class.getProtectionDomain().getCodeSource().getLocation();
		workingDirectory = new File(path.toURI()).getParentFile();
		System.setProperty("user.dir", workingDirectory.getParent());
		
		UIManager.put("Table.gridColor", new ColorUIResource(Color.gray));
		loadProperties();
		startRegistration();
	}

	private void loadProperties() {
		defaultProperties = new Properties();
		File path = new File(workingDirectory, DEFAULT_REGISTRATION_DIRECTORY);
		File props = new File(workingDirectory, DEFAULT_REGISTRATION_PROPERTIES);
		
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
			FileInputStream in = new FileInputStream(new File(workingDirectory, DEFAULT_REGISTRATION_PROPERTIES));
			defaultProperties.load(in);
			if(defaultProperties.getProperty(Configuration.KEY_NAME_FILE_PATH) == null) {
				defaultProperties.setProperty(Configuration.KEY_NAME_FILE_PATH, "RegistrationData/namn.txt");
			}
			if(defaultProperties.getProperty(Configuration.KEY_TIME_FILE_PATH) == null) {
				defaultProperties.setProperty(Configuration.KEY_TIME_FILE_PATH, "RegistrationData/times.txt");
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
		File nameFile = new File(workingDirectory, defaultProperties.getProperty(Configuration.KEY_NAME_FILE_PATH));
		File timeFile = new File(workingDirectory, defaultProperties.getProperty(Configuration.KEY_TIME_FILE_PATH));
        try {
            nameFile.createNewFile();
            timeFile.createNewFile();
        } catch(IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Could not create name or time file"
                            + "\nClosing registration system", "Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

		ContestantTimes times = new ContestantTimes(nameFile, timeFile);
		if (FormatErrorHandler.getErrorCount() > 0) {
			JOptionPane.showMessageDialog(null, FormatErrorHandler.errorToString());
		}
		TimeRegistrationHandler registrationHandler = new TimeRegistrationHandler(
				times);
		new RegistrationGUI("Enduro Time Registration", registrationHandler);
	}
}
