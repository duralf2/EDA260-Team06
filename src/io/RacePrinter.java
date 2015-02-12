package io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RacePrinter {
	
	private File target;
	
	public RacePrinter(String targetPath) {
		this.target = new File(targetPath);
	}
	
	public void print(String data) throws IOException
	{
		if (data != null)
		{
			File parentFile = target.getParentFile();
			if (parentFile != null)
				parentFile.mkdirs();
			FileWriter out = new FileWriter(target);
			out.write(data);
			out.close();
		}
		else
		{
			throw new IllegalArgumentException("Can't print null!");
		}
	}
}
