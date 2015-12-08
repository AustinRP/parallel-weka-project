// Imports, JCublas 
import jcuda.*;
import jcuda.jcublas.*;

class matrix_multiplication
{
  // Matrix size 
  private static final int N = 275;

  // Main 
  public static float[] matrix_multiplication(float[] A_input, int A_number_of_rows, int A_number_of_columns, 
    float[] B_input, int B_number_of_rows, int B_number_of_columns)
  {

    if (A_number_of_columns != B_number_of_rows)
    {
      System.out.println("Error in dimensions checking");
      System.exit(0);
    }

    float h_A[];
    float h_B[];
    float h_C[];
    Pointer d_A = new Pointer();
    Pointer d_B = new Pointer();
    Pointer d_C = new Pointer();
    float alpha = 1.0f;
    float beta = 0.0f;
    int n2 = N * N;
    int i;

    int A_number_of_elements = A_number_of_rows*A_number_of_columns;
    int B_number_of_elements = B_number_of_rows*B_number_of_columns;
    int C_number_of_rows = A_number_of_rows;
    int C_number_of_columns = B_number_of_columns;
    int C_number_of_elements = C_number_of_rows*C_number_of_columns;

    // Initialize JCublas 
    JCublas.cublasInit();

    h_C = new float[C_number_of_elements];

    /*
    // Allocate host memory for the matrices
    // oroginal code
    h_A = new float[n2];
    h_B = new float[n2];
    h_C = new float[n2];

    /// Fill the matrices with test data
    // oroginal code
    for (i = 0; i < n2; i++)
    {
      h_A[i] = (float)Math.random();
      h_B[i] = (float)Math.random();
      h_C[i] = (float)Math.random();
    }
    */

    for (i = 0; i < C_number_of_elements; i++)
    {
      h_C[i] = (float)0;
    }

    // Allocate device memory for the matrices 
    JCublas.cublasAlloc(A_number_of_elements, Sizeof.FLOAT, d_A);
    JCublas.cublasAlloc(A_number_of_elements, Sizeof.FLOAT, d_B);
    JCublas.cublasAlloc(C_number_of_elements, Sizeof.FLOAT, d_C);

    // Initialize the device matrices with the host matrices
    JCublas.cublasSetVector(A_number_of_elements, Sizeof.FLOAT, Pointer.to(h_A), 1, d_A, 1);
    JCublas.cublasSetVector(B_number_of_elements, Sizeof.FLOAT, Pointer.to(h_B), 1, d_B, 1);
    JCublas.cublasSetVector(C_number_of_elements, Sizeof.FLOAT, Pointer.to(h_C), 1, d_C, 1);




    /*
    public static void cublasSgemm(char transa,
               char transb,
               int A_number_of_rows,
               int B_number_of_columns,
               int A_number_of_columns,
               float alpha,
               jcuda.Pointer A,
               int lda,
               jcuda.Pointer B,
               int ldb,
               float beta,
               jcuda.Pointer C,
               int ldc)
    void
    cublasSgemm (char transa, char transb, int m, int n, int k, float alpha,
              const float *A, int lda, const float *B, int ldb, float beta,
              float *C, int ldc)

    computes the product of matrix A and matrix B, multiplies the result
    by a scalar alpha, and adds the sum to the product of matrix C and
    scalar beta. sgemm() performs one of the matrix-matrix operations:

     C = alpha * op(A) * op(B) + beta * C,

    where op(X) is one of

     op(X) = X   or   op(X) = transpose(X)

    alpha and beta are single precision scalars, and A, B and C are
    matrices consisting of single precision elements, with op(A) an m x k
    matrix, op(B) a k x n matrix, and C an m x n matrix. Matrices A, B,
    and C are stored in column major format, and lda, ldb, and ldc are
    the leading dimensions of the two-dimensional arrays containing A,
    B, and C.

    Input
    -----
    transa specifies op(A). If transa = 'n' or 'N', op(A) = A. If
        transa = 't', 'T', 'c', or 'C', op(A) = transpose(A)
    transb specifies op(B). If transb = 'n' or 'N', op(B) = B. If
        transb = 't', 'T', 'c', or 'C', op(B) = transpose(B)
    m      number of rows of matrix op(A) and rows of matrix C
    n      number of columns of matrix op(B) and number of columns of C
    k      number of columns of matrix op(A) and number of rows of op(B)
    alpha  single precision scalar multiplier applied to op(A)op(B)
    A      single precision array of dimensions (lda, k) if transa =
        'n' or 'N'), and of dimensions (lda, m) otherwise. When transa =
        'N' or 'n' then lda must be at least  max( 1, m ), otherwise lda
        must be at least max(1, k).
    lda    leading dimension of two-dimensional array used to store matrix A
    B      single precision array of dimensions  (ldb, n) if transb =
        'n' or 'N'), and of dimensions (ldb, k) otherwise. When transb =
        'N' or 'n' then ldb must be at least  max (1, k), otherwise ldb
        must be at least max (1, n).
    ldb    leading dimension of two-dimensional array used to store matrix B
    beta   single precision scalar multiplier applied to C. If 0, C does
        not have to be a valid input
    C      single precision array of dimensions (ldc, n). ldc must be at
        least max (1, m).
    ldc    leading dimension of two-dimensional array used to store matrix C

    Output
    ------
    C      updated based on C = alpha * op(A)*op(B) + beta * C

    Reference: http://www.netlib.org/blas/sgemm.f

    Error status for this function can be retrieved via cublasGetError().

    Error Status
    ------------
    CUBLAS_STATUS_NOT_INITIALIZED  if CUBLAS library has not been initialized
    CUBLAS_STATUS_INVALID_VALUE    if any of m, n, or k are < 0
    CUBLAS_STATUS_EXECUTION_FAILED if function failed to launch on GPU
    */

    //Performs operation using JCublas 
    JCublas.cublasSgemm('n', 'n', A_number_of_rows, B_number_of_columns, A_number_of_columns, alpha,
              d_A, A_number_of_rows, d_B, B_number_of_rows, beta, d_C, C_number_of_rows);

    // Read the result back 
    JCublas.cublasGetVector(C_number_of_elements, Sizeof.FLOAT, d_C, 1, Pointer.to(h_C), 1);

    // Memory clean up
    JCublas.cublasFree(d_A);
    JCublas.cublasFree(d_B);
    JCublas.cublasFree(d_C);

    // Shutdown 
    JCublas.cublasShutdown();
    return h_C;

  }
}