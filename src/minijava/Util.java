package minijava;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

public class Util {

	public static final BigInteger I1 = new BigInteger("1");
	public static final BigInteger I2 = new BigInteger("2");
	public static final BigInteger I8 = new BigInteger("8");
    
	public static void f(long functionIndex, ArrayList<Long> arrayList1, ArrayList<Long> arrayList2, long long1, long long2) {
		final int maxTypes = 7;
		int type = (int)functionIndex%maxTypes;
		int index1, index2, index3;
		BigInteger bigInteger1, bigInteger2, bigInteger3;
		
		switch(type) {
			case 0:	// gcd
				index1 = (int)long1%arrayList1.size();
				index2 = (int)long2%arrayList2.size();
			    bigInteger1 = BigInteger.valueOf(arrayList1.get(index1));
			    bigInteger2 = BigInteger.valueOf(arrayList2.get(index2));
			    bigInteger3 = bigInteger1.gcd(bigInteger2);
			    arrayList1.set(index1, bigInteger3.longValue());
			    break;	
			case 1:	// a^2 mod n
				index1 = (int)long1%arrayList1.size();
				index2 = (int)long2%arrayList2.size();
			    bigInteger1 = BigInteger.valueOf(arrayList1.get(index1));
			    bigInteger2 = BigInteger.valueOf(arrayList2.get(index2));
			    bigInteger3 = bigInteger1.multiply(bigInteger1).mod(bigInteger2);
			    arrayList1.set(index1, bigInteger3.longValue());
			    break;
			case 2:	// b^e mod n
				index1 = (int)long1%arrayList1.size();
				index2 = (int)long2%arrayList2.size();
				index3 = (int)(long1+1)%arrayList2.size();
			    bigInteger1 = BigInteger.valueOf(arrayList1.get(index1));
			    bigInteger2 = BigInteger.valueOf(arrayList2.get(index2));
			    bigInteger3 = BigInteger.valueOf(arrayList1.get(index3));
			    bigInteger3 = bigInteger1.modPow(bigInteger2, bigInteger3);
			    arrayList1.set(index1, bigInteger3.longValue());
			    break;	
			case 3:	// Cantor pairing (w parameter)
				index1 = (int)long1%arrayList1.size();
			    bigInteger1 = BigInteger.valueOf(arrayList1.get(index1));
			    bigInteger3 = sqrt(bigInteger1.multiply(I8).add(I1)).subtract(I1).divide(I2);
			    arrayList1.set(index1, bigInteger3.longValue());
			    break;	
			case 4:	// Cantor pairing (r parameter)
				index1 = (int)long1%arrayList1.size();
			    bigInteger1 = BigInteger.valueOf(arrayList1.get(index1));
			    bigInteger2 = sqrt(bigInteger1.multiply(I8).add(I1)).subtract(I1).divide(I2);
			    bigInteger3 = bigInteger2.multiply(bigInteger2).add(bigInteger2).divide(I2);
			    arrayList1.set(index1, bigInteger3.longValue());
			    break;
			case 5:	// sqrt
				index1 = (int)long1%arrayList1.size();
			    bigInteger1 = BigInteger.valueOf(arrayList1.get(index1));
			    bigInteger3 = sqrt(bigInteger1);
			    arrayList1.set(index1, bigInteger3.longValue());
			    break;
			case 6:	// swap if smaller
				index1 = (int)long1%arrayList1.size();
				index2 = (int)long2%arrayList2.size();
				if(arrayList1.get(index1) < arrayList2.get(index2)) {
					Long temp = new Long(arrayList1.get(index1));
					arrayList1.set(index1, arrayList2.get(index2));
					arrayList2.set(index2, temp);
				}
				break;
		}
	}
	
    public static BigInteger sqrt(BigInteger m) {
        //Uses the Newton method to find largest integer whose square does not exceed m
        //We search for a zero of f(x)=x^2-p ==>  note that derivative f'(x)=2x
        int diff=m.compareTo(BigInteger.ZERO);
        //Throw an exception for negative arguments
        if (diff<0) throw new IllegalArgumentException("Cannot compute square root of a negative integer!");
        //Return 0 in case m is 0
        if (diff==0) return BigInteger.valueOf(0);
        BigDecimal two=new BigDecimal(I2);
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
