*******************************************************************************
                                   README 
*******************************************************************************

Source File: matrix.c
C library used: BSP (Bulk Synchronous Parallel)

To compile:

  bspcc -o <EXECUTABLE> -lm matrix.c

To run:

  bsprun <EXECUTABLE> <N_PROCS> <ORDER> <NUMBER_OF_MULTIPLICATIONS>

  Where:
      <EXECUTABLE>: The name of executable to generate.
  
      <N_PROCS>: The number of processes. It has to be a number with
                 integer square root, because it simulates a square mesh.
      
      <ORDER>: The order of the square matrices <ORDER>x<ORDER>. 
               <ORDER> have to be a number divisible by the square root
               of <N_PROCS>, to divide the submatrices in equal size.
      
      <NUMBER_OF_MULTIPLICATIONS>: The consecutive number of systolic
                                   multiplications in pipeline.

The Program:

  The Program matrix.c performs <NUMBER_OF_MULTIPLICATIONS> systolic
multiplications of two square matrices A an B of order <ORDER> in pipeline.
  The program requires <N_PROCS> processes, arranged in two dimensiones 
simulating a Grade or Square Mesh.

Input and Output:

  The submatrices are generated automatically by the processes of the first
line and first column. There is no need of input data reading.
  The values of input and output are deterministics, and the output is
verified at the final of each multiplication.
  As the values of output are deterministics, nothing is printed about the
results, except when there are errors. The values of input matrix A are 
calculated imagining that the matrix is disposed on the Grade and that each
element of the submatrix takes the value of the rank of the process in which 
the submatrix is arranged. The values of input matrix B are calculated 
similarly, but taking the values of a identity matrix.
  In each multiplication, the values of matrix A are increased by <ORDER>, 
without changes to matrix B.
