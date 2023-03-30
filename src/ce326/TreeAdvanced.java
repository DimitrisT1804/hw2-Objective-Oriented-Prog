package ce326;

import java.io.File;
import java.util.*;

public class TreeAdvanced extends Tree
{
	public TreeAdvanced(File JSONFile) 
	{
		super(JSONFile);
	}
	
	ArrayList<TreeLeaves> prunedNode = new ArrayList<TreeLeaves>();		// na diatrexo ton pinaka apo ekei kai kato
	
	
	/* Na katalavo kalitera ton kodika auton */	//sxolio na vgei !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public double MinMax(TreeLeaves newNode, double alpha, double beta)
	{
		if( !(newNode instanceof MaximizerNode) && !(newNode instanceof MinimizerNode) )
		{
			return newNode.getValue();
		}
		
		if(newNode instanceof MaximizerNode)
		{
			TreeNode newMaximizer;
			newMaximizer = (TreeNode) newNode;
			double bestValue = Double.NEGATIVE_INFINITY;
			
			for(int i = 0; i < newMaximizer.getChildrenSize(); i++)
			{
				double currentValue;
				currentValue = MinMax(newMaximizer.ChildrenArray[i], alpha, beta);
				bestValue = Math.max(bestValue,  currentValue);
				alpha = Math.max(alpha, bestValue);
				
				//newMaximizer.SetValue(newMaximizer.AlphaValue);
				
				if(beta <= alpha)
				{
					for(int j = i+1; j < newMaximizer.getChildrenSize(); j++)
					{
						prunedNode.add(newMaximizer.ChildrenArray[j]);
					}
					
					
					break;		// no need to check the other children
				}
			}
			
			newMaximizer.AlphaValue = alpha;
			newMaximizer.SetValue(alpha);
			return bestValue;
		}
		
		else if(newNode instanceof MinimizerNode)
		{
			TreeNode newMinimizer;
			newMinimizer = (TreeNode) newNode;
			double bestValue = Double.POSITIVE_INFINITY;
			
			for(int i = 0; i < newMinimizer.getChildrenSize(); i++)
			{
				double currentValue;
				currentValue = MinMax(newMinimizer.ChildrenArray[i], alpha, beta);
				bestValue = Math.min(bestValue,  currentValue);
				beta = Math.min(beta, bestValue);
				
				//newMinimizer.SetValue(newMinimizer.BetaValue);
				
				if(beta <= alpha)
				{
					for(int j = i+1; j < newMinimizer.getChildrenSize(); j++)
					{
						prunedNode.add(newMinimizer.ChildrenArray[j]);
					}
					break;		// no need to check the other children
				}
			}
			newMinimizer.BetaValue = beta;
			newMinimizer.SetValue(newMinimizer.BetaValue);
			return bestValue;
		}
		
		
		return -512;
	}
	
	
	public void printarray()
	{
		System.out.println(prunedNode.toString());
	}
	
	
}
