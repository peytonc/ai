package minijava;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class CallableMiniJava implements Runnable {
	private Program program = null;
	private Class<?> cls = null;
	private Method method = null;
	
	private static final Logger LOGGER = Logger.getLogger(CallableMiniJava.class.getName());
	
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
			Thread thread = Thread.currentThread();
			thread.setPriority(Thread.MIN_PRIORITY);	// CallableMiniJava gets the lowest priority and is often computing results (minimum priority to avoid GP and Species starvation)
			long timeStart = GP.threadMXBean.getThreadCpuTime(thread.getId());
			try {
				int index = 0;
				boolean isInterrupted = false;
				for(; index<program.vectors.size(); index++) {
					if(thread.isInterrupted()) {
						isInterrupted = true;
						break;
					} else {
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
			long timeStop = GP.threadMXBean.getThreadCpuTime(thread.getId());
			if(timeStart == -1 || timeStop == -1) {
				program.fitness.sumSpeed = Long.MAX_VALUE;
				program.fitness.meanSpeed = Long.MAX_VALUE;
				LOGGER.severe("OS and JVM must support CPU time as defined by ThreadMXBean");
			} else {
				long speed = TimeUnit.NANOSECONDS.toMillis(timeStop - timeStart);
				program.fitness.addSampleSpeed(speed);
			}
			
		}
	}
}
