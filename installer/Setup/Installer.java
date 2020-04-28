import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Installer extends JFrame implements Runnable
{ 
 String Path;
 JProgressBar progressBar;
 JTextArea textArea;

 public Installer(String path)
 {
   Path = path;
   setTitle("WinRAR");
   JFrame frame=new JFrame();
   setResizable(false);
   setDefaultLookAndFeelDecorated(true);
   
   Container contentPane = getContentPane();
   GridBagLayout layout = new GridBagLayout();

   JLabel image = new JLabel(new ImageIcon("untitled.JPG"));

   Jpanel panel=new Jpanel();
   panel.setLayout(layout);
   
   textArea = new JTextArea(15,30);
   textArea.setLineWrap(true);
   textArea.setEditable(false);
   textArea.setBackground(Color.lightGray);
   textArea.setFont(new java.awt.Font("SansSerif", 0, 12));
   
   JScrollPane scrollpane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
   
   JLabel label = new JLabel("Installation progress");

   progressBar = new JProgressBar();
   progressBar.setStringPainted(true);
   progressBar.setMaximum(100);
   
   JPanel input = new JPanel();
   
   JButton b1=new JButton("   Install   ");
   b1.setToolTipText("Install's Online Recruitment System");
   
   b1.addActionListener(new ActionListener()
   {  
     public void actionPerformed(ActionEvent event)
     {
       start();
       /*Toolkit.getDefaultToolkit().beep();
       JOptionPane.showMessageDialog(null, "Hello","Error",JOptionPane.INFORMATION_MESSAGE);*/
     }
   });
   
   JButton b2=new JButton("   Cancel   ");
   b2.setToolTipText("Canel's the Installation");
   
   b2.addActionListener(new ActionListener()
   {  
     public void actionPerformed(ActionEvent event)
     {
       System.exit(0);
     }
   });
   
   /*KeyHandler listener = new KeyHandler(this);
   panel.addKeyListener(listener);*/
   
   this.addKeyListener(new KeyListener()
   {
      public void keyPressed(KeyEvent event)
      {
        int keyCode = event.getKeyCode();

        if (keyCode == KeyEvent.VK_ENTER)
           System.exit(0);//start();
        else if (keyCode == KeyEvent.VK_ESCAPE)
           System.exit(0);
      }

      public void keyReleased(KeyEvent event) {}

      public void keyTyped(KeyEvent event) {}
   	  
   });
   
   input.setLayout( new FlowLayout(FlowLayout.RIGHT) );
   input.add(b1);
   input.add(b2);
   input.setBorder(BorderFactory.createEtchedBorder());
   input.setFocusable(true);

   GridBagConstraints constraints = new GridBagConstraints();

   constraints.fill = GridBagConstraints.NONE;
   constraints.anchor = GridBagConstraints.EAST;
   constraints.weightx = 0;
   constraints.weighty = 0;   
   constraints.insets.left = 7;
   constraints.insets.right = 4;
   panel.add(image, constraints, 0, 0, 1, 3);

   constraints.fill = GridBagConstraints.HORIZONTAL;
   constraints.weightx = 100;
   constraints.insets.left = 5;
   constraints.insets.right = 7;
   constraints.insets.bottom = 0;    
   panel.add(scrollpane, constraints, 1, 0, 1, 1);

   constraints.fill = GridBagConstraints.HORIZONTAL;
   constraints.insets.top = 5;
   panel.add(label,constraints, 1, 1, 1, 1);
   constraints.insets.top = 2;
   panel.add(progressBar, constraints, 1, 2, 1, 1);
   
   contentPane.add(panel,BorderLayout.CENTER);
   contentPane.add(input,BorderLayout.SOUTH);

   addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent we){System.exit(0);}});
   setSize(526,393);
   setLocation(250,200);
   setVisible(true);
   
   System.out.println("Focus image = " + image.isFocusOwner());
   System.out.println("Focus scrollpane = " + scrollpane.isFocusOwner());
   System.out.println("Focus progressBar = " + progressBar.isFocusOwner());
   System.out.println("Focus panel = " + panel.isFocusOwner());
   System.out.println("Focus input = " + input.isFocusOwner());
   System.out.println("Focus this = " + this.isFocusOwner());
 }
  
 public boolean isFocusable()
 {
   return true;
 }

 public void start()
 {
   Thread MainThread = new Thread(this);
   MainThread.start();
 }

 public void run()
 {
   Object value;
   Win32RegKey key = new Win32RegKey(Win32RegKey.HKEY_LOCAL_MACHINE,
                                    "SOFTWARE\\ODBC\\ODBC.INI\\ORS");
   //key.setPath();
   key.newKey();
        
   key.setValue("BufferSize", "65535");
   key.setValue("Description", "Online Recruitment System");
   key.setValue("Driver", "C:\\WINDOWS\\System32\\msorcl32.dll");
   key.setValue("DSN", "ORS");
   key.setValue("GuessTheColDef", "0");
   key.setValue("PWD", "");
   key.setValue("Remarks", "0");
   key.setValue("SERVER", "");
   key.setValue("StdDayOfWeek", "1");
   key.setValue("StripTrailingZero", "0");
   key.setValue("SynonymColumns", "1");
   key.setValue("UID", "scott/tiger");
       
   key.setPath("Software\\ODBC\\ODBC.INI\\ODBC Data Sources");
   key.newKey();
   key.setValue("ORS", "Microsoft ODBC for Oracle");
      
   System.out.println();
   System.out.println("System DSN created Successfully !!");
   System.out.println();        
        
   key.setPath("SOFTWARE\\ODBC\\ODBC.INI\\ODBC Data Sources");
   value = key.getValue("ORS");
        
   boolean ok = true;
   Temp tem = new Temp();
   GoodThread Td = new GoodThread(progressBar,textArea);
                        
   if(value.equals("Microsoft ODBC for Oracle"))
   {
     Database uninstall = new Database();
     ok = uninstall.isConnected();
          
     if(ok == true)
     {
       try
       {
        tem.setPercentile(25);
        Td.setTemp(tem);
        Td.start();
        System.out.println("TTTD 1 : " + Td.getState());
        uninstall.Query("Uninstall.dat",tem);
       
        while(uninstall.getStop() == false && !Thread.interrupted())
        {
          Thread.sleep(1);
        }

        ok = uninstall.getValue();
        System.out.println("Value = " + ok);
       
        if(ok == true)
        {
          //Database install = new Database();
          tem.setPercentile(25);
          System.out.println("TTTD 2 : " + Td.getState());          
          uninstall.Query("Install.dat",tem);
          
          while(uninstall.getStop() == false && !Thread.interrupted())
          {
            Thread.sleep(1);
          }
          ok = uninstall.getValue();
          System.out.println("Install Value = " + ok);
        }
            
        if(ok == true)
        {
          //Database entries = new Database();
          tem.setPercentile(25);
          System.out.println("TTTD 3 : " + Td.getState());          
          uninstall.Query("Entries.dat",tem);
          
          while(uninstall.getStop() == false && !Thread.interrupted())
          {
            Thread.sleep(1);
          }
          ok = uninstall.getValue();
          System.out.println("Entries Value = " + ok);
        }  
            
        System.out.println();
              
        if(ok == true)
          System.out.println("System Database Created Successfully !!");
       }
       catch(InterruptedException ie)
       { }
					
     }
     else
     {
      String str = "Oracle is not available.\nPlease initialize Oracle by entering 'Startup' in Startup.bat ";
      ShowErrMessage(str);
     }
   }
   else
   {
   	ShowErrMessage("System DSN installation failed ");
   }

   /*if(ok == true)
   { 
     String port = "8080";
     value = null;
     key.setPath("SOFTWARE\\ORACLE\\Home0");
     value = key.getValue("ORACLE_HOME_NAME");
     
     if(value.equals("OraHome90"))
     {
       port = "1977";
       XMLParser server = new XMLParser();
       boolean flag = server.setPort(Path + "\\conf\\server.xml",port);
       System.out.println("Flag = " + flag);
          
       if(flag == false)
       {
         ok = false;
         ShowErrMessage("Failure in setting the Port value .");
       }
     }  
     
     Path = Path + "\\webapps\\examples";
     key.setPath("SOFTWARE\\SSCL\\ORS");
     key.newKey();        
     key.setValue("Version", "1.1");        
     key.setValue("Path", Path);
     key.setValue("DSN", "ORS");        
     key.setValue("Description", "Online Recruitment System");
         
     key.setPath("SOFTWARE\\SSCL\\ORS");
     value = key.getValue("Path");
                        
     if(value.equals(Path))
     {
       Copy files = new Copy();
       System.out.println("Destination : " + Path);
       files.setPath(Path);
                
       /*boolean exist = files.Exists("RESTORE");
            
       if(exist == false)
       {
         boolean val = false;
         val = files.rename("web.xml","RESTORE");
       }*//*
       
       try
       {
         /*tem.setPercentile(25);
         files.Copy("ORS.zip",tem);
       
         while(files.getStop() == false && !Thread.interrupted())
         {
           Thread.sleep(1);
         }
       
         ok = files.getValue();*/
         
      /*   if(ok == true)
         {
           System.out.println("System files copied Successfully !!");
           /*System.out.println("Class = " + this.getClass());
           System.out.println("TTTD 1 : " + Td.getState());
       	   System.out.println("path = " + Path);
           Process p = Runtime.getRuntime().exec("explorer F:\\SSCL\\Setup\\Readme.html");*/
        /*   files.Create("Readme.opt",port);
           Process p = Runtime.getRuntime().exec("explorer " + Path + "\\servlets\\Admin\\Readme.html");
           System.exit(0);
         }
         else
         {
           ShowErrMessage("Extracting files failed.");
         }
       }          
       catch(IOException ex)
       {
       	 ShowErrMessage("IO Exception occurred.");
       }
       /*catch(InterruptedException ie)
       { 
         ShowErrMessage("Unexpected interruption occurred.");
       }*//*
     }
     else
     {
       ShowErrMessage("Unable to set the Path.");
     }
   }
   else
   {
   	 ShowErrMessage("Database creation failed.");
   }*/	
 }
 
 private void ShowErrMessage(String Message)
 {
   Toolkit.getDefaultToolkit().beep();
   JOptionPane.showMessageDialog(null, Message,"Error",JOptionPane.ERROR_MESSAGE);
   System.exit(0); 	
 }   
}

class Jpanel extends JPanel
{
 public void add(Component c,GridBagConstraints constraints,
     int x, int y, int w, int h)
 {  
   constraints.gridx = x;
   constraints.gridy = y;
   constraints.gridwidth = w;
   constraints.gridheight = h;
   this.add(c, constraints);
 }
}

/*class KeyHandler implements KeyListener
{ 
  private Installer thread = null;
   
  public KeyHandler(Installer Th)
  {
  	thread = Th;
  }

  public void keyPressed(KeyEvent event)
  {  
    int keyCode = event.getKeyCode();

    if (keyCode == KeyEvent.VK_ENTER)
        thread.start();
    else if (keyCode == KeyEvent.VK_ESCAPE)
        System.exit(0);
  }

  public void keyReleased(KeyEvent event) {}

  public void keyTyped(KeyEvent event) {}
  
}*/

