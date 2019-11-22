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
	
	public boolean getDifferences(ArrayList<ArrayList<Long>> vectors, Fitness fitness) {
		if(vectors == null) {
			return false;
		} else if(listTests.size()==vectors.size()) {
			BigInteger differenceTotal = Constants.I0;
			int correct = 0;
			for(int index=0; index<MAX_TEST_VECTORS; index++) {
				BigInteger difference = listTests.get(index).getDifference(vectors.get(index));
				if(difference == null) {
					return false;
				} else {
					differenceTotal = differenceTotal.add(difference);
					if(difference.compareTo(Constants.I0) == 0) {
						correct++;
					}
				}
			}
			fitness.difference = differenceTotal;
			fitness.correct = correct;
			return true;
		} else {
			return false;
		}
	}
}
