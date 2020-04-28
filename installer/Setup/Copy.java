/*
   @Version 1.01 2005-09-28
   @author Pranav Patil
*/

import java.io.*;
import java.util.*;
import java.util.zip.*;
import javax.swing.*;

public class Copy extends Thread
{ 
  private String Path;
  private File f1;
  private ZipInputStream zin;
  private ZipEntry entry;
  private Temp tem;
  private boolean value = false;
  private boolean stop = false;
   
  public void setPath(String path)
  {
  	Path = path;
  } 
  
  public boolean Exists(String file)
  {
  	  String st = Path + "\\WEB-INF\\" + file;  	
      File f = new File(st);
      return f.exists();        	
  }

  public boolean rename(String st1,String st2)
  {
  	  String strg = Path + "\\WEB-INF\\" + st1;
  	  String strg1 = Path + "\\WEB-INF\\" + st2;
  	  
      File f1 = new File(strg);
      File f2 = new File(strg1);

      boolean val = f1.renameTo(f2);
      return val;
  } 

  public void Copy(String file,Temp tp)
  {
    try
    {
   	  long size = 0;
   	  tem = tp;
      f1 = new File(file);
      System.out.println("Source : " + f1.getCanonicalPath());
                        
      FileInputStream fin = new FileInputStream(f1);
      BufferedInputStream bin = new BufferedInputStream(fin);
      ZipInputStream z  = new ZipInputStream(bin);   	  
   	       
      while ((entry = z.getNextEntry()) != null)
      {
        String name = null;
        name = entry.getName();
                
        if(!name.contains("Setup") && !name.contains("META-INF"))
        {
          if(entry.isDirectory() == false)
          {
            size = size + entry.getSize();
          }
          z.closeEntry();                	
        }                                
      }
      z.close();
      
      zin  = new ZipInputStream(new BufferedInputStream(new FileInputStream(f1)));

      System.out.println("size = " + size);
      
      if(size == 0)
       throw new FileFormatException("Illegal File Format");
        
      tp.setTarget(size);
      this.start();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      tem.setText(ex.toString());
      value = false;
      stop = true;
    }
  }
  
  public boolean getStop()
  {
    return stop;
  }
  
  public boolean getValue()
  {
  	return value;
  }
  
  public void run()
  { 
    long current = 0;
    try
    {    
      while (((entry = zin.getNextEntry()) != null) && !stop)
      {
        String name,text,str = null;
        boolean create = false;
        name = entry.getName();
                
        if(name.regionMatches(0,"ORS/servlets/",0,13))
        {
          str = Path + "\\servlets\\" + name.substring(13);
          create = true;
        }
        else if(name.regionMatches(0,"ORS/ORS/",0,8))
        {                                  	
          str = Path + "\\WEB-INF\\classes\\" + name.substring(4);
          create = true;
        }
        else if(name.regionMatches(0,"ORS/WEB-INF/",0,12))
        {                                  	
          str = Path + "\\" + name.substring(4);
          create = true;
        }
        else
        {
          create = false;
        }
        
        if(create == true)
        {
          File f = new File(str);
                
          if(entry.isDirectory())
          {
            f.mkdirs();                	
          }
          else
          {
            f.createNewFile();                        
            FileOutputStream fout = new FileOutputStream(f);            
                              
            byte[]  by = new byte[1000];
            int val = 0;                
                                
            while((val = zin.read(by)) != -1 && !interrupted())
            { 
              fout.write(by,0,val);
              current = current + 1000;
              text = "Extracting  : " + f.getName() + "\n";
              tem.setValue(current);
              tem.setText(text);
              sleep(1);
            }
          }  
          zin.closeEntry();                	
        }                                
      }
      zin.close();
      value = true;
      stop = true;
      System.out.println("Exiting... !!");
    }
    catch (Exception ex)
    {
      tem.setText(ex.toString());
      value = false;
      stop = true;
    }
  }
    
  public void Delete(String[] args)
  {
    if (args.length == 0) args = new String[] { ".." };

    File pathName = new File(args[0]);
         
    if(pathName.isDirectory() == true)
    {
      String[] fileNames = pathName.list();
    
      for (int i = 0; i < fileNames.length; i++)
      {  
        File f = new File(pathName.getPath(),fileNames[i]);
        
        if (f.isDirectory())
        {  
          Delete(new String [] { f.getPath() });
        }
        else
        {
          System.out.println("Deleted File " + f.getPath());
          f.delete();        	
        }        
      }      	
    }
    System.out.println("Deleted Dir " + pathName.getPath());
    pathName.delete();
  }
  
  public void Create(String file,String port)
  {
    try
    {
      InputStream fin = Copy.class.getResourceAsStream(file);
      BufferedInputStream bin = new BufferedInputStream(fin);
      DataInputStream din = new DataInputStream(bin);
    
      String str = Path + "\\servlets\\Admin\\Readme.html";
      
      File f = new File(str);
      f.createNewFile();            
      System.out.println("WRITE = " + f.canWrite());
            
      PrintWriter out = new PrintWriter(new FileWriter(f));
            
      String line = null;
      String st = "C:\\Documents and Settings\\Administrator\\Start Menu\\Programs\\Apache Tomcat ";
      System.out.println("Path = " + Path);   
         
      while (din.available() != 0)
      {         	                        
        line = din.readUTF();
        System.out.println(line);
        if(line.contains("href=\"*\">*</A>"))
        {
          if(Path.contains("4.0"))	
           line = "href=\"" + st + "4.0" + "\\Start Tomcat.lnk" + "\">" + st + "4.0" + "</A>";          
          else
           line = "href=\"" + st + "4.1" + "\\Start Tomcat.lnk" + "\">" + st + "4.1" + "</A>";
           
        }
        
        if(line.contains("localhost:port"))
        {
          line = line.replaceAll("port",port);
        }
        out.println(line);
      }
       out.close();
    }
    catch(Exception ex)
    {
      System.out.println("Error : " + ex);	
    }
  }  
}