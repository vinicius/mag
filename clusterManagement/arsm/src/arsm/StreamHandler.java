/*
 * Created on 14/10/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package arsm;


/**
 * @author Braga Junior.
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class StreamHandler {
	private byte[] str;
	private byte[] id;
	private byte[] msg;
	private byte[] signature;
	private final int nByteId =2;
	private final int nByteMessage = 4;
	private final int nByteSignature =2;
/**
 * Message constructor
 * @param st
 * stream format:
 * [lengthId][id][lengthValue][value][signatureLength (*)][signature (*)]
 * length  - 5 bytes
 * lengthSignature - 2 bytes
 * (*) Optional
 */	

	public StreamHandler(byte[]   st)
	{
		str=st;
		int idLength;
		int msgLength;
		int signLength;
		int offset; // Auxiliar Variable
		// id Length
		offset=0;
		idLength=lengthCalc(st,offset,nByteId);
		//	set id
		byte[] idL= new byte[idLength];
		offset=offset+nByteId;
		System.arraycopy(st,offset,idL,0,idLength);
/*		for( int j=0;j<idLength;j++)
		{
			idL[j]=st.value[offset+j];
		}
		
*/
		id=idL;
		//Message Length
		offset=nByteId+idLength; 
		msgLength=lengthCalc(st,offset,nByteMessage);
		//Set msn
		byte[] msgL = new byte[msgLength];
		offset=offset+nByteMessage; 
		System.arraycopy(st,offset,msgL,0,msgLength);
/*		for(int j=0;j<msgLength;j++)
		{
			msgL[j]=st[offset+j];
		}
*/
		msg=msgL;
		
		offset=nByteId+idLength+nByteMessage + msgLength; 
		if(st.length>(offset + 1))
		{
			//		 signature Length
			signLength= lengthCalc(st,offset,nByteSignature);
			byte[] signL=new byte[signLength];
			
			//		 set signature
			offset=offset + nByteSignature;
			System.arraycopy(st,offset,signL,0,signLength);
/*			for(int j=0;j<signLength;j++)
			{
				signL[j]=st[offset+j];
			}
*/
				signature=signL;
		}
	}
	
	public StreamHandler(byte[] ident,byte[] msge)
	{
		id=ident;
		msg=msge;
		signature=null;
		str=null;
	}

	public StreamHandler(byte[] ident,byte[] msge,byte[] sign)
	{
		id=ident;
		msg=msge;
		signature=sign; 
		str=null;
	}

	
	
	public StreamHandler()
	{
		str=null;
	}
	public byte[] getStream()
	{
		if(str==null)
		{
			byte[] idL=new byte[nByteId];
			byte[] msgL=new byte[nByteMessage];
			for(int j=0;j<idL.length;j++)
			{
				idL[j]=byteCalc(id.length,j,nByteId);	
			}
			for(int j=0;j<msgL.length;j++)
			{
				msgL[j]=byteCalc(msg.length,j,nByteMessage);	
			}
			str = cat4(idL,id,msgL,msg);
			if(signature!=null)
			{
				byte[] tmpStr = new byte[str.length+ nByteSignature + signature.length];
				System.arraycopy(str,0,tmpStr,
						0,str.length);
				byte[] signL=new byte[nByteSignature];
				for(int j=0;j<nByteSignature;j++)
				{
					signL[j]=byteCalc(signature.length,j,nByteSignature);	
				}
				System.arraycopy(signL,0,tmpStr,
						str.length,nByteSignature);
				System.arraycopy(signature,0,tmpStr,
						str.length+nByteSignature,signature.length);
				str=tmpStr;
			}
		}

		return str;
	}
	// 	
	public byte[] getId()
	{
		return id;
	}
	public static byte[] getId(byte[] message)
	{
		byte[] st;
		st = message;
		StreamHandler mp = new StreamHandler(st);
		return mp.getId();
	}
	public byte[] getMsg()
{
	return msg;
}
	public int getMsgLength()
	{
		return msg.length;
	}
	public byte[] getSignature()
	{
		return signature;
	}
	public void print()
	{
		for(int i=0;i<str.length;i++)
			System.out.print("("+i+ "->" + str[i]+"):");
		
	}
	
	int lengthCalc(byte[] l,int offset,int byteLength)
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
	
	//this function contatenate 4 byte arrays
	byte[] cat4(byte[] src1,byte[] src2,byte[] src3, byte[] src4)
	{
		int offset=0;
		byte[] tmp = new byte[src1.length+src2.length+src3.length+src4.length];
		
		System.arraycopy(src1,0,tmp,offset,src1.length);
		
		offset=src1.length;
		System.arraycopy(src2,0,tmp,offset,src2.length);
		
		offset=offset+src2.length;
		System.arraycopy(src3,0,tmp,offset,src3.length);
		 
		offset=offset+src3.length;
		System.arraycopy(src4,0,tmp,offset,src4.length);
		
		return tmp;
	}
	
	private  static byte byteCalc(int number,int position,int qtdByte) 
		{
			number = number >> ((qtdByte - position-1)*8) ;
			number = number & 0xFF;
			Integer tempInteger = new Integer(number);	   
			return tempInteger.byteValue();	
		}

}
