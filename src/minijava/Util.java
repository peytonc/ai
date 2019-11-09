package minijava;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Util {

	public static long f(long functionIndex, long long1) {
		final int maxTypes = 5;
		int type = (int)functionIndex%maxTypes;
		long returnValue = 0;
		BigInteger bigInteger1;
		
		switch(type) {
			case 0:	// sqrt
			    bigInteger1 = BigInteger.valueOf(long1);
			    bigInteger1 = sqrt(bigInteger1);
			    returnValue = bigInteger1.longValue();
			    break;
			case 1:	// abs
			    bigInteger1 = BigInteger.valueOf(long1);
			    bigInteger1 = bigInteger1.abs();
			    returnValue = bigInteger1.longValue();
			    break;
			case 2:	// is Prime?
			    bigInteger1 = BigInteger.valueOf(long1);
			    if(bigInteger1.isProbablePrime(10)) {
			    	returnValue = 1;
			    } else {
		    		returnValue = 0;
		    	}
			    break;
			case 3:	// Cantor pairing, w=floor((sqrt(8i+1)-1)/2)
				bigInteger1 = BigInteger.valueOf(long1);
				bigInteger1 = sqrt(bigInteger1.multiply(Constants.I8).add(Constants.I1)).subtract(Constants.I1).divide(Constants.I2);
				returnValue = bigInteger1.longValue();
			    break;	
			case 4:	// Cantor pairing, t=(w*w+w)/2, w=floor((sqrt(8i+1)-1)/2)
				bigInteger1 = BigInteger.valueOf(long1);
				bigInteger1 = sqrt(bigInteger1.multiply(Constants.I8).add(Constants.I1)).subtract(Constants.I1).divide(Constants.I2);
				bigInteger1 = bigInteger1.multiply(bigInteger1).add(bigInteger1).divide(Constants.I2);
				returnValue = bigInteger1.longValue();
			    break;
		}
		
		return returnValue;
	}
	
	public static long f(long functionIndex, long long1, long long2) {
		final int maxTypes = 2;
		int type = (int)functionIndex%maxTypes;
		long returnValue = 0;
		BigInteger bigInteger1, bigInteger2;
		
		switch(type) {
			case 0:	// gcd
			    bigInteger1 = BigInteger.valueOf(long1);
			    bigInteger2 = BigInteger.valueOf(long2);
			    bigInteger1 = bigInteger1.gcd(bigInteger2);
			    returnValue = bigInteger1.longValue();
			    break;
			case 1:	// a^2 mod n
				bigInteger1 = BigInteger.valueOf(long1);
				bigInteger2 = BigInteger.valueOf(long2);
				bigInteger1 = bigInteger1.multiply(bigInteger1).mod(bigInteger2);
				returnValue = bigInteger1.longValue();
			    break;	
		}
		
		return returnValue;
	}
	
	public static long f(long functionIndex, long long1, long long2, long long3) {
		final int maxTypes = 1;
		int type = (int)functionIndex%maxTypes;
		long returnValue = 0;
		BigInteger bigInteger1, bigInteger2, bigInteger3;
		
		switch(type) {
			case 0:	// modPow or b^e mod n
			    bigInteger1 = BigInteger.valueOf(long1);
			    bigInteger2 = BigInteger.valueOf(long2);
			    bigInteger3 = BigInteger.valueOf(long3);
			    bigInteger1 = bigInteger1.modPow(bigInteger2, bigInteger3);
			    returnValue = bigInteger1.longValue();
			    break;
		}
		
		return returnValue;
	}
	
    public static BigInteger sqrt(BigInteger m) {
        //Uses the Newton method to find largest integer whose square does not exceed m
        //We search for a zero of f(x)=x^2-p ==>  note that derivative f'(x)=2x
        int diff=m.compareTo(BigInteger.ZERO);
        //Throw an exception for negative arguments
        if (diff<0) throw new IllegalArgumentException("Cannot compute square root of a negative integer!");
        //Return 0 in case m is 0
        if (diff==0) return Constants.I0;
        BigDecimal two=new BigDecimal(Constants.I2);
        //Convert the parameter to a BigDecimal
        BigDecimal n=new BigDecimal(m);
        //Begin with an initial guess-the square root will be half the size of m
        //Make a byte array at least that long, & set bit in the high order byte
        byte[] barray=new byte[m.bitLength()/16+1];
        barray[0]=(byte)255;
        //This is the first guess-it will be too high
        BigDecimal r=new BigDecimal(new BigInteger(1,barray));
        //Next approximation is computed by taking r-f(r)/f'(r)
        r=r.subtract(r.multiply(r).subtract(n).divide(r.multiply(two),BigDecimal.ROUND_UP));
        //As long as our new approximation squared exceeds m, we continue to approximate
        while (r.multiply(r).compareTo(n)>0) {
           r=r.subtract(r.multiply(r).subtract(n).divide(r.multiply(two),BigDecimal.ROUND_UP));
        }
        return r.toBigInteger();
      }
}
