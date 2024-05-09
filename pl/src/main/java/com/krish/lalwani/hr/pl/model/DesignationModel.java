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
public class DesignationModel extends AbstractTableModel 
{
private java.util.List<DesignationInterface> designations;
private DesignationManagerInterface designationManager;
private String[] columnTitle;
public DesignationModel()
{
this.populateDataStructur();
} 
public void populateDataStructur()
{
this.columnTitle=new String[2];
this.columnTitle[0]="S.No";
this.columnTitle[1]="Designation";
this.designations=new LinkedList<>();
try
{
designationManager=DesignationManager.getDesignationManager();
}catch(BLException blException)
{  
//System.out.println(blException.getMessage());
}
Set<DesignationInterface> blDesignations=designationManager.getDesignations();
for(DesignationInterface designation : blDesignations)
{
this.designations.add(designation);
}
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
}
public int getRowCount()
{
return designations.size();
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
return this.designations.get(rowIndex).getTitle();
}
public boolean isCellEditable()
{
return false;
}
public void add(DesignationInterface designation) throws BLException
{
designationManager.addDesignation(designation);
this.designations.add(designation);
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged(); //by the help of this method we can immideatly change the data of table
}
// data is changed but now we want to hilight new data which is added resently so that we want index of this data which is added reselently for this this method
public int indexOfDesignation(DesignationInterface designation) throws BLException
{
DesignationInterface d;
int index=0;
Iterator<DesignationInterface> iterator=this.designations.iterator();
while(iterator.hasNext())
{
d=iterator.next();
if(d.equals(designation)) return index;
index++;
}
BLException blException=new BLException();
blException.setGenericException("Invalid designation : "+designation.getTitle());
throw blException;
}
// the following method is for search whenever user search the title so the searched index of the follwing first title (Either partial inxed or full) index will be return of that first title
public int indexOfTitle(String title, boolean partialLeftSearch) throws BLException
{
DesignationInterface d;
int index=0;
Iterator<DesignationInterface> iterator=this.designations.iterator();
while(iterator.hasNext())
{
d=iterator.next();
if(partialLeftSearch)
{
if(d.getTitle().toUpperCase().startsWith(title.toUpperCase())) return index;
}
else
{
if(d.getTitle().equalsIgnoreCase(title.toUpperCase())) return index;
}
index++;
}
BLException blException=new BLException();
blException.setGenericException("Invalid title : "+title);
throw blException;
}
public DesignationInterface getDesignationAt(int index) throws BLException
{
if(index<0 || index>=this.designations.size())
{
BLException blException=new BLException();
blException.setGenericException("Invalid index : "+index);
throw blException;
}
return this.designations.get(index);
}
public void update(DesignationInterface designation) throws BLException
{
designationManager.updateDesignation(designation);
this.designations.remove(designation);
this.designations.add(designation);
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged();
}
public void remove(int code) throws BLException
{
designationManager.removeDesignation(code);
/*
Bad Way
DesignationInterface d=new Designation();
d.setCode(code);
d.setTitle("Something");
this.designations.remove(d);
*/
DesignationInterface d;
Iterator<DesignationInterface> iterator=this.designations.iterator();
int index=0;
while(iterator.hasNext())
{
d=iterator.next();
if(d.getCode()==code) break;
index++;
}
if(index==this.designations.size())
{
BLException blException=new BLException();
blException.setGenericException("Invalid designation code : "+code);
throw blException;
}
this.designations.remove(index);
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
document.setMargins(15,15,15,15);

Image logo=new Image(ImageDataFactory.create(this.getClass().getResource("/icons/logo_icon.png")));
Paragraph logoPara=new Paragraph();
logoPara.add(logo);

Paragraph companyNamePara=new Paragraph("ABCD Corporation");
PdfFont companyNameFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
companyNamePara.setFont(companyNameFont);
companyNamePara.setFontSize(18);

Paragraph reportTitlePara=new Paragraph("List of Designations");
PdfFont reportTitleFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
reportTitlePara.setFont(reportTitleFont);
reportTitlePara.setFontSize(15);

PdfFont pageNumberFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);

PdfFont columnTitleFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
PdfFont dataFont=PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);

Paragraph columnTitle1=new Paragraph("S.no");
columnTitle1.setFont(columnTitleFont);
columnTitle1.setFontSize(14);

Paragraph columnTitle2=new Paragraph("Designation");
columnTitle2.setFont(columnTitleFont);
columnTitle2.setFontSize(14);

float topTableColumnWidth[]={1,5};
float dataTableColumnWidth[]={1,5};
int pageNumber=0;
int sno=0;
int x=0;
boolean newPage=true;
int pageSize=20;
Table dataTable=null;
Table topTable;
Table pageNumberTable;
int numberOfPages=this.designations.size()/pageSize;
if((this.designations.size()%pageSize)!=0) numberOfPages++;
Cell cell;
Paragraph pageNumberPara;
Paragraph dataPara;
DesignationInterface designation;
while(x<this.designations.size())
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
cell=new Cell(1,2);
cell.add(reportTitlePara);
cell.setTextAlignment(TextAlignment.CENTER);
dataTable.addHeaderCell(cell);
dataTable.addHeaderCell(columnTitle1).setTextAlignment(TextAlignment.CENTER);
dataTable.addHeaderCell(columnTitle2).setTextAlignment(TextAlignment.CENTER);
newPage=false;
}
sno++;
//this code is for populating data in table
designation=this.designations.get(x);

dataPara=new Paragraph(String.valueOf(sno));
dataPara.setFont(dataFont);
dataPara.setFontSize(14);
cell=new Cell();
cell.add(dataPara);
cell.setTextAlignment(TextAlignment.RIGHT);
dataTable.addCell(cell);

dataPara=new Paragraph(designation.getTitle());
dataPara.setFont(dataFont);
dataPara.setFontSize(14);
cell=new Cell();
cell.add(dataPara);
cell.setTextAlignment(TextAlignment.LEFT);
dataTable.addCell(cell);
x++;
//this is footer code
if(sno%pageSize==0 || sno==this.designations.size())
{
document.add(dataTable);
document.add(new Paragraph("Software By : Team"));
if(sno<this.designations.size())
{
//this code is to add new page
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