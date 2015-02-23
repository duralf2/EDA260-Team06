package gui.model;

import java.io.IOException;
import java.util.TreeSet;

import sorter.model.Configuration;

public class FormatErrorHandler {
	public static final int NAME = 0, TIME = 1;
	
	private static TreeSet<String> tree;
	private static Configuration conf;
	private static boolean initialized;
	
	public static void addError(int type, int line) {
		setUp();
		if (type == NAME) {
			tree.add("Format error in " + conf.getProperty(Configuration.KEY_NAME_FILE_PATH) + " at line " + line);
		} else if (type == TIME) {
			tree.add("Format error in " + conf.getProperty(Configuration.KEY_TIME_FILE_PATH));
		}
		
	}
	
	public static int getErrorCount() {
		return tree.size();
	}
	
	public static String errorToString() {
		StringBuilder sb = new StringBuilder();
		for (String s : tree) {
			sb.append(s + "\n");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	} 
	
	private static void setUp() {
		if (!initialized) {
			tree = new TreeSet<String>();
			try {
				conf = new Configuration();
			} catch (IOException e) {
				e.printStackTrace();
			}
			initialized = true;
		}
	}
}
