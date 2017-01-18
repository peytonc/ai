/** Mini java language inspired by http://www.cambridge.org/us/features/052182060X/grammar.html
 *  $ java -jar ../../../lib/antlr-4.6-complete.jar -package minijava.parser MiniJava.g4
 *  $ javac -cp ../../../lib/antlr-4.6-complete.jar MiniJava*.java
 *  $ java -cp .:../../../lib/antlr-4.6-complete.jar org.antlr.v4.gui.TestRig MiniJava program -gui ../../../GeneticProgram.java
 */

grammar MiniJava;

program
    :   'package' packageName ';'
'import java.util.ArrayList;'
'import java.util.Collections;'
'public class GeneticProgram {'
'public static ArrayList<Long> compute(ArrayList<Long> values00) {'
block
'return values00;'
'}'
'}' EOF
    ;

block
    :   declaration* statement*
    ;

declaration
    :   longArrayDeclaration
    |   longDeclaration
    |   booleanArrayDeclaration
    |   booleanDeclaration
    ;

statement
    :   'if(' expressionBoolean ') {' statement '} else {' statement '}'
    |   'while(' expressionBoolean ') {' statement '}'
    |	longArrayName '.set(' expressionNumeric ', new Long(' expressionNumeric '));'
    |   longName '=' expressionNumeric ';'
    |	booleanArrayName '.set(' expressionNumeric ', new Boolean(' expressionBoolean '));'
    |   booleanName '=' expressionBoolean ';'
    ;

expressionNumeric
    :   '(' expressionNumeric ')'
    |   expressionNumeric ('^'|'%') expressionNumeric
    |   expressionNumeric ('*'|'/') expressionNumeric
    |   expressionNumeric ('+'|'-') expressionNumeric
    |   longArrayName '.' 'size()'
    |   longArrayValue
    |   longName
    |	numberValue
    ;

expressionBoolean
    :   '(' expressionBoolean ')'
    |   expressionNumeric ('<' | '<=') expressionNumeric
    |   expressionNumeric ('==' | '!=') expressionNumeric
    |   expressionBoolean ('==' | '!=') expressionBoolean
    |   expressionBoolean '&&' expressionBoolean
    |   expressionBoolean '||' expressionBoolean
    |   booleanArrayValue
    |   booleanName
    |	booleanValue
    ;

longArrayDeclaration
    :   'ArrayList<Long>' longArrayName '= new ArrayList<Long>(Collections.nCopies(values00.size(), new Long(' expressionNumeric ')));'
    ;

longArrayValue
    :   longArrayName '.get(' expressionNumeric ')'
    ;

longArrayName
    :   LONGARRAYNAME
    ;

longDeclaration
    :   'Long' longName ';'
    ;

longName
    :   LONGNAME
    ;

booleanArrayDeclaration
	:   'ArrayList<Boolean>' booleanArrayName '= new ArrayList<Boolean>(Collections.nCopies(values00.size(),' expressionBoolean '));'
    ;

booleanArrayValue
	:   booleanArrayName '.get(' expressionNumeric ')'
    ;

booleanArrayName
    :   BOOLEANARRAYNAME
    ;

booleanDeclaration
    :   'Boolean' booleanName ';'
    ;

booleanName
    :   BOOLEANNAME
    ;

numberValue
    :   NUMBER
    ;

booleanValue
    :   BOOLEAN
    ;

packageName
    :   PACKAGENAME
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
    |   '-' DIGITNOZERO DIGIT*
    ;

fragment DIGIT
    :   [0-9]
    ;

fragment DIGITNOZERO
    :   [1-9]
    ;

WS  :  [ \t\r\n\u000C]+ -> skip
    ;
