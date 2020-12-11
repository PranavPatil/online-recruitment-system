 /**
   @version 1.01 2006-06-17
   @author Pranav Patil
*/
package ORS.Utils;

import java.util.*;
import java.net.*;
import java.io.*;
import javax.swing.*;

public class Mail
{  

   private BufferedReader in;
   private PrintWriter out;

   public static void main(String[] args)
   {  
     Mail m = new Mail();
     m.sendMail(); 
   }
   
/*   public void sendMail()
   {  
      try
      {  
         Socket s = new Socket(smtpServer.getText(), 25);

         out = new PrintWriter(s.getOutputStream());
         in = new BufferedReader(new
            InputStreamReader(s.getInputStream()));

         String hostName
            = InetAddress.getLocalHost().getHostName();

         receive();
         send("HELO " + hostName);
         receive();
         send("MAIL FROM: <" + from.getText() + ">");
         receive();
         send("RCPT TO: <" + to.getText() + ">");
         receive();
         send("DATA");
         receive();
         StringTokenizer tokenizer = new StringTokenizer(
            message.getText(), "\n");
         while (tokenizer.hasMoreTokens())
            send(tokenizer.nextToken());
         send(".");
         receive();
         s.close();
      }
      catch (IOException exception)
      {  
         communication.append("Error: " + exception);
      }
   }

   public void send(String s) throws IOException
   {  
      communication.append(s);
      communication.append("\n");
      out.print(s);
      out.print("\r\n");
      out.flush();
   }

   public void receive() throws IOException
   {
      String line = in.readLine();
      if (line != null)
      {
         communication.append(line);
         communication.append("\n");
      }
   }*/
   
 public void sendMail()
 {
   try
   {
     Socket s=new Socket("yahoo.com",25);
     out= new PrintWriter(s.getOutputStream());
     in =new BufferedReader(new InputStreamReader(s.getInputStream()));
     String hostName=InetAddress.getLocalHost().getHostName();
     send(null);
     send("HELO"+hostName);
     send("MAIL FROM:amitdev_gupta@yahoo.com");
     send("RCPT TO:pranav_558@yahoo.com");
     send("DATA");
     send("Call me.");
     s.close();
   }
   catch(IOException e)
   {
     e.printStackTrace();
   }
 }

  public void send(String s)throws IOException
  {
    if(s!=null)
    {
     out.println(s);
     out.flush();	
    }
  }
}