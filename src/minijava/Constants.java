package minijava;

import java.math.BigInteger;

public final class Constants {

    private Constants() {	// no instantiation of this class
    }
    
    public static final BigInteger LONG_MAX_VALUE = BigInteger.valueOf(Long.MAX_VALUE);
    
    public static final BigInteger I0 = BigInteger.valueOf(0);
	public static final BigInteger I1 = new BigInteger("1");
	public static final BigInteger I2 = new BigInteger("2");
	public static final BigInteger I8 = new BigInteger("8");
	
}