package minijava;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public final class Tests {
	private static final Tests tests = new Tests();
	
	public static final int MAX_TEST_VECTORS = 500;
	public static final BigInteger MAX_TEST_VECTORS_BIG_INTEGER = BigInteger.valueOf(MAX_TEST_VECTORS);
	public List<Test> listTests;
	
	private Tests() {
	}
	
	public static Tests getTests() {
        return tests; 
	}
	
	public void createTests() {
		listTests = new ArrayList<Test>(MAX_TEST_VECTORS);
		for(int index=0; index<MAX_TEST_VECTORS; index++) {
			Test test = new Test();
			listTests.add(test);
		}
	}
	
	public BigInteger[] getDifferences(ArrayList<ArrayList<Long>> vectors) {
		if(vectors == null) {
			return null;
		} else if(listTests.size()==vectors.size()) {
			BigInteger returnValue[] = new BigInteger[2];
			BigInteger differenceTotal = Constants.I0;
			BigInteger correct = Constants.I0;
			for(int index=0; index<MAX_TEST_VECTORS; index++) {
				BigInteger difference = listTests.get(index).getDifference(vectors.get(index));
				if(difference == null) {
					return null;
				} else {
					differenceTotal = differenceTotal.add(difference);
					if(difference.compareTo(Constants.I0) == 0) {
						correct = correct.add(Constants.I1);
					}
				}
			}
			returnValue[0] = differenceTotal;
			returnValue[1] = correct;
			return returnValue;
		} else {
			return null;
		}
	}
}
