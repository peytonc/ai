package minijava;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.math3.distribution.NormalDistribution;

public class Fitness {
	
	public long generation;
	public long speed;
	public int size;
	public boolean isComplete;				// successfully completed all test cases?
	public boolean isMarginOfErrorFinal;	// is MarginOfErrorFinal less than 1, implies high confidence of sample mean converged to population mean 
	public int correct;
	public int correctScaled;
	public BigInteger sumScaled;
	public BigInteger confidenceIntervalUpper;
	public BigInteger confidenceIntervalUpperScaled;
	public long numeratorScaled;
	public long denominatorScaled;
	public BigInteger count;				// Welford's online algorithm
	public BigInteger sum;					// Welford's online algorithm
	public BigInteger sumSquareDifference;	// Welford's online algorithm
	
	private BigInteger mean;				// Welford's online algorithm
	private static final NormalDistribution normalDistribution = new NormalDistribution();
	private static final double confidenceLevel = 98.5;	// confidence level before final acceptance, in [0,100%) 
	private static final double alpha = 1.0-(confidenceLevel/100.0);
	private static final double zScore = normalDistribution.inverseCumulativeProbability(1.0-(alpha/2.0));
	private static final BigInteger zScore10000000000 = BigDecimal.valueOf(zScore * Constants.I10000000000.doubleValue()).toBigInteger();

	public Fitness() {
		generation = 0;
		speed = Integer.MAX_VALUE;
		size = Integer.MAX_VALUE;
		isComplete = false;
		isMarginOfErrorFinal = false;
		correct = 0;
		correctScaled = 0;
		sumScaled = Constants.LONG_MAX_VALUE;
		numeratorScaled = 1;
		denominatorScaled = 1;
		count = Constants.I0;
		mean = Constants.I0;
		sum = Constants.I0;
		sumSquareDifference = Constants.I0;
	}
	
	// add the sample to the fitness parameters 
	public void addSample(BigInteger sampledDifference) {
		if(sampledDifference.compareTo(Constants.I0) == 0) {
			correct++;
		}
		// update parameters of Welford's online algorithm, used to calculate statistical moments (e.g. mean, variance)
		// modification to Welford's online algorithm to use integer sum, because it needs to scale with large integer division (and stay integer) instead of reals
		count = count.add(Constants.I1);
		BigInteger deltaPrevious = sampledDifference.subtract(mean);
		sum = sum.add(sampledDifference);
		mean = sum.divide(count);
		BigInteger deltaCurrent = sampledDifference.subtract(mean);
		sumSquareDifference = sumSquareDifference.add(deltaPrevious.multiply(deltaCurrent));
	}
	
	// reset all daily fitness parameters
	public void reset(int size) {
		isComplete = false;
		correct = 0;
		this.size = size;
	}
	
	public void update() {
		if(speed > Environment.getEnvironment().speedBeforeRestrict) {
			// if speed exceeds restriction then punish fitness (by increasing value)
			numeratorScaled = speed;
			denominatorScaled = Environment.getEnvironment().speedBeforeRestrict;
		} else {
			// reset scaling factors before another day
			numeratorScaled = 1;
			denominatorScaled = 1;
		}
		if(size > Environment.getEnvironment().sizeBeforeRestrict) {
			// if size exceeds restriction then punish fitness (by increasing value)
			numeratorScaled *= size;
			denominatorScaled *= Environment.getEnvironment().sizeBeforeRestrict;
		}
		BigInteger denominatorScaledBigInteger = BigInteger.valueOf(denominatorScaled);
		BigInteger numeratorScaledBigInteger = BigInteger.valueOf(numeratorScaled);
		correctScaled = (int)(correct*denominatorScaled/numeratorScaled);	// flip numerator and denominator to punish fitness, because numeratorScaled>denominatorScaled
		sumScaled = sum.multiply(numeratorScaledBigInteger).divide(denominatorScaledBigInteger);
		// sample standard deviation
		BigInteger standardDeviation = Util.sqrt(sumSquareDifference.divide(count.subtract(Constants.I1)));
		// calculate maximum value of confidence interval range
		BigInteger marginOfError = zScore10000000000.multiply(standardDeviation).divide(Util.sqrt(count)).divide(Constants.I10000000000);
		if(marginOfError.compareTo(Constants.I0) == 0) {
			isMarginOfErrorFinal = true;
		}
		confidenceIntervalUpper = sum.divide(count).add(marginOfError);
		confidenceIntervalUpperScaled = confidenceIntervalUpper.multiply(numeratorScaledBigInteger).divide(denominatorScaledBigInteger);
	}

	
	public String toString() {
		return generation + "\t" + mean + "\t" + correct + "\t" + confidenceIntervalUpper + "\t" + speed + "\t" + size;
	}
}
