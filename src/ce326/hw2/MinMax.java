package ce326.hw2;

import java.util.*;
import java.io.*;

public class MinMax 
{
	public static void main (String []args) throws TreeExceptions, IOException
	{
		Tree newTree = null;
		TreeAdvanced AdvancedTree = null;
		String input = "-o";
		boolean initialized_tree = false;		// Check if the tree created
		boolean Tree_is_Advanced = false;		// Check if the tree is Advance (Pruning)
		boolean TreeSimple = false;				// Check if it is simple Tree
		ArrayList<Integer> optPath;
		
		
		Scanner sc = new Scanner(System.in);	// input from user
		
		while(true)
		{	
			/* Printing the menu of the program */
			System.out.println("\n-i <filename>   :  insert tree from file\n"
					+ "-j [<filename>] :  print tree in the specified filename using JSON format\n"
					+ "-d [<filename>] :  print tree in the specified filename using DOT format\n"
					+ "-c              :  calculate tree using min-max algorithm\n"
					+ "-p              :  calculate tree using min-max and alpha-beta pruning optimization\n"
					+ "-q              :  quit this program\n"
					+ "\n$> ");
			
			input = sc.nextLine();
		
			if(input.length() >= 2)		// All options are -x, so the input must be at least two characters
			{	
				switch(input.substring(0, 2))
				{
					case "-i":
					{
						String FilePath;
						FilePath = input.substring(3);
						
						File newFile = new File(FilePath);	// Initializing file with the FilePath
						if(newFile.exists())
						{
							if(newFile.canRead())
							{	
								try		// Try to create both Trees and handle any Exceptions
								{
									newTree = new Tree(newFile);
									AdvancedTree = new TreeAdvanced(newFile);	
									initialized_tree = true;
									System.out.println("OK");
								}
								catch (TreeExceptions ex2) 		// No JSON file exceptions
								{
									System.out.println("Invalid Format\n");
								}
							}
							else
							{
								System.out.println("Unable to open " + "'" + FilePath + "'\n");
							}
						}
						else
						{
							System.out.println("Unable to find " + "'" + FilePath + "'\n");
						}
						
						
						break;
					}
					
					case "-c":
					{
						if(initialized_tree)
						{	
							if(!newTree.onlyRoot)		// Checks if the Tree only has root
							{								
								newTree.minMax();
								optPath = newTree.optimalPath();
								
								/* Printing numbers of optimal Path except of the last one */
								for(Integer number : optPath.subList(0, optPath.size() - 1))
								{
									System.out.print(number + ", ");
								}
								
								Integer LastNumber = optPath.get(optPath.size()-1);
								System.out.print(LastNumber);
								System.out.print("\n\n");
								
								newTree.isMinMax = true;
								TreeSimple = true;
							}
						}
						else
						{
							System.out.println("Not OK\n");
						}
						break;
					}
					
					case "-p":
					{
						int prunedCounter;
						if(initialized_tree)
						{							
							if(!AdvancedTree.onlyRoot)
							{		
								AdvancedTree.MinMax();
								AdvancedTree.checkPrunedCall(AdvancedTree.prunedNode);
								
								prunedCounter = (int) Math.round(AdvancedTree.prunedNodes()); 	// Typecast the double 
								
								System.out.print("[" + AdvancedTree.size() + "," + prunedCounter + "] ");
								
								AdvancedTree.isMinMax = true;
								AdvancedTree.isPruned = true;
								Tree_is_Advanced = true;
								
								optPath = AdvancedTree.optimalPath();		// Using the method from Tree
								for(Integer number : optPath.subList(0, optPath.size() - 1))
								{
									System.out.print(number + ", ");
								}
								Integer LastNumber = optPath.get(optPath.size()-1);
								System.out.print(LastNumber);
								System.out.print("\n\n");
							}
							else		// if the Tree has only root
							{
								System.out.print("[1,0] \n\n");
							}
						}
						else
						{
							System.out.println("Not OK\n");
						}
						
						
						break;
						
					}
					
					case "-j":
					{
						if(input.length() > 2)
						{
							File file;
							String FilePath;						
							FilePath = input.substring(3);
							file = new File(FilePath);
							
							try
							{	
								/* Checks if it is SimpleTree or Advanced version (pruning) to export the correct json */
								if(TreeSimple)				
									newTree.toFile(file);
								else if (Tree_is_Advanced)
									AdvancedTree.toFile(file);	
								else
								{
									newTree.toFile(file);
								}
								System.out.println("OK\n");
							}
							catch(FileNotFoundException ex2)
							{
								System.out.println("Unable to write '" + FilePath + "'\n" );
							}
							catch(TreeExceptions ex3)
							{
								System.out.println("File '" + FilePath + "' already exists\n" );
							}
							catch(IOException ex4)
							{
								System.out.println("Unable to write '" + FilePath + "'\n" );
							}
							
						}
						
						else		// If there is no path, it prints it in the STDOUT 
						{		
							if(TreeSimple)
								System.out.println(newTree.toString());
							else if(Tree_is_Advanced)
								System.out.println(AdvancedTree.toString());
							else
							{
								System.out.println(newTree.toString());
							}
							
						}
						break;
					}
					
					case "-d":
					{
						if(input.length() > 2)
						{
							File file;
							String FilePath;						
							FilePath = input.substring(3);
							file = new File(FilePath);
							try
							{
								
								if(TreeSimple)
									newTree.toDotFile(file);
								else if(Tree_is_Advanced)
									AdvancedTree.toDotFile(file);
								else
								{
									newTree.toDotFile(file);
								}
								System.out.println("OK\n");
							}
							catch(FileNotFoundException ex2)
							{
								System.out.println("Unable to write '" + FilePath + "'\n" );
							}
							catch(TreeExceptions ex3)
							{
								System.out.println("File '" + FilePath + "' already exists\n" );
							}
							
						}
						
						else
						{			
							if(TreeSimple)
								System.out.println(newTree.toDOTString());
							else if(Tree_is_Advanced)
								System.out.println(AdvancedTree.toDOTString());
							else
								System.out.println(newTree.toDOTString());
							
							
						}
						break;
					}
					
					case "-q":
					{	/* Terminating the program */
						sc.close();
						return;
					}
					
					default:
					{
						
					}
				}
			}
		}		
	}
}

