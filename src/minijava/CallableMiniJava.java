package minijava;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class CallableMiniJava implements Callable<CallableResult>{
	private CallableResult callableResult  = new CallableResult();
	
	public CallableMiniJava(int ID, ArrayList<Long> vector) {
		callableResult.ID = ID;
		callableResult.vector = new ArrayList<Long>(vector);
		callableResult.milliseconds = Integer.MAX_VALUE;
	}
	
	@Override
	public CallableResult call() throws Exception {
		Class<?> cls = Class.forName("package" + callableResult.ID + ".GeneticProgram");
		Method method = cls.getMethod("compute", ArrayList.class);
		long timeStart = System.nanoTime();
if(callableResult.ID ==2) {
	System.out.println("IN " + callableResult.ID + callableResult.vector.toString());
}
		try {
			method.invoke(null, callableResult.vector);
		} catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			callableResult.vector = null;
			return callableResult;
		}
if(callableResult.ID ==2) {
	System.out.println("OUT " + callableResult.ID + callableResult.vector.toString());
}
        callableResult.milliseconds = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - timeStart);
		return callableResult;
	}

}
