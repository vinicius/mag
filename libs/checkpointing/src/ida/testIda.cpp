#include <iostream>
#include <iomanip>
#include <cstdlib>
#include <vector>
#include <cassert>
#include <cmath>

#include <sys/time.h>
#include <sys/times.h>

#include "IDAEncoder.h"
#include "IDAImpl.h"
#include "LocalParityEncoder.h"
#include "IDADecoder.h"
#include "IDADefinitions.h"
#include "IDAAuxVectors.h"

using namespace std;

enc_t *mulTable;
enc_t *mulList;
enc_t *iMulList;

FILE *outFile;

int p = 257;
//int p = 1000000000;


void testEncoding (IDAImpl & idaImpl, int size, int nSlices, int nExtras, int sliceNumbers[]);

int main(int argc, char **argv) {
              
    outFile = fopen("/tmp/ida.dat", "a" );            
    IDAImpl idaImpl;
                
//    int nSlices = 9;
//    int nExtras = 1;    
//    int sliceNumbers[] = {0,1,2,3,4,5,6,7,9};
//    int sliceNumbers[] = {0,1,2,3,4,5,8,9};

    int sliceNumbers[][9] = {{0,1,2,3,4,5,6,7,9}, 
                             {0,1,2,3,4,5,6,8,9}, 
                             {0,1,2,3,4,5,7,8,9}, 
                             {0,1,2,3,4,6,7,8,9}, // 6x4
                             {0,1,2,3,5,6,7,8,9}}; 

    //    int sliceNumbers[] = {0,1,2,3,4,5,6,7,9};
    
//    int **sliceNumbers = new int *[4];
//    sliceNumbers[0] = {0,1,2,3,4,5,6,7,9}; // 9x1
//    sliceNumbers[1] = {0,1,2,3,4,5,6,8,9}; // 8x2
//    sliceNumbers[2] = {0,1,2,3,4,5,7,8,9}; // 7x3
//    sliceNumbers[3] = {0,1,2,3,4,6,7,8,9}; // 6x4
//    sliceNumbers[4] = {0,1,2,3,5,6,7,8,9}; // 5x5            

    int nSlices[] = {9,8,7,6,5};
    int nExtras[] = {1,2,3,4,5};
                  
    int sizes[] = {10,20,50,100,200};

    for (int size=3; size<4; size++) 
      for (int exp=1; exp<3; exp++) 
        testEncoding(idaImpl, sizes[size], nSlices[exp], nExtras[exp], sliceNumbers[exp]);

    return 0;
}

void testEncoding (IDAImpl & idaImpl, int size, int nSlices, int nExtras, int sliceNumbers[]) {

    LocalParityEncoder localParityEncoder;

    int dataRandSize = size * 1000000;
    unsigned char *dataRand = new unsigned char[dataRandSize];
    for (int i=0; i<dataRandSize; i++)
        dataRand[i] = rand()%256; 

    fprintf (outFile, "size=%-5d nSlices=%-5d\n", size, nSlices);
    printf ("size=%-5d nSlices=%-5d\n", size, nSlices);
    
    vector<double> intervalEv, userEv, systemEv;
    vector<double> intervalDv, userDv, systemDv;    
    
    for (int i=0; i<1; i++) {
    
        //------------------------------------------------------------------    
        timeval startTime, lastTime, currTime;
        tms startTms, lastTms, currTms;
        double clockSec = sysconf(_SC_CLK_TCK);
        
        gettimeofday(&startTime, NULL); 
        times(&startTms);
        lastTime = startTime; lastTms = startTms;
        //------------------------------------------------------------------    
           
        enc_t **dataRandEnc = idaImpl.encodeData(dataRand, dataRandSize, nSlices, nExtras);
        //enc_t **dataRandEnc = localParityEncoder.encodeData(dataRand, dataRandSize, nSlices);
    
        int i=0;
                 
        //------------------------------------------------------------------
        gettimeofday(&currTime, NULL);
        times(&currTms);
        double intervalE = (currTime.tv_sec + currTime.tv_usec/1000000.) - 
                           (lastTime.tv_sec + lastTime.tv_usec/1000000.);
        double userE   = (currTms.tms_utime - lastTms.tms_utime)/clockSec;
        double systemE = (currTms.tms_stime - lastTms.tms_stime)/clockSec;
        
        fprintf (outFile, "E = [%-8.2f|%-8.2f|%-8.2f]\n", intervalE, userE, systemE);               
        printf ( "E = [%-8.2f|%-8.2f|%-8.2f]\n", intervalE, userE, systemE);
        lastTime = currTime; lastTms = currTms;
        //------------------------------------------------------------------        
            
        i++;
        
        enc_t **data1 = new enc_t *[nSlices];
        for (int i=0; i<nSlices; i++)
            data1[i] = dataRandEnc[sliceNumbers[i]];    
        
        unsigned char *dataRandFinal = idaImpl.decodeData(data1, dataRandSize, sliceNumbers, nSlices, nExtras);
	   //unsigned char *dataRandFinal = localParityEncoder.decodeData(dataRandEnc, dataRandSize, sliceNumbers, nSlices);
            
        delete[] data1;            
        //------------------------------------------------------------------
        gettimeofday(&currTime, NULL);
        times(&currTms);
        double intervalD = (currTime.tv_sec + currTime.tv_usec/1000000.) - 
                           (lastTime.tv_sec + lastTime.tv_usec/1000000.);
        double userD   = (currTms.tms_utime - lastTms.tms_utime)/clockSec;
        double systemD = (currTms.tms_stime - lastTms.tms_stime)/clockSec;
                       
        fprintf (outFile, "D = [%-8.2f|%-8.2f|%-8.2f]\n", intervalD, userD, systemD);
        printf ( "D = [%-8.2f|%-8.2f|%-8.2f]\n", intervalD, userD, systemD);
        lastTime = currTime; lastTms = currTms;
        //------------------------------------------------------------------        

        intervalEv.push_back(intervalE);
        userEv.push_back(userE);
        systemEv.push_back(systemE);

        intervalDv.push_back(intervalD);
        userDv.push_back(userD);
        systemDv.push_back(systemD);
        
        for (int i=0; i<dataRandSize; i++)
            assert(dataRand[i] == dataRandFinal[i]);               
                
        for (int slice=0; slice < nSlices+nExtras; slice++)
            delete[] dataRandEnc[slice];
        delete[] dataRandEnc;    
        delete[] dataRandFinal;    

    }

    delete[] dataRand;    

    fprintf (outFile, "------------------------------------------------------------\n");        
    printf ("------------------------------------------------------------\n");
    
    double intervalEm=0, userEm=0, systemEm=0;
    double intervalEstd=0, userEstd=0, systemEstd=0;    
    
    /** Evaluates the mean time **/
    for (unsigned int i=0; i<intervalEv.size(); i++) {
        intervalEm += intervalEv[i];
        userEm     += userEv[i];
        systemEm   += systemEv[i];                
    }
    intervalEm /= intervalEv.size();
    userEm     /= intervalEv.size();
    systemEm   /= intervalEv.size();              

    /** Evaluates the standard deviation **/
    for (unsigned int i=0; i<intervalEv.size(); i++) {
        intervalEstd += (intervalEv[i]-intervalEm)*(intervalEv[i]-intervalEm);
        userEstd     += (userEv[i]-userEm)*(userEv[i]-userEm);
        systemEstd   += (systemEv[i]-systemEm)*(systemEv[i]-systemEm);                
    }
    intervalEstd = sqrt(intervalEstd/intervalEv.size());
    userEstd     = sqrt(userEstd/intervalEv.size()    );
    systemEstd   = sqrt(systemEstd/intervalEv.size()  );                
    
    fprintf (outFile, "E = [ %-8.2f +- %-8.2f | %-8.2f + -%-8.2f | %-8.2f +- %-8.2f ]\n", 
              intervalEm, intervalEstd, userEm, userEstd, systemEm, systemEstd);
    printf ( "E = [ %-8.2f +- %-8.2f | %-8.2f + -%-8.2f | %-8.2f +- %-8.2f ]\n", 
              intervalEm, intervalEstd, userEm, userEstd, systemEm, systemEstd);

    //-----------------------------------------------------
    double intervalDm=0,   userDm=0,   systemDm=0;
    double intervalDstd=0, userDstd=0, systemDstd=0;       

    /** Evaluates the mean time **/
    for (unsigned int i=0; i<intervalDv.size(); i++) {
        intervalDm += intervalDv[i];
        userDm     += userDv[i];
        systemDm   += systemDv[i];                
    }
    intervalDm /= intervalDv.size();
    userDm     /= intervalDv.size();
    systemDm   /= intervalDv.size();              

    /** Evaluates the standard deviation **/
    for (unsigned int i=0; i<intervalDv.size(); i++) {
        intervalDstd += (intervalDv[i]-intervalDm)*(intervalDv[i]-intervalDm);
        userDstd     += (userDv[i]-userDm)*(userDv[i]-userDm);
        systemDstd   += (systemDv[i]-systemDm)*(systemDv[i]-systemDm);                
    }
    intervalDstd = sqrt(intervalDstd/intervalDv.size());
    userDstd     = sqrt(userDstd/intervalDv.size()    );
    systemDstd   = sqrt(systemDstd/intervalDv.size()  );                

    fprintf (outFile, "D = [ %-8.2f +- %-8.2f | %-8.2f + -%-8.2f | %-8.2f +- %-8.2f ]\n", 
              intervalDm, intervalDstd, userDm, userDstd, systemDm, systemDstd);    
    printf ( "D = [ %-8.2f +- %-8.2f | %-8.2f + -%-8.2f | %-8.2f +- %-8.2f ]\n", 
              intervalDm, intervalDstd, userDm, userDstd, systemDm, systemDstd);

    fprintf (outFile, "------------------------------------------------------------\n");
    printf ("------------------------------------------------------------\n");

    
}
