package minijava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Test {
	public final List<Long> listTest;
	public final List<Long> listAnswer;
	
	private static final Random RANDOM = new Random(GEP.RANDOM_SEED);
	private static final int MAX_TEST_VECTOR_SIZE = 1;
	
	private static boolean firstCall = true;
	private static final int MAX_COMPOSITE = 10000000;
    private static int[] smoothness = new int[MAX_COMPOSITE];
    public static void sieveOfEratosthenes() {
        Arrays.fill(smoothness,1);        // assume largest prime factor is 1
        for(int i=2; i<smoothness.length; i++) {
        	if(smoothness[i] == 1)	{	// update sieve with each prime number, and skip composite numbers
	            for(int j=1; i*j<smoothness.length; j++) {
	            	smoothness[i*j] = i;
	            }
        	}
        }
    }
	
	public Test() {
		synchronized(this) {
			if(firstCall) {
				firstCall = false;
				sieveOfEratosthenes();
			}
		}
		List<Long> listTest = new ArrayList<Long>(MAX_TEST_VECTOR_SIZE);
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
	
	// random composite numbers
	private void createTest(List<Long> listTest) {
		for(int index=0; index<MAX_TEST_VECTOR_SIZE; index++) {
			int i = RANDOM.nextInt(MAX_COMPOSITE-6) + 4;
			for(int j=i; i<smoothness.length; j++) {
				if(smoothness[j] != j)	{
					break;	// found a composite number
				}
				i++;
			}
			listTest.add(new Long(i));
		}
	}
	
	// smoothness
	private void createAnswer(List<Long> listAnswer) {
		for(int index=0; index<MAX_TEST_VECTOR_SIZE; index++) {
			int result = listAnswer.get(index).intValue();
			long largestFactor = smoothness[result];
			listAnswer.set(index, largestFactor);
		}
	}
}