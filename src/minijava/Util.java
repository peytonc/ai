package minijava;

import java.util.ArrayList;

public class Util {
	
	public static void f(long functionIndex, ArrayList<Long> arrayList1, ArrayList<Long> arrayList2, long indexArray1, long indexArray2) {
		final int maxTypes = 1;
		int type = (int)functionIndex%maxTypes;
		int index1 = (int)indexArray1%arrayList1.size();
		int index2 = (int)indexArray1%arrayList2.size();
		switch(type) {
			case 0:
				if(arrayList1.get(index1) < arrayList2.get(index2)) {
					Long temp = new Long(arrayList1.get(index1));
					arrayList1.set(index1, arrayList2.get(index2));
					arrayList2.set(index2, temp);
				}
		}
	}
}
