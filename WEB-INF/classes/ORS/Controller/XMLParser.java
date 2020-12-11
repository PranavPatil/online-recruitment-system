package ORS.Controller;

import java.io.*;
import java.util.*;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParser
{
  public static HashMap parseControlFile(String fileUrl)
  {
  	File file = new File(fileUrl);
  	HashMap urlMappings = null;

  	if(file.exists())
  	{
      try
  	  {
  	    SAXParserFactory factory = SAXParserFactory.newInstance();
  	    SAXParser saxParser = factory.newSAXParser();
  	
        URLMappingHandler urlMapHandler = new URLMappingHandler();

      // Parse the XML file
        saxParser.parse(fileUrl, urlMapHandler);

      // Get URLMappings
        urlMappings = urlMapHandler.getMappings();
  	  }
      catch (Exception pe) 
      {
        System.out.println("Parser Exception: " + pe);
      }
    }
    return urlMappings;
  }
  
  public static void main(String[] args)
  {
  	XMLParser t = new XMLParser();
  	HashMap map = t.parseControlFile("AdminControl.xml");
  	//System.out.println(map.toString());
  	
  	List keys = new ArrayList(map.keySet());
    //Collections.sort(keys);

    ListIterator keyIterator = keys.listIterator();
    int i = 0;
    while (keyIterator.hasNext()) 
    {
      String group = (String) keyIterator.next();
      //URLMapping group = (URLMapping) keyIterator.next();
      //String count = (String) map.get(group);
      i++;

      System.out.println(group + ":\t");
    }
    System.out.println("i = " + i);
  }
}


class URLMappingHandler extends DefaultHandler {

  // Hashmap to hold URLMappings
  private HashMap hmap = new HashMap();

  // Instance of URLMap, currently being populated.
  private URLMapping urlMap = null;

  // Name of the last tag encountered by startElement(..) method
  private String lastTag = null;

  /**
   * Returns the mappings that have been parsed and loaded in Hashmap.
   *
   * @return HashMap containing URLMappings.
   * @since 1.0
   */
  public HashMap getMappings() 
  {
    return hmap;
  }

  /**
   * Clears the Hashmap.
   *
   * @since 1.0
   */
  public void clearMappings() 
  {
    hmap.clear();
  }

  /**
   * This method is notified when a start tag is encountered.
   *
   * @param namespaceURI namespace URI
   * @param sName local name of the tag
   * @param qName quantified name of the tag
   * @param attrs Attribute list of the tag
   * @since 1.0
   */
  public void startElement(String namespaceURI, String sName, String qName,
                           Attributes attrs) 
  {
    String tagName = sName;

    // If Namespace aware
    if (sName.equals("") || (sName == null)) {
      tagName = qName;
    }

    // A new event was encountered, initialize a new URLMapping
    if (tagName.equals("Event")) {

    // Start a new URL Mapping
    urlMap = new URLMapping();

    } else {
      lastTag = tagName;
    }
  }

  /**
   * This method is notified when a end tag is encountered.
   *
   * @param namespaceURI namespace URI
   * @param sName local name of the tag
   * @param qName quantified name of the tag
   * @since 1.0
   */
  public void endElement(String namespaceURI, String sName, String qName) 
  {
    String tagName = sName;

    // If namespace aware
    if (sName.equals("") || (sName == null)) {
      tagName = qName;
    }

    // If end of a URL mapping
    if (tagName.equals("Event"))
    {
      // Put the  URL Mapping into the Hashmap
      hmap.put(urlMap.getEventName(), urlMap);
    }
    // Reset last tag
    lastTag = null;
  }

  /**
   * This method is notified when the characters in-between tags is encountered.
   *
   * @param ch Character array of the text
   * @param offset start position in the char array
   * @param len length of the characters to be taken from the char array
   * @since 1.0
   */

  public void characters(char[] ch, int offset, int len) 
  {
    if (len == 0 || lastTag==null)   return;

    String tagValue = new String(ch, offset, len);

    if (lastTag.equals("Name"))
    {
      if(urlMap.getEventName() == null)
        urlMap.setEventName(tagValue);
      else
        urlMap.setEventName(urlMap.getEventName() + tagValue);
    }

    else if (lastTag.equals("Class"))
    {
      if(urlMap.getClassName() == null)
      	urlMap.setClassName(tagValue);
      else
        urlMap.setClassName(urlMap.getClassName() + tagValue);
    }

    else if (lastTag.equals("Method"))
    {
      if(urlMap.getMethodName() == null)
        urlMap.setMethodName(tagValue);
      else
        urlMap.setMethodName(urlMap.getMethodName() + tagValue);
    }

    else if (lastTag.equals("View"))
    {
      if(urlMap.getNextScreen() == null)
        urlMap.setNextScreen(tagValue);
      else
      	urlMap.setNextScreen(urlMap.getNextScreen() + tagValue);
    }
  }
}  // End of class URLMappingHandler
