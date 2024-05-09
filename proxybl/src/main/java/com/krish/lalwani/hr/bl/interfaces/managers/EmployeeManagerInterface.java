package com.krish.lalwani.hr.bl.interfaces.managers;
import com.krish.lalwani.hr.bl.exceptions.*;
import com.krish.lalwani.hr.bl.interfaces.pojo.*;
import com.krish.lalwani.hr.bl.pojo.*;
import java.util.*;
public interface EmployeeManagerInterface
{
public void addEmployee(EmployeeInterface employee) throws BLException;
public void updateEmployee(EmployeeInterface employee) throws BLException;
public void removeEmployee(String employeeId) throws BLException;
public EmployeeInterface getEmployeeByEmployeeId(String employeeId) throws BLException;
public EmployeeInterface getEmployeeByPANNumber(String panNumber) throws BLException;
public EmployeeInterface getEmployeeByAadharCardNumber(String aadharCardNumber) throws BLException;
public boolean employeeIdExists(String employeeId) throws BLException;
public boolean employeePANNumberExists(String panNumber) throws BLException;
public boolean employeeAadharCardNumberExists(String aadharCardNumber) throws BLException;
public int getEmployeeCount();
public Set<EmployeeInterface> getEmployees();
public boolean designationAllotted(int designationCode) throws BLException;
public Set<EmployeeInterface> getEmployeesByDesignation(int designationCode) throws BLException;
public int getEmployeeCountByDesignationCode(int designationCode) throws BLException;
}