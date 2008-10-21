#ifndef LSMIMPL_HPP
#define LSMIMPL_HPP
#define SERVICENAME "srv/ARSM"

#include <string>
#include <map>
#include "utils/c++/NameServiceStub.hpp"


#include "utils/c++/GuardedVariable.hpp"

#include </usr/include/gssapi/gssapi_generic.h>

using std::string;
using std::map;

class Config;
class MessagePacket;
class ArsmStub;

#include <iostream> //FIXME: Remove

using std::cerr;
using std::endl;

/**

  ArscImpl - Implementation of the LSM IDL interface.

  ArscImpl implements LSM interface. It also hosts methods for setting
  up the server side of the LSM service (tasks such as ORB instantiation,
  proxy creation, etc). 

  LSM is a sigleton.
  
  @author Braga Junior

*/

/**
 * ArscImpl class
 */
 
class ArscImpl{

 private:


  string arsmIor;                    /**< The IOR of the ARSM we will contact */
  string serverNameRef;             /**< The Server Name Reference we will contact */
  string orbPath;                   /**< Path to the O2 files. MUST end with /?. Example:
                                         /usr/local/o2/?
                                    */

  string CosNamingIdlPath;           /**< Path to the CosNaming IDL*/  
  string securityIdlPath; /**< Path to the Resorce Management IDL. Contains
                                         ARSM 
                                    */
ArsmStub *arsmStub;
NameServiceStub *nameServiceStub;

/**
 * Kerberos authentication funtion
 */
 
int auth();
int unauth();
int wrap;
std::string ktPath;
std::string id;

  struct lua_State * clientSideState; /**< Lua state representing ArscImpl's client side*/
  
  ArscImpl(const Config & config);

  static ArscImpl * singleInstance_;
/**
 * Kerberos utils
 */    
void display_status_1(char *m, OM_uint32 code, int type)  ;
void display_status(char *msg, OM_uint32 maj_stat, OM_uint32 min_stat);
unsigned char  byteCalc(int number, int position,int qtdByte);

 public:
 gss_ctx_id_t context; /**< GSS context */
 string getPrincipal(); /**< return principal name */
 ~ArscImpl();
  static const int headerLength = 7;
  static const short NO_CONTEXT = 1;
  static const int verbose = 1; 
  static ArscImpl & init(const Config & config); /**<Singleton creator function */
  int initContext(); /*< Create an arsm  <-> arsc context */
  string getId(); /*< Return context identification (Principal name) */
  static  void  debug_token(MessagePacket msgverbose); /*< debug only */
  string  requestSignature(string  messageStream); /*< sign a Stream */
  string checkSignature(string signedStream); /*< unsign a Stream */
  string messageDigest(string digestString);
  bool verifyDigest(string digestString);
  void finish();  /*< finish  context */
  string  test(string stringTest); /*< selfTest function  remove?*/
  
   
};
#endif//LSMIMPL_HPP

