package ce326.hw2;

public class MinimizerNode extends TreeNode
{
	// Constructor
	public MinimizerNode()
	{
	
	}
	
	double getMin()
	{
		double min = super.ChildrenArray[0].getValue();
		for (int i = 0; i < super.ChildrenArray.length; i++)
		{
			if(ChildrenArray[i].getValue() <= min)
			{
				min = ChildrenArray[i].getValue();
			}
		}
		if(super.ChildrenArray.length == 0)
		{
			System.out.println("Error no children min");
		}
		return min;
	}
}
