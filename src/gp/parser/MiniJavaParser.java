// Generated from MiniJava.g4 by ANTLR 4.9.2
package gp.parser;
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
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, T__48=49, T__49=50, T__50=51, T__51=52, 
		T__52=53, T__53=54, T__54=55, T__55=56, T__56=57, T__57=58, T__58=59, 
		PACKAGENAME=60, LONGARRAYNAME=61, LONGNAME=62, BOOLEANNAME=63, BOOLEAN=64, 
		NUMBER=65, COMMENT=66, WS=67;
	public static final int
		RULE_program = 0, RULE_block = 1, RULE_declaration = 2, RULE_statement = 3, 
		RULE_expressionNumeric = 4, RULE_expressionBoolean = 5, RULE_longArrayDeclaration = 6, 
		RULE_longArrayValue = 7, RULE_longDeclaration = 8, RULE_booleanDeclaration = 9;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "block", "declaration", "statement", "expressionNumeric", 
			"expressionBoolean", "longArrayDeclaration", "longArrayValue", "longDeclaration", 
			"booleanDeclaration"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'package'", "';'", "'import'", "'java'", "'.'", "'lang'", "'Exception'", 
			"'util'", "'ArrayList'", "'gp'", "'Util'", "'public'", "'class'", "'GeneticProgram'", 
			"'{'", "'static'", "'void'", "'compute'", "'('", "'<'", "'Long'", "'>'", 
			"')'", "'int'", "'size'", "'='", "'try'", "'catch'", "'e'", "'clear'", 
			"'}'", "'if'", "'else'", "'while'", "'!'", "'Thread'", "'currentThread'", 
			"'isInterrupted'", "'&&'", "'set'", "'valueOf'", "'intValue'", "'%'", 
			"','", "'Boolean'", "'-'", "'&'", "'|'", "'^'", "'*'", "'/'", "'+'", 
			"'f'", "'<='", "'=='", "'!='", "'||'", "'new'", "'get'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			"PACKAGENAME", "LONGARRAYNAME", "LONGNAME", "BOOLEANNAME", "BOOLEAN", 
			"NUMBER", "COMMENT", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
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
		public TerminalNode PACKAGENAME() { return getToken(MiniJavaParser.PACKAGENAME, 0); }
		public List<TerminalNode> LONGARRAYNAME() { return getTokens(MiniJavaParser.LONGARRAYNAME); }
		public TerminalNode LONGARRAYNAME(int i) {
			return getToken(MiniJavaParser.LONGARRAYNAME, i);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode EOF() { return getToken(MiniJavaParser.EOF, 0); }
		public List<TerminalNode> COMMENT() { return getTokens(MiniJavaParser.COMMENT); }
		public TerminalNode COMMENT(int i) {
			return getToken(MiniJavaParser.COMMENT, i);
		}
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
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
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(23);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMENT) {
				{
				{
				setState(20);
				match(COMMENT);
				}
				}
				setState(25);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(26);
			match(T__0);
			setState(27);
			match(PACKAGENAME);
			setState(28);
			match(T__1);
			setState(29);
			match(T__2);
			setState(30);
			match(T__3);
			setState(31);
			match(T__4);
			setState(32);
			match(T__5);
			setState(33);
			match(T__4);
			setState(34);
			match(T__6);
			setState(35);
			match(T__1);
			setState(36);
			match(T__2);
			setState(37);
			match(T__3);
			setState(38);
			match(T__4);
			setState(39);
			match(T__7);
			setState(40);
			match(T__4);
			setState(41);
			match(T__8);
			setState(42);
			match(T__1);
			setState(43);
			match(T__2);
			setState(44);
			match(T__9);
			setState(45);
			match(T__4);
			setState(46);
			match(T__10);
			setState(47);
			match(T__1);
			setState(48);
			match(T__11);
			setState(49);
			match(T__12);
			setState(50);
			match(T__13);
			setState(51);
			match(T__14);
			setState(52);
			match(T__11);
			setState(53);
			match(T__15);
			setState(54);
			match(T__16);
			setState(55);
			match(T__17);
			setState(56);
			match(T__18);
			setState(57);
			match(T__8);
			setState(58);
			match(T__19);
			setState(59);
			match(T__20);
			setState(60);
			match(T__21);
			setState(61);
			match(LONGARRAYNAME);
			setState(62);
			match(T__22);
			setState(63);
			match(T__14);
			setState(64);
			match(T__23);
			setState(65);
			match(T__24);
			setState(66);
			match(T__25);
			setState(67);
			match(LONGARRAYNAME);
			setState(68);
			match(T__4);
			setState(69);
			match(T__24);
			setState(70);
			match(T__18);
			setState(71);
			match(T__22);
			setState(72);
			match(T__1);
			setState(76);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__8) | (1L << T__20) | (1L << T__44))) != 0)) {
				{
				{
				setState(73);
				declaration();
				}
				}
				setState(78);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(79);
			match(T__26);
			setState(80);
			block();
			setState(81);
			match(T__27);
			setState(82);
			match(T__18);
			setState(83);
			match(T__6);
			setState(84);
			match(T__28);
			setState(85);
			match(T__22);
			setState(86);
			match(T__14);
			setState(87);
			match(LONGARRAYNAME);
			setState(88);
			match(T__4);
			setState(89);
			match(T__29);
			setState(90);
			match(T__18);
			setState(91);
			match(T__22);
			setState(92);
			match(T__1);
			setState(93);
			match(T__30);
			setState(94);
			match(T__30);
			setState(95);
			match(T__30);
			setState(96);
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
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
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
			setState(98);
			match(T__14);
			setState(102);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__8) | (1L << T__20) | (1L << T__44))) != 0)) {
				{
				{
				setState(99);
				declaration();
				}
				}
				setState(104);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(108);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__31) | (1L << T__33) | (1L << LONGARRAYNAME) | (1L << LONGNAME) | (1L << BOOLEANNAME))) != 0)) {
				{
				{
				setState(105);
				statement();
				}
				}
				setState(110);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(111);
			match(T__30);
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

	public static class DeclarationContext extends ParserRuleContext {
		public LongArrayDeclarationContext longArrayDeclaration() {
			return getRuleContext(LongArrayDeclarationContext.class,0);
		}
		public LongDeclarationContext longDeclaration() {
			return getRuleContext(LongDeclarationContext.class,0);
		}
		public BooleanDeclarationContext booleanDeclaration() {
			return getRuleContext(BooleanDeclarationContext.class,0);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitDeclaration(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_declaration);
		try {
			setState(116);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__8:
				enterOuterAlt(_localctx, 1);
				{
				setState(113);
				longArrayDeclaration();
				}
				break;
			case T__20:
				enterOuterAlt(_localctx, 2);
				{
				setState(114);
				longDeclaration();
				}
				break;
			case T__44:
				enterOuterAlt(_localctx, 3);
				{
				setState(115);
				booleanDeclaration();
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

	public static class StatementContext extends ParserRuleContext {
		public ExpressionBooleanContext expressionBoolean() {
			return getRuleContext(ExpressionBooleanContext.class,0);
		}
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public TerminalNode LONGARRAYNAME() { return getToken(MiniJavaParser.LONGARRAYNAME, 0); }
		public List<ExpressionNumericContext> expressionNumeric() {
			return getRuleContexts(ExpressionNumericContext.class);
		}
		public ExpressionNumericContext expressionNumeric(int i) {
			return getRuleContext(ExpressionNumericContext.class,i);
		}
		public TerminalNode LONGNAME() { return getToken(MiniJavaParser.LONGNAME, 0); }
		public TerminalNode BOOLEANNAME() { return getToken(MiniJavaParser.BOOLEANNAME, 0); }
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
		enterRule(_localctx, 6, RULE_statement);
		int _la;
		try {
			setState(190);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__31:
				enterOuterAlt(_localctx, 1);
				{
				setState(118);
				match(T__31);
				setState(119);
				match(T__18);
				setState(120);
				expressionBoolean();
				setState(121);
				match(T__22);
				setState(122);
				block();
				setState(125);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__32) {
					{
					setState(123);
					match(T__32);
					setState(124);
					block();
					}
				}

				}
				break;
			case T__33:
				enterOuterAlt(_localctx, 2);
				{
				setState(127);
				match(T__33);
				setState(128);
				match(T__18);
				setState(129);
				match(T__34);
				setState(130);
				match(T__35);
				setState(131);
				match(T__4);
				setState(132);
				match(T__36);
				setState(133);
				match(T__18);
				setState(134);
				match(T__22);
				setState(135);
				match(T__4);
				setState(136);
				match(T__37);
				setState(137);
				match(T__18);
				setState(138);
				match(T__22);
				setState(139);
				match(T__38);
				setState(140);
				expressionBoolean();
				setState(141);
				match(T__22);
				setState(142);
				block();
				}
				break;
			case LONGARRAYNAME:
				enterOuterAlt(_localctx, 3);
				{
				setState(144);
				match(LONGARRAYNAME);
				setState(145);
				match(T__4);
				setState(146);
				match(T__39);
				setState(147);
				match(T__18);
				setState(148);
				match(T__20);
				setState(149);
				match(T__4);
				setState(150);
				match(T__40);
				setState(151);
				match(T__18);
				setState(152);
				expressionNumeric();
				setState(153);
				match(T__22);
				setState(154);
				match(T__4);
				setState(155);
				match(T__41);
				setState(156);
				match(T__18);
				setState(157);
				match(T__22);
				setState(158);
				match(T__42);
				setState(159);
				match(T__24);
				setState(160);
				match(T__43);
				setState(161);
				match(T__20);
				setState(162);
				match(T__4);
				setState(163);
				match(T__40);
				setState(164);
				match(T__18);
				setState(165);
				expressionNumeric();
				setState(166);
				match(T__22);
				setState(167);
				match(T__22);
				setState(168);
				match(T__1);
				}
				break;
			case LONGNAME:
				enterOuterAlt(_localctx, 4);
				{
				setState(170);
				match(LONGNAME);
				setState(171);
				match(T__25);
				setState(172);
				match(T__20);
				setState(173);
				match(T__4);
				setState(174);
				match(T__40);
				setState(175);
				match(T__18);
				setState(176);
				expressionNumeric();
				setState(177);
				match(T__22);
				setState(178);
				match(T__1);
				}
				break;
			case BOOLEANNAME:
				enterOuterAlt(_localctx, 5);
				{
				setState(180);
				match(BOOLEANNAME);
				setState(181);
				match(T__25);
				setState(182);
				match(T__44);
				setState(183);
				match(T__4);
				setState(184);
				match(T__40);
				setState(185);
				match(T__18);
				setState(186);
				expressionBoolean();
				setState(187);
				match(T__22);
				setState(188);
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
		public TerminalNode NUMBER() { return getToken(MiniJavaParser.NUMBER, 0); }
		public TerminalNode LONGNAME() { return getToken(MiniJavaParser.LONGNAME, 0); }
		public TerminalNode LONGARRAYNAME() { return getToken(MiniJavaParser.LONGARRAYNAME, 0); }
		public LongArrayValueContext longArrayValue() {
			return getRuleContext(LongArrayValueContext.class,0);
		}
		public List<ExpressionNumericContext> expressionNumeric() {
			return getRuleContexts(ExpressionNumericContext.class);
		}
		public ExpressionNumericContext expressionNumeric(int i) {
			return getRuleContext(ExpressionNumericContext.class,i);
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
		ExpressionNumericContext _localctx = new ExpressionNumericContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_expressionNumeric);
		try {
			setState(286);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(192);
				match(NUMBER);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(193);
				match(LONGNAME);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(194);
				match(LONGARRAYNAME);
				setState(195);
				match(T__4);
				setState(196);
				match(T__24);
				setState(197);
				match(T__18);
				setState(198);
				match(T__22);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(199);
				longArrayValue();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(200);
				match(T__18);
				setState(201);
				match(T__45);
				setState(202);
				expressionNumeric();
				setState(203);
				match(T__22);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(205);
				match(T__18);
				setState(206);
				expressionNumeric();
				setState(207);
				match(T__46);
				setState(208);
				expressionNumeric();
				setState(209);
				match(T__22);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(211);
				match(T__18);
				setState(212);
				expressionNumeric();
				setState(213);
				match(T__47);
				setState(214);
				expressionNumeric();
				setState(215);
				match(T__22);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(217);
				match(T__18);
				setState(218);
				expressionNumeric();
				setState(219);
				match(T__48);
				setState(220);
				expressionNumeric();
				setState(221);
				match(T__22);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(223);
				match(T__18);
				setState(224);
				expressionNumeric();
				setState(225);
				match(T__42);
				setState(226);
				expressionNumeric();
				setState(227);
				match(T__22);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(229);
				match(T__18);
				setState(230);
				expressionNumeric();
				setState(231);
				match(T__49);
				setState(232);
				expressionNumeric();
				setState(233);
				match(T__22);
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(235);
				match(T__18);
				setState(236);
				expressionNumeric();
				setState(237);
				match(T__50);
				setState(238);
				expressionNumeric();
				setState(239);
				match(T__22);
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(241);
				match(T__18);
				setState(242);
				expressionNumeric();
				setState(243);
				match(T__51);
				setState(244);
				expressionNumeric();
				setState(245);
				match(T__22);
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(247);
				match(T__18);
				setState(248);
				expressionNumeric();
				setState(249);
				match(T__45);
				setState(250);
				expressionNumeric();
				setState(251);
				match(T__22);
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(253);
				match(T__10);
				setState(254);
				match(T__4);
				setState(255);
				match(T__52);
				setState(256);
				match(T__18);
				setState(257);
				match(NUMBER);
				setState(258);
				match(T__43);
				setState(259);
				expressionNumeric();
				setState(260);
				match(T__22);
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(262);
				match(T__10);
				setState(263);
				match(T__4);
				setState(264);
				match(T__52);
				setState(265);
				match(T__18);
				setState(266);
				match(NUMBER);
				setState(267);
				match(T__43);
				setState(268);
				expressionNumeric();
				setState(269);
				match(T__43);
				setState(270);
				expressionNumeric();
				setState(271);
				match(T__22);
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(273);
				match(T__10);
				setState(274);
				match(T__4);
				setState(275);
				match(T__52);
				setState(276);
				match(T__18);
				setState(277);
				match(NUMBER);
				setState(278);
				match(T__43);
				setState(279);
				expressionNumeric();
				setState(280);
				match(T__43);
				setState(281);
				expressionNumeric();
				setState(282);
				match(T__43);
				setState(283);
				expressionNumeric();
				setState(284);
				match(T__22);
				}
				break;
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

	public static class ExpressionBooleanContext extends ParserRuleContext {
		public TerminalNode BOOLEAN() { return getToken(MiniJavaParser.BOOLEAN, 0); }
		public TerminalNode BOOLEANNAME() { return getToken(MiniJavaParser.BOOLEANNAME, 0); }
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
		ExpressionBooleanContext _localctx = new ExpressionBooleanContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_expressionBoolean);
		try {
			setState(349);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(288);
				match(BOOLEAN);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(289);
				match(BOOLEANNAME);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(290);
				match(T__18);
				setState(291);
				match(T__34);
				setState(292);
				expressionBoolean();
				setState(293);
				match(T__22);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(295);
				match(T__18);
				setState(296);
				expressionNumeric();
				setState(297);
				match(T__19);
				setState(298);
				expressionNumeric();
				setState(299);
				match(T__22);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(301);
				match(T__18);
				setState(302);
				expressionNumeric();
				setState(303);
				match(T__53);
				setState(304);
				expressionNumeric();
				setState(305);
				match(T__22);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(307);
				match(T__18);
				setState(308);
				expressionNumeric();
				setState(309);
				match(T__54);
				setState(310);
				expressionNumeric();
				setState(311);
				match(T__22);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(313);
				match(T__18);
				setState(314);
				expressionNumeric();
				setState(315);
				match(T__55);
				setState(316);
				expressionNumeric();
				setState(317);
				match(T__22);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(319);
				match(T__18);
				setState(320);
				expressionBoolean();
				setState(321);
				match(T__54);
				setState(322);
				expressionBoolean();
				setState(323);
				match(T__22);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(325);
				match(T__18);
				setState(326);
				expressionBoolean();
				setState(327);
				match(T__55);
				setState(328);
				expressionBoolean();
				setState(329);
				match(T__22);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(331);
				match(T__18);
				setState(332);
				expressionBoolean();
				setState(333);
				match(T__38);
				setState(334);
				expressionBoolean();
				setState(335);
				match(T__22);
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(337);
				match(T__18);
				setState(338);
				expressionBoolean();
				setState(339);
				match(T__56);
				setState(340);
				expressionBoolean();
				setState(341);
				match(T__22);
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(343);
				match(T__18);
				setState(344);
				expressionBoolean();
				setState(345);
				match(T__48);
				setState(346);
				expressionBoolean();
				setState(347);
				match(T__22);
				}
				break;
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

	public static class LongArrayDeclarationContext extends ParserRuleContext {
		public List<TerminalNode> LONGARRAYNAME() { return getTokens(MiniJavaParser.LONGARRAYNAME); }
		public TerminalNode LONGARRAYNAME(int i) {
			return getToken(MiniJavaParser.LONGARRAYNAME, i);
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
		enterRule(_localctx, 12, RULE_longArrayDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(351);
			match(T__8);
			setState(352);
			match(T__19);
			setState(353);
			match(T__20);
			setState(354);
			match(T__21);
			setState(355);
			match(LONGARRAYNAME);
			setState(356);
			match(T__25);
			setState(357);
			match(T__57);
			setState(358);
			match(T__8);
			setState(359);
			match(T__19);
			setState(360);
			match(T__20);
			setState(361);
			match(T__21);
			setState(362);
			match(T__18);
			setState(363);
			match(LONGARRAYNAME);
			setState(364);
			match(T__22);
			setState(365);
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

	public static class LongArrayValueContext extends ParserRuleContext {
		public TerminalNode LONGARRAYNAME() { return getToken(MiniJavaParser.LONGARRAYNAME, 0); }
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
		enterRule(_localctx, 14, RULE_longArrayValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(367);
			match(LONGARRAYNAME);
			setState(368);
			match(T__4);
			setState(369);
			match(T__58);
			setState(370);
			match(T__18);
			setState(371);
			match(T__20);
			setState(372);
			match(T__4);
			setState(373);
			match(T__40);
			setState(374);
			match(T__18);
			setState(375);
			expressionNumeric();
			setState(376);
			match(T__22);
			setState(377);
			match(T__4);
			setState(378);
			match(T__41);
			setState(379);
			match(T__18);
			setState(380);
			match(T__22);
			setState(381);
			match(T__42);
			setState(382);
			match(T__24);
			setState(383);
			match(T__22);
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
		public TerminalNode LONGNAME() { return getToken(MiniJavaParser.LONGNAME, 0); }
		public TerminalNode NUMBER() { return getToken(MiniJavaParser.NUMBER, 0); }
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
			setState(385);
			match(T__20);
			setState(386);
			match(LONGNAME);
			setState(387);
			match(T__25);
			setState(388);
			match(T__20);
			setState(389);
			match(T__4);
			setState(390);
			match(T__40);
			setState(391);
			match(T__18);
			setState(392);
			match(NUMBER);
			setState(393);
			match(T__22);
			setState(394);
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

	public static class BooleanDeclarationContext extends ParserRuleContext {
		public TerminalNode BOOLEANNAME() { return getToken(MiniJavaParser.BOOLEANNAME, 0); }
		public TerminalNode BOOLEAN() { return getToken(MiniJavaParser.BOOLEAN, 0); }
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
		enterRule(_localctx, 18, RULE_booleanDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(396);
			match(T__44);
			setState(397);
			match(BOOLEANNAME);
			setState(398);
			match(T__25);
			setState(399);
			match(T__44);
			setState(400);
			match(T__4);
			setState(401);
			match(T__40);
			setState(402);
			match(T__18);
			setState(403);
			match(BOOLEAN);
			setState(404);
			match(T__22);
			setState(405);
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3E\u019a\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\3\2\7\2\30\n\2\f\2\16\2\33\13\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\7\2M\n\2\f\2\16\2P\13\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\7\3g\n\3\f\3"+
		"\16\3j\13\3\3\3\7\3m\n\3\f\3\16\3p\13\3\3\3\3\3\3\4\3\4\3\4\5\4w\n\4\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5\u0080\n\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\5\5\u00c1\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6\u0121\n\6\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7\u0160\n\7\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\2\2\f\2\4\6\b\n\f\16\20\22\24\2\2\2\u01b4\2\31\3\2\2\2\4d\3\2\2"+
		"\2\6v\3\2\2\2\b\u00c0\3\2\2\2\n\u0120\3\2\2\2\f\u015f\3\2\2\2\16\u0161"+
		"\3\2\2\2\20\u0171\3\2\2\2\22\u0183\3\2\2\2\24\u018e\3\2\2\2\26\30\7D\2"+
		"\2\27\26\3\2\2\2\30\33\3\2\2\2\31\27\3\2\2\2\31\32\3\2\2\2\32\34\3\2\2"+
		"\2\33\31\3\2\2\2\34\35\7\3\2\2\35\36\7>\2\2\36\37\7\4\2\2\37 \7\5\2\2"+
		" !\7\6\2\2!\"\7\7\2\2\"#\7\b\2\2#$\7\7\2\2$%\7\t\2\2%&\7\4\2\2&\'\7\5"+
		"\2\2\'(\7\6\2\2()\7\7\2\2)*\7\n\2\2*+\7\7\2\2+,\7\13\2\2,-\7\4\2\2-.\7"+
		"\5\2\2./\7\f\2\2/\60\7\7\2\2\60\61\7\r\2\2\61\62\7\4\2\2\62\63\7\16\2"+
		"\2\63\64\7\17\2\2\64\65\7\20\2\2\65\66\7\21\2\2\66\67\7\16\2\2\678\7\22"+
		"\2\289\7\23\2\29:\7\24\2\2:;\7\25\2\2;<\7\13\2\2<=\7\26\2\2=>\7\27\2\2"+
		">?\7\30\2\2?@\7?\2\2@A\7\31\2\2AB\7\21\2\2BC\7\32\2\2CD\7\33\2\2DE\7\34"+
		"\2\2EF\7?\2\2FG\7\7\2\2GH\7\33\2\2HI\7\25\2\2IJ\7\31\2\2JN\7\4\2\2KM\5"+
		"\6\4\2LK\3\2\2\2MP\3\2\2\2NL\3\2\2\2NO\3\2\2\2OQ\3\2\2\2PN\3\2\2\2QR\7"+
		"\35\2\2RS\5\4\3\2ST\7\36\2\2TU\7\25\2\2UV\7\t\2\2VW\7\37\2\2WX\7\31\2"+
		"\2XY\7\21\2\2YZ\7?\2\2Z[\7\7\2\2[\\\7 \2\2\\]\7\25\2\2]^\7\31\2\2^_\7"+
		"\4\2\2_`\7!\2\2`a\7!\2\2ab\7!\2\2bc\7\2\2\3c\3\3\2\2\2dh\7\21\2\2eg\5"+
		"\6\4\2fe\3\2\2\2gj\3\2\2\2hf\3\2\2\2hi\3\2\2\2in\3\2\2\2jh\3\2\2\2km\5"+
		"\b\5\2lk\3\2\2\2mp\3\2\2\2nl\3\2\2\2no\3\2\2\2oq\3\2\2\2pn\3\2\2\2qr\7"+
		"!\2\2r\5\3\2\2\2sw\5\16\b\2tw\5\22\n\2uw\5\24\13\2vs\3\2\2\2vt\3\2\2\2"+
		"vu\3\2\2\2w\7\3\2\2\2xy\7\"\2\2yz\7\25\2\2z{\5\f\7\2{|\7\31\2\2|\177\5"+
		"\4\3\2}~\7#\2\2~\u0080\5\4\3\2\177}\3\2\2\2\177\u0080\3\2\2\2\u0080\u00c1"+
		"\3\2\2\2\u0081\u0082\7$\2\2\u0082\u0083\7\25\2\2\u0083\u0084\7%\2\2\u0084"+
		"\u0085\7&\2\2\u0085\u0086\7\7\2\2\u0086\u0087\7\'\2\2\u0087\u0088\7\25"+
		"\2\2\u0088\u0089\7\31\2\2\u0089\u008a\7\7\2\2\u008a\u008b\7(\2\2\u008b"+
		"\u008c\7\25\2\2\u008c\u008d\7\31\2\2\u008d\u008e\7)\2\2\u008e\u008f\5"+
		"\f\7\2\u008f\u0090\7\31\2\2\u0090\u0091\5\4\3\2\u0091\u00c1\3\2\2\2\u0092"+
		"\u0093\7?\2\2\u0093\u0094\7\7\2\2\u0094\u0095\7*\2\2\u0095\u0096\7\25"+
		"\2\2\u0096\u0097\7\27\2\2\u0097\u0098\7\7\2\2\u0098\u0099\7+\2\2\u0099"+
		"\u009a\7\25\2\2\u009a\u009b\5\n\6\2\u009b\u009c\7\31\2\2\u009c\u009d\7"+
		"\7\2\2\u009d\u009e\7,\2\2\u009e\u009f\7\25\2\2\u009f\u00a0\7\31\2\2\u00a0"+
		"\u00a1\7-\2\2\u00a1\u00a2\7\33\2\2\u00a2\u00a3\7.\2\2\u00a3\u00a4\7\27"+
		"\2\2\u00a4\u00a5\7\7\2\2\u00a5\u00a6\7+\2\2\u00a6\u00a7\7\25\2\2\u00a7"+
		"\u00a8\5\n\6\2\u00a8\u00a9\7\31\2\2\u00a9\u00aa\7\31\2\2\u00aa\u00ab\7"+
		"\4\2\2\u00ab\u00c1\3\2\2\2\u00ac\u00ad\7@\2\2\u00ad\u00ae\7\34\2\2\u00ae"+
		"\u00af\7\27\2\2\u00af\u00b0\7\7\2\2\u00b0\u00b1\7+\2\2\u00b1\u00b2\7\25"+
		"\2\2\u00b2\u00b3\5\n\6\2\u00b3\u00b4\7\31\2\2\u00b4\u00b5\7\4\2\2\u00b5"+
		"\u00c1\3\2\2\2\u00b6\u00b7\7A\2\2\u00b7\u00b8\7\34\2\2\u00b8\u00b9\7/"+
		"\2\2\u00b9\u00ba\7\7\2\2\u00ba\u00bb\7+\2\2\u00bb\u00bc\7\25\2\2\u00bc"+
		"\u00bd\5\f\7\2\u00bd\u00be\7\31\2\2\u00be\u00bf\7\4\2\2\u00bf\u00c1\3"+
		"\2\2\2\u00c0x\3\2\2\2\u00c0\u0081\3\2\2\2\u00c0\u0092\3\2\2\2\u00c0\u00ac"+
		"\3\2\2\2\u00c0\u00b6\3\2\2\2\u00c1\t\3\2\2\2\u00c2\u0121\7C\2\2\u00c3"+
		"\u0121\7@\2\2\u00c4\u00c5\7?\2\2\u00c5\u00c6\7\7\2\2\u00c6\u00c7\7\33"+
		"\2\2\u00c7\u00c8\7\25\2\2\u00c8\u0121\7\31\2\2\u00c9\u0121\5\20\t\2\u00ca"+
		"\u00cb\7\25\2\2\u00cb\u00cc\7\60\2\2\u00cc\u00cd\5\n\6\2\u00cd\u00ce\7"+
		"\31\2\2\u00ce\u0121\3\2\2\2\u00cf\u00d0\7\25\2\2\u00d0\u00d1\5\n\6\2\u00d1"+
		"\u00d2\7\61\2\2\u00d2\u00d3\5\n\6\2\u00d3\u00d4\7\31\2\2\u00d4\u0121\3"+
		"\2\2\2\u00d5\u00d6\7\25\2\2\u00d6\u00d7\5\n\6\2\u00d7\u00d8\7\62\2\2\u00d8"+
		"\u00d9\5\n\6\2\u00d9\u00da\7\31\2\2\u00da\u0121\3\2\2\2\u00db\u00dc\7"+
		"\25\2\2\u00dc\u00dd\5\n\6\2\u00dd\u00de\7\63\2\2\u00de\u00df\5\n\6\2\u00df"+
		"\u00e0\7\31\2\2\u00e0\u0121\3\2\2\2\u00e1\u00e2\7\25\2\2\u00e2\u00e3\5"+
		"\n\6\2\u00e3\u00e4\7-\2\2\u00e4\u00e5\5\n\6\2\u00e5\u00e6\7\31\2\2\u00e6"+
		"\u0121\3\2\2\2\u00e7\u00e8\7\25\2\2\u00e8\u00e9\5\n\6\2\u00e9\u00ea\7"+
		"\64\2\2\u00ea\u00eb\5\n\6\2\u00eb\u00ec\7\31\2\2\u00ec\u0121\3\2\2\2\u00ed"+
		"\u00ee\7\25\2\2\u00ee\u00ef\5\n\6\2\u00ef\u00f0\7\65\2\2\u00f0\u00f1\5"+
		"\n\6\2\u00f1\u00f2\7\31\2\2\u00f2\u0121\3\2\2\2\u00f3\u00f4\7\25\2\2\u00f4"+
		"\u00f5\5\n\6\2\u00f5\u00f6\7\66\2\2\u00f6\u00f7\5\n\6\2\u00f7\u00f8\7"+
		"\31\2\2\u00f8\u0121\3\2\2\2\u00f9\u00fa\7\25\2\2\u00fa\u00fb\5\n\6\2\u00fb"+
		"\u00fc\7\60\2\2\u00fc\u00fd\5\n\6\2\u00fd\u00fe\7\31\2\2\u00fe\u0121\3"+
		"\2\2\2\u00ff\u0100\7\r\2\2\u0100\u0101\7\7\2\2\u0101\u0102\7\67\2\2\u0102"+
		"\u0103\7\25\2\2\u0103\u0104\7C\2\2\u0104\u0105\7.\2\2\u0105\u0106\5\n"+
		"\6\2\u0106\u0107\7\31\2\2\u0107\u0121\3\2\2\2\u0108\u0109\7\r\2\2\u0109"+
		"\u010a\7\7\2\2\u010a\u010b\7\67\2\2\u010b\u010c\7\25\2\2\u010c\u010d\7"+
		"C\2\2\u010d\u010e\7.\2\2\u010e\u010f\5\n\6\2\u010f\u0110\7.\2\2\u0110"+
		"\u0111\5\n\6\2\u0111\u0112\7\31\2\2\u0112\u0121\3\2\2\2\u0113\u0114\7"+
		"\r\2\2\u0114\u0115\7\7\2\2\u0115\u0116\7\67\2\2\u0116\u0117\7\25\2\2\u0117"+
		"\u0118\7C\2\2\u0118\u0119\7.\2\2\u0119\u011a\5\n\6\2\u011a\u011b\7.\2"+
		"\2\u011b\u011c\5\n\6\2\u011c\u011d\7.\2\2\u011d\u011e\5\n\6\2\u011e\u011f"+
		"\7\31\2\2\u011f\u0121\3\2\2\2\u0120\u00c2\3\2\2\2\u0120\u00c3\3\2\2\2"+
		"\u0120\u00c4\3\2\2\2\u0120\u00c9\3\2\2\2\u0120\u00ca\3\2\2\2\u0120\u00cf"+
		"\3\2\2\2\u0120\u00d5\3\2\2\2\u0120\u00db\3\2\2\2\u0120\u00e1\3\2\2\2\u0120"+
		"\u00e7\3\2\2\2\u0120\u00ed\3\2\2\2\u0120\u00f3\3\2\2\2\u0120\u00f9\3\2"+
		"\2\2\u0120\u00ff\3\2\2\2\u0120\u0108\3\2\2\2\u0120\u0113\3\2\2\2\u0121"+
		"\13\3\2\2\2\u0122\u0160\7B\2\2\u0123\u0160\7A\2\2\u0124\u0125\7\25\2\2"+
		"\u0125\u0126\7%\2\2\u0126\u0127\5\f\7\2\u0127\u0128\7\31\2\2\u0128\u0160"+
		"\3\2\2\2\u0129\u012a\7\25\2\2\u012a\u012b\5\n\6\2\u012b\u012c\7\26\2\2"+
		"\u012c\u012d\5\n\6\2\u012d\u012e\7\31\2\2\u012e\u0160\3\2\2\2\u012f\u0130"+
		"\7\25\2\2\u0130\u0131\5\n\6\2\u0131\u0132\78\2\2\u0132\u0133\5\n\6\2\u0133"+
		"\u0134\7\31\2\2\u0134\u0160\3\2\2\2\u0135\u0136\7\25\2\2\u0136\u0137\5"+
		"\n\6\2\u0137\u0138\79\2\2\u0138\u0139\5\n\6\2\u0139\u013a\7\31\2\2\u013a"+
		"\u0160\3\2\2\2\u013b\u013c\7\25\2\2\u013c\u013d\5\n\6\2\u013d\u013e\7"+
		":\2\2\u013e\u013f\5\n\6\2\u013f\u0140\7\31\2\2\u0140\u0160\3\2\2\2\u0141"+
		"\u0142\7\25\2\2\u0142\u0143\5\f\7\2\u0143\u0144\79\2\2\u0144\u0145\5\f"+
		"\7\2\u0145\u0146\7\31\2\2\u0146\u0160\3\2\2\2\u0147\u0148\7\25\2\2\u0148"+
		"\u0149\5\f\7\2\u0149\u014a\7:\2\2\u014a\u014b\5\f\7\2\u014b\u014c\7\31"+
		"\2\2\u014c\u0160\3\2\2\2\u014d\u014e\7\25\2\2\u014e\u014f\5\f\7\2\u014f"+
		"\u0150\7)\2\2\u0150\u0151\5\f\7\2\u0151\u0152\7\31\2\2\u0152\u0160\3\2"+
		"\2\2\u0153\u0154\7\25\2\2\u0154\u0155\5\f\7\2\u0155\u0156\7;\2\2\u0156"+
		"\u0157\5\f\7\2\u0157\u0158\7\31\2\2\u0158\u0160\3\2\2\2\u0159\u015a\7"+
		"\25\2\2\u015a\u015b\5\f\7\2\u015b\u015c\7\63\2\2\u015c\u015d\5\f\7\2\u015d"+
		"\u015e\7\31\2\2\u015e\u0160\3\2\2\2\u015f\u0122\3\2\2\2\u015f\u0123\3"+
		"\2\2\2\u015f\u0124\3\2\2\2\u015f\u0129\3\2\2\2\u015f\u012f\3\2\2\2\u015f"+
		"\u0135\3\2\2\2\u015f\u013b\3\2\2\2\u015f\u0141\3\2\2\2\u015f\u0147\3\2"+
		"\2\2\u015f\u014d\3\2\2\2\u015f\u0153\3\2\2\2\u015f\u0159\3\2\2\2\u0160"+
		"\r\3\2\2\2\u0161\u0162\7\13\2\2\u0162\u0163\7\26\2\2\u0163\u0164\7\27"+
		"\2\2\u0164\u0165\7\30\2\2\u0165\u0166\7?\2\2\u0166\u0167\7\34\2\2\u0167"+
		"\u0168\7<\2\2\u0168\u0169\7\13\2\2\u0169\u016a\7\26\2\2\u016a\u016b\7"+
		"\27\2\2\u016b\u016c\7\30\2\2\u016c\u016d\7\25\2\2\u016d\u016e\7?\2\2\u016e"+
		"\u016f\7\31\2\2\u016f\u0170\7\4\2\2\u0170\17\3\2\2\2\u0171\u0172\7?\2"+
		"\2\u0172\u0173\7\7\2\2\u0173\u0174\7=\2\2\u0174\u0175\7\25\2\2\u0175\u0176"+
		"\7\27\2\2\u0176\u0177\7\7\2\2\u0177\u0178\7+\2\2\u0178\u0179\7\25\2\2"+
		"\u0179\u017a\5\n\6\2\u017a\u017b\7\31\2\2\u017b\u017c\7\7\2\2\u017c\u017d"+
		"\7,\2\2\u017d\u017e\7\25\2\2\u017e\u017f\7\31\2\2\u017f\u0180\7-\2\2\u0180"+
		"\u0181\7\33\2\2\u0181\u0182\7\31\2\2\u0182\21\3\2\2\2\u0183\u0184\7\27"+
		"\2\2\u0184\u0185\7@\2\2\u0185\u0186\7\34\2\2\u0186\u0187\7\27\2\2\u0187"+
		"\u0188\7\7\2\2\u0188\u0189\7+\2\2\u0189\u018a\7\25\2\2\u018a\u018b\7C"+
		"\2\2\u018b\u018c\7\31\2\2\u018c\u018d\7\4\2\2\u018d\23\3\2\2\2\u018e\u018f"+
		"\7/\2\2\u018f\u0190\7A\2\2\u0190\u0191\7\34\2\2\u0191\u0192\7/\2\2\u0192"+
		"\u0193\7\7\2\2\u0193\u0194\7+\2\2\u0194\u0195\7\25\2\2\u0195\u0196\7B"+
		"\2\2\u0196\u0197\7\31\2\2\u0197\u0198\7\4\2\2\u0198\25\3\2\2\2\13\31N"+
		"hnv\177\u00c0\u0120\u015f";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}