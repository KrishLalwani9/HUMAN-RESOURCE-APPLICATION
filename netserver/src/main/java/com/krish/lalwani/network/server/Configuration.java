package com.krish.lalwani.network.server;
import com.krish.lalwani.network.common.exceptions.*;
import java.io.*;
import javax.xml.xpath.*;
import org.xml.sax.*;
import org.w3c.dom.*; //no need
class Configuration //no need of public because whoever using this class is on same package
{
private static String host="";
private static int port=-1;
private static boolean malformed=false;
private static boolean fileMissing=false;
static 
{
try
{
File file=new File("server.xml");
if(file.exists())
{
InputSource inputSource=new InputSource("server.xml");
XPath xpath=XPathFactory.newInstance().newXPath();
String port=xpath.evaluate("//server/@port",inputSource);
Configuration.port=Integer.parseInt(port);
}
else fileMissing=true;
}catch(Exception e)
{
malformed=true;
//do nothing
}
}
public static int getPort() throws NetworkException
{
if(fileMissing) throw new NetworkException("server.xml missing, read the documentation");
if(malformed) throw new NetworkException("server.xml not configured according to documentation");
if(port<0 || port>49151) throw new NetworkException("server.xml contains invalid port number,read documentation");
return port;
}
}