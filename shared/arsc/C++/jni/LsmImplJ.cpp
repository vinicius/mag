#include "LsmImplJ.hpp"
#include "TokenGSS.hpp"
#include "LsmImpl.hpp"
#include "utils/c++/Config.hpp"
#include <stdio.h>

static ArscImpl * lsmPointer;

ArscImpl * getPointer(JNIEnv *env,jobject obj)
{
 	jclass cls = env->GetObjectClass(obj);
  	jfieldID fid = env->GetFieldID(cls,"LsmImplPointer_","I");	
	return (ArscImpl *) env->GetIntField(obj,fid);
}
JNIEXPORT jint JNICALL Java_LsmImpl_LsmImplCreate(JNIEnv *env, jobject obj)
{
		Config config("lsm.conf");
	    ArscImpl  lsm = ArscImpl::init(config); 
       char * name = "jrbraga";
       char * pass = "integrade";
       lsm.initContext(name,pass);
       char * msg = "mensagem de teste"; 
       TokenGSS *tk = new TokenGSS(strlen(msg) , msg,1,"");
       *tk = lsm.selfTest(lsm.signMessage(*tk));
         printf("Valor retornado: %d tam=%d\n",tk->value[1],tk->length);
       *tk = lsm.unsignMessage(*tk);
       printf("Mensagem retornada %s\n",tk->value);
       exit(0);
       
	
/*    Config config("lsm.conf");
	ArscImpl lsm = ArscImpl::init(config);
	lsm.initContext("jrbraga","integrade"); 
	lsm.context=NULL;
	lsmPointer = &lsm;
	printf("context=%d\n",lsmPointer->context);
	return (long) &lsm;
	*/
   	/*printf("create\n");
   	lsm.context=NULL;
	printf("context=%d\n",lsm.context);
    // Get the class associated with this object
	jobject clasJB ;
  	jclass cls = env->GetObjectClass(obj);
  	clasJB = env->NewGlobalRef(obj);
  	jfieldID fid = env->GetFieldID(cls,"LsmImplPointer_","I");
  	
  	ArscImpl *lsmRet = (ArscImpl *) malloc(sizeof(ArscImpl));
  	lsmRet = &lsm;
  	env->SetIntField(obj,fid,(long) lsmRet); */
  	
}

JNIEXPORT jboolean JNICALL Java_LsmImpl_initContext (JNIEnv *env, jobject obj, jstring jname ,  jstring jpass)
{	

//	ArscImpl *lsm = getPointer(env,obj);
//	const char * name = env->GetStringUTFChars(jname,0);
//	const char * pass = env->GetStringUTFChars(jpass, 0);
	      char * name = "jrbraga";
       char * pass = "integrade";
	printf("context=%d\n",lsmPointer->context);
	if(lsmPointer->initContext((char *) name,(char *) pass))
	return 1;
	else
	return 0;
		
}
JNIEXPORT void JNICALL Java_LsmImpl_finishContext(JNIEnv *env, jobject obj)
{
	ArscImpl *lsm = getPointer(env,obj);
	lsm->finishContext();
}

JNIEXPORT jbyteArray JNICALL Java_LsmImpl_signMessage(JNIEnv *env , jobject obj, jbyteArray jmessage)
{
	ArscImpl *lsm = getPointer(env,obj);
	unsigned int  length;
	printf("sigining\n");
	length= env->GetArrayLength(jmessage);
	const char *message  = (const char*) malloc(sizeof(char)*length);
	message=(const char *) env->GetByteArrayElements(jmessage, 0);
	for(int i=0;i<length;i++) printf("%c",message[i]);
   	TokenGSS *tk = new TokenGSS(length , (char *) message,1,"");
   	*tk = lsm->signMessage(*tk);
  	env->ReleaseByteArrayElements(jmessage, (jbyte *) message,JNI_ABORT);   
  	jbyteArray ret = env->NewByteArray(length);
  	env->SetByteArrayRegion(ret,0,tk->length,(jbyte*) tk->value);
	return ret;
}
JNIEXPORT jbyteArray JNICALL Java_LsmImpl_unsignMesage(JNIEnv * env, jobject obj, jbyteArray jmessage)
{
	ArscImpl *lsm = getPointer(env,obj);
	unsigned int  length;
	length= env->GetArrayLength(jmessage);
	const char *message  = (const char *) malloc(sizeof(char)*length);
	message=(const char *) env->GetByteArrayElements(jmessage, 0);
   	TokenGSS *tk = new TokenGSS(length , (char *) message,1,"");
   	*tk = lsm->unsignMessage(*tk);
  	env->ReleaseByteArrayElements(jmessage, (jbyte*) message,JNI_ABORT);   
  	jbyteArray ret = env->NewByteArray(length);
  	env->SetByteArrayRegion(ret,0,tk->length,(jbyte*) tk->value);
	return ret;
}

