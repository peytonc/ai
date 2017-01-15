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
		this.vector = vector;
	}
	
	@Override
	public CallableResult call() throws Exception {
		CallableResult callableResult = new CallableResult();
		Class<?> cls = Class.forName("package" + ID + ".GeneticProgram", false, classLoader);
		Method method = cls.getMethod("compute", ArrayList.class);
		long timeStart = System.nanoTime();
        Object object = method.invoke(null, vector);
        callableResult.milliseconds = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - timeStart);
        if(object instanceof ArrayList<?>) {
        	callableResult.vector = (ArrayList<Long>) object;
        } else {
        	throw new Exception("Method.invoke not instanceof");
        }
		return callableResult;
	}

}
