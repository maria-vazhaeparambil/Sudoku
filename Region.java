import java.util.*;
/**
 * Represents a region of the board that 
 * is updated when a square within the region
 * is changed.
 * 
 * @author Maria Vazhaeparambil
 * @version May 31, 2018
 */
public class Region extends Sudoku implements Section 
                    
{
    private int startRow;
    private int startCol;

    /**
     * Constructor for objects of class Region
     * 
     * @param row the row which the region starts from
     * @param col the col which the region starts from
     */
    public Region(int row, int col)
    {
        startRow = row;
        startCol = col;
    }

    /**
     * Updates the region based on the square that has been changed.
     * 
     * @param  square  the board data that holds all the Sudoku information
     * @param  aSquare the specific square in the board that has been changed
     */
    public void update(Square[][] square, Square aSquare)
    {
    	if((int)(aSquare.getRow() / 3) == (int)(startRow / 3) && (int)(aSquare.getCol() / 3) == (int)(startCol / 3))
    	{
    		for(int r = startRow; r < startRow + 3; r++)
        	{
            	for(int c = startCol; c < startCol + 3; c++)
            	{
                	if(aSquare.getValue() != 0 && (r != aSquare.getRow() || c != aSquare.getCol()))
                		square[r][c].removePossibleValue(aSquare.getValue());
            	}
        	} 
    	}
    }
    
    /**
     * Checks whether there is only one possibile location for a number within a region.
     * 
     * @param  square  is the board data that holds all Sudoku information
     */
    public boolean check(Square[][] square)
    {
        int count = 0;
        int row = -1;
        int col = -1;
        boolean ret = false;
        for(int i = 1; i <= 9; i++)
        {
            for(int r = startRow; r < startRow + 3; r++)
            {
                for(int c = startCol; c < startCol + 3; c++)
                {
                    if(square[r][c].getValue() == i)
                        count += 2;
                    if(square[r][c].isPossible(i))
                    {
                        count++;
                        row = r;
                        col = c;
                    }
                }
            }    
            if(count == 1)
            {
            	ret = true;
                square[row][col].setValue(i);
            }
            count = 0;
        }
        return ret;
    }
    
    /**
     * Checks whether the region has one of every number.
     * 
     * @param square is the board data that holds all the Sudoku information
     * 
     * @return true, if the region is complete and has every value, otherwise,
     *          false
     */
    public boolean checkRegion(Square[][] square)
    {
        int sum = 0;
        for(int r = startRow; r < startRow + 3; r++)
        {
            for(int c = startCol; c < startCol + 3; c++)
            {
                sum += square[r][c].getValue();
            }
        }
        return sum == 45;
    }
    
    /**
     * Returns a group of possible integers that is the same for a group of squares in the region.
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
        	for(int r = startRow; r < startRow + 3; r++)
        	{
        		for(int c = startCol; c < startCol + 3; c++)
            	{
        			if(square[r][c].returnPossible().indexOf(x) != -1)
        			{
            			pos.add((r - startRow) * 3 + c - startCol);
            		}
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
    	for (int p = 0; p < pos.size(); p++)
    	{
    		for(int a = 0; a < all.size(); a++)
    		{
    			int ps = pos.get(p);
    			boolean now = square[startRow + (int)(ps/3)][startCol + ps%3].removePossibleValue(all.get(a));
    			if(now)
                	rem = true;
    		}
    	}
    	return rem;
    }
    
    /**
     * Checks whether the only possibilites of a number lie in one column.
     * 
     * @param square is the board data that holds all the Sudoku information
     */
    public boolean singleColumn(Square[][] square)
    {
    	boolean rem = false;
        for(int i = 1; i <= 9; i++)
        {
            ArrayList<Integer> cols = new ArrayList<Integer>();
            for(int r = startRow; r < startRow + 3; r++)
            {
                for(int c = startCol; c < startCol + 3; c++)
                {
                    if(square[r][c].isPossible(i))
                    {
                        cols.add(c);
                    }
                }
            }
            
            boolean check = true;
            for(int n = 1; n < cols.size(); n++)
            {
                if(cols.get(n - 1) != cols.get(n))
                    check = false;
            }
            
            if(check && cols.size() > 0)
            {
                boolean now = processC(square, cols.get(0), i);
                if(now)
                	rem = true;
            }
        }
        return rem;
    }
    
    /**
     * Processes since the square is only possible in one column of the region.
     * 
     * @param square is the board data that holds all the Sudoku information
     * @param cnum   the only column of the region which a value is possible
     * @param num    the number that is only possible in one column of the region
     */
    public boolean processC(Square[][] square, int cnum, int num)
    {
    	boolean rem = false;
        for(int r = 0; r < 9; r++)
        {
            if(r < startRow || r  >= startRow + 3)
            {
                boolean now = square[r][cnum].removePossibleValue(num);
                if(now)
                	rem = true;
            }
        }
        return rem;
    }
    
    /**
     * Checks whether the only possibilites of a number lie in one row.
     * 
     * @param square is the board data that holds all the Sudoku information
     */
    public boolean singleRow(Square[][] square)
    {
    	boolean rem = false;
        for(int i = 1; i <= 9; i++)
        {
            ArrayList<Integer> rows = new ArrayList<Integer>();
            for(int r = startRow; r < startRow + 3; r++)
            {
                for(int c = startCol; c < startCol + 3; c++)
                {
                    if(square[r][c].isPossible(i))
                    {
                        rows.add(r);
                    }
                }
            }
            
            boolean check = true;
            for(int n = 1; n < rows.size(); n++)
            {
                if(rows.get(n - 1) != rows.get(n))
                    check = false;
            }
            
            if(check && rows.size() > 0)
            {
            	boolean now = processR(square, rows.get(0), i);
            	if(now)
                	rem = true;
            }
        }
        return rem;
    }
    
    /**
     * Processes since the square is only possible in one row of the region.
     * 
     * @param square is the board data that holds all the Sudoku information
     * @param rnum   the only row of the region which a value is possible
     * @param num    the number that is only possible in one row of the region
     */
    public boolean processR(Square[][] square, int rnum, int num)
    {
    	boolean rem = false; 
        for(int c = 0; c < 9; c++)
        {
            if(c < startCol || c  >= startCol + 3)
            {
                boolean now = square[rnum][c].removePossibleValue(num);
                if(now)
                	rem = true;
            }
        }
        return rem;
    }
    
    public boolean nakeds(Square[][] square)
    {
    	boolean rem = false;
    	for (int r = startRow; r < startRow + 3; r++)
    	{
    		for (int c = startCol; c < startCol + 3; c++)
        	{
    			ArrayList<Integer> poss = square[r][c].returnPossible();
    			if(poss.size() != 1)
    			{
    				ArrayList<Integer> count = new ArrayList<Integer>();
    		    	count.add((r - startRow) * 3 + c - startCol);
    		    	for (int r2 = startRow; r2 < startRow + 3; r2++)
        	    	{
    		    		for (int c2 = startCol; c2 < startCol + 3; c2++)
    	        		{
    		    			ArrayList<Integer> test = square[r2][c2].returnPossible();
    		    			if(r != r2 && c != c2 && test.size() != 1 && test.size() <= poss.size())
    		    			{
    		    				boolean n = true;
    		    				for(int i = 0; i < test.size(); i++)
    		    				{
    		    					if(poss.indexOf(test.get(i)) == -1)
    		    						n = false;
    		    				}
    		    				if(n)
    		    					count.add((r2 - startRow) * 3 + c2 - startCol);
    		    			}
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
    	}  
    	return rem;
    }
    
    public boolean removeNakeds(Square[][] square, ArrayList<Integer> poss, ArrayList<Integer> count)
    {
    	boolean rem = false;
    	for (int r = startRow; r < startRow + 3; r++)
    	{
    		for (int c = startCol; c < startCol + 3; c++)
        	{
    			if (count.indexOf((r - startRow) * 3 + (c - startCol)) == -1)
    			{
    				for(int x = 0; x < poss.size(); x++)
    				{
    					if(square[r][c].getValue() == 0)
    					{
    						boolean now = square[r][c].removePossibleValue(poss.get(x));
    						if(now)
                            	rem = true;
    					}
    				}
    			}
        	}
    	}
    	return rem;
    }
}
