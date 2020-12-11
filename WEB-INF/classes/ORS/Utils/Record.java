package ORS.Utils;

import java.io.*;
import java.util.*;

public class Record
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