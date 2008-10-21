#include <iostream>
#include <fstream>


#define TKT_LIFETIME     600
#define  KRB5_DEPRECATED 1
#define KRB5_PRIVATE 1
#include <exception>

extern "C" {
#include <time.h>
#include <pthread.h>
#include <lua.h>
#include <lualib.h>
#include <lauxlib.h>
#include </usr/include/gssapi/gssapi_generic.h>
#include </usr/include/krb5.h>
#include <syslog.h>
}

#include "ArscImpl.hpp"

#include "ArsmStub.hpp"
#include "ArscSkeleton.hpp"
#include "MessagePacket.hpp"


#include "utils/c++/LuaUtils.hpp"
#include "utils/c++/Config.hpp"
#include "utils/c++/OrbUtils.hpp"
#include "utils/c++/NoSuchConfigException.hpp"
#include "ContextInitiationException.hpp"
#include "SelfTestException.hpp"
#include "ContextFinalizationException.hpp"
#include "SignatureRequestException.hpp"
#include "SignatureCheckingException.hpp"
#include "SelfTestException.hpp"


#include "utils/c++/StringUtils.hpp"

#include <string>
#include <crypto++/sha.h>


  ArscImpl * ArscImpl::singleInstance_ = NULL;

  //---------------------------------------------------------------------
  ArscImpl & ArscImpl::init(const Config & config){
    if(ArscImpl::singleInstance_ == NULL){
      ArscImpl::singleInstance_ = new ArscImpl(config);
    }
    return *ArscImpl::singleInstance_;
    
  }
  
  ArscImpl::ArscImpl(const Config & config){
  
  	try{
	    orbPath = config.getConf("orbPath");
	    securityIdlPath = config.getConf("securityIdlPath");
	    id = config.getConf("principal");
	    ktPath = config.getConf("keytab");
	    wrap = (config.getConf("isWrap")=="true");
	    clientSideState = lua_open();  
	    OrbUtils::initLuaState(clientSideState);
	    OrbUtils::loadOrb(clientSideState, orbPath);
	    OrbUtils::loadIdl(clientSideState, securityIdlPath);
	
	    //NameService
	    serverNameRef = config.getConf("serverNameRef");
	    CosNamingIdlPath = config.getConf("cosNamingIdlPath");
	    nameServiceStub = new NameServiceStub(lua_open(),serverNameRef,CosNamingIdlPath);
	    // Name Resolving
	    arsmIor=nameServiceStub->resolve("ARSM");
	    arsmStub = new ArsmStub(clientSideState,arsmIor);
	    initContext();
  	}catch(NoSuchConfigException e)
  	{
  		cout << e.what() << endl;
  	}
  }
 

ArscImpl::~ArscImpl()
{

}
 // Function Based on free software kpass.c.  Shumon Huque - University of Pennsylvania
int  ArscImpl::auth()
{
    krb5_context        context;
    krb5_creds          credentials;
    krb5_ccache         ccache;
    krb5_error_code     err;
    krb5_keytab         keytab = NULL;
    krb5_data           tgs_name = { 0, KRB5_TGS_NAME_SIZE, KRB5_TGS_NAME };
    int ret=0;
    const char * keytabFile = ktPath.c_str();
    const char * username = id.c_str();

    err = krb5_init_context(&context);
    //krb5_init_ets(context);
    err = krb5_cc_default(context, &ccache);
    (void) memset( (char *)&credentials, 0, sizeof(credentials) );
    err = krb5_parse_name(context, username, &credentials.client);
    err = krb5_cc_initialize(context, ccache, credentials.client);
    credentials.times.endtime = (krb5_timestamp) time(0) + TKT_LIFETIME;
    err = krb5_build_principal_ext(
	     context, 
	     &credentials.server,
	     krb5_princ_realm(context, credentials.client)->length,
	     krb5_princ_realm(context, credentials.client)->data,
	     tgs_name.length,
	     tgs_name.data,
	     krb5_princ_realm(context, credentials.client)->length,
	     krb5_princ_realm(context, credentials.client)->data,
	     0 );

	err = krb5_kt_resolve(context, keytabFile, &keytab);
    	err = krb5_get_in_tkt_with_keytab(
	       context,
	       0, NULL, NULL, NULL, keytab, ccache, &credentials, 0);

    switch (err) {
    case 0:
	/* Success. */
	    printf("Sucessfull login:%s\n",username);
	    ret = 1;
	break;
    default:
	/* failure. */
	    printf("Login Failed.\n");
	    ret=0;
	break;
    }

	krb5_kt_close(context, keytab);
        krb5_free_context(context);
	return ret;
}

 int  ArscImpl::initContext(){
if(auth())
 	{
		gss_buffer_desc send_token;
		gss_buffer_desc *token_ptr;
		gss_name_t target_name;
		MessagePacket msgIn , msgOut;
		OM_uint32 min_stat,maj_stat,init_sec_min_stat;
		OM_uint32 *ret_flags=(OM_uint32*)malloc(sizeof(OM_uint32));
		string serviceName(SERVICENAME);
		send_token.value = (void *) serviceName.c_str();
		send_token.length = serviceName.length();
		maj_stat = gss_import_name(&min_stat,&send_token,(gss_OID)  gss_nt_user_name ,&target_name);
		if(maj_stat != GSS_S_COMPLETE)
		{
			display_status("inicializing context",maj_stat,min_stat);
			(void) gss_release_name(&min_stat, &target_name);
			return 0;
		}
				
		 token_ptr = GSS_C_NO_BUFFER;
		 context = GSS_C_NO_CONTEXT;
		 
		do {
			maj_stat = gss_init_sec_context(&init_sec_min_stat,
					     GSS_C_NO_CREDENTIAL,
					    &context,
					     target_name,
					     GSS_C_NULL_OID,
					     GSS_C_MUTUAL_FLAG | GSS_C_REPLAY_FLAG,
					     0,
					     NULL, // no chanel bindings
					     token_ptr,
					     NULL, // ignore mech type
					     &send_token,
					     ret_flags,
					     NULL); // ignore time_rec
				

		if(send_token.length != 0){
			msgOut = MessagePacket(std::string(id),
			std::string((char*)send_token.value,send_token.length));
			try{
				msgIn = (*arsmStub).initiateContext(msgOut);
			}catch(ContextInitiationException &e)
			{
				cerr << e.getMessage() << endl;
				return 0;
			}
		}
		(void) gss_release_buffer(&min_stat,&send_token);
		if(maj_stat!=GSS_S_COMPLETE && maj_stat != GSS_S_CONTINUE_NEEDED){
			display_status("initializing context",maj_stat,init_sec_min_stat);
			(void) gss_release_name(&min_stat,&target_name);
			if(context != GSS_C_NO_CONTEXT){
			//	(void) gss_delete_sec_context(&min_stat,context, GSS_C_NO_BUFFER);
			}
			return 0;
		}
		
		if(maj_stat == GSS_S_CONTINUE_NEEDED){
			 free(token_ptr);
			 token_ptr=(gss_buffer_desc*) malloc(sizeof(gss_buffer_desc));
			 token_ptr->value = (void *) msgIn.getMsg().c_str();
			 token_ptr->length = (int) msgIn.getMsg().length();
		}	
		} while( maj_stat == GSS_S_CONTINUE_NEEDED);
		(void) gss_release_name(&min_stat,&target_name);
		printf("Context created. %s\n",id.c_str());
			return 1;
	 	} else
	 	{
			exit(2);
	 	}
 }

string ArscImpl::getId()
{
	return id;	
}


string  ArscImpl::requestSignature(string  messageStream)
{
	MessagePacket message=MessagePacket(getId(),messageStream);
	gss_buffer_desc *in_buf, *out_buf;
	in_buf=(gss_buffer_desc*) malloc(sizeof(gss_buffer_desc));
    out_buf=(gss_buffer_desc*) malloc(sizeof(gss_buffer_desc));
	OM_uint32 minorStatus,major_status;
	in_buf->value = (void *) message.getMsg().c_str();
	in_buf->length = message.getMsg().length();
	int state = wrap;
	 major_status = gss_wrap(&minorStatus,
                                context,
                                wrap,
                                GSS_C_QOP_DEFAULT,
                                in_buf,
                                &state,
                                out_buf);
        if (major_status != GSS_S_COMPLETE) {
                display_status("wrapping message", major_status, minorStatus);
        if (!state) {
                        fprintf(stderr, "Warning! Message not encrypted.\n");
        }
	        throw SignatureRequestException();
        }
     
	MessagePacket signedMessagePacket=MessagePacket(getId(), string((char *)out_buf->value,out_buf->length));
//	gss_release_buffer(minorStatus,&signToken);
//	gss_release_buffer(minorStatus,messageBuffer);
 	return signedMessagePacket.getStream();					
}
string ArscImpl::checkSignature(string messageStream)
{
	MessagePacket signedMessagePacket = MessagePacket(messageStream);
	
	gss_qop_t qopState;
	gss_buffer_desc *in_buf, *out_buf;
	int state=wrap;
	OM_uint32 minorStatus,majorStatus;
	in_buf=(gss_buffer_t) malloc(sizeof(gss_buffer_desc));
	in_buf->value = (void*) signedMessagePacket.getMsg().c_str();
	in_buf->length = signedMessagePacket.getMsg().length();
	out_buf=(gss_buffer_desc*) malloc(sizeof(gss_buffer_desc));
	majorStatus = gss_unwrap(&minorStatus,
							 context,
							 in_buf,
							 out_buf,
							 &state,
							 (gss_qop_t *) NULL);

	// TODO release buffer
   // gss_release_buffer(minorStatus,&messageBuffer);
	//gss_release_buffer(minorStatus,&signToken);
							
	if(majorStatus != GSS_S_COMPLETE)
	{
		cerr<<"Data Verify error."<<endl;
		throw SignatureCheckingException();	
	}else	
		{
			if (! state) { 
			cerr<<"Warning! Message not encrypted.\n"<<endl; 
			}	
		cout << "Data Verify sucessfull. : \n" <<endl;	
		return string((char *)out_buf->value,out_buf->length);
		}
		 return MessagePacket().getStream();
   
}

void ArscImpl::finish()
{
	
	if(context != NULL)
	{
		try{
			
			(*arsmStub).finalizeContext(requestSignature(getId()));
			
		}catch(ContextFinalizationException e)
		{
			cerr << e.getMessage() << endl;
			throw;
		}
	}
}

void ArscImpl::display_status_1(char *m, OM_uint32 code, int type)
 {
     OM_uint32 maj_stat, min_stat;
     gss_buffer_desc msg;
     OM_uint32 msg_ctx;

     msg_ctx = 0;
     while (1) {
          maj_stat = gss_display_status(&min_stat, code,
                                       type, GSS_C_NULL_OID,
                                       &msg_ctx, &msg);
              printf( "GSS-API error %s: %s\n", m,
                      (char *)msg.value);
          (void) gss_release_buffer(&min_stat, &msg);

          if (!msg_ctx)
               break;
     }
 }
void ArscImpl::display_status(char *msg, OM_uint32 maj_stat, OM_uint32 min_stat)
{
 display_status_1(msg, maj_stat, GSS_C_GSS_CODE);
 display_status_1(msg, min_stat, GSS_C_MECH_CODE);
}

string  ArscImpl::test(string stringTest){	
MessagePacket tk = MessagePacket(stringTest);
try{
	tk=arsmStub->selfTest(tk);
}catch(SelfTestException e)
{
	cerr << e.getMessage() << endl;
	throw SelfTestException();
}
	
return tk.getStream() ;	
  	
  }
  
  string ArscImpl::messageDigest(string digestString){
   	using namespace std;
        using namespace CryptoPP;
	unsigned length =digestString.length();
        byte abDigest[SHA::DIGESTSIZE];
        SHA().CalculateDigest(abDigest,(byte const*) digestString.data(),length);
        return string((const char *) abDigest,SHA::DIGESTSIZE);	
  }
  
  bool ArscImpl::verifyDigest(string digestString){
	using namespace CryptoPP;
        byte abDigest[SHA::DIGESTSIZE];
	unsigned length = digestString.length();
        if(!SHA().VerifyDigest(abDigest,(byte const*) digestString.data(),length))
                        throw "Sequence does not contain the right hash";
        else return true; 
} 



