/*
Java™ Project: ICS4U
Package: pathFinding
Class: Path3
Programmer: Shaan Banday

Date Created: Monday, May 2nd, 2022.
Date Completed: Wednesday, May 4th, 2022.

Description: Similar to Path2, the following program/class reads a text file to create a two dimensional array. However, this time, the array is 20 
rows by 30 columns and it is a maze. The contents of the array are characters: a '0' represents a valid path in the maze, whereas a 'x' represents a
boundary of the maze. The maze has multiple branches, and with this program, can move in all 4 directions (North, South, East and West). The goal of 
the program is to implement a recursive method to find a treasure marked by the character 'T' in the shortest number of steps. The program will go 
through every possible path to the treasure and only keep the shortest one. However, before it finds the treasure, the program must find the entrance
to the maze, which can be anywhere on the outside. There must be a condition, though, that there can only be 1 entrance to the maze.
*/

package pathFinding; //Launch the class from this package named "pathFinding"

//Import input and output elements
import java.io.FileReader; //Import the FileReader class to actually open the file
import java.io.BufferedReader; //Import the BufferedReader Class to read the text file line by line
import java.io.IOException; //Import the class to handle input output exceptions

public class Path3 
{
	//Declare all global variables which all methods have access to. They are all private since other classes do not need access to them
	private static String maze[]; //Array of Strings to hold each row as a whole line
	private static int rows, columns; //Integers to hold the number of rows and the number of columns the text file has
	private static int steps; //Integer to hold the number of steps taken in the shortest path so far
	private static int entranceX, entranceY; //Integers to hold the x and y-coordinates of the entrance
	private static int minimum; //Integer to hold the minimum steps before breaking a circular recursive loop
	
	public static void main(String[] args) throws IOException //The main method throws an IOException to read the file and acts like a constructor
	{
		//Initialise all variables 
		steps = 0; //Set the number of steps taken to 0
		rows = 1; //Set the number of rows to 1
		columns = 1; //Set the number of columns 
		
		//Call other methods to hold logic of the program
		readMaze("maze1.txt"); //Call the readMaze method to read the text file and take each row
		minimum = (rows) * (columns); //Set minimum to the number of rows multiplied by the number of columns
		
		//Find the entrance
		checkFirstRow(); //Call method to see if the entrance is in the first row
		
		//Decisions
		if (entranceX == columns) //If there is no entrance
		{
			//Output to console
			System.out.println("\nError. The text file does not have an entrance."); //Inform user of the error
		}
		
		else //Otherwise, if the entrance was found
		{
			//Output to the console
			System.out.println("\nThe entrance is at: (" + entranceX + ", " + entranceY + ")"); //Display to user where the entrance to the maze is
			
			pathFinder(entranceX, entranceY, 1, '~'); //Call the recursive method and pass it the coordinates of the entrance to start
			
			//Output to the console for the user, to show the final results of the shortest path to the treasure 'T'
			System.out.println("\nThe length of the path from the entrance to the treasure is: " + minimum + " steps"); //Show them the shortest path
		}
		
		
		//By this point, the program has been terminated
	}
	
	private static void readMaze(String fileName) throws IOException, NumberFormatException //Private method to hold logic for reading the file
	{
		//Method throws an IOException for a file not found error and NumberFormatException to handle parsing from strings to integers
		
		//Declare all objects
		BufferedReader takeInput = new BufferedReader(new FileReader(fileName));
		
		//Read the first two lines
		rows = Integer.parseInt(takeInput.readLine().trim()); //Read the first line, trim empty space, and parse to an int for number of rows
		columns = Integer.parseInt(takeInput.readLine().trim()); //Read the second line, trim empty space, and parse it an int for number of columns
		
		//Initialise the array
		maze = new String[rows]; //Set the size of the array to hold each row of the text file as a String
		
		//Print the maze to the console
		printMaze(takeInput); //Call the printMaze method
		
		takeInput.close(); //Close the BufferedReader, since the file reading is now done, and to prevent a resource leak
	}
	
	private static void printMaze(BufferedReader br) throws IOException //Private method to hold logic to print the grid
	{
		//Method also throws an IOException for a file not found error
		
		//Output to console
		System.out.println("Here is the Maze:\n"); //Prompt for the maze to the user
		
		//Loops
		for (int m = 0; m < rows; m++) //Start the loop at 0 and iterate through every row of the array
		{
			maze[m] = br.readLine(); //Read the next whole line and add to to the array
			
			//Output to the console
			System.out.println(maze[m]); //Display a whole row of the array
		}
	}
	
	private static void checkFirstRow() //Private method to check if the entrance is in the first row
	{
		//Declare and Initialise all variables
		boolean breakFirstLoop = false; //Boolean to break the loop if the entrance is found
		entranceX = columns; //Set the x-coordinate to the length of the row (i.e., the value of the variable columns)
		entranceY = 0; //Set the y-coordinate to 0, since every element in the first row has a y-coordinate of 0
		
		//Loops
		for (int n = 0; (n < columns) && (!breakFirstLoop); n++) //Start at 0 and iterate through every character of the first row as a whole string
		{
			//Decisions
			if (maze[0].charAt(n) == '0') //If the character in the first row at the specified index of n is '0'
			{
				entranceX = n; //Record this x-coordinate
				breakFirstLoop = true; //Break the loop
			}
		}
		
		//Decisions
		if (entranceX == columns) //entranceX was previously initialised to the value of columns. Run if they are still equal
		{
			//If entranceX is still the value of columns, then it the entrance was NOT found in the first row
			checkMiddleRows(); //Call method to see if the entrance is at either end of the first or second row
		}
	}
	
	private static void checkMiddleRows () //Private method to check if the entrance is at the start or end of any of the middle rows
	{
		//Declare and Initialise all variables
		boolean breakMiddleLoop = false; //Boolean to break the loop if the entrance is found
		//If this method is run, the x-coordinate is still equal to the value of the length of the row (i.e., the value of the variable columns)
		entranceY = 1; //Set the y-coordinate to 1, since it is not in the first row and the y-coordinate can now NOT be 0
		
		//Loops
		for (int p = 1; (p < (rows - 1)) && (!breakMiddleLoop); p++) //Start at 0 and iterate through each row as a whole string
		{
			//Decisions
			if (maze[p].charAt(0) == '0') //If the first character of the row at index p is '0'
			{
				entranceX = 0; //Record this x-coordinate
				entranceY = p; //Record this y-coordinate
				breakMiddleLoop = true; //Break the loop
			}
			
			else if (maze[p].charAt(columns - 1) == '0') //Otherwise, If the last at index 0 of the row at index p is '0'
			{
				entranceX = columns - 1; //Record this x-coordinate
				entranceY = p; //Record this y-coordinate
				breakMiddleLoop = true; //Break the loop
			}
		}
		
		//Decisions
		if (entranceX == columns) //entranceX was originally initialised to the value of columns. Run if they are still equal
		{
			//If entranceX is still the value of columns, then it means the entrance was NOT found in the first row OR any of the middle ones
			checkLastRow(); //Call method to see if the entrance is at either end of the first or second row
		}
	}
	
	private static void checkLastRow() //Call method to see if the entrance is at either end of the first or second row
	{
		//Declare and Initialise all variables
		boolean breakLastLoop = false; //Boolean to break the loop if the entrance is found
		//If this method is run, the x-coordinate is still equal to the value of the length of the row (i.e., the value of the variable columns)
		entranceY = rows - 1; //Set the y-coordinate to the last value in the array, since it is not in any of the previous rows
		
		//Loops
		for (int q = 0; (q < columns) && (!breakLastLoop); q++) //Start at 0 and iterate through every character of the last row as a whole string
		{
			//Decisions
			if (maze[rows - 1].charAt(q) == '0') //If the character in the last row at the specified index of n is '0'
			{
				entranceX = q; //Record this x-coordinate
				breakLastLoop = true; //Break the loop
			}
		}
		
		//By this point, if the entrance has not been found, then there is no entrance at any of the boundaries
	}
	
	private static void pathFinder(int x, int y, int pathSoFar, char previousDirection) //Recursive method which finds the shortest path to treasure
	{
		/*For the variable previousDirection, it is passed to itself to prevent the program from zigzagging and causing a StackOverflow Error. It is 
		  a character and is either 'N' for North, 'S' for South, 'E' for East, and 'W' for West. A move will not happen if the path just came from
		  that direction. For example, a move will not happen East, if the previous direction passed was 'W' and vice versa.*/
		
		//Decisions
		if (pathSoFar < minimum) //So long as the path so far is not longer than the current minimum, which is to prevent going in circles
		{
			steps = pathSoFar; //Set steps to the value of pathSoFar
			
			//Nested Decisions
			if (maze[y].charAt(x) == '0') //If the character at the specified coordinate is '0', which means a valid path
			{
				
				//Double Nested Decision to check if moving East is valid
				if ((x + 1 < columns) && (previousDirection != 'W')) //If next x-position is not out of bounds and if previousDirection is not West
				{
					pathFinder(x + 1, y, pathSoFar + 1, 'E');  //Increase the x-coordinate by 1 and move East
				}
				
				//Separate Double Nested Decision to check if moving West is valid
				if ((x - 1 >= 0) && (previousDirection != 'E')) //If next x-position is not out of bounds and if previousDirection is not East
				{
					pathFinder(x - 1, y, pathSoFar + 1, 'W'); //Decrease the x-coordinate by 1 and move West
				}
				
				//Separate Double Nested Decision to check if moving South is valid
				if ((y + 1 < rows) && (previousDirection != 'N')) //If next y-position is not out of bounds and if previousDirection is not North
				{
					pathFinder(x, y + 1, pathSoFar + 1, 'S');  //Increase the x-coordinate by 1 and move South
				}
				
				//Separate Double Nested Decision to check if moving North is valid
				if ((y - 1 >= 0) && (previousDirection != 'S')) //If next x-position is not out of bounds and if previousDirection is not South
				{
					pathFinder(x, y - 1, pathSoFar + 1, 'N'); //Decrease the x-coordinate by 1 and move North
				}
			}
			
			else if ((maze[y].charAt(x) == 'T')) //Otherwise, if the character at the specified coordinate is 'T', which means the treasure is found
			{
				//Double Nested Decision to check if moving the current path is shortest so far
				if (steps < minimum) //If the current number of steps is less than the minimum number of steps from any previous path
				{
					minimum = steps; //Update minimum to reflect this new value
				}
			}
		}
	}
}