import BasicIO.*;
import java.util.LinkedList;

/**
 *COSC 1P03
 *ASSIGNMENT #5
 *Username: (Chibuike Nnolim)
 *Student #: (7644941)
 *
 *This is the starting point of a program for finding one's keys in a 'floor plan' data file.
 *You're provided with the code for loading the data file, and just need to fill in the recursive
 *algorithm. Make sure to include feedback on e.g. unsolveable floor plans!
 *
 *You've also been provided with three sample files, though many more are possible:
 *floor1.txt solves simply
 *floor2.txt requires quite a bit more 'backtracking'
 *floor3.txt is unsolveable
 */

public class Keychain
{
    //Instance Variables
    char[][] plans = null;
    int startRow = -1;//Starting row for 'Me'
    int startCol = -1;//Starting column for 'Me'
    //Extra
    int curRow = 0;
    int curCol = 0;
    int keyRow = 0;
    int keyCol = 0;
    LinkedList<Character> directions = new LinkedList();

    //Constructor
    public Keychain()
    {

    }


    //Methods
    /**
     * Basic code for loading the data file into the array.
     */
    private void loadFloorPlans()
    {
        int height, width;
        ASCIIDataFile file = new ASCIIDataFile();
        String temp;
        height = file.readInt();
        width = file.readInt();
        plans = new char[height][width];

        for (int i = 0;i < height; i++)//Loops through every row in the maze
        {
            temp=file.readLine();
            plans[i] = temp.toCharArray();
            if(temp.indexOf('M') >= 0)//Checks where the location of the starting point is
            {
                startRow = i; startCol = temp.indexOf('M');
                curRow = i; curCol = temp.indexOf('M');
            }
            if(temp.indexOf('K') >= 0)//Checks where the location of the keys is
            {
                keyRow = i; keyCol = temp.indexOf('K');
            }
        }
        System.out.println("File transcription complete!\n");
        solve();
    }

    /**
     * This method starts the process of solving the maze and seeing if the maze is solvable or not.
     */
    private void solve()
    {
        System.out.println("Initial State:");
        printFloorPlans();

        if(recursiveSolve(0))//Checks to see if the maze is solved
        {
            System.out.println("\nFinal Layout:");
            printFloorPlans();
            System.out.print("Findeth yon keys: "); //Directions for getting to my keys
            //TODO: DIRECTIONS HERE
            for(char l : directions)//loops through every direction in the list
            {
                System.out.print(l + ", ");
            }
        }
        else//Maze is not solved
        {
            System.out.println("\nOh no! The keys are lost to us!");
            printFloorPlans(); //Displaying anyway, since we presumably modified the floor plans
        }

    }

    //Put your recursive solution here.
    //TODO: include whatever parameters make sense for position, path, etc.
    /**
     * This method starts the recursion of going through the maze, and returns true or false depending on if the key is found or lost.
     *
     * @param path
     */
    private boolean recursiveSolve(int path)
    {
        if((keyRow == curRow) && (keyCol == curCol))//Checks to see if the character is on the keys
        {
            plans[curRow][curCol] = 'K';
            return true;
        }
        switch(path)//Checks to see the current key to see which path to take
        {
            //Go up
            case 0:
            {
                if((plans[curRow - 1][curCol] == 'O') || (plans[curRow - 1][curCol] == 'C'))//Checks to see if the space above is searchable or not
                {
                    return recursiveSolve(1);
                }
                else
                {
                    curRow--;
                    plans[curRow][curCol] = 'C';
                    directions.add('U');
                    return recursiveSolve(0);
                }
            }
            //Go Right
            case 1:
            {
                if((plans[curRow][curCol + 1] == 'O') || (plans[curRow][curCol + 1] == 'C'))//Checks to see if the space to the right is searchable or not
                {
                    return recursiveSolve(2);
                }
                else
                {
                    curCol++;
                    plans[curRow][curCol] = 'C';
                    directions.add('R');
                    return recursiveSolve(0);
                }
            }
            //Go Down
            case 2:
            {
                if((plans[curRow + 1][curCol] == 'O') || (plans[curRow + 1][curCol] == 'C'))//Checks to see if the space below is searchable or not
                {
                    return recursiveSolve(3);
                }
                else
                {
                    curRow++;
                    plans[curRow][curCol] = 'C';
                    directions.add('D');
                    return recursiveSolve(0);
                }
            }
            //Go Left
            case 3:
            {
                if((plans[curRow][curCol - 1] == 'O') || (plans[curRow][curCol - 1] == 'C'))//Checks to see if the space to the left is searchable or not
                {
                    curRow = startRow;
                    curCol = startCol;
                    if((plans[curRow - 1][curCol] | plans[curRow][curCol + 1] | plans[curRow + 1][curCol] | plans[curRow][curCol - 1]) == ('O' | 'C'))//Checks to see if every direction is blocked at the starting point
                    {
                        return false;
                    }
                    else
                    {
                        directions.clear();
                        return recursiveSolve(0);
                    }
                }
                else
                {
                    curCol--;
                    plans[curRow][curCol] = 'C';
                    directions.add('L');
                    return recursiveSolve(0);
                }
            }
        }
        return false;//this is just here as a placeholder, so it can compile
    }

    private void printFloorPlans()
    {
        for (char[] row:plans)//loops through every letter in the array
        {
            for (char c:row) System.out.print(c);
            System.out.println();
        }
    }

    public static void main(String args[]) {new Keychain().loadFloorPlans();}
}