package com.technion.oop.homework.zero.commentreader;
import java.lang.String;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;


/**
 * @author Arik Rinberg
 * @author Barak Zan
 *
 * This class will print out all the comments of a java file passed a command line parameter.
 * Legal comments are:
 *  1. Single line comments (//)
 *  2. Multi line comments (/* ... *\/)
 * When printing the comments we remove the comment markings.
 * For instance the comment /**\/ will just print an empty marking
 */

public class CommentReader
{
	final static String SingleLineComment = "//";
	final static String MultiLineCommentOpen = "/*";
	final static String MultiLineCommentClose = "*/";
	
	/**
	 * @param line Current line to handle
	 * @param partOfMultiLine True if part of a multi line
	 * @param printed true if we've printed somewhere for this line
	 * @effects Prints out all the comments in a line
	 * @return True if a multi line comment was opened without being closed
	 */
	public static boolean HandleLine(final String line, boolean partOfMultiLine, boolean printed)
	{
		if (line.length() == 0)
		{
			// We've reached the end of the line, if we've printed add a newline.
			if (printed)
			{
				System.out.println();
			}
			return partOfMultiLine;
		}

		if (partOfMultiLine)
		{
			// If current line is part of a multi line comment
			int indexOfCommentClose = line.indexOf(MultiLineCommentClose);
			
			if (indexOfCommentClose == -1)
			{
				// Comment wasn't closed this line, print it and return true
				System.out.println(line);
				return true;
			}
			else
			{
				// The comment was closed, print it and handle the rest of the line
				System.out.print(line.substring(0, indexOfCommentClose));
				return HandleLine(line.substring(indexOfCommentClose+2), false, true);
			}			
		}
		
		int indexOfComment = line.indexOf(SingleLineComment);
		int indexOfCommentOpen = line.indexOf(MultiLineCommentOpen);
		
		if (indexOfComment == -1 && indexOfCommentOpen == -1)
		{
			// No comments were found, if we've printed then add a new line 
			if (printed)
			{
				System.out.println();
			}
			return false;
		}
		
		if (indexOfComment != -1)
		{
			// We've found a regular line comment (//), check if a multi line was opened before it
			if (indexOfCommentOpen == -1 || indexOfComment < indexOfCommentOpen)
			{
				// The comment is before the multi line open, so ignore the open
				System.out.println(line.substring(indexOfComment+2));
				return false;
			}
		}
		
		// Here we know that there is no single line comment but we have a multi line.
		//  We'll handle the rest of the line in a recursive call.
		return HandleLine(line.substring(indexOfCommentOpen+2), true, true); 
	}
	
	/**
	 * @param args desc
	 * @effects Searches for file that is passed as command line parameter, and prints to 
	 * screen if the file was not found.
	 */
	public static void main(String[] args)
	{
		if (args.length < 1)
		{
			System.out.println("No file passed as parameter");
			return;
		}
		
		String fileName = args[0];

		File inputFile = new File(fileName);

		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			String line;
			boolean multiLineComment = false;
			while ((line = br.readLine()) != null)
			{
				multiLineComment = HandleLine(line, multiLineComment, false);
			}
			br.close();
		}
		catch (Exception e)
		{
			System.out.println("File not found");
		} 
	}
}
