package minijava;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
	public final int maxParent = 2;	// Size of parent pool
	public final int maxChildren = 2;	// Number of children each parent produces
	public final int maxPopulation = maxParent*maxChildren + maxParent;	// Total population size
	public final int maxExecuteMilliseconds = 300000;
	public int generation = 0;
	
	private List<Program> listProgramParent = new ArrayList<Program>(maxParent);
	private List<Program> listProgramPopulation = new ArrayList<Program>(maxPopulation);
	private ArrayList<Long> arrayListTest;
	private ArrayList<Long> arrayListExpected;
	private Fitness fitnessBest;
	
	private List<ParseTree> listParseTree = new ArrayList<ParseTree>();
	private Vocabulary vocabulary;
	private Random random = new Random(0);
	private final static int maxSizeBeforeRestrict = 3000;
	private final static int maxDepthCondition = 1;
	
	public Main() {
		System.setProperty("java.home", JAVA_HOME);
		arrayListTest = getTestVector();
		arrayListExpected = new ArrayList<Long>(arrayListTest);
		Collections.sort(arrayListExpected);
		try {
			String source = new String(Files.readAllBytes(Paths.get(PROGRAM_FILENAME)));
			listProgramParent.add(new Program(replacePackage(source, 0), 0, arrayListTest));
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
			listProgramPopulation.add(new Program(source, indexPackage, arrayListTest));	// add parent to population
			indexPackage++;
			for(int indexChild=0; indexChild<maxChildren; indexChild++) {
				source = replacePackage(program.source, indexPackage);
				source = mutate(source);
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
		stringBuilder.append(generator(parseTree, length));	// replace interval [a,b] with random segment of code of same type
		stringBuilder.append(" ");
		stringBuilder.append(stringBuilderAppend);
		return stringBuilder.toString();
	}
	
	public String generator(ParseTree parseTree, int size) {
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
				return generateStatementContext(size);
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
		final int maxStatement = 1;
		int sizeStatement = 0;
		if(size<maxSizeBeforeRestrict) {
			sizeStatement = random.nextInt(maxStatement);
		}
		size += 2;
		stringBuilder.append("{");
		for(int count=0; count<sizeStatement; count++) {
			source = generateStatementContext(size);
			size += source.length();
			stringBuilder.append(source);
		}
		stringBuilder.append("} ");
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
	
	//'ArrayList<Long>' LONGARRAYNAME '= new ArrayList<Long>(Collections.nCopies(values00.size(), new Long(' expressionNumeric ')));'
	public String generateLongArrayDeclarationContext(int size) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("ArrayList<Long> ");
		stringBuilder.append(generateTerminalNode(size+stringBuilder.length(), "LONGARRAYNAME"));
		stringBuilder.append(" = new ArrayList<Long>(Collections.nCopies(values00.size(), new Long(");
		stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), 0));
		stringBuilder.append("))); ");
		return stringBuilder.toString();
	}
	
	//'Long' LONGNAME ' = new Long(0);'
	public String generateLongDeclarationContext(int size) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Long ");
		stringBuilder.append(generateTerminalNode(size+stringBuilder.length(), "LONGNAME"));
		stringBuilder.append(" = new Long(0); ");
		return stringBuilder.toString();
	}
	
	//'ArrayList<Boolean>' BOOLEANARRAYNAME '= new ArrayList<Boolean>(Collections.nCopies(values00.size(),' expressionBoolean '));'
	public String generateBooleanArrayDeclarationContext(int size) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("ArrayList<Boolean> ");
		stringBuilder.append(generateTerminalNode(size+stringBuilder.length(), "BOOLEANARRAYNAME"));
		stringBuilder.append(" = new ArrayList<Boolean>(Collections.nCopies(values00.size(),");
		stringBuilder.append(generateExpressionBooleanContext(size+stringBuilder.length(), 0));
		stringBuilder.append(")); ");
		return stringBuilder.toString();
	}
	
	//'Boolean' BOOLEANNAME ' = new Boolean(false);'
	public String generateBooleanDeclarationContext(int size) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Boolean ");
		stringBuilder.append(generateTerminalNode(size+stringBuilder.length(), "BOOLEANNAME"));
		stringBuilder.append(" = new Boolean(false); ");
		return stringBuilder.toString();
	}
	
	/*    :   'if(' expressionBoolean ')' block 'else' block
    |   'while(' expressionBoolean ')' block
    |	LONGARRAYNAME '.set(new Long(' expressionNumeric ').intValue(), new Long(' expressionNumeric '));'
    |   LONGNAME '=' 'new Long(' expressionNumeric ');'
    |	BOOLEANARRAYNAME '.set(new Long(' expressionNumeric ').intValue(), new Boolean(' expressionBoolean '));'
    |   BOOLEANNAME '=' 'new Boolean(' expressionBoolean ');'
    */
	public String generateStatementContext(int size) {
		StringBuilder stringBuilder = new StringBuilder();
		final int maxStatement = 8;	// 6 types + 1 double declaration + 1 empty declaration
		int statement = random.nextInt(maxStatement);
		if(size>maxSizeBeforeRestrict) {
			statement = 7;	// remove declaration
		}
		switch(statement) {
			case 0:
				stringBuilder.append("if(");
				stringBuilder.append(generateExpressionBooleanContext(size+stringBuilder.length(), 0));
				stringBuilder.append(") ");
				stringBuilder.append(generateBlockContext(size+stringBuilder.length()));
				stringBuilder.append("else ");
				stringBuilder.append(generateBlockContext(size+stringBuilder.length()));
				return stringBuilder.toString();
			case 1:
				stringBuilder.append("while(");
				stringBuilder.append(generateExpressionBooleanContext(size+stringBuilder.length(), 0));
				stringBuilder.append(") ");
				stringBuilder.append(generateBlockContext(size+stringBuilder.length()));
				return stringBuilder.toString();
			case 2:
				stringBuilder.append(generateTerminalNode(size+stringBuilder.length(), "LONGARRAYNAME"));
				stringBuilder.append(".set(new Long(");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), 0));
				stringBuilder.append(").intValue(), new Long(");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), 0));
				stringBuilder.append(")); ");
				return stringBuilder.toString();
			case 3:
				stringBuilder.append(generateTerminalNode(size+stringBuilder.length(), "LONGNAME"));
				stringBuilder.append(" = new Long(");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), 0));
				stringBuilder.append("); ");
				return stringBuilder.toString();
			case 4:
				stringBuilder.append(generateTerminalNode(size+stringBuilder.length(), "BOOLEANARRAYNAME"));
				stringBuilder.append(".set(new Long(");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), 0));
				stringBuilder.append(").intValue(), new Boolean(");
				stringBuilder.append(generateExpressionBooleanContext(size+stringBuilder.length(), 0));
				stringBuilder.append(")); ");
				return stringBuilder.toString();
			case 5:
				stringBuilder.append(generateTerminalNode(size+stringBuilder.length(), "BOOLEANNAME"));
				stringBuilder.append(" = new Boolean(");
				stringBuilder.append(generateExpressionBooleanContext(size+stringBuilder.length(), 0));
				stringBuilder.append("); ");
				return stringBuilder.toString();
			case 6:
				String source;
				size++;	// whitespace per-added to avoid possible infinite loop
				source = generateStatementContext(size);
				size += source.length();
				return source + " " + generateStatementContext(size);
			case 7:
				return " ";
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
		final int maxExpression = 11;	// 11 types
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
				stringBuilder.append("^");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 6:
				stringBuilder.append("(");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append("%");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 7:
				stringBuilder.append("(");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append("*");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 8:
				stringBuilder.append("(");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append("/");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 9:
				stringBuilder.append("(");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append("+");
				stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), depth+1));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 10:
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
	
	//LONGARRAYNAME '.get(new Long(' expressionNumeric ').intValue())'
	public String generateLongArrayValueContext(int size) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(generateTerminalNode(size+stringBuilder.length(), "LONGARRAYNAME"));
		stringBuilder.append(".get(new Long(");
		stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), 0));
		stringBuilder.append(").intValue())");
		return stringBuilder.toString();
	}
	
	//BOOLEANARRAYNAME '.get(new Long(' expressionNumeric ').intValue())'
	public String generateBooleanArrayValueContext(int size) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(generateTerminalNode(size+stringBuilder.length(), "BOOLEANARRAYNAME"));
		stringBuilder.append(".get(new Long(");
		stringBuilder.append(generateExpressionNumericContext(size+stringBuilder.length(), 0));
		stringBuilder.append(").intValue())");
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

			List<Future<Void>> listFuture = executorService.invokeAll(listCallable, maxExecuteMilliseconds, TimeUnit.MILLISECONDS);
			for(Future<Void> future : listFuture) {
				if(!future.isCancelled()) {
					try {
						future.get(maxExecuteMilliseconds, TimeUnit.MILLISECONDS);
					} catch (InterruptedException | ExecutionException | TimeoutException e) {
						e.printStackTrace();
						// runtime error
					}
				} else {
					System.out.println("Time exceeded for execution, halted");
				}
			}
			executorService.shutdown();
		} catch (SecurityException | IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void selection() {
		for(Program program : listProgramPopulation) {
			program.fitness.difference = getDifference(arrayListExpected, program.vectorActual);
		}
		Collections.sort(listProgramPopulation);
		if(fitnessBest == null) {
			fitnessBest = listProgramPopulation.get(0).fitness;
			System.out.println(listProgramPopulation.get(0).vectorActual.toString());
			System.out.println("GEN" + generation + " ID" + listProgramPopulation.get(0).ID + fitnessBest.toString());
			System.out.println(listProgramPopulation.get(0).source);
		} else if(fitnessBest.compareTo(listProgramPopulation.get(0).fitness) > 0) {
			fitnessBest = listProgramPopulation.get(0).fitness;
			//try {
				System.out.println(listProgramPopulation.get(0).vectorActual.toString());
				System.out.println("GEN" + generation + " ID" + listProgramPopulation.get(0).ID + fitnessBest.toString());
				System.out.println(listProgramPopulation.get(0).source);
				String source = listProgramPopulation.get(0).source;
				source = replacePackage(source, 0);
				//Files.write(Paths.get(PROGRAM_FILENAME),source.getBytes());
			//} catch (IOException e) {
			//	e.printStackTrace();
			//}
		}
		listProgramParent.clear();
		int indexPackage = 0;
		for(Program program : listProgramPopulation) {
			if(program.fitness.difference == Integer.MAX_VALUE || indexPackage>=maxParent) {
				break;
			}
			listProgramParent.add(new Program(replacePackage(program.source, indexPackage), indexPackage, arrayListTest));
			indexPackage++;
		}
	}
	
	int getDifference(ArrayList<Long> arrayListExpected, ArrayList<Long> arrayListActual) {
		int difference = 0;
		if(arrayListActual == null) {
			difference = Integer.MAX_VALUE;
		} else {
			for(int index=0; index<arrayListExpected.size(); index++) {
				difference += Math.abs(arrayListExpected.get(index) - arrayListActual.get(index));
			}
		}
		return difference;
	}
	
	String replacePackage(String source, int packageNumber) {
		return source.replaceFirst("package package[0-9]*\\s*;", "package package" + packageNumber + ";");
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
		for(main.generation=0; main.generation<6; main.generation++) {
			main.createPopulation();
			main.execute();
			main.selection();
			//if(main.generation%100 == 0) {
			//	System.out.print(main.generation + ",");
			//}
		}
		System.out.println("");
		System.out.println("Ending best " + main.fitnessBest.toString());
	}
}
