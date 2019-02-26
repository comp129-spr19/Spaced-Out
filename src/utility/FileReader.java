package utility;
import java.io.*;
import java.util.Scanner;
public class FileReader {
	
	// reads the text from the whole file
	public static String readWholeFile(String filename) {
		 
		 
		 
		try {
			Scanner scanner = new Scanner(new File(filename));
			 //scanner.useDelimiter("\\Z");
			  String returnString = "";
			  while (scanner.hasNextLine()) {
				  returnString += scanner.nextLine();
				  returnString += System.lineSeparator();
			  }
			 
			  return returnString;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		  
		 
		 }
	
	/*
	public static void main(String[] args) {
		String a = readWholeFile("OBJECTIVE_ONE.txt");
		
		System.out.println(a);
	} */
		
}
