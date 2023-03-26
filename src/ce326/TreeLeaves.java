package ce326;

/* This is the class of the tree leaves */

public class TreeLeaves 
{
	private double NodeValue; 		// it is private
	
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
}
