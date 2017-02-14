package minijava;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Test {
	private final static Random random = new Random(0);
	private final static int maxTestVectorSize = 10;
	
	public static void createTests(ArrayList<ArrayList<Long>> arrayListTests, ArrayList<ArrayList<Long>> arrayListAnswers) {
		for(int index=0; index<Main.maxTestVectors; index++) {
			ArrayList<Long> arrayListTest = new ArrayList<Long>(maxTestVectorSize);
			createTest(arrayListTest);
			arrayListTests.add(arrayListTest);
			ArrayList<Long> arrayListAnswer = new ArrayList<Long>(arrayListTest);
			createAnswer(arrayListAnswer);
			arrayListAnswers.add(arrayListAnswer);
		}
	}
	
	// random ASCII character between [65-90]
	public static void createTest(ArrayList<Long> arrayListTest) {
		for(int index=0; index<maxTestVectorSize; index++) {
			arrayListTest.add(new Long(random.nextInt(26)+65));
		}
	}
	
	// ROT13 cipher
	public static void createAnswer(ArrayList<Long> arrayListAnswer) {
		for(int index=0; index<maxTestVectorSize; index++) {
			long rot13 = arrayListAnswer.get(index)+13;
			if(rot13>90) {
				rot13 = (rot13-90)+64;
			}
			arrayListAnswer.set(index, rot13);
		}
	}
	
	// random array of data
	public static void createTest1(ArrayList<Long> arrayListTest) {
		for(int index=0; index<maxTestVectorSize; index++) {
			arrayListTest.add(new Long(random.nextInt(Integer.MAX_VALUE)));
		}
	}
	
	// sort array
	public static void createAnswer1(ArrayList<Long> arrayListAnswer) {
		Collections.sort(arrayListAnswer);
	}
}
