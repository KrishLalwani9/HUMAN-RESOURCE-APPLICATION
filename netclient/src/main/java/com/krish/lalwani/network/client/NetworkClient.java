package com.krish.lalwani.network.client;
import com.krish.lalwani.network.common.*;
import com.krish.lalwani.network.common.exceptions.*;
import java.io.*;
import java.net.*;
public class NetworkClient
{
public Response send(Request request) throws NetworkException
{
try
{
ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(request);
oos.flush();
byte requestBytes[]=baos.toByteArray();
Socket socket=new Socket(Configuration.getHost(),Configuration.getPort()); //connected the server
byte header[]=new byte[1024];
int requestLength=requestBytes.length;
int x=requestLength; 
int i=1023;
while(x>0)
{
header[i]=(byte)(x%10);
x=x/10;
i--;
}
OutputStream os=socket.getOutputStream();
os.write(header,0,1024);
os.flush();
byte ack[]=new byte[1];
InputStream is=socket.getInputStream();
int bytesReadCount;
while(true)
{
bytesReadCount=is.read(ack);
if(bytesReadCount==-1) continue;
break;
}
int bytesToSend=requestLength;
x=0;
int chunkSize=4096;
while(x<bytesToSend)
{
if((bytesToSend-x)<chunkSize) chunkSize=bytesToSend-x;
os.write(requestBytes,x,chunkSize);
os.flush();
x+=chunkSize;
}
System.out.println("Request Sent");
while(true)
{
bytesReadCount=is.read(ack);
if(bytesReadCount==-1) continue;
break;
}
System.out.println("Acknowledgement received");
x=0;
int bytesToReceive=1024;
byte tmp[]=new byte[1024];
i=0;
header=new byte[1024];
int k;
while(x<bytesToReceive)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1) continue;
for(k=0;k<bytesReadCount;k++)
{
header[i]=tmp[k];
i++;
}
x=x+bytesReadCount;
}
os.write(1);
os.flush();
System.out.println("Acknowledgement send");
i=1023;
x=1;
int responseLength=0;
while(i>=0)
{
responseLength=responseLength+(header[i]*x);
x*=10;
i--;
}
System.out.println("Header Received of length : "+responseLength);
byte responseByte[]=new byte[responseLength];
bytesToReceive=responseLength;
x=0;
i=0;
tmp=new byte[4096];
System.out.println("***");
while(x<responseLength)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1) continue;
for(k=0;k<bytesReadCount;k++)
{
responseByte[i]=tmp[k];
i++;
}
x+=bytesReadCount;
}
System.out.println("Response Received of length : "+x);
os.write(1);
os.flush();
ByteArrayInputStream bais=new ByteArrayInputStream(responseByte);
ObjectInputStream ois=new ObjectInputStream(bais);
Response response=(Response)ois.readObject();
/*response.getResult().forEach((k)->{
System.out.println(k.getCode());
});*/
return response;
}catch(Exception e)
{
throw new NetworkException(e.getMessage());
}
}
}