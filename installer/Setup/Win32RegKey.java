/*
   @Version 1.01 2005-09-21
   @author Pranav Patil
*/

import java.util.*;

public class Win32RegKey
{
   public Win32RegKey(int theRoot, String thePath)
   {  
      root = theRoot;
      path = thePath;
   }

   public void setPath(String thePath)
   {
      path = thePath;	
   }
   
   public Enumeration names()
   {  
      return new Win32RegKeyNameEnumeration(root, path);
   }

   public native Object getValue(String name);

   public native Object isKey();

   public native void newKey();
   
   public native void delKey();

   public native void delValue(String name);
   
   public native void setValue(String name, Object value);

   public static final int HKEY_CLASSES_ROOT = 0x80000000;
   public static final int HKEY_CURRENT_USER = 0x80000001;
   public static final int HKEY_LOCAL_MACHINE = 0x80000002;
   public static final int HKEY_USERS = 0x80000003;
   public static final int HKEY_CURRENT_CONFIG = 0x80000005;
   public static final int HKEY_DYN_DATA = 0x80000006;

   private int root;
   private String path;

   static
   {  
      System.loadLibrary("Setup");
   }
}

class Win32RegKeyNameEnumeration implements Enumeration
{  
   Win32RegKeyNameEnumeration(int theRoot, String thePath)
   {  
      root = theRoot;
      path = thePath;
   }

   public native Object nextElement();
   public native boolean hasMoreElements();

   private int root;
   private String path;
   private int index = -1;
   private int hkey = 0;
   private int maxsize;
   private int count;
}

class Win32RegKeyException extends RuntimeException
{  
   public Win32RegKeyException() {}
   public Win32RegKeyException(String why)
   {  
      super(why);
   }
}
