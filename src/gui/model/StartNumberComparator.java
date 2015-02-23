package gui.model;

import java.math.BigInteger;
import java.util.Comparator;

public class StartNumberComparator implements Comparator<String> {

	@Override
	public int compare(String o1, String o2) {
		boolean pre1 = o1.equals("Pre-registered time");
		boolean pre2 = o2.equals("Pre-registered time");
		if (pre1 && pre2) {
			return 0;
		}
		if (pre1) {
			return -1;
		}
		if (pre2) {
			return 1;
		}
		BigInteger start1 = new BigInteger(o1);
		BigInteger start2 = new BigInteger(o2);
		return start1.compareTo(start2);
	}

}
