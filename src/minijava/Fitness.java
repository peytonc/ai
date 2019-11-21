package minijava;

import java.math.BigInteger;

public class Fitness {
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
}
