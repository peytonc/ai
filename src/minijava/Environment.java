package minijava;

import java.math.BigInteger;

public final class Environment {
	private static final Environment environment = new Environment();

	public static final int DAYS_PER_YEAR = 1000;
	public static final double RESTRICT_MIN_PERCENT = 0.975;	// use small seasonal differences (i.e. RESTRICT_MAX_PERCENT-RESTRICT_MIN_PERCENT < 0.05)
	public static final double RESTRICT_MAX_PERCENT = 1.025;
	public static final int MAX_EXECUTE_MILLISECONDS = 2000;
	public static final int MAX_EXECUTE_MILLISECONDS_95PERCENT = (int) Math.floor(0.95*MAX_EXECUTE_MILLISECONDS);
	
	public int sizeBeforeRestrictMin = 0;
	public int sizeBeforeRestrictMax = 0;
	public int sizeBeforeRestrict = 0;
	public BigInteger sizeBeforeRestrictBigInteger = Constants.I0;
	private int speedBeforeRestrictMin = (int)(RESTRICT_MIN_PERCENT * MAX_EXECUTE_MILLISECONDS/2.0);
	private int speedBeforeRestrictMax = (int)(RESTRICT_MAX_PERCENT * MAX_EXECUTE_MILLISECONDS/2.0);
	public int speedBeforeRestrict = 0;
	public BigInteger speedBeforeRestrictBigInteger = Constants.I0;
	
	private Environment() {
	}
	
	public static Environment getEnvironment() {
        return environment; 
	}
	
	public void createDay(int day) {
		// model environment resource (specifically program size) as Summer-Winter-Summer or sizeBeforeRestrictMax-sizeBeforeRestrictMin-sizeBeforeRestrictMax
		double percent = (double)(day%DAYS_PER_YEAR)/DAYS_PER_YEAR;
		double cosineWithOffset = (Math.cos(percent*2.0*Math.PI)+1.0)/2.0;	// range in [0,1]
		sizeBeforeRestrict = (int)(sizeBeforeRestrictMin + cosineWithOffset*(sizeBeforeRestrictMax-sizeBeforeRestrictMin));
		sizeBeforeRestrictBigInteger = BigInteger.valueOf(sizeBeforeRestrict);
		speedBeforeRestrict = (int)(speedBeforeRestrictMin + cosineWithOffset*(speedBeforeRestrictMax-speedBeforeRestrictMin));
		speedBeforeRestrictBigInteger = BigInteger.valueOf(speedBeforeRestrict);
	}
	
	public void createYear(int sizeSourceLength) {
		sizeBeforeRestrictMin = (int)(RESTRICT_MIN_PERCENT * sizeSourceLength);
		sizeBeforeRestrictMax = (int)(RESTRICT_MAX_PERCENT * sizeSourceLength);
	}
}
