package minijava;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tests {
	public List<Test> listTests;
	public List<Long> listDifferencesBase;
	
	public static final int MAX_TEST_VECTORS = 500;
	
	public void createTests() {
		List<Test> listTests = new ArrayList<Test>(MAX_TEST_VECTORS);
		for(int index=0; index<MAX_TEST_VECTORS; index++) {
			Test test = new Test();
			listTests.add(test);
		}
		this.listTests = Collections.unmodifiableList(listTests);
		listDifferencesBase = Collections.unmodifiableList(getDifferenceTestAnswer(this.listTests, this.listTests));
	}
	
	public static List<Long> getDifferenceAnswerAnswer(List<Test> list, ArrayList<ArrayList<Long>> vectors) {
		if(list == null || vectors == null) {
			return null;
		} else if(list.size()>0 && list.size()==vectors.size()) {
			List<Long> listDifferences = new ArrayList<Long>(list.size());
			for(int index=0; index<list.size(); index++) {
				Long difference = Test.getDifference(list.get(index).listAnswer, vectors.get(index));
				if(difference == null) {
					return null;
				} else {
					listDifferences.add(difference);
				}
			}
			return listDifferences;
		} else {
			return null;
		}
	}
	
	private static List<Long> getDifferenceTestAnswer(List<Test> list1, List<Test> list2) {
		List<Long> listDifferences = new ArrayList<Long>(list1.size());
		for(int index=0; index<list1.size(); index++) {
			Long difference = Test.getDifference(list1.get(index).listTest, list2.get(index).listAnswer);
			if(difference == null) {
				return null;
			} else {
				listDifferences.add(difference);
			}
		}
		return listDifferences;
	}
}
