import com.krish.lalwani.hr.dl.exceptions.*;
import com.krish.lalwani.hr.dl.interfaces.dao.*;
import com.krish.lalwani.hr.dl.interfaces.dto.*;
import com.krish.lalwani.hr.dl.dao.*;
import com.krish.lalwani.hr.dl.dto.*;
class DesignationDeleteTestCase
{
public static void main(String gg[])
{
try
{
int code=Integer.parseInt(gg[0]);
DesignationDAOInterface designationDAO=new DesignationDAO();
designationDAO.delete(code);
System.out.println("Designation deleted");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}