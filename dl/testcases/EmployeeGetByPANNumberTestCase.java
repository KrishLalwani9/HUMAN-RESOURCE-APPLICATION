import com.krish.lalwani.hr.dl.dao.*;
import com.krish.lalwani.hr.dl.interfaces.dao.*;
import com.krish.lalwani.hr.dl.dto.*;
import com.krish.lalwani.hr.dl.exceptions.*;
import com.krish.lalwani.hr.dl.interfaces.dto.*;
import java.math.BigDecimal;
import java.text.*;
import java.util.Date;
class EmployeeGetByPANNumberTestCase
{
public static void main(String gg[])
{
try
{
EmployeeDTOInterface employeeDTO;
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
employeeDTO=employeeDAO.getByPANNumber(gg[0]);
System.out.println(employeeDTO.getEmployeeId());
System.out.println(employeeDTO.getName());
System.out.println(employeeDTO.getDesignationCode());
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
System.out.println(sdf.format(employeeDTO.getDateOfBirth()));
System.out.println(employeeDTO.getGender());
System.out.println(employeeDTO.getIsIndian());
System.out.println(employeeDTO.getBasicSalary().toPlainString());
System.out.println(employeeDTO.getPANNumber());
System.out.println(employeeDTO.getAadharCardNumber());
}catch(DAOException dao)
{
System.out.println(dao.getMessage());
}
}
}