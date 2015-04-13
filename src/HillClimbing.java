import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Hill climbing using bit reverse
 * 
 * @author LISM_OEG
 *
 */
public class HillClimbing {

	public static File outputFolder;
	public static ArrayList<double[]> pathList;
	public static ArrayList<double[]> allList;

	public static int steps = 300;
	public static double stepLength = 0.05;

	/**
	 * Initialization
	 */
	public static void initialize() {

		GA_HW1.createLogFolder();

		// Create output folder
		Date date = new Date();
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		outputFolder = new File("./Log/HillClimbing/pathList_" + sdFormat.format(date));
		if (!outputFolder.exists()) {
			outputFolder.mkdir();
		}

		// Write down parameters
		try {
			FileWriter paramFW = new FileWriter(outputFolder.getAbsolutePath() + "/parameters.txt");
			paramFW.write("steps = " + steps + "\r\n");
			paramFW.write("stepLength = " + stepLength + "\r\n");
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
	 * Operation of each step
	 */
	public static void nextStep() {
		// load previous
		double[] temp = pathList.get(pathList.size() - 1).clone();

		// choose direction
		int direction = (int) (Math.random() * 4);

		switch (direction) {
		case 0: // X+
			temp[0] += stepLength;
			break;
		case 1: // X-
			temp[0] -= stepLength;
			break;
		case 2: // Y+
			temp[1] += stepLength;
			break;
		case 3: // Y-
			temp[1] -= stepLength;
			break;
		default:
			System.err.println("Missing direction!");
			break;
		}

		allList.add(new double[] { temp[0], temp[1], GA_HW1.equation(temp[0], temp[1]) });
		
		// check bound
		if (!GA_HW1.inBound(temp)) {
			return;
		}

		// Check higher and add list(or not)
		if (GA_HW1.equation(temp[0], temp[1]) > temp[2]) {
			pathList.add(new double[] { temp[0], temp[1], GA_HW1.equation(temp[0], temp[1]) });
		}

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
				fw.write(da[0] + "," + da[1] + "," + da[2] + "\r\n");
			}
			fw.close();
		} catch (IOException e) {
			System.err.println("Error while output results");
			e.printStackTrace();
		}
	}// end of output()

	public static void main(String[] args) {
		initialize();

		for (int i = 0; i < steps; i++) {
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

} // end of class HillClimbing
