package delete;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class GPDriver {

	public final int maxPackage = 2; 
	
	public GPDriver() {  
	}
	
	public void initalize() {
		File fileGeneticProgram = new File("C:\\Users\\peyton\\workspace\\delete\\src\\GeneticProgram.java");
		File file;
		try {
			FileReader fileReader = new FileReader(fileGeneticProgram);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			bufferedReader.mark(5000);
			FileWriter fileWriter;
			BufferedWriter bufferedWriter;
			String line;
			for(int index=0; index<maxPackage; index++) {
				bufferedReader.reset();
				file = new File("C:\\Users\\peyton\\workspace\\delete\\src\\package" + index);
				file.mkdir();
				fileWriter = new FileWriter(file.toPath().resolve(fileGeneticProgram.toPath().getFileName()).toFile());
				bufferedWriter = new BufferedWriter(fileWriter);
				bufferedReader.readLine();
				bufferedWriter.write("package package" + index + ";");
				bufferedWriter.newLine();
				while((line=bufferedReader.readLine()) != null) {
					bufferedWriter.write(line);
					bufferedWriter.newLine();
				}
				bufferedWriter.close();
				fileWriter.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void evolution() {
		final int milliseconds = 3000;
		ArrayList<Long> arrayListTest = getTestVector();
		ArrayList<Long> arrayListExpected = new ArrayList<Long>(arrayListTest);
		Collections.sort(arrayListExpected);
		List<File> listFile = new ArrayList<File>(maxPackage);
		List<GPFitness> listGPFitness = new ArrayList<GPFitness>(maxPackage);
		for(int index=0; index<maxPackage; index++) {
			listFile.add(new File("C:\\Users\\peyton\\workspace\\delete\\src\\package" + index + "\\GeneticProgram.java"));
			listGPFitness.add(new GPFitness(index, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE));
		}
		// This will require JAVA_HOME be set to JDK. JRE home will cause a NullPointerException
		System.setProperty("java.home", "C:\\Program Files\\Java\\jdk1.7.0_25");
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();   
	    StandardJavaFileManager standardJavaFileManager = compiler.getStandardFileManager(null, Locale.getDefault(), null);  
	    Iterable<? extends JavaFileObject> iterableJavaFileObject = standardJavaFileManager.getJavaFileObjectsFromFiles(listFile);
	    String[] compileOptions = new String[]{"-d", "bin", "-classpath", System.getProperty("java.class.path")};   
	    Iterable<String> compilationOptions = Arrays.asList(compileOptions);   
	    DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
	    CompilationTask compilerTask = compiler.getTask(null, standardJavaFileManager, diagnostics, compilationOptions, null, iterableJavaFileObject);
	    boolean status = compilerTask.call();	// Performs this compilation task
	
	    if (!status) {	//compilation error
	        for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()){   
	            System.out.format("Error at %d:%d in %s", diagnostic.getLineNumber(), diagnostic.getStartPosition(), diagnostic);   
	        }
	    }
	    try {
	    	standardJavaFileManager.close() ;//Close the file manager   
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	
	    // Now load the class and use it
		try {
			URL[] url = new URL[] { new URL("file:///") };
			ClassLoader classLoader = URLClassLoader.newInstance(url, getClass().getClassLoader());
			
			List<Callable<ArrayList<Long>>> listCallable = new ArrayList<Callable<ArrayList<Long>>>(maxPackage);
			for(int i=0; i<maxPackage; i++) {
				Callable<ArrayList<Long>> gPCallable = new GPCallable(classLoader, i, arrayListTest);
				listCallable.add(gPCallable);
			}
			ExecutorService executorService = Executors.newFixedThreadPool(maxPackage);
			List<Future<ArrayList<Long>>> listFuture = executorService.invokeAll(listCallable, milliseconds, TimeUnit.MILLISECONDS);
			int index=0;
			for(Future<ArrayList<Long>> future : listFuture) {
				if(!future.isCancelled()) {
					ArrayList<Long> vectorActual = future.get();
					listGPFitness.get(index).vectorActual = vectorActual;
					listGPFitness.get(index).fitness = getFitness(arrayListExpected, vectorActual);
					listGPFitness.get(index).speed = milliseconds;
					listGPFitness.get(index).size = (int)listFile.get(index).length();
				}
				else {
				}
				index++;
			}
			Collections.sort(listGPFitness);
			for(GPFitness gPFitness : listGPFitness) {
				System.out.println(gPFitness.toString());
				System.out.println(gPFitness.vectorActual.toString());
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SecurityException | IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
	
	int getFitness(ArrayList<Long> vectorExpected, ArrayList<Long> vectorActual) {
		int fitness = 0;
		for(int index=0; index<vectorExpected.size(); index++) {
			fitness += Math.abs(vectorExpected.get(index) - vectorActual.get(index));
		}
		return fitness;
	}
	
	ArrayList<Long> getTestVector() {
		ArrayList<Long> arrayList = new ArrayList<Long>();
        arrayList.add(new Long(9));
        arrayList.add(new Long(8));
        arrayList.add(new Long(7));
        arrayList.add(new Long(6));
        arrayList.add(new Long(5));
        arrayList.add(new Long(4));
        arrayList.add(new Long(3));
        arrayList.add(new Long(2));
        arrayList.add(new Long(1));
        arrayList.add(new Long(0));
        return arrayList;
	}
	
	public static void main(String[] args) {
		GPDriver gPDriver = new GPDriver();
		gPDriver.initalize();
		gPDriver.evolution();
	}
}
