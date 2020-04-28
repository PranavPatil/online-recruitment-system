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
    
  public void writeLog(int id,String entry)
  {         
 	int min =0, hrs = 0, sec = 0;
 	int yr = 0,month = 0,day = 0;
    Date cur = new Date();     
    
    min= cur.getMinutes();
    hrs = cur.getHours();
    sec= cur.getSeconds();
    yr = cur.getYear();
    month = cur.getMonth();
    day = cur.getDate();

    System.out.println("Enter in WriteLog ");
  	
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
        System.out.println("Data is = " + line);
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
  	System.out.println("Enter in ReadLog ");
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

   public Record(int id, String s,
      int year, int month, int day,int hrs,int min,int sec)
   {
      Id = id;
      Entry = s;
      
      GregorianCalendar calendar
         = new GregorianCalendar(year + 1900,month,day,hrs,min,sec);
      date = calendar.getTime();
   }

   public int getId()
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
   	  System.out.println("Entry in WriteData !");
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
   	  System.out.println("Entry in ReadData !");
      String s = in.readLine();
      StringTokenizer t = new StringTokenizer(s, "|");
      Id = Integer.parseInt(t.nextToken());      
      Entry = t.nextToken();
      int y = Integer.parseInt(t.nextToken());
      int m = Integer.parseInt(t.nextToken());
      int d = Integer.parseInt(t.nextToken());
      int hrs = Integer.parseInt(t.nextToken());
      int min = Integer.parseInt(t.nextToken());
      int sec = Integer.parseInt(t.nextToken());      
      
      System.out.println("Id = " + Id + "Entry = " + Entry);
      System.out.println("Hrs = " + hrs + "Min = " + min + "Sec = " + sec);
      GregorianCalendar calendar
         = new GregorianCalendar(y, m - 1, d,hrs,min,sec);
      date = calendar.getTime();
   }

   private String Entry;
   private int Id;
   private Date date;
}
