package minijava;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import minijava.parser.MiniJavaLexer;
import minijava.parser.MiniJavaParser;
import minijava.parser.MiniJavaParser.*;

public class Main {
	// This will require JAVA_HOME be set to JDK. JRE home will cause a NullPointerException
	public final static String JAVA_HOME = new String("/usr/local/jdk1.8.0_121/");
	public final static String PROGRAM_FILENAME = new String("GeneticProgram.java");
	public final Path pathBase = Paths.get("");
	public final int maxParent = 5;	// Size of parent pool
	public final int maxChildren = 3;	// Number of children each parent produces
	public final int maxPopulation = maxParent*maxChildren + maxParent;	// Total population size
	public final int maxExecuteMilliseconds = 3000;
	public final int maxGenerationsReload = 20;	// Force reload, because best fit program is usually just best due to random vector 
	public int generation = 0;
	
	private List<Program> listProgramParent = new ArrayList<Program>(maxParent);
	public List<Program> listProgramPopulation = new ArrayList<Program>(maxPopulation);
	private ArrayList<Long> arrayListTest = new ArrayList<Long>();
	private ArrayList<Long> arrayListExpected;
	private Fitness fitnessBest;
	
	private List<ParseTree> listParseTree = new ArrayList<ParseTree>();
	private Vocabulary vocabulary;
	private Random random = new Random(0);
	private final static int maxSizeBeforeRestrict = 3000;
	private final static int maxDepthCondition = 1;
	
	public Main() {
		System.setProperty("java.home", JAVA_HOME);
		loadProgram();
	}

	public void loadProgram() {
		try {
			String source = new String(Files.readAllBytes(Paths.get(PROGRAM_FILENAME)));
			listProgramParent.add(new Program(replacePackage(source, 0), 0, arrayListTest));
			fitnessBest = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createPopulation() {
		int indexPackage = 0;
		String source;
		listProgramPopulation.clear();
		for(Program program : listProgramParent) {
			source = replacePackage(program.source, indexPackage);
//System.out.println(source);
			listProgramPopulation.add(new Program(source, indexPackage, arrayListTest));	// add parent to population
			indexPackage++;
			for(int indexChild=0; indexChild<maxChildren; indexChild++) {
				source = replacePackage(program.source, indexPackage);
				source = mutate(source);
//System.out.println(source);
				listProgramPopulation.add(new Program(source, indexPackage, arrayListTest));	// add child to population
				indexPackage++;
			}
		}
	}
	
	public String mutate(String source) {
		MiniJavaLexer miniJavaLexer = new MiniJavaLexer(new ANTLRInputStream(source));
		MiniJavaParser miniJavaParser = new MiniJavaParser(new CommonTokenStream(miniJavaLexer));
		vocabulary = miniJavaParser.getVocabulary();
		BlockContext blockContext = miniJavaParser.program().block();
		listParseTree.clear();
		getParseTreeNonLiteral(blockContext);
		ParseTree parseTree = listParseTree.get(random.nextInt(listParseTree.size()));
		int a = parseTree.getSourceInterval().a;
		int b = parseTree.getSourceInterval().b;
		int size = miniJavaParser.getTokenStream().size()-1;	// remove last token inserted by ANTLR, <EOF>
		StringBuilder stringBuilder = new StringBuilder(source.length());
		for(int index=0; index<a; index++) {
			stringBuilder.append(miniJavaParser.getTokenStream().get(index).getText());
			stringBuilder.append(" ");
		}
		//Oddity, but to calculate total size of prepend and append source code
		StringBuilder stringBuilderAppend = new StringBuilder(source.length());
		for(int index=b+1; index<size; index++) {
			stringBuilderAppend.append(miniJavaParser.getTokenStream().get(index).getText());
			stringBuilderAppend.append(" ");
		}

		int length = stringBuilder.length() + stringBuilderAppend.length();
		stringBuilder.append(generator(miniJavaParser, parseTree, length));	// replace interval [a,b] with random segment of code of same type
		stringBuilder.append(" ");
		stringBuilder.append(stringBuilderAppend);
		return stringBuilder.toString();
	}
	
	public String generator(MiniJavaParser miniJavaParser, ParseTree parseTree, int size) {
		switch(parseTree.getClass().getName()) {
			case "minijava.parser.MiniJavaParser$BlockContext":
				return generateBlockContext(size);
			case "minijava.parser.MiniJavaParser$DeclarationContext":
				return generateDeclarationContext(size);
			case "minijava.parser.MiniJavaParser$LongArrayDeclarationContext":
				return generateLongArrayDeclarationContext(size);
			case "minijava.parser.MiniJavaParser$LongDeclarationContext":
				return generateLongDeclarationContext(size);
			case "minijava.parser.MiniJavaParser$BooleanArrayDeclarationContext":
				return generateBooleanArrayDeclarationContext(size);
			case "minijava.parser.MiniJavaParser$BooleanDeclarationContext":
				return generateBooleanDeclarationContext(size);
			case "minijava.parser.MiniJavaParser$StatementContext":
				return generateStatementContext(size, miniJavaParser, parseTree);
			case "minijava.parser.MiniJavaParser$ExpressionNumericContext":
				return generateExpressionNumericContext(size, 0);
			case "minijava.parser.MiniJavaParser$ExpressionBooleanContext":
				return generateExpressionBooleanContext(size, 0);
			case "minijava.parser.MiniJavaParser$LongArrayValueContext":
				return generateLongArrayValueContext(size);
			case "minijava.parser.MiniJavaParser$BooleanArrayValueContext":
				return generateBooleanArrayValueContext(size);
			case "org.antlr.v4.runtime.tree.TerminalNodeImpl":
				TerminalNode terminalNode = (TerminalNode)parseTree;
				return generateTerminalNode(size, vocabulary.getSymbolicName(terminalNode.getSymbol().getType()));
			default:
				return null;
		}
	}
	
	//'{' declaration* statement* '}'
	public String generateBlockContext(int size) {
		StringBuilder stringBuilder = new StringBuilder();
		String source;
		size += 2;
		stringBuilder.append("{");
		if(size<maxSizeBeforeRestrict) {
			source = generateStatementContext(size, null, null);
			size += source.length();
			stringBuilder.append(source);
		}
		stringBuilder.append("}");
		return stringBuilder.toString();
	}
	
	/*    :   longArrayDeclaration
    |   longDeclaration
    |   booleanArrayDeclaration
    |   booleanDeclaration
    */
	public String generateDeclarationContext(int size) {
		String source;
		final int maxDeclaration = 6;	// 4 types + 1 double declaration + 1 empty declaration
		int declaration = random.nextInt(maxDeclaration);
		if(size>maxSizeBeforeRestrict) {
			declaration = 5;	// remove declaration
		}
		switch(declaration) {
			case 0:
				return generateLongArrayDeclarationContext(size);
			case 1:
				return generateLongDeclarationContext(size);
			case 2:
				return generateBooleanArrayDeclarationContext(size);
			case 3:
				return generateBooleanDeclarationContext(size);
			case 4:
				size++;	// whitespace per-added to avoid possible infinite loop
				source = generateDeclarationContext(size);
				size += source.length();
				return source + " " + generateDeclarationContext(size);
			case 5:
				return " ";
			default:
				return null;
		}
	}
	
	//'ArrayList<Long>' LONGARRAYNAME '= new ArrayList<Long>(size);'
	public String generateLongArrayDeclarationContext(int size) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("ArrayList<Long> ");
		stringBuilder.append(generateTerminalNode(size+stringBuilder.length(), "LONGARRAYNAME"));
		stringBuilder.append("= new ArrayList<Long>(size); ");
		return stringBuilder.toString();
	}
	
	//'Long' LONGNAME '= new Long(0);'
	public String generateLongDeclarationContext(int size) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Long ");
		stringBuilder.append(generateTerminalNode(size+stringBuilder.length(), "LONGNAME"));
		stringBuilder.append("= new Long(0); ");
		return stringBuilder.toString();
	}
	
	//'ArrayList<Boolean>' BOOLEANARRAYNAME '= new ArrayList<Boolean>(size);'
	public String generateBooleanArrayDeclarationContext(int size) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("ArrayList<Boolean> ");
		stringBuilder.append(generateTerminalNode(size+stringBuilder.length(), "BOOLEANARRAYNAME"));
		stringBuilder.append("= new ArrayList<Boolean>(size); ");
		return stringBuilder.toString();
	}
	
	//'Boolean' BOOLEANNAME '= new Boolean(false);'
	public String generateBooleanDeclarationContext(int size) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Boolean ");
		stringBuilder.append(generateTerminalNode(size+stringBuilder.length(), "BOOLEANNAME"));
		stringBuilder.append("= new Boolean(false); ");
		return stringBuilder.toString();
	}
	
	/*    :   'if(' expressionBoolean ')' block 'else' block
    |   'while(!Thread.currentThread().isInterrupted()&&' expressionBoolean ')' block
    |	LONGARRAYNAME '.set(new Long(' expressionNumeric ').intValue()%size, new Long(' expressionNumeric '));'
    |   LONGNAME '=' 'new Long(' expressionNumeric ');'
    |	BOOLEANARRAYNAME '.set(new Long(' expressionNumeric ').intValue()%size, new Boolean(' expressionBoolean '));'
    |   BOOLEANNAME '=' 'new Boolean(' expressionBoolean ');'
    */
	public String generateStatementContext(int size, MiniJavaParser miniJavaParser, ParseTree parseTree) {
		StringBuilder stringBuilder = new StringBuilder();
		int maxStatement;
		StringBuilder stringBuilderSourceStatement = null;
		String sourceStatement = null;
		if(parseTree == null) {
			maxStatement = 7;	// 6 types + 1 empty declaration
		} else {
			maxStatement = 10;	// 6 types + 1 empty declaration + 1 prepend statement + 1 append statement + 1 statement duplication
		}
		int statement = random.nextInt(maxStatement);
		if(size>maxSizeBeforeRestrict) {
			statement = 6;	// remove declaration
		}
		if (statement==7 || statement==8 || statement ==9) {
			stringBuilderSourceStatement = new StringBuilder();
			for(int index=parseTree.getSourceInterval().a; index<=parseTree.getSourceInterval().b; index++) {
				stringBuilderSourceStatement.append(miniJavaParser.getTokenStream().get(index).getText());
				stringBuilderSourceStatement.append(" ");
			}
			sourceStatement = stringBuilderSourceStatement.toString();
		}
		switch(statement) {
			case 0:
				stringBuilder.append("if(");
				stringBuilder.append(generateExpressionBooleanContext(size+stringBuilder.length(), 0));
				stringBuilder.append(")");
				stringBuilder.append(generateBlockContext(size+stringBuilder.length()));
				stringBuilder.append("else");
				stringBuilder.append(generateBlockContext(size+stringBuilder.length()));
				return stringBuilder.toString();
			case 1:
				stringBuilder.append("while(!Thread.currentThread().isInterrupted()&&");
				stringBuilder.append(generateExpressionBooleanContext(size+stringBuilder.length(), 0));
				stringBuilder.append(")");
				stringBuilder.append(generateBlockContext(size+stringBuilder.length()));
				return stringBuilder.toString();
			case 2:
				stringBuilder.append(generateTerminalNode(size+stringBuilder.length(), "LONGARRAYNAME"));
				stringBuilder.append(".set(new Long(");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), 0));
				stringBuilder.append(").intValue()%size, new Long(");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), 0));
				stringBuilder.append("));");
				return stringBuilder.toString();
			case 3:
				stringBuilder.append(generateTerminalNode(size+stringBuilder.length(), "LONGNAME"));
				stringBuilder.append("= new Long(");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), 0));
				stringBuilder.append(");");
				return stringBuilder.toString();
			case 4:
				stringBuilder.append(generateTerminalNode(size+stringBuilder.length(), "BOOLEANARRAYNAME"));
				stringBuilder.append(".set(new Long(");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), 0));
				stringBuilder.append(").intValue()%size, new Boolean(");
				stringBuilder.append(generateExpressionBooleanContext(size+stringBuilder.length(), 0));
				stringBuilder.append("));");
				return stringBuilder.toString();
			case 5:
				stringBuilder.append(generateTerminalNode(size+stringBuilder.length(), "BOOLEANNAME"));
				stringBuilder.append("= new Boolean(");
				stringBuilder.append(generateExpressionBooleanContext(size+stringBuilder.length(), 0));
				stringBuilder.append(");");
				return stringBuilder.toString();
			case 6:
				return " ";
			case 7:
				size += sourceStatement.length();
				return sourceStatement + " " + generateStatementContext(size, null, null);
			case 8:
				size += sourceStatement.length();
				return generateStatementContext(size, null, null) + " " +  sourceStatement;
			case 9:
				return sourceStatement + " " +  sourceStatement;
			default:
				return null;
		}
	}
	
	/*     :   NUMBER
    |   LONGNAME
    |	LONGARRAYNAME '.' 'size()'
    |   longArrayValue
    |   '(' '-' expressionNumeric ')'
    |   expressionNumeric '^' expressionNumeric
    |   expressionNumeric '%' expressionNumeric
    |   expressionNumeric '*' expressionNumeric
    |   expressionNumeric '/' expressionNumeric
    |   expressionNumeric '+' expressionNumeric
    |   expressionNumeric '-' expressionNumeric
    */
	public String generateExpressionNumericContext(int size, int depth) {
		StringBuilder stringBuilder = new StringBuilder();
		final int maxExpression = 13;	// 13 types
		int expression = random.nextInt(maxExpression);
		if(depth>maxDepthCondition) {
			expression = random.nextInt(3);	// limit to first 3 types
		}
		if(size>maxSizeBeforeRestrict) {
			expression = random.nextInt(3);	// limit to first 3 types
		}
		switch(expression) {
			case 0:
				stringBuilder.append(generateTerminalNode(size+stringBuilder.length(), "NUMBER"));
				return stringBuilder.toString();
			case 1:
				stringBuilder.append(generateTerminalNode(size+stringBuilder.length(), "LONGNAME"));
				return stringBuilder.toString();
			case 2:
				stringBuilder.append(generateTerminalNode(size+stringBuilder.length(), "LONGARRAYNAME"));
				stringBuilder.append(".size()");
				return stringBuilder.toString();
			case 3:
				stringBuilder.append(generateLongArrayValueContext(size));
				return stringBuilder.toString();
			case 4:
				stringBuilder.append("(-");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 5:
				stringBuilder.append("(");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append("&");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 6:
				stringBuilder.append("(");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append("|");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 7:
				stringBuilder.append("(");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append("^");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 8:
				stringBuilder.append("(");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append("%");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 9:
				stringBuilder.append("(");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append("*");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 10:
				stringBuilder.append("(");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append("/");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 11:
				stringBuilder.append("(");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append("+");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 12:
				stringBuilder.append("(");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append("-");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append(")");
				return stringBuilder.toString();
			default:
				return null;
		}
	}
	
	/*    :   BOOLEAN
    |   BOOLEANNAME
    |   booleanArrayValue
    |   '(' '!' expressionBoolean ')'
    |   expressionNumeric '<' expressionNumeric
    |   expressionNumeric '<=' expressionNumeric
    |   expressionNumeric '==' expressionNumeric
    |   expressionNumeric '!=' expressionNumeric
    |   expressionBoolean '==' expressionBoolean
    |   expressionBoolean '!=' expressionBoolean
    |   expressionBoolean '&&' expressionBoolean
    |   expressionBoolean '||' expressionBoolean
    */
	public String generateExpressionBooleanContext(int size, int depth) {
		StringBuilder stringBuilder = new StringBuilder();
		final int maxExpression = 12;	// 12 types
		int expression = random.nextInt(maxExpression);
		if(depth>maxDepthCondition) {
			expression = random.nextInt(4);	// limit to first 4 types
		}
		if(size>maxSizeBeforeRestrict) {
			expression = random.nextInt(4);	// limit to first 4 types
		}
		switch(expression) {
			case 0:
				stringBuilder.append(generateTerminalNode(size+stringBuilder.length(), "BOOLEAN"));
				return stringBuilder.toString();
			case 1:
				stringBuilder.append(generateTerminalNode(size+stringBuilder.length(), "BOOLEANNAME"));
				return stringBuilder.toString();
			case 2:
				stringBuilder.append(generateBooleanArrayValueContext(size));
				return stringBuilder.toString();
			case 3:
				stringBuilder.append("(!");
				stringBuilder.append(generateExpressionBooleanContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 4:
				stringBuilder.append("(");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append("<");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 5:
				stringBuilder.append("(");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append("<=");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 6:
				stringBuilder.append("(");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append("==");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 7:
				stringBuilder.append("(");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append("!=");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 8:
				stringBuilder.append("(");
				stringBuilder.append(generateExpressionBooleanContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append("==");
				stringBuilder.append(generateExpressionBooleanContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 9:
				stringBuilder.append("(");
				stringBuilder.append(generateExpressionBooleanContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append("!=");
				stringBuilder.append(generateExpressionBooleanContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 10:
				stringBuilder.append("(");
				stringBuilder.append(generateExpressionBooleanContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append("&&");
				stringBuilder.append(generateExpressionBooleanContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 11:
				stringBuilder.append("(");
				stringBuilder.append(generateExpressionBooleanContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append("||");
				stringBuilder.append(generateExpressionBooleanContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append(")");
				return stringBuilder.toString();
			default:
				return null;
		}
	}
	
	//LONGARRAYNAME '.get(new Long(' expressionNumeric ').intValue()%size)'
	public String generateLongArrayValueContext(int size) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(generateTerminalNode(size+stringBuilder.length(), "LONGARRAYNAME"));
		stringBuilder.append(".get(new Long(");
		stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), 0));
		stringBuilder.append(").intValue()%size)");
		return stringBuilder.toString();
	}
	
	//BOOLEANARRAYNAME '.get(new Long(' expressionNumeric ').intValue()%size)'
	public String generateBooleanArrayValueContext(int size) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(generateTerminalNode(size+stringBuilder.length(), "BOOLEANARRAYNAME"));
		stringBuilder.append(".get(new Long(");
		stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), 0));
		stringBuilder.append(").intValue()%size)");
		return stringBuilder.toString();
	}
	
	//LONGARRAYNAME	'values' DIGIT DIGIT
	//LONGNAME	'value' DIGIT DIGIT
	//BOOLEANARRAYNAME	'conditions' DIGIT DIGIT
	//BOOLEANNAME	'condition' DIGIT DIGIT
	//BOOLEAN	'false' | 'true'
	//NUMBER	'0' | DIGITNOZERO DIGIT*
	public String generateTerminalNode(int size, String symbolicName) {
		StringBuilder stringBuilder = new StringBuilder();
		switch(symbolicName) {
			case "LONGARRAYNAME":
				stringBuilder.append("values");
				//stringBuilder.append(random.nextInt(10));
				stringBuilder.append(0);
				stringBuilder.append(random.nextInt(10));
				return stringBuilder.toString();
			case "LONGNAME":
				stringBuilder.append("value");
				//stringBuilder.append(random.nextInt(10));
				stringBuilder.append(0);
				stringBuilder.append(random.nextInt(10));
				return stringBuilder.toString();
			case "BOOLEANARRAYNAME":
				stringBuilder.append("conditions");
				//stringBuilder.append(random.nextInt(10));
				stringBuilder.append(0);
				stringBuilder.append(random.nextInt(10));
				return stringBuilder.toString();
			case "BOOLEANNAME":
				stringBuilder.append("condition");
				//stringBuilder.append(random.nextInt(10));
				stringBuilder.append(0);
				stringBuilder.append(random.nextInt(10));
				return stringBuilder.toString();
			case "BOOLEAN":
				if(random.nextBoolean()) {
					return "true";
				} else {
					return "false";
				}
			case "NUMBER":
				return Integer.toString(random.nextInt(100));
			default:
				return null;
		}
	}
	
	private void getParseTreeNonLiteral(ParseTree parseTree) {
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
				getParseTreeNonLiteral(parseTreeChild);
			}
		}
	}
	
	public void execute() {
		final ExecutorService executorService = Executors.newFixedThreadPool(maxPopulation);
		try {	// Load the class and use it
			List<CallableMiniJava> listCallable = new ArrayList<CallableMiniJava>(maxPopulation);
			for(Program program : listProgramPopulation) {
				listCallable.add(new CallableMiniJava(program));
			}
			for(CallableMiniJava callableMiniJava : listCallable) {
				executorService.execute(callableMiniJava);
			}
			executorService.shutdown();
			if(!executorService.awaitTermination(maxExecuteMilliseconds, TimeUnit.MILLISECONDS)) {
				//System.out.println("Forcing shutdownNow");
				executorService.shutdownNow();
			}
		} catch (Exception e) {
			System.out.println("Exception on terminate");
		}
	}
	
	public void selection() {
		long differenceBase = getDifference(arrayListExpected, arrayListTest);
		for(Program program : listProgramPopulation) {
			program.fitness.difference = getDifference(arrayListExpected, program.vector);
			program.fitness.fit = (double)program.fitness.difference / differenceBase;
		}
		Collections.sort(listProgramPopulation);
		if(fitnessBest == null) {
			fitnessBest = listProgramPopulation.get(0).fitness;
			System.out.println("GEN" + generation + "ID" + listProgramPopulation.get(0).ID + fitnessBest.toString() + listProgramPopulation.get(0).vector.toString() + listProgramPopulation.get(0).source);
		} else if(fitnessBest.compareTo(listProgramPopulation.get(0).fitness) > 0) {
			fitnessBest = listProgramPopulation.get(0).fitness;
			try {
				System.out.println("GEN" + generation + "ID" + listProgramPopulation.get(0).ID + fitnessBest.toString() + listProgramPopulation.get(0).vector.toString() + listProgramPopulation.get(0).source);
				String source = listProgramPopulation.get(0).source;
				source = replacePackage(source, 0);
				Files.write(Paths.get(PROGRAM_FILENAME),source.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		listProgramParent.clear();
		int indexPackage = 0;
		for(Program program : listProgramPopulation) {
			if(indexPackage>=maxParent || program.fitness.difference == Long.MAX_VALUE || program.fitness.speed >= Integer.MAX_VALUE || program.fitness.size == Integer.MAX_VALUE) {
				break;
			}
			boolean exists = false;
			String stringSource = program.source.replaceAll("\\s+","");
			for(Program programParent : listProgramParent) {
				if(programParent.source.replaceAll("\\s+","").equalsIgnoreCase(stringSource)) {
					exists = true;
					break;
				}
			}
			if(!exists) {
				listProgramParent.add(new Program(replacePackage(program.source, indexPackage), indexPackage, arrayListTest));
				indexPackage++;
			}
		}
		if(listProgramParent.size() == 0) {
			System.out.println("Restarting");
			loadProgram();
		}
	}
	
	long getDifference(ArrayList<Long> arrayList1, ArrayList<Long> arrayList2) {
		Long difference = new Long(Long.MAX_VALUE);
		if(arrayList1 != null && arrayList2 != null && arrayList1.size()==arrayList2.size()) {
			difference = 0L;
			for(int index=0; index<arrayListExpected.size(); index++) {
				difference += Math.abs(arrayList1.get(index) - arrayList2.get(index));
			}
		}
		return difference;
	}
	
	String replacePackage(String source, int packageNumber) {
		return source.replaceFirst("package package[0-9]*\\s*;", "package package" + packageNumber + ";");
	}
	
	public void initalizeTestVector() {
		arrayListTest.clear();
		arrayListTest.add(new Long(random.nextInt(Integer.MAX_VALUE)));
		arrayListTest.add(new Long(random.nextInt(Integer.MAX_VALUE)));
		arrayListTest.add(new Long(random.nextInt(Integer.MAX_VALUE)));
		arrayListTest.add(new Long(random.nextInt(Integer.MAX_VALUE)));
		arrayListTest.add(new Long(random.nextInt(Integer.MAX_VALUE)));
		arrayListTest.add(new Long(random.nextInt(Integer.MAX_VALUE)));
		arrayListTest.add(new Long(random.nextInt(Integer.MAX_VALUE)));
		arrayListTest.add(new Long(random.nextInt(Integer.MAX_VALUE)));
		arrayListTest.add(new Long(random.nextInt(Integer.MAX_VALUE)));
		arrayListTest.add(new Long(random.nextInt(Integer.MAX_VALUE)));
		arrayListExpected = new ArrayList<Long>(arrayListTest);
		Collections.sort(arrayListExpected);
	}
    
	public static void main(String[] args) {
		Main main = new Main();
		for(main.generation=0; main.generation<1000000; main.generation++) {
			main.initalizeTestVector();
			main.createPopulation();
			main.execute();
			main.selection();
			if(main.generation%main.maxGenerationsReload == 0) {
				main.loadProgram();
			}
			if(main.generation%100 == 0) {
				System.out.println("OUT" + main.generation + "ID" + main.listProgramPopulation.get(0).ID + main.listProgramPopulation.get(0).fitness.toString() + main.listProgramPopulation.get(0).vector.toString() + main.listProgramPopulation.get(0).source);
			}
		}
		System.out.println("");
		System.out.println("Ending best " + main.fitnessBest.toString());
	}
}
