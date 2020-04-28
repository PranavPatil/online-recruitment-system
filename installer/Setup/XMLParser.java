/*
   @Version 1.01 2006-01-07
   @author Pranav Patil
*/

import java.io.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

public class XMLParser
{
  public boolean setPort(String path,String value)
  {
  	boolean status = true,success = false;
  	File file = new File(path);
  	
  	System.out.println("exists = " + file.canRead());
  	
  	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
  	
  	if(file.exists())
  	{
      try
  	  {
  	    DocumentBuilder builder = factory.newDocumentBuilder();
  	
        Document doc = builder.parse(file);
  	    Element root = doc.getDocumentElement();
  	    System.out.println("Root = " + root.getTagName());
  	
  	    NodeList children = root.getChildNodes();
  	
  	    for(int i=0;i < children.getLength();i++)
  	    {
  	      Node child = children.item(i);
  	   
  	      if(child instanceof Element)
  	      {
  	        if(child.getNodeName().equals("Service"))
  	        {
  	   	      Element Chdelement = (Element) child;
  	   	      
  	   	      for(Node childNode = child.getFirstChild();
  	   	          childNode != null;
  	   	          childNode = childNode.getNextSibling())
  	   	      {
  	   	        if(childNode instanceof Element)
  	   	        {
  	   	   	     if(childNode.getNodeName().equals("Connector") && status == true)
  	   	   	     {
  	   	   	   	   Element elemt = (Element) childNode;
  	   	   	   	   elemt.setAttribute("port",value);
  	   	   	   	   System.out.println("Port set to " + elemt.getAttribute("port"));
  	   	   	   	   status = false;
  	   	   	   	   success = true;
  	   	   	     }
  	   	        }
  	   	      }  	   	   
  	        }
  	      }
  	    }
  	 
  	  Transformer t = TransformerFactory.newInstance().newTransformer();
  	 
  	  //t.setOutputProperty("doctype-system",systemIdentifier);
  	  //t.setOutputProperty("doctype-public",publicIdentifier);

  	  //t.setOutputProperty("doctype-system","http://www.w3.org/TR/2000/CR-SVG-20000802/DTD/svg-20000802.dtd");
      //t.setOutputProperty("doctype-public","-//W3C//DTD SVG 20000802//EN");
  	 
  	  t.transform(new DOMSource(doc),new StreamResult(new FileOutputStream(file)));
  	 }
  	 catch(Exception ex)
  	 {
  	   success = false;
  	   ex.printStackTrace();
  	 }
  	}
  	return success;
  }
}