/**********************************************************

 Author: Ulisses Kendi Hayashida
 Data: 27/08/2005

 **********************************************************/

/**
 * \file
 *
 * This program calculates de similarity of two DNA sequences.
 *
 */

#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>
#include <unistd.h>
#include "BspLib.hpp"
#define gap -2
#define p(a,b) (a == b ? 1 : -1)

/**
 * \brief Function that calculates the maximum of three integers.
 *
 * \param A an integer.
 * \param B an integer.
 * \param C an integer.
 * \return The maximum of three integers.
 */

int Max (int A, int B, int C)
{
  if (A >= B && A >= C) return A;
  else if (B >= C) return B;
  else return C;
}

/**
 * \brief Function that implements the Similarity algorithm.
 *
 * This function implements the Similarity algorithm using
 *   linear memory.
 * \param s a DNA string.
 * \param sizes the size of \a s.
 * \param t a DNA string.
 * \param sizet the size of \a t.
 * \param a a similarity vector.
 * \param b a auxiliar vector.
 * \return similarity betwen \a s and \a t.
 */

int Similarity (
		 char *s, int sizes,
		 char *t, int sizet,
		 int *a, int *b
	       )
{
  int i,j,aux,aux2;


  for (i = 1; i <= sizes; i++)
  {

    aux = a[1];
    aux2 = a[sizet];
    a[1] = Max (
		 a[1] + gap,
		 b[i-1] + p(s[i-1],t[0]),
		 b[i] + gap
	       );
    b[i-1] = aux2;
    for (j = 2; j <= sizet; j++)
    {
      aux2 = a[j];
      a[j] = Max (
		   a[j] + gap,
		   aux + p(s[i-1],t[j-1]),
		   a[j-1] + gap
		 );
      aux = aux2;
    }
  }
  b[i-1] = a[sizet];
  return a[sizet];
}

/**
 * \brief Exit procedure.
 *
 * \param arg a pointer to a string of constant size.
 */

void Exit(char arg[100])
{
  printf("%s\n",arg);
  exit (2);
}


/**
 * \brief Main function.
 */
int main(int argc, char **argv)
{
  char            *s,*t;
  int             size,sizes,sizet;
  int             i,j,k,P;
  int             cond;
  int             *simi,res,Paux;
  int             *a,*b;
  FILE            *f,*f2;
  fpos_t          filepos;
  int             my_rank,set;
  struct timeval  ini, fi;
  struct timezone tz;


  bsp_begin(atoi(argv[1]));

  size = atoi(argv[1]);

  f=fopen(argv[2],"r");
  if (f==NULL) Exit("Error: File %s not found\n",argv[2]);
  fscanf(f,"%d",&sizes);

  if (sizes%size != 0)
    Exit("Error: The sequences have to have multiple of "
         "processes quantity size");

  f2=fopen(argv[3],"r");

  if (f2==NULL) Exit("Error: File %s not found\n",argv[3]);

  fscanf(f2,"%d",&sizet);

  if (bsp_pid() == 0)
    if (sizet%size != 0)
      Exit("Error: The sequences have to have multiple of "
         "processes quantity size");

  P = atoi(argv[4]);

  if (bsp_pid() == 0)
    printf("align %d %s %s %d\n",size,argv[2],argv[3],P);

  sizes /= size;
  sizet /= size;

  s = (char*) malloc (sizes*sizeof(char));
  t = (char*) malloc (sizet*sizeof(char));

  if (s == NULL || t == NULL)
    Exit("No memory\n");


  a = (int*)malloc ((sizet+1)*sizeof(int));
  b = (int*)malloc ((sizes+1)*sizeof(int));


  if (a == NULL || b == NULL)
    Exit("No memory\n");


  if (bsp_pid() == size-1)
  {
    simi = (int*) malloc(P*sizeof(int));
    if (simi == NULL) Exit("No memory\n");
  }

  Paux = 0;

  bsp_push_reg(s,sizes*sizeof(char));
  bsp_push_reg(b,(sizes+1)*sizeof(int));
  bsp_push_reg(&filepos,sizeof(long int));
  bsp_push_reg(&i,sizeof(int));

  bsp_sync();

  gettimeofday(&ini,&tz);
  
  for (k = 0; k < P*size + size -1; k++)
  {
    if (k >= bsp_pid() && k <= P*size+bsp_pid()-1)
      cond = 1;
    else
      cond = 0;

    set = 0;
    if (cond==1 && (k-bsp_pid())%size == 0)/*start of a reading*/
    {
      if (bsp_pid() == 0 && k < size);
      else if (bsp_pid() == 0)
      {
	bsp_get(size-1,&filepos,0,&filepos,sizeof(long int));
      }
      else
      {
	bsp_get(bsp_pid()-1,&filepos,0,&filepos,sizeof(long int));
      }
      set = 1;
    }

    bsp_sync();

    if (cond==1 && (k-bsp_pid())%size == 0)/*start of a reading*/
    {
      if (set == 1) fsetpos(f2,&filepos);
      for (i = 0; i < sizet; i++)
      {
	fscanf(f2,"%c",&t[i]);
	if (t[i] == 'A' ||t[i] == 'T' ||t[i] == 'C' ||t[i] == 'G');
	else
	{
	  if (t[i] == EOF) Exit("Error: End of file reached without"
			   "read all sequence in %s\n",argv[3]);
	  i--;
	}
      }
      fgetpos(f2,&filepos);
      for (i = 0; i <= sizet; i++)
	a[i] = (i+bsp_pid()*sizet)*gap;

    }

    if (cond==1)
    {
      if (bsp_pid() == 0)
      {
	for (i = 0; i < sizes; i++)
	{
	  fscanf(f,"%c",&s[i]);
	  if (s[i] == 'A' ||s[i] == 'T' ||s[i] == 'C' ||s[i] == 'G');
	  else
	  {
	  if (s[i] == EOF) Exit("Error: End of file reached without"
			   "read all sequence in %s\n",argv[2]);
	    i--;
	  }
	}
	for (j = 0; j <= sizes; j++)
	  b[j] = (j + (k%size)*sizes)*gap;
      }

      res = Similarity (s, sizes, t, sizet, a, b);

      if (bsp_pid() == size-1 && (k-bsp_pid()+1)%size == 0)
      {
	simi[Paux++] = res;
      }
    }
    if (cond)
      {
	if (bsp_pid() != size -1)
	{
	  bsp_put(bsp_pid()+1,s,s,0,sizes*sizeof(char));
	  bsp_put(bsp_pid()+1,b,b,0,(sizes+1)*sizeof(int));
	}
      }
    bsp_sync();
  }

  gettimeofday(&fi,&tz);

  printf("process %d ended\n",bsp_pid());

  fclose(f);
  fclose(f2);

  if (bsp_pid() == size-1)
  {
    printf("Similarities: ");
    for (i = 0; i < P; i++)
      printf("%d ",simi[i]);
    printf("\n");
  }
  if (bsp_pid() == 0)
  {
    printf("Computation time: %f\n", (fi.tv_sec - ini.tv_sec + (double)(fi.tv_usec -
ini.tv_usec)/1000000)/60);
  }

  bsp_pop_reg(&filepos);
  bsp_pop_reg(b);
  bsp_pop_reg(s);
  bsp_sync();

  return 0;
}
