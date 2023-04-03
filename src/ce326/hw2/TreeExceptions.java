package ce326.hw2;

public class TreeExceptions extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	// i had a warning and this was the default solution

	/* It extends the Exception class, so i use them with super */
	public TreeExceptions(String myException)
	{
		super(myException);
	}
}
