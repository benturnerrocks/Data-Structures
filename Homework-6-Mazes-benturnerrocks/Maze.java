import java.io.*;
import java.lang.*;
import java.util.*;

/**
* Maze represents a maze that can be navigated. The maze
* should indicate its start and end squares, and where the
* walls are. 
*
* Eventually, this class will be able to load a maze from a
* file, and solve the maze.
* The starter code has part of the implementation of load, but
* it does not read and store the information about where the walls of the maze are.
*
*/
public class Maze { 
    //Number of rows in the maze.
    private int numRows;
    
    //Number of columns in the maze.
    private int numColumns;
    
    //Grid coordinates for the starting maze square
    private int startRow;
    private int startColumn;
    
    //Grid coordinates for the final maze square
    private int finishRow;
    private int finishColumn;
    
    //**************YOUR CODE HERE******************
    //You'll likely want to add one or more additional instance variables
    //to store the squares of the maze
    private ArrayList<MazeSquare> listOfSquares;
    /**
     * Creates an empty maze with no squares.
     */
    public Maze() {
        //creates a new array list to store all the squares in
        listOfSquares = new ArrayList<MazeSquare>();
        //defines everything as 0 as a baseline if .load fails
        this.numRows = 0;
        this.numColumns = 0;

        this.startRow = 0;
        this.startColumn = 0;

        this.finishRow = 0;
        this.finishColumn = 0;
    } 
    
    /**
     * Loads the maze that is written in the given fileName.
     * Returns true if the file in fileName is formatted correctly
     * (meaning the maze could be loaded) and false if it was formatted
     * incorrectly (meaning the maze could not be loaded). The correct format
     * for a maze file is given in the assignment description. Ways 
     * that you should account for a maze file being incorrectly
     * formatted are: one or more squares has a descriptor that doesn't
     * match  *, 7, _, or | as a descriptor; the number of rows doesn't match
     * what is specified at the beginning of the file; the number of
     * columns in any row doesn't match what's specified at the beginning
     * of the file; or the start square or the finish square is outside of
     * the maze. You can assume that the file does start with the number of
     * rows and columns.
     * 
     */
    public boolean load(String fileName) {
        //taking in the maze file
        File inputFile = new File(fileName);
        //tests to make sure there is a file, else it will catch to no file error and print a statement
        Scanner scanner = null;
        try {
            scanner = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }
        //get the first three lines that include dimenstions of maze and start/stop squares
        String[] dimensions = scanner.nextLine().split(" ");
        String[] start = scanner.nextLine().split(" ");
        String[] stop = scanner.nextLine().split(" ");
        //tests that the initial conditions for the maze are given correctly
        int correctFormat = dimensions.length + start.length + stop.length;
        if (correctFormat==6) {
            this.numColumns = Integer.parseInt(dimensions[0]);
            this.numRows = Integer.parseInt(dimensions[1]);

            this.startColumn = Integer.parseInt(start[0]);
            this.startRow = Integer.parseInt(start[1]);
        
            this.finishColumn = Integer.parseInt(stop[0]);
            this.finishRow = Integer.parseInt(stop[1]);
        } else{
            //incorrect format
            return false;
        }
        

        // Get one line at a time from the maze file
        //count the number of lines to test whether it is an accepted number
        for (int eachRow=0; eachRow < this.numRows; eachRow++) {
            //taking the square data from the file
            String squareData;
            try {
                //we try to get the next line
                squareData = scanner.nextLine();
            }
            catch (Exception e) {
                //if there is no next line when there should be it, it will return false
                return false;
            }
            //tests for correct format
            if (squareData.length() == this.numColumns) {
                //breaks down the line into each character and the square it represents
                for (int eachCol=0; eachCol < this.numColumns; eachCol++)
                {
                    char info = squareData.charAt(eachCol);
                //creating each and every square

                if (info == '|' || info == '7' || info == '*' || info == '_') {
                    MazeSquare newSquare = new MazeSquare(info, eachRow, eachCol);
                    listOfSquares.add(newSquare);
                }else{
                    return false;
                } 
                
                }
            } else{
                return false;
            }
            
        }
        if (scanner.hasNextLine()){
            //incorrect format since number of rows specified does not match the number of squares defined in file
            return false;
        }
        scanner.close();
    return true;
    } 
    
    
    /**
     * Prints the maze with the start and finish squares marked. Does
     * not include a solution.
     */
    public void print() {
        //We'll print off each row of squares in turn.
        for(int row = 0; row < numRows; row++) {
            
            //Print each of the lines of text in the row
            for(int charInRow = 0; charInRow < 4; charInRow++) {
                //Need to start with the initial left wall.
                if(charInRow == 0) {
                    System.out.print("+");
                } else {
                    System.out.print("|");
                }
                
                for(int col = 0; col < numColumns; col++) {
                    MazeSquare curSquare = this.getMazeSquare(row, col);
                    if(charInRow == 0) {
                        //We're in the first row of characters for this square - need to print
                        //top wall if necessary.
                        if(curSquare.hasTopWall()) {
                            System.out.print(getTopWallString());
                        } else {
                            System.out.print(getTopOpenString());
                        }
                    } else if(charInRow == 1 || charInRow == 3) {
                        //These are the interior of the square and are unaffected by
                        //the start/final state.
                        if(curSquare.hasRightWall()) {
                            System.out.print(getRightWallString());
                        } else {
                            System.out.print(getOpenWallString());
                        }
                    } else {
                        //We must be in the second row of characters.
                        //This is the row where start/finish should be displayed if relevant

                        //Check if we're in the start or finish state
                        
                        if(startRow == row && startColumn == col) {
                            System.out.print("  S  ");
                        } else if(finishRow == row && finishColumn == col) {
                            System.out.print("  F  ");
                        } 
                        else if(curSquare.inSolution()) {
                            //adding line that will add * if it is a square for our solution
                            System.out.print("  *  ");
                        }
                         else {
                            System.out.print("     ");
                        }
                        if(curSquare.hasRightWall()) {
                            System.out.print("|");
                        } else {
                            System.out.print(" ");
                        }
                    } 
                }
                
                //Now end the line to start the next
                System.out.print("\n");
            }           
        }
        
        //Finally, we have to print off the bottom of the maze, since that's not explicitly represented
        //by the squares. Printing off the bottom separately means we can think of each row as
        //consisting of four lines of text.
        printFullHorizontalRow(numColumns);
    }
    
    /**
     * Prints the very bottom row of characters for the bottom row of maze squares (which is always walls).
     * numColumns is the number of columns of bottom wall to print.
     */
    private static void printFullHorizontalRow(int numColumns) {
        System.out.print("+");
        for(int row = 0; row < numColumns; row++) {
            //We use getTopWallString() since bottom and top walls are the same.
            System.out.print(getTopWallString());
        }
        System.out.print("\n");
    }
    
    /**
     * Returns a String representing the bottom of a horizontal wall.
     */
    private static String getTopWallString() {
        return "-----+";
    }
    
    /**
     * Returns a String representing the bottom of a square without a
     * horizontal wall.
     */
    private static String getTopOpenString() {
        return "     +";
    }
    
    /**
     * Returns a String representing a left wall (for the interior of the row).
     */
    private static String getRightWallString() {
        return "     |";
    }
    
    /**
     * Returns a String representing no left wall (for the interior of the row).
     */
    private static String getOpenWallString() {
        return "      ";
    }
    

    public MazeSquare getMazeSquare(int row, int col) {
        //loops through the list of squares
        for (int i=0; i<listOfSquares.size(); i++) {
            //checks if the current square has a row and column equal to the one we are looking for
            int curRow = listOfSquares.get(i).getRow();
            int curCol = listOfSquares.get(i).getColumn();
            if (curCol==col && curRow==row) {
                //if we have found the right square, return it
                return listOfSquares.get(i);
            }
        }
        //returns null if there is no MazeSquare at the specified row and column
        return null;
    }
        /**
    * Computes and returns a solution to this maze. If there are multiple
    * solutions, only one is returned, and getSolution() makes no guarantees about
    * which one. However, the returned solution will not include visits to dead
    * ends or any backtracks, even if backtracking occurs during the solution
    * process. 
    *
    * @return a stack of MazeSquare objects containing the sequence of squares
    * visited to go from the start square (bottom of the stack) to the finish
    * square (top of the stack). If there is no solution, an empty stack is
    * returned.
    */
    public Stack<MazeSquare> getSolution() {
        //initialize a stack to hold the solution in, it does not need to be an instance variable since I do not use it outside this method
        Stack<MazeSquare> solutionMaze = new MysteryStackImplementation<MazeSquare>();
        //pull out the start square
        MazeSquare startSquare = this.getMazeSquare(this.startRow, this.startColumn);
        //pull out the end square
        MazeSquare endSquare = this.getMazeSquare(this.finishRow, this.finishColumn);
        //put the start square into the stack
        solutionMaze.push(startSquare);
        //define the start square as having been visited
        startSquare.updateVisited();
        //define the start square as a part of the solution
        startSquare.inSolution();
        //loop with the conditions to break only when the stack is empty (no solution) or the final square is on the top (found the solution) we use this end variable to adapt to the while statement
        boolean end = !solutionMaze.isEmpty();
        while (end) {
            //we test if there is anywhere to go from the top item in the stack
            //we can identify the top square so we can use its information
            MazeSquare curSquare = solutionMaze.peek();
            //pull out the row and column so we can use that to find the adjacent squares
            int curRow = curSquare.getRow();
            int curCol = curSquare.getColumn();
            //create adjacent maze square holders
            MazeSquare toTheRight = curSquare;
            MazeSquare toTheLeft = curSquare;
            MazeSquare toTheTop = curSquare;
            MazeSquare toTheBottom = curSquare;
            //if statement: test if the current square is on the edge of the maze, if there is a border we do not allow it to shift that direction
            //else statement: if they are not on the border, we get each adjacent square
            if (curRow==this.numRows-1) {
                //if on the bottom boundary, cannot look for square to bottom since it doesn't exist
                toTheBottom = this.getMazeSquare(curRow, curCol);
            } else {
                toTheBottom = this.getMazeSquare(curRow+1, curCol);
            }
            if (curRow==0){
                //if on the top boundary, cannot look for square to top since it doesn't exist
                toTheTop = this.getMazeSquare(curRow, curCol);
            } else {
                toTheTop = this.getMazeSquare(curRow-1, curCol);
            }
            if (curCol==this.numColumns-1){
                //if on the right boundary, cannot look for square to right since it doesn't exist
                toTheRight = this.getMazeSquare(curRow, curCol);
            } else {
                toTheRight = this.getMazeSquare(curRow, curCol+1);
            }
            if (curCol==0){
                //if on the left boundary, cannot look for square to left since it doesn't exist
                toTheLeft = this.getMazeSquare(curRow, curCol);
            } else {
                toTheLeft = this.getMazeSquare(curRow, curCol-1);
            }
            //now we test each of the adjacent squares to see if they satisfy the conditions for us to move there
            //first we can test to the right, if nothing there and it is unvisited, then we go that way
            if (!curSquare.hasRightWall() && !toTheRight.beenVisited()) {
                solutionMaze.push(toTheRight);
                toTheRight.updateVisited();
                toTheRight.isInSolution();
            } //next we test to the top
            else if (!curSquare.hasTopWall() && !toTheTop.beenVisited()) {
                solutionMaze.push(toTheTop);
                toTheTop.updateVisited();
                toTheTop.isInSolution();
            } //test to the left
            else if (!toTheLeft.hasRightWall() && !toTheLeft.beenVisited()) {
                solutionMaze.push(toTheLeft);
                toTheLeft.updateVisited();
                toTheLeft.isInSolution();
            } //test the bottom square
            else if (!toTheBottom.hasTopWall() && !toTheBottom.beenVisited()) {
                solutionMaze.push(toTheBottom);
                toTheBottom.updateVisited();
                toTheBottom.isInSolution();
            } else{
                //if we can move anywhere, then go back where we came from
                curSquare.isNotInSolution();
                solutionMaze.pop();
            }
            //we want to reevaluate the conditions for the while loop so that we end the loop at the right time
            if (solutionMaze.isEmpty()){
                end = false;
            } else if (solutionMaze.peek()==endSquare) {
                end = false;
            } else{
                end = true;
            }
        }
        return solutionMaze;
    }
 
    /**
     * You should modify main so that if there is only one
     * command line argument, it loads the maze and prints it
     * with no solution. If there are two command line arguments
     * and the second one is --solve,
     * it should load the maze, solve it, and print the maze
     * with the solution marked. No other command lines are valid.
     */ 
    public static void main(String[] args) {
        //create an instance for our maze to attach to 
        Maze aMAZEing = new Maze();
        
        //test if one command-line argument
        //if so, then just load maze from file
        if (args.length==1) {
            aMAZEing.load(args[0]);
            aMAZEing.print();
        }//test if two command-line arguments, test if it is the right input and then solve the maze
        else if (args.length==2){
            System.out.println(args[1]);
            if (args[1].equals("--solve")){
                aMAZEing.load(args[0]);
                aMAZEing.getSolution();
                aMAZEing.print();
            }else{
                System.out.println("Incorrect second argument.");
            }
            
        }
        else{
            System.out.println("Please enter 1 or 2 commandline arguments.");
        }
    } 
}