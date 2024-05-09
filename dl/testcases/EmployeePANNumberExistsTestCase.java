import com.krish.lalwani.hr.dl.dao.*;
import com.krish.lalwani.hr.dl.interfaces.dao.*;
import com.krish.lalwani.hr.dl.dto.*;
import com.krish.lalwani.hr.dl.exceptions.*;
import com.krish.lalwani.hr.dl.interfaces.dto.*;
import java.math.BigDecimal;
import java.text.*;
import java.util.Date;
class EmployeePANNumberExistsTestCase
{
public static void main(String gg[])
{
try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
boolean EmployeeIdExists=employeeDAO.panNumberExists(gg[0]);
System.out.println("Employee id Exists is "+EmployeeIdExists);
}catch(DAOException dao)
{
System.out.println(dao.getMessage());
}
}
}