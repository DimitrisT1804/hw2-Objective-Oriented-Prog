package ce326.hw2;

import java.io.*;
import java.util.*;
import org.json.*;

public class Tree 
{
	private TreeLeaves root;		// The root of the tree
	Tree newTree;
	JSONObject JSONFile;
	boolean isMinMax = false;
	boolean isPruned = false;
	boolean onlyRoot = false;
	
	
	// Constructor that has input a String in JSON Format
	public Tree (String jsonString)
	{
		try		// kano dokimi ean eiani JSONString kai an lavo to diko mou exception ektipono antistoixa
			
		{
			CheckingJSON(jsonString);		
		}
		catch(TreeExceptions ex)
		{
			System.out.println("Caught exception: " + ex.toString());	// mallon prepei na ektipono kai to exception
		}
		
		System.out.println("Ola komple");
		
	}
	
	public Tree (File JSONFile) throws TreeExceptions
	{
		String JSONstring;
	    StringBuilder strBuilder = new StringBuilder();
	    try(Scanner sc = new Scanner(JSONFile)) 	// Copying the text of file in a String 
	    {
	      while(sc.hasNextLine() )
	      {
	        String str = sc.nextLine();
	        strBuilder.append(str);
	        strBuilder.append("\n");
	      }
	    } 
	    catch(IOException ex) 		// handling exceptions if it is not JSON
	    {
	      ex.printStackTrace();		// If it catch any IOException it throws it
	    }
	    JSONstring = strBuilder.toString();
	    
	    try		// Trying to construct the tree and if it fails i throw a new exception
	    {	    	
	    	CheckingJSON(JSONstring);
	    }
	    
		catch(TreeExceptions ex)
		{
			throw new TreeExceptions("java.util.IllegalArgumentException");
		}
		
	}
	
	
	
	public void CheckingJSON(String jsonString) throws TreeExceptions	// elegxo ean einai JSONString kai an einai parago to diko mou exception
	{
		JSONObject JSONFile;
		
		try		// Check if the file has JSON format
		{
			JSONFile =  new JSONObject(jsonString);
		}
		catch(JSONException isJSON)		// Handling the case that it is not and i throw a new exception
		{
			throw new TreeExceptions("java.util.IllegalArgumentException");
		}
		
		root = CreateMinMaxTree(JSONFile);	// Calling the method that constructs the Tree
		
		try		// Testing if the Tree has only one node
		{
			TreeNode testNode;
			testNode = (TreeNode) root;
		}
		catch(ClassCastException ex6)		// Exception if it is only root in the tree cause by typecast
		{
			onlyRoot = true;
		}
	}
	
	public TreeLeaves CreateMinMaxTree(JSONObject JSONFile)
	{
		JSONArray jsonarray;
		
		if(JSONFile == null)	// if there is no JSONFile
		{
			return null;
		}
		
		/* Get type of the jsonfile object and try to find what type is */
		String StringFromJSON = JSONFile.getString("type");	
		if(StringFromJSON.matches("max"))
		{
			MaximizerNode newNode = new MaximizerNode();		// Create node of type Max
			jsonarray = JSONFile.getJSONArray("children");		// Create the array with children
			newNode.setChildrenSize(jsonarray.length());
			
			for(int i = 0; i < jsonarray.length(); i++)
			{
				JSONObject children = jsonarray.getJSONObject(i);
				newNode.insertChild(i, CreateMinMaxTree(children));		// recursive to add children in each node
			}
			return newNode;
		}
		
		else if(StringFromJSON.matches("min"))		// Same for min
		{
			MinimizerNode newNode = new MinimizerNode();
			jsonarray = JSONFile.getJSONArray("children");
			newNode.setChildrenSize(jsonarray.length());
			
			for(int i = 0; i < jsonarray.length(); i++)
			{		
				JSONObject children = jsonarray.getJSONObject(i);
				newNode.insertChild(i, CreateMinMaxTree(children));

			}
			
			return newNode;
		}
		else if (StringFromJSON.matches("leaf"))		// If it is leaf create a node of type TreeLeaves and just give them the value
		{
			TreeLeaves newNode = new TreeLeaves(JSONFile.getDouble("value"));
			return newNode;
		}
		else
		{			
			return null;
		}
		
	}
	
	
	/* Postorder Traversal for Checking the construction of the Tree */
	public void postorderTraversal(TreeLeaves node)
	{
	    if (node instanceof MaximizerNode) {
	        MaximizerNode maxNode = (MaximizerNode) node;
	        for (int i = 0; i < maxNode.getChildrenSize(); i++) {
	            postorderTraversal(maxNode.getChild(i));
	        }
	        System.out.print("Max ");
	    } else if (node instanceof MinimizerNode) {
	        MinimizerNode minNode = (MinimizerNode) node;
	        for (int i = 0; i < minNode.getChildrenSize(); i++) {
	            postorderTraversal(minNode.getChild(i));
	        }
	        System.out.print("Min ");
	    } else if (node instanceof TreeLeaves) {
	        TreeLeaves leavesNode = node;
	        System.out.print(leavesNode.getValue() + " ");
	    }
	}
	
	/* A method that run the MinMax algorithm for the Tree */
	public TreeLeaves minMaxCall(TreeLeaves root)
	{
		if (root instanceof MaximizerNode)		// Using instanceof to get the type of each node 
		{
			MaximizerNode newNode;
			newNode = (MaximizerNode) root;		// It is MaximizerNode so we can Typecast it and use the methods that we need
			for(int i = 0; i < newNode.ChildrenArray.length; i++)
			{
				minMaxCall(newNode.ChildrenArray[i]);		// Recursive for each child of the node
			}
			
			newNode.SetValue(newNode.getMax());		// After initializing all children of each node, it gets the maximum value of each node
			return newNode;
		}
		
		else if (root instanceof MinimizerNode)		// same for the MinimizerNode
		{
			MinimizerNode newNode;
			newNode = (MinimizerNode) root;
			for(int i = 0; i < newNode.ChildrenArray.length; i++)
			{
				minMaxCall(newNode.ChildrenArray[i]);
			}
			
			newNode.SetValue(newNode.getMin());
			return newNode;
		}
		
		else if(root instanceof TreeLeaves)		// if it is leaf just return it
		{
			return root;
		}
		return null;		// Just for the Warning
	}
	
	public double minMax()
	{
		root = minMaxCall(root);
		//postorderTraversalValues(root);
		
		return root.getValue();
	}
	
	public void postorderTraversalValues(TreeLeaves node) 
	{
		TreeNode newNode;
		if(node == null)
			return;
		
		if(node instanceof TreeNode)
		{		
			newNode = (TreeNode) node;
			for(int i = 0; i < newNode.ChildrenArray.length; i++)
			{
				postorderTraversalValues(newNode.ChildrenArray[i]);
			}
		}
		System.out.print(node.getValue() + " ");
	}
	
	
	/* It returns the size of the tree */
	int size = 1;		// it will have a root at least
	public int sizeImplementation(TreeLeaves newNode)
	{
		if(newNode instanceof TreeNode)
		{
			TreeNode node = (TreeNode) newNode;
			
			size = size + node.ChildrenArray.length;
			for(int i = 0; i < node.ChildrenArray.length; i++)
			{
				sizeImplementation(node.ChildrenArray[i]);
			}			
		}
		
		return size;
	}
	
	
	public int size()
	{
		int finalSize;
		finalSize = sizeImplementation(root);
		
		return finalSize;
	}
	
	/* Just to get root because it should be private */
	public TreeNode returnRoot()
	{
		
		return (TreeNode) root;
	}
	
	
	/* A method that generates the JSON format from current Tree */
	public JSONObject ExportJSON(TreeLeaves CurrentNode)	// It has input because it should be recursive
															// and it has type TreeLeaves cause it should take all kind of nodes
	{
		/* Printing JSON using json.put to make the correct format */
		if(CurrentNode instanceof MaximizerNode)
		{
			MaximizerNode newMaximizer;
			newMaximizer = (MaximizerNode) CurrentNode;
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type", "max");
			
			JSONArray jsonarray = new JSONArray();
			
			for(int i = 0; i < newMaximizer.ChildrenArray.length; i++)
			{
				jsonarray.put(ExportJSON(newMaximizer.ChildrenArray[i]));
			}
			jsonObject.put("children", jsonarray);		// it adds the string children and the array in format of array
			
			return jsonObject;
		}
		
		else if(CurrentNode instanceof MinimizerNode)
		{
			MinimizerNode newMaximizer;
			newMaximizer = (MinimizerNode) CurrentNode;
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type", "min");
			
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
			
			return jsonObject;
		}
		
		return null;
	}
	
	
	/* This method exports json when all nodes has value and replaces Min and Max with the value of the node*/
	public JSONObject ExportJSONValue(TreeLeaves CurrentNode)
	{
		
		if(CurrentNode instanceof MaximizerNode)
		{
			MaximizerNode newMaximizer;
			newMaximizer = (MaximizerNode) CurrentNode;
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type", "max");
			jsonObject.put("value", newMaximizer.getValue());
			
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
			jsonObject.put("value", newMinimizer.getValue());
			
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
			
			return jsonObject;
		}
		
		return null;
	}
	
	@Override
	public String toString()
	{
		if(isMinMax)
		{
			return (ExportJSONValue(root).toString(2));
		}
		else
		{
			return (ExportJSON(root).toString(2));
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
		
	
	/* An arrayList to store the optimal path of  the tree after MinMax algorithm */
	ArrayList<Integer> optPath = new ArrayList<Integer>(0);		// size 0 and changes each time i add element
	
	public void optimalPathCall(TreeNode newNode)
	{	
			for (int i = 0; i < newNode.getChildrenSize(); i++)
			{
				if(newNode.ChildrenArray[i].getValue() == root.getValue())
				{
					optPath.add(i);
					/* it means that it is not a TreeNode type */
					if(!(newNode.ChildrenArray[i] instanceof MaximizerNode) && (!(newNode.ChildrenArray[i] instanceof MinimizerNode)))
					{
						return;
					}
					optimalPathCall((TreeNode)newNode.ChildrenArray[i]);
				}
			}	
	}
	
	
	public ArrayList<Integer> optimalPath()
	{
		optimalPathCall((TreeNode) root);
		
		return optPath;
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
        graphPrint.append("digraph TreeGraph {\n");		// The format of DOT File
        TreeString = buildGraphvizTree(root);
        graphPrint.append(TreeString);
        graphPrint.append("}\n");
		
		return graphPrint.toString();
	}
	
	
	/* MEthod that print DOT in the specified File */
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
	
	
	
	
	
}
