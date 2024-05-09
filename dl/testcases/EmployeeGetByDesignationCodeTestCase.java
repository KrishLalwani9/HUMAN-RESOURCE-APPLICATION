import com.krish.lalwani.hr.dl.dao.*;
import com.krish.lalwani.hr.dl.interfaces.dao.*;
import com.krish.lalwani.hr.dl.dto.*;
import com.krish.lalwani.hr.dl.exceptions.*;
import com.krish.lalwani.hr.dl.interfaces.dto.*;
import java.math.BigDecimal;
import java.text.*;
import java.util.*;
class EmployeeGetByDesignationCodeTestCase
{
public static void main(String gg[])
{
try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
Set<EmployeeDTOInterface> employees;
employees=employeeDAO.getByDesignationCode(Integer.parseInt(gg[0])); //designationCode
System.out.println("Data Fatching starts from here");
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
for(EmployeeDTOInterface e : employees)
{
System.out.println(e.getEmployeeId());
System.out.println(e.getName());
System.out.println(e.getDesignationCode());
System.out.println(sdf.format(e.getDateOfBirth()));
System.out.println(e.getGender());
System.out.println(e.getIsIndian());
System.out.println(e.getBasicSalary().toPlainString());
System.out.println(e.getPANNumber());
System.out.println(e.getAadharCardNumber());
};
}catch(DAOException dao)
{
System.out.println(dao.getMessage());
}
}
}