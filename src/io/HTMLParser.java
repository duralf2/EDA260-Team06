package io;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.SliderUI;

public class HTMLParser {
	private final Map<String, String> headerMap;
	public HTMLParser()
	{
		headerMap = new HashMap<String, String>();
		headerMap.put("StartNr", "Start Number");
	}

	public String resultToHTML(String csvInput) {

		StringBuilder sb = new StringBuilder();
		List<String> lines = new ArrayList<String>(Arrays.asList(csvInput.split("\n")));
		
		sb.append("<style> table #header{ font-weight: bold; }</style>\n");
		sb.append("<html> <body> <table border=\"1\">\n");

		boolean wasLastLineClass = true;
		for (String line : lines) {
			String[] columns = line.split(";",-1);
			
			sb.append(convertLine(line, columns, wasLastLineClass) + "\n");
			if (columns.length == 1) {
				wasLastLineClass = true;
			} else {
				wasLastLineClass = false;
			}
		}

		sb.append("</table> </body> </html>");
		
		return sb.toString().replaceAll("<table border=\"1\">\n</table>", "");
	}
	
	private String convertLine(String line, String[] columns, boolean header) {

		StringBuilder sb = new StringBuilder();
		
		if (columns.length > 1)
		{
			sb.append("<tr>");
			for (String column : columns)
			{
				sb.append(header ? "<th>" : "<td>");
				sb.append(header && headerMap.containsKey(column) ? headerMap.get(column) : column);
				sb.append(header ? "</th>" : "</td>");
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
