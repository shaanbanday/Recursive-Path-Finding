/*
Java™ Project: ICS4U
Package: pathFinding
Class: Path2
Programmer: Shaan Banday

Date Created: Thursday, April 28th, 2022.
Date Completed: Friday, April 29th, 2022.

Description: Similar to Path1, the following program/class interacts with a two-dimensional array of integers taken from a text file which each 
represent a location. The value of each integer represents the toll the user has to pay at that location. The goal is the same as the previous 
program, in that it is to start in the top left corner (north-west) and make it to the bottom right corner (south-east). However, this time, the 
program can also move diagonally, but with the caveat of having to pay double the toll if they go "cross country". The goal of the program is to 
implement a recursive method which finds the cheapest path from the top left (north-west) to the bottom right (south-east) by going through every 
possible path and only keeping the least expensive total price. However, with this program also keeps track of the path (i.e., each toll it touches) 
it takes to get to the bottom-right corner of the array with the least amount of money.
*/

package pathFinding; //Launch the class from this package named "pathFinding"

//Import input and output elements
import java.io.FileReader; //Import the FileReader class to actually open the file
import java.io.BufferedReader; //Import the BufferedReader Class to read the text file line by line
import java.io.IOException; //Import the class to handle input output exceptions
import java.util.StringTokenizer; //Import the StringTokenizer class to break a string into tokens, making it easier to print back to the console

public class Path2 //Name of class
{
	//Declare all global variables which all methods have access to. They are all private since other classes do not need access to them
	private static int grid[][]; //Two Dimensional integer array with to hold the integers which represent the tolls at each stop
	private static int gridX = 0; //Integer to represent the current x-position within the two dimensional array
	private static int gridY = 0; //Integer to represent the current y-position within the two dimensional array
	private static int cheapest; //Integer which updates to hold the cost of cheapest path found so far
	private static String path; //String to hold the list of integers which represent the path itself

	public static void main(String[] args) throws IOException //The main method throws an IOException to read the file
	{
		//Output to the console for the user, to introduce them to the program
		System.out.println("The file with the rectangular maze must contain the width of the grid (gridX) on the first line and the "
				+ "height of the grid (gridY) on the second line.\n\n"); //Inform user of the specifications for the text file
		
		//Call other methods to hold logic of the program
		readGrid("cheap1.txt"); //Call the readGrid method to read the text file and take each of the elements
		
		cheapest = 100 * gridX + 100 * gridY; //Set the value of cost for the cheapest path to a very big number for now
		bestPath(0, 0, grid[0][0], "" + grid[0][0]); //Call the bestPath method to find the best path and pass it coordinates of (0,0) to start
		
		//Output to the console for the user, to show them the final results of the cheapest cost and the path to get to the end
		System.out.println("\nThe cheapest path will cost: $" + cheapest + "\nThrough:  " + path); //Show them the cheapest path
		
		//By this point, the program has been terminated
	}
	
	private static void readGrid(String fileName) throws IOException, NumberFormatException //Private method to hold logic for reading the file
	{
		//Method throws an IOException for a file not found error and NumberFormatException to handle parsing from strings to integers
		
		//Declare all objects
		BufferedReader takeInput = new BufferedReader(new FileReader(fileName)); //The object to read the opened file passed to the method
		
		//Read the first two lines
		gridX = Integer.parseInt(takeInput.readLine().trim()); //Read the first line, trim empty space, and parse to an int for the width
		gridY = Integer.parseInt(takeInput.readLine().trim()); //Read the second line, trim empty space, and parse it an int for the length
		
		//Initialise the array
		grid = new int[gridX][gridY]; //Set the size of the array the width and height from the first two lines in the text file
		
		//Output to the console
		System.out.println("Here is the maze:\n"); //Prompt to show the array, with a empty line between
		
		//Print the grid to the console
		printGrid(takeInput); //Call the printGrid method
		
		takeInput.close(); //Close the BufferedReader, since the file reading is now done, and to prevent a resource leak
	}
	
	private static void printGrid(BufferedReader br) throws IOException, NumberFormatException //Private method to hold logic to print the grid
	{
		//Method also throws an IOException for a file not found error and NumberFormatException to handle parsing from strings to integers
		
		//Loops
		for (int k = 0; k < gridY; k++) //Start the loop at 0 and iterate through every row of the array
		{
			//Declare all variables
			String line = br.readLine(); //Set the string line to the next full line the BufferedReader scans
			StringTokenizer list = new StringTokenizer(line); //Declare a StringTokenizer to break the String line
			
			//Output to the console
			System.out.println(line); //Display a whole row of the array
			
			//Nested Loops
			for (int l = 0; l < gridX; l++) //Start the loop at 0 and iterate through the width of the array
			{
				grid[l][k] = Integer.parseInt(list.nextToken().trim()); //At the specified index, parse string to an int and add it to the array
			}
		}
	}
	
	private static void bestPath(int x, int y, int cost, String p) //Private recursive method which finds the cheapest path to the bottom-right
	{
		//Decisions to check for moving South-East
		if ((x < (gridX - 1)) && (y < (gridY - 1))) //If the current position is not in the last column or last row
		{
			//Declare variables
			int crossCountryToll = (2*(grid[x + 1][y + 1])); //Cost to go diagonally takes the toll at that point and doubles it
			
			bestPath(x + 1, y + 1, 
					cost + crossCountryToll, p + " " + crossCountryToll/2); //Increase the x and y-coordinates by 1 and move South-East
		}
		
		//Separate Decisions to check for moving East
		if (x < (gridX - 1)) //If the current position is not in the last column
		{
			//Declare variables
			int eastToll = grid[x + 1][y]; //Cost to go East takes the toll at that point
			
			bestPath(x + 1, y, cost + eastToll, p + " " + eastToll); //Increase the x-coordinate by 1 and move East
		}
		
		//Separate Decisions to check for moving East
		if (y < (gridY - 1)) //If the current position is not in the last row
		{
			//Declare variables
			int southToll = grid[x][y + 1];
			
			bestPath(x, y + 1, cost + southToll, p + " " + southToll); //Increase the y-coordinate by 1 and move South
		}
		
		//Final Check
		if (((x + y) == (gridX + gridY - 2)) 
				&& (cost < cheapest)) //If the last position is reached and the running total for the current cost is less than the cheapest
		{
			cheapest = cost; //Set the cheapest path to the new, lesser value from this path
			path = p; //Record the path it took to get there
		}
	}
}