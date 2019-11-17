package minijava.comparator;

import java.util.Comparator;

import minijava.Fitness;

public class FitnessComparatorByFit implements Comparator<Fitness> {
    public int compare(Fitness fitness1, Fitness fitness2) { 
		int compare = 0;
		
		if(fitness1.speed==Integer.MAX_VALUE || fitness2.speed==Integer.MAX_VALUE) {
			compare = Long.compare(fitness1.speed, fitness2.speed);
		}
		if(compare == 0) {
			compare = fitness1.fit.compareTo(fitness2.fit);
			if(compare == 0) {
				compare = fitness1.difference.compareTo(fitness2.difference);
				if(compare == 0) {
					compare = Integer.compare(fitness1.correct, fitness2.correct);
					if(compare == 0) {
						compare = Long.compare(fitness1.speed, fitness2.speed);
						if(compare == 0) {
							compare = Integer.compare(fitness1.size, fitness2.size);
							if(compare == 0) {
								compare = Long.compare(fitness2.generationalFitness, fitness1.generationalFitness);		// flip order to obtain largest generationalFitness first
							}
						}
					}
				}
			}
		}
		return compare;
    } 
}
