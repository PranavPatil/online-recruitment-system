package ORS.ConnPool;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import ORS.ConnPool.*;

 public class InitilizeServlet extends HttpServlet{
 	
 	ConnectionPool pool=null;
 	public void InitilizeServlet()
 	{
 	   
 	}	
 	
 	public void init(ServletConfig conf)
	throws ServletException
	{
		super.init(conf);
		try{
		   pool=new ConnectionPool();
		   pool.setDriver("sun.jdbc.odbc.JdbcOdbcDriver");
		   pool.setUrl("jdbc:odbc:ORS");
		   pool.setUserName("scott");
		   pool.setPassword("tiger");
		   pool.setSize(2);
		   pool.initializePool();
		   ServletContext context=getServletContext();
		   context.setAttribute("ConPool",pool);
		   	       
		 }catch(Exception e)
		  {
		   System.out.println(e.getMessage());	
		  }
 }
public void doGet(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
	  //forwad(req,res);	
	}
public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
	 doGet(req,res);	
	}
/*protected void forwad(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
	{
	 String forwadStr="/servlets/User/UserLoginPage.html";
	 
	 RequestDispatcher dispatcher=req.getRequestDispatcher(forwadStr);
	 dispatcher.forward(req,res);
	 
	}*/


}