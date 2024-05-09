import com.krish.lalwani.hr.dl.dao.*;
import com.krish.lalwani.hr.dl.interfaces.dao.*;
import com.krish.lalwani.hr.dl.dto.*;
import com.krish.lalwani.hr.dl.exceptions.*;
import com.krish.lalwani.hr.dl.interfaces.dto.*;
import java.math.BigDecimal;
import java.text.*;
import java.util.Date;
class EmployeeIsDesignationAllotedTestCase
{
public static void main(String gg[])
{
try
{
int designationCode=Integer.parseInt(gg[0]);
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
boolean ff=employeeDAO.isDesignationAllotted(designationCode);
System.out.println("is designation code alloted "+designationCode+": "+ff);
}catch(DAOException dao)
{
System.out.println(dao.getMessage());
}
}
}