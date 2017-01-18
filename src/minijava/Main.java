package minijava;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.tree.ParseTree;

import minijava.parser.MiniJavaLexer;
import minijava.parser.MiniJavaParser;
import minijava.parser.MiniJavaParser.*;

public class Main {
	public final static String JAVA_HOME = new String("/usr/local/jdk1.8.0_112/");	// This will require JAVA_HOME be set to JDK. JRE home will cause a NullPointerException
	public final static String PROGRAM_CLASS_NAME = new String("GeneticProgram");
	public final static String PROGRAM_FILENAME = new String("GeneticProgram.java");
	public final int maxParent = 2;	// Size of parent pool
	public final int maxChildren = 2;	// Number of children each parent produces
	public final int maxPopulation = maxParent*maxChildren + maxParent;	// Total population size
	public final int maxExecuteMilliseconds = 3000;
	
	private List<Program> listProgramParent = new ArrayList<Program>(maxParent);
	private List<Program> listProgramPopulation = new ArrayList<Program>(maxPopulation);
	private ArrayList<Long> arrayListTest;
	private ArrayList<Long> arrayListExpected;
	private Fitness fitnessBest;
	
	private List<ParseTree> listParseTree = new ArrayList<ParseTree>();
	private Vocabulary vocabulary;
	
	public Main() {
		System.setProperty("java.home", JAVA_HOME);
		try {
			String source = new String(Files.readAllBytes(Paths.get(PROGRAM_FILENAME)));
			for(int index=0; index<maxParent; index++) {
				listProgramParent.add(new Program(PROGRAM_CLASS_NAME, replacePackage(source, index)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		arrayListTest = getTestVector();
		arrayListExpected = new ArrayList<Long>(arrayListTest);
		Collections.sort(arrayListExpected);
	}
	
	public void createPopulation() {
		int indexPackage = 0;
		listProgramPopulation.clear();
		for(Program program : listProgramParent) {
			listProgramPopulation.add(new Program(PROGRAM_CLASS_NAME, replacePackage(program.source, indexPackage++)));	// add parent to population
			for(int indexChild=0; indexChild<maxChildren; indexChild++) {
				String source = mutate(program.source);
				listProgramPopulation.add(new Program(PROGRAM_CLASS_NAME, replacePackage(source, indexPackage++)));	// add child to population
			}
		}
	}
	
	public String mutate(String source) {
		MiniJavaLexer miniJavaLexer = new MiniJavaLexer(new ANTLRInputStream(source));
		MiniJavaParser miniJavaParser = new MiniJavaParser(new CommonTokenStream(miniJavaLexer));
		vocabulary = miniJavaParser.getVocabulary();
		BlockContext blockContext = miniJavaParser.program().block();
		listParseTree.clear();
		//miniJavaParser.getTokenStream().getText(parseTree.getSourceInterval()));
		getChildren(blockContext);
		for(ParseTree parseTree : listParseTree) {
			System.out.println("parseTree.getSourceInterval().toString()" + parseTree.getSourceInterval().toString());
			System.out.println("parseTree.getText()" + parseTree.getText());
		}
        System.out.println(blockContext.getText());
		return source;
	}
	
	private void getChildren(ParseTree parseTree) {
		if(parseTree==null || parseTree.getText()==null) {
			return;
		}
		String text = "'" + parseTree.getText() + "'";
		boolean isLiteral = false;
		for(int index=0; index<vocabulary.getMaxTokenType(); index++) {
			if(text.equals(vocabulary.getLiteralName(index))) {
				isLiteral = true;
				break;
			}
		}
		if(!isLiteral) {
			listParseTree.add(parseTree);
			for(int index=0; index<parseTree.getChildCount(); index++) {
				ParseTree parseTreeChild = parseTree.getChild(index);
				getChildren(parseTreeChild);
			}
		}
	}
	
	public void execute() {
		final String[] compileOptions = new String[]{"-d", "bin", "-classpath", System.getProperty("java.class.path")};
		final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler(); 
		final Iterable<String> compilationOptions = Arrays.asList(compileOptions); 
		final StandardJavaFileManager standardJavaFileManager = compiler.getStandardFileManager(null, null, null);
		final DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		final ExecutorService executorService = Executors.newFixedThreadPool(maxPopulation);
		final List<Callable<CallableResult>> listCallable = new ArrayList<Callable<CallableResult>>(maxPopulation);
		
		CompilationTask compilerTask = compiler.getTask(null, standardJavaFileManager, diagnostics, compilationOptions, null, listProgramPopulation);
		if (!compilerTask.call()) {	//Compile and check for program errors
		    for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()){   
		        System.out.format("Error at %d:%d in %s", diagnostic.getLineNumber(), diagnostic.getStartPosition(), diagnostic);   
		    }
		} else {	// Load the class and use it
			try {
				URL[] url = new URL[] { new URL("file:///") };
				ClassLoader classLoader = URLClassLoader.newInstance(url, getClass().getClassLoader());
				for(int i=0; i<maxPopulation; i++) {
					Callable<CallableResult> callable = new CallableMiniJava(classLoader, i, arrayListTest);
					listCallable.add(callable);
				}
				
				List<Future<CallableResult>> listFuture = executorService.invokeAll(listCallable, maxExecuteMilliseconds, TimeUnit.MILLISECONDS);
				int index=0;
				for(Future<CallableResult> future : listFuture) {
					if(!future.isCancelled()) {
						CallableResult callableResult = future.get();
						listProgramPopulation.get(index).vectorActual = callableResult.vector;
						listProgramPopulation.get(index).fitness.difference = getDifference(arrayListExpected, callableResult.vector);
						listProgramPopulation.get(index).fitness.speed = callableResult.milliseconds;
					}
					else {
						System.out.println("ERROR");
					}
					index++;
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (SecurityException | IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void selection() {
		Collections.sort(listProgramPopulation);
		if(fitnessBest == null) {
			fitnessBest = listProgramPopulation.get(0).fitness;
		} else if(fitnessBest.compareTo(listProgramPopulation.get(0).fitness) > 0) {
			fitnessBest = listProgramPopulation.get(0).fitness;
			String source = replacePackage(listProgramPopulation.get(0).source, 0);
			try {
				Files.write(Paths.get(PROGRAM_FILENAME),source.getBytes());
				System.out.println(fitnessBest.toString());
				System.out.println(source);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		listProgramParent = new ArrayList<Program>(listProgramPopulation.subList(0, maxParent));
		for(Program program : listProgramPopulation) {
			System.out.println(program.fitness.toString());
			if(program.vectorActual != null) {
				System.out.println(program.vectorActual.toString());
			} else {
				
			}
		}
	}
	
	int getDifference(ArrayList<Long> arrayListExpected, ArrayList<Long> arrayListActual) {
		int difference = 0;
		for(int index=0; index<arrayListExpected.size(); index++) {
			difference += Math.abs(arrayListExpected.get(index) - arrayListActual.get(index));
		}
		return difference;
	}
	
	String replacePackage(String source, int packageNumber) {
		return source.replaceFirst("package package[0-9]*;", "package package" + packageNumber + ";");
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
		Main main = new Main();
		for(int iteration=0; iteration<1; iteration++) {
			main.createPopulation();
			main.execute();
			main.selection();
		}
	}
}
