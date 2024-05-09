package com.krish.lalwani.hr.bl.managers;
import com.krish.lalwani.hr.bl.interfaces.managers.*;
import com.krish.lalwani.hr.bl.interfaces.pojo.*;
import com.krish.lalwani.hr.bl.exceptions.*;
import com.krish.lalwani.hr.bl.pojo.*;
import java.util.*;
import com.krish.lalwani.network.common.*;
import com.krish.lalwani.network.common.exceptions.*;
import com.krish.lalwani.network.client.*;
public class EmployeeManager implements EmployeeManagerInterface
{
//private static EmployeeManager employeeManager=null;
private static EmployeeManagerInterface employeeManager=null;
private EmployeeManager() throws BLException
{
}
public static EmployeeManagerInterface getEmployeeManager() throws BLException 
{
if(employeeManager==null) employeeManager=new EmployeeManager();
return employeeManager;
}
//********************************************************************************************
public void addEmployee(EmployeeInterface employee) throws BLException
{
BLException blException=new BLException();
if(employee==null)
{
blException.setGenericException("Employee is null");
throw blException;
}
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
String employeeId=employee.getEmployeeId();
String name=employee.getName();
DesignationInterface designation=employee.getDesignation();
Date dateOfBirth=employee.getDateOfBirth();
char gender=employee.getGender();
boolean isIndian=employee.getIsIndian();
BigDecimal basicSalary=employee.getBasicSalary();
String panNumber=employee.getPANNumber();
String aadharCardNumber=employee.getAadharCardNumber();
if(employeeId!=null && employeeId.trim().length()>0)
{
blException.addException("employeeId","employeeId should be nil/empty");
}
if(name==null)
{
blException.addException("name","Name required"); 
}
else
{
name=name.trim();
if(name.length()==0)
{
blException.addException("name","Length of name is zero");
}
}
if(designation==null)
{
blException.addException("designation","Desigantin required");
}
else
{
if(designationManager.isDesignationCodeExists(designation.getCode())==false)
{
blException.addException("designation","Invalid Designation");
}
}
if(dateOfBirth==null)
{
blException.addException("dateOfBirth","Date of birth required");
}
if(gender==' ')
{
blException.addException("gender","Gender required");
}
if(basicSalary==null)
{
blException.addException("basicSalary","Basic salary required");
}
else
{
if(basicSalary.signum()==-1)
{
blException.addException("basicSalary","Basic salary should not be negative");
}
}
if(panNumber==null)
{
blException.addException("panNumber","Pan Number required");
}
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0)
{
blException.addException("panNumber","Pan number required");
}
}
if(aadharCardNumber==null)
{
blException.addException("aadharCardNumber","Aadhar card number required");
}
else
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)
{
blException.addException("aadharCardNumber","Aadhar card number required");
}
}
}
if(blException.hasExceptions())
{
throw blException;
}

Request request=new Request();
request.setManager(Managers.getManagersType(Managers.EMPLOYEE)); //
request.setAction(Managers.getAction(Managers.Employee.ADD_EMPLOYEE)); //
request.setArguments(employee);
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
EmployeeInterface d=(EmployeeInterface)response.getResult();
employee.setEmployeeId(d.getEmployeeId());
}catch(NetworkException networkException)
{
blException.setGenericException(networkException.getMessage());
throw blException;
}
}
//********************************************************************************************
public void updateEmployee(EmployeeInterface employee) throws BLException
{
BLException blException=new BLException();
if(employee==null)
{
blException.setGenericException("Employee required");
throw blException;
}
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
String employeeId=employee.getEmployeeId();
String name=employee.getName();
DesignationInterface designation=employee.getDesignation();
Date dateOfBirth=employee.getDateOfBirth();
char gender=employee.getGender();
boolean isIndian=employee.getIsIndian();
BigDecimal basicSalary=employee.getBasicSalary();
String panNumber=employee.getPANNumber();
String aadharCardNumber=employee.getAadharCardNumber();
if(employeeId==null)
{
blException.addException("employeeId","employee Id required");
throw blException;
}
else
{
employeeId=employeeId.trim();
if(employeeId.length()==0 || this.employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase())==false)
{
blException.addException("employeeId","Invalid Employee Id "+employeeId);
throw blException;
}
}
if(name==null)
{
blException.addException("name","Name required"); 
}
else
{
name=name.trim();
if(name.length()==0)
{
blException.addException("name","Length of name is zero");
}
}
if(designation==null)
{
blException.addException("designation","Desigantin required");
}
else
{
if(designationManager.isDesignationCodeExists(designation.getCode())==false)
{
blException.addException("designation","Invalid Designation");
}
}
if(dateOfBirth==null)
{
blException.addException("dateOfBirth","Date of birth required");
}
if(gender==' ')
{
blException.addException("gender","Gender required");
}
if(basicSalary==null)
{
blException.addException("basicSalary","Basic salary required");
}
else
{
if(basicSalary.signum()==-1)
{
blException.addException("basicSalary","Basic salary should not be negative");
}
}
if(panNumber==null)
{
blException.addException("panNumber","Pan Number required");
}
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0)
{
blException.addException("panNumber","Pan number required");
}
}
if(aadharCardNumber==null)
{
blException.addException("aadharCardNumber","Aadhar card number required");
}
else
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)
{
blException.addException("aadharCardNumber","Aadhar card number required");
}
}
if(blException.hasExceptions())
{
throw blException;
}
Request request=new Request();
request.setManager(Managers.getManagersType(Managers.EMPLOYEE)); //
request.setAction(Managers.getAction(Managers.Employee.UPDATE_EMPLOYEE)); //
request.setArguments(employee);
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
public void removeEmployee(String employeeId) throws BLException
{
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
if(employeeId==null)
{
BLException blException=new BLException();  
blException.addException("employeeId","employee Id required");
throw blException;
}
else
{
BLException blException=new BLException();  
employeeId=employeeId.trim();
if(employeeId.length()==0)
{
blException.addException("employeeId","Invalid Employee Id "+employeeId);
throw blException;
}
}
Request request=new Request();
request.setManager(Managers.getManagersType(Managers.EMPLOYEE)); 
request.setAction(Managers.getAction(Managers.Employee.REMOVE_EMPLOYEE)); 
request.setArguments(employeeId);
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
public EmployeeInterface getEmployeeByEmployeeId(String employeeId) throws BLException
{
BLException blException=new BLException();
if(employeeId==null)
{
blException.addException("employeeId","Employee id is null");
throw blException;
}
EmployeeInterface employee=this.employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
if(employee==null)
{
blException.addException("employeeId","Invalid employee id "+employeeId);
throw blException;
}
Request request=new Request();
request.setManager(Managers.getManagersType(Managers.EMPLOYEE)); 
request.setAction(Managers.getAction(Managers.Employee.GET_EMPLOYEE_BY_CODE)); 
request.setArguments(employeeId);
NetworkClient client=new NetworkClient();
try
{
Response response=client.send(request);
if(response.hasException())
{
blException=(BLException)response.getException();
throw blException;
}
return (EmployeeInterface)response.getResult();
}catch(NetworkException networkException)
{
blException.setGenericException(networkException.getMessage());
throw blException;
}
}
//********************************************************************************************
public EmployeeInterface getEmployeeByTitle(String title) throws BLException
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
request.setManager(Managers.getManagersType(Managers.EMPLOYEE)); 
request.setAction(Managers.getAction(Managers.Employee.GET_EMPLOYEE_BY_TITLE)); 
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
return (EmployeeInterface)response.getResult();
}catch(NetworkException networkException)
{
blException.setGenericException(networkException.getMessage());
throw blException;
}
}
//********************************************************************************************
public EmployeeInterface getEmployeeByPANNumber(String panNumber) throws BLException
{
BLException blException=new BLException();
if(panNumber==null)
{
blException.addException("panNumber","pan number is null");
throw blException;
}
EmployeeInterface employee=this.panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
if(employee==null)
{
blException.addException("panNumber","Invalid pan number "+panNumber);
throw blException;
}
EmployeeInterface dsEmployee=new Employee();
dsEmployee.setEmployeeId(employee.getEmployeeId());
dsEmployee.setName(employee.getName());
DesignationInterface designation=new Designation();
designation.setCode(employee.getDesignation().getCode());
designation.setTitle(employee.getDesignation().getTitle());
dsEmployee.setDesignation(designation);
dsEmployee.setDateOfBirth((Date)employee.getDateOfBirth().clone());
dsEmployee.setGender((employee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
dsEmployee.setIsIndian(employee.getIsIndian());
dsEmployee.setBasicSalary(employee.getBasicSalary());
dsEmployee.setPANNumber(employee.getPANNumber());
dsEmployee.setAadharCardNumber(employee.getAadharCardNumber());
return dsEmployee;
}
//********************************************************************************************
public EmployeeInterface getEmployeeByAadharCardNumber(String aadharCardNumber) throws BLException
{
BLException blException=new BLException();
if(aadharCardNumber==null)
{
blException.addException("aadharCardNumber","Aadhar Card Number is null");
throw blException;
}
EmployeeInterface employee=this.aadharCardNumberWiseEmployeesMap.get(aadharCardNumber.toUpperCase());
if(employee==null)
{
blException.addException("aadharCardNumber","Invalid aadhar card number "+aadharCardNumber);
throw blException;
}
EmployeeInterface dsEmployee=new Employee();
dsEmployee.setEmployeeId(employee.getEmployeeId());
dsEmployee.setName(employee.getName());
DesignationInterface designation=new Designation();
designation.setCode(employee.getDesignation().getCode());
designation.setTitle(employee.getDesignation().getTitle());
dsEmployee.setDesignation(designation);
dsEmployee.setDateOfBirth((Date)employee.getDateOfBirth().clone());
dsEmployee.setGender((employee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
dsEmployee.setIsIndian(employee.getIsIndian());
dsEmployee.setBasicSalary(employee.getBasicSalary());
dsEmployee.setPANNumber(employee.getPANNumber());
dsEmployee.setAadharCardNumber(employee.getAadharCardNumber());
return dsEmployee;
}
//********************************************************************************************
public boolean employeeIdExists(String employeeId) throws BLException
{
BLException blException=new BLException();
if(employeeId==null)
{
blException.addException("employeeId","Employee id is null");
throw blException;
}
return this.employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase());
}
//********************************************************************************************
public boolean employeePANNumberExists(String panNumber) throws BLException
{
BLException blException=new BLException();
if(panNumber==null)
{
blException.addException("panNumber","pan number is null");
throw blException;
}
return this.panNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase());
}
//********************************************************************************************
public boolean employeeAadharCardNumberExists(String aadharCardNumber) throws BLException
{
BLException blException=new BLException();
if(aadharCardNumber==null)
{
blException.addException("aadharCardNumber","Aadhar Card Number is null");
throw blException;
}
return this.aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber.toUpperCase());
}
//********************************************************************************************
public int getEmployeeCount()
{
return this.employeesSet.size();
}
//********************************************************************************************
public Set<EmployeeInterface> getEmployees()
{
Set<EmployeeInterface> employees=new TreeSet<>();
EmployeeInterface dsEmployee;
DesignationInterface designation;
for(EmployeeInterface employee : employeesSet)
{
dsEmployee=new Employee();
dsEmployee.setEmployeeId(employee.getEmployeeId());
dsEmployee.setName(employee.getName());
designation=new Designation();
designation.setCode(employee.getDesignation().getCode());
designation.setTitle(employee.getDesignation().getTitle());
dsEmployee.setDesignation(designation);
dsEmployee.setDateOfBirth((Date)employee.getDateOfBirth().clone());
dsEmployee.setGender((employee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
dsEmployee.setIsIndian(employee.getIsIndian());
dsEmployee.setBasicSalary(employee.getBasicSalary());
dsEmployee.setPANNumber(employee.getPANNumber());
dsEmployee.setAadharCardNumber(employee.getAadharCardNumber());
employees.add(dsEmployee);
}
return employees;
}
//********************************************************************************************
public boolean designationAllotted(int designationCode) throws BLException
{
BLException blException=new BLException();
if(DesignationManager.getDesignationManager().isDesignationCodeExists(designationCode)==false)
{
blException.addException("designation","Invalid designation code "+designationCode);
throw blException;
}
Set<EmployeeInterface> ets=this.designationCodeWiseEmployeesMap.get(designationCode);
if(ets==null)
{
return false;
}
return true;
}
//********************************************************************************************
public Set<EmployeeInterface> getEmployeesByDesignation(int designationCode) throws BLException
{
BLException blException=new BLException();
if(DesignationManager.getDesignationManager().isDesignationCodeExists(designationCode)==false)
{
blException.addException("designation","Invalid designation code "+designationCode);
throw blException;
}
Set<EmployeeInterface> ets=this.designationCodeWiseEmployeesMap.get(designationCode);
Set<EmployeeInterface> employeesAgainstDesignationCode=new TreeSet<>();
EmployeeInterface dsEmployee;   
DesignationInterface designation;
if(ets==null)
{
return employeesAgainstDesignationCode;
}
for(EmployeeInterface employee : ets)
{
dsEmployee=new Employee();
dsEmployee.setEmployeeId(employee.getEmployeeId());
dsEmployee.setName(employee.getName());
designation=new Designation();
designation.setCode(employee.getDesignation().getCode());
designation.setTitle(employee.getDesignation().getTitle());
dsEmployee.setDesignation(designation);
dsEmployee.setDateOfBirth((Date)employee.getDateOfBirth().clone());
dsEmployee.setGender((employee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
dsEmployee.setIsIndian(employee.getIsIndian());
dsEmployee.setBasicSalary(employee.getBasicSalary());
dsEmployee.setPANNumber(employee.getPANNumber());
dsEmployee.setAadharCardNumber(employee.getAadharCardNumber());
employeesAgainstDesignationCode.add(dsEmployee);
}
return employeesAgainstDesignationCode;
}
//********************************************************************************************
public int getEmployeeCountByDesignationCode(int designationCode) throws BLException
{
BLException blException=new BLException();
if(DesignationManager.getDesignationManager().isDesignationCodeExists(designationCode)==false)
{
blException.addException("designation","Invalid designation code "+designationCode);
throw blException;
}
Set<EmployeeInterface> ets=this.designationCodeWiseEmployeesMap.get(designationCode);
if(ets==null)
{
return 0;
}
return ets.size();
}
}