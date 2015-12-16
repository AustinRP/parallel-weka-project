Parallel Weka Project for CSCE 569 - Parallel Computing
-------------------------
### University of South Carolina - Columbia

By Philip Conrad, Austin Pahl, and Steve Rubin.

Issued under the [GNU General Public License][gpl].

### Introduction

This was written for USC-Columbia's CSCE 569 in Fall 2015, taught by Dr. Jianjun Hu.

[Weka][weka] is a widely used Java machine-learning library aimed towards data-mining applications. We have configured the library to run on Java 8 and parallelized its matrix operations using three different methods (see below).

Anyone is welcome to fork and build upon this project. Weka is a large library, so there is plenty that can be parallelized beyond what we have done.

### How things are parallelized

We include two approaches to parallelization:

 - Naive multi-threading.
 - "Smart" multi-threading. Switches from sequential code to multi-threading when the data size is large enough.


### What is parallelized

`weka.core.matrix`

 - `transpose()`
 - `uminus()`
 - `plus(Matrix B)`
 - `plusEquals(Matrix B)`
 - `minus(Matrix B)`
 - `minusEquals(Matrix B)`
 - `arrayTimes(Matrix B)`
 - `arrayTimesEquals(Matrix B)`
 - `arrayRightDivide(Matrix B)`
 - `arrayRightDivideEquals(Matrix B)`
 - `arrayLeftDivideEquals(Matrix B)`
 - `arrayLeftDivideEquals(Matrix B)`
 - `times(double s)`
 - `timesEquals(double s)`
 - `times(Matrix B)`

As new classes/methods are parallelized, they will be recorded here.


### How to build the code (ANT)

If you are working on the command line, you can use [Apache ANT][apache-ant] to build the code.

    ant compile

To build the test suite, just run:

    ant compile_tests


### How to benchmark the code

Check out the `weka-benchmark` repo, and follow the instructions in the README.
### please also take a look at our [benchmark repository][benchmark]

   [weka]: http://www.cs.waikato.ac.nz/ml/index.html
   [gpl]: http://www.gnu.org/licenses/gpl.html
   [apache-ant]: http://ant.apache.org/
   [jcuda]: http://www.jcuda.org/
   [benchmark]: https://github.com/AustinRP/weka-benchmark
