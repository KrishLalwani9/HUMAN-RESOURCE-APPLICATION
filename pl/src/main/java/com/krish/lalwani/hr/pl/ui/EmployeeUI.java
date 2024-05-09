package com.krish.lalwani.hr.pl.ui;
import com.krish.lalwani.hr.pl.model.*;
import com.krish.lalwani.hr.bl.exceptions.*;
import com.krish.lalwani.hr.bl.interfaces.pojo.*;
import com.krish.lalwani.hr.bl.pojo.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.filechooser.*;
import java.util.*;
public class EmployeeUI extends JFrame implements DocumentListener,ListSelectionListener
{
private JLabel titleLabel;
private JLabel searchLabel;
private JTextField searchTextField;
private JButton clearSearchTextFieldButton;
private JLabel searchErrorLabel;
private EmployeeModel employeeModel;
private JTable employeeTable;
private Container container;
private JScrollPane scrollPane;
private EmployeePanel employeePanel;
private enum MODE{VIEW,ADD,EDIT,CANCEL,DELETE,EXPORT_TO_PDF};
private MODE mode;
private ImageIcon addIcon;
private ImageIcon editIcon;
private ImageIcon cancelIcon;
private ImageIcon saveIcon;
private ImageIcon deleteIcon;
private ImageIcon pdfIcon;
private ImageIcon logoIcon;
public EmployeeUI()
{
initComponents();
setAppearance();
addListeners();
employeePanel.setViewMode();
}
private void setViewMode()
{
this.mode=MODE.VIEW;
if(employeeModel.getRowCount()==0)
{
this.searchTextField.setEnabled(false);
this.clearSearchTextFieldButton.setEnabled(false);
this.employeeTable.setEnabled(false);
}
else
{
this.searchTextField.setEnabled(true);
this.clearSearchTextFieldButton.setEnabled(true);
this.employeeTable.setEnabled(true);
}
}
private void setAddMode()
{
this.mode=MODE.ADD;
this.searchTextField.setEnabled(false);
this.clearSearchTextFieldButton.setEnabled(false);
this.employeeTable.setEnabled(false);
}
private void setEditMode()
{
this.mode=MODE.EDIT;
this.searchTextField.setEnabled(false);
this.clearSearchTextFieldButton.setEnabled(false);
this.employeeTable.setEnabled(false);
}
private void setDeleteMode()
{
this.mode=MODE.DELETE;
this.searchTextField.setEnabled(false);
this.clearSearchTextFieldButton.setEnabled(false);
this.employeeTable.setEnabled(false);
}
private void setExportTOPDF()
{
this.mode=MODE.EXPORT_TO_PDF;
this.searchTextField.setEnabled(false);
this.clearSearchTextFieldButton.setEnabled(false);
this.employeeTable.setEnabled(false);
}
private void initComponents()
{
logoIcon=new ImageIcon(this.getClass().getResource("/icons/logo_icon.png"));
addIcon=new ImageIcon(this.getClass().getResource("/icons/add_icon.png"));
editIcon=new ImageIcon(this.getClass().getResource("/icons/edit_icon.png"));
deleteIcon=new ImageIcon(this.getClass().getResource("/icons/delete_icon.png"));
cancelIcon=new ImageIcon(this.getClass().getResource("/icons/cancel_icon.png"));
pdfIcon=new ImageIcon(this.getClass().getResource("/icons/pdf_icon.png"));
saveIcon=new ImageIcon(this.getClass().getResource("/icons/save_icon.png"));
titleLabel=new JLabel("Employees");
searchLabel=new JLabel("Search");
searchTextField=new JTextField();
clearSearchTextFieldButton=new JButton("x");
searchErrorLabel=new JLabel("");
employeeModel=new EmployeeModel();
employeeTable=new JTable(employeeModel);
scrollPane=new JScrollPane(employeeTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container=getContentPane();
employeePanel=new EmployeePanel();
}
private void setAppearance()
{
Font titleFont=new Font("Verdana",Font.BOLD,18);
Font captionFont=new Font("Verdana",Font.BOLD,16);
Font dataFont=new Font("Verdana",Font.PLAIN,16);
Font searchErrorFont=new Font("Verdana",Font.BOLD,12);
Font columnHeaderFont=new Font("Verdana",Font.BOLD,16);
titleLabel.setFont(titleFont);
searchLabel.setFont(captionFont);
searchTextField.setFont(dataFont);
searchErrorLabel.setFont(searchErrorFont);
searchErrorLabel.setForeground(Color.red);
employeeTable.setFont(dataFont);
employeeTable.setRowHeight(35);
employeeTable.getColumnModel().getColumn(0).setPreferredWidth(20);
employeeTable.getColumnModel().getColumn(1).setPreferredWidth(100);
employeeTable.getColumnModel().getColumn(2).setPreferredWidth(150);
employeeTable.getColumnModel().getColumn(3).setPreferredWidth(80);
employeeTable.getColumnModel().getColumn(4).setPreferredWidth(150);
employeeTable.getColumnModel().getColumn(5).setPreferredWidth(50);
employeeTable.getColumnModel().getColumn(6).setPreferredWidth(50);
employeeTable.getColumnModel().getColumn(7).setPreferredWidth(100);
employeeTable.getColumnModel().getColumn(8).setPreferredWidth(130);
employeeTable.getColumnModel().getColumn(9).setPreferredWidth(130);
JTableHeader header=employeeTable.getTableHeader();
header.setFont(columnHeaderFont);
header.setReorderingAllowed(false);
header.setResizingAllowed(false);
employeeTable.setRowSelectionAllowed(true);
employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
container.setLayout(null);
int w=1400;
int h=800;
int lm,tm; // for convineance of leftMargin and topMargin
lm=0;
tm=0;
titleLabel.setBounds(lm+10,tm+10,200,40);
searchLabel.setBounds(lm+10,tm+10+40+10,80,30);
searchTextField.setBounds(lm+10+85+5,tm+10+40+10,400,30);
searchErrorLabel.setBounds(lm+10+85+5+330,tm+10+20+5,90,25);
clearSearchTextFieldButton.setBounds(lm+10+85+5+400+5,tm+10+40+10,30,30);
scrollPane.setBounds(lm+10,tm+10+40+10+30+10,1365,340);
employeePanel.setBounds(lm+10,tm+10+40+10+30+10+340+10,1365,200);
container.add(titleLabel);
container.add(searchLabel);
container.add(searchErrorLabel);
container.add(searchTextField);
container.add(clearSearchTextFieldButton);
container.add(scrollPane);
container.add(employeePanel);
setIconImage(logoIcon.getImage());
setSize(w,h);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
setLocation((d.width/2-w/2),(d.height/2-h/2));
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
private void addListeners()
{
this.searchTextField.getDocument().addDocumentListener(this);
this.clearSearchTextFieldButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent av)
{
searchTextField.setText("");
searchTextField.requestFocus();
}
});
employeeTable.getSelectionModel().addListSelectionListener(this);
}
public void searchEmployee()
{
searchErrorLabel.setText("");
String title=this.searchTextField.getText().trim();
if(title.length()==0) return;
int rowIndex=0;
try
{
rowIndex=employeeModel.indexOfTitle(title,true);
}catch(BLException blException)
{
searchErrorLabel.setText("Not Found");
return;
}
employeeTable.setRowSelectionInterval(rowIndex,rowIndex); //this setRowSelectionInterval method select the row of which we gave it index of.
Rectangle rectangle=employeeTable.getCellRect(rowIndex,0,true); // this getCellRect method return rapped up infromation of a cell in Rectangle object
employeeTable.scrollRectToVisible(rectangle); // it scroll the row of which we gave it address of in the form of rectangle object
}
public void changedUpdate(DocumentEvent de)
{
System.out.println("changedUpdate got called");
searchEmployee();
}
public void removeUpdate(DocumentEvent de)
{
searchEmployee();
}
public void insertUpdate(DocumentEvent de)
{
searchEmployee();
}
public void valueChanged(ListSelectionEvent lse)
{
int selectedRowIndex=employeeTable.getSelectedRow();
try
{
EmployeeInterface employee;
employee=this.employeeModel.getEmployeeAt(selectedRowIndex);
employeePanel.setEmployee(employee);
}catch(BLException blException)
{
employeePanel.clearEmployee();
}
}
//Inner class starts here
class EmployeePanel extends JPanel
{
private JLabel titleCaptionLabel;
private JLabel nameLabel;
private JTextField nameTextField;
private JButton clearnameTextFieldButton;
private JPanel buttonsPanel;
private JButton addButton;
private JButton editButton;
private JButton cancelButton;
private JButton deleteButton;
private JButton exportToPDFButton;
private EmployeeInterface employee;
EmployeePanel()
{
setBorder(BorderFactory.createLineBorder(new Color(150,150,150)));
initComponents();
setAppearance();
addListeners();
}
public void setEmployee(EmployeeInterface employee) //mainly for setting titleLable
{
this.employee=employee;
this.nameLabel.setText(this.employee.getName());
}
public void clearEmployee()
{
this.employee=null;
this.nameLabel.setText("");
}
public void initComponents()
{
employee=null;
titleCaptionLabel=new JLabel("Employee");
nameLabel=new JLabel("");
nameTextField=new JTextField();
clearnameTextFieldButton=new JButton("X");
buttonsPanel=new JPanel();
addButton=new JButton(addIcon);
editButton=new JButton(editIcon);
cancelButton=new JButton(cancelIcon);
deleteButton=new JButton(deleteIcon);
exportToPDFButton=new JButton(pdfIcon);
}
public void setAppearance()
{
Font captionFont=new Font("Verdana",Font.BOLD,16);
Font dataFont=new Font("Verdana",Font.PLAIN,16);
titleCaptionLabel.setFont(captionFont);
nameLabel.setFont(dataFont);
nameTextField.setFont(dataFont);
setLayout(null);
int lm,tm;
lm=0;
tm=0;
titleCaptionLabel.setBounds(lm+10+410,tm+23,110,30);
nameLabel.setBounds(lm+10+510+10,tm+23,350,30);
nameTextField.setBounds(lm+10+510+5,tm+23,350,30);
clearnameTextFieldButton.setBounds(lm+10+510+5+350+5,tm+23,30,30);
buttonsPanel.setBounds(lm+450,tm+20+65,455,80);
buttonsPanel.setBorder(BorderFactory.createLineBorder(new Color(150,150,150)));
addButton.setBounds(lm+450+60,tm+20+64+14,55,55);
editButton.setBounds(lm+450+60+55+15,tm+20+64+14,55,55);
cancelButton.setBounds(lm+450+60+55+55+15+15,tm+20+64+14,55,55);
deleteButton.setBounds(lm+450+60+55+55+55+15+15+15,tm+20+64+14,55,55);
exportToPDFButton.setBounds(lm+450+60+55+55+55+55+15+15+15+15,tm+20+64+14,55,55);
buttonsPanel.setLayout(null);
buttonsPanel.add(addButton);
buttonsPanel.add(editButton);
buttonsPanel.add(cancelButton);
buttonsPanel.add(deleteButton);
buttonsPanel.add(exportToPDFButton);
add(titleCaptionLabel);
add(nameLabel);
add(nameTextField);
add(clearnameTextFieldButton);
add(addButton);
add(editButton);
add(cancelButton);
add(deleteButton);
add(exportToPDFButton);
add(buttonsPanel);
}
void setViewMode()
{
EmployeeUI.this.setViewMode();
this.nameTextField.setVisible(false);
this.clearnameTextFieldButton.setVisible(false);
this.nameLabel.setVisible(true);
this.addButton.setIcon(addIcon);
this.editButton.setIcon(editIcon);
this.addButton.setEnabled(true);
this.cancelButton.setEnabled(false);
if(employeeModel.getRowCount()>0)
{
this.editButton.setEnabled(true);
this.deleteButton.setEnabled(true);
this.exportToPDFButton.setEnabled(true);
}
else
{
this.editButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}
}
void setEditMode()
{
if(employeeTable.getSelectedRow()<0 || employeeTable.getSelectedRow()>=employeeModel.getRowCount())
{
JOptionPane.showMessageDialog(this,"Selection Employee to edit");
return;
}
EmployeeUI.this.setEditMode();
this.nameTextField.setVisible(true);
this.editButton.setIcon(saveIcon);
this.nameTextField.setText(this.employee.getName());
this.clearnameTextFieldButton.setVisible(true);
this.nameLabel.setVisible(false);
this.addButton.setEnabled(false);
this.cancelButton.setEnabled(true);
this.editButton.setEnabled(true);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}
void setDeleteMode()
{
if(employeeTable.getSelectedRow()<0 || employeeTable.getSelectedRow()>=employeeModel.getRowCount())
{
JOptionPane.showMessageDialog(this,"Selection Employee to delete");
return;
}
EmployeeUI.this.setDeleteMode();
this.addButton.setEnabled(false);
this.cancelButton.setEnabled(false);
this.editButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
removeEmployee();
setViewMode();
}
void setAddMode()
{
EmployeeUI.this.setAddMode();
this.nameTextField.setText("");
this.nameTextField.setVisible(true);
this.clearnameTextFieldButton.setVisible(true);
this.nameLabel.setVisible(false);
this.addButton.setIcon(saveIcon);
//scrollPane.setVisible(false);
this.addButton.setEnabled(true); //remove visiblity of employee table
this.cancelButton.setEnabled(true);
this.editButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}
void setExportToPDFMode()
{
if(employeeTable.getSelectedRow()<0 || employeeTable.getSelectedRow()>=employeeModel.getRowCount())
{
JOptionPane.showMessageDialog(this,"Selection Employee to delete");
return;
}
EmployeeUI.this.setDeleteMode();
this.addButton.setEnabled(false);
this.cancelButton.setEnabled(false);
this.editButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}
/*private boolean addEmployee()
{
String title=this.nameTextField.getText().trim();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,"Employee required");
this.nameTextField.requestFocus();
return false;
}
EmployeeInterface d=new Employee();
d.setTitle(title);
int rowIndex=0;
try
{
employeeModel.add(d);
try
{
rowIndex=employeeModel.indexOfEmployee(d);
}catch(BLException blException)
{
//this is not gona happen because new record is resently added
}
employeeTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=employeeTable.getCellRect(rowIndex,0,true);
employeeTable.scrollRectToVisible(rectangle); 
return true;
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}
else
{
if(blException.hasException("title"))
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
}
}
}
nameTextField.requestFocus();
return false;
}
*/
private boolean updateEmployee()
{
String title=this.nameTextField.getText().trim();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,"Employee required");
this.nameTextField.requestFocus();
return false;
}
EmployeeInterface d=new Employee();
d.setEmployeeId(this.employee.getEmployeeId());
d.setName(title);
int rowIndex=0;
try
{
employeeModel.update(d);
try
{
rowIndex=employeeModel.indexOfEmployee(d);
}catch(BLException blException)
{
//this is not gona happen because new record is resently added
}
employeeTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=employeeTable.getCellRect(rowIndex,0,true);
employeeTable.scrollRectToVisible(rectangle); 
return true;
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}
else
{
if(blException.hasException("title"))
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
}
}
}
nameTextField.requestFocus();
return false;
}
//********************************************************************************************
public void removeEmployee()
{
try
{
String title=this.employee.getName();
int selectedOption=JOptionPane.showConfirmDialog(this,"Delete "+title+" ?","Confirmation",JOptionPane.YES_NO_OPTION);
if(selectedOption==JOptionPane.NO_OPTION) return;
employeeModel.remove(this.employee.getEmployeeId());
JOptionPane.showMessageDialog(this,title+" deleted");
//this.clearEmployee();
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}
else
{
if(blException.hasException("title"))
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
}
}
}
}



public void addListeners()
{
addButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
if(mode==MODE.VIEW)
{
setAddMode();
}
/*
else
{
if(addEmployee())
{
setViewMode();
}
}
*/
}
});
editButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
if(mode==MODE.VIEW)
{
setEditMode();
}
else
{
if(updateEmployee())
{
setViewMode();
}
}
}
});
this.cancelButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
setViewMode();
}
});
this.deleteButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
setDeleteMode();
}
});
this.exportToPDFButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
JFileChooser jfc=new JFileChooser();
jfc.setAcceptAllFileFilterUsed(false);
jfc.setCurrentDirectory(new File("."));
jfc.addChoosableFileFilter(new javax.swing.filechooser.FileFilter(){
public boolean accept(File file)
{
if(file.isDirectory()) return true;
if(file.getName().endsWith(".pdf")) return true; 
return false;
}
public String getDescription()
{
return "Pdf Files";
}
});
int selectedOption=jfc.showSaveDialog(EmployeeUI.this);
if(selectedOption==jfc.APPROVE_OPTION)
{
try
{
File selectedFile=jfc.getSelectedFile();
String pdfFile=selectedFile.getAbsolutePath();
if(pdfFile.endsWith(".")) pdfFile+="pdf";
else if(pdfFile.endsWith(".pdf")==false) pdfFile+=".pdf";
File file=new File(pdfFile);
if(file.exists())
{
selectedOption=JOptionPane.showConfirmDialog(EmployeeUI.this,"Do you Want to Override this File : "+file.getAbsolutePath(),"Confirmation",JOptionPane.YES_NO_OPTION);
if(selectedOption==JOptionPane.NO_OPTION) return;                                                                                                
}
File parent=new File(file.getParent());
if(parent.exists()==false || parent.isDirectory()==false)
{
JOptionPane.showMessageDialog(EmployeeUI.this,"Incorrect Path : "+file.getAbsolutePath());
return;
}
employeeModel.exportToPDF(file);
JOptionPane.showMessageDialog(EmployeeUI.this,"Data exported to : "+file.getAbsolutePath());
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(EmployeeUI.this,blException.getGenericException());
}
}
catch(Exception e)
{
System.out.println(e);
}
}
else
{
System.out.println("Cancel Selected");
}
}
});
}
}//Inner class ends here
}