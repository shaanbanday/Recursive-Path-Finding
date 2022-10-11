/*
Java™ Project: ICS4U
Package: pathFinding
Class: Path1
Programmer: Shaan Banday

Date Created: Tuesday, April 26th, 2022.
Date Completed: Wednesday, April 27th, 2022.

Description: The following program/class interacts with a two-dimensional array of integers which each represent a location. The value of each
integer represents the toll the user has to pay at that location. The goal is to start in the top left corner (north-west) and make it to the
bottom right corner (south-east). One can move east (right) and south (down), but not backwards. The goal of the program is to implement a 
recursive method which finds the cheapest path from the top left (north-west) to the bottom right (south-east) by going through every possible
path and only keeping the least expensive price.
*/

package pathFinding; //Launch the class from this package named "pathFinding"

public class Path1 //Name of class
{
	//Declare all global variables which all methods have access to. They are all private since other classes do not need access to them
	private static int m [][] = { //4 x 4 array to hold the integers which represent the tolls at each stop
			{12, 10, 33, 21},  //First row of the array
			{15, 20, 14, 24},  //Second row
			{18, 15, 52, 5},   //Third row
			{22, 21, 30, 41}}; //Fourth row
	
	private static int cheapest; //Integer which updates to hold the cost of the cheapest path found so far
	
	public static void main(String[] args) //The main method runs first but is relatively small, and calls other methods to do all the work
	{
		//Call other methods to hold logic of the program
		printArray(); //Call a print command method to display the entire multidimensional array on the console
		
		cheapest = 10000; //Set the value of the cheapest path to a very big number for now
		findMin (0, 0, 0); //Call the recursive method and pass it the starting position of (0, 0) and the initial sum of $0
		
		//Output to console for the user, to show them the final results of the cheapest cost
		System.out.println("The cheapest path will cost: $" + cheapest); //After cheapest path is found, print the cost to the console
		
		//By this point, the program has been terminated
	}
	
	private static void printArray() //Private printing command method which hold all the logic to display the array on the screen
	{
		//Loops
		for (int i = 0 ; i < 4 ; i++) //Start the loop at zero and iterate through each row of the array
		{
			//Nested Loops
			for (int j = 0 ; j < 4 ; j++) //Start the loop at zero and iterate through each column of the array
			{
				//Decisions
				if (j < 3) //If the index is 0, 1, or 2
				{
					//Output to the console for the user
					System.out.print(m[i][j] + "  "); //Print the integers to the console horizontally with spaces between them
				}
				
				else //Otherwise, if the last integer in the row is to be displayed
				{
					//Output to the console for the user
					System.out.print(m[i][j]); //Print the last integer to the console with no space
				}
			}
			System.out.println(); //Take care of subsequent rows of output being on a separate line
		}
	}

	//At the beginning, the position in the array is (0, 0), and sum is 0
	private static void findMin (int a, int b, int sum) //Private recursive method which calls itself to find the cheapest path
	{
		/*In general, when this method is called, the position in the array is (a,b). The method proceeds in one of two possible directions 
		  (South and East) and for each new point it touches, it updates the sum by adding the toll at that position. For the first run, the
		  method goes straight South, and then straight East, until the last point is reached. Each subsequent run finds a different path and if the 
	      running total (sum) at the end is less than the value of the cheapest, then cheapest is updated to reflect a new path. By the end of all
	      the recursive calls, the method has gone through and the cheapest path was recorded.*/
		
		sum += m[a][b]; //Add the toll at (a, b) to the running sum of the current path
		
		//This decisions happens first
		if (a < 3) //If the path is not at the last row yet
		{
			findMin (a + 1, b, sum); //Move South, by adding 1 to the row being referenced and by calling the method recursively
		}
		
		//This decisions happens second
		if (b < 3) //If the path is not at the last column yet
		{
			findMin (a, b + 1, sum); //Move East, by adding 1 to the column being referenced and by calling the method recursively
		}
		
		//By this point, the path has made it to to (3, 3)
		if (a + b == 6 && cheapest > sum) //If you are at the last index and if the minimum is greater than the running sum for the current path
		{
			cheapest = sum; //Update the minimum to the smaller value
		}
	}
}