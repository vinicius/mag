#include <stdio.h>

void main(int argc, char **argv)
{
  int i,j,lines,n;
  char c[4];


  c[0] = 'A';
  c[1] = 'C';
  c[2] = 'T';
  c[3] = 'G';


  lines = atoi(argv[1]);
  n = atoi(argv[2]);

  printf("%d\n",lines);


  for (j = 0; j < lines; j++)
    {
      for (i = 0; i < n; i++)
	printf("%c",c[rand()%4]);
      printf("\n\n");
    }


}
