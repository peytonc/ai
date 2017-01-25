/** Mini java language inspired by http://www.cambridge.org/us/features/052182060X/grammar.html
 *  $ java -jar ../../../lib/antlr-4.6-complete.jar -package minijava.parser MiniJava.g4
 *  $ javac -cp ../../../lib/antlr-4.6-complete.jar MiniJava*.java
 *  $ java -cp .:../../../lib/antlr-4.6-complete.jar org.antlr.v4.gui.TestRig MiniJava program -gui ../../../GeneticProgram.java
 */

grammar MiniJava;

program
    :   'package' PACKAGENAME ';'
'import java.util.ArrayList;'
'import java.util.Collections;'
'public class GeneticProgram {'
'public static void compute(ArrayList<Long> values00) {' 
declaration*
block 
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
    :   'if(' expressionBoolean ')' block 'else' block
    |   'while(' expressionBoolean ')' block
    |	LONGARRAYNAME '.set(new Long(' expressionNumeric ').intValue(), new Long(' expressionNumeric '));'
    |   LONGNAME '=' 'new Long(' expressionNumeric ');'
    |	BOOLEANARRAYNAME '.set(new Long(' expressionNumeric ').intValue(), new Boolean(' expressionBoolean '));'
    |   BOOLEANNAME '=' 'new Boolean(' expressionBoolean ');'
    ;

expressionNumeric
    :   NUMBER
    |   LONGNAME
    |	LONGARRAYNAME '.' 'size()'
    |   longArrayValue
    |   '(' '-' expressionNumeric ')'
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
    :   'ArrayList<Long>' LONGARRAYNAME '= new ArrayList<Long>(Collections.nCopies(values00.size(), new Long(' expressionNumeric ')));'
    ;

longArrayValue
    :   LONGARRAYNAME '.get(new Long(' expressionNumeric ').intValue())'
    ;

longDeclaration
    :   'Long' LONGNAME '= new Long(0);'
    ;

booleanArrayDeclaration
	:   'ArrayList<Boolean>' BOOLEANARRAYNAME '= new ArrayList<Boolean>(Collections.nCopies(values00.size(),' expressionBoolean '));'
    ;

booleanArrayValue
	:   BOOLEANARRAYNAME '.get(new Long(' expressionNumeric ').intValue())'
    ;

booleanDeclaration
    :   'Boolean' BOOLEANNAME '= new Boolean(false);'
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
