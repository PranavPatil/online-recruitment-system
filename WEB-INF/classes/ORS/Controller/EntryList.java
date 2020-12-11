
package ORS.Controller;

import java.util.*;
import ORS.Controller.Entry;

public class EntryList extends Thread
{
  private class Node
  {
    Entry record = null;
    Node previous = null;
    Node next = null;
    
    Node(Entry record, Node next, Node previous)
    {
	  this.record = record;
	  this.next = next;
	  this.previous = previous;
	}
  }

  private transient Node header = new Node(null, null, null);
  private transient int size = 0;

  public EntryList() 
  {
    header.next = header.previous = header;
    this.start();
  }

  public Entry getFirst() 
  {
	if (size==0)
      throw new NoSuchElementException();

	return header.next.record;
  }

  public Entry getLast()  
  {
	if (size==0)
      throw new NoSuchElementException();

	return header.previous.record;
  }

  public boolean contains(Object o) 
  {
    return indexOf(o) != -1;
  }

 /*  to check if prevoiusly logged in   */
  public boolean contains(long id)
  {
    ListItr iterator = new ListItr(0);
    boolean flag = false;
   	    
   	while(iterator.hasNext())
   	{
   	  Entry record = iterator.next();
   	  
   	  if(record.getId() == id)
   	  {
   	  	flag = true;
   	  	break;
   	  }
    }
    return flag;
  }

  public boolean contains(long id,String session)
  throws Exception
  {
    ListItr iterator = new ListItr(0);
    boolean flag = false;

    while(iterator.hasNext())
    {
      Entry record = iterator.next();
   	  
   	  if((record.getId() == id) && session.equals(record.getSessionId()))
   	  {
   	   flag = true;
   	   break;
   	  }
    }
    return flag;
  }

  public int size() 
  {
	return size;
  }
  
  public String toString()
  {
    ListItr iterator = new ListItr(0);
    String str = "";
   	    
   	while(iterator.hasNext())
   	{
   	  Entry record = iterator.next();
      str = str + record.toString() + "\n";
    }
    return str;
  }

  private Node addBefore(Entry o, Node e) 
  {
    Node newNode = new Node(o, e, e.previous);
    newNode.previous.next = newNode;
    newNode.next.previous = newNode;
	size++;
	return newNode;
  }

  public boolean add(Entry o) 
  {
    if(!contains(o.getId()))
    {
      addBefore(o, header);
      return true;    	
    }
    else
      return false;
  }

  public void add(int index, Entry record) 
  {
    if(!contains(record.getId()))
      addBefore(record, (index==size ? header : entry(index)));
  }

  public boolean remove(Entry o) 
  {
    if (o==null) 
    {
      for (Node e = header.next; e != header; e = e.next) 
      {
        if (e.record==null) 
        {
          remove(e);
          return true;
        }
      }
    } 
    else
    {
      for (Node e = header.next; e != header; e = e.next) 
      {
        if (o.equals(e.record)) 
        {
          remove(e);
          return true;
        }
      }
    }
    return false;
  }

  private Entry remove(Node e) 
  {
	if (e == header)
     throw new NoSuchElementException();

    Entry result = e.record;
	e.previous.next = e.next;
	e.next.previous = e.previous;
    e.next = e.previous = null;
    e.record = null;
	size--;
    return result;
  }

  public Entry remove(int index) 
  {
    return remove(entry(index));
  }

  public Entry removeEntry(long id)
  {
    ListItr iterator = new ListItr(0);
    Entry record = null;
   	    
   	while(iterator.hasNext())
   	{
   	  record = iterator.next();
   	  
   	  if(record.getId() == id)
   	  {
   	  	remove(record);
   	  	break;
   	  }
    }
    return record;
  }

  public void clear() 
  {
    Node e = header.next;
    while (e != header) 
    {
      Node next = e.next;
      e.next = e.previous = null;
      e.record = null;
      e = next;
    }
    header.next = header.previous = header;
    size = 0;
  }

  public Entry get(int index) 
  {
     return entry(index).record;
  }

  public Entry getEntry(long id) 
  {
    ListItr iterator = new ListItr(0);
    Entry record = null;
   	    
   	while(iterator.hasNext())
   	{
   	  record = iterator.next();
   	  
   	  if(record.getId() == id)
   	  	break;
    }
    return record;
  }

  public Entry set(int index, Entry record) 
  {
    if(!contains(record.getId()))
    {
      Node e = entry(index);
      Entry oldVal = e.record;
      e.record = record;
      return oldVal;
    }
    else
      return null;
  }

   /* Special case considered
    * id given, enter current time as last accessed time    * */
    
  public boolean set(long id, Date lastaccess)
  {
    ListItr iterator = new ListItr(0);
    boolean flag = false;
   	    
   	while(iterator.hasNext())
   	{
   	  Entry record = iterator.next();
   	  
   	  if(record.getId() == id)
   	  {
   	  	record.setLastAccess(lastaccess);
   	  	flag = true;
   	  	break;
   	  }
    }
    return flag;
  }

  private Node entry(int index) 
  {
    if (index < 0 || index >= size)
       throw new IndexOutOfBoundsException("Index: "+index+ ", Size: "+size);

    Node e = header;

    if (index < (size >> 1)) 
    {
      for (int i = 0; i <= index; i++)
         e = e.next;
    } 
    else 
    {
      for (int i = size; i > index; i--)
         e = e.previous;
    }
    return e;
  }

  public int indexOf(Object o) 
  {
    int index = 0;
    
    if (o==null) 
    {
      for (Node e = header.next; e != header; e = e.next) 
      {
        if (e.record==null)
          return index;
        index++;
      }
    }
    else
    {
      for (Node e = header.next; e != header; e = e.next) 
      {
        if (o.equals(e.record))
          return index;
        index++;
      }
    }
    return -1;
  }

  public int lastIndexOf(Object o) 
  {
    int index = size;
    
    if (o==null) 
    {
      for (Node e = header.previous; e != header; e = e.previous) 
      {
        index--;
      
        if (e.record==null)
          return index;
      }
    } 
    else 
    {
      for (Node e = header.previous; e != header; e = e.previous) 
      {
        index--;
        if (o.equals(e.record))
          return index;
      }
    }
    return -1;
  }

  public ListItr listIterator(int index) 
  {
    return new ListItr(index);
  }

  private class ListItr
  {
    private Node lastReturned = header;
    private Node next;
    private int nextIndex;

	ListItr(int index) 
	{
	  if (index < 0 || index > size)
		throw new IndexOutOfBoundsException("Index: "+index+", Size: "+size);
	  
	  if (index < (size >> 1))
	  {
		next = header.next;
		for (nextIndex=0; nextIndex<index; nextIndex++)
		    next = next.next;
	  } 
	  else 
	  {
		next = header;
		for (nextIndex=size; nextIndex>index; nextIndex--)
		    next = next.previous;
	  }
	}

	public boolean hasNext() 
	{
	  return nextIndex != size;
	}

	public Entry next() 
	{
	  if (nextIndex == size)
		throw new NoSuchElementException();

      lastReturned = next;
      next = next.next;
      nextIndex++;
      return lastReturned.record;
	}

	public boolean hasPrevious() 
	{
	  return nextIndex != 0;
	}

	public Entry previous() 
	{
	  if (nextIndex == 0)
		throw new NoSuchElementException();

	  lastReturned = next = next.previous;
	  nextIndex--;
	  return lastReturned.record;
	}

	public int nextIndex() 
	{
	  return nextIndex;
	}

	public int previousIndex() 
	{
	  return nextIndex-1;
	}

	public void remove() 
	{
      Node lastNext = lastReturned.next;
    
      try 
      {
        EntryList.this.remove(lastReturned);
      }
      catch (NoSuchElementException e)
      {
        throw new IllegalStateException();
      }
	  
	  if (next==lastReturned)
        next = lastNext;
      else
		nextIndex--;
	
	  lastReturned = header;
	}

	public void set(Entry o) 
	{
	  if (lastReturned == header)
		throw new IllegalStateException();
	  lastReturned.record = o;
	}

	public void add(Entry o) 
	{
      if(!contains(o.getId()))
      {
	    lastReturned = header;
	    addBefore(o, next);
	    nextIndex++;
	  }
    }
  }
  
  public void run()
  {
  	while(true)
  	{
  	  try
  	  {
   	    ListItr iterator = new ListItr(0);
   	    
   	    while(iterator.hasNext())
   	    {
   	      Entry record = iterator.next();
   	      Calendar time = Calendar.getInstance();
   	      time.add(Calendar.HOUR,-1);
      
   	      if(record != null && record.getLastAccess().compareTo(time.getTime()) <= 0 )
   	      {
   	      	System.out.println("Element = " + record);
   	      	this.remove(record);
   	      }
   	      record = null;
   	    }
  	    sleep(1000);
  	  }
  	  catch(InterruptedException Iex)
  	  {
  	  	Iex.printStackTrace();
  	  }
  	}
  }
}