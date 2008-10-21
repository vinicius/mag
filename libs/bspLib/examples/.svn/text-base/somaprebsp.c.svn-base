// Programa: somapre.c -- version BSPww - example
// Programador: Edson
// Data: 26/01/2004
// O Dialogo: Este programa le um um vetor de elementos e um
// conjunto de p tarefas. Envia para cada tarefa um subvetor de
// tamanho TAMMAX/p. Cada tarefa i efetua a soma de seu subvetor e envia a
// resposta para as tarefas j>=i. A tarefa i recebe as somas parciais das
// tarefas k <= i e calcula a soma de prefixos dos seus elementos, relativo
// ao vetor de entrada.
// Declaracao das bibliotecas utilizadas
#include "BspLib.hpp"
#include<stdio.h> // printf, fprintf, fscanf
#include<stdlib.h> // printf, fprintf, fscanf

// Declaracao das funcoes
int LeNum(void);
void LeDados(int *);
void ImprimeVetor(const int *, int, int);
void EnviaValor(int * const, int * const, int);
void EnviaDados(int *, int, int *, int, int);
void CalculaSoma(int *, int, int * const);
void CalculaSomaEsq(int, int, int, int * const);
void CalcSomaPre(int *, int, int, int *);
void EnviaResult(int *, int, int, int, int *);
void EscreveResult(int *, int, int);

// inicio da funcao principal
//------------------------------------------------------------------------------------
int main(int argc, char *argv[])
{
// declaracao das variaveis locais
   int Soma = 0, SomaP = 0, rank, size, tam;
   int numelem;
   int *SubVetor, *SPre;
   int *VetorDados; // vetor dados
   int *SomaPre;
   int root=0;

// Passo 1. Inicilizacao
//   bsplib_saveargs(&argc, &argv);
   bsp_begin(0); // iniciliza o pub
   size = bsp_nprocs(); // numero de tarefas
   rank = bsp_pid(); // identificacao da tarefa
// Passo 2. Processador raiz le os dados
   if (rank == 0) {
// Passo 2.1. Leia o numero de elementos
      numelem = LeNum();
// Passo 2.2. Dimensione o vetor de entrada
      VetorDados = (int *)malloc(numelem*sizeof(int));
// Passo 2.3. Leia os elementos
      LeDados(VetorDados);
// Passo P.2. Imprima o vetor
//      ImprimeVetor(VetorDados, numelem, rank);
   }
// Passo 3. Envie os dados as tarefas filhos
// Passo 3.1. Envie numelem para as demais tarefas
   EnviaValor(&numelem, &tam, size);
// Passo 3.2. Dimensione o vetor de recebimento
   SubVetor = (int *)malloc(tam*sizeof(int));
// Passo 3.3. Distribua os dados entre as tarefas
   EnviaDados(VetorDados, numelem, SubVetor, tam, rank);
// Passo P.4. Imprima o vetor
//   ImprimeVetor(SubVetor, tam, rank);
// Passo 4. Libere o espaco do vetor Vetor Dados
   if (rank == 0) {
      free(VetorDados);
   }
// Passo 4. Calcule a Soma em cada tarefa
   CalculaSoma(SubVetor, tam, &SomaP);
// Passo 5. Receba as Somas das tarefas com pid < rank
   CalculaSomaEsq(SomaP, rank, size, &Soma);
// Passo 6. Calcule as Somas de Prefixos
// Passo 6.1. Dimensione o vetor
   SPre = (int *)malloc(tam*sizeof(int));
// Passo 6.2. Calcule as Somas de Prefixos em cada tarefa
   CalcSomaPre(SubVetor, Soma, tam, SPre);
// Passo 6.3. Libere o espaco de SubVetor
   free(SubVetor);
// Passo 7. Envie os valores para a raiz
   if (rank == 0) {
// Passo 7.1. Dimensione o vetor SomaPre
      SomaPre = (int *)malloc(numelem*sizeof(int));
   }
// Passo 7.2. Envie as soma de prefixos para a raiz
   EnviaResult(SPre, tam, rank, numelem, SomaPre);
   free(SPre);
// Passo 8. Armazene o resultado
   if (rank == root) {
// Passo 8.1. Escreva os resulltados num arquivo
      EscreveResult(SomaPre, numelem, size);
// Passo P.8. Imprima os resultados
//      ImprimeVetor(SomaPre, numelem, rank);
// Passo 8.2. Libere o espaco do vetor SomaPre
      free(SomaPre);
   }

// Passo 9. Finalize o BSP-pub
//   bsp_end();
   return 0;
} // fim funcao main

//------------------------------------------------------------------------------------
int LeNum(void) {
// Declaracao da variaveis locais
   int num;
   FILE *ArqA;

// Passo LN 1. Abra o arquivo de Entrada
   ArqA = fopen("sequencia.txt", "r");
// Passo LN 2. Leia o numero de elementos de sequencia.txt
   fscanf(ArqA,"%i", &num);
// Passo LN 3. Feche o arquivo de entrada
   fclose(ArqA);
// Passo LN 4. Retorne o numero de elementos
   return num;
} // fim da funcao LeNum

//------------------------------------------------------------------------------------
void LeDados(int *VetorDados) {
// Declaracao da variaveis locais
   int i;
   int num;
   FILE *ArqA;

// Passo LD 1. Abra o arquivo de Entrada
   ArqA = fopen("sequencia.txt", "r");
// Passo LD 2. Leia o numero de elementos de sequencia.txt
   fscanf(ArqA,"%i", &num);
// Passo LD 3. Leia a entrada e armazene em VetorDados
   for (i = 0; i < num; i++) {
      fscanf(ArqA,"%i", &VetorDados[i]);
   }
   fclose(ArqA);
} // fim da funcao LeDados

//------------------------------------------------------------------------------------
void ImprimeVetor(const int *Vetor, int num, int rank){
// Declaracao das variaveis locais
   int i;

// Passo IV.1. Imprima o vetor
   for (i = 0; i < num; i++) {
      printf("rank %d Vetor[%d]=%d\n", rank, i, Vetor[i]);
   }
} // fim da funcao ImprimeVetor

//------------------------------------------------------------------------------------
void EnviaValor(int * const numelem, int * const tam, int size){

// Passo EV 1. Compartilhe a variavel numelem
   bsp_push_reg(&(*numelem), sizeof(int));
   bsp_sync();
// Passo EV 2. Leia a variavel numelem na tarefa 0
   bsp_get(0, &(*numelem), 0, &(*numelem), sizeof(int));
// Passo EV 3. Sincronize
   bsp_sync();
// Passo EV 4. Remova o compartilhamento da variavel numelem
   bsp_pop_reg(&(*numelem));
   bsp_sync();
// Passo EV 5. Calcule o numero de elementos de cada tarefa
   *tam = (int) (*numelem)/size;
} // fim da funcao EnviaValor

//------------------------------------------------------------------------------------
void EnviaDados(int *VetorDados, int numelem, int *SubVetor, int tam, int rank){

// Passo ED 1. Compartilhe a variavel VetorDados
   bsp_push_reg(VetorDados, numelem*sizeof(int));
   bsp_sync();
// Passo ED 2. Leia tam elementos da variavel VetorDados na tarefa 0
   bsp_get(0, VetorDados, rank*tam*sizeof(int), SubVetor, tam*sizeof(int));
// Passo ED 3. Sincronize
   bsp_sync();
// Passo ED 4. Remova o compartilhamento da variavel VetorDados
   bsp_pop_reg(VetorDados);
   bsp_sync();
} // fim da funcao EnviaDados

//------------------------------------------------------------------------------------
void CalculaSoma(int *SubVetor, int tam, int * const SomaP){
// Declaracao das variaveis locais
   int i;

// Passo CS 1. Calcule a Soma dos elementos da tarefa
   for (i = 0; i < tam; i++)
      *SomaP = *SomaP + SubVetor[i];
} // fim da funcao CalculaSoma

//------------------------------------------------------------------------------------
void CalculaSomaEsq(int SomaP, int rank, int size, int * const Soma){
// Declaracao das variaveis locais
   int i;
   int soma;

// Passo CSE 1. Compute o valor das somas das tarefas com pid < rank
// Passo CSE 1.1. Armazene o valor da soma da tarefa na variavel soma
   soma = SomaP;
// Passo CSE 1.2. Para cada tarefa i
   for (i = 0; i < size-1; i++) {
// Passo CSE 1.2.1. Compartilhe a variavel soma
      bsp_push_reg(&soma, sizeof(int));
      bsp_sync();
// Passo CSE 1.2.2. Se rank > indice da tarefa
      if (rank > i) {
// Passo CSE 1.2.2.1. Leia  a variavel soma na tarefa i
         bsp_get(i, &soma, 0, &soma, sizeof(int));
      }
// Passo CSE 1.2.3. Sincronize
      bsp_sync();
// Passo CSE 1.2.4. Se rank > indice da tarefa
      if (rank > i) {
// Passo CSE 1.2.4.1. Acrescente o valor de soma a *Soma
         *Soma = *Soma + soma;
      }
// Passo CSE 1.2.5 Remova o compartilhamento da variavel VetorDados
      bsp_pop_reg(&soma);
      bsp_sync();
// Passo CSE 1.2.6. Armazene o valor da soma da tarefa na variavel soma
      soma = SomaP;
   } // fim for
} // fim funcao CalculaSomaEsq

//------------------------------------------------------------------------------------
void CalcSomaPre(int *SubVetor, int Soma, int tam, int *SPre){
// Declaracao das variaveis locais
   int i;

// Passo CSP 1. Calucule a soma de prefixos global para cada elemento da tarefa
   SPre[0] = Soma + SubVetor[0];
   for (i = 1; i < tam; i++)
      SPre[i] = SPre[i-1] + SubVetor[i];
} // fim da funcao CalcSomaPre

//------------------------------------------------------------------------------------
void EnviaResult(int *SPre, int tam, int rank, int numelem, int *SomaPre){

// Passo ER 1. Compartilhe a variavel SomaPre
   bsp_push_reg(SomaPre, numelem*sizeof(int));
   bsp_sync();
// Passo ER 2 Escreva tam elementos (Spre) na variavel SomaPre da tarefa 0
   bsp_put(0, SPre, SomaPre, tam*rank*sizeof(int), tam*sizeof(int));
// Passo ER 3. Sincronize
   bsp_sync();
// Passo ER 4. Remova o compartilhamento da variavel VetorDados
   bsp_pop_reg(SomaPre);
   bsp_sync();
} // fim da funcao EnviaResult

//------------------------------------------------------------------------------------
void EscreveResult(int *SomaPre, int numelem, int size){
// Declaracao das variaveis locais
   int i;
   FILE *ArqS;

// Passo ERe 1. Abre o arquivo
   ArqS = fopen("ArquivoS.txt", "a");
// Passo ERe 2. Escreve no arquivo
   fprintf(ArqS, "\n%i %i\n", numelem, size);
   for (i = 0; i < numelem; i++) {
      fprintf(ArqS, "%d ", SomaPre[i]);
   }
// Passo ERe 3. Feche o arquivo
   fclose(ArqS);
} // fim da funcao EscreveResult
