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
       ConnectionPool pool=new ConnectionPool();
       pool.setDriver(conf.getInitParameter("Driver"));
       pool.setUrl(conf.getInitParameter("Url"));
       pool.setUserName("scott");
       pool.setPassword("tiger");
       pool.setSize(2);
       pool.initializePool();
	   ServletContext context=getServletContext();
	   context.setAttribute("ConPool",pool);
	 }
	 catch(Exception e)
	 {
	   e.printStackTrace();
	 }
 }
}