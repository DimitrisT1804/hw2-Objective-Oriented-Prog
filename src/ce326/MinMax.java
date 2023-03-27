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
		String input = "";
		
		System.out.println("-i <filename>  :  insert tree from file");
		
		Scanner sc = new Scanner(System.in);
		
		while(true)
		{			
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
				
				default:
				{
					System.out.println("Invalid input");
				}
			}
	}
				
		
		//File myFile = new File(input);
		
		//Tree newTree = new Tree(myFile);	// methodos gia ton proto kataskevasti
		
		//Tree newTree = new Tree(myFile);
		
		
	}
}

