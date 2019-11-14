package minijava;

import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;

import javax.tools.SimpleJavaFileObject;

import minijava.parser.MiniJavaParser;
import minijava.parser.MiniJavaParser.BlockContext;

/**
 * A file object used to represent source coming from a string.
 */
public class Program extends SimpleJavaFileObject implements Comparable<Program> {
	public static final String PROGRAM_CLASS = new String("GeneticProgram");
	public static final String PACKAGE_SPECIES = new String("species");
	public static final String PACKAGE_ID = new String("id");
	public String source;
	public Fitness fitness = null;
	public ArrayList<ArrayList<Long>> vectors;
	public int species;
	public int ID;
	public ProgramClassLoader programClassLoader = null;
	public MiniJavaParser miniJavaParser = null;
	public BlockContext blockContext = null;
	
	/**
	 * Constructs a new JavaSourceFromString.
	 * 
	 * @param className
	 *            the name of the compilation unit represented by this file object
	 * @param source
	 *            the source code for the compilation unit represented by this file object
	 */
	Program(String source, int species, int ID, long generation, long generationalFitness) {
		super(URI.create("string:///" + PACKAGE_SPECIES + species + '/' + PACKAGE_ID + ID + '/' + PROGRAM_CLASS + Kind.SOURCE.extension), Kind.SOURCE);
		this.source = new String(source);
		fitness = new Fitness();
		fitness.generation = generation;
		fitness.generationalFitness = generationalFitness;
		fitness.size = source.length();
		this.species = species;
		this.ID = ID;
		vectors = new ArrayList<ArrayList<Long>>(Tests.MAX_TEST_VECTORS);
		for(Test test : Tests.getTests().listTests) {
			ArrayList<Long> arrayList = new ArrayList<Long>(test.listTest);
			vectors.add(arrayList);
		}
		programClassLoader = new ProgramClassLoader(ClassLoader.getSystemClassLoader());
	}

	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) {
		return source;
	}
	
	@Override
	public int compareTo(Program program) {
		return fitness.compareTo(program.fitness);
	}
	
}

class ProgramComparatorByFit implements Comparator<Program> {
    public int compare(Program program1, Program program2) { 
		FitnessComparatorByFit fitnessComparatorByFit = new FitnessComparatorByFit();
		int compare = fitnessComparatorByFit.compare(program1.fitness, program2.fitness);
		return compare;
    }
}

class ProgramComparatorByGenerational implements Comparator<Program> {
    public int compare(Program program1, Program program2) { 
		FitnessComparatorByGenerational fitnessComparatorByGenerational = new FitnessComparatorByGenerational();
		int compare = fitnessComparatorByGenerational.compare(program1.fitness, program2.fitness);
		return compare;
    }
}