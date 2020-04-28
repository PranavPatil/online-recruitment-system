/*
   @Version 1.01 2005-09-21
   @author Pranav Patil
*/
import java.util.*;

public class Win32
{
   public native void CreateLink(String file, String lpathname, String desc,
                                 String workdir, String iconpath, int splocation);

   public native void Beep(int bptype);
   
   public native void Reboot();

   static
   {  
      System.loadLibrary("Win32");
   }
}