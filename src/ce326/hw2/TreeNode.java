package ce326.hw2;

public class TreeNode extends TreeLeaves
{
		TreeLeaves[] ChildrenArray;		// TreeLeaves Array that stores Children of each node
		double alpha = Double.NEGATIVE_INFINITY;		// -infinite
		double beta = Double.POSITIVE_INFINITY;		// +infinite
		
		// Default Constructor
		public TreeNode()
		{
			
		}
		// Constructor with arguments
		public TreeNode(TreeLeaves[] ChildrenArray)
		{
			this.ChildrenArray = ChildrenArray;
		}
		
		/* Initialize the size of the array with children Nodes */
		public void setChildrenSize(int size)
		{
			ChildrenArray = new TreeLeaves[size];
		}
		
		
		public int getChildrenSize()
		{
			return ChildrenArray.length;
		}
		
		// add node X in the position pos of the array
		void insertChild(int pos, TreeLeaves children)
		{
			ChildrenArray[pos] = children;
		}
		
		// Return TreeNode of position pos
		TreeLeaves getChild(int pos)
		{
			return ChildrenArray[pos];
		}
}
