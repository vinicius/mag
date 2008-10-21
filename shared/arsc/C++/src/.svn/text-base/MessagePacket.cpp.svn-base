#include "MessagePacket.hpp"
#include <string>
#include <stdio.h>
#include <iostream>
#include <fstream>
#include <unistd.h>
#include <string>
#include <cstdlib>
using namespace std;


MessagePacket::MessagePacket(std::string st){		
	unsigned int idLength;
	unsigned int msgLength;
	unsigned int signLength;
	unsigned int offset=0; // Auxiliar Variable

	// Id length	
	idLength=lengthCalc(st,offset,NBYTEID);

	// setting ID

	offset=NBYTEID;
	id=st.substr(offset,idLength);
	
	//MessagePacket Length

	offset=offset+idLength; 
	msgLength=lengthCalc(st,offset,NBYTEMESSAGE);
	
	// setting Message

	offset=offset+NBYTEMESSAGE; 
	msg=st.substr(offset,msgLength);

	
	// Signature

	offset = offset + msgLength;

	if(st.length()>(offset + 1)) // if message is  signed 
	{
		// signature Length
		signLength= lengthCalc(st,offset,NBYTESIGNATURE);
		// set signature
		offset=offset + NBYTESIGNATURE;
		signature=st.substr(offset,signLength);
	}
	
	str= std::string(st);
}
MessagePacket::MessagePacket(){
str="";
}
MessagePacket::MessagePacket(std::string ident,std::string msge)
{
	
	std::string idL=string("",NBYTEID);
	std::string msgL=string("",NBYTEMESSAGE);
	for(unsigned int j=0;j<NBYTEID;j++)
	{
		idL[j]=(unsigned char)byteCalc(ident.length(),j,NBYTEID);	
	}
	for(unsigned int j=0;j<NBYTEMESSAGE;j++)
	{
		msgL[j]=(unsigned char) byteCalc(msge.length(),j,NBYTEMESSAGE);	
	}
	id=ident;
	msg=msge;
	signature="";
	str = idL+ident+msgL+msge;
}

MessagePacket::MessagePacket(string ident,string msge,string sign)
{
	id=ident;
	msg=msge;
	string signL = string("",NBYTESIGNATURE);
	for(unsigned int j=0;j<NBYTESIGNATURE;j++) 
	{
		signL[j]=byteCalc(sign.length(),j,NBYTESIGNATURE);
	}
	MessagePacket mpTmp = MessagePacket(ident,msge);
	signature=sign;
	str=mpTmp.getStream() + signL + signature; 
}

std::string MessagePacket::getId(){

return id;

}
std::string MessagePacket::getMsg(){

return msg;

}
std::string MessagePacket::getSignature(){

return signature;

}

std::string MessagePacket::getStream(){
return str;
}


unsigned char  MessagePacket::byteCalc( int number, int position,int qtByte)
{
	number = number >> ((qtByte - position-1)*8);
	number = number & 0xFF;
	return (unsigned char )number;
	
}
int MessagePacket::lengthCalc(string l,int offset,int byteLength)
{
	int m=1;
	int s=0;
	int t;
	for(int j=byteLength-1;j>0;j--)
	{
		t= l[j+offset]& 0xff;
		s= s + (t*m) ;
		m=256*m;
	}
	return s;
}
	

void MessagePacket::print(){

cout << endl << "Length String=" << str.length()<<endl;
 cout << "ID ("<< getId().length()<< ")=" + getId() << endl; 
 cout << "Message(" << getMsg().length() << ")=" + getMsg()<< endl;

 if(signature.length()>NBYTESIGNATURE+1)
		cout << "Signature(" << getSignature().length() << ")=" << endl;
 for(unsigned int i=0;i<str.length();i++) printf("[%d]= %c ",str[i],str[i]);
 cout << endl;

}

