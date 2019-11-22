package minijava;

import java.math.BigInteger;

public class Fitness {
	public long generation;
	public long generationalFitness;
	public long speed;
	public int size;
	public boolean isComplete;
	public int correct;
	public int correctScaled;
	public BigInteger difference;
	public BigInteger differenceScaled;
	public long numeratorScaled;
	public long denominatorScaled;

	public Fitness() {
		generation = 0;
		generationalFitness = 0;
		speed = Integer.MAX_VALUE;
		size = Integer.MAX_VALUE;
		isComplete = false;
		correct = 0;
		correctScaled = 0;
		difference = Constants.LONG_MAX_VALUE;
		differenceScaled = Constants.LONG_MAX_VALUE;
		numeratorScaled = 1;
		denominatorScaled = 1;
	}
	
	public void updateScaled() {
		if(speed > Environment.getEnvironment().speedBeforeRestrict) {
			// if speed exceeds restriction then punish fitness (by increasing value)
			numeratorScaled *= speed;
			denominatorScaled *= Environment.getEnvironment().speedBeforeRestrict;
		}
		if(size > Environment.getEnvironment().sizeBeforeRestrict) {
			// if size exceeds restriction then punish fitness (by increasing value)
			numeratorScaled *= size;
			denominatorScaled *= Environment.getEnvironment().sizeBeforeRestrict;
		}
		correctScaled = (int)(correct*denominatorScaled/numeratorScaled);	// flip numerator and denominator to punish fitness, because numeratorScaled>denominatorScaled
		differenceScaled = difference.multiply(BigInteger.valueOf(numeratorScaled)).divide(BigInteger.valueOf(denominatorScaled));
	}
	
	public String toString() {
		//return "Fitness{" + "gen=" + generation + ",genFit=" + generationalFitness + ",diff=" + difference + ",correct=" + correct + ",speed=" + speed + ",size=" + size  + "}";
		return generation + "\t" + generationalFitness + "\t" + difference + "\t" + correct + "\t" + speed + "\t" + size;
	}
}
