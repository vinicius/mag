/*********************************************************

 Author: Ulisses Kendi Hayashida
 Data: 21/07/2004

 *********************************************************/

/**
 * \file
 *
 * This program calculates the systolic multiplication
 * of two matrixes.
 *
 */


#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include "BspLib.hpp"
#include <sys/time.h>
#define row (bsp_pid()/q)
#define column (bsp_pid()%q)

/**
 * \brief Function that initiates a matrix A with zeros.
 *
 * \param A a matrix.
 * \param order order of the matrix.
 */

void Zero(long double* A,int order);

/**
 * \brief Function that calculates the systolic
 *        multiplication of two matrices.
 *
 * \param M number of pairs of the matrices.
 * \param n order of the matrices.
 * \param q order of the grid.
 *
 */

void Syst_Mult(int M,int n, int q);

/**
 * \brief Function that multiplies two matrices A and B,
 *	  and puts the result in matrix C.
 *
 * \param A matrix A.
 * \param B matrix B.
 * \param C matrix C.
 * \param order order of the matrices.
 */

void Multiplication(long double *A,long double *B,long double *C,int order);

/**
 * \brief Main function. See the README file for more details.
 *	  
 */

int main(int argc, char** argv)
{
  int              p;  /*number of processes*/
  int              N;  /*matrices order*/
  int              n;  /*submatrices order*/
  int              M;  /*number of matrices*/
  int              q;  /*grid order*/

  bsp_begin(atoi(argv[1]));
  p = atoi(argv[1]);

  if (bsp_pid() == 0)
  {
    if (p != bsp_nprocs()) /*if there are not sufficient processes*/
    {
      printf("%d processes instead of %d\n",bsp_nprocs(),p);
      exit(1);
    }
  }

  N = atoi(argv[2]);
  M = atoi(argv[3]);
  q = sqrt(p);

  if (q*q != p)
  {
    printf("The first argument(number of processes) have to have integer square root\n");
    exit(2);
  }

  n = N/q;

  if (N != n*q)
  {
    printf("The second argument(matrices order) have to be multiple of the square root of the first argument\n");
    exit(2);
  }

  printf("sistolyc %d %d %d\n",p,N,M);

  Syst_Mult(M,n,q);

  return 0;
}/* main */


void Syst_Mult
(
  int     M,    /*number of matrices*/
  int     n,    /*order of the submatrices*/
  int     q     /*order of the grid*/
)
{
  long double*    A;      /*matrix A*/
  long double*    B;      /*matrix B*/
  long double*    C;      /*matrix C*/
  int             i,j,k,cond,tag,tag_size,status;
  long double     aux2;
  double          *auxc,*auxc2;
  struct timeval  ini,fi;
  struct timezone tz;
  char processor[20];  

  A = (long double*)malloc(n*n*sizeof(long double));
  B = (long double*)malloc(n*n*sizeof(long double));
  C = (long double*)malloc(n*n*sizeof(long double));

  if (A == NULL || B == NULL || C == NULL)
  {
    printf("rank %d, no memory\n",bsp_pid());
    exit(-2);
  }

  Zero(C,n);

  tag_size = sizeof(int);

  bsp_set_tagsize(&tag_size);
  bsp_sync();

  gettimeofday(&ini,&tz);

  for (i = 0; i < (M+2)*q -2; i++)
  {
    if (row + column  <= i && i <= row+column+M*q-1)
      cond = 1;
    else cond = 0;

    if (cond)
    {
      if (column == 0)
      {
	aux2 = (long double) (bsp_pid() + i - row);
	for (j = 0; j < n; j++)
	  for (k = 0; k < n; k++)
	    A[j*n + k] = aux2;
      }
      if (row == 0)
      {
	for (j = 0; j < n; j++)
	  for (k = 0; k < n; k++)
	    B[j*n + k] = (long double) 0;
	if (i == 2*(column) + ((i-2*(column))/q)*q)
	  for (j = 0; j < n; j++)
	    B[j*n + j] = (long double) 1;
      }

    }
    if (cond)
    {
      Multiplication(A,B,C,n);
    }
    if (cond && (i-row-column+1)%q == 0)
    {
      aux2 = (long double)(bsp_pid()+i-row-column+1-q);
      for (j = 0; j < n; j++)
      {
	for (k = 0; k < n; k++)
	{
	  if (C[j*n + k] != aux2)
	  {
	    printf("Rank %d, Error:\n",bsp_pid());
	    printf("Something is wrong\n");
	    j = n;
	    k = n;
	  }
	}
      }
      Zero(C,n);
    }

    if (cond)
    {
      if (column != q-1)
      {
	tag = 1;
	bsp_send(bsp_pid()+1,&tag,A,n*n*sizeof(long double));
      }
      if (row != q-1)
      {
	tag = 2;
	bsp_send(bsp_pid()+q,&tag,B,n*n*sizeof(long double));
      }
    }

    bsp_sync();

    bsp_qsize(&j,&k);

    if (j>0) {

      bsp_get_tag(&status,&k);
      if (status != n*n*sizeof(long double))
      {
	printf("rank %d, error, status don't matches\n",bsp_pid());
      }
      if (k == 1)
      {
	bsp_move(A,n*n*sizeof(long double));
	if (j > 1)
	{
	  bsp_get_tag(&status,&k);
	  bsp_move(B,n*n*sizeof(long double));
	}
      }
      else
      {
	bsp_move(B,n*n*sizeof(long double));
	if (j > 1)
	{
	  bsp_get_tag(&status,&k);
	  bsp_move(A,n*n*sizeof(long double));
	}
      }

    }

    bsp_sync();

  }/* for */

  gettimeofday(&fi,&tz);

  gethostname(processor,20);
  printf("processor %s, time: %f\n",processor,(fi.tv_sec - ini.tv_sec +
	 (double)(fi.tv_usec - ini.tv_usec)/1000000)/60);


} /* main */


void Zero(long double *A, int order) {

 int i;

 for (i = 0; i < order*order; i++)
   A[i] = (long double) 0.0;
}

void Multiplication(long double* A, long double *B,
  long double* C, int order)
{
  int i, j, k;
  long double aux;

  for (i = 0; i < order; i++)
    for (j = 0; j < order; j++)
      for (k = 0; k < order; k++)
      {
	aux = A[i*order+k]*B[k*order+j];
	C[i*order+j] += aux;
      }
}
