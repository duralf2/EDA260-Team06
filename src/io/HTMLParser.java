package io;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class HTMLParser {
	private static final Map<String, String> headerMap;
	static
	{
		headerMap = new HashMap<String, String>();
		headerMap.put("StartNo", "Start Number");
	}

	public static String resultToHTML(String csvInput) {

		StringBuilder sb = new StringBuilder();
		List<String> lines = Arrays.asList(csvInput.split("\n"));
		
		sb.append("<style> table #header{ font-weight: bold; }</style>\n");
		sb.append("<html> <body> <table border=\"1\">\n");

		sb.append(convertLine(lines.remove(0), true));
		for (String line : lines)
			sb.append(convertLine(line, false) + "\n");

		sb.append("</table> </body> </html>");
		
		return sb.toString().replaceAll("<table border=\"1\">\n</table>", "");
	}
	
	private static String convertLine(String line, boolean header) {

		StringBuilder sb = new StringBuilder();
		String[] columns = line.split(";");
		
		if (columns.length > 1)
		{
			sb.append("<tr>");
			for (String column : columns)
			{
				sb.append(header ? "<th>" : "<td>");
				sb.append(header && headerMap.containsKey(column) ? headerMap.get(column) : column);
				sb.append(header ? "<th>" : "</td>");
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
