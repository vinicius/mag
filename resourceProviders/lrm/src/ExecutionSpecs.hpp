#ifndef ExecutionSpecs_HPP
#define ExecutionSpecs_HPP

#include <string>
#include <vector>

#include "utils/c++/StringTokenizer.hpp"


using namespace std;

 /**
   ExecutionSpecs - Holds information about an execution request

   @author Andrei Goldchleger
 */

 class ExecutionSpecs{

  private:

    string requestingAsctIor_;    /**< IOR of the ASCT requesting this execution */
    string applicationRepositoryIor_; /**< IOR of the Application Repository related to the first cluster */
    string applicationId_;     /**< Main id of this execution */
    string processId_;         /**< Node id of this execution */
    // Vinicius {
    //string replicaId_;        /**<Replica id of this execution */
    // } Vinicius
    string asctApplicationId_; /**< Main id of this execution as atributed by the ASCT */
    string grmIor_;               /**< IOR of the GRM which this request will be sent  */
    string previousGrmIor_;       /**< IOR of the last GRM that forwarded this request  */

    string basePath_;
    string applicationName_;
    string binaryName_;
                                    
    string appArgs_;           /**< Arguments that will be passed to the application */
    string appConstraints_;    /**< Constraints regarding execution (in TCL) */
    string appPreferences_;    /**< Preferences regarding execution (in TCL) */
    vector<string> inputFiles_;      /**< List of files needed by this request*/
    vector<string> outputFiles_;      /**< List of files needed by this request*/

    string numberOfReplicas_;


  public:


    //Named parameter idiom---------------------------------------------------

    ExecutionSpecs & requestingAsctIor(const string & aRequestingAsctIor){
      requestingAsctIor_= aRequestingAsctIor;
      return (*this);
    }

    ExecutionSpecs & applicationRepositoryIor(const string & applicationRepositoryIor){
      applicationRepositoryIor_= applicationRepositoryIor;
      return (*this);
    }

    ExecutionSpecs & applicationId(const string & appMainRequestId){
      applicationId_= appMainRequestId;
      return (*this);
    }

    ExecutionSpecs & asctApplicationId(const string & asctApplicationId){
      asctApplicationId_= asctApplicationId;
      return (*this);
    }

    ExecutionSpecs & processId(const string & appNodeRequestId){
      processId_= appNodeRequestId;
      return (*this);
    }

    // Vinicius {
    //ExecutionSpecs & replicaId(const string & appNodeReplicaId){
    //  replicaId_ = appNodeReplicaId;
    //  return (*this);
    //}
    // } Vinicius

    ExecutionSpecs & grmIor(const string & aGrmIor){
      grmIor_= aGrmIor;
      return (*this);
    }

    ExecutionSpecs & previousGrmIor(const string & aGrmIor){
      previousGrmIor_= aGrmIor;
      return (*this);
    }

    ExecutionSpecs & basePath(const string & basePath){
      basePath_= basePath;
      return (*this);
    }

    ExecutionSpecs & applicationName(const string & applicationName){
      applicationName_= applicationName;
      return (*this);
    }

    ExecutionSpecs & binaryName(const string & binaryName){
      binaryName_= binaryName;
      return (*this);
    }

    ExecutionSpecs & appArgs(const string & aAppArgs){
      appArgs_= aAppArgs;
      return (*this);
    }

    ExecutionSpecs & appConstraints(const string & aAppConstraints){
      appConstraints_= aAppConstraints;
      return (*this);
    }

    ExecutionSpecs & appPreferences(const string & aAppPreferences){
      appPreferences_= aAppPreferences;
      return (*this);
    }

    //This method assumes that filenames are separated by whitespace.
    ExecutionSpecs & inputFiles(const string & aInputFiles){
      StringTokenizer st(aInputFiles);
      while(st.hasMoreTokens())
       inputFiles_.push_back(st.nextToken());
      return (*this);
    }

    ExecutionSpecs & inputFiles(vector<string> aInputFiles){
      inputFiles_ = aInputFiles;
      return (*this);
    }

    ExecutionSpecs & outputFiles(vector<string> aOutputFiles){
      outputFiles_ = aOutputFiles;
      return (*this);
    }

    ExecutionSpecs & numberOfReplicas(const string & numberOfReplicas){
      numberOfReplicas_ = numberOfReplicas;
      return (*this);
    }


   //getters-------------------------------------------------------------
   const string & applicationRepositoryIor() const{return  applicationRepositoryIor_;}
   const string & requestingAsctIor() const{return requestingAsctIor_;}
   const string & applicationId() const{return applicationId_;}
   const string & asctApplicationId() const{return asctApplicationId_;}
   const string & processId() const{return processId_;}
   // Vinicius {
   //const string & replicaId() const{return replicaId_;}
   // } Vinicius
   const string & grmIor() const{return  grmIor_;}
   const string & previousGrmIor() const{return  previousGrmIor_;}
   const string & applicationName() const{return applicationName_;}
   const string & basePath() const{return basePath_;}
   const string & appArgs() const{return   appArgs_;}
   const string & appConstraints() const{return  appConstraints_;}
   const string & appPreferences() const{return  appPreferences_;}
   const vector<string> & inputFiles() const{return inputFiles_;}
   const vector<string> & outputFiles() const{return outputFiles_;}
   const string & binaryName() const{return binaryName_;}
   const string & numberOfReplicas() const{return numberOfReplicas_;}

 };


#endif//ExecutionSpecs_HPP

