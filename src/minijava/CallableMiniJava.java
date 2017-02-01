package minijava;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

public class CallableMiniJava implements Runnable {
	private static final JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler(); 
	private Program program = null;
	
	public CallableMiniJava(Program program) {
		this.program = program;
	}
	
	@Override
	public void run() {
		ProgramClassLoader dynamicClassLoader = null;
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		try (StandardJavaFileManager standardJavaFileManager = javaCompiler.getStandardFileManager(diagnostics, Locale.ENGLISH, null)) {
			Iterable<? extends JavaFileObject> javaFileObject = Arrays.asList(program);
			dynamicClassLoader = new ProgramClassLoader(ClassLoader.getSystemClassLoader());
			ProgramClassSimpleJavaFileObject programClassSimpleJavaFileObject = null;
			try {
				programClassSimpleJavaFileObject = new ProgramClassSimpleJavaFileObject("package" + program.ID + ".GeneticProgram");
			} catch (Exception e) {
				program.vector = null;
				e.printStackTrace();
			}
			ProgramForwardingJavaFileManager programForwardingJavaFileManager = new ProgramForwardingJavaFileManager(standardJavaFileManager, programClassSimpleJavaFileObject, dynamicClassLoader);
			CompilationTask compilerTask = javaCompiler.getTask(null, programForwardingJavaFileManager, diagnostics, null, null, javaFileObject);
			Boolean success = compilerTask.call();
			for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
				program.vector = null;
		    	System.out.println(diagnostic.getMessage(null));
		    }
			if (!success) {	//Compile and check for program errors, random code may have compile errors
				if(diagnostics.getDiagnostics().size() != 0) {
					program.vector = null;
				}
			    
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(program.vector != null) {
			Class<?> cls = null;
			try {
				cls = dynamicClassLoader.loadClass(Program.PACKAGE_NAME + program.ID + "." + Program.PROGRAM_CLASS_NAME);
			} catch (ClassNotFoundException e1) {
				program.vector = null;
				e1.printStackTrace();
			}
			Method method = null;
			try {
				method = cls.getMethod("compute", ArrayList.class);
			} catch (NoSuchMethodException e1) {
				program.vector = null;
				e1.printStackTrace();
			} catch (SecurityException e1) {
				e1.printStackTrace();
			}
			long timeStart = System.nanoTime();
			try {
				method.invoke(null, program.vector);
			} catch(Exception e) {
				program.vector = null;
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
