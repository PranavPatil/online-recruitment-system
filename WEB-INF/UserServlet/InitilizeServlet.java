package ORS.ConnPool;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import ORS.ConnPool.*;

 public class InitilizeServlet extends HttpServlet
 {	
   public void init(ServletConfig conf)
   throws ServletException
   {
	 super.init(conf);
	 
	 try
	 {
	   Database db = new Database();
	   ServletContext context=getServletContext();
	   context.setAttribute("Database",db);
	 }
	 catch(Exception e)
	 {
	   System.out.println(e.getMessage());	
	 }
 }

 public void doGet(HttpServletRequest req,HttpServletResponse res)
 throws ServletException,IOException
 {
 }

 public void doPost(HttpServletRequest req,HttpServletResponse res)
 throws ServletException,IOException
 {
   doGet(req,res);	
 }
}