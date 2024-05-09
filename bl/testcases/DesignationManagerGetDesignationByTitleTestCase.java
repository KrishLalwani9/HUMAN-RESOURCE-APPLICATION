import com.krish.lalwani.hr.bl.interfaces.pojo.*;
import com.krish.lalwani.hr.bl.interfaces.managers.*;
import com.krish.lalwani.hr.bl.pojo.*;
import com.krish.lalwani.hr.bl.managers.*;
import com.krish.lalwani.hr.bl.exceptions.*;
import java.util.*;
class DesignationManagerGetDesignationByTitleTestCase
{
public static void main(String gg[])
{
try
{
DesignationInterface designation=DesignationManager.getDesignationManager().getDesignationByTitle(gg[0]);
System.out.println(designation.getCode()+" "+designation.getTitle());
}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getGenericException());
}
if(blException.hasExceptions())
{
List<String> properties=blException.getProperties();
for(String property : properties)
{
System.out.println(blException.getException(property));
} 
}
}
}
}