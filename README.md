# Synopsis #

The project is a genetic program (GP) that automatically generates source code in order to solve a particular problem. The GP is written in Java and generates source code in Java. The problem is defined by a set of input variables (X) and the desired output variables (Y). The GP’s goal is to generate source code P that maps X to Y, resulting in the functional mapping Y=P(X).

The GP creates an population (or set) of pseudo-random source codes from a population of parent source code; by way of preservation, mutation, and crossover. Source code fitness is determined by mean error, correctness, code size, code speed, and no compile/runtime error/warnings to solving a specific problem. The most fit of this population becomes the parents of the next generation of codes. And the process repeats. This approach uses concepts from compiler construction and genetic programming.

The GP life cycle is a follows:
```java
do {
	initalizeYear(year);
	extinction();
	for(int day=0; day<Environment.DAYS_PER_YEAR; day++) {
		executeDay(day);
	}
	year++;
} while(!isSolved());
```

Each day of a species is as follows:
```java
createPopulation();
compilePopulation();
executePopulation();
evaluatePopulation();
downselectPopulation();
promoteChampion();
storeBestFitness();
```

# Installation #

1. git clone https://github.com/peytonc/gp.git
2. cd gp/src/minijava/parser
3. java -jar ../../../lib/antlr-4.9.2-complete.jar -package minijava.parser MiniJava.g4
4. cd ../../..
5. mkdir log
6. mkdir data
7. mkdir bin
8. javac -Xlint -d bin -sourcepath src -cp lib/\* src/minijava/GP.java
9. java -cp bin:lib/\* minijava.GP

# Bugs/Issues #
1. Must use latest version of OpenJDK per [https://stackoverflow.com/questions/58927052/is-java-vm-defective-or-why-does-repeated-use-of-compilationtask-and-reflections](https://stackoverflow.com/questions/58927052/is-java-vm-defective-or-why-does-repeated-use-of-compilationtask-and-reflections)

# Resources #

1. [https://github.com/peytonc/gp](https://github.com/peytonc/gp)
2. [https://en.wikipedia.org/wiki/Genetic_programming](https://en.wikipedia.org/wiki/Genetic_programming)
3. [http://cswww.essex.ac.uk/staff/poli/gp-field-guide/index.html](http://cswww.essex.ac.uk/staff/poli/gp-field-guide/index.html)
4. [http://www.antlr.org](http://www.antlr.org)
5. [https://commons.apache.org/proper/commons-math](https://commons.apache.org/proper/commons-math/)

# License #

See [LICENSE.md](LICENSE.md) for details.
