package com.krish.lalwani.hr.dl.dto;
import com.krish.lalwani.hr.dl.interfaces.dto.*;
public class DesignationDTO implements DesignationDTOInterface
{
public int code;
public String title;
public DesignationDTO()
{
this.code=0;
this.title="";
}
public void setCode(int code)
{
this.code=code;
}
public int getCode()
{
return this.code;
}
public void setTitle(java.lang.String title)
{
this.title=title;
}
public java.lang.String getTitle()
{
return this.title;
}
public boolean equals(Object other)
{
if(!(other instanceof DesignationDTOInterface)) return false;
DesignationDTOInterface designationDTO=(DesignationDTO)other;
return this.code==designationDTO.getCode();
}
public int compareTo(DesignationDTOInterface other)
{
return this.code-other.getCode();
}
public int hashCode()
{
return this.code;
}
}