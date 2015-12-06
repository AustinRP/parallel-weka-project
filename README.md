Weka Project for CSCE 569
-------------------------

By Philip Conrad, Austin Pahl, and Steve Rubin.


### How things are parallelized

We chose to try three different approaches for the sake of comparison:

 - Naive multi-threading.
 - "Smart" multi-threading. (Switches from sequential code to multi-threading when the data size is large enough.)
 - [JCuda][jcuda]-based GPU-acceleration.


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


### License

The license is the same as Weka's.


   [apache-ant]: http://ant.apache.org/
   [jcuda]: http://www.jcuda.org/