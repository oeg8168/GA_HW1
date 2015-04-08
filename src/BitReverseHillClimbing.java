import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Hill climbing using bit reverse
 * 
 * @author LISM_OEG
 *
 */
public class BitReverseHillClimbing {

	public static ArrayList<double[]> pathList;

	public static int steps = 500;

	/**
	 * Initialization
	 */
	public static void initialize() {
		pathList = new ArrayList<double[]>();

		double originX = Math.random() * 2 - 0.5;
		double originY = Math.random() * 2 - 0.5;
		pathList.add(new double[] { originX, originY, GA_HW1.equation(originX, originY) });
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
	 * Operation of each step
	 */
	public static void nextStep() {
		// load previous
		double[] temp = pathList.get(pathList.size() - 1).clone();

		// pick XY
		int dimension = (int) (Math.random() * 2);

		// encode
		String bits = encode(temp[dimension]);

		// random change bit
		bits = reverseBit(bits, (int) (Math.random() * bits.length()));

		// decode
		double changedValue = decode(bits);
		temp[dimension] = changedValue;

		// check bound
		if (!GA_HW1.inBound(temp)) {
			return;
		}

		// Check higher and add list(or not)
		if (GA_HW1.equation(temp[0], temp[1]) > temp[2]) {
			pathList.add(new double[] { temp[0], temp[1], GA_HW1.equation(temp[0], temp[1]) });
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

	} // end of main()

} // end of class BitReverseHillClimbing
