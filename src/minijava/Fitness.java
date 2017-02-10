package minijava;

public class Fitness implements Comparable<Fitness> {
	public double fit;
	public long difference;
	public long speed;
	public int size;
	
	public Fitness() {
		fit = Double.MAX_VALUE;
		difference = Long.MAX_VALUE;
		speed = Integer.MAX_VALUE;
		size = Integer.MAX_VALUE;
	}
	
	public String toString() {
		return "Fitness{fit=" + fit + ",difference=" + difference + ",size=" + size + ",speed=" + speed + "}";
	}
	
	@Override
	public int compareTo(Fitness fitness) {
		int compare = 0;
		if(size <= Main.sizeBeforeRestrict ) {
			compare = Double.compare(fit, fitness.fit);
		} else {
			compare = Double.compare(fit+size/Main.sizeBeforeRestrict, fitness.fit+fitness.size/Main.sizeBeforeRestrict);
		}
		if(compare == 0) {
			compare = Long.compare(difference, fitness.difference);
			if(compare == 0) {
				compare = Integer.compare(size, fitness.size);
				if(compare == 0) {
					compare = Long.compare(speed, fitness.speed);
				}
			}
		}
		return compare;
	}
}