package ce326.hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class TreeAdvanced extends Tree		// Advanced Tree that implements the MinMax with pruning algorithm and extends basic Tree
{
	/* Construct TreeAdvanced using super constructor with input JSONFile */
	public TreeAdvanced(File JSONFile) throws TreeExceptions 
	{
		super(JSONFile);
	}
	
	// ArrayList that stores the pruned nodes
	ArrayList<TreeLeaves> prunedNode = new ArrayList<TreeLeaves>();
	/* Method that solve Tree with MinMax with pruning algorithm. It has input also alpha and beta to take easier the correct value from parent */
	public double MinMaxCall(TreeLeaves newNode, double alpha, double beta)
	{
		if( !(newNode instanceof MaximizerNode) && !(newNode instanceof MinimizerNode) )  	// if it is node just return value
		{
			return newNode.getValue();
		}
		
		if(newNode instanceof MaximizerNode)
		{
			TreeNode newMaximizer;
			newMaximizer = (TreeNode) newNode;	// Typecast only if it is of type MaximizerNode, otherwise it throws classException
			double MaximumValue = Double.NEGATIVE_INFINITY;		// Initializing with maximum negative value
			
			for(int i = 0; i < newMaximizer.getChildrenSize(); i++)
			{
				double currentValue;
				currentValue = MinMaxCall(newMaximizer.ChildrenArray[i], alpha, beta);
				MaximumValue = Math.max(MaximumValue,  currentValue);		// We choose maximum value of the returning and current maximum
				alpha = Math.max(alpha, MaximumValue);		// We add new value to alpha which is the maximum of maximum value and last alpha value
				
				if(beta <= alpha)		// If beta is smaller than alpha the node should be pruned 
				{
					/* We add all pruned Nodes in an arrayList to know which of them are pruned */
					for(int j = i+1; j < newMaximizer.getChildrenSize(); j++)
					{
						prunedNode.add(newMaximizer.ChildrenArray[j]);
					}
					
					newMaximizer.alpha = alpha;
					newMaximizer.SetValue(MaximumValue);
					return MaximumValue;
				}
			}
			
			newMaximizer.alpha = alpha;		// We add new value in the current node with the new alpha value
			newMaximizer.SetValue(MaximumValue);	// Set value of current node with MaximumValue and then return maximum value
			return MaximumValue;
		}
		
		else if(newNode instanceof MinimizerNode)
		{
			TreeNode newMinimizer;
			newMinimizer = (TreeNode) newNode;
			double MaximumValue = Double.POSITIVE_INFINITY;
			
			for(int i = 0; i < newMinimizer.getChildrenSize(); i++)
			{
				double currentValue;
				currentValue = MinMaxCall(newMinimizer.ChildrenArray[i], alpha, beta);
				MaximumValue = Math.min(MaximumValue,  currentValue);		// same thing but with minimum value 
				beta = Math.min(beta, MaximumValue);		// same thing but for beta value and minimum value
				
				if(beta <= alpha)
				{
					for(int j = i+1; j < newMinimizer.getChildrenSize(); j++)
					{
						prunedNode.add(newMinimizer.ChildrenArray[j]);
						
					}
					
					newMinimizer.beta = beta;
					newMinimizer.SetValue(MaximumValue);
					return MaximumValue;
				}
			}
			newMinimizer.beta = beta;
			newMinimizer.SetValue(MaximumValue);
			return MaximumValue;
		}
		
		
		return -512;		// Random value just for the Warning, it will never send it
	}
	
	
	/* Calling the MinMax with alpha-beta pruning algorithm for the current Tree */
	public double MinMax()
	{
		/* Need to use super to use the method returnRoot because it extends the Tree class */
		return (MinMaxCall(super.returnRoot(), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
	}
	
	
	/* Method that check the variable pruned true for the input node and all childrend of it if exists */
	public void checkPruned(TreeLeaves Node)
	{
		TreeNode newNode;
		
		if( !(Node instanceof MaximizerNode) && !(Node instanceof MinimizerNode) )	// If it is leaf
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
	
	/* Calling the method to check PrunedNodes for each node of the array */
	public void checkPrunedCall(ArrayList<TreeLeaves> arrayPruned)
	{
		for(int j = 0; j < arrayPruned.size(); j++)
		{
			checkPruned(arrayPruned.get(j));
		}
		CheckIfAllChildrenArePruned(super.returnRoot());
	}
	
	/* If all Children of a node are pruned the node should also be pruned */
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
	
	
//	public void printarray()
//	{
//		System.out.println(prunedNode.toString());
//	}
//	
	
	/* A method that generates the JSON format from current Tree */
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
				jsonObject.put("pruned", "true");	// We also add the pruned variable if the node is pruned
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
			if(CurrentNode.isPruned)
			{
				jsonObject.put("pruned", true);
			}
			else
			{				
				jsonObject.put("value", newMaximizer.getValue());
			}
			
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
		if(isMinMax)	// Checks if the algorithm run in this Tree to know if we should run the JSON with values
		{
			return (ExportJSONValue(super.returnRoot()).toString(2));	// 2 as input in toString to add spaces for better view
		}
		else
		{
			return (ExportJSON(super.returnRoot()).toString(2));
		}
	}
	
	/* Method that prints in a file the JSON format of the tree */
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
	/*Method that Created the DOT Format in a StringBuilder so that we can use the graphiz suite to represent the Tree */
	public String buildGraphvizTree(TreeLeaves node) 
	 {
		
		TreeNode newNode;
		StringBuilder graph = new StringBuilder();
		if (node != null)  
		{
           int nodeNumber = nodeCount++;

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
        	   
               if(node.isPruned)	// If it is pruned we should make the node red 
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
           
           else
           {
        	   graph.append(node.getValue());
               if(node.isPruned)
            	   graph.append("\", color = \"red\"];\n");
               else
            	   graph.append("\"];\n");
           }

       }
       return graph.toString();
   }
	
	/* Method that print DOT in STDOUT */
	public String toDOTString()
	{
		String TreeString;
		StringBuilder graphPrint = new StringBuilder();
        graphPrint.append("digraph TreeGraph {\n");
        TreeString = buildGraphvizTree(super.returnRoot());
        graphPrint.append(TreeString);
        graphPrint.append("}\n");
		
		return graphPrint.toString();
	}
	
	/* Method that print DOT in the specified File */
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
	/* Method that penetrates each node to count the prunedNodes */
	public void prunedNodeCaclulation(TreeLeaves node)
	{
		TreeNode newNode;
		if( !(node instanceof MaximizerNode) && !(node instanceof MinimizerNode) )
		{
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
	
	
	double prunedNodes()
	{
		prunedNodeCaclulation(super.returnRoot());
		return PrunedCounter;
	}
}
