import java.io.File;

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

	public static int numberOfBits = 10; // this cannot be too large

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
	 * Create log folders if it doesn't exist
	 */
	public static void createLogFolder() {
		if (!new File("Log/").exists())
			new File("Log/").mkdirs();

		if (!new File("Log/BitReverseHillClimbing/").exists())
			new File("Log/BitReverseHillClimbing/").mkdirs();

		if (!new File("Log/HillClimbing/").exists())
			new File("Log/HillClimbing/").mkdirs();

		if (!new File("Log/SimulatedAnnealing/").exists())
			new File("Log/SimulatedAnnealing/").mkdirs();

		if (!new File("Log/ClassicalGA/").exists())
			new File("Log/ClassicalGA/").mkdirs();
	}// end of createLogFolder()

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
	} // end of inBound()

	/**
	 * Check if the input is out of bound, with given bounds
	 * 
	 * @param input
	 * @param upper
	 *            - upper bound
	 * @param lower
	 *            - lower bound
	 * @return boolean value if the input is out of bound
	 */
	public static boolean inBound(double input, double upper, double lower) {
		if (input <= upper && input >= lower) {
			return true;
		} else {
			return false;
		}
	} // end of inBound()

	/**
	 * Take input decimal and encode to binary, with x/y as dimension
	 * 
	 * @param decimal
	 * @param dimension
	 *            - x or y
	 * @return encoded string
	 */
	public static String encode(double decimal, String dimension) {
		double fixed = 0;

		if (dimension.equalsIgnoreCase("X")) {
			if (inBound(decimal, upperX, lowerX)) {
				fixed = ((decimal - lowerX) / (upperX - lowerX)) * Math.pow(2, numberOfBits);
			} else {
				System.err.println("Input out of bound while encoding!!");
			}
		} else if (dimension.equalsIgnoreCase("Y")) {
			if (inBound(decimal, upperY, lowerY)) {
				fixed = ((decimal - lowerY) / (upperY - lowerY)) * Math.pow(2, numberOfBits);
			} else {
				System.err.println("Input out of bound while encoding!!");
			}
		} else {
			System.err.println("Wrong dimension when encoding!!");
			return null;
		}

		String bits = Integer.toBinaryString((int) fixed);
		bits = String.format("%" + numberOfBits + "s", bits).replace(' ', '0');
		return bits;
	} // end of encode()

	/**
	 * Take input decimal and encode to binary, with 0/1 as dimension
	 * 
	 * @param decimal
	 * @param dimension
	 *            - 0 or 1
	 * @return encoded string
	 */
	public static String encode(double decimal, int dimension) {
		double fixed = 0;

		if (dimension == 0) {
			if (inBound(decimal, upperX, lowerX)) {
				fixed = ((decimal - lowerX) / (upperX - lowerX)) * Math.pow(2, numberOfBits);
			} else {
				System.err.println("Input out of bound while encoding!!");
			}
		} else if (dimension == 1) {
			if (inBound(decimal, upperY, lowerY)) {
				fixed = ((decimal - lowerY) / (upperY - lowerY)) * Math.pow(2, numberOfBits);
			} else {
				System.err.println("Input out of bound while encoding!!");
			}
		} else {
			System.err.println("Wrong dimension when encoding!!");
			return null;
		}

		String bits = Integer.toBinaryString((int) fixed);
		bits = String.format("%" + numberOfBits + "s", bits).replace(' ', '0');
		return bits;
	} // end of encode()

	/**
	 * Take input bit string and convert to decimal value, with x/y as dimension
	 * 
	 * @param bits
	 * @param dimension
	 *            - x or y
	 * @return decoded value
	 */
	public static Double decode(String bits, String dimension) {
		int index = Integer.parseInt(bits, 2);

		if (dimension.equalsIgnoreCase("X")) {
			return index / Math.pow(2, numberOfBits) * (upperX - lowerX) + lowerX;
		} else if (dimension.equalsIgnoreCase("Y")) {
			return index / Math.pow(2, numberOfBits) * (upperY - lowerY) + lowerY;
		} else {
			System.err.println("Wrong dimension when decoding!!");
			return null;
		}

	} // end of decode()

	/**
	 * Take input bit string and convert to decimal value, with 0/1 as dimension
	 * 
	 * @param bits
	 * @param dimension
	 *            - 0 or 1
	 * @return decoded value
	 */
	public static Double decode(String bits, int dimension) {
		int index = Integer.parseInt(bits, 2);

		if (dimension == 0) {
			return index / Math.pow(2, numberOfBits) * (upperX - lowerX) + lowerX;
		} else if (dimension == 1) {
			return index / Math.pow(2, numberOfBits) * (upperY - lowerY) + lowerY;
		} else {
			System.err.println("Wrong dimension when decoding!!");
			return null;
		}

	} // end of decode()

	/**
	 * Reverse bit at given position
	 * 
	 * @param bits
	 * @param index
	 * @return reversed bit string
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

} // end of class GA_HW1
