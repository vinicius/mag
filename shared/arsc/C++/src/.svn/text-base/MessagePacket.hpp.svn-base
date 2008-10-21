#ifndef MessagePacket_HPP
#define MessagePacket_HPP
#include <string>
class MessagePacket{
private:
	std::string str; //all string (idLength|id|msgLength|msg[signLength|sign])
	std::string id; // message id
	std::string msg; // MessagePacket
	std::string signature; // message signature
	const static unsigned int NBYTEID =2; // Number of bytes for the id length
	const static unsigned int NBYTEMESSAGE = 4; // Number of bytes for the message length
	const static unsigned int NBYTESIGNATURE = 2; // Number of bytes for the signature length


public:
	/** MessagePacket constructor
	 * @param Stream :  idLength|id|msgLength|msg[signLength|sign] 
	 * 					 2  variable   5	variable		2 variable */
	MessagePacket(std::string Stream);
	/** MessagePacket constructor
	 * @param identification, message  */
	MessagePacket(std::string ident,std::string msge);
	/** MessagePacket constructor
	 * @param identification, message, signature  */
	MessagePacket(std::string ident,std::string msge,std::string sign);
	/** MessagePacket constructor
	 *  Stream */
	MessagePacket();
	/** get Stream
	 */
	std::string getStream();
	/** get Identification */
	std::string getId();
	/** get Message */
	std::string getMsg();
	
	// get signature
	std::string getSignature();
	//TODO: implement me
	void print();
	//TODO: comment 
	unsigned char  byteCalc( int length, int position,int qtByte);
	//TODO: comment 
	int lengthCalc(std::string l,int offset,int byteLength);

};
#endif//MessagePacket_HPP
