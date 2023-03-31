package ce326.hw2;

public class MaximizerNode extends TreeNode
{
	public MaximizerNode()
	{
		// isos thelei na kano construct me size
	}
	
	double getMax()
	{
		double max = super.ChildrenArray[0].getValue();
		for (int i = 0; i < super.ChildrenArray.length; i++)
		{
			if(ChildrenArray[i].getValue() > max)
			{
				max = ChildrenArray[i].getValue();
			}
		}
		if(super.ChildrenArray.length == 0)
		{
			System.out.println("Error no children");
		}
		return max;
	}
}
