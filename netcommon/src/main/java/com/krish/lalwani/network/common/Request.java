package com.krish.lalwani.network.common;
public class Request implements java.io.Serializable
{
private String manager;
private String action;
private Object [] arguments;
public void setManager(String manager)
{
this.manager=manager;
}
public String getManager()
{
return this.manager;
}
public void setAction(String action)
{
this.action=action;
}
public String getAction()
{
return this.action;
}
public void setArguments(Object ...arguments)
{
this.arguments=arguments;
}
public Object [] getArguments()
{
return this.arguments;
}
}