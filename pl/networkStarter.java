import com.krish.lalwani.nframework.client.*;
class networkStarter
{
public static void main(String ...krish)
{
try
{
NFrameworkClient client=new NFrameworkClient();
String branchName=(String)client.execute("/main/starter");
System.out.println(branchName);
}catch(Throwable t)
{
System.out.println(t.getMessage());
}
}
}