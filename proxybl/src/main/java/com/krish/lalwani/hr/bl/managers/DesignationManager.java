package com.krish.lalwani.hr.bl.managers;
import com.krish.lalwani.hr.bl.interfaces.managers.*;
import com.krish.lalwani.hr.bl.interfaces.pojo.*;
import com.krish.lalwani.hr.bl.exceptions.*;
import com.krish.lalwani.hr.bl.pojo.*;
import java.util.*;
import com.krish.lalwani.network.common.*;
import com.krish.lalwani.network.common.exceptions.*;
import com.krish.lalwani.network.client.*;
public class DesignationManager implements DesignationManagerInterface
{
//private static DesignationManager designationManager=null;
private static DesignationManagerInterface designationManager=null;
private DesignationManager() throws BLException
{
}
public static DesignationManagerInterface getDesignationManager() throws BLException 
{
if(designationManager==null) designationManager=new DesignationManager();
return designationManager;
}
//********************************************************************************************
public void addDesignation(DesignationInterface designation) throws BLException
{
BLException blException=new BLException();
if(designation==null)
{
blException.setGenericException("Designation Requried");
throw blException;
}
int code=designation.getCode();
String title=designation.getTitle();
if(code!=0)
{
blException.addException("code","Code should be zero");
}
if(title==null)
{
blException.addException("title","Title required");
title="";
}
else
{
title=title.trim();
if(title.length()==0)
{
blException.addException("title","Title required");
}
}
if(blException.hasExceptions())
{
throw blException;
}
Request request=new Request();
request.setManager(Managers.getManagersType(Managers.DESIGNATION)); //
request.setAction(Managers.getAction(Managers.Designation.ADD_DESIGNATION)); //
request.setArguments(designation);
NetworkClient client=new NetworkClient();
try
{
Response response=client.send(request);
if(response.hasException())
{
blException=(BLException)response.getException();
throw blException;
}
//need to do this
DesignationInterface d=(DesignationInterface)response.getResult();
designation.setCode(d.getCode());
}catch(NetworkException networkException)
{
blException.setGenericException(networkException.getMessage());
throw blException;
}
}
//********************************************************************************************
public void updateDesignation(DesignationInterface designation) throws BLException
{
BLException blException=new BLException();
if(designation==null)
{
blException.setGenericException("Designation required");
throw blException;
}
int code=designation.getCode();
String title=designation.getTitle();
if(code<=0)
{
blException.addException("code","Invalid code "+code);
}
if(title==null)
{
blException.addException("title","Title Required");
title="";
}
else
{
title=title.trim();
if(title.length()==0)
{
blException.addException("title","Title Required");
}
}
if(blException.hasExceptions())
{
throw blException;
}
Request request=new Request();
request.setManager(Managers.getManagersType(Managers.DESIGNATION)); //
request.setAction(Managers.getAction(Managers.Designation.UPDATE_DESIGNATION)); //
request.setArguments(designation);
NetworkClient client=new NetworkClient();
try
{
Response response=client.send(request);
if(response.hasException())
{
blException=(BLException)response.getException();
throw blException;
}
}catch(NetworkException networkException)
{
blException.setGenericException(networkException.getMessage());
throw blException;
}
}
//********************************************************************************************
public void removeDesignation(int code) throws BLException
{
BLException blException=new BLException();
if(code<=0)
{
blException.addException("code","Invalid code "+code);
throw blException;
}
Request request=new Request();
request.setManager(Managers.getManagersType(Managers.DESIGNATION)); 
request.setAction(Managers.getAction(Managers.Designation.REMOVE_DESIGNATION)); 
request.setArguments(new Integer(code));
NetworkClient client=new NetworkClient();
try
{
Response response=client.send(request);
if(response.hasException())
{
blException=(BLException)response.getException();
throw blException;
}
}catch(NetworkException networkException)
{
blException.setGenericException(networkException.getMessage());
throw blException;
}
}
//********************************************************************************************
public DesignationInterface getDesignationByCode(int code) throws BLException
{
BLException blException=new BLException();
if(code<=0)
{
blException.addException("code","Invalid code "+code);
throw blException;
}
Request request=new Request();
request.setManager(Managers.getManagersType(Managers.DESIGNATION)); 
request.setAction(Managers.getAction(Managers.Designation.GET_DESIGNATION_BY_CODE)); 
request.setArguments(new Integer(code));
NetworkClient client=new NetworkClient();
try
{
Response response=client.send(request);
if(response.hasException())
{
blException=(BLException)response.getException();
throw blException;
}
return (DesignationInterface)response.getResult();
}catch(NetworkException networkException)
{
blException.setGenericException(networkException.getMessage());
throw blException;
}
}
//********************************************************************************************
public DesignationInterface getDesignationByTitle(String title) throws BLException
{
BLException blException=new BLException();
if(title==null)
{
blException.addException("title","Title required");
throw blException;
}
else
{
title=title.trim();
if(title.length()==0) 
{
blException.addException("title","Title required");
throw blException;
}
}
Request request=new Request();
request.setManager(Managers.getManagersType(Managers.DESIGNATION)); 
request.setAction(Managers.getAction(Managers.Designation.GET_DESIGNATION_BY_TITLE)); 
request.setArguments(new Integer(title));
NetworkClient client=new NetworkClient();
try
{
Response response=client.send(request);
if(response.hasException())
{
blException=(BLException)response.getException();
throw blException;
}
return (DesignationInterface)response.getResult();
}catch(NetworkException networkException)
{
blException.setGenericException(networkException.getMessage());
throw blException;
}
}
//********************************************************************************************
public boolean isDesignationCodeExists(int code) 
{
if(code<=0)
{
return false;
}
Request request=new Request();
request.setManager(Managers.getManagersType(Managers.DESIGNATION)); 
request.setAction(Managers.getAction(Managers.Designation.IS_DESIGNATION_CODE_EXISTS)); 
request.setArguments(new Integer(code));
NetworkClient client=new NetworkClient();
try
{
Response response=client.send(request);
return (Boolean)response.getResult(); //No need, Unboxing will be done implictly
}catch(NetworkException networkException)
{
throw new RuntimeException(networkException.getMessage());
}
}
//********************************************************************************************
public boolean isDesignationTitleExists(String title)
{
if(title==null)
{
return false;
}
else
{
title=title.trim();
if(title.length()==0) 
{
return true;
}
}
Request request=new Request();
request.setManager(Managers.getManagersType(Managers.DESIGNATION)); 
request.setAction(Managers.getAction(Managers.Designation.IS_DESIGNATION_TITLE_EXISTS)); 
request.setArguments(title);
NetworkClient client=new NetworkClient();
try
{
Response response=client.send(request);
return (Boolean)response.getResult();
}catch(NetworkException networkException)
{
throw new RuntimeException(networkException.getMessage());
}
}
//********************************************************************************************
public int getDesignationCount()
{
Request request=new Request();
request.setManager(Managers.getManagersType(Managers.DESIGNATION)); 
request.setAction(Managers.getAction(Managers.Designation.GET_DESIGNATION_BY_TITLE)); 
NetworkClient client=new NetworkClient();
try
{
Response response=client.send(request);
return ((Integer)response.getResult()).intValue(); //No need, Unboxing will be done implictly
}catch(NetworkException networkException)
{
throw new RuntimeException(networkException.getMessage());
}
}
//********************************************************************************************
public Set<DesignationInterface> getDesignations()
{
Request request=new Request();
request.setManager(Managers.getManagersType(Managers.DESIGNATION)); 
request.setAction(Managers.getAction(Managers.Designation.GET_DESIGNATIONS)); 
NetworkClient client=new NetworkClient();
try
{
Response response=client.send(request);
Set<DesignationInterface> designations=(Set<DesignationInterface>)response.getResult();
/*designations.forEach((e)->{
System.out.println(e.getTitle());
});  for testing purpuse*/ 
return designations; //No need, Unboxing will be done implictly
}catch(NetworkException networkException)
{
throw new RuntimeException(networkException.getMessage());
}
}
}