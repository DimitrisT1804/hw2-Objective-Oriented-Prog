package ce326.hw2;

public class TreeExceptions extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	// i had a warning and this was the default solution

	//String myException = null;
	public TreeExceptions(String myException)
	{
		//this.myException = myException;
		super(myException);
	}
	
	//@Override
//	public String toString()
//	{
//		return myException;
//	}
}
