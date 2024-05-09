package com.krish.lalwani.network.server;
import com.krish.lalwani.network.common.*;
import com.krish.lalwani.network.common.exceptions.*;
import java.io.*;
import java.net.*;
public class NetworkServer
{
private RequestHandlerInterface requestHandler;
public NetworkServer(RequestHandlerInterface requestHandler) throws NetworkException
{
if(requestHandler==null)
{
throw new NetworkException("Request Handler is Missing");
}
this.requestHandler=requestHandler;
}
public void start() throws NetworkException //will run on main thread
{
ServerSocket serverSocket=null;
int port=Configuration.getPort();
try
{
serverSocket=new ServerSocket(port);
}catch(Exception exception)
{
throw new NetworkException("Unable to start server on port :"+port);
}
try
{
Socket socket;
RequestProcessor requestProcessor;
while(true)
{
System.out.println("Krish's server is ready to accept your request on port number : "+port+".\nHow can I help you");
socket=serverSocket.accept(); // server is goes in wait mode to accept request
requestProcessor=new RequestProcessor(socket,requestHandler);
}
}catch(Exception e)
{
System.out.println(e);
}
}
}