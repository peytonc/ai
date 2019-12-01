package minijava.comparator;

import java.util.ArrayList;
import java.util.Comparator;

import minijava.Program;

public class ProgramComparators {
	private static final ProgramComparators programComparators = new ProgramComparators();
	
	public static final int MAX_CATEGORIES = 4;
	public static final Comparator<Program> BY_COMBINED = new ProgramComparatorByCombined();
	public static final Comparator<Program> BY_MEAN_ERROR_CONFIDENCE_INTERVAL = new ProgramComparatorByMeanErrorConfidenceInterval();
	public static final Comparator<Program> BY_MEAN_CORRECT_CONFIDENCE_INTERVAL = new ProgramComparatorByMeanCorrectConfidenceInterval();
	public static final Comparator<Program> BY_MEAN_ERROR = new ProgramComparatorByMeanError();
	public static final Comparator<Program> BY_MEAN_CORRECT = new ProgramComparatorByMeanCorrect();
	public final ArrayList<Comparator<Program>> category = new ArrayList<Comparator<Program>>(MAX_CATEGORIES);
	
	private ProgramComparators() {
	}
	
	public static ProgramComparators getProgramComparators() {
        return programComparators; 
	}
	
	public void createProgramComparators() {
		category.add(BY_COMBINED);
		category.add(BY_MEAN_ERROR_CONFIDENCE_INTERVAL);
		category.add(BY_MEAN_CORRECT_CONFIDENCE_INTERVAL);
		category.add(BY_MEAN_ERROR);
		category.add(BY_MEAN_CORRECT);
	}
}
