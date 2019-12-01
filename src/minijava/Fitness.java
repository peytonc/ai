package minijava;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.math3.distribution.NormalDistribution;

public class Fitness {
	// sample size parameters
	public long generation;					// number of generations (or days) this program has survived
	public BigInteger count;				// total samples or generation*Tests.MAX_TEST_VECTORS, for Welford's online algorithm
	// size	parameters
	public int size;						// size of source code, which is nearly a fixed size (minor exception with identify # length)
	// CPU time parameters
	public long meanSpeed;					// mean CPU time taken to execute program across samples (i.e. # of generations)
	public long sumSpeed;					// aggregates the speeds, for Welford's online algorithm
	// BY_MEAN_ERROR and BY_MEAN_ERROR_CONFIDENCE_INTERVAL parameters
	public BigInteger meanError;			// sample mean of error (between actual and expected), for Welford's online algorithm
	private BigInteger sumError;			// aggregates the difference between actual and expected, for Welford's online algorithm
	public BigInteger sumErrorM2;			// aggregates the squared distance from the sample mean error, for Welford's online algorithm
	public BigInteger meanErrorScaled;		// scaled when speed/size is over allocation
	public BigInteger meanErrorConfidenceInterval;			// sample mean error plus maximum value of confidence interval
	public BigInteger meanErrorConfidenceIntervalScaled;	// scaled when speed/size is over allocation
	// BY_MEAN_CORRECT and BY_MEAN_CORRECT_CONFIDENCE_INTERVAL parameters
	public long meanCorrect;						// number of samples that are correct (e.g. no difference between actual and expected)
	private long sumCorrect;						// aggregates number of correct, for Welford's online algorithm
	public long sumCorrectM2;					// aggregates the squared distance from the mean correct, for Welford's online algorithm
	public long meanCorrectScaled;				// scaled when speed/size is over allocation
	public long meanCorrectConfidenceInterval;	// sample mean error plus maximum value of confidence interval
	public long meanCorrectConfidenceIntervalScaled;	// scaled when speed/size is over allocation
	// BY_COMBINED parameters
	public BigInteger combinedFunction;		// a combined function of: correct, mean confidence interval, speed confidence interval, and size
	// program control parameters
	public boolean isComplete;				// successfully completed all test cases?
	public boolean isMarginOfErrorFinal;	// is MarginOfErrorFinal less than 1, implies high confidence of sample mean converged to population mean 
	// scaling parameters
	public long numeratorScaled;			// for scaling when speed/size is over allocation
	public long denominatorScaled;			// for scaling when speed/size is over allocation
	// statistical parameters
	private static final NormalDistribution normalDistribution = new NormalDistribution();	// assume normal (TODO random programs make random probability mass function)
	private static final double confidenceLevel = 98.5;	// confidence level of confidence interval, in [0,100%)
	private static final double alpha = 1.0-(confidenceLevel/100.0);
	private static final double zScore = normalDistribution.inverseCumulativeProbability(1.0-(alpha/2.0));
	private static final BigInteger zScore10000000000 = BigDecimal.valueOf(zScore * Constants.I10000000000.doubleValue()).toBigInteger();

	public Fitness() {
		// sample size parameters
		generation = 0;					// start at 0 and program will increment
		count = Constants.I0;
		// size parameters
		size = Integer.MAX_VALUE;
		// CPU time parameters
		meanSpeed = 0;
		sumSpeed = 0;
		// BY_MEAN_ERROR and BY_MEAN_ERROR_CONFIDENCE_INTERVAL parameters
		meanError = Constants.I0;
		sumError = Constants.I0;
		sumErrorM2 = Constants.I0;
		meanErrorScaled = Constants.I0;
		// BY_MEAN_CORRECT and BY_MEAN_CORRECT_CONFIDENCE_INTERVAL parameters
		meanCorrect = 0;
		sumCorrect = 0;
		sumCorrectM2 = 0;
		meanCorrectScaled = 0;
		// BY_COMBINED parameters
		combinedFunction = Constants.I0;
		// program control parameters
		isComplete = false;
		isMarginOfErrorFinal = false;
		// scaling parameters
		numeratorScaled = 1;
		denominatorScaled = 1;
	}
	
	public Fitness(Fitness fitness) {
		// sample size parameters
		this.generation = fitness.generation;
		this.count = fitness.count;
		// size	parameters
		this.size = fitness.size;
		// CPU time parameters
		this.meanSpeed = fitness.meanSpeed;
		this.sumSpeed = fitness.sumSpeed;
		// BY_MEAN_ERROR and BY_MEAN_ERROR_CONFIDENCE_INTERVAL parameters
		this.meanError = fitness.meanError;
		this.sumError = fitness.sumError;
		this.sumErrorM2 = fitness.sumErrorM2;
		this.meanErrorScaled = fitness.meanErrorScaled;
		this.meanErrorConfidenceInterval = fitness.meanErrorConfidenceInterval;
		this.meanErrorConfidenceIntervalScaled = fitness.meanErrorConfidenceIntervalScaled;
		// BY_MEAN_CORRECT and BY_MEAN_CORRECT_CONFIDENCE_INTERVAL parameters
		this.meanCorrect = fitness.meanCorrect;
		this.sumCorrect = fitness.sumCorrect;
		this.sumCorrectM2 = fitness.sumCorrectM2;
		this.meanCorrectScaled = fitness.meanCorrectScaled;
		this.meanCorrectConfidenceInterval = fitness.meanCorrectConfidenceInterval;
		this.meanCorrectConfidenceIntervalScaled = fitness.meanCorrectConfidenceIntervalScaled;
		// BY_COMBINED parameters
		this.combinedFunction = fitness.combinedFunction;
		// scaling parameters
		this.numeratorScaled = fitness.numeratorScaled;
		this.denominatorScaled = fitness.denominatorScaled;
		// scaling parameters
		this.isComplete = fitness.isComplete;
		this.isMarginOfErrorFinal = fitness.isMarginOfErrorFinal;
	}
	
	// add the sampled difference to the fitness parameters
	// update parameters of Welford's online algorithm, used to calculate statistical moments (e.g. mean, variance)
	// modification to Welford's online algorithm to use integer sum, because it needs to scale with large integer division (and stay integer) instead of reals
	public void addSampleDifference(BigInteger sampledDifference) {
		if(sampledDifference.compareTo(Constants.I0) == 0) {
			meanCorrect++;
		}
		
		count = count.add(Constants.I1);
		BigInteger deltaPrevious = sampledDifference.subtract(meanError);
		sumError = sumError.add(sampledDifference);
		meanError = sumError.divide(count);
		BigInteger deltaCurrent = sampledDifference.subtract(meanError);
		sumErrorM2 = sumErrorM2.add(deltaPrevious.multiply(deltaCurrent));
	}
	
	// add the sampled speed to the fitness parameters 
	public void addSampleSpeed(long sampleSpeed) {
		sumSpeed += sampleSpeed;
		meanSpeed = sumSpeed/generation;
	}
	
	// reset all daily fitness parameters
	public void reset(int size) {
		isComplete = false;
		meanCorrect = 0;
		this.size = size;
	}
	
	public void update() {
		if(meanSpeed > Environment.getEnvironment().speedBeforeRestrict) {
			// if speed exceeds restriction then punish fitness (by increasing value)
			numeratorScaled = meanSpeed;
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
		meanCorrectScaled = (int)(meanCorrect*denominatorScaled/numeratorScaled);	// flip numerator and denominator to punish fitness, because numeratorScaled>denominatorScaled
		meanErrorScaled = meanError.multiply(numeratorScaledBigInteger).divide(denominatorScaledBigInteger);
		// sample standard deviation
		BigInteger standardDeviation = Util.sqrt(sumErrorM2.divide(count.subtract(Constants.I1)));
		// calculate maximum value of confidence interval range
		BigInteger marginOfError = zScore10000000000.multiply(standardDeviation).divide(Util.sqrt(count)).divide(Constants.I10000000000);
		if(marginOfError.compareTo(Constants.I0) == 0) {
			isMarginOfErrorFinal = true;
		}
		meanErrorConfidenceInterval = meanError.add(marginOfError);
		meanErrorConfidenceIntervalScaled = meanErrorConfidenceInterval.multiply(numeratorScaledBigInteger).divide(denominatorScaledBigInteger);
		combinedFunction = meanErrorConfidenceIntervalScaled.multiply(BigInteger.valueOf(Tests.MAX_TEST_VECTORS));
		if(meanCorrect>0) {
			combinedFunction = combinedFunction.divide(BigInteger.valueOf(meanCorrect));
		}
	}

	
	public String toString() {
		return generation + "\t" + meanError + "\t" + meanCorrect + "\t" + meanErrorConfidenceInterval + "\t" + combinedFunction + "\t" + meanSpeed + "\t" + size;
	}
}
