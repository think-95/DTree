package data;

/**
 * @author:wzc
 * @version:10:02:33 PM
 */
public class NewMath {
	private static double lg2 = Math.log(2);

	public static double log(double value) {
		if (value == 0)
			return 0;
		else {
			return Math.log(value) / lg2;
		}
	}
}
