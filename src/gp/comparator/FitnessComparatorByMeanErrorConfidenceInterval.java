package gp.comparator;

import java.util.Comparator;

import gp.Fitness;

public class FitnessComparatorByMeanErrorConfidenceInterval implements Comparator<Fitness> {
    public int compare(Fitness fitness1, Fitness fitness2) { 
		int compare = 0;
		compare = fitness1.meanErrorConfidenceIntervalScaled.compareTo(fitness2.meanErrorConfidenceIntervalScaled);
		if(compare == 0) {
			compare = Long.compare(fitness1.meanSpeed, fitness2.meanSpeed);
			if(compare == 0) {
				compare = Integer.compare(fitness1.size, fitness2.size);
				if(compare == 0) {
					compare = fitness1.meanErrorScaled.compareTo(fitness2.meanErrorScaled);
					if(compare == 0) {
						// flip order to obtain largest correct first
						compare = Double.compare(fitness2.meanCorrectScaled, fitness1.meanCorrectScaled);
					}
				}
			}
		}
		return compare;
    } 
}
