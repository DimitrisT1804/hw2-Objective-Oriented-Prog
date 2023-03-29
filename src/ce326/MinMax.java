package ce326;

import java.util.*;
import java.io.*;

public class MinMax 
{
	public static void main (String []args) throws TreeExceptions, IOException
	{
		Tree newTree = null;
		//for(String arg : args)
		//	System.out.println(arg);
		String input = "-o";
		
		
		Scanner sc = new Scanner(System.in);
		
		while(!input.substring(0,2).matches("-q"))
		{			
			System.out.println("-i <filename>  :  insert tree from file");
			input = sc.nextLine();
			System.out.println(input);
		
			
			
			
		
			switch(input.substring(0, 2))
			{
				case "-i":
				{
					String FilePath;
					FilePath = input.substring(3);
					File newFile = new File(FilePath);
					System.out.println(FilePath);
					
					newTree = new Tree(newFile);
					break;
				}
				
				case "-c":
				{
					newTree.MinMaxImplementationCall();
					newTree.isMinMax = true;
					break;
				}
				
				case "-s":
				{
					System.out.println(newTree.size());
					break;
				}
				
				case "-j":
				{
					//System.out.println(newTree.ExportJSON(newTree.returnRoot()).toString(2));
					if(input.length() > 2)
					{
						File file;
						String FilePath;						
						FilePath = input.substring(3);
						file = new File(FilePath);
						newTree.toFile(file);
					}
					
					else
					{						
						System.out.println(newTree.toString());
					}
					break;
				}
				
				case "-h":
				{
					System.out.println(newTree.ExportJSONValue(newTree.returnRoot()).toString());
					break;
				}
				
				case "-o":
				{
					//System.out.println(newTree.ExportJSONValue(newTree.returnRoot()).toString());
					System.out.println(newTree.optimalPath());
					
					break;
				}
				
				case "-t":
				{
//        	        graphPrint.append("digraph TreeGraph {\n");
//        	        graphPrint.append(newTree.buildGraphvizTree(newTree.returnRoot()));
//        	        graphPrint.append("}\n");
//        	        System.out.println(graphPrint.toString());
					System.out.println(newTree.toDOTString());
					
					break;
				}
				
				case "-d":
				{
					//System.out.println(newTree.ExportJSON(newTree.returnRoot()).toString(2));
					if(input.length() > 2)
					{
						File file;
						String FilePath;						
						FilePath = input.substring(3);
						file = new File(FilePath);
						newTree.toDotFile(file);
					}
					
					else
					{						
						System.out.println(newTree.toDOTString());
					}
					break;
				}
				
				default:
				{
					//ystem.out.println("Invalid input");
				}
			}
	}
				
		
	}
}

