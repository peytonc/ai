package minijava.comparator;

import java.util.Comparator;

import minijava.Fitness;

public class FitnessComparators {
	public static final Comparator<Fitness> BY_FIT = new FitnessComparatorByFit();
	public static final Comparator<Fitness> BY_DIFFERENCE = new FitnessComparatorByDifference();
	public static final Comparator<Fitness> BY_GENERATIONAL = new FitnessComparatorByGenerational();
}
