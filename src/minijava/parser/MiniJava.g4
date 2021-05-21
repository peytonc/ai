/** Mini java language inspired by http://www.cambridge.org/us/features/052182060X/grammar.html
 *  $ java -jar ../../../lib/antlr-4.9.2-complete.jar -package minijava.parser MiniJava.g4
 *  $ javac -cp ../../../lib/antlr-4.9.2-complete.jar MiniJava*.java
 *  $ java -cp .:../../../lib/antlr-4.9.2-complete.jar org.antlr.v4.gui.TestRig MiniJava program -gui ../../../GeneticProgram.java
 */

grammar MiniJava;

program
    :   'package' PACKAGENAME ';'
'import' 'java' '.' 'lang' '.' 'Exception' ';'
'import' 'java' '.' 'util' '.' 'ArrayList' ';'
'import' 'minijava' '.' 'Util' ';'
'public' 'class' 'GeneticProgram' '{'
'public' 'static' 'void' 'compute' '(' 'ArrayList' '<' 'Long' '>' LONGARRAYNAME ')' '{'
'int' 'size' '=' LONGARRAYNAME '.' 'size' '(' ')' ';'
declaration*
'try'
block
'catch' '(' 'Exception' 'e' ')' '{' LONGARRAYNAME '.' 'clear' '(' ')' ';' '}'
'}'
'}' EOF
    ;

block
    :   '{' declaration* statement* '}'
    ;

declaration
    :   longArrayDeclaration
    |   longDeclaration
    |   booleanDeclaration
    ;

statement
    :   'if' '(' expressionBoolean ')' block ('else' block)?
    |   'while' '(' '!' 'Thread' '.' 'currentThread' '(' ')' '.' 'isInterrupted' '(' ')' '&&' expressionBoolean ')' block
	|	LONGARRAYNAME '.' 'set' '(' 'Long' '.' 'valueOf' '(' expressionNumeric ')' '.' 'intValue' '(' ')' '%' 'size' ',' 'Long' '.' 'valueOf' '(' expressionNumeric ')' ')' ';'
    |   LONGNAME '=' 'Long' '.' 'valueOf' '(' expressionNumeric ')' ';'
    |   BOOLEANNAME '=' 'Boolean' '.' 'valueOf' '(' expressionBoolean ')' ';'
    ;

expressionNumeric
    :   NUMBER
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
    ;

expressionBoolean
    :   BOOLEAN
    |   BOOLEANNAME
    |   '(' '!' expressionBoolean ')'
    |   '(' expressionNumeric '<' expressionNumeric ')'
    |   '(' expressionNumeric '<=' expressionNumeric ')'
    |   '(' expressionNumeric '==' expressionNumeric ')'
    |   '(' expressionNumeric '!=' expressionNumeric ')'
    |   '(' expressionBoolean '==' expressionBoolean ')'
    |   '(' expressionBoolean '!=' expressionBoolean ')'
    |   '(' expressionBoolean '&&' expressionBoolean ')'
    |   '(' expressionBoolean '||' expressionBoolean ')'
    |   '(' expressionBoolean '^' expressionBoolean ')'
    ;

longArrayDeclaration
	:   'ArrayList' '<' 'Long' '>' LONGARRAYNAME '=' 'new' 'ArrayList' '<' 'Long' '>' '(' LONGARRAYNAME ')' ';'
	;

longArrayValue
	:   LONGARRAYNAME '.' 'get' '(' 'Long' '.' 'valueOf' '(' expressionNumeric ')' '.' 'intValue' '(' ')' '%' 'size' ')'
	;

longDeclaration
    :   'Long' LONGNAME '=' 'Long' '.' 'valueOf' '(' NUMBER ')' ';'
    ;

booleanDeclaration
    :   'Boolean' BOOLEANNAME '=' 'Boolean' '.' 'valueOf' '(' BOOLEAN ')' ';'
    ;
    
PACKAGENAME
	:   'species' '0' '.' 'id' '0'
	|   'species' '0' '.' 'id' DIGITNOZERO DIGIT*
	|   'species' DIGITNOZERO DIGIT* '.' 'id' '0'
	|   'species' DIGITNOZERO DIGIT* '.' 'id' DIGITNOZERO DIGIT*
    ;
    
LONGARRAYNAME
    :   'values' DIGIT DIGIT
    ;

LONGNAME
    :   'value' DIGIT DIGIT
    ;

BOOLEANNAME
    :   'condition' DIGIT DIGIT
    ;

BOOLEAN
    :   'false'
    |   'true'
    ;

NUMBER
    :   '0'
    |   DIGITNOZERO DIGIT*
    ;

fragment DIGIT
    :   [0-9]
    ;

fragment DIGITNOZERO
    :   [1-9]
    ;

WS  :  [ \t\r\n\u000C]+ -> skip
    ;
