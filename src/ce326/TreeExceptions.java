package ce326;

public class TreeExceptions extends java.lang.Exception
{
	String myException = null;
	public TreeExceptions(String myException)
	{
		this.myException = myException;
	}
	
	@Override
	public String toString()
	{
		return myException;
	}
}
