import java.util.*;
/**
 * Represents a column of the board that 
 * is updated when a square within the column
 * is changed.
 * 
 * @author Maria Vazhaeparambil
 * @version May 31, 2018
 */
public class Column implements Section
{
    private int colNum;

    /**
     * Constructor for objects of class Column
     * 
     * @param num the number used to define which column in the sudoku this is
     */
    public Column(int num)
    {
        colNum = num;
    }

    /**
     * Updates the column based on the square that has been changed.
     * 
     * @param  square  the board data that holds all the Sudoku information
     * @param  aSquare the specific square in the board that has been changed
     */
    public void update(Square[][] square, Square aSquare)
    {
        if(aSquare.getCol() == colNum)
        {
            for(int item = 0; item < square[0].length; item++)
            {
                if(aSquare.getValue() != 0 && item != aSquare.getRow())
                    square[item][colNum].removePossibleValue(aSquare.getValue());
            }
        }
    }
    
    /**
     * Returns a group of possible integers that is the same for a group of squares in the column.
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
        	for(int r = 0; r < 9; r++)
        	{
        		if(square[r][colNum].returnPossible().indexOf(x) != -1)
        		{
        			pos.add(r);
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
    	for (int r = 0; r < pos.size(); r++)
    	{
    		for(int a = 0; a < all.size(); a++)
    		{
    			boolean now = square[pos.get(r)][colNum].removePossibleValue(all.get(a));
    			if(now)
                	rem = true;
    		}
    	}
    	return rem;
    }
    
    /**
     * Checks whether there is only one possibile location for a number within a column.
     * 
     * @param  square  is the board data that holds all Sudoku information
     */
    public boolean check(Square[][] square)
    {
        int count = 0;
        int row = -1;
        boolean ret = false;
        for(int i = 1; i <= 9; i++)
        {
            for(int r = 0; r < square.length; r++)
            {
                if(square[r][colNum].getValue() == i)
                    count += 2;
                if(square[r][colNum].isPossible(i))
                {
                    count++;
                    row = r;
                }
            }    
            if(count == 1)
            {
            	ret = true;
                square[row][colNum].setValue(i);
            }
            count = 0;
        }
        return ret;
    }
    
    /**
     * Checks whether the column has one of every number.
     * 
     * @param square is the board data that holds all the Sudoku information
     * 
     * @return true, if the column is complete and has every value, otherwise,
     *          false
     */
    public boolean checkCol(Square[][] square)
    {
        int sum = 0;
        for(int r = 0; r < square.length; r++)
        {
            sum += square[r][colNum].getValue();
        }
        return sum == 45;
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
            ArrayList<Integer> cols = new ArrayList<Integer>();
            for(int r = 0; r < 9; r++)
            {
                if(square[r][colNum].isPossible(i))
                	cols.add(r);
            }
            
            boolean check = true;
            for(int n = 1; n < cols.size(); n++)
            {
                if((int)(cols.get(n - 1)/3) != (int)(cols.get(n)))
                    check = false;
            }
            
            if(check && cols.size() > 0)
            {
                boolean now = processRegion(square, cols.get(0), i);
                if(now)
                	rem = true;
            }
        }
        return rem;
    }
    
    public boolean processRegion(Square[][] square, int row, int num)
    {
    	boolean rem = false;
    	int rstart = (int)(row/3) * 3;
    	for (int r = rstart; r < rstart + 3; r++)
    	{
    		int cstart = (int)(colNum/3) * 3;
    		for (int c = cstart; c < cstart + 3; c++)
        	{
        		if(c != colNum)
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
    	for (int r = 0; r < 9; r++)
    	{
    		ArrayList<Integer> poss = square[r][colNum].returnPossible();
    		if(poss.size() != 1)
    		{
    			ArrayList<Integer> count = new ArrayList<Integer>();
    			count.add(r);
    			for (int r2 = 0; r2 < 9; r2++)
    			{
    				ArrayList<Integer> test = square[r2][colNum].returnPossible();
    				if(r != r2 && test.size() != 1 && test.size() <= poss.size())
    				{
    					boolean n = true;
    					for(int i = 0; i < test.size(); i++)
    					{
    						if(poss.indexOf(test.get(i)) == -1)
    							n = false;
    					}
    					if(n)
    						count.add(r2);
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
    	for (int r = 0; r < 9; r++)
    	{
    		if (count.indexOf(r) == -1)
    		{
    			for(int x = 0; x < poss.size(); x++)
    			{
    				if(square[r][colNum].getValue() == 0)
    				{
    					boolean now = square[r][colNum].removePossibleValue(poss.get(x));
    					if(now)
                        	rem = true;
    				}
    			}
    		}
    	}
    	return rem;
    }
}
