package minijava;

import java.util.ArrayList;

public class Util {
	
	public static void f(long functionIndex, ArrayList<Long> arrayList1, ArrayList<Long> arrayList2, long long1, long long2) {
		final int maxTypes = 1;
		int type = (int)functionIndex%maxTypes;
		int index1;
		int index2;
		switch(type) {
			case 0:
				index1 = (int)long1%arrayList1.size();
				index2 = (int)long2%arrayList2.size();
				if(arrayList1.get(index1) < arrayList2.get(index2)) {
					Long temp = new Long(arrayList1.get(index1));
					arrayList1.set(index1, arrayList2.get(index2));
					arrayList2.set(index2, temp);
				}
		}
	}
}
