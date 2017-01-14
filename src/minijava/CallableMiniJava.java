package minijava;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class CallableMiniJava implements Callable<ArrayList<Long>>{
	private ClassLoader classLoader;
	private int ID;
	private ArrayList<Long> vector;
	
	public CallableMiniJava(ClassLoader classLoader, int ID, ArrayList<Long> vector) {
		this.classLoader = classLoader;
		this.ID = ID;
		this.vector = vector;
	}
	
	@Override
	public ArrayList<Long> call() throws Exception {
		ArrayList<Long> vectorReturn = null;
		Class<?> cls = Class.forName("package" + ID + ".GeneticProgram", false, classLoader);
		Method method = cls.getMethod("compute", ArrayList.class);
        Object object = method.invoke(null, vector);
        if(object instanceof ArrayList<Long>) {
        	vectorReturn = (ArrayList<Long>) object;
        } else {
        	throw new Exception("String not returned");
        }
		return vectorReturn;
	}

}
