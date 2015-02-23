package gui.model;

import java.math.BigInteger;
import java.util.Comparator;

/**
 * Compares the times for two start numbers.
 */
public class StartNumberComparator implements Comparator<String> {

	/**
	 * Compares the times for two start numbers.
	 * 
	 * @param contestant1 the first start number
	 * @param contestant2 the second start number
	 * @return -1, 0 or 1 as the time for contestant1 is numerically less than, equal to, or greater than that of contestant2.
	 */
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
