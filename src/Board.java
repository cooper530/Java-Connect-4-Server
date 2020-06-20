import java.util.Arrays;

/*
The Board Class handles the entire logic of the game, including the board and methods to check if the game
has been won. An instance of this class is created in the Main Class, where the game is handled.
 */
public class Board {
    //Board 2D Array
    int[][] board;

    /*
    The constructor creates a 6x7 Connect 4 Board
     */
    public Board()
    {
        //[row][col]
        board = new int[6][7];
    }

    /*
    Determines whether the player can place a chip in the desired column
     */
    public boolean update(int col, int player)
    {
        if(col == -1)
            return false;
        for(int i=board.length - 1;i>=0;i--)
        {
            if(board[i][col] == 0)
            {
                board[i][col] = player;
                return true;
            }
        }
        return false;
    }

    /*
    Returns the next available row for a chip to be placed
     */
    public int getAvailRow(int col)
    {
        for(int i =0;i<board.length;i++)
        {
            if(board[i][col] != 0)
            {
                return i;
            }
        }
        return 5;
    }

    /*
    Clears the entire board once the game is completed
     */
    public void clearBoard()
    {
        for(int[] row : board)
            Arrays.fill(row, 0);
    }

    /*
    Returns the size of each column
     */
    public int getColSize()
    {
        return board[0].length;
    }

    /*
    Checks if there is a Connect 4 diagonally, horizontally, or vertically
     */
    public boolean checkBoard()
    {
        return checkDiag() || checkHoriz() || checkVert();
    }

    /*
    Checks whether there is a Connect 4 diagonally
     */
    private boolean checkDiag()
    {
        int regCounter = 0;
        int p1Counter = 0;
        int p2Counter = 0;
        //row
        for(int l=0;l<board.length;l++) {
            //col
            for (int k = 0; k < board[0].length; k++) {
                //row and col modifiers
                for (int i = l, j = k; (i < k + l + 4 && i < board.length) && (j < k + 4 + l && j < board[0].length); i++, j++) {
                    if(board[i][j] == 1) {
                        p1Counter++;
                    }
                    else if(board[i][j] == 2) {
                        p2Counter++;
                    }
                    if(p1Counter == 4 || p2Counter == 4)
                        return true;
                    if(regCounter == 4)
                        break;
                    regCounter++;
                }
                regCounter = 0;
                p1Counter = 0;
                p2Counter = 0;
            }
        }

        //row
        for(int l=0;l<board.length;l++) {
            //col
            for (int k = board[0].length - 1; k >= 0; k--) {
                //row and col modifiers
                for (int i = l, j = k;j >= 0 && i<board.length; i++, j--) {
                    if(board[i][j] == 1) {
                        p1Counter++;
                    }
                    else if(board[i][j] == 2) {
                        p2Counter++;
                    }
                    if(p1Counter == 4 || p2Counter == 4)
                        return true;
                    if(regCounter == 3)
                        break;
                    regCounter++;
                }
                regCounter = 0;
                p1Counter = 0;
                p2Counter = 0;
            }
        }

        return false;
    }

    /*
    Checks whether there is a Connect 4 vertically
     */
    private boolean checkVert()
    {
        int regCounter = 0;
        int p1Counter = 0;
        int p2Counter = 0;
        for(int j=0;j<board[0].length;j++)
        {
            for(int i=0;i<board.length;i++) {
                if (board[i][j] == 1)
                    p1Counter++;
                else if(board[i][j] == 2)
                    p2Counter++;
                regCounter++;

                if (regCounter == 4) {
                    if (p1Counter == 4 || p2Counter == 4)
                        return true;
                    else
                        i -= 3;
                    regCounter = 0;
                    p1Counter = 0;
                    p2Counter = 0;
                }
            }
            regCounter = 0;
            p1Counter = 0;
            p2Counter = 0;
        }
        return false;
    }

    /*
    Checks whether there is a Connect 4 horizontally
     */
    private boolean checkHoriz()
    {
        int regCounter = 0;
        int p1Counter = 0;
        int p2Counter = 0;
        for(int i=0;i<board.length;i++)
        {
            for(int j=0;j<board[0].length;j++) {
                if (board[i][j] == 1)
                    p1Counter++;
                else if(board[i][j] == 2)
                    p2Counter++;
                regCounter++;

                if (regCounter == 4) {
                    if (p1Counter == 4 || p2Counter == 4)
                        return true;
                    else
                        j -= 3;
                    regCounter = 0;
                    p1Counter = 0;
                    p2Counter = 0;
                }
            }
            regCounter = 0;
            p1Counter = 0;
            p2Counter = 0;
        }
        return false;
    }

    public boolean isFull()
    {
        for(int i = 0;i<board.length;i++)
        {
            for(int j=0;j<board[i].length;j++)
            {
                if(board[i][j] == 0)
                    return false;
            }
        }
        return true;
    }

    /*
    Prints the board to the console
     */
    public String toString()
    {
        //Starting rows
        for(int i=0;i<board[0].length;i++)
            System.out.print(i + " ");
        System.out.println(" ");
        for(int i=0;i<board[0].length;i++)
            System.out.print("- ");
        System.out.println(" ");

        for (int i=0;i<board.length;i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println(" ");
        }
        return "";
    }
}
