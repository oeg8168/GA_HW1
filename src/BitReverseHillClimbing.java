import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Hill climbing using bit reverse
 * 
 * @author LISM_OEG
 *
 */
public class BitReverseHillClimbing {

	public static double upperX = 1.5;
	public static double lowerX = -0.5;

	public static double upperY = 1.5;
	public static double lowerY = -0.5;

	public static ArrayList<double[]> pathList;

	public static int steps = 500;

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
	 * Initialization
	 */
	public static void initialize() {
		pathList = new ArrayList<double[]>();

		double originX = Math.random() * 2 - 0.5;
		double originY = Math.random() * 2 - 0.5;
		pathList.add(new double[] { originX, originY, equation(originX, originY) });
	} // end of initialize()

	/**
	 * Encode double decimal value to binary bits
	 * 
	 * @param decimal
	 * @return String of binary bits
	 */
	public static String encode(double decimal) {
		return Long.toBinaryString(Double.doubleToLongBits(decimal));
	} // end of encode()

	/**
	 * Decode binary bits to double decimal value
	 * 
	 * @param bits
	 * @return double value of decoded binary bits
	 */
	public static double decode(String bits) {
		return Double.longBitsToDouble(new BigInteger(bits, 2).longValue());
	} // end of decode()

	/**
	 * Reverse bit at given index
	 * 
	 * @param bits
	 * @param index
	 * @return reversed bits string
	 */
	public static String reverseBit(String bits, int index) {

		char[] temp = bits.toCharArray();
		if (temp[index] == '0') {
			temp[index] = '1';
		} else if (temp[index] == '1') {
			temp[index] = '0';
		} else {
			System.err.println("Reverse bits error!");
			return null;
		}

		return new String(temp);
	} // end of reverseBit()

	/**
	 * Check if the input is out of bound
	 * 
	 * @param input
	 * @param upper
	 * @param lower
	 * @return boolean value if the input is out of bound
	 */
	public static boolean inBound(double input, double upper, double lower) {
		if (input > upper || input < lower) {
			return false;
		} else {
			return true;
		}
	} // end of checkBound()

	/**
	 * Operation of each step
	 */
	public static void nextStep() {
		// load previous
		double[] temp = pathList.get(pathList.size() - 1).clone();

		// pick XY
		int dimension = (int) (Math.random() * 2);

		for (double d : temp) {
			System.out.print(d + "\t");
		}
		System.out.println(dimension);
		System.out.println();

		// encode
		String bits = encode(temp[dimension]);

		// random change bit
		bits = reverseBit(bits, (int) (Math.random() * bits.length()));

		// decode
		double changedValue = decode(bits);

		// check bound
		if (dimension == 0) {
			if (!inBound(changedValue, upperX, lowerX))
				return;
		} else if (dimension == 1) {
			if (!inBound(changedValue, upperY, lowerY))
				return;
		}

		// Check higher and add list(or not)
		temp[dimension] = changedValue;
		if (equation(temp[0], temp[1]) > temp[2]) {
			pathList.add(new double[] { temp[0], temp[1], equation(temp[0], temp[1]) });
		}
	} // end of nextStep()

	public static void main(String[] args) {
		initialize();

		for (int i = 0; i < steps; i++) {
			nextStep();
		}

		System.out.println("********");
		for (double[] ds : pathList) {
			for (double d : ds) {
				System.out.print(d + "\t");

			}
			System.out.println();
		}

		// System.out.println((int) (Math.random() * 4));

	} // end of main()

} // end of class BitReverseHillClimbing
