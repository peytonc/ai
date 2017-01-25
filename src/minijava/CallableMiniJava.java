package minijava;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

public class CallableMiniJava implements Callable<Void> {
	private static final String[] compileOptions = new String[]{"-d", "bin", "-classpath", System.getProperty("java.class.path")};
	private static final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler(); 
	private static final Iterable<String> compilationOptions = Arrays.asList(compileOptions);
	private Program program = null;
	
	public CallableMiniJava(Program program) {
		this.program = program;
	}
	
	@Override
	public Void call() throws Exception {
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		try (StandardJavaFileManager standardJavaFileManager = compiler.getStandardFileManager(diagnostics, Locale.ENGLISH, null)) {
			Iterable<? extends JavaFileObject> javaFileObject = Arrays.asList(program);
			CompilationTask compilerTask = compiler.getTask(null, standardJavaFileManager, diagnostics, compilationOptions, null, javaFileObject);
			if (!compilerTask.call()) {	//Compile and check for program errors, random code may have compile errors
				if(diagnostics.getDiagnostics().size() != 0) {
					program.vectorActual = null;
				}
			    for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
			    	System.out.println(diagnostic.getMessage(null));
			    }
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if(program.vectorActual != null) {
			Class<?> cls = Class.forName("package" + program.ID + ".GeneticProgram");
			Method method = cls.getMethod("compute", ArrayList.class);
			long timeStart = System.nanoTime();
if(program.ID ==2) {
	System.out.println("IN " + program.ID + program.vectorActual.toString());
}
			try {
				method.invoke(null, program.vectorActual);
			} catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				program.vectorActual = null;
			}
if(program.ID ==2) {
	System.out.println("OUT " + program.ID + program.vectorActual.toString());
}
			program.fitness.speed = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - timeStart);
		}
		return null;
	}

}
