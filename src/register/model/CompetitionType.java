package register.model;

import java.io.PrintWriter;

public interface CompetitionType {
	
	void result(DataStructure ds, PrintWriter pw);
	void columnNames(StringBuilder sb);
	void writeStandardContestant();
	

}
