package minijava.comparator;

import java.util.Comparator;

import minijava.Fitness;

public class FitnessComparatorByCombined implements Comparator<Fitness> {
    public int compare(Fitness fitness1, Fitness fitness2) { 
		int compare = 0;
		compare = fitness1.combinedFunction.compareTo(fitness2.combinedFunction);
		if(compare == 0) {
			compare = Long.compare(fitness1.meanSpeed, fitness2.meanSpeed);
			if(compare == 0) {
				compare = Integer.compare(fitness1.size, fitness2.size);
				if(compare == 0) {
					compare = Long.compare(fitness2.meanCorrectConfidenceIntervalScaled, fitness1.meanCorrectConfidenceIntervalScaled);		// flip order to obtain largest correct first
					if(compare == 0) {
						compare = fitness1.meanErrorConfidenceIntervalScaled.compareTo(fitness2.meanErrorConfidenceIntervalScaled);
					}
				}
			}
		}
		return compare;
    } 
}
