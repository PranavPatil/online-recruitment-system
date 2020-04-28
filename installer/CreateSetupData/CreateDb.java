/*
   @Version 1.01 2005-09-21
   @author Pranav Patil
*/

import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;


public class CreateDb
{  
   public static void main(String[] args) throws IOException
   {  
     //File f = new File("C:\\");
     /*Process p = Runtime.getRuntime().exec("explorer F:\\SSCL\\Setup\\help.html");
     //Process p = Runtime.getRuntime().exec("explorer E:\\HINDI VIDEO\\hum tum.dat");
     BufferedReader ex = new BufferedReader(new InputStreamReader(p.getErrorStream()));
     String line;
     while((line = ex.readLine()) != null)
     {
     	System.out.println(line);
     }*/
     
     /*Toolkit tk = Toolkit.getDefaultToolkit();
     tk.setDynamicLayout(true);
     System.out.println("st = " + tk.getProperty("awt.beep","d"));

     tk.beep();
     JOptionPane.showMessageDialog(null, "install Apache Tomcat ","Error",JOptionPane.ERROR_MESSAGE);
     
     //System.out.println("st = " + st);*/
          
      try
      { 
            boolean b = false;

            File f1 = new File("Install.sql");
            //File f1 = new File("Uninstall.txt");
            //File f1 = new File("Entries.txt");
            System.out.println("Path = " + f1.getPath());
            b = f1.canRead();   
            System.out.println("READ = " + b);
            
            File f = new File("Install.dat");
            //File f = new File("Uninstall.dat");
            //File f = new File("Entries.dat");
            System.out.println("Path = " + f.getCanonicalPath());
            f.createNewFile();            
            b = f.canWrite();   
            System.out.println("WRITE = " + b);
            
            FileInputStream fin = new FileInputStream(f1);
            FileOutputStream fout = new FileOutputStream(f);            
            //BufferedInputStream bin = new BufferedInputStream(fin);
            //BufferedOutputStream bout = new BufferedOutputStream(fout);            
            //FileReader read = new FileReader(f1);            
            //FileWriter write = new FileWriter(f);

            BufferedReader din = new BufferedReader(new InputStreamReader(fin));
            DataOutputStream dout = new DataOutputStream(fout);            
                        
            System.out.println("Length = " + f1.length());
            String str;
            
            while((str = din.readLine()) != null)
            {               
              dout.writeUTF(str);
            }
            
            /*InputStream in = AboutPanel.class.
               getResourceAsStream("about.txt");
            BufferedReader reader = new BufferedReader(new
               InputStreamReader(in));*/
            
                                 
            //File f2 = new File("In.txt");new FileInputStream(f2);
            /*InputStream fi = Test.class.getResourceAsStream("Install.dat");
            
            //BufferedReader di = new BufferedReader(new InputStreamReader(fi));
            BufferedInputStream bi = new BufferedInputStream(fi);
            DataInputStream d = new DataInputStream(bi);

            while(d.available() != 0)
            {               
               str = d.readUTF();
               System.out.println(str);
            }*/
      }
      catch(IOException e)
      {  
         e.printStackTrace(); 
      }               
   }
}
