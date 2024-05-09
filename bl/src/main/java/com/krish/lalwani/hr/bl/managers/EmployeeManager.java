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
import com.krish.lalwani.enums.*;
import java.text.*;
import java.math.*;
import java.util.*;
public class EmployeeManager implements EmployeeManagerInterface
{
private Map<String,EmployeeInterface> employeeIdWiseEmployeesMap;
private Map<String,EmployeeInterface> panNumberWiseEmployeesMap;
private Map<String,EmployeeInterface> aadharCardNumberWiseEmployeesMap;
private Set<EmployeeInterface> employeesSet;
private Map<Integer,Set<EmployeeInterface>> designationCodeWiseEmployeesMap;
//private static DesignationManager designationManager=null;
private static EmployeeManagerInterface employeeManager=null;
private EmployeeManager() throws BLException
{
populateDataStructures();
}
private void populateDataStructures() throws BLException
{
this.employeeIdWiseEmployeesMap=new HashMap<>();
this.panNumberWiseEmployeesMap=new HashMap<>();
this.aadharCardNumberWiseEmployeesMap=new HashMap<>();
this.employeesSet=new TreeSet<>();
this.designationCodeWiseEmployeesMap=new HashMap<>();
try
{
Set<EmployeeDTOInterface> dlEmployees=new EmployeeDAO().getAll();
EmployeeInterface employee;
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
Set<EmployeeInterface> ets;
for(EmployeeDTOInterface dlEmployee : dlEmployees)
{
employee=new Employee();
employee.setEmployeeId(dlEmployee.getEmployeeId());
employee.setName(dlEmployee.getName());
employee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(dlEmployee.getDesignationCode()));
employee.setDateOfBirth(dlEmployee.getDateOfBirth());
employee.setGender((dlEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dlEmployee.getIsIndian());
employee.setBasicSalary(dlEmployee.getBasicSalary());
employee.setPANNumber(dlEmployee.getPANNumber());
employee.setAadharCardNumber(dlEmployee.getAadharCardNumber());
this.employeeIdWiseEmployeesMap.put(employee.getEmployeeId().toUpperCase(),employee);
this.panNumberWiseEmployeesMap.put(employee.getPANNumber().toUpperCase(),employee);
this.aadharCardNumberWiseEmployeesMap.put(employee.getAadharCardNumber().toUpperCase(),employee);
this.employeesSet.add(employee);
ets=this.designationCodeWiseEmployeesMap.get(employee.getDesignation().getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(employee);
this.designationCodeWiseEmployeesMap.put(new Integer(dlEmployee.getDesignationCode()),ets);
}
else
{
ets.add(employee);
}
}
}catch(DAOException daoException)
{
BLException blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
//********************************************************************************************
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
if(panNumber!=null && panNumber.length()>0)
{
if(this.panNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase()))
{
blException.addException("panNumber","pan number "+panNumber+" exists.");
}
}
if(aadharCardNumber!=null && aadharCardNumber.length()>0)
{
if(this.aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber.toUpperCase()))
{
blException.addException("aadharCardNumber","aadhar card number "+aadharCardNumber+" exists.");
}
}
if(blException.hasExceptions())
{
throw blException;
}
try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
EmployeeDTOInterface dlEmployee=new EmployeeDTO();
Set<EmployeeInterface> ets;
dlEmployee.setName(name);
dlEmployee.setDesignationCode(designation.getCode());
dlEmployee.setDateOfBirth(dateOfBirth);
dlEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dlEmployee.setIsIndian(isIndian);
dlEmployee.setBasicSalary(basicSalary);
dlEmployee.setPANNumber(panNumber);
dlEmployee.setAadharCardNumber(aadharCardNumber);
employeeDAO.add(dlEmployee);
employee.setEmployeeId(dlEmployee.getEmployeeId());
EmployeeInterface dsEmployee=new Employee();
dsEmployee.setEmployeeId(dlEmployee.getEmployeeId());
dsEmployee.setName(dlEmployee.getName());
dsEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode())); 
dsEmployee.setDateOfBirth((Date)dlEmployee.getDateOfBirth().clone());
dsEmployee.setGender((dlEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
dsEmployee.setIsIndian(dlEmployee.getIsIndian());
dsEmployee.setBasicSalary(dlEmployee.getBasicSalary());
dsEmployee.setPANNumber(dlEmployee.getPANNumber());
dsEmployee.setAadharCardNumber(dlEmployee.getAadharCardNumber());
this.employeesSet.add(dsEmployee);
this.employeeIdWiseEmployeesMap.put(dsEmployee.getEmployeeId().toUpperCase(),dsEmployee);
this.panNumberWiseEmployeesMap.put(panNumber.toUpperCase(),dsEmployee);
this.aadharCardNumberWiseEmployeesMap.put(aadharCardNumber.toUpperCase(),dsEmployee);
ets=this.designationCodeWiseEmployeesMap.get(dsEmployee.getDesignation().getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(dsEmployee);
this.designationCodeWiseEmployeesMap.put(new Integer(dsEmployee.getDesignation().getCode()),ets); //alloting new key value pair employees set against new designation code
}
else
{
ets.add(dsEmployee);
}
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
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
if(panNumber!=null && panNumber.length()>0)
{
EmployeeInterface ee=this.panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
if(ee!=null && employeeId.equalsIgnoreCase(ee.getEmployeeId())==false)
{
blException.addException("panNumber","pan number "+panNumber+" exists.");
}
}
if(aadharCardNumber!=null && aadharCardNumber.length()>0)
{
EmployeeInterface ee=this.aadharCardNumberWiseEmployeesMap.get(aadharCardNumber.toUpperCase());
if(ee!=null && employeeId.equalsIgnoreCase(ee.getEmployeeId())==false)
{
blException.addException("aadharCardNumber","aadhar card number "+aadharCardNumber+" exists.");
}
}
if(blException.hasExceptions())
{
throw blException;
}
try
{
EmployeeInterface dsEmployee=this.employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
Set<EmployeeInterface> ets;
DesignationInterface oldDesignation=dsEmployee.getDesignation();
String oldPANNumber=dsEmployee.getPANNumber();
String oldAadharCardNumber=dsEmployee.getAadharCardNumber();
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
EmployeeDTOInterface dlEmployee=new EmployeeDTO();
dlEmployee.setEmployeeId(dsEmployee.getEmployeeId());
dlEmployee.setName(name);
dlEmployee.setDesignationCode(designation.getCode());
dlEmployee.setDateOfBirth(dateOfBirth);
dlEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dlEmployee.setIsIndian(isIndian);
dlEmployee.setBasicSalary(basicSalary);
dlEmployee.setPANNumber(panNumber);
dlEmployee.setAadharCardNumber(aadharCardNumber);
employeeDAO.update(dlEmployee);
dsEmployee.setName(name);
dsEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode())); 
dsEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dsEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dsEmployee.setIsIndian(isIndian);
dsEmployee.setBasicSalary(basicSalary);
dsEmployee.setPANNumber(panNumber);
dsEmployee.setAadharCardNumber(aadharCardNumber);
this.employeesSet.remove(dsEmployee);
this.employeeIdWiseEmployeesMap.remove(employeeId.toUpperCase());
this.panNumberWiseEmployeesMap.remove(oldPANNumber.toUpperCase()); 
this.aadharCardNumberWiseEmployeesMap.remove(oldAadharCardNumber.toUpperCase());
this.employeesSet.add(dsEmployee);
this.employeeIdWiseEmployeesMap.put(dsEmployee.getEmployeeId().toUpperCase(),dsEmployee);
this.panNumberWiseEmployeesMap.put(panNumber.toUpperCase(),dsEmployee);
this.aadharCardNumberWiseEmployeesMap.put(aadharCardNumber.toUpperCase(),dsEmployee);
ets=this.designationCodeWiseEmployeesMap.get(oldDesignation.getCode());
if(oldDesignation.getCode()!=dsEmployee.getDesignation().getCode())
{
ets.remove(dsEmployee); //it remove on the basis of employeeId because it use our equals,hashCode,compareTo methods
ets=this.designationCodeWiseEmployeesMap.get(dsEmployee.getDesignation().getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(dsEmployee);
this.designationCodeWiseEmployeesMap.put(new Integer(dsEmployee.getDesignation().getCode()),ets);
}
else
{
ets.add(dsEmployee);
}
}
else
{
ets.add(dsEmployee);
}
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
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
if(employeeId.length()==0 || this.employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase())==false)
{
blException.addException("employeeId","Invalid Employee Id "+employeeId);
throw blException;
}
}
try
{
EmployeeInterface dsEmployee=this.employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
Set<EmployeeInterface> ets;
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
employeeDAO.delete(dsEmployee.getEmployeeId());
this.employeesSet.remove(dsEmployee);
this.employeeIdWiseEmployeesMap.remove(dsEmployee.getEmployeeId().toUpperCase());
this.panNumberWiseEmployeesMap.remove(dsEmployee.getPANNumber().toUpperCase());
this.aadharCardNumberWiseEmployeesMap.remove(dsEmployee.getAadharCardNumber().toUpperCase());
ets=this.designationCodeWiseEmployeesMap.get(dsEmployee.getDesignation().getCode());
ets.remove(dsEmployee);
}catch(DAOException daoException)
{
BLException blException=new BLException();  
blException.setGenericException(daoException.getMessage());
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