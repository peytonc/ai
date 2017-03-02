# Synopsis #

The project is a genetic program (GP) that automatically generates source code in order to solve a particular problem. The GP is written in Java and generates source code in Java. The problem is defined by a set of input variables (X) and the desired output variables (Y). The GP’s goal is to generate source code P that maps X to Y, resulting in the functional mapping Y=P(X).

The GP creates an population (or set) of pseudo-random source codes from a population of parent source code; by way of mutation and crossover. Source code fitness is determined by correctness or closeness to solving a specific problem, code size, code speed, and no compile/runtime error/warnings. The most fit of this population becomes the parents of the next generation of codes. And the process repeats. This approach uses concepts from compiler construction and genetic programming.

Each generation of the GP life cycle is as follows:
```java
createEnviroment();
createTests();
createPopulation();
compilePopulation();
executePopulation();
evaluatePopulation();
storeBestFit();
downselectPopulation();
```

# Installation #

1. git clone https://github.com/peytonc/ai.git
2. cd ai/src/minijava/parser
3. java -jar ../../../lib/antlr-4.6-complete.jar -package minijava.parser MiniJava.g4
4. cd ../../..
5. mkdir log
6. mkdir data
7. TODO add command line execution

# Resources #

1. [https://github.com/peytonc/ai](https://github.com/peytonc/ai)
2. [https://en.wikipedia.org/wiki/Genetic_programming](https://en.wikipedia.org/wiki/Genetic_programming)
3. [http://cswww.essex.ac.uk/staff/poli/gp-field-guide/index.html](http://cswww.essex.ac.uk/staff/poli/gp-field-guide/index.html)
4. [http://www.antlr.org](http://www.antlr.org)

# License #

See [LICENSE.md](LICENSE.md) for details.
