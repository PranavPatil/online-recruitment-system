package ORS.User;

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

class Record
{
   public Record() {}

   public Record(long id, String s,
      int year, int month, int day,int hrs,int min,int sec)
   {
      Id = id;
      Entry = s;
      
      GregorianCalendar calendar
         = new GregorianCalendar(year + 1900,month,day,hrs,min,sec);
      date = calendar.getTime();
   }

   public long getId()
   {
      return Id;
   }

   public String getEntry()
   {
      return Entry;
   }

   public Date getDate()
   {
      return date;
   }

   public void writeData(PrintWriter out) throws IOException
   {
      GregorianCalendar calendar = new GregorianCalendar();
      calendar.setTime(date);      
      out.println(Id + "|"
                + Entry + "|"
                + calendar.get(Calendar.YEAR) + "|"
                + (calendar.get(Calendar.MONTH) + 1) + "|"
                + calendar.get(Calendar.DAY_OF_MONTH) + "|"
                + calendar.get(Calendar.HOUR) + "|"
                + calendar.get(Calendar.MINUTE) + "|"
                + calendar.get(Calendar.SECOND));      	
   }

   public void readData(BufferedReader in) throws IOException
   {
      String s = in.readLine();
      StringTokenizer t = new StringTokenizer(s, "|");
      Id = Long.parseLong(t.nextToken());
      Entry = t.nextToken();
      int y = Integer.parseInt(t.nextToken());
      int m = Integer.parseInt(t.nextToken());
      int d = Integer.parseInt(t.nextToken());
      int hrs = Integer.parseInt(t.nextToken());
      int min = Integer.parseInt(t.nextToken());
      int sec = Integer.parseInt(t.nextToken());      
      
      GregorianCalendar calendar
         = new GregorianCalendar(y, m - 1, d,hrs,min,sec);
      date = calendar.getTime();
   }

   private String Entry;
   private long Id;
   private Date date;
}
