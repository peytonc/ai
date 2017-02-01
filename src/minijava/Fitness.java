package minijava;

public class Fitness implements Comparable<Fitness> {
	public int difference;
	public long speed;
	public int size;
	
	public Fitness() {
		difference = Integer.MAX_VALUE;
		speed = Integer.MAX_VALUE;
		size = Integer.MAX_VALUE;
	}
	public String toString() {
		return "Fitness{difference=" + difference + ",size=" + size + ",speed=" + speed + "}";
	}
	
	@Override
	public int compareTo(Fitness fitness) {
		int compare = Integer.compare(difference, fitness.difference);
		if(compare == 0) {
			compare = Integer.compare(size, fitness.size);
			if(compare == 0) {
				compare = Long.compare(speed, fitness.speed);
			}
		}
		return compare;
	}
}
