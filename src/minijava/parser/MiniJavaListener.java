// Generated from MiniJava.g4 by ANTLR 4.6
package minijava.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MiniJavaParser}.
 */
public interface MiniJavaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MiniJavaParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MiniJavaParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(MiniJavaParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(MiniJavaParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(MiniJavaParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(MiniJavaParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#expressionNumeric}.
	 * @param ctx the parse tree
	 */
	void enterExpressionNumeric(MiniJavaParser.ExpressionNumericContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#expressionNumeric}.
	 * @param ctx the parse tree
	 */
	void exitExpressionNumeric(MiniJavaParser.ExpressionNumericContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#expressionBoolean}.
	 * @param ctx the parse tree
	 */
	void enterExpressionBoolean(MiniJavaParser.ExpressionBooleanContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#expressionBoolean}.
	 * @param ctx the parse tree
	 */
	void exitExpressionBoolean(MiniJavaParser.ExpressionBooleanContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#longArrayDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterLongArrayDeclaration(MiniJavaParser.LongArrayDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#longArrayDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitLongArrayDeclaration(MiniJavaParser.LongArrayDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#longArrayValue}.
	 * @param ctx the parse tree
	 */
	void enterLongArrayValue(MiniJavaParser.LongArrayValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#longArrayValue}.
	 * @param ctx the parse tree
	 */
	void exitLongArrayValue(MiniJavaParser.LongArrayValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#longArrayName}.
	 * @param ctx the parse tree
	 */
	void enterLongArrayName(MiniJavaParser.LongArrayNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#longArrayName}.
	 * @param ctx the parse tree
	 */
	void exitLongArrayName(MiniJavaParser.LongArrayNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#longDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterLongDeclaration(MiniJavaParser.LongDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#longDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitLongDeclaration(MiniJavaParser.LongDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#longName}.
	 * @param ctx the parse tree
	 */
	void enterLongName(MiniJavaParser.LongNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#longName}.
	 * @param ctx the parse tree
	 */
	void exitLongName(MiniJavaParser.LongNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#booleanArrayDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterBooleanArrayDeclaration(MiniJavaParser.BooleanArrayDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#booleanArrayDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitBooleanArrayDeclaration(MiniJavaParser.BooleanArrayDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#booleanArrayValue}.
	 * @param ctx the parse tree
	 */
	void enterBooleanArrayValue(MiniJavaParser.BooleanArrayValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#booleanArrayValue}.
	 * @param ctx the parse tree
	 */
	void exitBooleanArrayValue(MiniJavaParser.BooleanArrayValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#booleanArrayName}.
	 * @param ctx the parse tree
	 */
	void enterBooleanArrayName(MiniJavaParser.BooleanArrayNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#booleanArrayName}.
	 * @param ctx the parse tree
	 */
	void exitBooleanArrayName(MiniJavaParser.BooleanArrayNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#booleanDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterBooleanDeclaration(MiniJavaParser.BooleanDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#booleanDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitBooleanDeclaration(MiniJavaParser.BooleanDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#booleanName}.
	 * @param ctx the parse tree
	 */
	void enterBooleanName(MiniJavaParser.BooleanNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#booleanName}.
	 * @param ctx the parse tree
	 */
	void exitBooleanName(MiniJavaParser.BooleanNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#numberValue}.
	 * @param ctx the parse tree
	 */
	void enterNumberValue(MiniJavaParser.NumberValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#numberValue}.
	 * @param ctx the parse tree
	 */
	void exitNumberValue(MiniJavaParser.NumberValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#booleanValue}.
	 * @param ctx the parse tree
	 */
	void enterBooleanValue(MiniJavaParser.BooleanValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#booleanValue}.
	 * @param ctx the parse tree
	 */
	void exitBooleanValue(MiniJavaParser.BooleanValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniJavaParser#packageName}.
	 * @param ctx the parse tree
	 */
	void enterPackageName(MiniJavaParser.PackageNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#packageName}.
	 * @param ctx the parse tree
	 */
	void exitPackageName(MiniJavaParser.PackageNameContext ctx);
}