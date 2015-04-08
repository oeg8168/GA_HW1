import java.util.ArrayList;

/**
 * Hill climbing using bit reverse
 * 
 * @author LISM_OEG
 *
 */
public class HillClimbing {

	public static ArrayList<double[]> pathList;

	public static int steps = 10000;
	public static double stepLength = 0.001;

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

		// choose direction
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

} // end of class HillClimbing
