package ORS.Utils;

public class Validator
{
  public static boolean isAlphabet(String str)
  {
	int i=0;
	boolean flag = true;
	
	if(str == null)
	  return false;

    while(flag==true && i<str.length())
	{
   	  flag = Character.isLetter(str.charAt(i));
      i++;
	}

	return flag;
  }

  public static boolean isNameField(String str)
  {
	int i=0;
	boolean flag = true,space = true;
	
	if(str == null)
	  return false;

    while(flag==true && i<str.length())
	{
   	  flag = Character.isLetter(str.charAt(i));
   	  
   	  if(flag)
   	  {
   	  	space = false;
   	  }   	  
   	  else if(str.charAt(i) == ' ' && space==false)
   	  {
   	    flag = true;
   	    space = true;
   	  }
   	  
      i++;
      
      if(i == str.length() && str.charAt(i-1) == ' ')
       flag = false;
	}

	return flag;
  }

  public static boolean isEmailValid(String email)
  {
	int i=0,aval =  0;
	boolean flag = true;
	boolean amp = false,dot = false;
	String[] list = {"com","org","gov","net","usa","edu"};
	
	if(email == null)
	  return false;

	while(flag==true && i<email.length())
	{
	  flag = false;
	  char ch = email.charAt(i);
	  
	  if(i < 3)
	  {
	  	flag = Character.isLetter(ch);
	  	//System.out.println("(i < 3) ch = " + ch + "  i=" + i);
	  }
	  else if(amp == false)
	  {
	  	flag = Character.isLetter(ch);
	  	
	  	if(flag == false)
	  	{
	  	   flag = Character.isDigit(ch);
	  	   
	  	   if(flag == false)
	  	   {
	  	   	 if(ch == '\u0040')
	  	   	 {
	  	   	 	amp = true;
	  	   	 	flag = true;
	  	   	 }
	  	   }
	  	}
	  	//System.out.println("(i > 4 && amp==false) ch = " + ch + "  i=" + i);
	  }
	  else if(amp == true && dot == false)
	  {
	  	flag = Character.isLetter(ch);
	  	
        if(flag == false && aval > 3)
	    {
	   	  if(ch == '.')
	  	  {
	  	 	dot = true;
	  	 	flag = true;
	  	  }
	  	}
	  	aval++;
	  }
	  else if(dot == true)
	  {
	  	String substr = email.substring(i);
	  	//System.out.println("substr = " + substr);
	  	i = email.length() - 1;
	  	
	  	for(int j =0; j < list.length; j++)
	  	{
	  	  if(substr.equalsIgnoreCase(list[j]))
	  	    flag = true;
	  	}
	  }
      i++;
	}
	
	if(flag == true && amp == true && dot == true)
	  flag = true;
	else
	  flag = false;
	
	return flag;
  }

  public static boolean isValidField(String field)
  {
	int i=0;
	boolean flag = true;
	
	if(field == null)
	  return false;

	while(flag==true && i<field.length())
	{
	  char ch = field.charAt(i);
   	  flag = Character.isLetter(ch);
   	  
   	  if(flag == false)
   	  {
   	  	flag = Character.isDigit(ch);
   	  	
   	    if(flag == false)
   	    {
   	      if(ch == '_')
   	      {
   	      	flag = true;
   	      }
   	    }
   	  }

      i++;
	}
	
	return flag;
  }
}