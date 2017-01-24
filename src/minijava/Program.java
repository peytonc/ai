package minijava;

import java.net.URI;
import java.util.ArrayList;

import javax.tools.SimpleJavaFileObject;

/**
 * A file object used to represent source coming from a string.
 */
public class Program extends SimpleJavaFileObject implements Comparable<Program> {
	public final static String PROGRAM_CLASS_NAME = new String("GeneticProgram");
	public final static String PACKAGE_NAME = new String("package");
	public String source;
	Fitness fitness = new Fitness();
	ArrayList<Long> vectorActual;
	public int ID;
	public URI uri = null;
	
	/**
	 * Constructs a new JavaSourceFromString.
	 * 
	 * @param className
	 *            the name of the compilation unit represented by this file object
	 * @param source
	 *            the source code for the compilation unit represented by this file object
	 */
	Program(String source, int ID) {
		super(URI.create("string:///" + PACKAGE_NAME + ID + '/' + PROGRAM_CLASS_NAME + Kind.SOURCE.extension), Kind.SOURCE);
		uri = URI.create("string:///" + PACKAGE_NAME + ID + '/' + PROGRAM_CLASS_NAME + Kind.SOURCE.extension);
		this.source = new String(source);
		fitness.size = source.length();
		this.ID = ID;
		vectorActual = null;
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