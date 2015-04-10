import java.util.ArrayList;
import java.util.HashSet;

/**
 * 
 * @author LISM_OEG
 *
 */

class Individual {
	protected double x;
	protected double y;
	protected String xBinary;
	protected String yBinary;

	protected double fitness;

} // end of class Individual

public class ClassicalGA {

	public static ArrayList<HashSet<double[]>> pathList;

	public static int population = 10;
	public static double crossoverRate = 0.85;
	public static double mutationRate = 0.05;
	public static int maxGeneration = 500;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	} // end of main()

} // end of class ClassicalGA
