package gp.comparator;

import java.util.Comparator;

import gp.Fitness;

public class FitnessComparatorByMeanCorrect implements Comparator<Fitness> {
    public int compare(Fitness fitness1, Fitness fitness2) { 
		int compare = 0;
		// flip order to obtain largest correct first
		compare = Double.compare(fitness2.meanCorrectScaled, fitness1.meanCorrectScaled);
		if(compare == 0) {
			compare = fitness1.combinedFunction.compareTo(fitness2.combinedFunction);
			if(compare == 0) {
				compare = Long.compare(fitness1.meanSpeed, fitness2.meanSpeed);
				if(compare == 0) {
					compare = Integer.compare(fitness1.size, fitness2.size);
				}
			}
		}
		return compare;
    } 
}
