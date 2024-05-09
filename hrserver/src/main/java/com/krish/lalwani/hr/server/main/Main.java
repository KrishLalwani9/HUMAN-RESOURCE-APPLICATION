package com.krish.lalwani.hr.server.main;
import com.krish.lalwani.hr.server.*;
import com.krish.lalwani.network.server.*;
import com.krish.lalwani.network.common.exceptions.*;
public class Main
{
public static void main(String gg[])
{
try 
{
RequestHandler requestHandler=new RequestHandler();
NetworkServer networkServer=new NetworkServer(requestHandler);
networkServer.start();
}catch(NetworkException networkException)
{
System.out.println(networkException);
}
}
}
