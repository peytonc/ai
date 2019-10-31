package species0.id0;

import java.lang.Exception;
import java.util.ArrayList;
import minijava.Util;

public class GeneticProgram {
	public static void compute(ArrayList<Long> values00) {
		int size = values00.size();
		ArrayList<Long> values01 = new ArrayList<Long>(values00);
		ArrayList<Long> values02 = new ArrayList<Long>(values00);
		ArrayList<Long> values03 = new ArrayList<Long>(values00);
		ArrayList<Long> values04 = new ArrayList<Long>(values00);
		ArrayList<Long> values05 = new ArrayList<Long>(values00);
		ArrayList<Long> values06 = new ArrayList<Long>(values00);
		ArrayList<Long> values07 = new ArrayList<Long>(values00);
		ArrayList<Long> values08 = new ArrayList<Long>(values00);
		ArrayList<Long> values09 = new ArrayList<Long>(values00);
		Long value00 = new Long(0);
		Long value01 = new Long(0);
		Long value02 = new Long(0);
		Long value03 = new Long(0);
		Long value04 = new Long(0);
		Long value05 = new Long(0);
		Long value06 = new Long(0);
		Long value07 = new Long(0);
		Long value08 = new Long(0);
		Long value09 = new Long(0);
		ArrayList<Boolean> conditions00 = new ArrayList<Boolean>(size);
		ArrayList<Boolean> conditions01 = new ArrayList<Boolean>(size);
		ArrayList<Boolean> conditions02 = new ArrayList<Boolean>(size);
		ArrayList<Boolean> conditions03 = new ArrayList<Boolean>(size);
		ArrayList<Boolean> conditions04 = new ArrayList<Boolean>(size);
		ArrayList<Boolean> conditions05 = new ArrayList<Boolean>(size);
		ArrayList<Boolean> conditions06 = new ArrayList<Boolean>(size);
		ArrayList<Boolean> conditions07 = new ArrayList<Boolean>(size);
		ArrayList<Boolean> conditions08 = new ArrayList<Boolean>(size);
		ArrayList<Boolean> conditions09 = new ArrayList<Boolean>(size);
		Boolean condition00 = new Boolean(false);
		Boolean condition01 = new Boolean(false);
		Boolean condition02 = new Boolean(false);
		Boolean condition03 = new Boolean(false);
		Boolean condition04 = new Boolean(false);
		Boolean condition05 = new Boolean(false);
		Boolean condition06 = new Boolean(false);
		Boolean condition07 = new Boolean(false);
		Boolean condition08 = new Boolean(false);
		Boolean condition09 = new Boolean(false);
		try {
			value01 = new Long(2);
			value02 = new Long(2);
			value03 = new Long(19);
			value04 = new Long(23);
			value00 = new Long(values00.get(new Long(0).intValue()%size));
			while(!Thread.currentThread().isInterrupted()&&((value00%2)==0)) {
				value00 = new Long((value00/2));
				values00.set(new Long(0).intValue()%size, new Long(value00));
			}
			value08 = new Long(Util.f(0, value00));
			value09 = new Long(1);
			while(!Thread.currentThread().isInterrupted()&&(value09==1)) {
				value01 = new Long(((((value01*value01)%value00)+value03)%value00));
				value02 = new Long(((((value02*value02)%value00)+value04)%value00));
				value05 = new Long(((((value02*value02)%value00)+value04)%value00));
				value06 = new Long((value01-value05));
				value07 = new Long(Util.f(1, value06));
				value09 = new Long(Util.f(0, value00, value07));
				if((value09==1)) {
					value05 = new Long((value08*value08));
					value06 = new Long((value05%value00));
					value07 = new Long(Util.f(0, value06));
					if(((value07*value07)==value06)) {
						value05 = new Long((value08+value07));
						value09 = new Long(Util.f(0, value00, value05));
					}
					value08 = new Long((value08+1));
				}
			}
			values00.set(new Long(0).intValue()%size, new Long(value09));
		} catch (Exception e) {
			values00.clear();
		}
	}
}