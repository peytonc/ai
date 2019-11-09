package minijava;

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
	
	public Long getDifferences(ArrayList<ArrayList<Long>> vectors) {
		if(vectors == null) {
			return null;
		} else if(listTests.size()==vectors.size()) {
			Long differenceTotal = new Long(0);
			for(int index=0; index<listTests.size(); index++) {
				Long difference = listTests.get(index).getDifference(vectors.get(index));
				if(difference == null) {
					return null;
				} else {
					differenceTotal += difference;
				}
			}
			return differenceTotal;
		} else {
			return null;
		}
	}
}
