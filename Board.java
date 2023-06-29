import java.util.*;
/**
 * Board class models the 9 by 9 rectangular array of squares.
 * Each square can be blank or have an integer value 1 through 9.
 * Each square allso has a list of possible values that may be used
 * for that square's value.
 * 
 * @author Maria Vazhaeparambil
 * @version May 31, 2018
 */
public class Board extends Sudoku
{
    private Square[][] square;
    private Row[] rows = {new Row(0), new Row(1), new Row(2),
                          new Row(3), new Row(4), new Row(5),
                          new Row(6), new Row(7), new Row(8)};
    private Column[] cols = {new Column(0), new Column(1), new Column(2),
                             new Column(3), new Column(4), new Column(5),
                             new Column(6), new Column(7), new Column(8)};
    private Region[] regions = {new Region(0, 0), new Region(0, 3), new Region(0, 6),
                                new Region(3, 0), new Region(3, 3), new Region(3, 6),
                                new Region(6, 0), new Region(6, 3), new Region(6, 6)};
    /**
     * Constructor for objects of class Board
     */
    public Board()
    {
        square = new Square[9][9];
        sectionsInit();
    }

    /**
     * Initiates all squares to new squares.
     */
    public void squareInit()
    {
        for(int r = 0; r < square.length; r++)
        {
            for(int c = 0; c < square[r].length; c++)
            {
                square[r][c] = new Square(r, c, 0);
            }
        }
    }

    /** 
     * Initiates all aspects of the sudoku.
     */
    public void sectionsInit()
    {
        squareInit();
        boolean check = false;
        for(int r = 0; r < square.length; r++)
        {

            rows[r] = new Row(r);
            for(int c = 0; c < square[r].length; c++)
            {
                if(!check)
                {
                    cols[c] = new Column(c);
                    if(c == 8)
                        check = true;
                }

                if(r % 3 == 0 && c % 3 == 0)
                {
                    regions[r + c / 3] = new Region(r, c);
                }
            }
        }
    }

    /**
     * Updates the sudoku based on the given value 
     * assigned to a given row and column.
     * 
     * @param row  the row the number is assigned
     * @param col  the col the number is assigned
     * @param val  the value of the number
     */
    public void updateSections(int row, int col, int val)
    {
    	square[row][col].setValue(val);
    	rows[row].update(square, square[row][col]);
    	cols[col].update(square, square[row][col]);
    	regions[(int)(row / 3) + (int)(col / 3) % 3].update(square, square[row][col]);
    }
    
    /**
     * Updates all squares of the sudoku based on the known values.
     */
    public void change()
    {
    	update();
        int track = 0;
        while (track < 6)
        {
        	if(track == 0)
        	{
        		update();
        		track++;
        	}
        	if(track == 1)
        	{
        		for(int i = 0; i < 9; i++)
                {
        			if(rows[i].check(square) == true)
        	         	track = 0;
        			else if (cols[i].check(square) == true)
        	        	track = 0;
        			else if (regions[i].check(square) == true)
        	        	track = 0;
        			else 
        				track = 2;
                }
        	}
        	if(track == 2)
        	{
        		for(int i = 0; i < 9; i++)
                {
        			if(rows[i].singleRegion(square) == true)
        	         	track = 0;
        			else if (cols[i].singleRegion(square) == true)
        				track = 0;
        			else if (regions[i].singleRow(square) == true)
        				track = 0;
        			else if (regions[i].singleColumn(square) == true)
        				track = 0;
        			else 
        				track = 3;
                }
        	}
        	if(track == 3)
        	{
        		for(int i = 0; i < 9; i++)
                {
        			if(rows[i].nakeds(square) == true)
        	         	track = 0;
        			else if (cols[i].nakeds(square) == true)
        	        	track = 0;
        			else if (regions[i].nakeds(square) == true)
        	        	track = 0;
        			else
        				track = 4;
                }
        	}
        	if(track == 4)
        	{
        		for(int i = 0; i < 9; i++)
                {
        			if(rows[i].hidden(square) == true)
        	         	track = 0;
        			else if (cols[i].hidden(square) == true)
        	        	track = 0;
        			else if (regions[i].hidden(square) == true)
        	        	track = 0;
        			else
        				track = 5;
                }
        		if(track == 5)
            	{
            		if(yWing() == true)
            			track = 0;
            		else
            			track = 6;
            	}
        	}
        }
    } 
    
    public void changeE()
    {
    	update();
        int track = 0;
        while (track < 5)
        {
        	if(track == 0)
        	{
        		update();
        		track++;
        	}
        	if(track == 1)
        	{
        		for(int i = 0; i < 9; i++)
                {
        			if(rows[i].check(square) == true)
        	         	track = 0;
        			else if (cols[i].check(square) == true)
        	        	track = 0;
        			else if (regions[i].check(square) == true)
        	        	track = 0;
        			else 
        				track = 2;
                }
        	}
        	if(track == 2)
        	{
        		for(int i = 0; i < 9; i++)
                {
        			if(rows[i].singleRegion(square) == true)
        	         	track = 0;
        			else if (cols[i].singleRegion(square) == true)
        				track = 0;
        			else if (regions[i].singleRow(square) == true)
        				track = 0;
        			else if (regions[i].singleColumn(square) == true)
        				track = 0;
        			else 
        				track = 3;
                }
        	}
        	if(track == 3)
        	{
        		for(int i = 0; i < 9; i++)
                {
        			if(rows[i].nakeds(square) == true)
        	         	track = 0;
        			else if (cols[i].nakeds(square) == true)
        	        	track = 0;
        			else if (regions[i].nakeds(square) == true)
        	        	track = 0;
        			else
        				track = 4;
                }
        	}
        	if(track == 4)
        	{
        		for(int i = 0; i < 9; i++)
                {
        			if(rows[i].hidden(square) == true)
        	         	track = 0;
        			else if (cols[i].hidden(square) == true)
        	        	track = 0;
        			else if (regions[i].hidden(square) == true)
        	        	track = 0;
        			else
        				track = 5;
                }
        	}
        }
    } 
    
    public void update()
    {
    	for(int r = 0; r < square.length; r++)
        {
            for(int c = 0; c < square[r].length; c++)
            {
                if(square[r][c].getValue() == 0)
                    square[r][c].found();
                else
                    updateSections(r, c, square[r][c].getValue());
            }    
        }
    }
    
    /**
     * Returns the contents of the sudoku.
     * 
     * @return a 2Darray acting as the sudoku
     */
    public Square[][] getSquare()
    {
        return square;
    }

    /**
     * Compares the values of this sudoku to the values of another sudoku.
     * 
     * @param template  the sudoku that this sudoku is being compared to 
     * 
     * @return true, if the values of both sudokus are the same, otherwise
     *          false
     */
    public boolean compare(Board template)
    {
        boolean ans = true;
        for(int r = 0; r < 9; r++)
        {
            for(int c = 0; c < 9; c++)
            {
                if((template.getSquare())[r][c].getValue() != (this.getSquare())[r][c].getValue())
                    ans = false;
            }   
        }
        return ans;
    }

    /**
     * Solves this sudoku.
     */
    public void solved()
    {
        Board template = new Board();
        while(!compare(template))
        {
            for(int r = 0; r < 9; r++)
            {
                for(int c = 0; c < 9; c++)
                {
                    template.updateSections(r, c, ((getSquare())[r][c]).getValue());
                    template.update();
                }
            }
            change();
        }
    }
    
    /**
     * Solves this sudoku.
     */
    public void solvedE()
    {
        Board template = new Board();
        while(!compare(template))
        {
            for(int r = 0; r < 9; r++)
            {
                for(int c = 0; c < 9; c++)
                {
                    template.updateSections(r, c, ((getSquare())[r][c]).getValue());
                    template.update();
                }
            }
            changeE();
        }
    }
    


    /**
     * Implements the YWing strategy on this sudoku.
     */
    public boolean yWing()
    {
    	boolean rem = false;
        for(int r = 0; r < 9; r++)
        {
            for(int c = 0; c < 9; c++)
            {
                if(square[r][c].getValue() == 0 && square[r][c].returnPossible().size() == 2)
                {
                    if(ycheck(r, c, square[r][c].returnPossible().get(0), 
                    		square[r][c].returnPossible().get(1)) == true)
                    	rem = true;
                }
                if(square[r][c].getValue() == 0 && square[r][c].returnPossible().size() == 2)
                {
                    if(ycheck(r, c, square[r][c].returnPossible().get(1), 
                    		square[r][c].returnPossible().get(0)) == true)
                    	rem = true;
                }
            }
        }
        return rem;
    }

    /**
     * Checks if the YWing can eliminate any values for a square given two possible values.
     * 
     * @param row   the row of the square
     * @param col   the column of the square
     * @param val1  a possible value of the square
     * @param val2  another possible value of the square
     */
    public boolean ycheck(int row, int col, int val1, int val2)
    {
    	boolean rem = false;
        ArrayList<Integer> possibleRows = new ArrayList<Integer>();
        for(int r = 0; r < 9; r++)
        {
            if(r != row && square[r][col].getValue() == 0 &&
                square[r][col].returnPossible().size() == 2 && square[r][col].isPossible(val1))
                possibleRows.add(r);
        }   
        ArrayList<Integer> possibleCols = new ArrayList<Integer>();
        for(int c = 0; c < 9; c++)
        {
            if(c != col && square[row][c].getValue() == 0 &&
                square[row][c].returnPossible().size() == 2 && square[row][c].isPossible(val2))
                possibleCols.add(c);
        }
        ArrayList<Integer> possibleRegions1 = new ArrayList<Integer>();
        int sr = ((int) (row / 3)) * 3;
        int sc = ((int) (col / 3)) * 3;
        for(int r = sr; r < sr + 3; r++)
        {
        	for(int c = sc; c < sc + 3; c++)
            {
        		if(r != row && c != col && square[r][c].getValue() == 0 &&
        			square[r][c].returnPossible().size() == 2 && square[r][c].isPossible(val1))
        			possibleRegions1.add((r - sr) * 3 + c - sc);
            }
        }
        
        ArrayList<Integer> possibleRegions2 = new ArrayList<Integer>();
        for(int r = sr; r < sr + 3; r++)
        {
        	for(int c = sc; c < sc + 3; c++)
            {
        		if(r != row && c != col && square[r][c].getValue() == 0 &&
        			square[r][c].returnPossible().size() == 2 && square[r][c].isPossible(val2))
        			possibleRegions2.add((r - sr) * 3 + c - sc);
            }
        }
        
        for(int i = 0; i < possibleRows.size(); i++)
        {
            for(int j = 0; j < possibleCols.size(); j++)
            {
                for(int x = 1; x <= 9; x++)
                {
                    if( !square[row][col].isPossible(x)
                    	&& square[possibleRows.get(i)][col].isPossible(x)
                        && square[row][possibleCols.get(j)].isPossible(x)
                        && square[possibleRows.get(i)][possibleCols.get(j)].getValue() == 0
                        && square[possibleRows.get(i)][possibleCols.get(j)].isPossible(x))
                    {
                        boolean now = square[possibleRows.get(i)][possibleCols.get(j)].removePossibleValue(x);
                        if(now)
                        	rem = true;
                    }
                }
            }
        }
        
        for(int i = 0; i < possibleRows.size(); i++)
        {
        	for(int k = 0; k < possibleRegions2.size(); k++)
            {
        		for(int x = 1; x <= 9; x++)
                {
                    if( !square[row][col].isPossible(x)
                    	&& square[possibleRows.get(i)][col].isPossible(x)
                        && square[sr + (int)possibleRegions2.get(k)/3][sc + possibleRegions2.get(k)%3].isPossible(x)
                        && square[possibleRows.get(i)][sc + possibleRegions2.get(k)%3].getValue() == 0
                        && square[possibleRows.get(i)][sc + possibleRegions2.get(k)%3].isPossible(x))
                    {
                        boolean now = square[possibleRows.get(i)][sc + possibleRegions2.get(k)%3].removePossibleValue(x); 
                        if(now)
                        	rem = true;
                    }
                }
            }
        }
        
        for(int j = 0; j < possibleCols.size(); j++)
        {
        	for(int k = 0; k < possibleRegions1.size(); k++)
            {
        		for(int x = 1; x <= 9; x++)
                {
                    if( !square[row][col].isPossible(x)
                    	&& square[row][possibleCols.get(j)].isPossible(x)
                        && square[sr + (int)possibleRegions1.get(k)/3][sc + possibleRegions1.get(k)%3].isPossible(x)
                        && square[sr + (int)possibleRegions1.get(k)/3][possibleCols.get(j)].getValue() == 0
                        && square[sr + (int)possibleRegions1.get(k)/3][possibleCols.get(j)].isPossible(x))
                    {
                        boolean now = square[sr + (int)possibleRegions1.get(k)/3][possibleCols.get(j)].removePossibleValue(x);
                        if(now)
                        	rem = true;
                    }
                }
            }
        }
        return rem;
    }

    /**
     * Creates a random sudoku solution.
     */
    public void create()
    {
        for(int r = 0; r < 9; r++)
        {
            for(int c = 0; c < 9; c++)
            {
                int rand = (int)(Math.random() * square[r][c].returnPossible().size());
                ArrayList<Integer> poss = new ArrayList<Integer>();
                for(int i = 0; i < square[r][c].returnPossible().size(); i++)
                {
                    if(i != rand)
                        poss.add(square[r][c].returnPossible().get(i));
                }
                if(square[r][c].returnPossible().size() == 0)
                {
                    sectionsInit();
                    create();
                }
                square[r][c].setValue(square[r][c].returnPossible().get(rand));
                change();
                if(!check())
                {
                    square[r][c] = new Square(r, c, 0);
                    for(int i = 0; i < poss.size(); i++)
                    {
                        square[r][c].setPossible(poss);
                    }
                }
            }
        }
        if(!right())
        {
        	sectionsInit();
        	create();
        }
    }

    /**
     * Checks whether the sudoku is correct or not.
     * 
     * @return true, if the sudoku is solved, otherwise,
     *          false
     */
    public boolean right()
    {
        boolean check = true;
        for(int i = 0; i < 9; i++)
        {
            if(!rows[i].checkRow(square) || !cols[i].checkCol(square) 
                || !regions[i].checkRegion(square))
            {
                check = false;
            }
        }
        return check;
    }

    /**
     * Checks whether any square has no solution while being solved.
     * 
     * @return true, if there are no solutions for any square in the sudoku, otherwise
     *          false
     */
    public boolean check()
    {
        for (int r = 0; r < 9; r++)
        {
            for(int c = 0; c < 9; c++)
            {
                if(square[r][c].returnPossible().size() == 0)
                    return false;
            }
        }
        return true;
    }

    /**
     * Checks whether any square is still equal to 0.
     * 
     * @return true, if no squares have a value of 0, otherwise
     *          false
     */
    public boolean see0()
    {
        for (int r = 0; r < 9; r++)
        {
            for(int c = 0; c < 9; c++)
            {
                if(square[r][c].returnPossible().size() == 0)
                    return false;
                if(square[r][c].getValue() == 0)
                    return false;
            }
        }
        return true;
    }

    /**
     * Removes every value that is not needed to solve the sudoku.
     */
    public void hard()
    {
        for(int r = 0; r < 9; r++)
        {
            for(int c = 0; c < 9; c++)
            {
                Board copy = new Board();
                Square[][] temp = copy.getSquare();
                for(int ro = 0; ro < 9; ro++)
                {
                    for(int co = 0; co < 9; co++)
                    {
                        temp[ro][co] = new Square(ro, co, square[ro][co].getValue());
                    }
                } 
                temp[r][c].reset();
                temp[8 - r][8 - c].reset();
                copy.solved();
                if(copy.see0() && unique(copy))
                {
                    square[r][c].reset();
                    square[8 - r][8 - c].reset();
                }
            }
        }

        for(int r = 0; r < 9; r++)
        {
            for(int c = 0; c < 9; c++)
            {
                Board copy = new Board();
                Square[][] temp = copy.getSquare();
                for(int ro = 0; ro < 9; ro++)
                {
                    for(int co = 0; co < 9; co++)
                    {
                        temp[ro][co] = new Square(ro, co, square[ro][co].getValue());
                    }
                } 
                temp[r][c].reset();
                copy.solved();
                if(copy.see0() && unique(copy))
                {
                	square[r][c].reset();
                }
            }
        }
    }

    /**
     * Creates a hard sudoku using the easy methods.
     */
    public void hardE()
    {
    	for(int r = 0; r < 9; r++)
        {
            for(int c = 0; c < 9; c++)
            {
                Board copy = new Board();
                Square[][] temp = copy.getSquare();
                for(int ro = 0; ro < 9; ro++)
                {
                    for(int co = 0; co < 9; co++)
                    {
                        temp[ro][co] = new Square(ro, co, square[ro][co].getValue());
                    }
                } 
                temp[r][c].reset();
                temp[8 - r][8 - c].reset();
                copy.solvedE();
                if(copy.see0() && unique(copy))
                {
                    square[r][c].reset();
                    square[8 - r][8 - c].reset();
                }
            }
        }
    }
    
    /**
     * Checks to make sure the board has one solution.
     * 
     * @param temp the Board that this sudoku is being checked with
     * 
     * @return true, if the sudoku is unique, otherwise,
     *          false
     */
    public boolean unique(Board temp)
    {
        Board template = new Board();
        for(int r = 0; r < 9; r++)
        {
            for(int c = 0; c < 9; c++)
            {
                template.updateSections(r, c, square[r][c].getValue());
                template.update();
            }
        }
        Board template2 = new Board();
        for(int r = 0; r < 9; r++)
        {
            for(int c = 0; c < 9; c++)
            {
                template2.updateSections(r, c, temp.getSquare()[r][c].getValue());
                template2.update();
            }
        }
        template.solved();
        boolean check = true;
        for(int r = 0; r < 9; r++)
        {
            for(int c = 0; c < 9; c++)
            {
                if(template.getSquare()[r][c].getValue() != template2.getSquare()[r][c].getValue())
                    check = false;
            }
        }
        return check;
    }
}
