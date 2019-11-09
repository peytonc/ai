package minijava;

import java.util.Comparator;

public class Fitness implements Comparable<Fitness> {
	public long generation;
	public long generationalFitness;
	public long difference;
	public long speed;
	public int size;
	public int sizeBeforeRestrict;
	public int speedBeforeRestrict;
	public boolean isComplete;
	
	public Fitness() {
		generation = 0;
		generationalFitness = 0;
		difference = Long.MAX_VALUE;
		speed = Integer.MAX_VALUE;
		size = Integer.MAX_VALUE;
		sizeBeforeRestrict = 0;
		speedBeforeRestrict = 0;
		isComplete = false;
	}
	
	public String toString() {
		return "Fitness{" + "gen=" + generation + ",genFit=" + generationalFitness + ",diff=" + difference + ",speed=" + speed + ",size=" + size  + "}";
	}
	
	@Override
	public int compareTo(Fitness fitness) {
		FitnessComparatorByDifference fitnessComparatorByDifference = new FitnessComparatorByDifference();
		int compare = fitnessComparatorByDifference.compare(this, fitness);
		return compare;
	}
}

class FitnessComparatorByDifference implements Comparator<Fitness> {
    public int compare(Fitness fitness1, Fitness fitness2) { 
		int compare = 0;
		if(fitness1.speed==Integer.MAX_VALUE || fitness2.speed==Integer.MAX_VALUE) {
			compare = Long.compare(fitness1.speed, fitness2.speed);
		} else if(fitness1.size > fitness1.sizeBeforeRestrict || fitness2.size > fitness2.sizeBeforeRestrict) {
			compare = Double.compare(fitness1.difference * (fitness1.size/fitness1.sizeBeforeRestrict), fitness2.difference * (fitness2.size/fitness2.sizeBeforeRestrict));
		} else if(fitness1.speed > fitness1.speedBeforeRestrict || fitness2.speed > fitness2.speedBeforeRestrict) {
			compare = Double.compare(fitness1.difference * (fitness1.speed/fitness1.speedBeforeRestrict), fitness2.difference * (fitness2.speed/fitness2.speedBeforeRestrict));
		}
		if(compare == 0) {
			compare = Long.compare(fitness1.difference, fitness2.difference);
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
		return compare;
    } 
}

class FitnessComparatorByGenerational implements Comparator<Fitness> {
    public int compare(Fitness fitness1, Fitness fitness2) { 
		int compare = 0;
		if(fitness1.speed==Integer.MAX_VALUE || fitness2.speed==Integer.MAX_VALUE) {
			compare = Long.compare(fitness1.speed, fitness2.speed);
		} else if(fitness1.difference==Long.MAX_VALUE || fitness2.difference==Long.MAX_VALUE) {
			compare = Long.compare(fitness1.difference, fitness2.difference);
		} else if(fitness1.size > fitness1.sizeBeforeRestrict || fitness2.size > fitness2.sizeBeforeRestrict) {
			compare = Double.compare(fitness1.difference * (fitness1.size/fitness1.sizeBeforeRestrict), fitness2.difference * (fitness2.size/fitness2.sizeBeforeRestrict));
		} else if(fitness1.speed > fitness1.speedBeforeRestrict || fitness2.speed > fitness2.speedBeforeRestrict) {
			compare = Double.compare(fitness1.difference * (fitness1.speed/fitness1.speedBeforeRestrict), fitness2.difference * (fitness2.speed/fitness2.speedBeforeRestrict));
		}
		if(compare == 0) {
			compare = Long.compare(fitness2.generationalFitness, fitness1.generationalFitness);		// flip order to obtain largest generationalFitness first
			if(compare == 0) {
				compare = Long.compare(fitness1.difference, fitness2.difference);
				if(compare == 0) {
					compare = Long.compare(fitness1.speed, fitness2.speed);
					if(compare == 0) {
						compare = Integer.compare(fitness1.size, fitness2.size);
					}
				}
			}
		}
		return compare;
    } 
}