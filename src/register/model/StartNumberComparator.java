package register.model;

import java.math.BigInteger;
import java.util.Comparator;

public class StartNumberComparator implements Comparator<String> {

	@Override
	public int compare(String o1, String o2) {
		boolean x1 = o1.equals("x");
		boolean x2 = o2.equals("x");
		if (x1 && x2) {
			return 0;
		}
		if (x1) {
			return -1;
		}
		if (x2) {
			return 1;
		}
		BigInteger start1 = new BigInteger(o1);
		BigInteger start2 = new BigInteger(o2);
		return start1.compareTo(start2);
	}

}
