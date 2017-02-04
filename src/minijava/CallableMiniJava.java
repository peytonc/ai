package minijava;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class CallableMiniJava implements Runnable {
	private Program program = null;
	
	public CallableMiniJava(Program program) {
		this.program = program;
	}
	
	@Override
	public void run() {
		if(program.vectors != null) {
			Class<?> cls = null;
			try {
				cls = program.programClassLoader.loadClass(Program.PACKAGE_NAME + program.ID + "." + Program.PROGRAM_CLASS_NAME);
			} catch (ClassNotFoundException e1) {
				program.vectors = null;
				e1.printStackTrace();
			}
			Method method = null;
			try {
				method = cls.getMethod("compute", ArrayList.class);
			} catch (NoSuchMethodException e1) {
				program.vectors = null;
				e1.printStackTrace();
			} catch (SecurityException e1) {
				e1.printStackTrace();
			}
			long timeStart = System.nanoTime();
			try {
				for(int index=0; index<program.vectors.size(); index++) {
					method.invoke(null, program.vectors.get(index));
					if(program.vectors.get(index)==null || program.vectors.get(index).isEmpty()) {
						break;
					}
				}
			} catch(Exception e) {
				program.vectors = null;
				e.printStackTrace();
			}
			program.fitness.speed = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - timeStart);
		}
/*if(program.vector != null) {
System.out.println("ID" + program.ID + program.fitness.toString() + program.vector.toString() + program.source);
} else {
System.out.println("ID" + program.ID + program.fitness.toString() + "NULL                    " + program.source);
}*/
	}

}
