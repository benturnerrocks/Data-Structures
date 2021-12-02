/**
* MazeSquare represents a single square within a Maze.
*
* The body of each of the existing methods needs to
* be changed so that it works properly, and minimally,
* you'll want to add one or more instance variable(s)
* as well as a constructor. You may add any other methods
* you choose as well, but you shouldn't delete my methods
* (the code I give to the graders assumes that, e.g.,
*  getColumn() can be called on a MazeSquare instance).
* @author Anna Rafferty
*/ 
public class MazeSquare {
        //defaults that the square does not have a top or right wall
    private char type = '*';
    private int row;
    private int column;
    private boolean visited = false;
    private boolean solution = false;

	public MazeSquare(char info, int row, int column)	{
        this.type = info;
        this.row = row;
        this.column = column;
    }
    public void updateVisited() {
        this.visited = true;
    }
    public boolean beenVisited() {
        return this.visited;
    }
    /**
     * Returns true if this square has a top wall.
     */
    public boolean hasTopWall() {
        return (this.type == '_' || this.type == '7');
    }
		
    /**
     * Returns true if this square has a right wall.
     */
    public boolean hasRightWall() {
        return (this.type == '|' || this.type == '7');
    }
		
    /**
     * Returns the row this square is in.
     */
    public int getRow() {
        return this.row;
    }
		
    /**
     * Returns the column this square is in.
     */
    public int getColumn() {
        return this.column;
    }
    //the next three methods are used to keep track of which squares are in the solution for printing purposes
    public void isInSolution() {
        this.solution = true;
    }

    public void isNotInSolution() {
        this.solution = false;
    }

    public boolean inSolution() {
        return this.solution;
    }
    
    
} 