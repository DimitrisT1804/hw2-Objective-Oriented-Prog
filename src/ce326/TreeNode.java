package ce326;

public class TreeNode extends TreeLeaves
{
		TreeLeaves[] ChildrenArray;		// isos thelei allo tipo apo pinaka
		double AlphaValue = java.lang.Double.MIN_VALUE;		// -infinite
		double BetaValue= java.lang.Double.MAX_VALUE;		// +infinite
		
		// Default Constructor
		public TreeNode()
		{
			
		}
		// Constructor with arguments
		public TreeNode(TreeLeaves[] ChildrenArray)
		{
			//super(ChildrenArray[0]);
			//this.ChildrenArray = new TreeLeaves[ChildrenArray.length];
			//System.arraycopy(ChildrenArray, 0, this.ChildrenArray, 0, ChildrenArray.length);
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
		void insertChildren(int pos, TreeLeaves children)
		{
			ChildrenArray[pos] = children;
		}
		
		// Return TreeNode of position pos
		TreeLeaves getChild(int pos)
		{
			return ChildrenArray[pos];
		}
}
