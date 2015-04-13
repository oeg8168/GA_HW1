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
public class BitReverseHillClimbing {

	public static File outputFolder;
	public static ArrayList<double[]> pathList;
	public static ArrayList<double[]> allList;

	public static int steps = 200;

	/**
	 * Initialization
	 */
	public static void initialize() {

		GA_HW1.createLogFolder();

		// Create output folder
		Date date = new Date();
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		outputFolder = new File("./Log/BitReverseHillClimbing/pathList_" + sdFormat.format(date));
		if (!outputFolder.exists()) {
			outputFolder.mkdir();
		}

		// Write down parameters
		try {
			FileWriter paramFW = new FileWriter(outputFolder.getAbsolutePath() + "/parameters.txt");
			paramFW.write("steps = " + steps + "\r\n");
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
			allList.add(new double[] { temp[0], temp[1], GA_HW1.equation(temp[0], temp[1]) });
		} else {
			allList.add(null);
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

} // end of class BitReverseHillClimbing
