import com.krish.lalwani.hr.bl.interfaces.pojo.*;
import com.krish.lalwani.hr.bl.interfaces.managers.*;
import com.krish.lalwani.hr.bl.pojo.*;
import com.krish.lalwani.hr.bl.managers.*;
import com.krish.lalwani.hr.bl.exceptions.*;
import java.util.*;
class DesignationManagerGetDesignationCountTestCase
{
public static void main(String gg[])
{
try
{
System.out.println(DesignationManager.getDesignationManager().getDesignationCount());
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