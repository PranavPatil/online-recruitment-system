import javax.swing.*;
import java.awt.*;

public class GoodThread extends Thread
{  
   public GoodThread(JProgressBar bar,JTextArea Text)
   {  
      progress = 0;
      progressBar = bar;
      TArea = Text;
      System.out.println("GoodWorker = " + progress);
   }
   
   public void setTemp(Temp tem)
   {
      tr = tem;
   }

   public void setTarget(int value)
   {
   	  progress = value;
   }

   public void run()
   {  
      try
      {
         while (!interrupted())
         {  
            EventQueue.invokeLater(new 
               Runnable()
               {  
                  public void run()
                  {                      
                    if(tr != null)
                    {
                      text = tr.getText();
                      
                      if(text != null)
                      {
                        if(text.equals(check) == false)
                        {
                          TArea.append(text);
                          check = text;
                        }
                      }
                    }

                    if(progress < 100)
                    {
                      progress = tr.getValue();
                      progressBar.setValue(progress);
                      //System.out.println("Running..." + progress);
                    }
                  }
               });
            Thread.sleep(1); 
         }
      }
      catch (InterruptedException exception) {} 
   }
   private int progress = 0;
   private String text = null;
   private String check = null;   
   private JProgressBar progressBar;
   private JTextArea TArea;   
   private Temp tr = null;
}