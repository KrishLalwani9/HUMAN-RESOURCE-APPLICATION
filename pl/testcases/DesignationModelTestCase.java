import com.krish.lalwani.hr.pl.model.*;
//import com.krish.lalwani.hr.bl.managers.*;
//import com.krish.lalwani.hr.bl.pojo.*;
//import com.krish.lalwani.hr.bl.exceptions.*;
//import com.krish.lalwani.hr.bl.interfaces.pojo.*;
//import com.krish.lalwani.hr.bl.interfaces.managers.*;
import java.awt.*;
//import javax.swing.table.*;
import javax.swing.*;
class DesignationModelTestCase extends JFrame
{
private JTable table;
private JScrollPane jsp;
private Container container;
DesignationModelTestCase()
{
DesignationModel dm=new DesignationModel();
table=new JTable(dm);
Font font=new Font("Times New Roman",Font.PLAIN,24);
table.setFont(font);
table.setRowHeight(30);
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container=getContentPane();
container.setLayout(new BorderLayout());
container.add(jsp);
setLocation(100,200);
setSize(600,600);
setVisible(true);
}
}
class DesignationModelTestCasepsp
{
public static void main(String gg[])
{
DesignationModelTestCase dmtc=new DesignationModelTestCase();
}
}