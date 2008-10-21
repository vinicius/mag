#ifndef _IDADEFINITIONS_H_
#define _IDADEFINITIONS_H_

typedef unsigned char enc_t;
//extern int p;

#define FIELD_EXP 8 // 2^8
#define FIELD_SIZE 256
#define IRREDUCILBE_POLYNOMIAL 285 // x^8 + x^4 + x^3 + x^2 + 1 (100011101)

//#define FIELD_EXP 3
//#define FIELD_SIZE 8
//#define IRREDUCILBE_POLYNOMIAL 11

extern enc_t *mulTable;
extern enc_t *mulList;
extern enc_t *iMulList;

//#define psum(x,y) (x+y)%257 
//#define pmul(x,y) (x*y)%257
//#define pmul1(x,y) (x*y)%257
//#define pgetmul(mul1,res) iMulList[mulList[res]^mulList[mul1]]

// sums two numbers
#define psum(x,y) (x^y) 

// multiplies two numbers
#define pmul(x,y) mulTable[x*FIELD_SIZE+y] 

// not in use
#define pmul1(x,y) iMulList[(mulList[x]+mulList[y])%(FIELD_SIZE-1)] 

// returns the number which, multiplied by mul1, results in res
#define pgetmul(mul1,res) iMulList[( mulList[res]+((FIELD_SIZE-1)-mulList[mul1]) )%(FIELD_SIZE-1)]

#endif
