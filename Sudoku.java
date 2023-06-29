import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Creates a sudoku game with options make a 
 * solvable sudoku, and a solver for a sudoku.
 * 
 * @author Maria Vazhaeparambil
 * @version May 2, 2018
 */
public class Sudoku
{
	private static Color background = new JButton().getBackground();
	private static Font defaultFont = new JButton().getFont();
	
	
	public Sudoku()
	{
		
	}
	
    /**
     * Creates a sudoku game.
     * 
     * @param args array with information that may be passed at start of processing
     */
    public static void main (String[] args) throws IOException
    {
    	createGUI();
    }
    
    private static void createGUI()
	{
    	JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setPreferredSize(new Dimension(570, 500));
        //GridBagConstraints c = new GridBagConstraints();
        frame.setLayout(new BorderLayout());
        frame.pack();
        
        int buttonL = (int)(frame.getSize().getWidth() / 15);
        		
        JPanel numbers = new JPanel();
        JButton one = new JButton("1");
        one.setPreferredSize(new Dimension(buttonL, buttonL));
		numbers.add(one);
		JButton two = new JButton("2");
		two.setPreferredSize(new Dimension(buttonL, buttonL));
		numbers.add(two);
		JButton three = new JButton("3");
		three.setPreferredSize(new Dimension(buttonL, buttonL));
		numbers.add(three);
		JButton four = new JButton("4");
		four.setPreferredSize(new Dimension(buttonL, buttonL));
		numbers.add(four);
		JButton five = new JButton("5");
		five.setPreferredSize(new Dimension(buttonL, buttonL));
		numbers.add(five);
		JButton six = new JButton("6");
		six.setPreferredSize(new Dimension(buttonL, buttonL));
		numbers.add(six);
		JButton seven = new JButton("7");
		seven.setPreferredSize(new Dimension(buttonL, buttonL));
		numbers.add(seven);
		JButton eight = new JButton("8");
		eight.setPreferredSize(new Dimension(buttonL, buttonL));
		numbers.add(eight);
		JButton nine = new JButton("9");
		nine.setPreferredSize(new Dimension(buttonL, buttonL));
		numbers.add(nine);	
		JButton zero = new JButton("");
		zero.setPreferredSize(new Dimension(buttonL, buttonL));
		numbers.add(zero);	
		
		JButton[] options = {one, two, three, four, five, six, seven, eight, nine, zero};
		
        JPanel board = new JPanel(new GridLayout());
        SudokuBoard sudBoard = new SudokuBoard(options, buttonL);
        frame.add(board);
        frame.pack();
        board.add(sudBoard);
        Board temp = new Board();
        for(int rt = 0; rt < 9; rt++)
        {
        	for(int ct = 0; ct < 9; ct++)
            {
        		temp.getSquare()[rt][ct].setValue(temp.getSquare()[rt][ct].getValue());
            }
        }
        
        JPanel menu = new JPanel();
		menu.setBorder(new EmptyBorder(4, 4, 4, 4));
        menu.setLayout(new GridBagLayout());
        
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = 1;
        g.weightx = 1;
        g.fill = GridBagConstraints.HORIZONTAL;

        JButton create = new JButton("New Game");
        menu.add(create, g);
        g.gridy++;
        JButton solver = new JButton("Solve a Puzzle");
        menu.add(solver, g);
        g.gridy++;
        create.addActionListener(new ActionListener() {
       	 @Override
           public void actionPerformed(ActionEvent e) {
       		   create.setVisible(false);
       		   solver.setVisible(false);
       		   resetChange(true, sudBoard);
       		   JLabel label = new JLabel("Select your difficulty.");
       		   menu.add(label);
       		   JButton easy = new JButton("Easy");
       		   menu.add(easy, g);
       		   g.gridy++;
       		   JButton hard = new JButton("Hard");
    		   menu.add(hard, g);
    		   g.gridy++;
    		   easy.addActionListener(new ActionListener() {
               	 @Override
                   public void actionPerformed(ActionEvent e) {
               		 label.setVisible(true);
               		 label.setText("Try to solve.");
               		 playAGame(1, sudBoard, frame, menu, g, create, solver, label, options);
               		 easy.setVisible(false);
           			 frame.remove(easy);
           			 hard.setVisible(false);
           			 frame.remove(hard);
               	 }
         		   });
    		   hard.addActionListener(new ActionListener() {
    	          @Override
    	          public void actionPerformed(ActionEvent e) {
    	          	 playAGame(2, sudBoard, frame, menu, g, create, solver, label, options);
    	          	 label.setText("Try to solve.");
    	          	 easy.setVisible(false);
          			 frame.remove(easy);
          			 hard.setVisible(false);
          			 frame.remove(hard);
    	          }
    		   });
           }
       });
        
        solver.addActionListener(new ActionListener() {
          	 @Override
              public void actionPerformed(ActionEvent e) {
          		 create.setVisible(false);
          		 solver.setVisible(false);
          		 resetChange(true, sudBoard);
          		 JLabel label = new JLabel("<html>Please input all the<br>known numbers and<br> and then click 'Solve'.</html>");
          		 menu.add(label);
          		 JButton solve = new JButton("Solve");
                 menu.add(solve, g);
                 g.gridy++;
                 solve.addActionListener(new ActionListener() {
                  	 @Override
                      public void actionPerformed(ActionEvent e) {
                  		 if(sudBoard.right())
                  	     {
                  			 SudokuSolve solving = new SudokuSolve();
                  			 int[][] inputted = new int[9][9];
                  			 for(int i = 0; i < inputted.length; i++)
                  			 {
                  				 for(int j = 0; j < inputted[i].length; j++)
                  				 {
                  					 String jbut = (sudBoard.getFields()[i][j]).getText();
                  					 if(jbut != "")
                  					 {
                  						 inputted[i][j] = Integer.valueOf(jbut);
                  					 }
                  					 else
                  						 inputted[i][j] = 0;
                  				 }
                  			 }
                  			 Square[][] squares = solving.use(inputted);
                  			 updateSudoku(squares, sudBoard);
                  			 boolean np = false;
                  			 for(int r = 0; r < squares.length; r++)
                  			 {
                  				 for(int c = 0; c < squares[0].length; c++)
                     			 {
                  					 if(squares[r][c].returnPossible().size() == 0)
                     				 {
                     					 sudBoard.getFields()[r][c].setBackground(Color.RED);
                     					 sudBoard.getFields()[r][c].setOpaque(true);
                     					 np = true;
                     				 }
                     					 
                     			 } 
                  			 }
                  			 if (!np && anyEmpty(squares, sudBoard) == 0)
                  			 {
                  				 updateSudoku(squares, sudBoard);
                  				 label.setVisible(false);
                  				 frame.remove(label);
                  				 solve.setVisible(false);
                  				 frame.remove(solve);
                  			 }
                  	     }
                  	 }
                 });
                 JButton exit = new JButton("Exit");
                 menu.add(exit, g);
                 exit.addActionListener(new ActionListener() {
              		 @Override
              		 public void actionPerformed(ActionEvent e) {
              			 setNormalColor(sudBoard);
              			 reset(sudBoard, options);
              			 sudBoard.nullFlags(false);
              			 label.setVisible(false);
                    	 frame.remove(label);
              			 solve.setVisible(false);
                     	 frame.remove(solve);
              			 exit.setVisible(false);
              			 frame.remove(exit);
              			 create.setVisible(true);
              			 solver.setVisible(true);
              		 }
              	 });
              }
          });
        frame.add(menu, BorderLayout.AFTER_LINE_ENDS);
        frame.add(numbers, BorderLayout.PAGE_END);
        frame.pack();
        frame.setVisible(true);
	}
    
    public static void setAll(Board copy, SudokuBoard board, JFrame frame, JLabel label,
    		JButton checkWrong, JButton hint, JButton solve, JButton restart, JButton undo) {
  		 if(anyWrong(copy.getSquare(), board) == 0 && anyEmpty(copy.getSquare(), board) == 0)
  		 {
  			 setNormalColor(board);
  			 resetChange(false, board);
  			 label.setText("Correct!");
  			 checkWrong.setVisible(false);
			 frame.remove(checkWrong);
      		 hint.setVisible(false);
			 frame.remove(hint);
  			 solve.setVisible(false);
  			 frame.remove(solve);
  			 restart.setVisible(false);
			 frame.remove(restart);
			 undo.setVisible(false);
			 frame.remove(undo);
  		 }
  	}
    
    /**
     * Plays a game of sudoku.
     */
    public static void playAGame(int level, SudokuBoard board, JFrame frame, JPanel menu, GridBagConstraints g, JButton create, JButton solver, JLabel label, JButton[] opt)
    {
    	reset(board, opt);
        Board sudoku = new Board();
        sudoku.create();
        Board copy = new Board();
        for(int r = 0; r < 9; r++)
        {
        	for(int c = 0; c < 9; c++)
            {
        		copy.getSquare()[r][c].setValue(sudoku.getSquare()[r][c].getValue());
            }
        }
        board.setAnswer(copy.getSquare());
        if(level == 1)
        {
        	sudoku.hardE();
        }
        else
        {
        	sudoku.hard(); 
        }
        board.setFirstBoard(sudoku);
        Board temp = new Board();
        for(int r = 0; r < 9; r++)
        {
        	for(int c = 0; c < 9; c++)
            {
        		temp.getSquare()[r][c].setValue(sudoku.getSquare()[r][c].getValue());
            }
        }
        board.setFirstBoard(temp);
        
        updateSudoku(sudoku.getSquare(), board);
        boldFont(sudoku.getSquare(), board);
        setFalse(board);
        JButton checkWrong = new JButton("Find Mistakes");
		menu.add(checkWrong, g);
		g.gridy++;
		JButton hint = new JButton("Hint");
		menu.add(hint, g);
		g.gridy++;
		JButton undo = new JButton("Undo");
		menu.add(undo, g);
		g.gridy++;
		JButton restart = new JButton("Restart");
		menu.add(restart, g);
		g.gridy++;
		JButton solve = new JButton("Solve");
		menu.add(solve, g);
		g.gridy++;
		checkWrong.addActionListener(new ActionListener() {
	       	 @Override
	           public void actionPerformed(ActionEvent e) {
	       		 anyWrong(copy.getSquare(), board);
	       	 }
		});
		hint.addActionListener(new ActionListener() {
	       	 @Override
	           public void actionPerformed(ActionEvent e) {
	       		 int[] ret = hint(copy.getSquare(), board);
	       		 board.setLast(ret[0], ret[1], ret[2]);
	       		 board.checkNumbersComplete(opt);
	       		 if (board.done())
					 opt[9].setVisible(false);
	       	 }
		});
		solve.addActionListener(new ActionListener() {
	       	 @Override
	           public void actionPerformed(ActionEvent e) {
	       		updateSudoku(copy.getSquare(), board);
	       		setNormalColor(board);
	       		resetChange(false, board);
	       		label.setText("Puzzle Completed!");
	       		label.setVisible(true);
	       		checkWrong.setVisible(false);
     			frame.remove(checkWrong);
	       		hint.setVisible(false);
     			frame.remove(hint);
     			undo.setVisible(false);
				frame.remove(undo);
	       		solve.setVisible(false);
     			frame.remove(solve);
     			restart.setVisible(false);
    			frame.remove(restart);
    			for(int i = 0; i < opt.length; i++)
    	    		opt[i].setEnabled(false);
	       	 }
		});
		restart.addActionListener(new ActionListener() {
	       	 @Override
	           public void actionPerformed(ActionEvent e) {
	       		setNormalColor(board);
	       		updateSudoku(temp.getSquare(), board);
	       		board.checkNumbersComplete(opt);
	       	 }
		});
		undo.addActionListener(new ActionListener() {
	       	 @Override
	           public void actionPerformed(ActionEvent e) {
	            Board change = board.getUndo();
	       		updateSudoku(change.getSquare(), board);
	       		board.checkNumbersComplete(opt);
	       		setColorUndo(board);
	       	 }
		});
		
		board.setFlags(copy, frame, label, checkWrong, hint, solve, restart, undo);
		
		JButton exit = new JButton("Exit");
        menu.add(exit, g);
        exit.addActionListener(new ActionListener() {
     		 @Override
     		 public void actionPerformed(ActionEvent e) {
     			 reset(board, opt);
     			 setNormalColor(board);
     			 label.setVisible(false);
     			 frame.remove(label);
     			 checkWrong.setVisible(false);
    			 frame.remove(checkWrong);
    			 undo.setVisible(false);
     			 frame.remove(undo);
     			 hint.setVisible(false);
    			 frame.remove(hint);
    			 restart.setVisible(false);
     			 frame.remove(restart);
     			 solve.setVisible(false);
            	 frame.remove(solve);
     			 exit.setVisible(false);
     			 frame.remove(exit);
     			 create.setVisible(true);
     			 solver.setVisible(true);
     		 }
     	 });
    }
    public static void setFlags(Board copy, SudokuBoard board, JFrame frame, JLabel label,
    		JButton checkWrong, JButton hint, JButton solve, JButton restart, JButton undo) 
    {
  		 board.setFlags(copy, frame, label, checkWrong, hint, solve, restart, undo);
  	}
    
    public static void updateSudoku(Square[][] squares, SudokuBoard sudBoard)
    {
    	for(int r = 0; r < squares.length; r++)
        {
            for(int c = 0; c < squares[r].length; c++)
            {
            	if(squares[r][c].getValue() != 0)
            	{
            		(sudBoard.getFields()[r][c]).setText(Integer.toString(squares[r][c].getValue()));
            	}
            	else
            	{
            		(sudBoard.getFields()[r][c]).setText("");
            	}
            }
        }
    }
    
    public static void boldFont(Square[][] squares, SudokuBoard sudBoard)
    {
    	for(int r = 0; r < squares.length; r++)
        {
            for(int c = 0; c < squares[r].length; c++)
            {
            	if(squares[r][c].getValue() != 0)
            	{
            		(sudBoard.getFields()[r][c]).setText(Integer.toString(squares[r][c].getValue()));
            		Font newButtonFont = new Font((sudBoard.getFields()[r][c]).getFont().getName(), Font.BOLD, (sudBoard.getFields()[r][c]).getFont().getSize());
            		(sudBoard.getFields()[r][c]).setFont(newButtonFont);
            		(sudBoard.getFields()[r][c]).setForeground(Color.BLUE);
            	}
            }
        }
    }
    
    public static int anyWrong(Square[][] squares, SudokuBoard sudBoard)
    {
    	int count = 0;
    	for(int r = 0; r < squares.length; r++)
        {
            for(int c = 0; c < squares[r].length; c++)
            {
            	if(!(sudBoard.getFields()[r][c]).getText().equals("") && !Integer.toString(squares[r][c].getValue()).equals((sudBoard.getFields()[r][c]).getText()))
            	{
            		count++;
            		(sudBoard.getFields()[r][c]).setBackground(Color.RED);
            		(sudBoard.getFields()[r][c]).setOpaque(true);
            	}
            }
        }
    	return count;
    }
    
    public static int anyEmpty(Square[][] squares, SudokuBoard sudBoard)
    {
    	int count = 0;
    	for(int r = 0; r < squares.length; r++)
        {
            for(int c = 0; c < squares[r].length; c++)
            {
            	if((sudBoard.getFields()[r][c]).getText().equals(""))
            	{
            		count++;
            	}
            }
        }
    	return count;
    }
    
    public static int[] hint(Square[][] squares, SudokuBoard sudBoard)
    {
    	int r = (int)(Math.random() * 9);
    	int c = (int)(Math.random() * 9);
    	while(anyEmpty(squares, sudBoard) != 0 && !(sudBoard.getFields()[r][c]).getText().equals(""))
        {
    		r = (int)(Math.random() * 9);
    		c = (int)(Math.random() * 9);
        }
    	(sudBoard.getFields()[r][c]).setText(Integer.toString(squares[r][c].getValue()));
        (sudBoard.getFields()[r][c]).setBackground(Color.YELLOW);
        (sudBoard.getFields()[r][c]).setOpaque(true);
        
        int[] ret = {r, c, squares[r][c].getValue()};
        return ret;
    }
    
    public static void reset(SudokuBoard sudBoard, JButton[] opt)
    {
    	for(int a = 0; a < sudBoard.getFields().length; a++)
        {
    		for(int b = 0; b < sudBoard.getFields()[a].length; b++)
            {
    			sudBoard.getFields()[a][b].setText("");
				sudBoard.getFields()[a][b].setFont(defaultFont);
				sudBoard.getFields()[a][b].setForeground(Color.BLACK);
            }
        }
    	for(int i = 0; i < opt.length; i++)
    		opt[i].setEnabled(true);
    }
    
    public static void setFalse(SudokuBoard sudBoard)
    {
    	sudBoard.setFillFalse();
    }
    
    public static void resetChange(boolean set, SudokuBoard sudBoard)
    {
    	sudBoard.setAll(set);
    }
    
    public static void setNormalColor(SudokuBoard sudBoard)
    {
    	for(int a = 0; a < sudBoard.getFields().length; a++)
        {
    		for(int b = 0; b < sudBoard.getFields()[a].length; b++)
            {
    			sudBoard.getFields()[a][b].setBackground(background);
				sudBoard.getFields()[a][b].setOpaque(true);
            }
        }
    }    
    
    public static void setColorUndo(SudokuBoard sudBoard)
    {
    	for(int a = 0; a < sudBoard.getFields().length; a++)
        {
    		for(int b = 0; b < sudBoard.getFields()[a].length; b++)
            {
    			if(sudBoard.getFields()[a][b].getText().equals(""))
    			{
    				if(sudBoard.getFields()[a][b].getBackground() != Color.YELLOW || sudBoard.getFields()[a][b].getText().equals(""))
    					sudBoard.getFields()[a][b].setBackground(background);
    				sudBoard.getFields()[a][b].setOpaque(true);
    			}
            }
        }
    } 

}
