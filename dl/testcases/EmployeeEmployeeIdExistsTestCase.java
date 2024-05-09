import com.krish.lalwani.hr.dl.dao.*;
import com.krish.lalwani.hr.dl.interfaces.dao.*;
import com.krish.lalwani.hr.dl.dto.*;
import com.krish.lalwani.hr.dl.exceptions.*;
import com.krish.lalwani.hr.dl.interfaces.dto.*;
import java.math.BigDecimal;
import java.text.*;
import java.util.Date;
class EmployeeEmployeeIdExistsTestCase
{
public static void main(String gg[])
{
try
{

System.out.println("Employee id Exists is "+new EmployeeDAO().employeeIdExists(gg[0]));
}catch(DAOException dao)
{
System.out.println(dao.getMessage());
}
}
}