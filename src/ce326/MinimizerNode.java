package ce326;

public class MinimizerNode extends TreeNode
{
	public MinimizerNode()
	{
		// isos thelei na kano construct me size
	}
	
	double getMin()
	{
		double min = 0;
		for (int i = 0; i < super.ChildrenArray.length; i++)
		{
			if(ChildrenArray[i].getValue() < min)
			{
				min = ChildrenArray[i].getValue();
			}
		}
		return min;
	}
}
