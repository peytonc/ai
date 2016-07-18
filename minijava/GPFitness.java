package minijava;

import java.util.ArrayList;

public class GPFitness implements Comparable<GPFitness> {
	public int ID;
	public int fitness;
	public int speed;
	public int size;
	ArrayList<Long> vectorActual;
	
	public GPFitness(int ID, int fitness, int speed, int size) {
		this.ID = ID;
		this.fitness = fitness;
		this.speed = speed;
		this.size = size;
	}
	
	@Override
	public int compareTo(GPFitness gPFitness) {
		int compare = Integer.compare(fitness, gPFitness.fitness);
		if(compare == 0) {
			compare = Integer.compare(speed, gPFitness.speed);
			if(compare == 0) {
				compare = Integer.compare(size, gPFitness.size);
			}
		}
		return compare;
	}
	
	public String toString() {
		return "GPFitness{ID=" + ID + ",fitness=" + fitness + ",speed=" + speed + ",size=" + size + "}";
	}
}
