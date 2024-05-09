package com.krish.lalwani.hr.server;
import com.krish.lalwani.network.server.*;
import com.krish.lalwani.network.common.*;
import com.krish.lalwani.hr.bl.interfaces.managers.*;
import com.krish.lalwani.hr.bl.interfaces.pojo.*;
import com.krish.lalwani.hr.bl.managers.*;
import com.krish.lalwani.hr.bl.exceptions.*;
public class RequestHandler implements RequestHandlerInterface
{
private DesignationManagerInterface designationManager;
public RequestHandler()
{
try
{
designationManager=DesignationManager.getDesignationManager();
}catch(BLException blException)
{
System.out.println("This is : "+blException.getMessage());
}
}
public Response process(Request request)
{
Response response=new Response();
String manager=request.getManager();
String action=request.getAction();
Object arguments[]=request.getArguments();
if(manager.equals("DesignationManager"))
{
if(designationManager==null) 
{
//We will implement it later
System.out.println("This is null");
}
if(action.equals("getDesignations"))
{
Object result=designationManager.getDesignations();
response.setSuccess(true);
response.setResult(result);
response.setException(null);
}else if(action.equals("addDesignation"))
{
//Object result= May no need this
try
{
DesignationInterface designation=(DesignationInterface)arguments[0];
designationManager.addDesignation(designation);
response.setSuccess(true);
response.setResult((Object)designation);
response.setException(null);
}catch(BLException blException)
{
response.setSuccess(false);
response.setResult(null);
response.setException(blException);
return response;
}
}
else if(action.equals("updateDesignation"))
{
//Object result= May no need this
try
{
designationManager.updateDesignation((DesignationInterface)arguments[0]);
response.setSuccess(true);
response.setResult(null);
response.setException(null);
}catch(BLException blException)
{
response.setSuccess(false);
response.setResult(null);
response.setException(blException);
return response;
}
}else if(action.equals("removeDesignation"))
{
//Object result= May no need this
try
{
designationManager.removeDesignation((int)arguments[0]);
response.setSuccess(true);
response.setResult(null);
response.setException(null);
}catch(BLException blException)
{
response.setSuccess(false);
response.setResult(null);
response.setException(blException);
return response;
}
}else if(action.equals("getDesignationByCode"))
{
try
{
Object result=designationManager.getDesignationByCode((int)arguments[0]);
response.setSuccess(true);
response.setResult(result);
response.setException(null);
}catch(BLException blException)
{
response.setSuccess(false);
response.setResult(null);
response.setException(blException);
return response;
}
}else if(action.equals("getDesignationByTitle"))
{
try
{
Object result=designationManager.getDesignationByTitle((String)arguments[0]);
response.setSuccess(true);
response.setResult(result);
response.setException(null);
}catch(BLException blException)
{
response.setSuccess(false);
response.setResult(null);
response.setException(blException);
return response;
}
}else if(action.equals("getDesignationCount"))
{
Object result=designationManager.getDesignationCount();
response.setSuccess(true);
response.setResult(result);
response.setException(null);
}else if(action.equals("isDesignationCodeExists"))
{
Object result=designationManager.isDesignationCodeExists((int)arguments[0]);
response.setSuccess(true);
response.setResult(result);
response.setException(null);
}else if(action.equals("isDesignationTitleExists"))
{
Object result=designationManager.isDesignationCodeExists((int)arguments[0]);
response.setSuccess(true);
response.setResult(result);
response.setException(null);
}
}//DesignationManager part ends here
return response;
}
}