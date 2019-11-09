package minijava;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Tests {
	public List<Test> listTests;
	
	public static final int MAX_TEST_VECTORS = 500;
	
	public Tests() {
		
	}
	
	public void createTests() {
		listTests = new ArrayList<Test>(MAX_TEST_VECTORS);
		for(int index=0; index<MAX_TEST_VECTORS; index++) {
			Test test = new Test();
			listTests.add(test);
		}
	}
	
	public BigInteger getDifferences(ArrayList<ArrayList<Long>> vectors) {
		if(vectors == null) {
			return null;
		} else if(listTests.size()==vectors.size()) {
			BigInteger differenceTotal = BigInteger.valueOf(0);
			for(int index=0; index<listTests.size(); index++) {
				BigInteger difference = listTests.get(index).getDifference(vectors.get(index));
				if(difference == null) {
					return null;
				} else {
					differenceTotal.add(difference);
				}
			}
			return differenceTotal;
		} else {
			return null;
		}
	}
}
