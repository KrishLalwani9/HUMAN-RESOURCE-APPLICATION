package com.krish.lalwani.hr.pl.model;
import com.krish.lalwani.hr.bl.managers.*;
import com.krish.lalwani.hr.bl.pojo.*;
import com.krish.lalwani.hr.bl.exceptions.*;
import com.krish.lalwani.hr.bl.interfaces.pojo.*;
import com.krish.lalwani.hr.bl.interfaces.managers.*;
import java.util.*;
import javax.swing.table.*;
import java.io.*;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.font.*;
import com.itextpdf.io.font.constants.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.*;
import com.itextpdf.layout.borders.*;
import com.itextpdf.io.image.*; 
import java.text.SimpleDateFormat;
import java.text.*;
public class EmployeeModel extends AbstractTableModel 
{
private java.util.List<EmployeeInterface> employees;
private EmployeeManagerInterface employeeManager;
private String[] columnTitle;
public EmployeeModel()
{
this.populateDataStructur();
} 
public void populateDataStructur()
{
this.columnTitle=new String[10];
this.columnTitle[0]="S.No.";
this.columnTitle[1]="Employee ID";
this.columnTitle[2]="Name";
this.columnTitle[3]="Designation code";
this.columnTitle[4]="Date of Birth";
this.columnTitle[5]="Gender";
this.columnTitle[6]="Is Indian";
this.columnTitle[7]="Basic salary";
this.columnTitle[8]="PAN card";
this.columnTitle[9]="Aadhar card";
this.employees=new LinkedList<>();
try
{
employeeManager=EmployeeManager.getEmployeeManager();
}catch(BLException blException)
{  
//System.out.println(blException.getMessage());
}
Set<EmployeeInterface> blEmployees=employeeManager.getEmployees();
for(EmployeeInterface employee : blEmployees)
{
this.employees.add(employee);
}
Collections.sort(this.employees,new Comparator<EmployeeInterface>(){
public int compare(EmployeeInterface left,EmployeeInterface right)
{
return left.getEmployeeId().toUpperCase().compareTo(right.getEmployeeId().toUpperCase());
}
});
}
public int getRowCount()
{
return employees.size();
}
public int getColumnCount()
{
return columnTitle.length;
}
public String getColumnName(int columnIndex)
{
return columnTitle[columnIndex];
}
public Class getColumnClass(int columnIndex)
{
Class c=null;
try
{
if(columnIndex==0) return Integer.class; // Spetial treatmentInteger.class is ass good as Class.forName("java.lang.Integer")
return String.class;
}catch(Exception e)
{
System.out.println(e);
}
return c;
}
public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex==0) return rowIndex+1;
else if(columnIndex==1) return this.employees.get(rowIndex).getEmployeeId();
else if(columnIndex==2) return this.employees.get(rowIndex).getName();
else if(columnIndex==3) return this.employees.get(rowIndex).getDesignation().getCode();
else if(columnIndex==4) 
{
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
String dateOfBirthString=sdf.format(this.employees.get(rowIndex).getDateOfBirth());
return dateOfBirthString;
}
else if(columnIndex==5) return this.employees.get(rowIndex).getGender();
else if(columnIndex==6) return this.employees.get(rowIndex).getIsIndian();
else if(columnIndex==7) return this.employees.get(rowIndex).getBasicSalary(); 
else if(columnIndex==8) return this.employees.get(rowIndex).getPANNumber();
else return this.employees.get(rowIndex).getAadharCardNumber();
}
public boolean isCellEditable()
{
return false;
}
public void add(EmployeeInterface employee) throws BLException
{
employeeManager.addEmployee(employee);
this.employees.add(employee);
Collections.sort(this.employees,new Comparator<EmployeeInterface>(){
public int compare(EmployeeInterface left,EmployeeInterface right)
{
return left.getEmployeeId().toUpperCase().compareTo(right.getEmployeeId().toUpperCase());
}
});
fireTableDataChanged(); //by the help of this method we can immideatly change the data of table
}
// data is changed but now we want to hilight new data which is added resently so that we want index of this data which is added reselently for this this method
public int indexOfEmployee(EmployeeInterface employee) throws BLException
{
EmployeeInterface d;
int index=0;
Iterator<EmployeeInterface> iterator=this.employees.iterator();
while(iterator.hasNext())
{
d=iterator.next();
if(d.equals(employee)) return index;
index++;
}
BLException blException=new BLException();
blException.setGenericException("Invalid employee : "+employee.getEmployeeId());
throw blException;
}
// the following method is for search whenever user search the title so the searched index of the follwing first title (Either partial inxed or full) index will be return of that first title
public int indexOfTitle(String title, boolean partialLeftSearch) throws BLException
{
EmployeeInterface d;
int index=0;
Iterator<EmployeeInterface> iterator=this.employees.iterator();
while(iterator.hasNext())
{
d=iterator.next();
if(partialLeftSearch)
{
if(d.getEmployeeId().toUpperCase().startsWith(title.toUpperCase())) return index;
}
else
{
if(d.getEmployeeId().equalsIgnoreCase(title.toUpperCase())) return index;
}
index++;
}
BLException blException=new BLException();
blException.setGenericException("Invalid title : "+title);
throw blException;
}
public EmployeeInterface getEmployeeAt(int index) throws BLException
{
if(index<0 || index>=this.employees.size())
{
BLException blException=new BLException();
blException.setGenericException("Invalid index : "+index);
throw blException;
}
return this.employees.get(index);
}
public void update(EmployeeInterface employee) throws BLException
{
employeeManager.updateEmployee(employee);
this.employees.remove(employee);
this.employees.add(employee);
Collections.sort(this.employees,new Comparator<EmployeeInterface>(){
public int compare(EmployeeInterface left,EmployeeInterface right)
{
return left.getEmployeeId().toUpperCase().compareTo(right.getEmployeeId().toUpperCase());
}
});
fireTableDataChanged();
}
public void remove(String employeeId) throws BLException
{
employeeManager.removeEmployee(employeeId);
/*
Bad Way
EmployeeInterface d=new Employee();
d.setCode(code);
d.setTitle("Something");
this.employees.remove(d);
*/
EmployeeInterface d;
Iterator<EmployeeInterface> iterator=this.employees.iterator();
int index=0;
while(iterator.hasNext())
{
d=iterator.next();
if(d.getEmployeeId()==employeeId) break;
index++;
}
if(index==this.employees.size())
{
BLException blException=new BLException();
blException.setGenericException("Invalid employee employeeId : "+employeeId);
throw blException;
}
this.employees.remove(index);
fireTableDataChanged();
}

public void exportToPDF(File file) throws BLException
{
try
{
if(file.exists()) file.delete();
PdfWriter pdfWriter=new PdfWriter(file);
PdfDocument pdfDocument=new PdfDocument(pdfWriter);
Document document=new Document(pdfDocument);
document.setMargins(10,10,10,10);

Image logo=new Image(ImageDataFactory.create(this.getClass().getResource("/icons/logo_icon.png")));
Paragraph logoPara=new Paragraph();
logoPara.add(logo);

Paragraph companyNamePara=new Paragraph("ABCD Corporation");
PdfFont companyNameFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
companyNamePara.setFont(companyNameFont);
companyNamePara.setFontSize(18);

Paragraph reportTitlePara=new Paragraph("List of Employees");
PdfFont reportTitleFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
reportTitlePara.setFont(reportTitleFont);
reportTitlePara.setFontSize(15);

PdfFont pageNumberFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);

PdfFont columnTitleFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
PdfFont dataFont=PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);

Paragraph columnTitle1=new Paragraph("S.no");
columnTitle1.setFont(columnTitleFont);
columnTitle1.setFontSize(10);

Paragraph columnTitle2=new Paragraph("Employee");
columnTitle2.setFont(columnTitleFont);
columnTitle2.setFontSize(10);

Paragraph columnTitle3=new Paragraph("Name");
columnTitle3.setFont(columnTitleFont);
columnTitle3.setFontSize(10);

Paragraph columnTitle4=new Paragraph("Designation");
columnTitle4.setFont(columnTitleFont);
columnTitle4.setFontSize(10);

Paragraph columnTitle5=new Paragraph("Date Of Birth");
columnTitle5.setFont(columnTitleFont);
columnTitle5.setFontSize(10);

Paragraph columnTitle6=new Paragraph("Gender");
columnTitle6.setFont(columnTitleFont);
columnTitle6.setFontSize(10);

Paragraph columnTitle7=new Paragraph("Is Indian");
columnTitle7.setFont(columnTitleFont);
columnTitle7.setFontSize(10);

Paragraph columnTitle8=new Paragraph("Basic Salary");
columnTitle8.setFont(columnTitleFont);
columnTitle8.setFontSize(10);

Paragraph columnTitle9=new Paragraph("Pan Card");
columnTitle9.setFont(columnTitleFont);
columnTitle9.setFontSize(10);

Paragraph columnTitle10=new Paragraph("Aadhar Card");
columnTitle10.setFont(columnTitleFont);
columnTitle10.setFontSize(10);

float topTableColumnWidth[]={1,5};
float dataTableColumnWidth[]={1f,1f,1f,1f,1f,1f,1f,1f,1f,1f};
int pageNumber=0;
int sno=0;
int x=0;
boolean newPage=true;
int pageSize=20;
Table dataTable=null;
Table topTable;
Table pageNumberTable;
int numberOfPages=this.employees.size()/pageSize;
if((this.employees.size()%pageSize)!=0) numberOfPages++;
Cell cell;
Paragraph pageNumberPara;
Paragraph dataPara;
EmployeeInterface employee;
while(x<this.employees.size())
{
if(newPage)
{
pageNumber++;
//this code is to create header
topTable=new Table(UnitValue.createPercentArray(topTableColumnWidth));
cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(logoPara);
topTable.addCell(cell);

cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(companyNamePara);
cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
topTable.addCell(cell);

pageNumberPara=new Paragraph(pageNumber+"/"+numberOfPages);
pageNumberPara.setFont(pageNumberFont);
pageNumberPara.setFontSize(13);

pageNumberTable=new Table(1);
pageNumberTable.setWidth(UnitValue.createPercentValue(100));

cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(pageNumberPara);
cell.setTextAlignment(TextAlignment.RIGHT);
pageNumberTable.addCell(cell);

document.add(topTable);
document.add(pageNumberTable);

dataTable=new Table(UnitValue.createPercentArray(dataTableColumnWidth));
dataTable.setWidth(UnitValue.createPercentValue(100));
cell=new Cell(1,10);
cell.add(reportTitlePara);
cell.setTextAlignment(TextAlignment.CENTER);
dataTable.addHeaderCell(cell);
dataTable.addHeaderCell(columnTitle1).setTextAlignment(TextAlignment.CENTER);
dataTable.addHeaderCell(columnTitle2).setTextAlignment(TextAlignment.CENTER);
dataTable.addHeaderCell(columnTitle3).setTextAlignment(TextAlignment.CENTER);
dataTable.addHeaderCell(columnTitle4).setTextAlignment(TextAlignment.CENTER);
dataTable.addHeaderCell(columnTitle5).setTextAlignment(TextAlignment.CENTER);
dataTable.addHeaderCell(columnTitle6).setTextAlignment(TextAlignment.CENTER);
dataTable.addHeaderCell(columnTitle7).setTextAlignment(TextAlignment.CENTER);
dataTable.addHeaderCell(columnTitle8).setTextAlignment(TextAlignment.CENTER);
dataTable.addHeaderCell(columnTitle9).setTextAlignment(TextAlignment.CENTER);
dataTable.addHeaderCell(columnTitle10).setTextAlignment(TextAlignment.CENTER);
newPage=false;
}
sno++;
//this code is for populating data in table
employee=this.employees.get(x);

dataPara=new Paragraph(String.valueOf(sno));
dataPara.setFont(dataFont);
dataPara.setFontSize(10);
cell=new Cell();
cell.add(dataPara);
cell.setTextAlignment(TextAlignment.RIGHT);
dataTable.addCell(cell);

dataPara=new Paragraph(employee.getEmployeeId());
dataPara.setFont(dataFont);
dataPara.setFontSize(10);
cell=new Cell();
cell.add(dataPara);
cell.setTextAlignment(TextAlignment.LEFT);
dataTable.addCell(cell);

dataPara=new Paragraph(employee.getName());
dataPara.setFont(dataFont);
dataPara.setFontSize(10);
cell=new Cell();
cell.add(dataPara);
cell.setTextAlignment(TextAlignment.LEFT);
dataTable.addCell(cell);

dataPara=new Paragraph(String.valueOf(employee.getDesignation().getCode())); 
dataPara.setFont(dataFont);
dataPara.setFontSize(10);
cell=new Cell();
cell.add(dataPara);
cell.setTextAlignment(TextAlignment.RIGHT);
dataTable.addCell(cell);

SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
String dateOfBirthString=sdf.format(employee.getDateOfBirth());
dataPara=new Paragraph(dateOfBirthString);
dataPara.setFont(dataFont);
dataPara.setFontSize(10);
cell=new Cell();
cell.add(dataPara);
cell.setTextAlignment(TextAlignment.LEFT);
dataTable.addCell(cell);

dataPara=new Paragraph(String.valueOf(employee.getGender()));
dataPara.setFont(dataFont);
dataPara.setFontSize(10);
cell=new Cell();
cell.add(dataPara);
cell.setTextAlignment(TextAlignment.LEFT);
dataTable.addCell(cell);

dataPara=new Paragraph(String.valueOf(employee.getIsIndian()));
dataPara.setFont(dataFont);
dataPara.setFontSize(10);
cell=new Cell();
cell.add(dataPara);
cell.setTextAlignment(TextAlignment.LEFT);
dataTable.addCell(cell);

dataPara=new Paragraph(String.valueOf(employee.getBasicSalary()));
dataPara.setFont(dataFont);
dataPara.setFontSize(10);
cell=new Cell();
cell.add(dataPara);
cell.setTextAlignment(TextAlignment.LEFT);
dataTable.addCell(cell);

dataPara=new Paragraph(employee.getPANNumber());
dataPara.setFont(dataFont);
dataPara.setFontSize(10);
cell=new Cell();
cell.add(dataPara);
cell.setTextAlignment(TextAlignment.LEFT);
dataTable.addCell(cell);

dataPara=new Paragraph(employee.getAadharCardNumber());
dataPara.setFont(dataFont);
dataPara.setFontSize(10);
cell=new Cell();
cell.add(dataPara);
cell.setTextAlignment(TextAlignment.LEFT);
dataTable.addCell(cell);

x++;
//this is footer code
if(sno%pageSize==0 || sno==this.employees.size())
{
document.add(dataTable);
document.add(new Paragraph("Software By : Team"));
if(sno<this.employees.size())
{
//this code is to add new pages
document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
newPage=true;
}
}
}
document.close();
}catch(Exception e)
{
BLException blException=new BLException();
blException.setGenericException(e.getMessage());
throw blException;
}
} 
}