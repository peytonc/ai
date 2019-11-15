package minijava;

import java.math.BigInteger;
import java.util.Comparator;

public class Fitness implements Comparable<Fitness> {
	public int correct;
	public BigInteger fit;
	public long generation;
	public long generationalFitness;
	public BigInteger difference;
	public long speed;
	public int size;
	public boolean isComplete;
	
	
	
	public Fitness() {
		correct = 0;
		fit = Constants.LONG_MAX_VALUE;
		generation = 0;
		generationalFitness = 0;
		difference = Constants.LONG_MAX_VALUE;
		speed = Integer.MAX_VALUE;
		size = Integer.MAX_VALUE;
		isComplete = false;
	}
	
	public void calculateFitness() {
		fit = difference;
		if(speed > Environment.getEnvironment().speedBeforeRestrict) {
			// if speed exceeds restriction then punish fitness (by increasing value)
			// first use multiplication because (integer/integer) is non-continuous and a bad multiplier near [1,2]
			fit = fit.multiply(BigInteger.valueOf(speed)).divide(Environment.getEnvironment().speedBeforeRestrictBigInteger);
		}
		if(size > Environment.getEnvironment().sizeBeforeRestrict) {
			// if size exceeds restriction then punish fitness (by increasing value)
			// first use multiplication because (integer/integer) is non-continuous and a bad multiplier near [1,2]
			fit = fit.multiply(BigInteger.valueOf(size)).divide(Environment.getEnvironment().sizeBeforeRestrictBigInteger);
		}
		if(correct > 0) {
			// if correct answers were found then reward fitness (by reducing value) by wrong/total
			BigInteger wrong = Tests.MAX_TEST_VECTORS_BIG_INTEGER.subtract(BigInteger.valueOf(correct));
			// first use multiplication because (integer/integer) is non-continuous and a bad multiplier near [1,2]
			fit = fit.multiply(wrong).divide(Tests.MAX_TEST_VECTORS_BIG_INTEGER);
		}
	}
	
	public String toString() {
		//return "Fitness{" + "gen=" + generation + ",genFit=" + generationalFitness + ",fit=" + fit + ",diff=" + difference + ",correct=" + correct + ",speed=" + speed + ",size=" + size  + "}";
		return generation + "\t" + generationalFitness + "\t" + fit + "\t" + difference + "\t" + correct + "\t" + speed + "\t" + size;
	}
	
	@Override
	public int compareTo(Fitness fitness) {
		FitnessComparatorByFit fitnessComparatorByFit = new FitnessComparatorByFit();
		int compare = fitnessComparatorByFit.compare(this, fitness);
		return compare;
	}
}

class FitnessComparatorByFit implements Comparator<Fitness> {
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

class FitnessComparatorByGenerational implements Comparator<Fitness> {
    public int compare(Fitness fitness1, Fitness fitness2) { 
		int compare = 0;
		if(fitness1.speed==Integer.MAX_VALUE || fitness2.speed==Integer.MAX_VALUE) {
			compare = Long.compare(fitness1.speed, fitness2.speed);
		} else if(fitness1.difference.compareTo(Constants.LONG_MAX_VALUE)>=0 || fitness2.difference.compareTo(Constants.LONG_MAX_VALUE)>=0) {
			compare = fitness1.difference.compareTo(fitness2.difference);
		} 
		if(compare == 0) {
			compare = Long.compare(fitness2.generationalFitness, fitness1.generationalFitness);		// flip order to obtain largest generationalFitness first
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
							}
						}
					}
				}
			}
		}
		return compare;
    } 
}