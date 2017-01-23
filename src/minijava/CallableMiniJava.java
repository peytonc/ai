package minijava;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class CallableMiniJava implements Callable<CallableResult>{
	private ClassLoader classLoader;
	private int ID;
	private ArrayList<Long> vector;
	
	public CallableMiniJava(ClassLoader classLoader, int ID, ArrayList<Long> vector) {
		this.classLoader = classLoader;
		this.ID = ID;
		this.vector = new ArrayList<Long>(vector);
	}
	
	@Override
	public CallableResult call() throws Exception {
		CallableResult callableResult = new CallableResult();
		callableResult.ID = ID;
		Class<?> cls = Class.forName("package" + ID + ".GeneticProgram", false, classLoader);
		Method method = cls.getMethod("compute", ArrayList.class);
		long timeStart = System.nanoTime();
if(ID ==2) {
	System.out.println("IN1 " + ID + vector.toString());
}
        Object object = method.invoke(null, vector);
if(ID ==2) {
	System.out.println("IN2 " + ID + vector.toString());
}
        callableResult.milliseconds = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - timeStart);
        if(object instanceof ArrayList<?>) {
        	callableResult.vector = new ArrayList<Long>((ArrayList<Long>) object);
if(ID ==2) {
	System.out.println("IN3 " + ID + vector.toString());
	System.out.println("OUT3 " + ID + callableResult.vector.toString());
}
        } else {
        	throw new Exception("Method.invoke not instanceof");
        }
if(ID ==2) {
	System.out.println("IN4 " + ID + vector.toString());
	System.out.println("OUT4 " + ID + callableResult.vector.toString());
}
		return callableResult;
	}

}
