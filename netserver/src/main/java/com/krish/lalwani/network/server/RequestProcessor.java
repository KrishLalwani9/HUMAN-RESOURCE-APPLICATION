package com.krish.lalwani.network.server;
import com.krish.lalwani.network.common.*;
import com.krish.lalwani.network.common.exceptions.*;
import java.io.*;
import java.net.*;
import java.util.*;
class RequestProcessor extends Thread
{
private RequestHandlerInterface requestHandler;
private Socket socket;
public RequestProcessor(Socket socket,RequestHandlerInterface requestHandler)
{
this.requestHandler=requestHandler;
this.socket=socket;
start();
}
public void run()
{
try
{
InputStream is=socket.getInputStream();
OutputStream os=socket.getOutputStream();
byte header[]=new byte[1024];
byte tmp[]=new byte[1024];
int chunkSize=1024;
int i,j,k;
j=0;
i=0;
int bytesReadCount;
int bytesToReceive=1024;
while(j<bytesToReceive)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1) continue;
for(k=0;k<bytesReadCount;k++)
{
header[i]=tmp[k];
i++;
}
j=j+bytesReadCount;
}
int requestLength=0;
j=1;
i=1023;
while(i>=0)
{
requestLength=requestLength+(header[i]*j);
j=j*10;
i--;
}
System.out.println("Header received");
byte ack[]=new byte[1];
ack[0]=1;
os.write(ack,0,1);
os.flush();
System.out.println("Acknowlegment sent");
byte requestBytes[]=new byte[requestLength];
bytesToReceive=requestLength;
i=0;
j=0;
tmp=new byte[4096];
while(j<bytesToReceive)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1) continue;
for(k=0;k<bytesReadCount;k++)
{
requestBytes[i]=tmp[i];
i++;
}
j=j+bytesReadCount;
}
System.out.println("Request arrived of length : "+requestLength);
os.write(1);
os.flush();
ByteArrayInputStream bais=new ByteArrayInputStream(requestBytes);
ObjectInputStream ois=new ObjectInputStream(bais);
Request request=(Request)ois.readObject();
System.out.println(request.getManager());
System.out.println(request.getAction());
Response response=requestHandler.process(request); // this is unfair
/*Set<DesignationInterface> designations=(Set<DesignationInterface>)response.getResult();
if(designations==null) System.out.println("Yes it is null");
else System.out.println("Not null");
designations.forEach((e)->{
System.out.println(e.getTitle());
}); This is just used for testing*/

ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(response);
oos.flush();
byte bytes[]=baos.toByteArray();
int responseLength=bytes.length;
int x=responseLength;
i=1023;
header=new byte[1024];
while(x>0)
{
header[i]=(byte)(x%10);
x=x/10;
i--;
}
os.write(header,0,1024);
os.flush();
System.out.println("Header Sent of length : "+responseLength);
while(true)
{
bytesReadCount=is.read(ack);
if(bytesReadCount==-1) continue;
break;
}
System.out.println("Acknowledgement received");
int bytesToSend=responseLength;
j=0;
chunkSize=4096;
while(j<bytesToSend)
{
if((bytesToSend-j)<chunkSize) chunkSize=bytesToSend-j;
os.write(bytes,j,chunkSize);
os.flush();
j=j+chunkSize;
}
System.out.println("Response sent of length : "+j);
while(true)
{
bytesReadCount=is.read(ack);
if(bytesReadCount==-1) continue;
break;
}
System.out.println("Acknowledgment received");
socket.close();
}catch(Exception e)
{
System.out.println(e);
}
}
}
