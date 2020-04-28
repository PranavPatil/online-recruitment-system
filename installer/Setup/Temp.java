public class Temp 
{
	private int current;
	private int Total;
	private int Percentile;
	private long target;
	private String Text;
	
	public void Temp()
	{
	  current = 0;
	  Total = 0;
	  Percentile = 0;
	  target = 0;
	  Text = null;
	}
	
	public void setPercentile(int value)
	{
	  Total = Total + Percentile;
	  Percentile = value;
	}

	public void setTarget(long val)
	{
	  target = val;
	}

	public int getValue()
	{
	  return current;	
	}
	
	public void setValue(long val)
	{
	  current = (int)((val*Percentile)/target);
	  current = current + Total;
	}
	
	public String getText()
	{
	  return Text;	
	}
	
	public void setText(String t)
	{
		Text = t;
	}
}