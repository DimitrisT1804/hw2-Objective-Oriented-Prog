package ce326;

public class MaximizerNode extends TreeNode
{
	public MaximizerNode()
	{
		// isos thelei na kano construct me size
	}
	
	double getMax()
	{
		double max = 0;
		for (int i = 0; i < super.ChildrenArray.length; i++)
		{
			if(ChildrenArray[i].getValue() > max)
			{
				max = ChildrenArray[i].getValue();
			}
		}
		return max;
	}
}
