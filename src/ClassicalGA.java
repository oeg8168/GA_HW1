import java.util.ArrayList;
import java.util.Comparator;

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

	public static ArrayList<Individual> populations;
	public static ArrayList<ArrayList<Individual>> pathList;

	public static int maxPopulation = 10;
	public static double crossoverRate = 0.70;
	public static double mutationRate = 0.10;
	public static int maxGeneration = 100;

	/**
	 * Initialization
	 */
	public static void initialize() {
		populations = new ArrayList<Individual>();
		pathList = new ArrayList<ArrayList<Individual>>();

		for (int i = 0; i < maxPopulation; i++) {
			populations.add(new Individual());
		}
	} // end of initialize()

	public static ArrayList<Individual> selection() {
		// TODO
		return null;
	}

	/**
	 * Make two parent individual crossover and generate children
	 * 
	 * @param f
	 *            - parent
	 * @param m
	 *            - parent
	 * @return
	 */
	public static ArrayList<Individual> crossover(Individual f, Individual m) {
		int cutpoint = (int) (Math.random() * (2 * GA_HW1.numberOfBits - 1) + 1);

		String child_1 = f.getGene().substring(0, cutpoint) + m.getGene().substring(cutpoint, m.getGene().length());
		String child_2 = m.getGene().substring(0, cutpoint) + f.getGene().substring(cutpoint, f.getGene().length());

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
	 * Sort populations and keep top N individuals
	 */
	public static void keepBest() {
		// Sort populations by decreasing order
		populations.sort(new Comparator<Individual>() {
			public int compare(Individual o1, Individual o2) {
				return Double.compare(o2.getFitness(), o1.getFitness());
			}
		});

		// Keep top N individuals
		while (populations.size() > maxPopulation) {
			populations.remove(populations.size() - 1);
		}
	}// end of keepBest()

	/**
	 * Operation taken for each generation
	 */
	public static void nextGeneration() {
		// Crossover
		int index1;
		int index2;
		while (Math.random() < crossoverRate) {
			index1 = (int) (Math.random() * populations.size());
			index2 = (int) (Math.random() * populations.size());
			populations.addAll(crossover(populations.get(index1), populations.get(index2)));
		}

		// Mutation
		int index;
		while (Math.random() < mutationRate) {
			index = (int) (Math.random() * populations.size());
			populations.set(index, mutate(populations.get(index)));
		}

		keepBest();

		pathList.add(new ArrayList<Individual>(populations));
	}// end of nextGeneration()

	public static void main(String[] args) {
		initialize();

		for (int i = 0; i < maxGeneration; i++) {
			nextGeneration();
		}

		System.out.println("=====");

		for (ArrayList<Individual> arrayList : pathList) {
			for (Individual individual : arrayList) {
				System.out.println(individual.getX() + " " + individual.getY() + " " + individual.getFitness());
			}
			System.out.println("*****");
		}

	} // end of main()
} // end of class ClassicalGA
