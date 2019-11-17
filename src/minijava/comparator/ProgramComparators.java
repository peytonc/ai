package minijava.comparator;

import java.util.Comparator;

import minijava.Program;

public class ProgramComparators {
	public static final Comparator<Program> BY_FIT = new ProgramComparatorByFit();
	public static final Comparator<Program> BY_GENERATIONAL = new ProgramComparatorByGenerational();
}
