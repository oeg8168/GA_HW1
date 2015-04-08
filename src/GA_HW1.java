/**
 * 
 * @author LISM_OEG
 *
 */
public class GA_HW1 {

	public static double upperX = 1.5;
	public static double lowerX = -0.5;

	public static double upperY = 1.5;
	public static double lowerY = -0.5;

	/**
	 * Equation to be solve
	 * 
	 * @param x
	 * @param y
	 * @return equation value
	 */
	public static double equation(double x, double y) {

		double value = 0;
		value = -20 - x * x - y * y + 10 * Math.cos(2 * Math.PI * x) + 10 * Math.cos(2 * Math.PI * y);

		return value;
	} // end of equation()

	/**
	 * Check if the input is out of bound
	 * 
	 * @param input
	 * @return boolean value if the input is out of bound
	 */
	public static boolean inBound(double[] input) {
		if (input[0] > upperX || input[0] < lowerX) {
			return false;
		} else if (input[1] > upperY || input[1] < lowerY) {
			return false;
		} else {
			return true;
		}
	} // end of checkBound()

} // end of class GA_HW1
