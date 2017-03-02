package minijava;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import minijava.parser.MiniJavaLexer;
import minijava.parser.MiniJavaParser;

public class Species implements Runnable {
	public Fitness fitnessBest = null;
	public final static int maxExecuteMilliseconds = 2000;
	public int stagnant = maxStagnant;
	public int species;
	
	private static final int maxStagnant = 5;	// number of years a species can live without progress on bestfit
	private final int maxParent = 5;	// Size of parent pool
	private final int maxChildren = 3;	// Number of children each parent produces
	private final int maxPopulation = maxParent*maxChildren + maxParent;	// Total population size
	private static final JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
	private Random random = new Random(Main.randomSeed);
	private final static int maxNewCodeSegmentSize = 75;
	private final static double worstFitAccepted = 2.0;
	private List<Program> listProgramParent = new ArrayList<Program>(maxParent);
	private List<Program> listProgramPopulation = new ArrayList<Program>(maxPopulation);
	private List<CallableMiniJava> listCallable = new ArrayList<CallableMiniJava>(maxPopulation);
	private String stringBestSourceCompact;
	private String stringBestSource;
	private static int sizeBeforeRestrictMin = 0;
	private static int sizeBeforeRestrictMax = 0;
	private static int sizeBeforeRestrict = 0;
	private final static double sizeBeforeRestrictMinPercent = 0.95;
	private final static double sizeBeforeRestrictMaxPercent = 1.25;
	private Tests tests = null;
	
	private int daysPerYear;
	private final String PROGRAM_FILENAME;
	private int year;
	private int day;
	
	public Species(int species, String sourceOrigin, Tests tests, int daysPerYear) {
		this.species = species;
		this.tests = tests;
		this.daysPerYear = daysPerYear;
		PROGRAM_FILENAME = new String("data" + File.separator + "GeneticProgram" + species + ".java");
		try {
			stringBestSource = new String(Files.readAllBytes(Paths.get(PROGRAM_FILENAME)));
		} catch (IOException e) {
			stringBestSource = sourceOrigin;
			try {
				Files.write(Paths.get(PROGRAM_FILENAME),stringBestSource.getBytes());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		stringBestSourceCompact = removeSpace(sourceOrigin);
	}
	
	public void initalizeYear(int year) {
		this.year = year;
		sizeBeforeRestrictMin = (int)(sizeBeforeRestrictMinPercent * stringBestSource.length());
		sizeBeforeRestrictMax = (int)(sizeBeforeRestrictMaxPercent * stringBestSource.length());
		stagnant--;
	}
	
	public void extinction() {
		LOGGER.info("EXTY" + year + "S" + species + fitnessBest.toString() + stringBestSource);
		try {
			Files.delete(Paths.get(PROGRAM_FILENAME));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initalizeDay(int day) {
		this.day = day;
	}
	
	@Override
	public void run() {
		createEnviroment();
		createPopulation();
		compilePopulation();
		executePopulation();
		evaluatePopulation();
		storeBestFit();
		downselectPopulation();
		//if(day%1 == 0) {
		//	LOGGER.info("Y" + year + "D" + day + "S" + listProgramPopulation.get(0).species + "ID" + listProgramPopulation.get(0).ID + listProgramPopulation.get(0).fitness.toString() + listProgramPopulation.get(0).source);
		//	LOGGER.info("Y" + year + "D" + day + "S" + listProgramPopulation.get(listProgramPopulation.size()-1).species + "ID" + listProgramPopulation.get(listProgramPopulation.size()-1).ID + listProgramPopulation.get(listProgramPopulation.size()-1).fitness.toString() + listProgramPopulation.get(listProgramPopulation.size()-1).source);
		//}
	}
	
	public void createEnviroment() {
		// model environment resource (specifically program size) as Summer-Winter-Summer or sizeBeforeRestrictMax-sizeBeforeRestrictMin-sizeBeforeRestrictMax
		double percent = (double)(day%daysPerYear)/daysPerYear;
		double cosineWithOffset = (Math.cos(percent*2*Math.PI)+1)/2;
		sizeBeforeRestrict = (int)(sizeBeforeRestrictMin + cosineWithOffset*(sizeBeforeRestrictMax-sizeBeforeRestrictMin));
	}

	public void createPopulation() {
		int indexPackage = 0;
		String source;
		listProgramPopulation.clear();
		if(listProgramParent.isEmpty()) {
			listProgramParent.add(new Program(stringBestSource, species, 0, sizeBeforeRestrict, tests));
		}
		//make ANTLR parsers for parents, used by crossover
		for(Program program : listProgramParent) {
			source = replacePackage(program.source, species, indexPackage);
			Program programParent = new Program(source, species, indexPackage, sizeBeforeRestrict, tests);
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
				source = replacePackage(source, species, indexPackage);
				listProgramPopulation.add(new Program(source, species, indexPackage, sizeBeforeRestrict, tests));	// add child to population
				indexPackage++;
			}
		}
	}
	
	public void compilePopulation() { 
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		for(Program program : listProgramPopulation) {
			try (StandardJavaFileManager standardJavaFileManager = javaCompiler.getStandardFileManager(diagnostics, Locale.ENGLISH, null)) {
				Iterable<? extends JavaFileObject> javaFileObject = Arrays.asList(program);
				ProgramClassSimpleJavaFileObject programClassSimpleJavaFileObject = null;
				try {
					programClassSimpleJavaFileObject = new ProgramClassSimpleJavaFileObject(Program.PACKAGE_SPECIES + species + "." + Program.PACKAGE_ID + program.ID + "." + Program.PROGRAM_CLASS);
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
		ExecutorService executorService = Executors.newFixedThreadPool(Main.threads-1);
		try {	// Load the class and use it
			listCallable.clear();
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
				int milliseconds = maxExecuteMilliseconds;
				while(!executorService.awaitTermination(maxExecuteMilliseconds, TimeUnit.MILLISECONDS)) {
					milliseconds += maxExecuteMilliseconds;
					LOGGER.warning("Runaway species #" + species + " for " + milliseconds + " milliseconds");
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			LOGGER.severe("Exception on species #" + species);
		}
	}
	
	public void evaluatePopulation() {
		for(Program program : listProgramPopulation) {
			List<Long> arrayListDifferences = Tests.getDifferenceAnswerAnswer(tests.listTests, program.vectors);
			if(arrayListDifferences == null || arrayListDifferences.size()!=tests.listDifferencesBase.size()) {
				program.fitness.difference = Long.MAX_VALUE;
				program.fitness.fit = Double.MAX_VALUE;
			} else {
				long differenceSum = 0;
				double fitSum = 0;
				for(int index=0; index<arrayListDifferences.size(); index++) {
					long difference = arrayListDifferences.get(index);
					double fit = (double)difference / tests.listDifferencesBase.get(index);
					differenceSum += difference;
					fitSum += fit;
				}
				program.fitness.difference = differenceSum/arrayListDifferences.size();
				program.fitness.fit = fitSum/arrayListDifferences.size();
			}
		}
	}
	
	public void storeBestFit() {
		Collections.sort(listProgramPopulation);
		if(fitnessBest == null) {
			stagnant = maxStagnant;
			fitnessBest = listProgramPopulation.get(0).fitness;
			stringBestSource = listProgramPopulation.get(0).source;
			stringBestSourceCompact = removeSpace(stringBestSource);
			LOGGER.info("NEWY" + year + "D" + day + "S" + listProgramPopulation.get(0).species + "ID" + listProgramPopulation.get(0).ID + fitnessBest.toString() + listProgramPopulation.get(0).source);
		} else if(fitnessBest.fit >= listProgramPopulation.get(0).fitness.fit) {	// first store in terms of best fit, next use compareTo
			if(fitnessBest.compareTo(listProgramPopulation.get(0).fitness) > 0) {
				stagnant = maxStagnant;
				fitnessBest = listProgramPopulation.get(0).fitness;
				try {
					LOGGER.info("BSTY" + year + "D" + day + "S" + listProgramPopulation.get(0).species + "ID" + listProgramPopulation.get(0).ID + fitnessBest.toString() + listProgramPopulation.get(0).source);
					String source = listProgramPopulation.get(0).source;
					source = replacePackage(source, species, 0);
					if(!removeSpace(source).equals(stringBestSourceCompact)) {
						stringBestSource = source;
						stringBestSourceCompact = removeSpace(stringBestSource);	// only save if different to reduce storage writes
						Files.write(Paths.get(PROGRAM_FILENAME),source.getBytes());
					}
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
			String stringSource = removeSpace(programPopulation.source);
			for(Program programParent : listProgramParent) {
				if(removeSpace(programParent.source).equalsIgnoreCase(stringSource)) {
					exists = true;
					break;
				}
			}
			if(!exists) {
				listProgramParent.add(new Program(replacePackage(programPopulation.source, species, indexPackage), species, indexPackage, sizeBeforeRestrict, tests));
				indexPackage++;
			}
		}
	}
	
	public static String replacePackage(String source, int species, int packageNumber) {
		return source.replaceFirst("package species[0-9][^;]*;", "package " + Program.PACKAGE_SPECIES + species + "." + Program.PACKAGE_ID + packageNumber + ";");
	}
	
	private String removeSpace(String source) {
		return source.replaceAll("\\s+","");
	}
	
	private String createProgram(Program program1, Program program2, String source) {
		List<ParseTree> listParseTree1 = new ArrayList<ParseTree>();
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
				stringBuilder.append(Generator.getCode(program2.miniJavaParser, parseTree2));
			} else {	// mutate source because no equivalent class types
				stringBuilder.append(generator(program1.miniJavaParser, parseTree1));	// replace interval [a,b] with random segment of code of same type
			}
		}
		stringBuilder.append(" ");
		stringBuilder.append(stringBuilderAppend);
		return stringBuilder.toString();
	}
	
	private String generator(MiniJavaParser miniJavaParser, ParseTree parseTree) {
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
				return Generator.generateExpressionNumericContext(maxNewCodeSegmentSize, miniJavaParser, parseTree);
			case "minijava.parser.MiniJavaParser$ExpressionBooleanContext":
				return Generator.generateExpressionBooleanContext(maxNewCodeSegmentSize, miniJavaParser, parseTree);
			case "minijava.parser.MiniJavaParser$LongArrayValueContext":
				return Generator.generateLongArrayValueContext(maxNewCodeSegmentSize);
			case "minijava.parser.MiniJavaParser$BooleanArrayValueContext":
				return Generator.generateBooleanArrayValueContext(maxNewCodeSegmentSize);
			case "org.antlr.v4.runtime.tree.TerminalNodeImpl":
				TerminalNode terminalNode = (TerminalNode)parseTree;
				return Generator.generateTerminalNode(maxNewCodeSegmentSize, miniJavaParser, parseTree, MiniJavaParser.VOCABULARY.getSymbolicName(terminalNode.getSymbol().getType()));
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
		for(int index=0; index<MiniJavaParser.VOCABULARY.getMaxTokenType(); index++) {
			if(text.equals(MiniJavaParser.VOCABULARY.getLiteralName(index))) {
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
}
