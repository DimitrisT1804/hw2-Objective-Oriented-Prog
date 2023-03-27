package ce326;

import java.util.*;
import java.io.*;

public class MinMax 
{
	public static void main (String []args)
	{
		//for(String arg : args)
		//	System.out.println(arg);
		String input = "";
		
		Scanner sc = new Scanner(System.in);
		
		//while(sc.hasNextLine())
		//{			
			input = sc.nextLine();
			System.out.println(input);
		//}
		File myFile = new File(input);
		
		//Tree newTree = new Tree(myFile);
		
		Tree newTree = new Tree(myFile);
		
		
	}
}

