package minijava;

import java.net.URI;
import java.util.ArrayList;

import javax.tools.SimpleJavaFileObject;

import minijava.parser.MiniJavaParser;
import minijava.parser.MiniJavaParser.BlockContext;

/**
 * A file object used to represent source coming from a string.
 */
public class Program extends SimpleJavaFileObject {
	public static final String PROGRAM_CLASS = new String("GeneticProgram");
	public static final String PACKAGE_SPECIES = new String("species");
	public static final String PACKAGE_ID = new String("id");
	public String source;
	public Fitness fitness;
	public ArrayList<ArrayList<Long>> vectors;
	public int species;
	public int ID;
	public ProgramClassLoader programClassLoader = null;
	public MiniJavaParser miniJavaParser = null;
	public BlockContext blockContext = null;
	

	Program(String source, int species, int ID, Fitness fitness) {
		super(URI.create("string:///" + PACKAGE_SPECIES + species + '/' + PACKAGE_ID + ID + '/' + PROGRAM_CLASS + Kind.SOURCE.extension), Kind.SOURCE);
		this.source = new String(source);
		if(fitness == null) {
			this.fitness = new Fitness();
		} else {
			this.fitness = new Fitness(fitness);
		}
		this.fitness.generation++;
		this.fitness.size = source.length();
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
}