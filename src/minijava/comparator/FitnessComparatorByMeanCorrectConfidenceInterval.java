package minijava.comparator;

import java.util.Comparator;

import minijava.Fitness;

public class FitnessComparatorByMeanCorrectConfidenceInterval implements Comparator<Fitness> {
    public int compare(Fitness fitness1, Fitness fitness2) { 
		int compare = 0;
		compare = fitness2.meanCorrectConfidenceIntervalScaled.compareTo(fitness1.meanCorrectConfidenceIntervalScaled);		// flip order to obtain largest correct first
		if(compare == 0) {
			compare = Long.compare(fitness1.meanSpeed, fitness2.meanSpeed);
			if(compare == 0) {
				compare = Integer.compare(fitness1.size, fitness2.size);
				if(compare == 0) {
					compare = fitness1.meanErrorScaled.compareTo(fitness2.meanErrorScaled);
					if(compare == 0) {
						compare = Integer.compare(fitness2.meanCorrectScaled, fitness1.meanCorrectScaled);		// flip order to obtain largest correct first
					}
				}
			}
		}
		return compare;
    } 
}
