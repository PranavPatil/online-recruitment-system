package ORS.Utils;

import java.io.*;
import java.util.*;

public class Log
{   
  private String Path;
  
  public Log(String path)
  {
  	Path = path;
  }
    
  public void writeLog(long id,String entry)
  {         
 	int min =0, hrs = 0, sec = 0;
 	int yr = 0,month = 0,day = 0;

    Calendar cur = Calendar.getInstance();
    min= cur.get(Calendar.MINUTE);
    hrs = cur.get(Calendar.HOUR_OF_DAY);
    sec= cur.get(Calendar.SECOND);
    yr = cur.get(Calendar.YEAR) - 1900;
    month = cur.get(Calendar.MONTH);
    day = cur.get(Calendar.DAY_OF_MONTH);

  	Record rec = new Record(id,entry,yr,month,day,hrs,min,sec);
      try
      {      	
        PrintWriter out = new PrintWriter(new FileWriter(Path,true));         	         	
        rec.writeData(out);
        out.close();         
      }
      catch(IOException exception)
      {
      	System.out.println("Problem in Log Entry !");
         exception.printStackTrace();
      }  	
  }
  
  public boolean ifExists()
  {
    File file = new File(Path);
    return(file.exists());    
  }

  public int getCount()
  {
    String line;
    int count = 0;
    try
    {
  	  BufferedReader in = new BufferedReader(new FileReader(Path));      
      while((line = in.readLine()) != null)
      {
        count++;
      }    	
    }
    catch(IOException exception)
    {
      System.out.println("Problem in GetCount !");
      exception.printStackTrace();
    }    
    return count;
  }
  
  public Record[] readLog(int count)
  {
    Record[] e = null;
    
    if(count != 0)
    {
      try
      {      	  	       
        BufferedReader in = new BufferedReader(new FileReader(Path));
        e = new Record[count];
        for (int i = 0; i < count; i++)
        {
          e[i] = new Record();
          e[i].readData(in);
        }
        in.close();
      }
      catch(IOException exception)
      {
        System.out.println("Problem in Log Retrival !");
        exception.printStackTrace();
      }    	
    }
    return e;  	
  }    
}

