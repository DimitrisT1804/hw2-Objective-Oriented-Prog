package ce326;

import java.util.*;
import java.io.*;

public class MinMax 
{
	public static void main (String []args)
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
					break;
				}
				
				case "-s":
				{
					System.out.println(newTree.size());
					break;
				}
				
				case "-j":
				{
					System.out.println(newTree.ExportJSON(newTree.returnRoot()).toString());
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
					newTree.optimalPath(newTree.returnRoot());
					break;
				}
				
				default:
				{
					//ystem.out.println("Invalid input");
				}
			}
	}
				
		
		//File myFile = new File(input);
		
		//Tree newTree = new Tree(myFile);	// methodos gia ton proto kataskevasti
		
		//Tree newTree = new Tree(myFile);
		
		
	}
}

