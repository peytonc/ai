/* DELETE THIS HEADER */
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
		Long value00 = Long.valueOf(0);
		Long value01 = Long.valueOf(0);
		Long value02 = Long.valueOf(0);
		Long value03 = Long.valueOf(0);
		Long value04 = Long.valueOf(0);
		Long value05 = Long.valueOf(0);
		Long value06 = Long.valueOf(0);
		Long value07 = Long.valueOf(0);
		Long value08 = Long.valueOf(0);
		Long value09 = Long.valueOf(0);
		Boolean condition00 = Boolean.valueOf(false);
		Boolean condition01 = Boolean.valueOf(false);
		Boolean condition02 = Boolean.valueOf(false);
		Boolean condition03 = Boolean.valueOf(false);
		Boolean condition04 = Boolean.valueOf(false);
		Boolean condition05 = Boolean.valueOf(false);
		Boolean condition06 = Boolean.valueOf(false);
		Boolean condition07 = Boolean.valueOf(false);
		Boolean condition08 = Boolean.valueOf(false);
		Boolean condition09 = Boolean.valueOf(false);
		try {
			value09 = Long.valueOf(1);
			value00 = Long.valueOf(values00.get(Long.valueOf(0).intValue() % size));
			value01 = Long.valueOf(2);
			while (!Thread.currentThread().isInterrupted() && (((value00 % value01) == 0) && (value01 <= value00))) {
				value00 = Long.valueOf((value00 / value01));
				if((value00 == 1)) {
					value09 = Long.valueOf(value01);
				}
			}
			value01 = Long.valueOf(1);
			value08 = Long.valueOf(Util.f(0, value00));
			while (!Thread.currentThread().isInterrupted() && (value09 == 1)) {
				value01 = Long.valueOf((value01 + 2));
				while (!Thread.currentThread().isInterrupted() && (((value00 % value01) == 0) && (value01 <= value00))) {
					value00 = Long.valueOf((value00 / value01));
					value08 = Long.valueOf(Util.f(0, value00));
					if((value00 == 1)) {
						value09 = Long.valueOf(value01);
					}
				}
				if((value09 == 1)) {
					value05 = Long.valueOf((value08 * value08));
					condition06 = Boolean.valueOf(false);
					value05 = Long.valueOf((value08 * value08));
					value06 = Long.valueOf((value05 % value00));
					value07 = Long.valueOf(Util.f(0, value06));
					if(((value07 * value07) == value06)) {
						value05 = Long.valueOf((value08 + value07));
						value09 = Long.valueOf(Util.f(0, value00, value05));
						if((value09 != 1)) {
							value00 = Long.valueOf((value00 / value09));
							condition00 = Boolean.valueOf((Util.f(2, value00) == 1));
							condition09 = Boolean.valueOf((Util.f(2, value09) == 1));
							if((condition00 && condition09)) {
								if((value09 < value00)) {
									value09 = Long.valueOf(value00);
								}
							} else {
								if(condition00) {
									if((value09 < value00)) {
										value09 = Long.valueOf(value00);
									}
								}
							}
						}
					}
					if (((value09 == 1) && (1000 < value01))) {
						if ((Util.f(2, value00) == 1)) {
							value09 = Long.valueOf(value00);
						} else {
							value09 = Long.valueOf(Util.f(0, value00));
						}
					}
					value08 = Long.valueOf((value08 + 19));
				}
			}
			values00.set(Long.valueOf(0).intValue() % size, Long.valueOf(value09));
		} catch (Exception e) {
			values00.clear();
		}
	}
}
