package minijava.comparator;

import java.util.ArrayList;
import java.util.Comparator;

import minijava.Program;

public class ProgramComparators {
	private static final ProgramComparators programComparators = new ProgramComparators();
	
	public static final int MAX_CATEGORIES = 3;
	public static final Comparator<Program> BY_MEAN = new ProgramComparatorByMean();
	public static final Comparator<Program> BY_CORRECT = new ProgramComparatorByCorrect();
	public static final Comparator<Program> BY_CONFIDENCE_INTERVAL = new ProgramComparatorByConfidenceInterval();
	public final ArrayList<Comparator<Program>> category = new ArrayList<Comparator<Program>>(MAX_CATEGORIES);
	
	private ProgramComparators() {
	}
	
	public static ProgramComparators getProgramComparators() {
        return programComparators; 
	}
	
	public void createProgramComparators() {
		category.add(BY_MEAN);
		category.add(BY_CORRECT);
		category.add(BY_CONFIDENCE_INTERVAL);
	}
}
