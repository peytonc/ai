package minijava.comparator;

import java.util.Comparator;

import minijava.Program;

public class ProgramComparatorByFit implements Comparator<Program> {
    public int compare(Program program1, Program program2) { 
		int compare = FitnessComparators.BY_FIT.compare(program1.fitness, program2.fitness);
		return compare;
    }
}