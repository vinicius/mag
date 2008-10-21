#include "CheckpointingLib.hpp"

#include <cstdio>
#include <cassert>
#include <cstdlib>
#include <unistd.h>
#include <sys/wait.h>

#include <iostream>

#define compareFloat(data,value,interval) ((value-interval) < data && data < (value+interval))

extern int ckp_restoring;

int checkpoint_candidate() {
  if (ckp_restoring == 0)
    return 1;
  else
    return 0;
}



class abc{
  double a;
  int b;
public:
  abc(){}
  ~abc(){}
  int teste (void) {return 0;}
};

struct Point {
  int x;
  int y;
  int z;
};
void ckp_save_Point(void *data){
 Point *str = (Point *)data;
   ckp_push_data(&(str->x), sizeof(int), 5, 0);
   ckp_push_data(&(str->y), sizeof(int), 5, 0);
   ckp_push_data(&(str->z), sizeof(int), 5, 0);
 }
 void ckp_restore_Point(void *data){
   Point *str = (Point *)data;
   ckp_get_data(&(str->x), sizeof(int), 5, 0);
   ckp_get_data(&(str->y), sizeof(int), 5, 0);
   ckp_get_data(&(str->z), sizeof(int), 5, 0);
 }


struct BuiltInData {
  bool sBool;
  char sChar;

  int sInt;
  long sLong1;
  long sLong2;
  long sLong3;
  float sFloat;
  double sDouble;
  long double sLDouble;
  unsigned char uChar;
  unsigned int uInt;
  unsigned long uLong;

};
void ckp_save_BuiltInData(void *data){
 BuiltInData *str = (BuiltInData *)data;
   ckp_push_data(&(str->sBool), sizeof(bool), 1, 0);
   ckp_push_data(&(str->sChar), sizeof(char), 2, 0);
   ckp_push_data(&(str->sInt), sizeof(int), 5, 0);
   ckp_push_data(&(str->sLong1), sizeof(long), 6, 0);
   ckp_push_data(&(str->sLong2), sizeof(long), 6, 0);
   ckp_push_data(&(str->sLong3), sizeof(long), 6, 0);
   ckp_push_data(&(str->sFloat), sizeof(float), 7, 0);
   ckp_push_data(&(str->sDouble), sizeof(double), 8, 0);
   ckp_push_data(&(str->sLDouble), sizeof(long double), 9, 0);
   ckp_push_data(&(str->uChar), sizeof(unsigned char), 2, 0);
   ckp_push_data(&(str->uInt), sizeof(unsigned int), 5, 0);
   ckp_push_data(&(str->uLong), sizeof(unsigned long), 6, 0);
 }
 void ckp_restore_BuiltInData(void *data){
   BuiltInData *str = (BuiltInData *)data;
   ckp_get_data(&(str->sBool), sizeof(bool), 1, 0);
   ckp_get_data(&(str->sChar), sizeof(char), 2, 0);
   ckp_get_data(&(str->sInt), sizeof(int), 5, 0);
   ckp_get_data(&(str->sLong1), sizeof(long), 6, 0);
   ckp_get_data(&(str->sLong2), sizeof(long), 6, 0);
   ckp_get_data(&(str->sLong3), sizeof(long), 6, 0);
   ckp_get_data(&(str->sFloat), sizeof(float), 7, 0);
   ckp_get_data(&(str->sDouble), sizeof(double), 8, 0);
   ckp_get_data(&(str->sLDouble), sizeof(long double), 9, 0);
   ckp_get_data(&(str->uChar), sizeof(unsigned char), 2, 0);
   ckp_get_data(&(str->uInt), sizeof(unsigned int), 5, 0);
   ckp_get_data(&(str->uLong), sizeof(unsigned long), 6, 0);
 }


struct Node{
  long double a;
  int b;
  char c;
  char *pointer;
  Point point;
  Node *node;
};
void ckp_save_Node(void *data){
 Node *str = (Node *)data;
   ckp_push_data(&(str->a), sizeof(long double), 9, 0);
   ckp_push_data(&(str->b), sizeof(int), 5, 0);
   ckp_push_data(&(str->c), sizeof(char), 2, 0);
   ckp_push_data(&(str->pointer), sizeof(char), 1002, 0);
   ckp_push_data(&(str->point), sizeof(Point), 2000, &ckp_save_Point);
   ckp_push_data(&(str->node), sizeof(Node), 3000, &ckp_save_Node);
 }
 void ckp_restore_Node(void *data){
   Node *str = (Node *)data;
   ckp_get_data(&(str->a), sizeof(long double), 9, 0);
   ckp_get_data(&(str->b), sizeof(int), 5, 0);
   ckp_get_data(&(str->c), sizeof(char), 2, 0);
   ckp_get_data(&(str->pointer), sizeof(char), 1002, 0);
   ckp_get_data(&(str->point), sizeof(Point), 2000, &ckp_restore_Point);
   ckp_get_data(&(str->node), sizeof(Node), 3000, &ckp_restore_Node);
 }



struct SimpleNode{
  int value;
  SimpleNode *nextA;
  SimpleNode *nextB;
};

void ckp_save_SimpleNode(void *data){
 SimpleNode *str = (SimpleNode *)data;
   ckp_push_data(&(str->value), sizeof(int), 5, 0);
   ckp_push_data(&(str->nextA), sizeof(SimpleNode), 3000, &ckp_save_SimpleNode);
   ckp_push_data(&(str->nextB), sizeof(SimpleNode), 3000, &ckp_save_SimpleNode);
 }
 void ckp_restore_SimpleNode(void *data){
   SimpleNode *str = (SimpleNode *)data;
   ckp_get_data(&(str->value), sizeof(int), 5, 0);
   ckp_get_data(&(str->nextA), sizeof(SimpleNode), 3000, &ckp_restore_SimpleNode);
   ckp_get_data(&(str->nextB), sizeof(SimpleNode), 3000, &ckp_restore_SimpleNode);
 }



int globalVar = 1;

void testPointerGraph();
void testArray();
void testBuiltIn();
void testPointerArray();
void testStructure();
void testStructurePointer();



int main (int argc, char **argv) {


  std::cout << "Must fix the this test!!!!!" << std::endl;  
  return -1;

//  ckp_initialize_stack(1000);
  set_ckp_store(-30, 0);

 if (argc == 2 && argv[1][0] == '-' && argv[1][1] == 'r') {
   ckp_restoring = 1;
   if (argv[1][2] != '\0') 
     ckp_restore_data(atoi(argv[1] + 2));
   else 
     ckp_restore_data(0);
 }

 ckp_push_data(&globalVar, sizeof(int), 55, 0);
 if (ckp_restoring==1) {
   ckp_get_data(&globalVar, sizeof(int), 55, 0);
 } 
 int currentGotoLabel = -1;
 ckp_push_data(&currentGotoLabel, sizeof(int), 5, 0);
 if (ckp_restoring==1)
   ckp_get_data(&currentGotoLabel, sizeof(int), 5, 0);
 ckp_push_data(&argv, sizeof(char), 1102, 0);
 ckp_push_data(&argc, sizeof(int), 5, 0);
  int mainInt , mainInt2 = 5 ; 
 ckp_push_data(&mainInt, sizeof(int), 5, 0);
 ckp_push_data(&mainInt2, sizeof(int), 5, 0);
  int * pointerInt ; 
 ckp_push_data(&pointerInt, sizeof(int), 1005, 0);
  int * localPointerInt ; 
 ckp_push_data(&localPointerInt, sizeof(int), 1005, 0);
  double mainDouble ; 
 ckp_push_data(&mainDouble, sizeof(double), 8, 0);
  double * localPointerDouble ; 
 ckp_push_data(&localPointerDouble, sizeof(double), 1008, 0);
 if (ckp_restoring==1) {
   ckp_get_data(&argv, sizeof(char), 1102, 0);
   ckp_get_data(&argc, sizeof(int), 5, 0);
   ckp_get_data(&mainInt, sizeof(int), 5, 0);
   ckp_get_data(&mainInt2, sizeof(int), 5, 0);
   ckp_get_data(&pointerInt, sizeof(int), 1005, 0);
   ckp_get_data(&localPointerInt, sizeof(int), 1005, 0);
   ckp_get_data(&mainDouble, sizeof(double), 8, 0);
   ckp_get_data(&localPointerDouble, sizeof(double), 1008, 0);
   if(currentGotoLabel == 0)
     goto ckp0;
   if(currentGotoLabel == 1)
     goto ckp1;
   if(currentGotoLabel == 2)
     goto ckp2;
   if(currentGotoLabel == 3)
     goto ckp3;
   if(currentGotoLabel == 4)
     goto ckp4;
 }

 assert(ckp_restoring == 0);

 mainInt = 1 ; 
 mainDouble = 2.675 ; 
 pointerInt = ( int * ) ckp_malloc ( sizeof ( int  ) ) ; 
 * pointerInt = 2 ; 
 localPointerInt = & mainInt ; 
 localPointerDouble = & mainDouble ; 
 ckp0:
 currentGotoLabel = 0;
 testPointerGraph (  ) ; 
  fflush ( 0 ) ; 
 ckp1:
 currentGotoLabel = 1;
 testBuiltIn (  ) ; 
  fflush ( 0 ) ; 
 ckp2:
 currentGotoLabel = 2;
 testPointerArray (  ) ; 
  fflush ( 0 ) ; 
 ckp3:
 currentGotoLabel = 3;
 testStructure (  ) ; 
  fflush ( 0 ) ; 
 ckp4:
 currentGotoLabel = 4;
 testStructurePointer (  ) ; 

  assert(mainInt  == (int)1 );
  assert(mainInt2 == (int)5 );
  assert(*pointerInt  == (int)2 );
  assert(*localPointerInt == mainInt );
  assert(localPointerInt == &mainInt );
  assert(mainDouble == (double)2.675 );
  assert(*localPointerDouble == mainDouble );
  assert(localPointerDouble == &mainDouble );

 sleep(1);

 {ckp_npop_data(9);  return 0 ; }
 
 ckp_npop_data(9);


}



void testPointerGraph() {

  

int currentGotoLabel = -1;
 ckp_push_data(&currentGotoLabel, sizeof(int), 5, 0);
 if (ckp_restoring==1)
   ckp_get_data(&currentGotoLabel, sizeof(int), 5, 0);
 printf ( "[testPointerGraph]\n" ) ; 
  SimpleNode node1 ; 
 ckp_push_data(&node1, sizeof(SimpleNode), 2000, &ckp_save_SimpleNode);
  SimpleNode * node2 ; 
 ckp_push_data(&node2, sizeof(SimpleNode), 3000, &ckp_save_SimpleNode);
 if (ckp_restoring==1) {
   ckp_get_data(&node1, sizeof(SimpleNode), 2000, &ckp_restore_SimpleNode);
   ckp_get_data(&node2, sizeof(SimpleNode), 3000, &ckp_restore_SimpleNode);
   if(currentGotoLabel == 0)
     goto ckp0;
 }

  assert(ckp_restoring == 0);

 node2 = ( SimpleNode * ) ckp_malloc ( sizeof ( Node  ) ) ; 
 node1 . value = 1 ; 
 node2 -> value = 2 ; 
 node1 . nextA = node2 ; 
 node1 . nextB = __null ; 
 node2 -> nextA = ( SimpleNode * ) ckp_malloc ( sizeof ( Node  ) ) ; 
 node2 -> nextA -> value = 3 ; 
 node2 -> nextB = & node1 ; 
 node2 -> nextA -> nextA = node2 -> nextA ; 
 node2 -> nextA -> nextB = & node1 ; 
 ckp0:
 currentGotoLabel = 0;
 if (ckp_restoring == 0) {
   int startCkp = checkpoint_candidate();
   if (startCkp > 0) ckp_save_stack_data();
 } 
 else ckp_restoring = 0;
 

  assert(node1.value == 1);
  assert(node2->value == 2);
  assert(node2->nextA->value == 3);  
  assert(node1.nextA == node2);
  assert(node1.nextB == NULL);
  assert(node2->nextA == node2->nextA);
  assert(node2->nextB == &node1);
  assert(node2->nextA->nextA == node2->nextA);
  assert(node2->nextA->nextB == &node1);
  assert(node1.nextA->value == 2);
  assert(node2->nextA->value == 3);
  assert(node2->nextB->value == 1);    
  assert(node2->nextA->nextA->value == 3);
  assert(node2->nextA->nextB->value == 1);    

 ckp_npop_data(5);




}



void testStructurePointer() {



  

int currentGotoLabel = -1;
 ckp_push_data(&currentGotoLabel, sizeof(int), 5, 0);
 if (ckp_restoring==1)
   ckp_get_data(&currentGotoLabel, sizeof(int), 5, 0);
  Node * node ; 
 ckp_push_data(&node, sizeof(Node), 3000, &ckp_save_Node);
 if (ckp_restoring==1) {
   ckp_get_data(&node, sizeof(Node), 3000, &ckp_restore_Node);
   if(currentGotoLabel == 0)
     goto ckp0;
 }

  assert(ckp_restoring == 0);

 node = ( Node * ) ckp_malloc ( sizeof ( Node  ) ) ; 
 node -> a = 2.1 ; 
 node -> b = 2 ; 
 node -> c = 'a' ; 
 node -> pointer = ( char * ) ckp_malloc ( 2 * sizeof ( char  ) ) ; 
 node -> pointer [ 0 ] = 'a' ; 
 node -> pointer [ 1 ] = 'b' ; 
 node -> point . x = 1 ; 
 node -> point . y = 2 ; 
 node -> point . z = 4 ; 
 node -> node = __null ; 
 ckp0:
 currentGotoLabel = 0;
 if (ckp_restoring == 0) {
   int startCkp = checkpoint_candidate();
   if (startCkp > 0) ckp_save_stack_data();
 } 
 else ckp_restoring = 0;

  assert(node->node == NULL);
  assert(node->point.x + node->point.y + node->point.z == 7);  
  assert(node->pointer[0] == 'a');  
  assert(node->pointer[1] == 'b');  
  assert(node->c == 'a' );  
  assert(node->b == (int)2 );
  assert(node->a == (double)2.1 );

 ckp_npop_data(2);




}



void testStructure() {

  

int currentGotoLabel = -1;
 ckp_push_data(&currentGotoLabel, sizeof(int), 5, 0);
 if (ckp_restoring==1)
   ckp_get_data(&currentGotoLabel, sizeof(int), 5, 0);
 printf ( "[testStructure]\n" ) ; 
  Node node ; 
 ckp_push_data(&node, sizeof(Node), 2000, &ckp_save_Node);
 if (ckp_restoring==1) {
   ckp_get_data(&node, sizeof(Node), 2000, &ckp_restore_Node);
   if(currentGotoLabel == 0)
     goto ckp0;
 }

  assert(ckp_restoring == 0);

 node . a = 2.1 ; 
 node . b = 2 ; 
 node . c = 'a' ; 
 node . pointer = ( char * ) ckp_malloc ( 3 * sizeof ( char  ) ) ; 
 node . pointer [ 0 ] = 'a' ; 
 node . pointer [ 1 ] = 'b' ; 
 node . pointer [ 2 ] = 'c' ; 
 node . point . x = 1 ; 
 node . point . y = 2 ; 
 node . point . z = 4 ; 
 node . node = __null ; 
 ckp0:
 currentGotoLabel = 0;
 if (ckp_restoring == 0) {
   int startCkp = checkpoint_candidate();
   if (startCkp > 0) ckp_save_stack_data();
 } 
 else ckp_restoring = 0;
 

  assert(node.node == NULL);
  assert(node.point.x + node.point.y + node.point.z == 7);  
  assert(node.pointer[0] == 'a');  
  assert(node.pointer[1] == 'b');  
  assert(node.pointer[2] == 'c');    
  assert(node.c == 'a' );  
  assert(node.b == (int)2 );
  assert(node.a == (double)2.1 );

 ckp_npop_data(9);



}



void testBuiltIn() {

  

int currentGotoLabel = -1;
 ckp_push_data(&currentGotoLabel, sizeof(int), 5, 0);
 if (ckp_restoring==1)
   ckp_get_data(&currentGotoLabel, sizeof(int), 5, 0);
 printf ( "[testBuiltIn]\n" ) ; 
  BuiltInData data ; 
 ckp_push_data(&data, sizeof(BuiltInData), 2000, &ckp_save_BuiltInData);
 if (ckp_restoring==1) {
   ckp_get_data(&data, sizeof(BuiltInData), 2000, &ckp_restore_BuiltInData);
   if(currentGotoLabel == 0)
     goto ckp0;
 }
 data . sBool = true ; 
 data . sChar = 'a' ; 
 data . sInt = 10 ; 
 data . sLong1 = 111 ; 
 data . sLong2 = 0 ; 
 data . sLong3 = - 111 ; 
 data . sFloat = 123.45 ; 
 data . sDouble = 987.654 ; 
 data . sLDouble = 567.1234 ; 
 data . uChar = 'a' ; 
 data . uInt = 100 ; 
 data . uLong = 1110 ; 


  assert(ckp_restoring == 0);

 ckp0:
 currentGotoLabel = 0;
 if (ckp_restoring == 0) {
   int startCkp = checkpoint_candidate();
   if (startCkp > 0) ckp_save_stack_data();
 } 
 else ckp_restoring = 0;
 
  printf ( "%f\n" , data . sFloat ) ; 

  assert(data.sBool    == true       );
  assert(data.sChar    == 'a'        );
//assert(data.sWChar   == 'a'        );
  assert(data.sInt     == 10         );
  assert(data.sLong1   == 111        );
  assert(data.sLong2   == 0          );
  assert(data.sLong3   == -111       );
  assert(compareFloat(data.sFloat,   123.45,   0.001));
  assert(compareFloat(data.sDouble,  987.654,  0.0001));
  assert(compareFloat(data.sLDouble, 567.1234, 0.00001));
  assert(data.uChar    == 'a'        );
  assert(data.uInt     == 100        );
  assert(data.uLong    == 1110       );

 ckp_npop_data(13);




}




void testArray() {

}




void testPointerArray() {

  

int currentGotoLabel = -1;
 ckp_push_data(&currentGotoLabel, sizeof(int), 5, 0);
 if (ckp_restoring==1)
   ckp_get_data(&currentGotoLabel, sizeof(int), 5, 0);
 printf ( "[testPointerArray]\n" ) ; 
  int * pointerArray ; 
 ckp_push_data(&pointerArray, sizeof(int), 1005, 0);
  int * * pointerMatrix ; 
 ckp_push_data(&pointerMatrix, sizeof(int), 1105, 0);
  int * * * pointer3DMatrix ; 
 ckp_push_data(&pointer3DMatrix, sizeof(int), 1205, 0);
  int * pointerArrayCopy ; 
 ckp_push_data(&pointerArrayCopy, sizeof(int), 1005, 0);
  int * nullPointerArray ; 
 ckp_push_data(&nullPointerArray, sizeof(int), 1005, 0);
  int * invalidPointerArray ; 
 ckp_push_data(&invalidPointerArray, sizeof(int), 1005, 0);
 if (ckp_restoring==1) {
   ckp_get_data(&pointerArray, sizeof(int), 1005, 0);
   ckp_get_data(&pointerMatrix, sizeof(int), 1105, 0);
   ckp_get_data(&pointer3DMatrix, sizeof(int), 1205, 0);
   ckp_get_data(&pointerArrayCopy, sizeof(int), 1005, 0);
   ckp_get_data(&nullPointerArray, sizeof(int), 1005, 0);
   ckp_get_data(&invalidPointerArray, sizeof(int), 1005, 0);
   if(currentGotoLabel == 0)
     goto ckp0;
 }

  assert(ckp_restoring == 0);

 nullPointerArray = __null ; 
 pointerArray = ( int * ) ckp_malloc ( 4 * sizeof ( int  ) ) ; 
 pointerArray [ 0 ] = 10 ; 
 pointerArray [ 1 ] = 20 ; 
 pointerArray [ 2 ] = 30 ; 
 pointerArray [ 3 ] = 40 ; 
 pointerMatrix = ( int * * ) ckp_malloc ( 2 * sizeof ( int * ) ) ; 
 pointerMatrix [ 0 ] = pointerArray ; 
 pointerMatrix [ 1 ] = ( int * ) ckp_malloc ( 3 * sizeof ( int  ) ) ; 
 pointerMatrix [ 1 ] [ 0 ] = 110 ; 
 pointerMatrix [ 1 ] [ 1 ] = 120 ; 
 pointerMatrix [ 1 ] [ 2 ] = 130 ; 
 pointer3DMatrix = ( int * * * ) ckp_malloc ( 2 * sizeof ( int * * ) ) ; 
 pointer3DMatrix [ 0 ] = pointerMatrix ; 
 pointer3DMatrix [ 1 ] = ( int * * ) ckp_malloc ( sizeof ( int * ) ) ; 
 pointer3DMatrix [ 1 ] [ 0 ] = ( int * ) ckp_malloc ( 2 * sizeof ( int  ) ) ; 
 pointer3DMatrix [ 1 ] [ 0 ] [ 0 ] = 123 ; 
 pointer3DMatrix [ 1 ] [ 0 ] [ 1 ] = 456 ; 
 pointerArrayCopy = pointerArray ; 
 ckp0:
 currentGotoLabel = 0;
 if (ckp_restoring == 0) {
   int startCkp = checkpoint_candidate();
   if (startCkp > 0) ckp_save_stack_data();
 } 
 else ckp_restoring = 0;

  assert(nullPointerArray    == NULL          );
  assert(pointerArray[0]     == (int)10       );
  assert(pointerArray[1]     == (int)20       );
  assert(pointerArray[2]     == (int)30       );
  assert(pointerArray[3]     == (int)40       );
  assert(pointerMatrix[0][1] == (int)20       );
  assert(pointerMatrix[1][0] == (int)110      );  
  assert(pointerMatrix[1][1] == (int)120      );  
  assert(pointerMatrix[1][2] == (int)130      );      
  assert(pointerMatrix[0]    == pointerArray  );
  assert(pointerArrayCopy    == pointerArray  );
  assert(pointerArrayCopy[1] == (int)20       );

  assert(pointer3DMatrix[0]  == pointerMatrix );
  assert(pointer3DMatrix[0][0][1] ==  20 );
  assert(pointer3DMatrix[0][1][2] == 130 );
  assert(pointer3DMatrix[1][0][0] == 123 );
  assert(pointer3DMatrix[1][0][1] == 456 );
 

 ckp_free ( pointerArray ) ; 
 ckp_free ( pointerMatrix [ 1 ] ) ; 
 ckp_free ( pointerMatrix ) ; 
 ckp_npop_data(7);

}


