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
			for(int index=0; index<MAX_TEST_VECTORS; index++) {
				BigInteger differenceError = listTests.get(index).getDifference(vectors.get(index));
				if(differenceError == null) {
					return false;
				} else {
					fitness.addSampleDifference(differenceError);
				}
			}
			fitness.update();	// update statistical moments
			return true;
		} else {
			return false;
		}
	}
}
