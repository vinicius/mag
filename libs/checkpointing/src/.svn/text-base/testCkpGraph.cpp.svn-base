#include "CheckpointingLib.hpp"

#include <cstdio>
#include <cassert>
#include <cstdlib>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/time.h>

#include <iostream>

#define compareFloat(data,value,interval) ((value-interval) < data && data < (value+interval))

typedef long double node_t;
#define CKPT_NODE_T CKPT_LDOUBLE
#define N_NODES 500000 // 20000

struct timeval timeBegin ; 
struct timeval timeEnd ; 

extern int ckp_restoring;

int checkpoint_candidate() {
  if (ckp_restoring == 0)
    return 1;
  else
    return 0;
}

struct Node{
  node_t a;
  Node *node1;
  Node *node2;
};

void ckp_save_Node(void *data){
  Node *str = (Node *)data;
  ckp_push_data(&(str->a), sizeof(node_t), CKPT_NODE_T, 0);
  ckp_push_data(&(str->node1), sizeof(Node), 3000, &ckp_save_Node);
  ckp_push_data(&(str->node2), sizeof(Node), 3000, &ckp_save_Node);
}

void ckp_restore_Node(void *data){
  Node *str = (Node *)data;
  ckp_get_data(&(str->a), sizeof(node_t), CKPT_NODE_T, 0);
  ckp_get_data(&(str->node1), sizeof(Node), 3000, &ckp_restore_Node);
  ckp_get_data(&(str->node2), sizeof(Node), 3000, &ckp_restore_Node);
}

void testPointerGraph();

int main (int argc, char **argv) {

  std::cout << "Must fix the this test!!!!!" << std::endl;  
  return -1;

  std::cerr << "Starting..." << std::endl;

  //ckp_initialize_stack(1000000);
  set_ckp_store(-1, 0);
  
  if (argc == 2 && argv[1][0] == '-' && argv[1][1] == 'r') {
    ckp_restoring = 1;
    if (argv[1][2] != '\0') 
      ckp_restore_data(atoi(argv[1] + 2));
    else 
      ckp_restore_data(0);
  }
  
  int currentGotoLabel = -1;
  ckp_push_data(&currentGotoLabel, sizeof(int), 5, 0);
  if (ckp_restoring==1) {
    ckp_get_data(&currentGotoLabel, sizeof(int), 5, 0);
    if(currentGotoLabel == 0)
      goto ckp0;
  }
  
  assert(ckp_restoring == 0);
  
 ckp0:
  currentGotoLabel = 0;
  testPointerGraph (  ) ; 
  
  sleep(1);
  
  FILE * timeFile = fopen ( "time.dat" , "a" ) ;
  double bef  = ( double  ) timeBegin . tv_sec ;
  double befu = ( double  ) timeBegin . tv_usec ;
  double aft  = ( double  ) timeEnd . tv_sec ;
  double aftu = ( double  ) timeEnd . tv_usec ;
  double execTime = ( aft + aftu / 1000000. ) - ( bef + befu / 1000000. ) ;
  fprintf ( timeFile , "ckpTime=%-8.4f \n" , execTime) ;
  
  ckp_npop_data(9);
  return 0 ;
  
}

void testPointerGraph() {
  
  int currentGotoLabel = -1;
  ckp_push_data(&currentGotoLabel, sizeof(int), 5, 0);
  if (ckp_restoring==1)
    ckp_get_data(&currentGotoLabel, sizeof(int), 5, 0);
  printf ( "[testPointerGraph]\n" ) ; 
  
  Node *nodeVector[N_NODES];
  for (int i=0; i<N_NODES; i++)
    nodeVector[i] = (Node *)ckp_malloc(sizeof(Node));
  for (int i=0; i<N_NODES; i++) {
    nodeVector[i]->a = (node_t)i;
    nodeVector[i]->node1 = nodeVector[(i+1)%N_NODES];
    nodeVector[i]->node2 = nodeVector[rand()%N_NODES];
  }
  
  Node * node1 = nodeVector[0]; 
  ckp_push_data(&node1, sizeof(Node), 3000, &ckp_save_Node);
  if (ckp_restoring==1) {
    gettimeofday ( & timeBegin , NULL ) ; 
    ckp_get_data(&node1, sizeof(Node), 3000, &ckp_restore_Node);
    gettimeofday ( & timeEnd , NULL ) ; 
    
    if(currentGotoLabel == 0)
      goto ckp0;
  }

  printf ( "Restored Succesfuly!\n" ) ; 
  
  assert(ckp_restoring == 0);
  
 ckp0:
  currentGotoLabel = 0;
  if (ckp_restoring == 0) {
    int startCkp = checkpoint_candidate();
    gettimeofday ( & timeBegin , NULL ) ; 
    if (startCkp > 0) ckp_save_stack_data();
    gettimeofday ( & timeEnd , NULL ) ;    
  } 
  else ckp_restoring = 0;

  printf ( "Executing!\n" ) ; 
  
  Node * nodeTmp = node1;
  for (int i=0; i<N_NODES; i++) {
    assert(-0.01 < nodeTmp->a        && nodeTmp->a        < N_NODES);
    assert(-0.01 < nodeTmp->node1->a && nodeTmp->node1->a < N_NODES);
    assert(-0.01 < nodeTmp->node2->a && nodeTmp->node2->a < N_NODES);
    nodeTmp = nodeTmp->node1;
  }
  
  ckp_npop_data(4);
  
}
