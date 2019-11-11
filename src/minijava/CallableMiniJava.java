package minijava;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class CallableMiniJava implements Runnable {
	private Program program = null;
	private Class<?> cls = null;
	Method method = null;
	
	public CallableMiniJava(Program program) {
		if(program.vectors != null) {
			this.program = program;
			try {
				cls = program.programClassLoader.loadClass(Program.PACKAGE_SPECIES + program.species + "." + Program.PACKAGE_ID + program.ID + "." + Program.PROGRAM_CLASS);
			} catch (ClassNotFoundException e1) {
				program.vectors = null;
				e1.printStackTrace();
			}
			try {
				method = cls.getMethod("compute", ArrayList.class);
			} catch (NoSuchMethodException e1) {
				program.vectors = null;
				e1.printStackTrace();
			} catch (SecurityException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		if(method != null) {
			long timeStart = 0;
			try {
				boolean isInterrupted = false;
				int index = 0;
				for(; index<program.vectors.size(); index++) {
					if(Thread.currentThread().isInterrupted()) {
						isInterrupted = true;
						break;
					} else {
						if(timeStart == 0) {
							timeStart = System.nanoTime();
						}
						method.invoke(null, program.vectors.get(index));
						if(program.vectors.get(index)==null || program.vectors.get(index).isEmpty()) {
							program.vectors = null;
							break;
						}
					}
				}
				if(!isInterrupted && program.vectors != null && index==program.vectors.size()) {
					program.fitness.isComplete = true;
				}
			} catch(Exception e) {
				System.out.println("CallableMiniJava");
				program.vectors = null;
				e.printStackTrace();
			}
			if(timeStart == 0) {
				program.fitness.speed = 0;
			} else {
				program.fitness.speed = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - timeStart) + 1;	// add +1 to show process ran
			}
		}
	}
}
