#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include "BspLib.hpp"
#define linha bsp_pid()/q
#define coluna bsp_pid()%q

void zera(long double* A,int order);
void Mult_sist(int M,int n, int q,long double* A,
long double* B,long double* C);
void multiplica(long double *A,long double *B,long double *C,int order);

/*********************************************************/
int main(int argc, char** argv)
{
  int              p; 	/*numero de processadores*/
  long double*           A;		/*matriz A*/
  long double*           B;		/*matriz B*/
  long double*           C;		/*matriz C*/
  int              N;		/*ordem das matrizes*/
  int              n;		/*ordem das submatrizes*/
  int 		   M;		/*numero de matrizes*/
  int		   q;		/*ordem do grid*/
  int              i;

  printf("######################################################\n");

  p = atoi(argv[1]);

  bsp_begin(p);

  printf("Bom dia, meu PID eh: %d\n", bsp_pid());

  if (bsp_pid() == 0)
    {
      if (p != bsp_nprocs()) /*se nao ha procs suficiente*/
	{
          printf("Ha apenas %d procs, ao invez de %d\n",bsp_nprocs(),p);
          exit(1);
        }
      else printf("Comecando com %d procs\n",bsp_nprocs());
      N = atoi(argv[2]);
      M = atoi(argv[3]);
    }

  bsp_push_reg(&p,sizeof(int));
  bsp_push_reg(&N,sizeof(int));
  bsp_push_reg(&M,sizeof(int));

  bsp_sync();

  if (bsp_pid() == 0)
    for (i = 1; i < p; i++)
      {
	bsp_put(i,&p,&p,0,sizeof(int));
	bsp_put(i,&N,&N,0,sizeof(int));
	bsp_put(i,&M,&M,0,sizeof(int));
      }

  bsp_sync();

  bsp_pop_reg(&p);
  bsp_pop_reg(&N);
  bsp_pop_reg(&M);
  /*a partir daqui todos tem p, N e M*/

  q = sqrt(p);
  if (q*q != p)
    {
      printf("p deve ter raiz inteira\n");
      exit(2);
    }
  n = N/q;
  if (N != n*q)
    {
      printf("N deve ser multiplo de q\n");
      exit(2);
    }

  A = (long double*)malloc(n*n*sizeof(long double));
  B = (long double*)malloc(n*n*sizeof(long double));
  C = (long double*)malloc(n*n*sizeof(long double));

  if (A == NULL || B == NULL || C == NULL)
    {
      printf("rank %d, faltou mem¢ria\n",bsp_pid());
      exit(-2);
    }

  Mult_sist(M,n,q,A, B, C);

  /*bsp_end();*/

  return 0;
}  /* main */

/*********************************************************/
void Mult_sist(
  int	  M	  ,     /*numero de matrizes*/
  int     n	  ,	/*ordem da submatriz*/
  int     q       ,	/*ordem do grid*/
  long double*  A       ,    	/*matriz A*/
  long double*  B       , 	/*matriz B*/
  long double*  C   	)	/*matriz C*/
{
  int		   i,j,k,cond;
  long double      aux2;
  zera(C,n);

  bsp_push_reg(A,n*n*sizeof(long double));
  bsp_push_reg(B,n*n*sizeof(long double));
  bsp_sync();

  for (i = 0; i < (M+2)*q -2; i++)
    {
      if (linha + coluna  <= i && i <= linha+coluna+M*q-1)/*linha + coluna >= i-q+1)*/
	cond = 1;
      else cond = 0;

      if (cond)
	{
	  if (coluna == 0)
	    {
	      aux2 = (long double) (bsp_pid() + i - linha);
	      for (j = 0; j < n; j++)
		for (k = 0; k < n; k++)
		  A[j*n + k] = aux2;
	    }
	  if (linha == 0)
	    {
	      for (j = 0; j < n; j++)
		for (k = 0; k < n; k++)
		  B[j*n + k] = (long double) 0;
	      if (i == 2*(coluna) + ((i-2*(coluna))/q)*q)
		for (j = 0; j < n; j++) B[j*n + j] = (long double) 1;
	    }
	}

      if (cond)
	multiplica(A,B,C,n);

      if (cond)
	{
	  printf("PID %d,resultado para AxB, sendo A igual a:\n",bsp_pid());
	  for (j = 0; j < n; j++)
	    {
	      for (k = 0; k < n; k++)
		{
		  printf("%Lf ", A[j*n+k]);
		}
	      printf("\n");
	    }
	  printf("PID %d, e a matriz B sendo: \n",bsp_pid());
	  for (j = 0; j < n; j++)
	    {
	      for (k = 0; k < n; k++)
		{
		  printf("%Lf ", B[j*n+k]);
		}
	      printf("\n");
	    }
	  printf("PID %d, RESULTADO matriz C:\n",bsp_pid());
	  for (j = 0; j < n; j++)
	    {
	      for (k = 0; k < n; k++)
		{
		  printf("%Lf ", C[j*n+k]);
		}
	      printf("\n");
	    }
	  }

      if (cond && (i-linha-coluna+1)%q == 0)
	{
	  aux2 = (long double)(bsp_pid()+i-linha-coluna+1-q);
	  for (j = 0; j < n; j++)
	    {
	      for (k = 0; k < n; k++)
		{
		  if (C[j*n + k] != aux2)
		    {
		      printf("rank %d, erro, C[i,j] == %Lf, i = %d,j = %d, k = %d\n",bsp_pid(),C[j*n+k],i,j,k);
		      printf("deveria ser %Lf\n",aux2);
		      printf("A == %Lf, B == %Lf\n",A[j*n],B[k]);
		      j = n;
		      k = n;
		    }
		}
	    }

	  zera(C,n);
	}

      if (cond)
	{
	  if (coluna != q-1)
	    bsp_put(bsp_pid()+1,A,A,0,n*n*sizeof(long double));

	  if (linha != q-1)
	    bsp_put(bsp_pid()+q,B,B,0,n*n*sizeof(long double));
	}
      bsp_sync();
    }
} /* Fox */

/*********************************************************/
void zera(long double *A, int order) {

 int i;

 for (i = 0; i < order*order; i++)
   A[i] = (long double) 0.0;
}

/*********************************************************/
void multiplica(long double* A,long double *B,long double *C, int order)
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
