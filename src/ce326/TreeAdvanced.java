package ce326;

import java.io.File;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

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
	
	
	public void checkPruned(TreeLeaves Node)
	{
		TreeNode newNode;
		
		if( !(Node instanceof MaximizerNode) && !(Node instanceof MinimizerNode) )
		{
			Node.isPruned = true;
			return;
		}
		
		newNode = (TreeNode) Node;
		
		for(int i = 0; i < newNode.getChildrenSize(); i++)
		{
			newNode.ChildrenArray[i].isPruned = true;
			checkPruned(newNode.ChildrenArray[i]);
		}
	}
	
	public void checkPrunedCall(ArrayList<TreeLeaves> arrayPruned)
	{
		for(int j = 0; j < arrayPruned.size(); j++)
		{
			checkPruned(arrayPruned.get(j));
		}
	}
	
	
	public void printarray()
	{
		System.out.println(prunedNode.toString());
	}
	
	
	public JSONObject ExportJSON(TreeLeaves CurrentNode)
	{
		if(CurrentNode instanceof MaximizerNode)
		{
			MaximizerNode newMaximizer;
			newMaximizer = (MaximizerNode) CurrentNode;
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type", "max");
			
			if(CurrentNode.isPruned)
			{
				jsonObject.put("pruned", "true");
			}
			
			JSONArray jsonarray = new JSONArray();
			
			for(int i = 0; i < newMaximizer.ChildrenArray.length; i++)
			{
				jsonarray.put(ExportJSON(newMaximizer.ChildrenArray[i]));
			}
			jsonObject.put("children", jsonarray);
			
			return jsonObject;
		}
		
		else if(CurrentNode instanceof MinimizerNode)
		{
			MinimizerNode newMaximizer;
			newMaximizer = (MinimizerNode) CurrentNode;
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type", "min");
			
			if(CurrentNode.isPruned)
			{
				jsonObject.put("pruned", "true");
			}
			
			JSONArray jsonarray = new JSONArray();
			
			for(int i = 0; i < newMaximizer.ChildrenArray.length; i++)
			{
				jsonarray.put(ExportJSON(newMaximizer.ChildrenArray[i]));
			}
			jsonObject.put("children", jsonarray);
			
			return jsonObject;
		}
		
		else if(CurrentNode instanceof TreeLeaves)
		{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("value", CurrentNode.getValue());
			
			if(CurrentNode.isPruned)
			{
				jsonObject.put("pruned", "true");
			}
			
			return jsonObject;
		}
		
		return null;
	}
	
	
	/* This method exports json when all nodes has value */
	public JSONObject ExportJSONValue(TreeLeaves CurrentNode)
	{
		
		if(CurrentNode instanceof MaximizerNode)
		{
			MaximizerNode newMaximizer;
			newMaximizer = (MaximizerNode) CurrentNode;
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type", "max");
			//System.out.println(CurrentNode.getValue());
			jsonObject.put("value", newMaximizer.getValue());
			if(CurrentNode.isPruned)
			{
				jsonObject.put("pruned", "true");
			}
			//jsonObject.put("value=", "kati");
			
			JSONArray jsonarray = new JSONArray();
			
			for(int i = 0; i < newMaximizer.ChildrenArray.length; i++)
			{
				jsonarray.put(ExportJSONValue(newMaximizer.ChildrenArray[i]));
			}
			jsonObject.put("children", jsonarray);
			
			return jsonObject;
		}
		
		else if(CurrentNode instanceof MinimizerNode)
		{
			MinimizerNode newMinimizer;
			newMinimizer = (MinimizerNode) CurrentNode;
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type", "min");
			//System.out.println(CurrentNode.getValue());
			jsonObject.put("value", newMinimizer.getValue());
			
			if(CurrentNode.isPruned)
			{
				jsonObject.put("pruned", "true");
			}
			
			JSONArray jsonarray = new JSONArray();
			
			for(int i = 0; i < newMinimizer.ChildrenArray.length; i++)
			{
				jsonarray.put(ExportJSONValue(newMinimizer.ChildrenArray[i]));
			}
			jsonObject.put("children", jsonarray);
			
			return jsonObject;
		}
		
		else if(CurrentNode instanceof TreeLeaves)
		{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("value", CurrentNode.getValue());
			
			if(CurrentNode.isPruned)
			{
				jsonObject.put("pruned", "true");
			}
			
			return jsonObject;
		}
		
		return null;
	}
	
	@Override
	public String toString()
	{
		if(isMinMax)
		{
			return (ExportJSONValue(super.returnRoot()).toString(2));
		}
		else
		{
			return (ExportJSON(super.returnRoot()).toString(2));
		}
	}
	
	
}
