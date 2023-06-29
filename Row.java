import java.util.*;
/**
 * Represents a column of the board that 
 * is updated when a square within the column
 * is changed.
 * 
 * @author Maria Vazhaeparambil
 * @version May 31, 2018
 */
public class Row implements Section 
{
    private int rowNum;

    /**
     * Constructor for objects of class Row
     * 
     * @param num the number used to define which row in the sudoku this is
     */
    public Row(int num)
    {
        rowNum = num;
    }

    /**
     * Updates the row based on the square that has been changed.
     * 
     * @param  square  the board data that holds all the Sudoku information
     * @param  aSquare the specific square in the board that has been changed
     */
    public void update(Square[][] square, Square aSquare)
    {
        if(aSquare.getRow() == rowNum)
        {
            for(int item = 0; item < square.length; item++)
            {
                if(aSquare.getValue() != 0 && item != aSquare.getCol())
                    square[rowNum][item].removePossibleValue(aSquare.getValue());
            }
        }
    }
    
    /**
     * Checks whether there is only one possibile location for a number within a row.
     * 
     * @param  square  is the board data that holds all Sudoku information
     */
    public boolean check(Square[][] square)
    {
        int count = 0;
        int col = -1;
        boolean ret = false;
        for(int i = 1; i <= 9; i++)
        {
            for(int c = 0; c < square[rowNum].length; c++)
            {
                if(square[rowNum][c].getValue() == i)
                    count += 2;
                if(square[rowNum][c].isPossible(i))
                {
                    count++;
                    col = c;
                }
            }    
            if(count == 1)
            {
            	ret = true;
                square[rowNum][col].setValue(i);
            }
            count = 0;
        }
        return ret;
    }
    
    /**
     * Checks whether the row has one of every number.
     * 
     * @param square is the board data that holds all the Sudoku information
     * 
     * @return true, if the column is complete and has every value, otherwise,
     *          false
     */
    public boolean checkRow(Square[][] square)
    {
        int sum = 0;
        for(int c = 0; c < square[rowNum].length; c++)
        {
            sum += square[rowNum][c].getValue();
        }
        return sum == 45;
    }
     
    /**
     * Returns a group of possible integers that is the same for a group of squares in the row.
     * 
     * @param  square  is the board data that holds all Sudoku information
     * 
     * @return array of int of length two that contains the first pair of
     * possible values that occur in two squares of this section;
     * returns null if no such pair exists
     */
    public boolean hidden(Square[][] square)
    {
    	boolean rem = false;
    	ArrayList<ArrayList<Integer>> poss = new ArrayList<ArrayList<Integer>>();
        for(int x = 1; x <= 9; x++)
        {
        	ArrayList<Integer> pos = new ArrayList<Integer>();
        	for(int c = 0; c < 9; c++)
        	{
        		if(square[rowNum][c].returnPossible().indexOf(x) != -1)
        		{
        			pos.add(c);
        		}
        	}
        	poss.add(pos);
        }
        
        for(int x = 0; x < poss.size(); x++)
        {
        	if(poss.get(x).size() != 1)
        	{
        		ArrayList<Integer> other = new ArrayList<Integer>();
        		for(int x2 = 0; x2 < poss.size(); x2++)
        		{
        			if(contains(poss.get(x), poss.get(x2)))
            		{
        				other.add(x2 + 1);	
            		}	
        		}
        		if(other.size() == poss.get(x).size())
        		{
        			boolean now = removeElse(square, other, poss.get(x));
        			if(now)
                    	rem = true;
        		}
        	}
        }
        return rem;
    }
    
    public boolean contains(ArrayList<Integer> first, ArrayList<Integer> second)
    {
    	if(second.size() > first.size())
    		return false;
    	for(int a = 0; a < second.size(); a++)
    	{
    		if(first.indexOf(second.get(a)) == -1)
    			return false;
    	}
    	return true;
    }
    
    public boolean removeElse(Square[][] square, ArrayList<Integer> remove, ArrayList<Integer> pos)
    {
    	boolean rem = false;
    	ArrayList<Integer> all = new ArrayList<Integer>(Arrays. asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
    	for (int i = 0; i < remove.size(); i++)
    	{
    		all.remove(Integer.valueOf(remove.get(i)));
    	}
    	for (int c = 0; c < pos.size(); c++)
    	{
    		for(int a = 0; a < all.size(); a++)
    		{
    			boolean now = square[rowNum][pos.get(c)].removePossibleValue(all.get(a));
    			if(now)
                	rem = true;
    		}
    	}
    	return rem;
    }
    
    /**
     * Checks whether the only possibilites of a number lie in one region.
     * 
     * @param square is the board data that holds all the Sudoku information
     */
    public boolean singleRegion(Square[][] square)
    {
    	boolean rem = false;
        for(int i = 1; i <= 9; i++)
        {
            ArrayList<Integer> rows = new ArrayList<Integer>();
            for(int c = 0; c < 9; c++)
            {
                if(square[rowNum][c].isPossible(i))
                	rows.add(c);
            }
            
            boolean check = true;
            for(int n = 1; n < rows.size(); n++)
            {
                if((int)(rows.get(n - 1)/3) != (int)(rows.get(n)))
                    check = false;
            }
            
            if(check && rows.size() > 0)
            {
            	boolean now = processRegion(square, rows.get(0), i);
            	if(now)
                	rem = true;
            }
        }
        return rem;
    }
    
    public boolean processRegion(Square[][] square, int col, int num)
    {
    	boolean rem = false;
    	int rstart = (int)(rowNum/3) * 3;
    	for (int r = rstart; r < rstart + 3; r++)
    	{
    		if(r != rowNum)
    		{
    			int cstart = (int)(col/3) * 3;
    			for (int c = cstart; c < cstart + 3; c++)
    			{
        			boolean now = square[r][c].removePossibleValue(num);
        			if(now)
                    	rem = true;
        		}
        	}
    	}
    	return rem;
    }
    
    public boolean nakeds(Square[][] square)
    {
    	boolean rem = false;
    	for (int c = 0; c < 9; c++)
    	{
    		ArrayList<Integer> poss = square[rowNum][c].returnPossible();
    		if(poss.size() != 1)
    		{
    			ArrayList<Integer> count = new ArrayList<Integer>();
    			count.add(c);
    			for (int c2 = 0; c2 < 9; c2++)
    			{
    				ArrayList<Integer> test = square[rowNum][c2].returnPossible();
    				if(c != c2 && test.size() != 1 && test.size() <= poss.size())
    				{
    					boolean n = true;
    					for(int i = 0; i < test.size(); i++)
    					{
    						if(poss.indexOf(test.get(i)) == -1)
    							n = false;
    					}	
    					if(n)
    						count.add(c2);
    				}
    			}
    			if(poss.size() == count.size())
    			{
    				boolean now = removeNakeds(square, poss, count);
    				if(now)
                    	rem = true;
    			}
    			
    		}
    	}   
    	return rem;
    }
    
    public boolean removeNakeds(Square[][] square, ArrayList<Integer> poss, ArrayList<Integer> count)
    {	
    	boolean rem = false;
    	for (int c = 0; c < 9; c++)
    	{
    		if (count.indexOf(c) == -1)
    		{
    			for(int x = 0; x < poss.size(); x++)
    			{
    				if(square[rowNum][c].getValue() == 0)
    				{
    					boolean now = square[rowNum][c].removePossibleValue(poss.get(x));
    					if(now)
                        	rem = true;
    				}
    			}
    		}
    	}
    	return rem;
    }
}
