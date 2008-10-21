#include "IDAAuxVectors.h"

#include <iostream>
#include <iomanip>
#include <cassert>
using namespace std;

IDAAuxVectors::IDAAuxVectors()
{
    auxVectors = 0;
}

IDAAuxVectors::~IDAAuxVectors()
{
}

//=============================================================================

enc_t **IDAAuxVectors::calculateInverse(int *slices, int nSlices) {

    return calculateInverseJordan(slices, nSlices);
}

enc_t **IDAAuxVectors::generateIdentityG(int nSlices, int nExtra) {
   
    generateVectors(nSlices+nExtra, nSlices);
   
    /** Apply the Gauss Jordan algorithm */
    for (int col=0; col<nSlices; col++) { // Iterates for each column
        /** makes [*][i] == 0 for all rows except i */
        for (int row=0; row<nSlices; row++) {
            if (row==col || auxVectors[col][row]==0) continue;
            enc_t mul = pgetmul(auxVectors[col][row], auxVectors[col][col]);
            for (int pos=0; pos<nSlices+nExtra; pos++) {                               
                auxVectors[pos][row] = psum( pmul(auxVectors[pos][row],mul), auxVectors[pos][col]);  
            }                                
        }
    }

    // Normalize the generated vector
    for (int line=0; line<nSlices; line++) { // Iterates for each column    
        enc_t mul = pgetmul(auxVectors[line][line], 1);
        for (int col=0; col<nSlices+nExtra; col++) {            
            auxVectors[col][line] = pmul(auxVectors[col][line],mul);
        }
    }

    return auxVectors;
    
}

enc_t **IDAAuxVectors::calculateInverseJordan(int *slices, int nSlices) {
   
    /** Initializes the matrix */
    enc_t **inverseM = new enc_t *[nSlices*2];
    for (int i=0; i<nSlices; i++) {
        inverseM[i] = new enc_t[nSlices];
        inverseM[i+nSlices] = new enc_t[nSlices];
        for (int j=0; j<nSlices; j++) {
            inverseM[i][j] = auxVectors[slices[i]][j];
            inverseM[i+nSlices][j] = ((j==i) ? 1 : 0);                
        }
    }

    /** Removes zero elements from diagonals */
    for (int dia=0; dia<nSlices; dia++) // Iterates for each column        
        if (inverseM[dia][dia]==0)
            for (int row=0; row<nSlices; row++)             
                if (inverseM[dia][row] != 0)
                    for (int pos=0; pos<2*nSlices; pos++)
                        inverseM[pos][dia] = psum( inverseM[pos][dia], inverseM[pos][row] );  

                
    /** Apply the Gauss Jordan algorithm 
     * 
     *  Iterates for each column, transforming all elements, except the diagonal one,
     *  in zeros, by combining each line with another one. */
    for (int col=0; col<nSlices; col++) { 
        /** makes [*][i] == 0 for all rows except i */
        for (int row=0; row<nSlices; row++) {
                       
            if (row==col || inverseM[col][row]==0) continue;
            
            enc_t mul = pgetmul(inverseM[col][row], inverseM[col][col]);
            for (int pos=0; pos<2*nSlices; pos++) {
                inverseM[pos][row] = psum( pmul(inverseM[pos][row],mul), inverseM[pos][col]);  
            }                                
        }
    }
    
    /** Normalize the matrix and copy the elements from the right columns to the left ones */                                                
    for (int line=0; line<nSlices; line++) {
        enc_t mul = pgetmul(inverseM[line][line], 1);
        for (int pos=0; pos<nSlices; pos++) {            
            
            /** Checks if the matrix was really diagonalized */
            if (pos == line) assert(inverseM[pos][line] > 0);
            else assert(inverseM[pos][line]==0);
            
            /** Normalize and copy a single element */                        
            inverseM[pos][line] = pmul(inverseM[pos+nSlices][line],mul);
        }
    }

    return inverseM;
}

//=============================================================================

enc_t **IDAAuxVectors::generateVectors(int nLines, int size) {
    return generateVectorsQuadratic(nLines, size);
}

enc_t **IDAAuxVectors::generateVectorsTest(int nLines, int size) {

    enc_t temp[][3] = {{1,2,4}, {1,3,9}, {1,4,16}, {1,5,25}};    
    
    auxVectors = new enc_t *[4];
    for (int i=0; i<4; i++) {
        auxVectors[i] = new enc_t[3];
        for (int j=0; j<3; j++) {
            auxVectors[i][j] = temp[i][j];
        }
    }
                    
    return auxVectors;
}
    
enc_t **IDAAuxVectors::generateVectorsQuadratic(int nLines, int size) {
    auxVectors = new enc_t *[nLines];
    for (int i=0; i<nLines; i++)
        auxVectors[i] = new enc_t[size];

    for (int i=0; i<nLines; i++) {
        int alpha = i+1;
        auxVectors[i][0]=(enc_t)alpha;//=1;
        for (int j=1; j<size; j++) {
            auxVectors[i][j] = pmul(auxVectors[i][j-1],alpha);
        }
    }
    
    return auxVectors;
}
