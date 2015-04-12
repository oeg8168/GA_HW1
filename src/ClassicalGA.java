import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

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

	public static HashSet<Individual> populations;
	public static ArrayList<HashSet<Individual>> pathList;

	public static int population = 10;
	public static double crossoverRate = 0.85;
	public static double mutationRate = 0.05;
	public static int maxGeneration = 500;

	public static void initialize() {
		populations = new HashSet<Individual>();

		for (int i = 0; i < population; i++) {
			populations.add(new Individual());
		}

	} // end of initialize()

	public static HashSet<Individual> crossover(Individual f, Individual m) {
		int cutpoint = (int) (Math.random() * (2 * GA_HW1.numberOfBits - 1) + 1);

		String child_1 = f.getGene().substring(0, cutpoint) + " " + m.getGene().substring(cutpoint, m.getGene().length());
		String child_2 = m.getGene().substring(0, cutpoint) + " " + f.getGene().substring(cutpoint, f.getGene().length());

		HashSet<Individual> childs = new HashSet<Individual>();
		childs.add(new Individual(child_1));
		childs.add(new Individual(child_2));

		return childs;
	} // end of crossover()

	public static Individual mutate(Individual input) {
		String gene = input.getGene();
		gene = GA_HW1.reverseBit(gene, (int) (Math.random() * gene.length()));

		return new Individual(gene);
	}// end of mutate()

	public static void main(String[] args) {
		Individual a = new Individual("00000000001111111111");
		System.out.println(a.getGene());

		initialize();

		System.out.println("=====");
		for (int i = 0; i < 100; i++) {
			Individual b = new Individual();
			Individual c = new Individual();
			System.out.println(b.getGene());
			System.out.println(c.getGene());
			crossover(b, c);
			System.out.println();
		}
		// Individual b = new Individual();
		// Individual c = new Individual();
		// System.out.println(b.getGene());
		// System.out.println(c.getGene());
		// crossover(b, c);

	} // end of main()

} // end of class ClassicalGA
