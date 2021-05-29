package gp.test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public abstract class Test {
	public List<Long> listTest;
	public ArrayList<ArrayList<Long>> listAnswer;

	public abstract BigInteger getDifference(List<Long> listActuals);
	
}
