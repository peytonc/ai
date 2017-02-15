package minijava;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import minijava.parser.MiniJavaLexer;
import minijava.parser.MiniJavaParser;

public class Main {
	private static final String PROPERTIES_FILENAME = new String("config.properties");
	public final static String PROGRAM_FILENAME = new String("GeneticProgram.java");
	public final Path pathBase = Paths.get("");
	public final int maxParent = 5;	// Size of parent pool
	public final int maxChildren = 3;	// Number of children each parent produces
	public final int maxPopulation = maxParent*maxChildren + maxParent;	// Total population size
	public final int maxExecuteMilliseconds = 2000;
	public final int maxGenerationsReload = 1000;	// Force reload, because best fit program could be to random variable (distribution of means) 
	public int generation = 0;
	
	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
	private List<Program> listProgramParent = new ArrayList<Program>(maxParent);
	public List<Program> listProgramPopulation = new ArrayList<Program>(maxPopulation);
	private ArrayList<ArrayList<Long>> arrayListTests;
	private ArrayList<ArrayList<Long>> arrayListAnswers;
	private Fitness fitnessBest;
	
	private Vocabulary vocabulary;
	private Random random = new Random(0);
	public static int sizeBeforeRestrictMin = 0;
	public static int sizeBeforeRestrictMax = 0;
	public static int sizeBeforeRestrict = 0;
	private final static double sizeBeforeRestrictMinPercent = 0.95;
	private final static double sizeBeforeRestrictMaxPercent = 1.25;
	public final static int maxTestVectors = 3000;
	private final static int maxNewCodeSegmentSize = 80;
	private final static double worstFitAccepted = 2.0;
	
	public Main() {
		try(InputStream inputStream = new FileInputStream(PROPERTIES_FILENAME)) {
			LogManager.getLogManager().readConfiguration(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		fitnessBest = null;
		loadProgram();
	}

	public void loadProgram() {
		try {
			createTests();
			String source = new String(Files.readAllBytes(Paths.get(PROGRAM_FILENAME)));
			source = replacePackage(source, 0);
			sizeBeforeRestrictMin = (int)(sizeBeforeRestrictMinPercent * source.length());
			sizeBeforeRestrictMax = (int)(sizeBeforeRestrictMaxPercent * source.length());
			sizeBeforeRestrict = sizeBeforeRestrictMax;
			listProgramParent.add(new Program(source, 0, arrayListTests));
			//restart generate interval at beginning, so environment cycle doesn't make starved/winter population the best
			generation -= generation%maxGenerationsReload;
			createEnviroment();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createPopulation() {
		int indexPackage = 0;
		String source;
		listProgramPopulation.clear();
		//make ANTLR parsers for parents, used by crossover
		for(Program program : listProgramParent) {
			source = replacePackage(program.source, indexPackage);
			Program programParent = new Program(source, indexPackage, arrayListTests);
			MiniJavaLexer miniJavaLexer = new MiniJavaLexer(new ANTLRInputStream(programParent.source));
			programParent.miniJavaParser = new MiniJavaParser(new CommonTokenStream(miniJavaLexer));	// may contain incorrect ID
			programParent.blockContext = programParent.miniJavaParser.program().block();	// ANTLR 4.6 only allows one call to program() before EOF error
			program.miniJavaParser = programParent.miniJavaParser;
			program.blockContext = programParent.blockContext;
			listProgramPopulation.add(programParent);	// add parent to population
			indexPackage++;
		}
		for(Program program : listProgramParent) {
			for(int indexChild=0; indexChild<maxChildren; indexChild++) {
				Program program2 = null;	// program2 null means mutate
				if(random.nextBoolean()) {	// program2 !null means crossover
					program2 = listProgramParent.get(random.nextInt(listProgramParent.size()));
				}
				source = createProgram(program, program2, program.source);
				source = replacePackage(source, indexPackage);
				listProgramPopulation.add(new Program(source, indexPackage, arrayListTests));	// add child to population
				indexPackage++;
			}
		}
	}
	
	public String createProgram(Program program1, Program program2, String source) {
		List<ParseTree> listParseTree1 = new ArrayList<ParseTree>();
		vocabulary = program1.miniJavaParser.getVocabulary();
		getParseTreeNonLiteral(listParseTree1, program1.blockContext);
		ParseTree parseTree1 = listParseTree1.get(random.nextInt(listParseTree1.size()));
		int a = parseTree1.getSourceInterval().a;
		int b = parseTree1.getSourceInterval().b;
		int size = program1.miniJavaParser.getTokenStream().size()-1;	// remove last token inserted by ANTLR, <EOF>
		StringBuilder stringBuilder = new StringBuilder(source.length());
		for(int index=0; index<a; index++) {
			stringBuilder.append(program1.miniJavaParser.getTokenStream().get(index).getText());
			stringBuilder.append(" ");
		}
		//Oddity, but to calculate total size of prepend and append source code
		StringBuilder stringBuilderAppend = new StringBuilder(source.length());
		for(int index=b+1; index<size; index++) {
			stringBuilderAppend.append(program1.miniJavaParser.getTokenStream().get(index).getText());
			stringBuilderAppend.append(" ");
		}

		int length = stringBuilder.length() + stringBuilderAppend.length();
		if(program2 == null) {	// mutate source
			stringBuilder.append(generator(program1.miniJavaParser, parseTree1));	// replace interval [a,b] with random segment of code of same type
		} else {	// crossover program and program2
			List<ParseTree> listParseTree2 = new ArrayList<ParseTree>();
			getParseTreeNonLiteral(listParseTree2, program2.blockContext);
			List<ParseTree> listParseTreeCandidate = new ArrayList<ParseTree>();
			if(length < sizeBeforeRestrict) {
				for(ParseTree parseTree2 : listParseTree2) {	// add all equivalent class types
					if(length + parseTree2.getText().length() < sizeBeforeRestrict) {
						if(!parseTree2.getClass().getName().equals("minijava.parser.MiniJavaParser$BlockContext")) {	// don't add blocks, as it results in equivalent program
							if(parseTree2.getClass().getName().equals(parseTree1.getClass().getName())) {
								if (parseTree2.getClass().getName().equals("org.antlr.v4.runtime.tree.TerminalNodeImpl")) {	// TerminalNodeImpl has multiple sub-types
									TerminalNode terminalNode1 = (TerminalNode)parseTree1;
									TerminalNode terminalNode2 = (TerminalNode)parseTree2;
									if(terminalNode1.getSymbol().getType() == terminalNode2.getSymbol().getType()) {
										listParseTreeCandidate.add(parseTree2);
									}
								} else {
									listParseTreeCandidate.add(parseTree2);
								}
							}
						}
					}
				}
			}
			if(listParseTreeCandidate.size() > 0) {	// crossover if equivalent class type exists
				ParseTree parseTree2 = listParseTreeCandidate.get(random.nextInt(listParseTreeCandidate.size()));
				for(int index=parseTree2.getSourceInterval().a; index<=parseTree2.getSourceInterval().b; index++) {
					stringBuilder.append(program2.miniJavaParser.getTokenStream().get(index).getText());
					stringBuilder.append(" ");
				}
			} else {	// mutate source because no equivalent class types
				stringBuilder.append(generator(program1.miniJavaParser, parseTree1));	// replace interval [a,b] with random segment of code of same type
			}
		}
		stringBuilder.append(" ");
		stringBuilder.append(stringBuilderAppend);
		return stringBuilder.toString();
	}
	
	public String generator(MiniJavaParser miniJavaParser, ParseTree parseTree) {
		switch(parseTree.getClass().getName()) {
			case "minijava.parser.MiniJavaParser$BlockContext":
				return Generator.generateBlockContext(maxNewCodeSegmentSize);
			case "minijava.parser.MiniJavaParser$DeclarationContext":
				return Generator.generateDeclarationContext(maxNewCodeSegmentSize);
			case "minijava.parser.MiniJavaParser$LongArrayDeclarationContext":
				return Generator.generateLongArrayDeclarationContext(maxNewCodeSegmentSize);
			case "minijava.parser.MiniJavaParser$LongDeclarationContext":
				return Generator.generateLongDeclarationContext(maxNewCodeSegmentSize);
			case "minijava.parser.MiniJavaParser$BooleanArrayDeclarationContext":
				return Generator.generateBooleanArrayDeclarationContext(maxNewCodeSegmentSize);
			case "minijava.parser.MiniJavaParser$BooleanDeclarationContext":
				return Generator.generateBooleanDeclarationContext(maxNewCodeSegmentSize);
			case "minijava.parser.MiniJavaParser$StatementContext":
				return Generator.generateStatementContext(maxNewCodeSegmentSize, miniJavaParser, parseTree);
			case "minijava.parser.MiniJavaParser$ExpressionNumericContext":
				return Generator.generateExpressionNumericContext(maxNewCodeSegmentSize);
			case "minijava.parser.MiniJavaParser$ExpressionBooleanContext":
				return Generator.generateExpressionBooleanContext(maxNewCodeSegmentSize);
			case "minijava.parser.MiniJavaParser$LongArrayValueContext":
				return Generator.generateLongArrayValueContext(maxNewCodeSegmentSize);
			case "minijava.parser.MiniJavaParser$BooleanArrayValueContext":
				return Generator.generateBooleanArrayValueContext(maxNewCodeSegmentSize);
			case "org.antlr.v4.runtime.tree.TerminalNodeImpl":
				TerminalNode terminalNode = (TerminalNode)parseTree;
				return Generator.generateTerminalNode(maxNewCodeSegmentSize, vocabulary.getSymbolicName(terminalNode.getSymbol().getType()));
			default:
				return null;
		}
	}
	
	private void getParseTreeNonLiteral(List<ParseTree> listParseTree, ParseTree parseTree) {
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
				getParseTreeNonLiteral(listParseTree, parseTreeChild);
			}
		}
	}
	
	public void compilePopulation() {
		final JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler(); 
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		for(Program program : listProgramPopulation) {
			try (StandardJavaFileManager standardJavaFileManager = javaCompiler.getStandardFileManager(diagnostics, Locale.ENGLISH, null)) {
				Iterable<? extends JavaFileObject> javaFileObject = Arrays.asList(program);
				ProgramClassSimpleJavaFileObject programClassSimpleJavaFileObject = null;
				try {
					programClassSimpleJavaFileObject = new ProgramClassSimpleJavaFileObject("package" + program.ID + ".GeneticProgram");
				} catch (Exception e) {
					program.vectors = null;
					e.printStackTrace();
				}
				ProgramForwardingJavaFileManager programForwardingJavaFileManager = new ProgramForwardingJavaFileManager(standardJavaFileManager, programClassSimpleJavaFileObject, program.programClassLoader);
				CompilationTask compilerTask = javaCompiler.getTask(null, programForwardingJavaFileManager, diagnostics, null, null, javaFileObject);
				Boolean success = compilerTask.call();
				for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
					program.vectors = null;
					LOGGER.severe(diagnostic.getMessage(null));
			    }
				if (!success) {	//Compile and check for program errors, random code may have compile errors
					if(diagnostics.getDiagnostics().size() != 0) {
						program.vectors = null;
					}
				    
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void executePopulation() {
		final ExecutorService executorService = Executors.newFixedThreadPool(maxPopulation);
		try {	// Load the class and use it
			List<CallableMiniJava> listCallable = new ArrayList<CallableMiniJava>(maxPopulation);
			for(Program program : listProgramPopulation) {
				if(program.vectors != null) {
					listCallable.add(new CallableMiniJava(program));
				}
			}
			for(CallableMiniJava callableMiniJava : listCallable) {
				executorService.execute(callableMiniJava);
			}
			executorService.shutdown();
			if(!executorService.awaitTermination(maxExecuteMilliseconds, TimeUnit.MILLISECONDS)) {
				executorService.shutdownNow();
			}
		} catch (Exception e) {
			LOGGER.severe("Exception on terminate");
		}
	}
	
	public void evaluatePopulation() {
		ArrayList<Long> arrayListDifferencesBase = getDifferences(arrayListTests, arrayListAnswers);
		for(Program program : listProgramPopulation) {
			ArrayList<Long> arrayListDifferences = getDifferences(arrayListAnswers, program.vectors);
			if(arrayListDifferences == null || arrayListDifferences.size()!=maxTestVectors) {
				program.fitness.difference = Long.MAX_VALUE;
				program.fitness.fit = Double.MAX_VALUE;
			} else {
				long differenceSum = 0;
				double fitSum = 0;
				for(int index=0; index<maxTestVectors; index++) {
					long difference = arrayListDifferences.get(index);
					double fit = (double)difference / arrayListDifferencesBase.get(index);
					differenceSum += difference;
					fitSum += fit;
				}
				program.fitness.difference = differenceSum/maxTestVectors;
				program.fitness.fit = fitSum/maxTestVectors;
			}
		}
	}
	
	public void storeBestFit() {
		Collections.sort(listProgramPopulation);
		if(fitnessBest == null) {
			fitnessBest = listProgramPopulation.get(0).fitness;
			LOGGER.info("NEW" + generation + "ID" + listProgramPopulation.get(0).ID + fitnessBest.toString() + listProgramPopulation.get(0).source);
		} else if(fitnessBest.fit >= listProgramPopulation.get(0).fitness.fit) {	// first store in terms of best fit, next use compareTo
			if(fitnessBest.compareTo(listProgramPopulation.get(0).fitness) > 0) {
				fitnessBest = listProgramPopulation.get(0).fitness;
				try {
					LOGGER.info("BST" + generation + "ID" + listProgramPopulation.get(0).ID + fitnessBest.toString() + listProgramPopulation.get(0).source);
					String source = listProgramPopulation.get(0).source;
					source = replacePackage(source, 0);
					Files.write(Paths.get(PROGRAM_FILENAME),source.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void downselectPopulation() {
		int indexPackage = 0;
		listProgramParent.clear();
		for(Program programPopulation : listProgramPopulation) {
			if(indexPackage>=maxParent) {
				break;
			}
			if(programPopulation.fitness.fit>=worstFitAccepted || programPopulation.fitness.difference==Long.MAX_VALUE || programPopulation.fitness.speed>=Integer.MAX_VALUE || programPopulation.fitness.size==Integer.MAX_VALUE) {
				continue;
			}
			boolean exists = false;
			String stringSource = programPopulation.source.replaceAll("\\s+","");
			for(Program programParent : listProgramParent) {
				if(programParent.source.replaceAll("\\s+","").equalsIgnoreCase(stringSource)) {
					exists = true;
					break;
				}
			}
			if(!exists) {
				listProgramParent.add(new Program(replacePackage(programPopulation.source, indexPackage), indexPackage, arrayListTests));
				indexPackage++;
			}
		}
		if(listProgramParent.size() == 0) {
			LOGGER.warning("Restarting");
			fitnessBest = null;
			loadProgram();
		}
	}
	
	ArrayList<Long> getDifferences(ArrayList<ArrayList<Long>> arrayList1, ArrayList<ArrayList<Long>> arrayList2) {
		if(arrayList1 == null || arrayList2 == null) {
			return null;
		} else {
			ArrayList<Long> arrayListDifferences = new ArrayList<Long>(maxTestVectors);
			for(int index=0; index<maxTestVectors; index++) {
				Long difference = getDifference(arrayList1.get(index), arrayList2.get(index));
				if(difference == null) {
					return null;
				} else {
					arrayListDifferences.add(difference);
				}
			}
			return arrayListDifferences;
		}
	}
	
	Long getDifference(ArrayList<Long> arrayList1, ArrayList<Long> arrayList2) {
		if(arrayList1 == null || arrayList2 == null || arrayList1.size()!=arrayList2.size()) {
			return null;
		} else {
			Long difference = new Long(0);
			for(int index=0; index<arrayList1.size(); index++) {
				if(arrayList1.get(index) == Long.MAX_VALUE || arrayList2.get(index) == Long.MAX_VALUE) {
					return null;
				}
				difference += Math.abs(arrayList1.get(index) - arrayList2.get(index));
			}
			return difference;
		}
		
	}
	
	String replacePackage(String source, int packageNumber) {
		return source.replaceFirst("package package[0-9]*\\s*;", "package package" + packageNumber + ";");
	}
    
	public void createTests() {
		arrayListTests = new ArrayList<ArrayList<Long>>(Main.maxTestVectors);
		arrayListAnswers = new ArrayList<ArrayList<Long>>(Main.maxTestVectors);
		Test.createTests(arrayListTests, arrayListAnswers);
	}
	
	public void createEnviroment() {
		// model environment resource (specifically program size) as Summer-Winter-Summer or sizeBeforeRestrictMax-sizeBeforeRestrictMin-sizeBeforeRestrictMax
		double percent = (double)(generation%maxGenerationsReload)/maxGenerationsReload;
		double cosineWithOffset = (Math.cos(percent*2*Math.PI)+1)/2;
		sizeBeforeRestrict = (int)(sizeBeforeRestrictMin + cosineWithOffset*(sizeBeforeRestrictMax-sizeBeforeRestrictMin));
	}
	
	public static void main(String[] args) {
		Main main = new Main();
		for(main.generation=0; main.generation<1000000; main.generation++) {
			main.createEnviroment();
			main.createTests();
			main.createPopulation();
			main.compilePopulation();
			main.executePopulation();
			main.evaluatePopulation();
			main.storeBestFit();
			main.downselectPopulation();
			if(main.generation%main.maxGenerationsReload == 0) {
				LOGGER.fine("RLD" + main.generation + "ID" + main.listProgramPopulation.get(0).ID + main.listProgramPopulation.get(0).fitness.toString() + main.listProgramPopulation.get(0).source);
				main.loadProgram();
			}
			if(main.generation%100 == 0) {
				LOGGER.info("PP0" + main.generation + "ID" + main.listProgramPopulation.get(0).ID + main.listProgramPopulation.get(0).fitness.toString() + main.listProgramPopulation.get(0).source);
				//LOGGER.info("PPN" + main.generation + "ID" + main.listProgramPopulation.get(main.listProgramPopulation.size()-1).ID + main.listProgramPopulation.get(main.listProgramPopulation.size()-1).fitness.toString() + main.listProgramPopulation.get(main.listProgramPopulation.size()-1).source);
			}
		}
	}
}
