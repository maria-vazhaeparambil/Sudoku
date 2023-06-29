/**
 * A class that asks the user for a Sudoku that will be solved.
 * 
 * @author Maria Vazhaeparambil
 * @version May 31, 2018
 */
public class SudokuSolve
{
    /**
     * Helps the user use the sudoku solver.
     */
    public Square[][] use(int[][] inputted)
    {
        Board sudoku = new Board();
        for(int row = 0; row < 9; row++)
        {
            for(int col = 0; col < 9; col++)
            {
                sudoku.updateSections(row, col, inputted[row][col]);
            }
        }
        
        sudoku.solved();

        return sudoku.getSquare();
    }
}