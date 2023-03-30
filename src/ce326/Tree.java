package ce326;

import java.io.*;
import java.util.*;

import org.json.*;

public class Tree 
{
	private TreeLeaves root;		// The root of the tree
	Tree newTree;
	JSONObject jsontry;
	boolean isMinMax = false;
	boolean isPruned = false;
	
	
	// Constructor that has input a String in JSON Format
//	public Tree (String jsonString)
//	{
//		try		// kano dokimi ean eiani JSONString kai an lavo to diko mou exception ektipono antistoixa
//		{
//			Error(jsonString);		
//		}
//		catch(TreeExceptions ex)
//		{
//			System.out.println("Caught exception: " + ex.toString());	// mallon prepei na ektipono kai to exception
//			//System.out.println("\u001B[31mCaught exception: " + ex.toString() + "\u001B[0m");
//		}
//		
//		System.out.println("Ola komple");
//		
//	}
	
	public Tree (File JSONFile)
	{
		String JSONstring;
	    StringBuilder strBuilder = new StringBuilder();
	    try(Scanner sc = new Scanner(JSONFile)) 
	    {
	      while(sc.hasNextLine() ) {
	        String str = sc.nextLine();
	        strBuilder.append(str);
	        strBuilder.append("\n");
	      }
	    } 
	    catch(IOException ex) 
	    {
	      ex.printStackTrace();
	    }
	    JSONstring = strBuilder.toString();
	    
	    try
	    {	    	
	    	Error(JSONstring);
	    }
	    
		catch(TreeExceptions ex)
		{
			System.out.println("Caught exception: " + ex.toString());	// mallon prepei na ektipono kai to exception
			//System.out.println("\u001B[31mCaught exception: " + ex.toString() + "\u001B[0m");
		}
		
	}
	
	
	
	public void Error(String jsonString) throws TreeExceptions	// elegxo ean einai JSONString kai an einai parago to diko mou exception
	{
		JSONObject jsontry;
		JSONArray jsonarray;
		
		//TreeLeaves root;		// den prepei na einai topiko to root, alla pedio sto class
		
		try
		{
			jsontry =  new JSONObject(jsonString);
		}
		catch(JSONException isJSON)
		{
			//System.out.println("It is not JSON");
			//return;
			throw new TreeExceptions("java.util.IllegalArgumentException");
		}
		
		root = CreateMinMaxTree(jsontry);
		//System.out.println(root);
		postorderTraversal(root);
		//root = MinMaxImplementation(root);
		//postorderTraversalValues(root);
	}
	
	public TreeLeaves CreateMinMaxTree(JSONObject jsontry)
	{
		JSONArray jsonarray;
		
		if(jsontry == null)
		{
			return null;
		}
		
		String kati = jsontry.getString("type");
		if(kati.matches("max"))
		{
			MaximizerNode newNode = new MaximizerNode();
			jsonarray = jsontry.getJSONArray("children");
			newNode.setChildrenSize(jsonarray.length());
			
			for(int i = 0; i < jsonarray.length(); i++)
			{
				JSONObject children = jsonarray.getJSONObject(i);
				newNode.insertChildren(i, CreateMinMaxTree(children));
			}
			return newNode;
		}
		
		else if(kati.matches("min"))
		{
			MinimizerNode newNode = new MinimizerNode();
			jsonarray = jsontry.getJSONArray("children");
			newNode.setChildrenSize(jsonarray.length());
			
			for(int i = 0; i < jsonarray.length(); i++)
			{		
				JSONObject children = jsonarray.getJSONObject(i);
				newNode.insertChildren(i, CreateMinMaxTree(children));

			}
			
			return newNode;
		}
		else if (kati.matches("leaf"))
		{
			TreeLeaves newNode = new TreeLeaves(jsontry.getDouble("value"));
			return newNode;
		}
		else
		{			
			return null;
		}
		
	}
	
	
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
	        TreeLeaves leavesNode = (TreeLeaves) node;
	        System.out.print(leavesNode.getValue() + " ");
	    }
	}
	
	public TreeLeaves MinMaxImplementation(TreeLeaves root)
	{
		//TreeLeaves node = null;
		if (root instanceof MaximizerNode)
		{
			MaximizerNode newNode;
			newNode = (MaximizerNode) root;
			for(int i = 0; i < newNode.ChildrenArray.length; i++)
			{
				MinMaxImplementation(newNode.ChildrenArray[i]);
			}
			//newNode = (MaximizerNode) root;
			newNode.SetValue(newNode.getMax());
			System.out.println("Value is: "+newNode.getValue());
			return newNode;
		}
		
		else if (root instanceof MinimizerNode)
		{
			MinimizerNode newNode;
			newNode = (MinimizerNode) root;
			for(int i = 0; i < newNode.ChildrenArray.length; i++)
			{
				MinMaxImplementation(newNode.ChildrenArray[i]);
			}
			//newNode = (MaximizerNode) root;
			newNode.SetValue(newNode.getMin());
			//System.out.println("Value is: "+newNode.getValue());
			return newNode;
		}
		
		else if(root instanceof TreeLeaves)
		{
			return root;
		}
		return null;
	}
	
	
	// mallon den xreiazete kapos allios prepei na to kalo kai mallon prepei na kano initialize to root
	public void MinMaxImplementationCall()
	{
		root = MinMaxImplementation(root);
		postorderTraversalValues(root);
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
	int size = 1;
	public int sizeImplementation(TreeLeaves newNode)
	{
		//TreeNode newNode = (TreeNode) root;
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
	
	public TreeNode returnRoot()
	{
		return (TreeNode) root;
	}
	
	/* Method that returns the JSON format of this tree */
//	@Override
//	public String toString()
//	{
//		
//	}
	
	//StringBuilder json = new StringBuilder();
	public JSONObject ExportJSON(TreeLeaves CurrentNode)
	{
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
			jsonObject.put("children", jsonarray);
			
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
			jsonObject.put("value", CurrentNode.getValue());
			
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
	
	
	ArrayList<Integer> optPath = new ArrayList<Integer>(0);		// to ftiaxno me 0 gia na exei akrivos idio mikos me ta elements
	
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
						//System.out.println(optPath);
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
        	   //graph.append("\"];\n");
           }
           
           else
           {
        	   //newNode = (TreeNode) node;
        	   //graph.append("max");
        	   graph.append(node.getValue());
        	   graph.append("\"];\n");
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
        TreeString = buildGraphvizTree(root);
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
	
	
	
	
	
}
