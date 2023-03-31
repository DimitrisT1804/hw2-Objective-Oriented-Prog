package ce326.hw2;

/* This is the class of the tree leaves */

public class TreeLeaves 
{
	private double NodeValue; 		// it is private
	public boolean isPruned = false;
	
	public TreeLeaves()
	{
		
	}
	
	public TreeLeaves(double NodeValue)
	{
		//TreeLeaves newNode = TreeLeaves();
		this.NodeValue = NodeValue;
	}
	
	public double getValue()
	{
		return NodeValue;
	}
	
	public void SetValue(double NodeValue)
	{
		this.NodeValue = NodeValue;
	}
}
