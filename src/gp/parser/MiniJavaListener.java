// Generated from MiniJava.g4 by ANTLR 4.9.2
package gp.parser;
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
	 * Enter a parse tree produced by {@link MiniJavaParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(MiniJavaParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(MiniJavaParser.DeclarationContext ctx);
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
	 * Enter a parse tree produced by {@link MiniJavaParser#booleanDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterBooleanDeclaration(MiniJavaParser.BooleanDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniJavaParser#booleanDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitBooleanDeclaration(MiniJavaParser.BooleanDeclarationContext ctx);
}