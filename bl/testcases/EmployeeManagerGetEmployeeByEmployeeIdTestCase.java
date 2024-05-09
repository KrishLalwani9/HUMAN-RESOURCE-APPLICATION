import com.krish.lalwani.hr.bl.interfaces.managers.*;
import com.krish.lalwani.hr.bl.interfaces.pojo.*;
import com.krish.lalwani.hr.bl.managers.*;
import com.krish.lalwani.hr.bl.pojo.*;
import com.krish.lalwani.hr.dl.interfaces.dao.*;
import com.krish.lalwani.hr.dl.interfaces.dto.*;
import com.krish.lalwani.enums.*;
import com.krish.lalwani.hr.bl.exceptions.*;
import com.krish.lalwani.hr.dl.exceptions.*;
import java.text.*;
import java.util.*;
import java.math.*;
class EmployeeManagerGetEmployeeByEmployeeIdTestCase
{
public static void main(String gg[])
{
try
{
EmployeeManagerInterface employeeManager=EmployeeManager.getEmployeeManager();
EmployeeInterface employee=employeeManager.getEmployeeByEmployeeId("a10000002");
System.out.println(employee.getEmployeeId());
System.out.println(employee.getName());
System.out.printf("Designation code %d,Designation title %s",employee.getDesignation().getCode(),employee.getDesignation().getTitle());
SimpleDateFormat sdf=new SimpleDateFormat("dd/mm/yyyy");
System.out.println(sdf.format(employee.getDateOfBirth()));
System.out.println(employee.getGender());
System.out.println(employee.getIsIndian());
System.out.println(employee.getBasicSalary().toPlainString());
System.out.println(employee.getPANNumber());
System.out.println(employee.getAadharCardNumber());
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