package com.krish.lalwani.hr.dl.dao;
import java.sql.*;
import com.krish.lalwani.hr.dl.exceptions.*;
public class DAOConnection
{
private DAOConnection(){}
public static Connection getConnection() throws DAOException
{
Connection connection=null; 
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/hrdb","hr","hr");
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return connection;
}
}