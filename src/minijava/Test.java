package minijava;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Test {
	public final List<Long> listTest;
	public final List<Long> listAnswer;
	
	private final static Random random = new Random(Main.randomSeed);
	private final static int maxTestVectorSize = 10;
	
	public Test() {
		List<Long> listTest = new ArrayList<Long>(maxTestVectorSize);
		createTest(listTest);
		this.listTest = Collections.unmodifiableList(listTest);
		List<Long> listAnswer = new ArrayList<Long>(listTest);
		createAnswer(listAnswer);
		this.listAnswer = Collections.unmodifiableList(listAnswer);
	}
	
	public static Long getDifference(List<Long> list1, List<Long> list2) {
		if(list1 == null || list2 == null || list1.size()!=list2.size()) {
			return null;
		} else if(list1.size()>0 && list1.size()==list2.size()) {
			Long difference = new Long(0);
			for(int index=0; index<list1.size(); index++) {
				if(list1.get(index) == Long.MAX_VALUE || list2.get(index) == Long.MAX_VALUE) {
					return null;
				}
				difference += Math.abs(list1.get(index) - list2.get(index));
			}
			return difference;
		} else {
			return null;
		}
	}
	
	// random ASCII character between [65-90]
	private void createTest(List<Long> listTest) {
		for(int index=0; index<maxTestVectorSize; index++) {
			listTest.add(new Long(random.nextInt(26)+65));
		}
	}
	
	// ROT13 cipher
	private void createAnswer(List<Long> listAnswer) {
		for(int index=0; index<maxTestVectorSize; index++) {
			long rot13 = listAnswer.get(index)+13;
			if(rot13>90) {
				rot13 = (rot13-90)+64;
			}
			listAnswer.set(index, rot13);
		}
	}
	
	// random array of data
	private void createTest1() {
		for(int index=0; index<maxTestVectorSize; index++) {
			listTest.add(new Long(random.nextInt(Integer.MAX_VALUE)));
		}
	}
	
	// sort array
	private void createAnswer1() {
		Collections.sort(listAnswer);
	}
}
