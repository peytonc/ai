package minijava.comparator;

import java.util.Comparator;

import minijava.Fitness;

public class FitnessComparators {
	public static final Comparator<Fitness> BY_SUM = new FitnessComparatorBySum();
	public static final Comparator<Fitness> BY_CORRECT = new FitnessComparatorByCorrect();
	public static final Comparator<Fitness> BY_CONFIDENCE_INTERVAL = new FitnessComparatorByConfidenceInterval();
}