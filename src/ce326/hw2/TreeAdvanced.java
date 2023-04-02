package ce326.hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class TreeAdvanced extends Tree
{
	public TreeAdvanced(File JSONFile) throws TreeExceptions 
	{
		super(JSONFile);
	}
	
	ArrayList<TreeLeaves> prunedNode = new ArrayList<TreeLeaves>();		// na diatrexo ton pinaka apo ekei kai kato
	
	
	/* Na katalavo kalitera ton kodika auton */	//sxolio na vgei !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public double MinMaxCall(TreeLeaves newNode, double alpha, double beta)
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
				currentValue = MinMaxCall(newMaximizer.ChildrenArray[i], alpha, beta);
				bestValue = Math.max(bestValue,  currentValue);
				alpha = Math.max(alpha, bestValue);
				
				//newMaximizer.SetValue(newMaximizer.AlphaValue);
				
				if(beta <= alpha)
				{
					for(int j = i+1; j < newMaximizer.getChildrenSize(); j++)
					{
						prunedNode.add(newMaximizer.ChildrenArray[j]);
					}
					
					//prunedNode.add(newMaximizer);
					
					newMaximizer.AlphaValue = alpha;
					newMaximizer.SetValue(bestValue);
					return bestValue;
					//break;		// no need to check the other children
				}
			}
			
			newMaximizer.AlphaValue = alpha;
			//newMaximizer.SetValue(newMaximizer.AlphaValue);
			newMaximizer.SetValue(bestValue);
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
				currentValue = MinMaxCall(newMinimizer.ChildrenArray[i], alpha, beta);
				bestValue = Math.min(bestValue,  currentValue);
				beta = Math.min(beta, bestValue);
				
				//newMinimizer.SetValue(newMinimizer.BetaValue);
				
				if(beta <= alpha)
				{
					for(int j = i+1; j < newMinimizer.getChildrenSize(); j++)
					{
						prunedNode.add(newMinimizer.ChildrenArray[j]);
						
					}
					//prunedNode.add(newMinimizer);
					
					newMinimizer.BetaValue = beta;
					newMinimizer.SetValue(bestValue);
					return bestValue;
					//break;		// no need to check the other children
				}
			}
			newMinimizer.BetaValue = beta;
			//newMinimizer.SetValue(newMinimizer.BetaValue);
			newMinimizer.SetValue(bestValue);
			return bestValue;
		}
		
		
		return -512;
	}
	
	public double MinMax()
	{
		return (MinMaxCall(super.returnRoot(), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
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
		CheckIfAllChildrenArePruned(super.returnRoot());
	}
	
	
	public void CheckIfAllChildrenArePruned(TreeLeaves node)
	{
		
		TreeNode newNode;
		int counter = 0;
		
		if( !(node instanceof MaximizerNode) && !(node instanceof MinimizerNode) )
			return;
		
		newNode = (TreeNode) node;
		
		for(int i = 0; i < newNode.ChildrenArray.length; i++)
		{
			if(newNode.ChildrenArray[i].isPruned)
				counter = counter + 1;
		}
		if(counter == newNode.ChildrenArray.length)
		{
			newNode.isPruned = true;
			prunedNode.add(newNode);
		}
		
		for(int i = 0; i < newNode.ChildrenArray.length; i++)
		{
			CheckIfAllChildrenArePruned(newNode.ChildrenArray[i]);
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
			jsonObject.put("type", "leaf");
			jsonObject.put("value", CurrentNode.getValue());
			
			if(CurrentNode.isPruned)
			{
				jsonObject.put("pruned", true);
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
			if(CurrentNode.isPruned)
			{
				jsonObject.put("pruned", true);
			}
			else
			{				
				jsonObject.put("value", newMaximizer.getValue());
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
			//jsonObject.put("value", newMinimizer.getValue());
			
			if(CurrentNode.isPruned)
			{
				jsonObject.put("pruned", true);
			}
			else
			{
				jsonObject.put("value", newMinimizer.getValue());
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
			jsonObject.put("type", "leaf");
			jsonObject.put("value", CurrentNode.getValue());
			
			if(CurrentNode.isPruned)
			{
				jsonObject.put("pruned", true);
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
	
	
	public void toFile(File file) throws TreeExceptions, IOException
	{
		if(file.exists())
		{
			throw new TreeExceptions("java.io.IOException");
		}
		else
		{
			if(file.createNewFile())
			{
				//System.out.println("New file Created!");
				
				try
				{
					PrintWriter WriteFile = new PrintWriter(file);	
					WriteFile.print(toString());					
					WriteFile.close();
				}
				catch (FileNotFoundException ex)
				{
					throw new TreeExceptions("FileNotFoundException");
				}

				
			}
		}
		
	}
	
	
	
	int nodeCount = 0;
	
	public String buildGraphvizTree(TreeLeaves node) 
	 {
		
		TreeNode newNode;
		StringBuilder graph = new StringBuilder();
		if (node != null)  
		{
           int nodeNumber = nodeCount++;
           
//           if(node.isPruned)
//        	   graph.append("[color = 'red'];");
           
           graph.append("\tnode").append(nodeNumber).append(" [label=\"");
           
           if (node instanceof MaximizerNode)
           {
        	   newNode = (TreeNode) node;
        	   if(!isMinMax)
        	   {
        		   graph.append("max");	   
        	   }
        	   else
        	   {
        		   graph.append(newNode.getValue());	
        	   }
        	   
        	  // graph.append("\"]\n");
        	   
               if(node.isPruned)
            	   graph.append("\", color = \"red\"];\n");
               else
            	   graph.append("\"];\n");
        	   for(int i = 0; i < newNode.getChildrenSize(); i++)
        	   {
                   graph.append("\tnode").append(nodeNumber).append(" -> ");
                   graph.append("node").append(nodeCount).append(";\n");
                   graph.append(buildGraphvizTree(newNode.ChildrenArray[i]));
        	   }
        	   
           }
           
           
           else if (node instanceof MinimizerNode)
           {
        	   newNode = (TreeNode) node;
        	   if(!isMinMax)
        	   {        		   
        		   graph.append("min");
        	   }
        	   else
        	   {
        		   graph.append(newNode.getValue());
        	   }
        	   //graph.append("\"]");
        	   
               if(node.isPruned)
            	   graph.append("\", color = \"red\"];\n");
               else
            	   graph.append("\"];\n");
        	   
        	   for(int i = 0; i < newNode.getChildrenSize(); i++)
        	   {
                   graph.append("\tnode").append(nodeNumber).append(" -> ");
                   graph.append("node").append(nodeCount).append(";\n");
                   graph.append(buildGraphvizTree(newNode.ChildrenArray[i]));
        	   }
        	   //graph.append("\"];\n");
           }
           
           else
           {
        	   //newNode = (TreeNode) node;
        	   //graph.append("max");
        	   graph.append(node.getValue());
               if(node.isPruned)
            	   graph.append("\", color = \"red\"];\n");
               else
            	   graph.append("\"];\n");
        	   //graph.append("\"];\n");
        	   //return;
//        	   for(int i = 0; i < newNode.getChildrenSize(); i++)
//        	   {
//                   graph.append("\tnode").append(nodeNumber).append(" -> ");
//                   graph.append("node").append(nodeCount).append(";\n");
//                   graph.append(buildGraphvizTree(newNode.ChildrenArray[i]));
//        	   }
           }

       }
       return graph.toString();
   }
	
	
	public String toDOTString()
	{
		String TreeString;
		StringBuilder graphPrint = new StringBuilder();
        graphPrint.append("digraph TreeGraph {\n");
        //graphPrint.append(newTree.buildGraphvizTree(newTree.returnRoot()));
        TreeString = buildGraphvizTree(super.returnRoot());
        graphPrint.append(TreeString);
        graphPrint.append("}\n");
        //System.out.println(graphPrint.toString());
		
		return graphPrint.toString();
	}
	
	
	public void toDotFile(File file) throws TreeExceptions, IOException
	{
		if(file.exists())
		{
			throw new TreeExceptions("java.io.IOException");
		}
		else
		{
			if(file.createNewFile())
			{
				//System.out.println("New file Created!");
				
				try
				{
					PrintWriter WriteFile = new PrintWriter(file);	
					WriteFile.print(toDOTString());					
					WriteFile.close();
				}
				catch (FileNotFoundException ex)
				{
					throw new TreeExceptions("FileNotFoundException");
				}	
			}
		}
		
	}
	
	double PrunedCounter = 0;
	public void prunedNodeCaclulation(TreeLeaves node)
	{
		TreeNode newNode;
		if( !(node instanceof MaximizerNode) && !(node instanceof MinimizerNode) )
		{
//			if(node.isPruned)
//				PrunedCounter = PrunedCounter + 1;
			return;
		}
		else
		{
			newNode = (TreeNode) node;
			for(int i = 0; i < newNode.getChildrenSize(); i++)
			{
				prunedNodeCaclulation(newNode.ChildrenArray[i]);
				if(newNode.ChildrenArray[i].isPruned)
					PrunedCounter = PrunedCounter + 1;
			}
		}
	}
	
	
	double prunedNode()
	{
		prunedNodeCaclulation(super.returnRoot());
		return PrunedCounter;
	}
}
