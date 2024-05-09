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
public class DesignationUI extends JFrame implements DocumentListener,ListSelectionListener
{
private JLabel titleLabel;
private JLabel searchLabel;
private JTextField searchTextField;
private JButton clearSearchTextFieldButton;
private JLabel searchErrorLabel;
private DesignationModel designationModel;
private JTable designationTable;
private Container container;
private JScrollPane scrollPane;
private DesignationPanel designationPanel;
private enum MODE{VIEW,ADD,EDIT,CANCEL,DELETE,EXPORT_TO_PDF};
private MODE mode;
private ImageIcon addIcon;
private ImageIcon editIcon;
private ImageIcon cancelIcon;
private ImageIcon saveIcon;
private ImageIcon deleteIcon;
private ImageIcon pdfIcon;
private ImageIcon logoIcon;
public DesignationUI()
{
initComponents();
setAppearance();
addListeners();
designationPanel.setViewMode();
}
private void setViewMode()
{
this.mode=MODE.VIEW;
if(designationModel.getRowCount()==0)
{
this.searchTextField.setEnabled(false);
this.clearSearchTextFieldButton.setEnabled(false);
this.designationTable.setEnabled(false);
}
else
{
this.searchTextField.setEnabled(true);
this.clearSearchTextFieldButton.setEnabled(true);
this.designationTable.setEnabled(true);
}
}
private void setAddMode()
{
this.mode=MODE.ADD;
this.searchTextField.setEnabled(false);
this.clearSearchTextFieldButton.setEnabled(false);
this.designationTable.setEnabled(false);
}
private void setEditMode()
{
this.mode=MODE.EDIT;
this.searchTextField.setEnabled(false);
this.clearSearchTextFieldButton.setEnabled(false);
this.designationTable.setEnabled(false);
}
private void setDeleteMode()
{
this.mode=MODE.DELETE;
this.searchTextField.setEnabled(false);
this.clearSearchTextFieldButton.setEnabled(false);
this.designationTable.setEnabled(false);
}
private void setExportTOPDF()
{
this.mode=MODE.EXPORT_TO_PDF;
this.searchTextField.setEnabled(false);
this.clearSearchTextFieldButton.setEnabled(false);
this.designationTable.setEnabled(false);
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
titleLabel=new JLabel("Designations");
searchLabel=new JLabel("Search");
searchTextField=new JTextField();
clearSearchTextFieldButton=new JButton("x");
searchErrorLabel=new JLabel("");
designationModel=new DesignationModel();
designationTable=new JTable(designationModel);
scrollPane=new JScrollPane(designationTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container=getContentPane();
designationPanel=new DesignationPanel();
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
designationTable.setFont(dataFont);
designationTable.setRowHeight(35);
designationTable.getColumnModel().getColumn(0).setPreferredWidth(20);
designationTable.getColumnModel().getColumn(1).setPreferredWidth(400);
JTableHeader header=designationTable.getTableHeader();
header.setFont(columnHeaderFont);
header.setReorderingAllowed(false);
header.setResizingAllowed(false);
designationTable.setRowSelectionAllowed(true);
designationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
container.setLayout(null);
int w=600;
int h=700;
int lm,tm; // for convineance of leftMargin and topMargin
lm=0;
tm=0;
titleLabel.setBounds(lm+10,tm+10,200,40);
searchLabel.setBounds(lm+10,tm+10+40+10,80,30);
searchTextField.setBounds(lm+10+85+5,tm+10+40+10,400,30);
searchErrorLabel.setBounds(lm+10+85+5+330,tm+10+20+5,90,25);
clearSearchTextFieldButton.setBounds(lm+10+85+5+400+5,tm+10+40+10,30,30);
scrollPane.setBounds(lm+10,tm+10+40+10+30+10,565,340);
designationPanel.setBounds(lm+10,tm+10+40+10+30+10+340+10,565,200);
container.add(titleLabel);
container.add(searchLabel);
container.add(searchErrorLabel);
container.add(searchTextField);
container.add(clearSearchTextFieldButton);
container.add(scrollPane);
container.add(designationPanel);
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
designationTable.getSelectionModel().addListSelectionListener(this);
}
public void searchDesignation()
{
searchErrorLabel.setText("");
String title=this.searchTextField.getText().trim();
if(title.length()==0) return;
int rowIndex=0;
try
{
rowIndex=designationModel.indexOfTitle(title,true);
}catch(BLException blException)
{
searchErrorLabel.setText("Not Found");
return;
}
designationTable.setRowSelectionInterval(rowIndex,rowIndex); //this setRowSelectionInterval method select the row of which we gave it index of.
Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true); // this getCellRect method return rapped up infromation of a cell in Rectangle object
designationTable.scrollRectToVisible(rectangle); // it scroll the row of which we gave it address of in the form of rectangle object
}
public void changedUpdate(DocumentEvent de)
{
System.out.println("changedUpdate got called");
searchDesignation();
}
public void removeUpdate(DocumentEvent de)
{
searchDesignation();
}
public void insertUpdate(DocumentEvent de)
{
searchDesignation();
}
public void valueChanged(ListSelectionEvent lse)
{
int selectedRowIndex=designationTable.getSelectedRow();
try
{
DesignationInterface designation;
designation=this.designationModel.getDesignationAt(selectedRowIndex);
designationPanel.setDesignation(designation);
}catch(BLException blException)
{
designationPanel.clearDesignation();
}
}
//Inner class starts here
class DesignationPanel extends JPanel
{
private JLabel titleCaptionLabel;
private JLabel titleLabel;
private JTextField titleTextField;
private JButton clearTitleTextFieldButton;
private JPanel buttonsPanel;
private JButton addButton;
private JButton editButton;
private JButton cancelButton;
private JButton deleteButton;
private JButton exportToPDFButton;
private DesignationInterface designation;
DesignationPanel()
{
setBorder(BorderFactory.createLineBorder(new Color(150,150,150)));
initComponents();
setAppearance();
addListeners();
}
public void setDesignation(DesignationInterface designation) //mainly for setting titleLable
{
this.designation=designation;
this.titleLabel.setText(this.designation.getTitle());
}
public void clearDesignation()
{
this.designation=null;
this.titleLabel.setText("");
}
public void initComponents()
{
designation=null;
titleCaptionLabel=new JLabel("Designation");
titleLabel=new JLabel("");
titleTextField=new JTextField();
clearTitleTextFieldButton=new JButton("X");
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
titleLabel.setFont(dataFont);
titleTextField.setFont(dataFont);
setLayout(null);
int lm,tm;
lm=0;
tm=0;
titleCaptionLabel.setBounds(lm+10,tm+23,110,30);
titleLabel.setBounds(lm+10+110+10,tm+23,350,30);
titleTextField.setBounds(lm+10+110+5,tm+23,350,30);
clearTitleTextFieldButton.setBounds(lm+10+110+5+350+5,tm+23,30,30);
buttonsPanel.setBounds(lm+55,tm+20+65,455,80);
buttonsPanel.setBorder(BorderFactory.createLineBorder(new Color(150,150,150)));
addButton.setBounds(lm+55+60,tm+20+64+14,55,55);
editButton.setBounds(lm+55+60+55+15,tm+20+64+14,55,55);
cancelButton.setBounds(lm+55+60+55+55+15+15,tm+20+64+14,55,55);
deleteButton.setBounds(lm+55+60+55+55+55+15+15+15,tm+20+64+14,55,55);
exportToPDFButton.setBounds(lm+55+60+55+55+55+55+15+15+15+15,tm+20+64+14,55,55);
buttonsPanel.setLayout(null);
buttonsPanel.add(addButton);
buttonsPanel.add(editButton);
buttonsPanel.add(cancelButton);
buttonsPanel.add(deleteButton);
buttonsPanel.add(exportToPDFButton);
add(titleCaptionLabel);
add(titleLabel);
add(titleTextField);
add(clearTitleTextFieldButton);
add(addButton);
add(editButton);
add(cancelButton);
add(deleteButton);
add(exportToPDFButton);
add(buttonsPanel);
}
void setViewMode()
{
DesignationUI.this.setViewMode();
this.titleTextField.setVisible(false);
this.clearTitleTextFieldButton.setVisible(false);
this.titleLabel.setVisible(true);
this.addButton.setIcon(addIcon);
this.editButton.setIcon(editIcon);
this.addButton.setEnabled(true);
this.cancelButton.setEnabled(false);
if(designationModel.getRowCount()>0)
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
if(designationTable.getSelectedRow()<0 || designationTable.getSelectedRow()>=designationModel.getRowCount())
{
JOptionPane.showMessageDialog(this,"Selection Designation to edit");
return;
}
DesignationUI.this.setEditMode();
this.titleTextField.setVisible(true);
this.editButton.setIcon(saveIcon);
this.titleTextField.setText(this.designation.getTitle());
this.clearTitleTextFieldButton.setVisible(true);
this.titleLabel.setVisible(false);
this.addButton.setEnabled(false);
this.cancelButton.setEnabled(true);
this.editButton.setEnabled(true);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}
void setDeleteMode()
{
if(designationTable.getSelectedRow()<0 || designationTable.getSelectedRow()>=designationModel.getRowCount())
{
JOptionPane.showMessageDialog(this,"Selection Designation to delete");
return;
}
DesignationUI.this.setDeleteMode();
this.addButton.setEnabled(false);
this.cancelButton.setEnabled(false);
this.editButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
removeDesignation();
setViewMode();
}
void setAddMode()
{
DesignationUI.this.setAddMode();
this.titleTextField.setText("");
this.titleTextField.setVisible(true);
this.clearTitleTextFieldButton.setVisible(true);
this.titleLabel.setVisible(false);
this.addButton.setIcon(saveIcon);
this.addButton.setEnabled(true);
this.cancelButton.setEnabled(true);
this.editButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}
void setExportToPDFMode()
{
if(designationTable.getSelectedRow()<0 || designationTable.getSelectedRow()>=designationModel.getRowCount())
{
JOptionPane.showMessageDialog(this,"Selection Designation to delete");
return;
}
DesignationUI.this.setDeleteMode();
this.addButton.setEnabled(false);
this.cancelButton.setEnabled(false);
this.editButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}
private boolean addDesignation()
{
String title=this.titleTextField.getText().trim();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,"Designation required");
this.titleTextField.requestFocus();
return false;
}
DesignationInterface d=new Designation();
d.setTitle(title);
int rowIndex=0;
try
{
designationModel.add(d);
try
{
rowIndex=designationModel.indexOfDesignation(d);
}catch(BLException blException)
{
//this is not gona happen because new record is resently added
}
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle); 
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
titleTextField.requestFocus();
return false;
}
private boolean updateDesignation()
{
String title=this.titleTextField.getText().trim();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,"Designation required");
this.titleTextField.requestFocus();
return false;
}
DesignationInterface d=new Designation();
d.setCode(this.designation.getCode());
d.setTitle(title);
int rowIndex=0;
try
{
designationModel.update(d);
try
{
rowIndex=designationModel.indexOfDesignation(d);
}catch(BLException blException)
{
//this is not gona happen because new record is resently added
}
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle); 
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
titleTextField.requestFocus();
return false;
}
public void removeDesignation()
{
try
{
String title=this.designation.getTitle();
int selectedOption=JOptionPane.showConfirmDialog(this,"Delete "+title+" ?","Confirmation",JOptionPane.YES_NO_OPTION);
if(selectedOption==JOptionPane.NO_OPTION) return;
designationModel.remove(this.designation.getCode());
JOptionPane.showMessageDialog(this,title+" deleted");
//this.clearDesignation();
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
else
{
if(addDesignation())
{
setViewMode();
}
}
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
if(updateDesignation())
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
int selectedOption=jfc.showSaveDialog(DesignationUI.this);
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
selectedOption=JOptionPane.showConfirmDialog(DesignationUI.this,"Do you Want to Override this File : "+file.getAbsolutePath(),"Confirmation",JOptionPane.YES_NO_OPTION);
if(selectedOption==JOptionPane.NO_OPTION) return;                                                                                                
}
File parent=new File(file.getParent());
if(parent.exists()==false || parent.isDirectory()==false)
{
JOptionPane.showMessageDialog(DesignationUI.this,"Incorrect Path : "+file.getAbsolutePath());
return;
}
designationModel.exportToPDF(file);
JOptionPane.showMessageDialog(DesignationUI.this,"Data exported to : "+file.getAbsolutePath());
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(DesignationUI.this,blException.getGenericException());
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