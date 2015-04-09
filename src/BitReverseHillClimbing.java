import java.util.ArrayList;

/**
 * Hill climbing using bit reverse
 * 
 * @author LISM_OEG
 *
 */
public class BitReverseHillClimbing {

	public static ArrayList<double[]> pathList;

	public static int steps = 1000;

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
	 * Operation of each step
	 */
	public static void nextStep() {
		// load previous
		double[] temp = pathList.get(pathList.size() - 1).clone();

		// pick XY
		int dimension = (int) (Math.random() * 2);

		// encode
		String bits = GA_HW1.encode(temp[dimension], dimension);

		// random change bit
		bits = GA_HW1.reverseBit(bits, (int) (Math.random() * bits.length()));

		// decode
		double changedValue = GA_HW1.decode(bits, dimension);
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

		// System.out.println(GA_HW1.encode(0.13, "X"));
		// System.out.println(GA_HW1.decode("1111111111", "X"));

	} // end of main()

} // end of class BitReverseHillClimbing
