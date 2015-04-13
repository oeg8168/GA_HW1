import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 
 * @author LISM_OEG
 *
 */
public class SimulatedAnnealing {

	public static File outputFolder;
	public static ArrayList<double[]> pathList;
	public static ArrayList<double[]> allList;

	public static double initTemperature = 100;
	public static double minTemperature = 0.001;
	public static double coolingRate = 0.975;

	/**
	 * Initialization
	 */
	public static void initialize() {

		GA_HW1.createLogFolder();

		// Create output folder
		Date date = new Date();
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		outputFolder = new File("./Log/SimulatedAnnealing/pathList_" + sdFormat.format(date));
		if (!outputFolder.exists()) {
			outputFolder.mkdir();
		}

		// Write down parameters
		try {
			FileWriter paramFW = new FileWriter(outputFolder.getAbsolutePath() + "/parameters.txt");
			paramFW.write("initTemperature = " + initTemperature + "\r\n");
			paramFW.write("minTemperature = " + minTemperature + "\r\n");
			paramFW.write("coolingRate = " + coolingRate + "\r\n");
			paramFW.close();
		} catch (IOException e) {
			System.err.println("Error while output parameter!");
			e.printStackTrace();
		}

		pathList = new ArrayList<double[]>();
		allList = new ArrayList<double[]>();

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
			return Math.exp((newValue - oldValue) / initTemperature);
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
			allList.add(new double[] { x, y, GA_HW1.equation(x, y) });
		} else {
			allList.add(null);
		}

		// cooling
		initTemperature = initTemperature * coolingRate;

	} // end of nextStep()

	/**
	 * Output pathList
	 */
	public static void output() {

		// Write file
		try {
			FileWriter fw = new FileWriter(outputFolder.getAbsolutePath() + "/pathList.csv");
			fw.write("x,y,f" + "\r\n");
			for (double[] da : pathList) {
				fw.write(da[0] + "," + da[1] + "," + da[2] + "\r\n");
			}
			fw.close();

			fw = new FileWriter(outputFolder.getAbsolutePath() + "/allList.csv");
			fw.write("x,y,f" + "\r\n");
			for (double[] da : allList) {
				if (da != null) {
					fw.write(da[0] + "," + da[1] + "," + da[2] + "\r\n");
				} else {
					fw.write(",," + "\r\n");
				}
			}
			fw.close();
		} catch (IOException e) {
			System.err.println("Error while output results");
			e.printStackTrace();
		}
	}// end of output()

	public static void main(String[] args) {
		initialize();

		while (initTemperature > minTemperature) {
			nextStep();
		}

		for (double[] ds : pathList) {
			for (double d : ds) {
				System.out.print(d + "\t");

			}
			System.out.println();
		}

		output();

		System.out.println("~~End of program~~");
	} // end of main()

} // end of class SimulatedAnnealing
