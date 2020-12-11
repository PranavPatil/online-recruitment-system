/*
   @Version 1.01 2005-09-21
   @author Pranav Patil
   
   
   ##HEADER
   Document Identifier : ORS_QUESTION #
   Document Version    : 1.4 #
   Category Id         : 1 #
   Category Name       : English #
   Modified At         : GMT 2006 12 24  11:35:44 #
   Created By          : 1 #
   Machine Id          : 0346797356 #
   Security Code       : 0346797356 #
   HEADER##
   ##DOC
   Srno : 1 # Type : MC # Question : Trauma # 
   Ans1 : Ill # Ans2 : Feeble # Ans3 : Sad # Ans4 : Horrifying Experience # Correct : 4
   //
   Srno : 2 # Type : MC # Question : Relinquish # 
   Ans1 : Mournful # Ans2 : Intimidate # Ans3 : Abdicate # Ans4 : Grace # Correct : 3
   //
   DOC##
   END
*/

import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;


public class Test
{  
/*   
  public String getIdentifier()
  {
   	
  }
   
  public float getVersion()
  {
   	
  }

  public int getCategoryId()
  {
   	
  }
   
  public void setCategoryId(int catid)
  {
   	
  }
  
  public String getCategoryName()
  {
   	
  }

  public void setCategoryName(String name)
  {
   	
  }
  
  public Date getModified()
  {
   	
  }

  public int getAuthor()
  {
   	
  }

  public void setAuthor(int id)
  {
   	
  }

  public long getMachineId()
  {
   	
  }

  public long getAccessId()
  {
   	
  }
*/  
   public static void main(String[] args) throws IOException
   {  
      try
      { 
        File f = new File("English.qs");
        f.createNewFile();            
            
        //FileInputStream fin = new FileInputStream(f);
        //FileOutputStream fout = new FileOutputStream(f);            
        //BufferedReader din = new BufferedReader(new InputStreamReader(fin));
        //DataOutputStream dout = new DataOutputStream(fout);            
        //BufferedInputStream bin = new BufferedInputStream(fin);
        //BufferedOutputStream bout = new BufferedOutputStream(fout);            

        FileReader read = new FileReader(f);            
        FileWriter write = new FileWriter(f);
                        
        //System.out.println("Length = " + f1.length());
        //String str;
        char[] str = new char[1000];
        
        /*dout.writeUTF("##HEADER");
        dout.writeUTF("Document Identifier : ORS_QUESTION");
        dout.writeUTF("Document Version    : 1.4");
        dout.writeUTF("Category Id         : 1");
        dout.writeUTF("Category Name       : English");
        dout.writeUTF("Modified At         : GMT 2006 12 24  11:35:44");
        dout.writeUTF("Created By          : 1");
        dout.writeUTF("Machine Id          : 0346797356");
        dout.writeUTF("Security Code       : 0346797356");
        dout.writeUTF("HEADER##");
        
        while((str = din.readLine()) != null)
        
        */

        write.write("##HEADER");
        write.write("Document Identifier : ORS_QUESTION");
        write.write("Document Version    : 1.4");
        write.write("Category Id         : 1");
        write.write("Category Name       : English");
        write.write("Modified At         : GMT 2006 12 24  11:35:44");
        write.write("Created By          : 1");
        write.write("Machine Id          : 0346797356");
        write.write("Security Code       : 0346797356");
        write.write("HEADER##");
        write.close();


        while(read.read(str) != -1)
        {               
          //dout.writeUTF(str);
          System.out.println(str);
          if(str.equals("##HEADER"))
           System.out.println("true = " + str);
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