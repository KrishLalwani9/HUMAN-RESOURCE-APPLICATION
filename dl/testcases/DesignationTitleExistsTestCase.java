import com.krish.lalwani.hr.dl.exceptions.*;
import com.krish.lalwani.hr.dl.interfaces.dao.*;
import com.krish.lalwani.hr.dl.interfaces.dto.*;
import com.krish.lalwani.hr.dl.dao.*;
import com.krish.lalwani.hr.dl.dto.*;
class DesignationTitleExistsTestCase
{
public static void main(String gg[])
{
try
{
String title=gg[0];
DesignationDTOInterface designationDTO;
DesignationDAOInterface designationDAO=new DesignationDAO();
System.out.println("Designation title exists : "+designationDAO.titleExists(title));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}