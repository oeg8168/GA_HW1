import java.util.ArrayList;

/**
 * Hill climbing using bit reverse
 * 
 * @author LISM_OEG
 *
 */
public class HillClimbing {

	public static double upperX = 1.5;
	public static double lowerX = -0.5;

	public static double upperY = 1.5;
	public static double lowerY = -0.5;

	public static ArrayList<double[]> pathList;

	public static int steps = 1000;
	public static double stepLength = 0.0001;

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

		int direction = (int) (Math.random() * 4);

		switch (direction) {
		case 0: // X+
			temp[0] += 0.001;
			break;
		case 1: // X-
			temp[0] -= 0.001;
			break;
		case 2: // Y+
			temp[1] += 0.001;
			break;
		case 3: // Y-
			temp[1] -= 0.001;
			break;
		default:
			System.err.println("Missing direction!");
			break;
		}

		// check bound
		if (!inBound(temp[0], upperX, lowerX)) {
			return;
		}
		if (!inBound(temp[1], upperY, lowerY)) {
			return;
		}

		// Check higher and add list(or not)
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
