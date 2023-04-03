package ce326.hw2;

/* This is the class of the tree leaves */

public class TreeLeaves 
{
	private double NodeValue; 		// it is private
	public boolean isPruned = false;
	
	// Constructor
	public TreeLeaves()
	{
		
	}
	
	// Constructor that initializes the NodeValue
	public TreeLeaves(double NodeValue)
	{
		this.NodeValue = NodeValue;
	}
	
	/*Because NodeValue must be private, i use this method to get the value of each node */
	public double getValue()
	{
		return NodeValue;
	}
	
	public void SetValue(double NodeValue)
	{
		this.NodeValue = NodeValue;
	}
}
