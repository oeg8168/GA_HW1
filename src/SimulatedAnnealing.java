import java.util.ArrayList;

/**
 * 
 * @author LISM_OEG
 *
 */
public class SimulatedAnnealing {

	public static ArrayList<double[]> pathList;

	public static double temperature = 100;
	public static double minTemperature = 0.001;
	public static double coolingRate = 0.99;

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
	 * Count acceptance probabilities
	 * 
	 * @param oldValue
	 * @param newValue
	 * @return
	 */
	public static double acceptProbability(double oldValue, double newValue) {
		if (newValue > oldValue) {
			return 1;
		} else {
			return Math.exp((newValue - oldValue) / temperature);
		}
	} // end of acceptProbability()

	/**
	 * Operation of each step
	 */
	public static void nextStep() {
		// load previous
		double[] temp = pathList.get(pathList.size() - 1).clone();

		// generate random solution
		double x = Math.random() * 2 - 0.5;
		double y = Math.random() * 2 - 0.5;

		// Count acceptance probabilities
		double ap = acceptProbability(temp[2], GA_HW1.equation(x, y));

		// Decided transfer or not
		if (ap > Math.random()) {
			pathList.add(new double[] { x, y, GA_HW1.equation(x, y) });
		}

		// cooling
		temperature = temperature * coolingRate;

	} // end of nextStep()

	public static void main(String[] args) {
		initialize();

		while (temperature > minTemperature) {
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

} // end of class SimulatedAnnealing
