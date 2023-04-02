package ce326.hw2;

import java.util.*;
import java.io.*;

public class MinMax 
{
	public static void main (String []args) throws TreeExceptions, IOException
	{
		Tree newTree = null;
		TreeAdvanced AdvancedTree = null;
		//for(String arg : args)
		//	System.out.println(arg);
		String input = "-o";
		boolean initialized_tree = false;
		boolean Tree_is_Advanced = false;
		boolean TreeSimple = false;
		ArrayList<Integer> optPath;
		
		
		Scanner sc = new Scanner(System.in);
//		String path = ( "C:\\Users\\jimar\\Desktop\\Uni\\Objective Programming\\hw2 git\\hw2-Objective-Oriented-Prog\\out.txt");
//		File myFile = new File (path);
//        PrintStream out = new PrintStream(myFile);
//        System.setOut(out);
		
		//while(!input.substring(0,2).matches("-q"))
//		System.out.println("\n-i <filename>   : insert tree from file\r\n"
//				+ "-j [<filename>] : print tree in the specified filename using JSON format\r\n"
//				+ "-d [<filename>] : print tree in the specified filename using DOT format\r\n"
//				+ "-c              : calculate tree using min-max algorithm\r\n"
//				+ "-p              : calculate tree using min-max and alpha-beta pruning optimization\r\n"
//				+ "-q              : quit this program\r\n\n"
//				+"$>");
		while(true)
		{			
			//System.out.println("-i <filename>  :  insert tree from file");
			//System.out.println("\n");
			System.out.println("\n-i <filename>   : insert tree from file\r\n"
					+ "-j [<filename>] : print tree in the specified filename using JSON format\r\n"
					+ "-d [<filename>] : print tree in the specified filename using DOT format\r\n"
					+ "-c              : calculate tree using min-max algorithm\r\n"
					+ "-p              : calculate tree using min-max and alpha-beta pruning optimization\r\n"
					+ "-q              : quit this program\r\n\n"
					+"$>");

			
			input = sc.nextLine();
			//System.out.println(input);
			
			//System.out.println(input);
		
			if(input.length() >= 2)
			{	
				switch(input.substring(0, 2))
				{
					case "-i":
					{
						String FilePath;
						FilePath = input.substring(3);
						
						File newFile = new File(FilePath);
						if(newFile.exists())
						{
							if(newFile.canRead())
							{	
								
//					            Scanner scanner = new Scanner(newFile);
//
//					            while (scanner.hasNextLine()) {
//					                String line = scanner.nextLine();
//					                System.out.println(line);
//					            }
								try
								{
									newTree = new Tree(newFile);
									AdvancedTree = new TreeAdvanced(newFile);	
									initialized_tree = true;
									System.out.println("OK");
								}
								catch (TreeExceptions ex2) 
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
							if(!newTree.onlyRoot)
							{								
								newTree.MinMaxImplementationCall();
								//System.out.println(newTree.optimalPath());
								optPath = newTree.optimalPath();
//						Integer[] array = new Integer[optPath.size()];
//						array = optPath.toArray();
								//System.out.println("\n");
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
								//AdvancedTree.CheckIfAllChildrenArePruned(AdvancedTree.super.returnRoot);
								AdvancedTree.checkPrunedCall(AdvancedTree.prunedNode);
								
								prunedCounter = (int) Math.round(AdvancedTree.prunedNode()); 
								
								System.out.print("[" + AdvancedTree.size() + "," + prunedCounter + "] ");
								
								AdvancedTree.isMinMax = true;
								AdvancedTree.isPruned = true;
								Tree_is_Advanced = true;
								
								optPath = AdvancedTree.optimalPath();
								//System.out.println("\n");
								for(Integer number : optPath.subList(0, optPath.size() - 1))
								{
									System.out.print(number + ", ");
								}
								Integer LastNumber = optPath.get(optPath.size()-1);
								System.out.print(LastNumber);
								System.out.print("\n\n");
							}
							else
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
					
//					case "-s":
//					{
//						System.out.println(newTree.size());
//						break;
//					}
					
					case "-j":
					{
						//System.out.println(newTree.ExportJSON(newTree.returnRoot()).toString(2));
						if(input.length() > 2)
						{
							File file;
							String FilePath;						
							FilePath = input.substring(3);
							file = new File(FilePath);
							
							try
							{
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
						
						else
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
					
//					case "-h":
//					{
//						System.out.println(newTree.ExportJSONValue(newTree.returnRoot()).toString());
//						break;
//					}
					
//					case "-o":
//					{
//						//System.out.println(newTree.ExportJSONValue(newTree.returnRoot()).toString());
//						System.out.println(newTree.optimalPath());
//						
//						break;
//					}
					
//					case "-t":
//					{
//	//        	        graphPrint.append("digraph TreeGraph {\n");
//	//        	        graphPrint.append(newTree.buildGraphvizTree(newTree.returnRoot()));
//	//        	        graphPrint.append("}\n");
//	//        	        System.out.println(graphPrint.toString());
//						System.out.println(newTree.toDOTString());
//						
//						break;
//					}
					
					case "-d":
					{
						//System.out.println(newTree.ExportJSON(newTree.returnRoot()).toString(2));
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
					
					
//					case "-n":
//					{
//						String FilePath;
//						FilePath = input.substring(3);
//						File newFile = new File(FilePath);
//						System.out.println(FilePath);
//						
//						double here;
//						
//						AdvancedTree = new TreeAdvanced(newFile);
//						here = AdvancedTree.MinMaxCall(AdvancedTree.returnRoot(), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
//						System.out.println("The value is: " + here);
//						AdvancedTree.isMinMax = true;
//						AdvancedTree.printarray();
//						AdvancedTree.checkPrunedCall(AdvancedTree.prunedNode);
//						
//						break;
//					}
					
//					case "-v":
//					{
//						//System.out.println(AdvancedTree.toDOTString());
//						System.out.println(AdvancedTree.toString());
//						break;
//					}
					
					case "-q":
					{
						return;
					}
					
					default:
					{
//						System.out.println("\n-i <filename>   : insert tree from file\r\n"
//								+ "-j [<filename>] : print tree in the specified filename using JSON format\r\n"
//								+ "-d [<filename>] : print tree in the specified filename using DOT format\r\n"
//								+ "-c              : calculate tree using min-max algorithm\r\n"
//								+ "-p              : calculate tree using min-max and alpha-beta pruning optimization\r\n"
//								+ "-q              : quit this program\r\n\n"
//								+"$>");
						//ystem.out.println("Invalid input");
					}
				}
			}
		}
				
		
	}
}

