/** Mini java language inspired by http://www.cambridge.org/us/features/052182060X/grammar.html
 *  $ java -jar ../../../lib/antlr-4.6-complete.jar -package minijava.parser MiniJava.g4
 *  $ javac -cp ../../../lib/antlr-4.6-complete.jar MiniJava*.java
 *  $ java -cp .:../../../lib/antlr-4.6-complete.jar org.antlr.v4.gui.TestRig MiniJava program -gui ../../../GeneticProgram.java
 */

grammar MiniJava;

program
    :   'package' PACKAGENAME ';'
'import' 'java' '.' 'lang' '.' 'Exception' ';'
'import' 'java' '.' 'util' '.' 'ArrayList' ';'
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
    :   '{' statement* '}'
    ;

declaration
    :   longArrayDeclaration
    |   longDeclaration
    |   booleanArrayDeclaration
    |   booleanDeclaration
    ;

statement
    :   'if' '(' expressionBoolean ')' block 'else' block
    |   'while' '(' '!' 'Thread' '.' 'currentThread' '(' ')' '.' 'isInterrupted' '(' ')' '&&' expressionBoolean ')' block
	|	LONGARRAYNAME '.' 'set' '(' 'new' 'Long' '(' expressionNumeric ')' '.' 'intValue' '(' ')' '%' 'size' ',' 'new' 'Long' '(' expressionNumeric ')' ')' ';'
    |   LONGNAME '=' 'new' 'Long' '(' expressionNumeric ')' ';'
    |	BOOLEANARRAYNAME '.' 'set' '(' 'new' 'Long' '(' expressionNumeric ')' '.' 'intValue' '(' ')' '%' 'size' ',' 'new' 'Boolean' '(' expressionBoolean ')' ')' ';'
    |   BOOLEANNAME '=' 'new' 'Boolean' '(' expressionBoolean ')' ';'
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
    ;

expressionBoolean
    :   BOOLEAN
    |   BOOLEANNAME
    |   booleanArrayValue
    |   '(' '!' expressionBoolean ')'
    |   '(' expressionNumeric '<' expressionNumeric ')'
    |   '(' expressionNumeric '<=' expressionNumeric ')'
    |   '(' expressionNumeric '==' expressionNumeric ')'
    |   '(' expressionNumeric '!=' expressionNumeric ')'
    |   '(' expressionBoolean '==' expressionBoolean ')'
    |   '(' expressionBoolean '!=' expressionBoolean ')'
    |   '(' expressionBoolean '&&' expressionBoolean ')'
    |   '(' expressionBoolean '||' expressionBoolean ')'
    ;

longArrayDeclaration
	:   'ArrayList' '<' 'Long' '>' LONGARRAYNAME '=' 'new' 'ArrayList' '<' 'Long' '>' '(' LONGARRAYNAME ')' ';'
	;

longArrayValue
	:   LONGARRAYNAME '.' 'get' '(' 'new' 'Long' '(' expressionNumeric ')' '.' 'intValue' '(' ')' '%' 'size' ')'
	;

longDeclaration
    :   'Long' LONGNAME '=' 'new' 'Long' '(' NUMBER ')' ';'
    ;

booleanArrayDeclaration
	:   'ArrayList' '<' 'Boolean' '>' BOOLEANARRAYNAME '=' 'new' 'ArrayList' '<' 'Boolean' '>' '(' 'size' ')' ';'
    ;

booleanArrayValue
	:   BOOLEANARRAYNAME '.' 'get' '(' 'new' 'Long' '(' expressionNumeric ')' '.' 'intValue' '(' ')' '%' 'size' ')'
    ;

booleanDeclaration
    :   'Boolean' BOOLEANNAME '=' 'new' 'Boolean' '(' 'false' ')' ';'
    ;
    
PACKAGENAME
	:   'package' DIGIT
	|   'package' DIGITNOZERO DIGIT
    ;
    
LONGARRAYNAME
    :   'values' DIGIT DIGIT
    ;

LONGNAME
    :   'value' DIGIT DIGIT
    ;

BOOLEANARRAYNAME
    :   'conditions' DIGIT DIGIT
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