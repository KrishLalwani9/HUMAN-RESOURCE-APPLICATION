import com.krish.lalwani.hr.bl.managers.*;
import com.krish.lalwani.hr.bl.pojo.*;
import com.krish.lalwani.hr.bl.interfaces.managers.*;
import com.krish.lalwani.hr.bl.interfaces.pojo.*;
import java.util.*;
import com.krish.lalwani.hr.bl.exceptions.*;
class DesignationManagerUpdateTestCase
{
public static void main(String gg[])
{
try
{
DesignationInterface designation=new Designation();
designation.setCode(Integer.parseInt(gg[0]));
designation.setTitle(gg[1]);
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
designationManager.updateDesignation(designation);
System.out.println("Designation update with code : "+designation.getCode());
}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getGenericException());
}
//or List<String> properties = blException.getProperties();
for(String property : blException.getProperties())
{
System.out.println(blException.getException(property));
}
}
}
}