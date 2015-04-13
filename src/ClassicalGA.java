import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

/**
 * Data structure of an individual
 * 
 * @author LISM_OEG
 *
 */
class Individual {
	protected String xBinary;
	protected String yBinary;

	/**
	 * Random generate an individual
	 */
	public Individual() {
		String gene = new String();
		while (gene.length() < 2 * GA_HW1.numberOfBits) {
			gene = gene + String.valueOf((int) (Math.random() * 2));
		}

		setGene(gene);
	}

	/**
	 * Generate an individual with given gene
	 * 
	 * @param gene
	 */
	public Individual(String gene) {
		setGene(gene);
	}

	/**
	 * Set gene of this individual
	 * 
	 * @param gene
	 */
	public void setGene(String gene) {
		xBinary = gene.substring(0, GA_HW1.numberOfBits);
		yBinary = gene.substring(GA_HW1.numberOfBits, gene.length());
	}

	/**
	 * @return Decimal format of x
	 */
	public double getX() {
		return GA_HW1.decode(xBinary, "X");
	}

	/**
	 * @return Decimal format of y
	 */
	public double getY() {
		return GA_HW1.decode(yBinary, "Y");
	}

	/**
	 * @return Gene of this individual
	 */
	public String getGene() {
		return xBinary + yBinary;
	}

	/**
	 * Count fitness(equation value) and return
	 * 
	 * @return fitness
	 */
	public double getFitness() {
		return GA_HW1.equation(this.getX(), this.getY());
	}

} // end of class Individual

public class ClassicalGA {

	public static File outputFolder;
	public static ArrayList<Individual> populations;
	public static ArrayList<ArrayList<Individual>> pathList;
	public static ArrayList<ArrayList<Individual>> allList;

	public static int maxPopulation = 20;
	public static double crossoverRate = 0.80;
	public static double mutationRate = 0.10;
	public static int maxGeneration = 200;

	/**
	 * Initialization
	 */
	public static void initialize() {

		GA_HW1.createLogFolder();

		// Create output folder
		Date date = new Date();
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		outputFolder = new File("./Log/ClassicalGA/pathList_" + sdFormat.format(date));
		if (!outputFolder.exists()) {
			outputFolder.mkdir();
		}

		// Write down parameters
		try {
			FileWriter paramFW = new FileWriter(outputFolder.getAbsolutePath() + "/parameters.txt");
			paramFW.write("maxPopulation = " + maxPopulation + "\r\n");
			paramFW.write("crossoverRate = " + crossoverRate + "\r\n");
			paramFW.write("mutationRate = " + mutationRate + "\r\n");
			paramFW.write("maxGeneration = " + maxGeneration + "\r\n");
			paramFW.close();
		} catch (IOException e) {
			System.err.println("Error while output parameter!");
			e.printStackTrace();
		}

		populations = new ArrayList<Individual>();
		pathList = new ArrayList<ArrayList<Individual>>();
		allList = new ArrayList<ArrayList<Individual>>();

		for (int i = 0; i < maxPopulation; i++) {
			populations.add(new Individual());
		}

		sortPopulations();
		pathList.add(new ArrayList<Individual>(populations));
	} // end of initialize()

	/**
	 * Sort populations by decreasing order
	 */
	public static void sortPopulations() {
		populations.sort(new Comparator<Individual>() {
			public int compare(Individual ind1, Individual ind2) {
				return Double.compare(ind2.getFitness(), ind1.getFitness());
			}
		});
	} // end of sortPopulations()

	/**
	 * Do selection using Roulette Wheel
	 * 
	 * @return selected index
	 */
	public static int selection() {

		double totalFitness = 0;
		for (Individual i : populations) {
			totalFitness += Math.abs(i.getFitness());
		}

		// Roulette Wheel selection
		int index = 0;
		double shoot = Math.random();

		double relativeFitness = 0;
		double cumulativeFitness = 0;

		while (true) {
			relativeFitness = Math.abs(populations.get(index).getFitness()) / totalFitness;
			cumulativeFitness += relativeFitness;

			if (shoot < cumulativeFitness) {
				return index;
			} else {
				index++;
			}
		}
	} // end of selection()

	/**
	 * Make two parent individual crossover and generate children
	 * 
	 * @param p1
	 *            - parent
	 * @param p2
	 *            - parent
	 * @return
	 */
	public static ArrayList<Individual> crossover(Individual p1, Individual p2) {
		int cutpoint = (int) (Math.random() * (2 * GA_HW1.numberOfBits - 1) + 1);

		String child_1 = p1.getGene().substring(0, cutpoint) + p2.getGene().substring(cutpoint, p2.getGene().length());
		String child_2 = p2.getGene().substring(0, cutpoint) + p1.getGene().substring(cutpoint, p1.getGene().length());

		ArrayList<Individual> child = new ArrayList<Individual>();
		child.add(new Individual(child_1));
		child.add(new Individual(child_2));

		return child;
	} // end of crossover()

	/**
	 * Randomly mutate gene at random position
	 * 
	 * @param input
	 *            - Individual
	 * @return mutated Individual
	 */
	public static Individual mutate(Individual input) {
		String gene = input.getGene();
		gene = GA_HW1.reverseBit(gene, (int) (Math.random() * gene.length()));

		return new Individual(gene);
	}// end of mutate()

	/**
	 * Keep top N individuals
	 */
	public static void keepBest() {
		while (populations.size() > maxPopulation) {
			populations.remove(populations.size() - 1);
		}
	}// end of keepBest()

	/**
	 * Operation taken for each generation
	 */
	public static void nextGeneration() {
		// Selection and Reproduction
		int index1;
		int index2;
		while (Math.random() < crossoverRate) {
			index1 = selection();
			index2 = selection();
			populations.addAll(crossover(populations.get(index1), populations.get(index2)));
		}

		// Mutation
		int index;
		while (Math.random() < mutationRate) {
			index = (int) (Math.random() * populations.size());
			populations.set(index, mutate(populations.get(index)));
		}

		sortPopulations();
		keepBest();

		allList.add(new ArrayList<Individual>(populations));

		if (!populations.equals(pathList.get(pathList.size() - 1))) {
			pathList.add(new ArrayList<Individual>(populations));
		}
	}// end of nextGeneration()

	/**
	 * Output pathList
	 */
	public static void output() {

		double averageFitness = 0;

		// Write file
		try {
			FileWriter fw = new FileWriter(outputFolder.getAbsolutePath() + "/pathList.csv");
			fw.write("x,y,f,Average fitness" + "\r\n");
			for (ArrayList<Individual> arrayList : pathList) {
				averageFitness = 0;
				fw.write("Generation " + pathList.indexOf(arrayList) + "\r\n");
				for (Individual individual : arrayList) {
					fw.write(individual.getX() + "," + individual.getY() + "," + individual.getFitness() + "\r\n");
					averageFitness += individual.getFitness();
				}
				averageFitness = averageFitness / arrayList.size();
				fw.write("Average fitness:" + ",,," + averageFitness + "\r\n");
			}
			fw.close();

			fw = new FileWriter(outputFolder.getAbsolutePath() + "/allList.csv");
			fw.write("x,y,f,Average fitness" + "\r\n");
			for (ArrayList<Individual> arrayList : allList) {
				averageFitness = 0;
				fw.write("Generation " + allList.indexOf(arrayList) + "\r\n");
				for (Individual individual : arrayList) {
					fw.write(individual.getX() + "," + individual.getY() + "," + individual.getFitness() + "\r\n");
					averageFitness += individual.getFitness();
				}
				averageFitness = averageFitness / arrayList.size();
				fw.write("Average fitness:" + ",,," + averageFitness + "\r\n");
			}
			fw.close();
		} catch (IOException e) {
			System.err.println("Error while output results");
			e.printStackTrace();
		}
	}// end of output()

	public static void main(String[] args) {
		initialize();

		for (int i = 0; i < maxGeneration; i++) {
			nextGeneration();
		}

		for (ArrayList<Individual> arrayList : pathList) {
			System.out.println("Generation " + pathList.indexOf(arrayList));
			for (Individual individual : arrayList) {
				System.out.println(individual.getX() + "\t" + individual.getY() + "\t" + individual.getFitness());
			}
			System.out.println();
		}

		output();

		System.out.println("~~End of program~~");
	} // end of main()

} // end of class ClassicalGA
