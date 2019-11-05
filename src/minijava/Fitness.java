package minijava;

public class Fitness implements Comparable<Fitness> {
	public double fit;
	public long difference;
	public long speed;
	public int size;
	public int sizeBeforeRestrict;
	public boolean isComplete;
	
	public Fitness() {
		fit = Double.MAX_VALUE;
		difference = Long.MAX_VALUE;
		speed = Integer.MAX_VALUE;
		size = Integer.MAX_VALUE;
		sizeBeforeRestrict = 0;
		isComplete = false;
	}
	
	public String toString() {
		return "Fitness{" + "difference=" + difference + ",fit=" + fit + ",size=" + size + ",speed=" + speed + "}";
	}
	
	@Override
	public int compareTo(Fitness fitness) {
		int compare = 0;
		if(speed==Integer.MAX_VALUE || fitness.speed==Integer.MAX_VALUE) {
			compare = Long.compare(speed, fitness.speed);
		} else if(size > sizeBeforeRestrict || fitness.size > sizeBeforeRestrict) {
			compare = Double.compare(difference * (size/sizeBeforeRestrict), fitness.difference * (fitness.size/sizeBeforeRestrict));
		}
		if(compare == 0) {
			compare = Long.compare(difference, fitness.difference);
			if(compare == 0) {
				compare = Double.compare(fit, fitness.fit);
				if(compare == 0) {
					compare = Integer.compare(size, fitness.size);
					if(compare == 0) {
						compare = Long.compare(speed, fitness.speed);
					}
				}
			}
		}
		return compare;
	}
}