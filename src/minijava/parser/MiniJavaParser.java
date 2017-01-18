// Generated from MiniJava.g4 by ANTLR 4.6
package minijava.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MiniJavaParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.6", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, PACKAGENAME=42, LONGARRAYNAME=43, LONGNAME=44, 
		BOOLEANARRAYNAME=45, BOOLEANNAME=46, BOOLEAN=47, NUMBER=48, WS=49;
	public static final int
		RULE_program = 0, RULE_block = 1, RULE_statement = 2, RULE_expressionNumeric = 3, 
		RULE_expressionBoolean = 4, RULE_longArrayDeclaration = 5, RULE_longArrayValue = 6, 
		RULE_longArrayName = 7, RULE_longDeclaration = 8, RULE_longName = 9, RULE_booleanArrayDeclaration = 10, 
		RULE_booleanArrayValue = 11, RULE_booleanArrayName = 12, RULE_booleanDeclaration = 13, 
		RULE_booleanName = 14, RULE_numberValue = 15, RULE_booleanValue = 16, 
		RULE_packageName = 17;
	public static final String[] ruleNames = {
		"program", "block", "statement", "expressionNumeric", "expressionBoolean", 
		"longArrayDeclaration", "longArrayValue", "longArrayName", "longDeclaration", 
		"longName", "booleanArrayDeclaration", "booleanArrayValue", "booleanArrayName", 
		"booleanDeclaration", "booleanName", "numberValue", "booleanValue", "packageName"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'package'", "';'", "'import java.util.ArrayList;'", "'import java.util.Collections;'", 
		"'public class GeneticProgram {'", "'public static ArrayList<Long> compute(ArrayList<Long> values00)'", 
		"'}'", "'{'", "'return values00;'", "'if('", "') {'", "'} else {'", "'while('", 
		"'.set('", "', new Long('", "'));'", "'='", "', new Boolean('", "'('", 
		"')'", "'^'", "'%'", "'*'", "'/'", "'+'", "'-'", "'.'", "'length'", "'<'", 
		"'<='", "'=='", "'!='", "'&&'", "'||'", "'ArrayList<Long>'", "'= new ArrayList<Long>(Collections.nCopies(values00.length,'", 
		"'.get('", "'Long'", "'ArrayList<Boolean>'", "'= new ArrayList<Boolean>(Collections.nCopies(values00.length,'", 
		"'Boolean'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, "PACKAGENAME", "LONGARRAYNAME", "LONGNAME", 
		"BOOLEANARRAYNAME", "BOOLEANNAME", "BOOLEAN", "NUMBER", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "MiniJava.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MiniJavaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public PackageNameContext packageName() {
			return getRuleContext(PackageNameContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode EOF() { return getToken(MiniJavaParser.EOF, 0); }
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitProgram(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(36);
			match(T__0);
			setState(37);
			packageName();
			setState(38);
			match(T__1);
			setState(39);
			match(T__2);
			setState(40);
			match(T__3);
			setState(41);
			match(T__4);
			setState(42);
			match(T__5);
			setState(43);
			block();
			setState(44);
			match(T__6);
			setState(45);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public List<LongArrayDeclarationContext> longArrayDeclaration() {
			return getRuleContexts(LongArrayDeclarationContext.class);
		}
		public LongArrayDeclarationContext longArrayDeclaration(int i) {
			return getRuleContext(LongArrayDeclarationContext.class,i);
		}
		public List<LongDeclarationContext> longDeclaration() {
			return getRuleContexts(LongDeclarationContext.class);
		}
		public LongDeclarationContext longDeclaration(int i) {
			return getRuleContext(LongDeclarationContext.class,i);
		}
		public List<BooleanArrayDeclarationContext> booleanArrayDeclaration() {
			return getRuleContexts(BooleanArrayDeclarationContext.class);
		}
		public BooleanArrayDeclarationContext booleanArrayDeclaration(int i) {
			return getRuleContext(BooleanArrayDeclarationContext.class,i);
		}
		public List<BooleanDeclarationContext> booleanDeclaration() {
			return getRuleContexts(BooleanDeclarationContext.class);
		}
		public BooleanDeclarationContext booleanDeclaration(int i) {
			return getRuleContext(BooleanDeclarationContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitBlock(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			match(T__7);
			setState(51);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__34) {
				{
				{
				setState(48);
				longArrayDeclaration();
				}
				}
				setState(53);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(57);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__37) {
				{
				{
				setState(54);
				longDeclaration();
				}
				}
				setState(59);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(63);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__38) {
				{
				{
				setState(60);
				booleanArrayDeclaration();
				}
				}
				setState(65);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(69);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__40) {
				{
				{
				setState(66);
				booleanDeclaration();
				}
				}
				setState(71);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(75);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__9) | (1L << T__12) | (1L << LONGARRAYNAME) | (1L << LONGNAME) | (1L << BOOLEANARRAYNAME) | (1L << BOOLEANNAME))) != 0)) {
				{
				{
				setState(72);
				statement();
				}
				}
				setState(77);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(78);
			match(T__8);
			setState(79);
			match(T__6);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public ExpressionBooleanContext expressionBoolean() {
			return getRuleContext(ExpressionBooleanContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public LongArrayNameContext longArrayName() {
			return getRuleContext(LongArrayNameContext.class,0);
		}
		public List<ExpressionNumericContext> expressionNumeric() {
			return getRuleContexts(ExpressionNumericContext.class);
		}
		public ExpressionNumericContext expressionNumeric(int i) {
			return getRuleContext(ExpressionNumericContext.class,i);
		}
		public LongNameContext longName() {
			return getRuleContext(LongNameContext.class,0);
		}
		public BooleanArrayNameContext booleanArrayName() {
			return getRuleContext(BooleanArrayNameContext.class,0);
		}
		public BooleanNameContext booleanName() {
			return getRuleContext(BooleanNameContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitStatement(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_statement);
		try {
			setState(119);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__9:
				enterOuterAlt(_localctx, 1);
				{
				setState(81);
				match(T__9);
				setState(82);
				expressionBoolean(0);
				setState(83);
				match(T__10);
				setState(84);
				statement();
				setState(85);
				match(T__11);
				setState(86);
				statement();
				setState(87);
				match(T__6);
				}
				break;
			case T__12:
				enterOuterAlt(_localctx, 2);
				{
				setState(89);
				match(T__12);
				setState(90);
				expressionBoolean(0);
				setState(91);
				match(T__10);
				setState(92);
				statement();
				setState(93);
				match(T__6);
				}
				break;
			case LONGARRAYNAME:
				enterOuterAlt(_localctx, 3);
				{
				setState(95);
				longArrayName();
				setState(96);
				match(T__13);
				setState(97);
				expressionNumeric(0);
				setState(98);
				match(T__14);
				setState(99);
				expressionNumeric(0);
				setState(100);
				match(T__15);
				}
				break;
			case LONGNAME:
				enterOuterAlt(_localctx, 4);
				{
				setState(102);
				longName();
				setState(103);
				match(T__16);
				setState(104);
				expressionNumeric(0);
				setState(105);
				match(T__1);
				}
				break;
			case BOOLEANARRAYNAME:
				enterOuterAlt(_localctx, 5);
				{
				setState(107);
				booleanArrayName();
				setState(108);
				match(T__13);
				setState(109);
				expressionNumeric(0);
				setState(110);
				match(T__17);
				setState(111);
				expressionBoolean(0);
				setState(112);
				match(T__15);
				}
				break;
			case BOOLEANNAME:
				enterOuterAlt(_localctx, 6);
				{
				setState(114);
				booleanName();
				setState(115);
				match(T__16);
				setState(116);
				expressionBoolean(0);
				setState(117);
				match(T__1);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionNumericContext extends ParserRuleContext {
		public List<ExpressionNumericContext> expressionNumeric() {
			return getRuleContexts(ExpressionNumericContext.class);
		}
		public ExpressionNumericContext expressionNumeric(int i) {
			return getRuleContext(ExpressionNumericContext.class,i);
		}
		public LongArrayNameContext longArrayName() {
			return getRuleContext(LongArrayNameContext.class,0);
		}
		public LongArrayValueContext longArrayValue() {
			return getRuleContext(LongArrayValueContext.class,0);
		}
		public LongNameContext longName() {
			return getRuleContext(LongNameContext.class,0);
		}
		public NumberValueContext numberValue() {
			return getRuleContext(NumberValueContext.class,0);
		}
		public ExpressionNumericContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionNumeric; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterExpressionNumeric(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitExpressionNumeric(this);
		}
	}

	public final ExpressionNumericContext expressionNumeric() throws RecognitionException {
		return expressionNumeric(0);
	}

	private ExpressionNumericContext expressionNumeric(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionNumericContext _localctx = new ExpressionNumericContext(_ctx, _parentState);
		ExpressionNumericContext _prevctx = _localctx;
		int _startState = 6;
		enterRecursionRule(_localctx, 6, RULE_expressionNumeric, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(133);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				{
				setState(122);
				match(T__18);
				setState(123);
				expressionNumeric(0);
				setState(124);
				match(T__19);
				}
				break;
			case 2:
				{
				setState(126);
				longArrayName();
				setState(127);
				match(T__26);
				setState(128);
				match(T__27);
				}
				break;
			case 3:
				{
				setState(130);
				longArrayValue();
				}
				break;
			case 4:
				{
				setState(131);
				longName();
				}
				break;
			case 5:
				{
				setState(132);
				numberValue();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(146);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(144);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionNumericContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expressionNumeric);
						setState(135);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(136);
						_la = _input.LA(1);
						if ( !(_la==T__20 || _la==T__21) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(137);
						expressionNumeric(8);
						}
						break;
					case 2:
						{
						_localctx = new ExpressionNumericContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expressionNumeric);
						setState(138);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(139);
						_la = _input.LA(1);
						if ( !(_la==T__22 || _la==T__23) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(140);
						expressionNumeric(7);
						}
						break;
					case 3:
						{
						_localctx = new ExpressionNumericContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expressionNumeric);
						setState(141);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(142);
						_la = _input.LA(1);
						if ( !(_la==T__24 || _la==T__25) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(143);
						expressionNumeric(6);
						}
						break;
					}
					} 
				}
				setState(148);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ExpressionBooleanContext extends ParserRuleContext {
		public List<ExpressionBooleanContext> expressionBoolean() {
			return getRuleContexts(ExpressionBooleanContext.class);
		}
		public ExpressionBooleanContext expressionBoolean(int i) {
			return getRuleContext(ExpressionBooleanContext.class,i);
		}
		public List<ExpressionNumericContext> expressionNumeric() {
			return getRuleContexts(ExpressionNumericContext.class);
		}
		public ExpressionNumericContext expressionNumeric(int i) {
			return getRuleContext(ExpressionNumericContext.class,i);
		}
		public BooleanArrayValueContext booleanArrayValue() {
			return getRuleContext(BooleanArrayValueContext.class,0);
		}
		public BooleanNameContext booleanName() {
			return getRuleContext(BooleanNameContext.class,0);
		}
		public BooleanValueContext booleanValue() {
			return getRuleContext(BooleanValueContext.class,0);
		}
		public ExpressionBooleanContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionBoolean; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterExpressionBoolean(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitExpressionBoolean(this);
		}
	}

	public final ExpressionBooleanContext expressionBoolean() throws RecognitionException {
		return expressionBoolean(0);
	}

	private ExpressionBooleanContext expressionBoolean(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionBooleanContext _localctx = new ExpressionBooleanContext(_ctx, _parentState);
		ExpressionBooleanContext _prevctx = _localctx;
		int _startState = 8;
		enterRecursionRule(_localctx, 8, RULE_expressionBoolean, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(165);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				{
				setState(150);
				match(T__18);
				setState(151);
				expressionBoolean(0);
				setState(152);
				match(T__19);
				}
				break;
			case 2:
				{
				setState(154);
				expressionNumeric(0);
				setState(155);
				_la = _input.LA(1);
				if ( !(_la==T__28 || _la==T__29) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(156);
				expressionNumeric(0);
				}
				break;
			case 3:
				{
				setState(158);
				expressionNumeric(0);
				setState(159);
				_la = _input.LA(1);
				if ( !(_la==T__30 || _la==T__31) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(160);
				expressionNumeric(0);
				}
				break;
			case 4:
				{
				setState(162);
				booleanArrayValue();
				}
				break;
			case 5:
				{
				setState(163);
				booleanName();
				}
				break;
			case 6:
				{
				setState(164);
				booleanValue();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(178);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(176);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionBooleanContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expressionBoolean);
						setState(167);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(168);
						_la = _input.LA(1);
						if ( !(_la==T__30 || _la==T__31) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(169);
						expressionBoolean(7);
						}
						break;
					case 2:
						{
						_localctx = new ExpressionBooleanContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expressionBoolean);
						setState(170);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(171);
						match(T__32);
						setState(172);
						expressionBoolean(6);
						}
						break;
					case 3:
						{
						_localctx = new ExpressionBooleanContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expressionBoolean);
						setState(173);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(174);
						match(T__33);
						setState(175);
						expressionBoolean(5);
						}
						break;
					}
					} 
				}
				setState(180);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class LongArrayDeclarationContext extends ParserRuleContext {
		public LongArrayNameContext longArrayName() {
			return getRuleContext(LongArrayNameContext.class,0);
		}
		public ExpressionNumericContext expressionNumeric() {
			return getRuleContext(ExpressionNumericContext.class,0);
		}
		public LongArrayDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_longArrayDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterLongArrayDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitLongArrayDeclaration(this);
		}
	}

	public final LongArrayDeclarationContext longArrayDeclaration() throws RecognitionException {
		LongArrayDeclarationContext _localctx = new LongArrayDeclarationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_longArrayDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(181);
			match(T__34);
			setState(182);
			longArrayName();
			setState(183);
			match(T__35);
			setState(184);
			expressionNumeric(0);
			setState(185);
			match(T__15);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LongArrayValueContext extends ParserRuleContext {
		public LongArrayNameContext longArrayName() {
			return getRuleContext(LongArrayNameContext.class,0);
		}
		public ExpressionNumericContext expressionNumeric() {
			return getRuleContext(ExpressionNumericContext.class,0);
		}
		public LongArrayValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_longArrayValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterLongArrayValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitLongArrayValue(this);
		}
	}

	public final LongArrayValueContext longArrayValue() throws RecognitionException {
		LongArrayValueContext _localctx = new LongArrayValueContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_longArrayValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(187);
			longArrayName();
			setState(188);
			match(T__36);
			setState(189);
			expressionNumeric(0);
			setState(190);
			match(T__19);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LongArrayNameContext extends ParserRuleContext {
		public TerminalNode LONGARRAYNAME() { return getToken(MiniJavaParser.LONGARRAYNAME, 0); }
		public LongArrayNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_longArrayName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterLongArrayName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitLongArrayName(this);
		}
	}

	public final LongArrayNameContext longArrayName() throws RecognitionException {
		LongArrayNameContext _localctx = new LongArrayNameContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_longArrayName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(192);
			match(LONGARRAYNAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LongDeclarationContext extends ParserRuleContext {
		public LongNameContext longName() {
			return getRuleContext(LongNameContext.class,0);
		}
		public LongDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_longDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterLongDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitLongDeclaration(this);
		}
	}

	public final LongDeclarationContext longDeclaration() throws RecognitionException {
		LongDeclarationContext _localctx = new LongDeclarationContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_longDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(194);
			match(T__37);
			setState(195);
			longName();
			setState(196);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LongNameContext extends ParserRuleContext {
		public TerminalNode LONGNAME() { return getToken(MiniJavaParser.LONGNAME, 0); }
		public LongNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_longName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterLongName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitLongName(this);
		}
	}

	public final LongNameContext longName() throws RecognitionException {
		LongNameContext _localctx = new LongNameContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_longName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
			match(LONGNAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BooleanArrayDeclarationContext extends ParserRuleContext {
		public BooleanArrayNameContext booleanArrayName() {
			return getRuleContext(BooleanArrayNameContext.class,0);
		}
		public ExpressionBooleanContext expressionBoolean() {
			return getRuleContext(ExpressionBooleanContext.class,0);
		}
		public BooleanArrayDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanArrayDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterBooleanArrayDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitBooleanArrayDeclaration(this);
		}
	}

	public final BooleanArrayDeclarationContext booleanArrayDeclaration() throws RecognitionException {
		BooleanArrayDeclarationContext _localctx = new BooleanArrayDeclarationContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_booleanArrayDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(200);
			match(T__38);
			setState(201);
			booleanArrayName();
			setState(202);
			match(T__39);
			setState(203);
			expressionBoolean(0);
			setState(204);
			match(T__15);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BooleanArrayValueContext extends ParserRuleContext {
		public BooleanArrayNameContext booleanArrayName() {
			return getRuleContext(BooleanArrayNameContext.class,0);
		}
		public ExpressionNumericContext expressionNumeric() {
			return getRuleContext(ExpressionNumericContext.class,0);
		}
		public BooleanArrayValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanArrayValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterBooleanArrayValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitBooleanArrayValue(this);
		}
	}

	public final BooleanArrayValueContext booleanArrayValue() throws RecognitionException {
		BooleanArrayValueContext _localctx = new BooleanArrayValueContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_booleanArrayValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			booleanArrayName();
			setState(207);
			match(T__36);
			setState(208);
			expressionNumeric(0);
			setState(209);
			match(T__19);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BooleanArrayNameContext extends ParserRuleContext {
		public TerminalNode BOOLEANARRAYNAME() { return getToken(MiniJavaParser.BOOLEANARRAYNAME, 0); }
		public BooleanArrayNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanArrayName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterBooleanArrayName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitBooleanArrayName(this);
		}
	}

	public final BooleanArrayNameContext booleanArrayName() throws RecognitionException {
		BooleanArrayNameContext _localctx = new BooleanArrayNameContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_booleanArrayName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(211);
			match(BOOLEANARRAYNAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BooleanDeclarationContext extends ParserRuleContext {
		public BooleanNameContext booleanName() {
			return getRuleContext(BooleanNameContext.class,0);
		}
		public BooleanDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterBooleanDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitBooleanDeclaration(this);
		}
	}

	public final BooleanDeclarationContext booleanDeclaration() throws RecognitionException {
		BooleanDeclarationContext _localctx = new BooleanDeclarationContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_booleanDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(213);
			match(T__40);
			setState(214);
			booleanName();
			setState(215);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BooleanNameContext extends ParserRuleContext {
		public TerminalNode BOOLEANNAME() { return getToken(MiniJavaParser.BOOLEANNAME, 0); }
		public BooleanNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterBooleanName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitBooleanName(this);
		}
	}

	public final BooleanNameContext booleanName() throws RecognitionException {
		BooleanNameContext _localctx = new BooleanNameContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_booleanName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(217);
			match(BOOLEANNAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumberValueContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(MiniJavaParser.NUMBER, 0); }
		public NumberValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numberValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterNumberValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitNumberValue(this);
		}
	}

	public final NumberValueContext numberValue() throws RecognitionException {
		NumberValueContext _localctx = new NumberValueContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_numberValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(219);
			match(NUMBER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BooleanValueContext extends ParserRuleContext {
		public TerminalNode BOOLEAN() { return getToken(MiniJavaParser.BOOLEAN, 0); }
		public BooleanValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterBooleanValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitBooleanValue(this);
		}
	}

	public final BooleanValueContext booleanValue() throws RecognitionException {
		BooleanValueContext _localctx = new BooleanValueContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_booleanValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(221);
			match(BOOLEAN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PackageNameContext extends ParserRuleContext {
		public TerminalNode PACKAGENAME() { return getToken(MiniJavaParser.PACKAGENAME, 0); }
		public PackageNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_packageName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterPackageName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitPackageName(this);
		}
	}

	public final PackageNameContext packageName() throws RecognitionException {
		PackageNameContext _localctx = new PackageNameContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_packageName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(223);
			match(PACKAGENAME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 3:
			return expressionNumeric_sempred((ExpressionNumericContext)_localctx, predIndex);
		case 4:
			return expressionBoolean_sempred((ExpressionBooleanContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expressionNumeric_sempred(ExpressionNumericContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 7);
		case 1:
			return precpred(_ctx, 6);
		case 2:
			return precpred(_ctx, 5);
		}
		return true;
	}
	private boolean expressionBoolean_sempred(ExpressionBooleanContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return precpred(_ctx, 6);
		case 4:
			return precpred(_ctx, 5);
		case 5:
			return precpred(_ctx, 4);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\63\u00e4\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\7\3\64"+
		"\n\3\f\3\16\3\67\13\3\3\3\7\3:\n\3\f\3\16\3=\13\3\3\3\7\3@\n\3\f\3\16"+
		"\3C\13\3\3\3\7\3F\n\3\f\3\16\3I\13\3\3\3\7\3L\n\3\f\3\16\3O\13\3\3\3\3"+
		"\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\5\4z\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\5\5\u0088\n\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\7\5\u0093\n\5"+
		"\f\5\16\5\u0096\13\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\5\6\u00a8\n\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\7\6\u00b3"+
		"\n\6\f\6\16\6\u00b6\13\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3"+
		"\t\3\t\3\n\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3"+
		"\r\3\r\3\16\3\16\3\17\3\17\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23"+
		"\3\23\3\23\2\4\b\n\24\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$\2\7\3"+
		"\2\27\30\3\2\31\32\3\2\33\34\3\2\37 \3\2!\"\u00ea\2&\3\2\2\2\4\61\3\2"+
		"\2\2\6y\3\2\2\2\b\u0087\3\2\2\2\n\u00a7\3\2\2\2\f\u00b7\3\2\2\2\16\u00bd"+
		"\3\2\2\2\20\u00c2\3\2\2\2\22\u00c4\3\2\2\2\24\u00c8\3\2\2\2\26\u00ca\3"+
		"\2\2\2\30\u00d0\3\2\2\2\32\u00d5\3\2\2\2\34\u00d7\3\2\2\2\36\u00db\3\2"+
		"\2\2 \u00dd\3\2\2\2\"\u00df\3\2\2\2$\u00e1\3\2\2\2&\'\7\3\2\2\'(\5$\23"+
		"\2()\7\4\2\2)*\7\5\2\2*+\7\6\2\2+,\7\7\2\2,-\7\b\2\2-.\5\4\3\2./\7\t\2"+
		"\2/\60\7\2\2\3\60\3\3\2\2\2\61\65\7\n\2\2\62\64\5\f\7\2\63\62\3\2\2\2"+
		"\64\67\3\2\2\2\65\63\3\2\2\2\65\66\3\2\2\2\66;\3\2\2\2\67\65\3\2\2\28"+
		":\5\22\n\298\3\2\2\2:=\3\2\2\2;9\3\2\2\2;<\3\2\2\2<A\3\2\2\2=;\3\2\2\2"+
		">@\5\26\f\2?>\3\2\2\2@C\3\2\2\2A?\3\2\2\2AB\3\2\2\2BG\3\2\2\2CA\3\2\2"+
		"\2DF\5\34\17\2ED\3\2\2\2FI\3\2\2\2GE\3\2\2\2GH\3\2\2\2HM\3\2\2\2IG\3\2"+
		"\2\2JL\5\6\4\2KJ\3\2\2\2LO\3\2\2\2MK\3\2\2\2MN\3\2\2\2NP\3\2\2\2OM\3\2"+
		"\2\2PQ\7\13\2\2QR\7\t\2\2R\5\3\2\2\2ST\7\f\2\2TU\5\n\6\2UV\7\r\2\2VW\5"+
		"\6\4\2WX\7\16\2\2XY\5\6\4\2YZ\7\t\2\2Zz\3\2\2\2[\\\7\17\2\2\\]\5\n\6\2"+
		"]^\7\r\2\2^_\5\6\4\2_`\7\t\2\2`z\3\2\2\2ab\5\20\t\2bc\7\20\2\2cd\5\b\5"+
		"\2de\7\21\2\2ef\5\b\5\2fg\7\22\2\2gz\3\2\2\2hi\5\24\13\2ij\7\23\2\2jk"+
		"\5\b\5\2kl\7\4\2\2lz\3\2\2\2mn\5\32\16\2no\7\20\2\2op\5\b\5\2pq\7\24\2"+
		"\2qr\5\n\6\2rs\7\22\2\2sz\3\2\2\2tu\5\36\20\2uv\7\23\2\2vw\5\n\6\2wx\7"+
		"\4\2\2xz\3\2\2\2yS\3\2\2\2y[\3\2\2\2ya\3\2\2\2yh\3\2\2\2ym\3\2\2\2yt\3"+
		"\2\2\2z\7\3\2\2\2{|\b\5\1\2|}\7\25\2\2}~\5\b\5\2~\177\7\26\2\2\177\u0088"+
		"\3\2\2\2\u0080\u0081\5\20\t\2\u0081\u0082\7\35\2\2\u0082\u0083\7\36\2"+
		"\2\u0083\u0088\3\2\2\2\u0084\u0088\5\16\b\2\u0085\u0088\5\24\13\2\u0086"+
		"\u0088\5 \21\2\u0087{\3\2\2\2\u0087\u0080\3\2\2\2\u0087\u0084\3\2\2\2"+
		"\u0087\u0085\3\2\2\2\u0087\u0086\3\2\2\2\u0088\u0094\3\2\2\2\u0089\u008a"+
		"\f\t\2\2\u008a\u008b\t\2\2\2\u008b\u0093\5\b\5\n\u008c\u008d\f\b\2\2\u008d"+
		"\u008e\t\3\2\2\u008e\u0093\5\b\5\t\u008f\u0090\f\7\2\2\u0090\u0091\t\4"+
		"\2\2\u0091\u0093\5\b\5\b\u0092\u0089\3\2\2\2\u0092\u008c\3\2\2\2\u0092"+
		"\u008f\3\2\2\2\u0093\u0096\3\2\2\2\u0094\u0092\3\2\2\2\u0094\u0095\3\2"+
		"\2\2\u0095\t\3\2\2\2\u0096\u0094\3\2\2\2\u0097\u0098\b\6\1\2\u0098\u0099"+
		"\7\25\2\2\u0099\u009a\5\n\6\2\u009a\u009b\7\26\2\2\u009b\u00a8\3\2\2\2"+
		"\u009c\u009d\5\b\5\2\u009d\u009e\t\5\2\2\u009e\u009f\5\b\5\2\u009f\u00a8"+
		"\3\2\2\2\u00a0\u00a1\5\b\5\2\u00a1\u00a2\t\6\2\2\u00a2\u00a3\5\b\5\2\u00a3"+
		"\u00a8\3\2\2\2\u00a4\u00a8\5\30\r\2\u00a5\u00a8\5\36\20\2\u00a6\u00a8"+
		"\5\"\22\2\u00a7\u0097\3\2\2\2\u00a7\u009c\3\2\2\2\u00a7\u00a0\3\2\2\2"+
		"\u00a7\u00a4\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a7\u00a6\3\2\2\2\u00a8\u00b4"+
		"\3\2\2\2\u00a9\u00aa\f\b\2\2\u00aa\u00ab\t\6\2\2\u00ab\u00b3\5\n\6\t\u00ac"+
		"\u00ad\f\7\2\2\u00ad\u00ae\7#\2\2\u00ae\u00b3\5\n\6\b\u00af\u00b0\f\6"+
		"\2\2\u00b0\u00b1\7$\2\2\u00b1\u00b3\5\n\6\7\u00b2\u00a9\3\2\2\2\u00b2"+
		"\u00ac\3\2\2\2\u00b2\u00af\3\2\2\2\u00b3\u00b6\3\2\2\2\u00b4\u00b2\3\2"+
		"\2\2\u00b4\u00b5\3\2\2\2\u00b5\13\3\2\2\2\u00b6\u00b4\3\2\2\2\u00b7\u00b8"+
		"\7%\2\2\u00b8\u00b9\5\20\t\2\u00b9\u00ba\7&\2\2\u00ba\u00bb\5\b\5\2\u00bb"+
		"\u00bc\7\22\2\2\u00bc\r\3\2\2\2\u00bd\u00be\5\20\t\2\u00be\u00bf\7\'\2"+
		"\2\u00bf\u00c0\5\b\5\2\u00c0\u00c1\7\26\2\2\u00c1\17\3\2\2\2\u00c2\u00c3"+
		"\7-\2\2\u00c3\21\3\2\2\2\u00c4\u00c5\7(\2\2\u00c5\u00c6\5\24\13\2\u00c6"+
		"\u00c7\7\4\2\2\u00c7\23\3\2\2\2\u00c8\u00c9\7.\2\2\u00c9\25\3\2\2\2\u00ca"+
		"\u00cb\7)\2\2\u00cb\u00cc\5\32\16\2\u00cc\u00cd\7*\2\2\u00cd\u00ce\5\n"+
		"\6\2\u00ce\u00cf\7\22\2\2\u00cf\27\3\2\2\2\u00d0\u00d1\5\32\16\2\u00d1"+
		"\u00d2\7\'\2\2\u00d2\u00d3\5\b\5\2\u00d3\u00d4\7\26\2\2\u00d4\31\3\2\2"+
		"\2\u00d5\u00d6\7/\2\2\u00d6\33\3\2\2\2\u00d7\u00d8\7+\2\2\u00d8\u00d9"+
		"\5\36\20\2\u00d9\u00da\7\4\2\2\u00da\35\3\2\2\2\u00db\u00dc\7\60\2\2\u00dc"+
		"\37\3\2\2\2\u00dd\u00de\7\62\2\2\u00de!\3\2\2\2\u00df\u00e0\7\61\2\2\u00e0"+
		"#\3\2\2\2\u00e1\u00e2\7,\2\2\u00e2%\3\2\2\2\16\65;AGMy\u0087\u0092\u0094"+
		"\u00a7\u00b2\u00b4";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}