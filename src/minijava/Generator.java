package minijava;

import java.util.Random;
import java.util.logging.Logger;

import org.antlr.v4.runtime.tree.ParseTree;

import minijava.parser.MiniJavaParser;

public class Generator {
	private static Random random = new Random(GP.RANDOM_SEED);
	private static final Logger LOGGER = Logger.getLogger(Generator.class.getName());
	
	//'{' declaration* statement* '}'
	public static String generateBlockContext(int maxNewCodeSegmentSize) {
		StringBuilder stringBuilder = new StringBuilder();
		String source;
		maxNewCodeSegmentSize -= 2;
		stringBuilder.append("{");
		if(maxNewCodeSegmentSize>0) {
			source = generateStatementContext(maxNewCodeSegmentSize, null, null);
			stringBuilder.append(source);
		}
		stringBuilder.append("}");
		return stringBuilder.toString();
	}
	
	/*    :   longArrayDeclaration
    |   longDeclaration
    |   booleanDeclaration
    */
	public static String generateDeclarationContext(int maxNewCodeSegmentSize) {
		String source;
		final int maxDeclaration = 5;	// 3 types + 1 double declaration + 1 empty declaration
		int declaration = random.nextInt(maxDeclaration);
		if(maxNewCodeSegmentSize<=0) {
			declaration = 4;	// remove declaration
		}
		switch(declaration) {
			case 0:
				return generateLongArrayDeclarationContext(maxNewCodeSegmentSize);
			case 1:
				return generateLongDeclarationContext(maxNewCodeSegmentSize);
			case 2:
				return generateBooleanDeclarationContext(maxNewCodeSegmentSize);
			case 3:
				maxNewCodeSegmentSize--;	// decrement to avoid possible infinite loop
				source = generateDeclarationContext(maxNewCodeSegmentSize);
				maxNewCodeSegmentSize -= source.length();
				if(maxNewCodeSegmentSize>0) {
					source = source + " " + generateDeclarationContext(maxNewCodeSegmentSize);
				}
				return source;
			case 4:
				return " ";
			default:
				LOGGER.severe("Defective code in generateDeclarationContext)");
				return null;
		}
	}
	
	//'ArrayList<Long>' LONGARRAYNAME '= new ArrayList<Long>(size);'
	public static String generateLongArrayDeclarationContext(int maxNewCodeSegmentSize) {
		StringBuilder stringBuilder = new StringBuilder();
		if(maxNewCodeSegmentSize>0) {
			stringBuilder.append("ArrayList<Long> ");
			stringBuilder.append(generateTerminalNode(maxNewCodeSegmentSize-stringBuilder.length(), null, null, "LONGARRAYNAME"));
			stringBuilder.append("= new ArrayList<Long>(size); ");
		}
		return stringBuilder.toString();
	}
	
	//'Long' LONGNAME '= new Long(0);'
	public static String generateLongDeclarationContext(int maxNewCodeSegmentSize) {
		StringBuilder stringBuilder = new StringBuilder();
		if(maxNewCodeSegmentSize>0) {
			stringBuilder.append("Long ");
			stringBuilder.append(generateTerminalNode(maxNewCodeSegmentSize-stringBuilder.length(), null, null, "LONGNAME"));
			stringBuilder.append("= new Long(0); ");
		}
		return stringBuilder.toString();
	}
	
	//'Boolean' BOOLEANNAME '= new Boolean(false);'
	public static String generateBooleanDeclarationContext(int maxNewCodeSegmentSize) {
		StringBuilder stringBuilder = new StringBuilder();
		if(maxNewCodeSegmentSize>0) {
			stringBuilder.append("Boolean ");
			stringBuilder.append(generateTerminalNode(maxNewCodeSegmentSize-stringBuilder.length(), null, null, "BOOLEANNAME"));
			stringBuilder.append("= new Boolean(false); ");
		}
		return stringBuilder.toString();
	}
	
	/*    :   'if(' expressionBoolean ')' block 'else' block
    |   'while(!Thread.currentThread().isInterrupted()&&' expressionBoolean ')' block
    |	LONGARRAYNAME '.set(new Long(' expressionNumeric ').intValue()%size, new Long(' expressionNumeric '));'
    |   LONGNAME '=' 'new Long(' expressionNumeric ');'
    |   BOOLEANNAME '=' 'new Boolean(' expressionBoolean ');'
    */
	public static String generateStatementContext(int maxNewCodeSegmentSize, MiniJavaParser miniJavaParser, ParseTree parseTree) {
		StringBuilder stringBuilder = new StringBuilder();
		int maxStatement = 6;	// 5 types + 1 empty declaration
		int variant = 0;
		String sourceStatement = null;
		if(parseTree != null && random.nextBoolean()) {
			sourceStatement = getCode(miniJavaParser, parseTree);	// mutate with existing expression
			maxStatement = 9;	// 5 types + 1 empty declaration + 1 prepend statement + 1 append statement + 1 statement duplication
		}
		int statement = random.nextInt(maxStatement);
		if(maxNewCodeSegmentSize<=0) {
			statement = 5;		// remove declaration
		}
		switch(statement) {
			case 0:
				if(sourceStatement != null) {
					variant = random.nextInt(2);
				}
				switch(variant) {
					case 0:
						stringBuilder.append("if(");
						stringBuilder.append(generateExpressionBooleanContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						stringBuilder.append(")");
						stringBuilder.append(generateBlockContext(maxNewCodeSegmentSize-stringBuilder.length()));
						if(maxNewCodeSegmentSize>0 && random.nextBoolean()) {
							stringBuilder.append("else");
							stringBuilder.append(generateBlockContext(maxNewCodeSegmentSize-stringBuilder.length()));
						}
						return stringBuilder.toString();
					case 1:
						stringBuilder.append("if(");
						stringBuilder.append(generateExpressionBooleanContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						stringBuilder.append("){");
						stringBuilder.append(sourceStatement);
						stringBuilder.append("}");
						if(maxNewCodeSegmentSize>0 && random.nextBoolean()) {
							stringBuilder.append("else{");
							stringBuilder.append(sourceStatement);
							stringBuilder.append("}");
						}
						return stringBuilder.toString();
				}
			case 1:
				if(sourceStatement != null) {
					variant = random.nextInt(2);
				}
				switch(variant) {
					case 0:
						stringBuilder.append("while(!Thread.currentThread().isInterrupted()&&");
						stringBuilder.append(generateExpressionBooleanContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						stringBuilder.append(")");
						stringBuilder.append(generateBlockContext(maxNewCodeSegmentSize-stringBuilder.length()));
						return stringBuilder.toString();
					case 1:
						stringBuilder.append("while(!Thread.currentThread().isInterrupted()&&");
						stringBuilder.append(generateExpressionBooleanContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						stringBuilder.append("){");
						stringBuilder.append(sourceStatement);
						stringBuilder.append("}");
						return stringBuilder.toString();
				}
			case 2:
				stringBuilder.append(generateTerminalNode(maxNewCodeSegmentSize-stringBuilder.length(), null, null, "LONGARRAYNAME"));
				stringBuilder.append(".set(new Long(");
				stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
				stringBuilder.append(").intValue()%size, new Long(");
				stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
				stringBuilder.append("));");
				return stringBuilder.toString();
			case 3:
				stringBuilder.append(generateTerminalNode(maxNewCodeSegmentSize-stringBuilder.length(), null, null, "LONGNAME"));
				stringBuilder.append("= new Long(");
				stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
				stringBuilder.append(");");
				return stringBuilder.toString();
			case 4:
				stringBuilder.append(generateTerminalNode(maxNewCodeSegmentSize-stringBuilder.length(), null, null, "BOOLEANNAME"));
				stringBuilder.append("= new Boolean(");
				stringBuilder.append(generateExpressionBooleanContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
				stringBuilder.append(");");
				return stringBuilder.toString();
			case 5:
				return " ";
			case 6:
				// allow sourceStatement.length() without effect on maxNewCodeSegmentSize
				return sourceStatement + " " + generateStatementContext(maxNewCodeSegmentSize, null, null);
			case 7:
				// allow sourceStatement.length() without effect on maxNewCodeSegmentSize
				return generateStatementContext(maxNewCodeSegmentSize, null, null) + " " +  sourceStatement;
			case 8:
				return sourceStatement + " " +  sourceStatement;
			default:
				LOGGER.severe("Defective code in generateStatementContext)");
				return null;
		}
	}
	
	/*	:   NUMBER
    |   LONGNAME
	|	LONGARRAYNAME '.' 'size' '(' ')'
    |   longArrayValue
    |   '(' '-' expressionNumeric ')'
    |   '(' expressionNumeric '&' expressionNumeric ')'
    |   '(' expressionNumeric '|' expressionNumeric ')'
    |   '(' expressionNumeric '^' expressionNumeric ')'
    |   '(' expressionNumeric '%' expressionNumeric ')'
    |   '(' expressionNumeric '*' expressionNumeric ')'
    |   '(' expressionNumeric '/' expressionNumeric ')'
    |   '(' expressionNumeric '+' expressionNumeric ')'
    |   '(' expressionNumeric '-' expressionNumeric ')'
    |   'Util' '.' 'f' '(' NUMBER ',' expressionNumeric ')'
    |   'Util' '.' 'f' '(' NUMBER ',' expressionNumeric ',' expressionNumeric ')'
    |   'Util' '.' 'f' '(' NUMBER ',' expressionNumeric ',' expressionNumeric ',' expressionNumeric ')'
    */
	public static String generateExpressionNumericContext(int maxNewCodeSegmentSize, MiniJavaParser miniJavaParser, ParseTree parseTree) {
		StringBuilder stringBuilder = new StringBuilder();
		final int maxExpression = 16;	// 16 types
		int variant = 0;
		String sourceExpression = null;
		int expression = random.nextInt(maxExpression);
		maxNewCodeSegmentSize /= 2;	// reduce expression size by half, as rules grow exponentially
		if(maxNewCodeSegmentSize<0) {
			expression = random.nextInt(3);	// limit to first 3 types
		}
		if(parseTree != null && random.nextBoolean()) {
			sourceExpression = getCode(miniJavaParser, parseTree);	// mutate with existing expression
		}
		switch(expression) {
			case 0:
				stringBuilder.append(generateTerminalNode(maxNewCodeSegmentSize-stringBuilder.length(), null, null, "NUMBER"));
				return stringBuilder.toString();
			case 1:
				stringBuilder.append(generateTerminalNode(maxNewCodeSegmentSize-stringBuilder.length(), null, null, "LONGNAME"));
				return stringBuilder.toString();
			case 2:
				stringBuilder.append(generateTerminalNode(maxNewCodeSegmentSize-stringBuilder.length(), null, null, "LONGARRAYNAME"));
				stringBuilder.append(".size()");
				return stringBuilder.toString();
			case 3:
				stringBuilder.append(generateLongArrayValueContext(maxNewCodeSegmentSize));
				return stringBuilder.toString();
			case 4:
				stringBuilder.append("(-");
				stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 5:
				stringBuilder.append("(");
				if(sourceExpression != null) {
					stringBuilder.append(sourceExpression);
				} else {
					stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
				}
				stringBuilder.append("&");
				stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 6:
				stringBuilder.append("(");
				if(sourceExpression != null) {
					stringBuilder.append(sourceExpression);
				} else {
					stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
				}
				stringBuilder.append("|");
				stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 7:
				stringBuilder.append("(");
				if(sourceExpression != null) {
					variant = random.nextInt(4);
				}
				switch(variant) {
					case 0:
						stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						stringBuilder.append("^");
						stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						break;
					case 1:
						stringBuilder.append(sourceExpression);
						stringBuilder.append("^");
						stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						break;
					case 2:
						stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						stringBuilder.append("^");
						stringBuilder.append(sourceExpression);
						break;
					case 3:
						stringBuilder.append(sourceExpression);
						stringBuilder.append("^");
						stringBuilder.append("2");
						break;
				}
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 8:
				stringBuilder.append("(");
				if(sourceExpression != null) {
					variant = random.nextInt(4);
				}
				switch(variant) {
					case 0:
						stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						stringBuilder.append("%");
						stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						break;
					case 1:
						stringBuilder.append(sourceExpression);
						stringBuilder.append("%");
						stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						break;
					case 2:
						stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						stringBuilder.append("%");
						stringBuilder.append(sourceExpression);
						break;
					case 3:
						stringBuilder.append(sourceExpression);
						stringBuilder.append("%");
						stringBuilder.append("2");
						break;
				}
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 9:
				stringBuilder.append("(");
				if(sourceExpression != null) {
					variant = random.nextInt(4);
				}
				switch(variant) {
					case 0:
						stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						stringBuilder.append("*");
						stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						break;
					case 1:
						stringBuilder.append(sourceExpression);
						stringBuilder.append("*");
						stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						break;
					case 2:
						stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						stringBuilder.append("*");
						stringBuilder.append(sourceExpression);
						break;
					case 3:
						stringBuilder.append(sourceExpression);
						stringBuilder.append("*");
						stringBuilder.append("2");
						break;
				}
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 10:
				stringBuilder.append("(");
				if(sourceExpression != null) {
					variant = random.nextInt(4);
				}
				switch(variant) {
					case 0:
						stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						stringBuilder.append("/");
						stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						break;
					case 1:
						stringBuilder.append(sourceExpression);
						stringBuilder.append("/");
						stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						break;
					case 2:
						stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						stringBuilder.append("/");
						stringBuilder.append(sourceExpression);
						break;
					case 3:
						stringBuilder.append(sourceExpression);
						stringBuilder.append("/");
						stringBuilder.append("2");
						break;
				}
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 11:
				stringBuilder.append("(");
				if(sourceExpression != null) {
					variant = random.nextInt(4);
				}
				switch(variant) {
					case 0:
						stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						stringBuilder.append("+");
						stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						break;
					case 1:
						stringBuilder.append(sourceExpression);
						stringBuilder.append("+");
						stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						break;
					case 2:
						stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						stringBuilder.append("+");
						stringBuilder.append(sourceExpression);
						break;
					case 3:
						stringBuilder.append(sourceExpression);
						stringBuilder.append("+");
						stringBuilder.append("1");
						break;
				}
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 12:
				stringBuilder.append("(");
				if(sourceExpression != null) {
					variant = random.nextInt(4);
				}
				switch(variant) {
					case 0:
						stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						stringBuilder.append("-");
						stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						break;
					case 1:
						stringBuilder.append(sourceExpression);
						stringBuilder.append("-");
						stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						break;
					case 2:
						stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						stringBuilder.append("-");
						stringBuilder.append(sourceExpression);
						break;
					case 3:
						stringBuilder.append(sourceExpression);
						stringBuilder.append("-");
						stringBuilder.append("1");
						break;
				}
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 13:
				stringBuilder.append("Util.f(");
				stringBuilder.append(generateTerminalNode(maxNewCodeSegmentSize-stringBuilder.length(), null, null, "NUMBER"));
				stringBuilder.append(",");
				stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 14:
				stringBuilder.append("Util.f(");
				stringBuilder.append(generateTerminalNode(maxNewCodeSegmentSize-stringBuilder.length(), null, null, "NUMBER"));
				stringBuilder.append(",");
				stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
				stringBuilder.append(",");
				stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 15:
				stringBuilder.append("Util.f(");
				stringBuilder.append(generateTerminalNode(maxNewCodeSegmentSize-stringBuilder.length(), null, null, "NUMBER"));
				stringBuilder.append(",");
				stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
				stringBuilder.append(",");
				stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
				stringBuilder.append(",");
				stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
				stringBuilder.append(")");
				return stringBuilder.toString();
			default:
				LOGGER.severe("Defective code in generateExpressionNumericContext)");
				return null;
		}
	}
	
	/*    :   BOOLEAN
    |   BOOLEANNAME
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
	public static String generateExpressionBooleanContext(int maxNewCodeSegmentSize, MiniJavaParser miniJavaParser, ParseTree parseTree) {
		StringBuilder stringBuilder = new StringBuilder();
		final int maxExpression = 12;	// 12 types
		int variant = 0;
		String sourceExpression = null;
		int expression = random.nextInt(maxExpression);
		maxNewCodeSegmentSize /= 2;	// reduce expression size by half, as rules grow exponentially
		if(maxNewCodeSegmentSize<0) {
			expression = random.nextInt(3);	// limit to first 3 types
		}
		if(parseTree != null && random.nextBoolean()) {
			sourceExpression = getCode(miniJavaParser, parseTree);	// mutate with existing expression
		}
		switch(expression) {
			case 0:
				stringBuilder.append(generateTerminalNode(maxNewCodeSegmentSize-stringBuilder.length(), null, null, "BOOLEAN"));
				return stringBuilder.toString();
			case 1:
				stringBuilder.append(generateTerminalNode(maxNewCodeSegmentSize-stringBuilder.length(), null, null, "BOOLEANNAME"));
				return stringBuilder.toString();
			case 2:
				stringBuilder.append("(!");
				stringBuilder.append(generateExpressionBooleanContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 3:
				stringBuilder.append("(");
				stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
				stringBuilder.append("<");
				stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 4:
				stringBuilder.append("(");
				stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
				stringBuilder.append("<=");
				stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 5:
				stringBuilder.append("(");
				stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
				stringBuilder.append("==");
				stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 6:
				stringBuilder.append("(");
				stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
				stringBuilder.append("!=");
				stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 7:
				stringBuilder.append("(");
				if(sourceExpression != null) {
					variant = random.nextInt(2);
				}
				switch(variant) {
					case 0:
						stringBuilder.append(generateExpressionBooleanContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						stringBuilder.append("==");
						stringBuilder.append(generateExpressionBooleanContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						break;
					case 1:
						stringBuilder.append(sourceExpression);
						stringBuilder.append("==");
						stringBuilder.append(generateExpressionBooleanContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						break;
				}
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 8:
				stringBuilder.append("(");
				if(sourceExpression != null) {
					variant = random.nextInt(2);
				}
				switch(variant) {
					case 0:
						stringBuilder.append(generateExpressionBooleanContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						stringBuilder.append("!=");
						stringBuilder.append(generateExpressionBooleanContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						break;
					case 1:
						stringBuilder.append(sourceExpression);
						stringBuilder.append("!=");
						stringBuilder.append(generateExpressionBooleanContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						break;
				}
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 9:
				stringBuilder.append("(");
				if(sourceExpression != null) {
					variant = random.nextInt(2);
				}
				switch(variant) {
					case 0:
						stringBuilder.append(generateExpressionBooleanContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						stringBuilder.append("&&");
						stringBuilder.append(generateExpressionBooleanContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						break;
					case 1:
						stringBuilder.append(sourceExpression);
						stringBuilder.append("&&");
						stringBuilder.append(generateExpressionBooleanContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						break;
				}
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 10:
				stringBuilder.append("(");
				if(sourceExpression != null) {
					variant = random.nextInt(2);
				}
				switch(variant) {
					case 0:
						stringBuilder.append(generateExpressionBooleanContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						stringBuilder.append("||");
						stringBuilder.append(generateExpressionBooleanContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						break;
					case 1:
						stringBuilder.append(sourceExpression);
						stringBuilder.append("||");
						stringBuilder.append(generateExpressionBooleanContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						break;
				}
				stringBuilder.append(")");
				return stringBuilder.toString();
			case 11:
				stringBuilder.append("(");
				if(sourceExpression != null) {
					variant = random.nextInt(2);
				}
				switch(variant) {
					case 0:
						stringBuilder.append(generateExpressionBooleanContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						stringBuilder.append("^");
						stringBuilder.append(generateExpressionBooleanContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						break;
					case 1:
						stringBuilder.append(sourceExpression);
						stringBuilder.append("^");
						stringBuilder.append(generateExpressionBooleanContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
						break;
				}
				stringBuilder.append(")");
				return stringBuilder.toString();
			default:
				LOGGER.severe("Defective code in generateExpressionBooleanContext)");
				return null;
		}
	}
	
	//LONGARRAYNAME '.get(new Long(' expressionNumeric ').intValue()%size)'
	public static String generateLongArrayValueContext(int maxNewCodeSegmentSize) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(generateTerminalNode(maxNewCodeSegmentSize-stringBuilder.length(), null, null, "LONGARRAYNAME"));
		stringBuilder.append(".get(new Long(");
		stringBuilder.append(generateExpressionNumericContext(maxNewCodeSegmentSize-stringBuilder.length(), null, null));
		stringBuilder.append(").intValue()%size)");
		return stringBuilder.toString();
	}
	
	//LONGARRAYNAME	'values' DIGIT DIGIT
	//LONGNAME	'value' DIGIT DIGIT
	//BOOLEANNAME	'condition' DIGIT DIGIT
	//BOOLEAN	'false' | 'true'
	//NUMBER	'0' | DIGITNOZERO DIGIT*
	public static String generateTerminalNode(int maxNewCodeSegmentSize, MiniJavaParser miniJavaParser, ParseTree parseTree, String symbolicName) {
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
				if(parseTree != null && random.nextBoolean()) {
					String stringNumber = getCode(miniJavaParser, parseTree).trim();
					long number = Long.parseLong(stringNumber);
					switch(random.nextInt(4)) {
						case 0:
							number++;
							break;
						case 1:
							if(number>0) {
								number--;
							}
							break;
						case 2:
							number *= 2;
							break;
						case 3:
							number /= 2;
							break;
					}
					return Long.toString(number);
				} else {
					return Integer.toString(random.nextInt(100));
				}
			default:
				return null;
		}
	}
	
	public static String getCode(MiniJavaParser miniJavaParser, ParseTree parseTree) {
		StringBuilder stringBuilder = new StringBuilder();
		for(int index=parseTree.getSourceInterval().a; index<=parseTree.getSourceInterval().b; index++) {
			stringBuilder.append(miniJavaParser.getTokenStream().get(index).getText());
			stringBuilder.append(" ");
		}
		return stringBuilder.toString();
	}
}
