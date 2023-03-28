package ce326;

import java.io.*;
import java.util.*;

import org.json.*;

public class Tree 
{
	private TreeLeaves root;		// The root of the tree
	Tree newTree;
	JSONObject jsontry;
	
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
		System.out.println(root);
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
			//jsonObject.put("value=", newMaximizer.getValue());
			//jsonObject.put("value=", "kati");
			
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
			MinimizerNode newMinimizer;
			newMinimizer = (MinimizerNode) CurrentNode;
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type", "min");
			//System.out.println(CurrentNode.getValue());
			//jsonObject.put("value=", newMinimizer.getValue());
			
			JSONArray jsonarray = new JSONArray();
			
			for(int i = 0; i < newMinimizer.ChildrenArray.length; i++)
			{
				jsonarray.put(ExportJSON(newMinimizer.ChildrenArray[i]));
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
	
	/* Method that prints in a file the JSON format of the tree */
	void toFile(File file)
	{
		
	}
	
}
