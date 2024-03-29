import java.util.*;
/**
 * Represents a single square in a Sudoku puzzle.
 * 
 * @author Maria Vazhaeparambil
 * @version May 31, 2018
 */
public class Square
{
    private int val = 0;
    private int row;
    private int col;
    ArrayList<Integer> numbers = new ArrayList<Integer>(
            Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

    /**
     * Constructor for objects of class Square.
     * 
     * @param row the row of the square
     * @param col the column of the square
     * @param val the value of the number in the square
     */
    public Square(int row, int col, int val)
    {
        if(val != 0)
            setValue(val);
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the value.
     * 
     * @return the value of the square
     */
    public int getValue()
    {
        return val;
    }
    
    /**
     * Returns the row.
     * 
     * @return the row of the square
     */
    public int getRow()
    {
        return row;
    }
    
    /**
     * Returns the column.
     * 
     * @return the column of the square
     */
    public int getCol()
    {
        return col;
    }
    
    
    /**
     * Sets the value.
     * 
     * @param n what the value is set to
     */
    public void setValue(int n)
    {
        val = n;
        if(n != 0)
        {
            for(int i = numbers.size() - 1; i >= 0; i--)
            {
                numbers.remove(i);
            }
            numbers.add(n);
        }
    }
    
    /**
     * Initializes the number that is found.
     */
    public void found()
    {
        if(numbers.size() == 1)
        {
            setValue(numbers.get(0));
        }
    }
    
    /**
     * Returns an array of possible numbers.
     * 
     * @return an array list of possible numbers for the square
     */
    public ArrayList<Integer> returnPossible()
    {
        return numbers;
    }
    
    /**
     * Checks if a number is possible in this square.
     * 
     * @param num the number that will be checked if it is possible
     * 
     * @return true, if the number can be in the square, otherwise
     *          false
     */
    public boolean isPossible(int num)
    {
        for(int i = 0; i < numbers.size(); i++)
        {
            if(numbers.get(i) == num)
                return true;
        }
        return false;
    }
    
    /**
     * Removes a number from the possible choices.
     * 
     * @param value the value that should not be possible in this square
     */
    public boolean removePossibleValue(int value)
    {
        return numbers.remove(Integer.valueOf(value));
    }
    
    /**
     * Resets the square so that any number can be in it and so that there is no value.
     */
    public void reset()
    {
        val = 0;
        numbers = new ArrayList<Integer>(
            Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
    }
    
    /**
     * Sets the numbers possible to the given array.
     * 
     * @param arrayPoss an array of the values that are now possible
     */
    public void setPossible(ArrayList<Integer> arrayPoss)
    {
        numbers = new ArrayList<Integer>();
        for(int i = 0; i < arrayPoss.size(); i++)
            numbers.add(arrayPoss.get(i));
    }
}
