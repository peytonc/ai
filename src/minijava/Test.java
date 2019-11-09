package minijava;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test {
	public List<Long> listTest;
	public List<ArrayList<Long>> listAnswer;
	
	private static final Random RANDOM = new Random(GP.RANDOM_SEED);
	private static final int MAX_TEST_VECTOR_SIZE = 1;
	private static final int MAX_PRIME_BITS = 50;
	private static final int MIN_FACTORS = 2;
	private static final int MAX_FACTORS = 6;
	private static final Long LARGE_NUMBER = new Long(Long.MAX_VALUE/65536);
	
	public Test() {
		listTest = new ArrayList<Long>(MAX_TEST_VECTOR_SIZE);
		listAnswer = new ArrayList<ArrayList<Long>>(MAX_TEST_VECTOR_SIZE);
		createTest();
	}
	
	public Long getDifference(List<Long> listActuals) {
		if(listActuals == null || listAnswer.size()!=listActuals.size()) {
			return null;
		} else if(listAnswer.size()==listActuals.size()) {
			Long differenceTotal = new Long(0);
			Long difference;
			Long actual;
			for(int index=0; index<listAnswer.size(); index++) {
				actual = listActuals.get(index);
				if(actual==null || actual==Long.MAX_VALUE) {
					return null;
				}
				
				boolean isMultipleOfComposite = actual % listTest.get(index) == 0;	// ignore actuals that are a multiple of test composite
				Long smallestDifference = Long.MAX_VALUE;
				for(Long primeFactor : listAnswer.get(index)) {
					if(!isMultipleOfComposite && actual>=2 && actual%primeFactor == 0) {
						smallestDifference = new Long(0);	// actual is factor
						break;
					}
					difference = Math.abs(primeFactor - actual);
					if(smallestDifference > difference) {
						smallestDifference = difference;
					}
				}
				if(smallestDifference>LARGE_NUMBER) {
					return null;
				}
				differenceTotal += smallestDifference;
			}
			return differenceTotal;
		} else {
			return null;
		}
	}
	
	// random composite numbers
	private void createTest() {
		for(int index=0; index<MAX_TEST_VECTOR_SIZE; index++) {
			BigInteger composite = Util.I1;
			int factors = RANDOM.nextInt(MAX_FACTORS-MIN_FACTORS+1)+MIN_FACTORS;
			int numberPrimeBits = MAX_PRIME_BITS/factors;
			ArrayList<Long> listPrimeFactors = new ArrayList<Long>(factors);
			for(int factor=0; factor<factors; factor++) {
				BigInteger primeNumber = new BigInteger(numberPrimeBits, 10, RANDOM);
				composite = composite.multiply(primeNumber);
				listPrimeFactors.add(primeNumber.longValue());
			}
			listTest.add(new Long(composite.longValue()));
			listAnswer.add(listPrimeFactors);
		}
	}
}
