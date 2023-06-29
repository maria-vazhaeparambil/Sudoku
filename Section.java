/**
 * Represents a portion of the board that must be
 * updated when a board square contained within this
 * section is modified.
 * 
 * @author Maria Vazhaeparambil
 * @version February 22, 2018
 */
public interface Section
{
    /**
     * Updates the board based on the square that has been changed.
     * 
     * @param  square  is the board data that holds all Sudoku information
     * @param  aSquare  is the Square used to update all Section values
     */
    void update(Square[][] square, Square aSquare);
}
