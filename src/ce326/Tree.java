package ce326;

import org.json.*;

public class Tree 
{
	private TreeNode root;		// The root of the tree
	Tree newTree;
	JSONObject jsontry;
	
	// Constructor that has input a String in JSON Format
	public Tree (String jsonString)
	{
		try		// kano dokimi ean eiani JSONString kai an lavo to diko mou exception ektipono antistoixa
		{
			Error(jsonString);		
		}
		catch(TreeExceptions ex)
		{
			System.out.println("Caught exception: " + ex.toString());	// mallon prepei na ektipono kai to exception
			//System.out.println("\u001B[31mCaught exception: " + ex.toString() + "\u001B[0m");
		}
		
		System.out.println("Ola komple");
		
	}
	
	
	
	public void Error(String jsonString) throws TreeExceptions	// elegxo ean einai JSONString kai an einai parago to diko mou exception
	{
		JSONObject jsontry;
		JSONArray jsonarray;
		
		TreeLeaves root;
		
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
	
	
	public void postorderTraversal(TreeLeaves node) {
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
}
