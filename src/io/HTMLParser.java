package io;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class HTMLParser {
	
	public static String resultToHTML(String csvInput) {

		StringBuilder sb = new StringBuilder();
		String[] lines = csvInput.split("\n");
		
		sb.append("<style> table #header{ font-weight: bold; }</style>\n");
		sb.append("<html> <body> <table border=\"1\">\n");
		
		for (String line : lines)
			sb.append(convertLine(line) + "\n");

		sb.append("</table> </body> </html>");
		
		return sb.toString().replaceAll("<table border=\"1\">\n</table>", "");
	}
	
	private static String convertLine(String line) {

		StringBuilder sb = new StringBuilder();
		String[] columns = line.split(";");
		
		if (columns.length > 1)
		{
			sb.append("<tr>");
			for (String column : columns)
			{
				sb.append("<td>");
				sb.append(column);
				sb.append("</td>");
			}
			sb.append("</tr>");
		}
		else
		{
			sb.append("</table>");
			
			sb.append("<h3>");
			sb.append(line);
			sb.append("</h3>");
			
			sb.append("<table border=\"1\">");
		}
		
		return sb.toString();
	}
}
