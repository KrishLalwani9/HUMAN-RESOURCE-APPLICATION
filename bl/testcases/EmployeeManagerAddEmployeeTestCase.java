import com.krish.lalwani.hr.bl.interfaces.managers.*;
import com.krish.lalwani.hr.bl.interfaces.pojo.*;
import com.krish.lalwani.hr.bl.managers.*;
import com.krish.lalwani.hr.bl.pojo.*;
import com.krish.lalwani.hr.dl.interfaces.dao.*;
import com.krish.lalwani.hr.dl.interfaces.dto.*;
import com.krish.lalwani.enums.*;
import com.krish.lalwani.hr.bl.exceptions.*;
import com.krish.lalwani.hr.dl.exceptions.*;
import java.util.*;
import java.math.*;
class EmployeeManagerAddEmployeeTestCase
{
public static void main(String gg[])
{
try
{
EmployeeInterface employee=new Employee();
employee.setName("Harshat Meheta");
DesignationInterface desigantion=new Designation();
DesignationInterface designation=new Designation();
designation.setCode(3);
employee.setDesignation(designation);
employee.setDateOfBirth(new Date());
employee.setGender(GENDER.MALE);
employee.setIsIndian(true);
employee.setBasicSalary(new BigDecimal(20000000));
employee.setPANNumber("Pin1239");
employee.setAadharCardNumber("Ain1239");
EmployeeManagerInterface employeeManager=EmployeeManager.getEmployeeManager();
employeeManager.addEmployee(employee);
System.out.println("Employee add with code : "+employee.getEmployeeId());
}catch(BLException blException)
{
if(blException.hasGenericException()) System.out.println(blException.getGenericException());
List<String> properties=blException.getProperties();
for(String property : properties)
{
System.out.println(blException.getException(property));
} 
}
}
}