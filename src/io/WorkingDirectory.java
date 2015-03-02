package io;

import gui.RegistrationStarter;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class WorkingDirectory {
	public File getDirectory()
	{
		try
		{
			// Set the working directory of the program to the folder containing the program.
			// If you double-click a jar-file in linux the working directory is set to the user home by default.
			// We want it to be set to the folder of the program, therefore these lines are necessary
			URL path = RegistrationStarter.class.getProtectionDomain().getCodeSource().getLocation();
			File workingDirectory = new File(path.toURI()).getParentFile();
			System.setProperty("user.dir", workingDirectory.getParent());
			return workingDirectory;
		} catch (URISyntaxException e)
		{
			return new File("");
		}
	}
}
