package com.krish.lalwani.hr.bl.managers;
import com.krish.lalwani.hr.bl.interfaces.managers.*;
import com.krish.lalwani.hr.bl.interfaces.pojo.*;
import com.krish.lalwani.hr.bl.exceptions.*;
import com.krish.lalwani.hr.bl.pojo.*;
import com.krish.lalwani.hr.dl.exceptions.*;
import com.krish.lalwani.hr.dl.interfaces.dao.*;
import com.krish.lalwani.hr.dl.interfaces.dto.*;
import com.krish.lalwani.hr.dl.dao.*;
import com.krish.lalwani.hr.dl.dto.*;
import java.util.*;
public class DesignationManager implements DesignationManagerInterface
{
private Map<Integer,DesignationInterface> codeWiseDesignationsMap;
private Map<String,DesignationInterface> titleWiseDesignationsMap;
private Set<DesignationInterface> designationsSet;
//private static DesignationManager designationManager=null;
private static DesignationManagerInterface designationManager=null;
private DesignationManager() throws BLException
{
populateDataStructures();
}
private void populateDataStructures() throws BLException
{
this.codeWiseDesignationsMap=new HashMap<>();
this.titleWiseDesignationsMap=new HashMap<>();
this.designationsSet=new TreeSet<>();
try
{
Set<DesignationDTOInterface> dlDesignations=new DesignationDAO().getAll();
DesignationInterface designation;
for(DesignationDTOInterface dlDesignation : dlDesignations)
{
designation=new Designation();
designation.setTitle(dlDesignation.getTitle());
designation.setCode(dlDesignation.getCode());
this.codeWiseDesignationsMap.put(new Integer(designation.getCode()),designation);
this.titleWiseDesignationsMap.put(designation.getTitle().toUpperCase(),designation);
this.designationsSet.add(designation);
}
}catch(DAOException daoException)
{
BLException blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
//********************************************************************************************
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
if(title.length()>0)
{
if(this.titleWiseDesignationsMap.containsKey(title.toUpperCase())) blException.addException("title","Designation : "+title+" exists.");
}
if(blException.hasExceptions())
{
throw blException;
}
try
{
DesignationDTOInterface designationDTO=new DesignationDTO();
designationDTO.setTitle(title);
DesignationDAOInterface designationDAO=new DesignationDAO();
designationDAO.add(designationDTO);
code=designationDTO.getCode();
designation.setCode(code);
DesignationInterface dsDesignation=new Designation();
dsDesignation.setCode(code);
dsDesignation.setTitle(title);
this.codeWiseDesignationsMap.put(new Integer(code),dsDesignation); //boxing
this.titleWiseDesignationsMap.put(title.toUpperCase(),dsDesignation);
this.designationsSet.add(dsDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
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
if(code<=0 || !this.codeWiseDesignationsMap.containsKey(code))
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
if(title.length()>0)
{
DesignationInterface d;
d=this.titleWiseDesignationsMap.get(title.toUpperCase());
if(d!=null && d.getCode()!=code)
{
blException.addException("title","Designation : "+title+" exists.");
}
}
if(blException.hasExceptions())
{
throw blException;
}
try
{
DesignationInterface dsDesignation=this.codeWiseDesignationsMap.get(code);
DesignationDTOInterface dlDesignation=new DesignationDTO();
dlDesignation.setCode(code);
dlDesignation.setTitle(title);
DesignationDAOInterface designationDAO=new DesignationDAO();
designationDAO.update(dlDesignation); //or new DesignationDAO().update(dlDesignation);
//code to remove that object from all ds you may cannot remove or directly put object data will will be overide against key but this cannot be aplicable in the case of titleWiseDesignationsMap because new title is comming so we have to update key also overriding possibal on the basis of key only if key exists then so you have to remove titleWiseDesignationsMap
this.codeWiseDesignationsMap.remove(code);
System.out.println(dsDesignation.getTitle().toUpperCase());
this.titleWiseDesignationsMap.remove(dsDesignation.getTitle().toUpperCase());
this.designationsSet.remove(dsDesignation);
//updateing dsDesignation
dsDesignation.setTitle(title);
//updateing our ds
this.codeWiseDesignationsMap.put(code,dsDesignation);
System.out.println(title.toUpperCase());
this.titleWiseDesignationsMap.put(title.toUpperCase(),dsDesignation);
this.designationsSet.add(dsDesignation);
System.out.println("*************************************************************************************************");
this.codeWiseDesignationsMap.forEach((k,somedesignation)->{
System.out.println(somedesignation.getCode()+" "+somedesignation.getTitle());
});
System.out.println("*************************************************************************************************");
this.titleWiseDesignationsMap.forEach((k,somedesignation)->{
System.out.println(k+" "+somedesignation.getCode()+" "+somedesignation.getTitle());
});
System.out.println("*************************************************************************************************");
for(DesignationInterface somedesignation : designationsSet)
{
System.out.println(somedesignation.getCode()+" "+somedesignation.getTitle());
}
System.out.println("*************************************************************************************************");
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
//********************************************************************************************
public void removeDesignation(int code) throws BLException
{
BLException blException=new BLException();
if(code<=0 || !this.codeWiseDesignationsMap.containsKey(code))
{
blException.addException("code","Invalid code "+code);
throw blException;
}
try
{
DesignationInterface dsDesignation=this.codeWiseDesignationsMap.get(code);
DesignationDAOInterface designationDAO=new DesignationDAO();
designationDAO.delete(code); //or new DesignationDAO().update(dlDesignation);
//code to remove that object from all ds you may cannot remove or directly put object data will will be overide against key but this cannot be aplicable in the case of titleWiseDesignationsMap because new title is comming so we have to update key also overriding possibal on the basis of key only if key exists then so you have to remove titleWiseDesignationsMap
this.codeWiseDesignationsMap.remove(code);
this.titleWiseDesignationsMap.remove(dsDesignation.getTitle().toUpperCase());
this.designationsSet.remove(dsDesignation);
System.out.println("*************************************************************************************************");
this.codeWiseDesignationsMap.forEach((k,somedesignation)->{
System.out.println(somedesignation.getCode()+" "+somedesignation.getTitle());
});
System.out.println("*************************************************************************************************");
this.titleWiseDesignationsMap.forEach((k,somedesignation)->{
System.out.println(k+" "+somedesignation.getCode()+" "+somedesignation.getTitle());
});
System.out.println("*************************************************************************************************");
for(DesignationInterface somedesignation : designationsSet)
{
System.out.println(somedesignation.getCode()+" "+somedesignation.getTitle());
}
System.out.println("*************************************************************************************************");
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
//********************************************************************************************
public DesignationInterface getDesignationByCode(int code) throws BLException
{
DesignationInterface designation=this.codeWiseDesignationsMap.get(code);
if(designation==null)
{
BLException blException=new BLException();
blException.addException("code","Invalid code "+code);
throw blException;
}
DesignationInterface dsDesignation=new Designation();
dsDesignation.setCode(designation.getCode());
dsDesignation.setTitle(designation.getTitle());
return dsDesignation;
}
//********************************************************************************************
DesignationInterface getDSDesignationByCode(int code)
{
DesignationInterface designation;
designation=this.codeWiseDesignationsMap.get(code);
return designation;
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
DesignationInterface designation=this.titleWiseDesignationsMap.get(title.toUpperCase());
if(designation==null)
{
blException.addException("title","Designation : "+title+" not exists.");
throw blException;
}
DesignationInterface dsDesignation=new Designation();
dsDesignation.setCode(designation.getCode());
dsDesignation.setTitle(designation.getTitle());
return dsDesignation;
}
//********************************************************************************************
public boolean isDesignationCodeExists(int code) 
{
return this.codeWiseDesignationsMap.containsKey(code);
}
//********************************************************************************************
public boolean isDesignationTitleExists(String title)
{
return this.titleWiseDesignationsMap.containsKey(title.toUpperCase());
}
//********************************************************************************************
public int getDesignationCount()
{
return this.codeWiseDesignationsMap.size();
}
//********************************************************************************************
public Set<DesignationInterface> getDesignations()
{
DesignationInterface dsDesignation;
Set<DesignationInterface> dsDesignations=new TreeSet<>(); 
for(DesignationInterface designation : this.designationsSet)
{
dsDesignation=new Designation();
dsDesignation.setCode(designation.getCode());
dsDesignation.setTitle(designation.getTitle());
dsDesignations.add(dsDesignation);
}
return dsDesignations;
}
}