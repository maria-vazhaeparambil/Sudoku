import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class SudokuBoard extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int ROWS = 3;
	public static final int COLUMNS = 3;
	private int rows = 9;
	private int cols = 9;
	private JButton[][] fields;
	private Color background = new JButton().getBackground();
	private boolean changeable[][];
	ArrayList<Board> last = new ArrayList<Board>();
	ArrayList<Integer> done = new ArrayList<Integer>();
	private int[] selected;
	private Square[][] answer = null;
	private Board copy;
	private JFrame frame;
	private JLabel label;
	private JButton checkWrong;
	private JButton hint; 
	private JButton solve; 
	private JButton restart;
	private JButton undo;
	private boolean flags = false;
	
	public SudokuBoard(JButton[] opt, int buttonL) {
		
		boolean change [][] = new boolean[rows][cols];
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				change[row][col] = true;
			}
		}
		changeable = change;
		
		fields = new JButton[rows][cols];
		
		setBorder(new EmptyBorder(4, 4, 4, 4));
		setLayout(new GridLayout(ROWS, COLUMNS, 2, 2));
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLUMNS; col++) {
				JPanel board = new JPanel(new GridLayout(ROWS, COLUMNS, 3, 3));
				board.setBorder(new CompoundBorder(new LineBorder(Color.GRAY, 3), new EmptyBorder(4, 4, 4, 4)));
				for (int r = 0; r < ROWS; r++)
				{
					for (int c = 0; c < COLUMNS; c++)
					{
						int nr = row * 3 + r;
						int nc = col * 3 + c;
						JButton button = new JButton();
						button.setPreferredSize(new Dimension(buttonL, buttonL));
						button.addActionListener(new ActionListener() {
					       	 @Override
					       	 public void actionPerformed(ActionEvent e) {
					       		selected[0] = nr;
								selected[1] = nc;
					       	 }
					    });
						board.add(button);
						fields[nr][nc] = button;
						selected = new int[2];
						
					}
				}
				add(board);
			}
		}
		
		if(getDone().indexOf(1) == -1)
		{
			JButton one = opt[0];
			one.addActionListener(new ActionListener() {
  	          @Override
  	          public void actionPerformed(ActionEvent e) {
  	          	 if(selected != null)
  	          	 {
  	          		int row = selected[0];
					int col = selected[1];
					if(changeable[row][col])
					{
						setLast(row, col, 1);
						fields[selected[0]][selected[1]].setText(one.getText());
						if(answer != null && anyWrong() == 0)
							checkNumberComplete(1, opt);
						fields[row][col].setBackground(background);
						fields[row][col].setOpaque(true);
						if (done())
							opt[9].setEnabled(false);
					}
  	          	 }
  	          }
  		   });
		}
		if(getDone().indexOf(2) == -1)
		{
			JButton two = opt[1];
			two.addActionListener(new ActionListener() {
  	          @Override
  	          public void actionPerformed(ActionEvent e) {
  	          	 if(selected != null)
  	          	 {
  	          		int row = selected[0];
					int col = selected[1];
					if(changeable[row][col])
					{
						setLast(row, col, 2);
						fields[row][col].setText(two.getText());
						if(answer != null && anyWrong() == 0)
							checkNumberComplete(2, opt);
						fields[row][col].setBackground(background);
						fields[row][col].setOpaque(true);
						if (done())
							opt[9].setEnabled(false);
					}
  	          	 }
  	          }
  		   });
		}
		if(getDone().indexOf(3) == -1)
		{
			JButton three = opt[2];
			three.addActionListener(new ActionListener() {
  	          @Override
  	          public void actionPerformed(ActionEvent e) {
  	          	 if(selected != null)
  	          	 {
  	          		int row = selected[0];
					int col = selected[1];
					if(changeable[row][col])
					{
						setLast(row, col, 3);
						fields[row][col].setText(three.getText());
						if(answer != null && anyWrong() == 0)
							checkNumberComplete(3, opt);
						fields[row][col].setBackground(background);
						fields[row][col].setOpaque(true);
						if (done())
							opt[9].setEnabled(false);
					}
  	          	 }
  	          }
  		   });
		}
		if(getDone().indexOf(4) == -1)
		{
			JButton four = opt[3];
			four.addActionListener(new ActionListener() {
  	          @Override
  	          public void actionPerformed(ActionEvent e) {
  	          	 if(selected != null)
  	          	 {
  	          		int row = selected[0];
					int col = selected[1];
					if(changeable[row][col])
					{
						setLast(row, col, 4);
						fields[row][col].setText(four.getText());
						if(answer != null && anyWrong() == 0)
							checkNumberComplete(4, opt);
						fields[row][col].setBackground(background);
						fields[row][col].setOpaque(true);
						if (done())
							opt[9].setEnabled(false);
					}
  	          	 }
  	          }
  		   });
		}
		if(getDone().indexOf(5) == -1)
		{
			JButton five = opt[4];
			five.addActionListener(new ActionListener() {
  	          @Override
  	          public void actionPerformed(ActionEvent e) {
  	          	 if(selected != null)
  	          	 {
  	          		int row = selected[0];
					int col = selected[1];
					if(changeable[row][col])
					{
						setLast(row, col, 5);
						fields[row][col].setText(five.getText());
						if(answer != null && anyWrong() == 0)
							checkNumberComplete(5, opt);
						fields[row][col].setBackground(background);
						fields[row][col].setOpaque(true);
						if (done())
							opt[9].setEnabled(false);
					}
  	          	 }
  	          }
  		   });
		}
		if(getDone().indexOf(6) == -1)
		{
			JButton six = opt[5];
			six.addActionListener(new ActionListener() {
  	          @Override
  	          public void actionPerformed(ActionEvent e) {
  	          	 if(selected != null)
  	          	 {
  	          		int row = selected[0];
					int col = selected[1];
					if(changeable[row][col])
					{
						setLast(row, col, 6);
						fields[row][col].setText(six.getText());
						if(answer != null && anyWrong() == 0)
							checkNumberComplete(6, opt);
						fields[row][col].setBackground(background);
						fields[row][col].setOpaque(true);
						if (done())
							opt[9].setEnabled(false);
					}
  	          	 }
  	          }
  		   });
		}
		if(getDone().indexOf(7) == -1)
		{
			JButton seven = opt[6];
			seven.addActionListener(new ActionListener() {
  	          @Override
  	          public void actionPerformed(ActionEvent e) {
  	          	 if(selected != null)
  	          	 {
  	          		int row = selected[0];
					int col = selected[1];
					if(changeable[row][col])
					{
						setLast(row, col, 7);
						fields[row][col].setText(seven.getText());
						if(answer != null && anyWrong() == 0)
							checkNumberComplete(7, opt);
						fields[row][col].setBackground(background);
						fields[row][col].setOpaque(true);
						if (done())
							opt[9].setEnabled(false);
					}
  	          	 }
  	          }
  		   });
		}
		if(getDone().indexOf(8) == -1)
		{
			JButton eight = opt[7];
			eight.addActionListener(new ActionListener() {
  	          @Override
  	          public void actionPerformed(ActionEvent e) {
  	          	 if(selected != null)
  	          	 {
  	          		int row = selected[0];
					int col = selected[1];
					if(changeable[row][col])
					{
						setLast(row, col, 8);
						fields[row][col].setText(eight.getText());
						if(answer != null && anyWrong() == 0)
							checkNumberComplete(8, opt);
						fields[row][col].setBackground(background);
						fields[row][col].setOpaque(true);
						if (done())
							opt[9].setEnabled(false);
					}
  	          	 }
  	          }
  		   });
		}
		if(getDone().indexOf(9) == -1)
		{
			JButton nine = opt[8];
			nine.addActionListener(new ActionListener() {
  	          @Override
  	          public void actionPerformed(ActionEvent e) {
  	          	 if(selected != null)
  	          	 {
  	          		int row = selected[0];
					int col = selected[1];
					if(changeable[row][col])
					{
						setLast(row, col, 9);
						fields[row][col].setText(nine.getText());
						if(answer != null && anyWrong() == 0)
							checkNumberComplete(9, opt);
						fields[row][col].setBackground(background);
						fields[row][col].setOpaque(true);
						if (done())
							opt[9].setEnabled(false);
					}
  	          	 }
  	          }
  		   });
		}
		JButton zero = opt[9];
		zero.addActionListener(new ActionListener() {
			@Override
  	        public void actionPerformed(ActionEvent e) {
				if(selected != null)
				{
					int row = selected[0];
					int col = selected[1];
					if(changeable[row][col])
					{
						setLast(row, col, 1);
						int prev = -1;
						if(!fields[row][col].getText().equals(""))
							prev = Integer.valueOf(fields[row][col].getText());
						fields[row][col].setText(zero.getText());
						if(answer != null && anyWrong() == 0 && prev != -1)
							checkNumberComplete(prev, opt);
						fields[row][col].setBackground(background);
						fields[row][col].setOpaque(true);
					}
				}
			}	
  		 });
	}
	
	public JButton[][] getFields() 
	{
		return fields;
	}
	
	public int[] getLoc(JButton button) 
	{
		int[] loc = new int[2];
		for(int r = 0; r < rows; r++)
		{
			for(int c = 0; c < cols; c++)
			{
				if(fields[r][c] == button)
				{
					loc[0] = r;
					loc[1] = c;
				}
			}
		}
		return loc;
	}
	
	public void setAll(boolean set)
	{
		for(int r = 0; r < rows; r++)
		{
			for(int c = 0; c < cols; c++)
			{
				changeable[r][c] = set;
			}
		}
	}
	
	public void setFillFalse()
	{
		for(int r = 0; r < rows; r++)
		{
			for(int c = 0; c < cols; c++)
			{
				if(!fields[r][c].getText().equals(""))
					changeable[r][c] = false;
			}
		}
	}
	
	public void setLast(int row, int col, int val)
	{
		if(last.size() >= 1)
		{
			ArrayList<Board> next = getLast();
			Board b = next.get(next.size() - 1);
			Board temp = new Board();
			for(int r = 0; r < 9; r++)
			{
				for(int c = 0; c < 9; c++)
				{
					if(r == row && c == col)
						temp.getSquare()[row][col].setValue(val);
					else
						temp.getSquare()[r][c].setValue(b.getSquare()[r][c].getValue());
				}	
			}
			addArray(temp);
		}
	}
	
	public void setFirstBoard(Board b)
	{
		last = new ArrayList<Board>();
		Board temp = new Board();
		for(int r = 0; r < rows; r++)
        {
        	for(int c = 0; c < cols; c++)
            {
        		temp.getSquare()[r][c].setValue(b.getSquare()[r][c].getValue());
            }
        }
		last.add(temp);
	}
	
	public ArrayList<Board> getLast()
	{
		return last;
	}
	
	public void addArray(Board b)
	{
		last.add(b);
	}
	
	public Board getUndo()
	{
		if(last.size() > 1)
			last.remove(last.size() - 1);
		return last.get(last.size() - 1);
	}
	
	public ArrayList<Integer> getDone()
	{
		return done;
	}
	
	public void setDone(ArrayList<Integer> arr)
	{
		done = arr;
	}
	
	public void checkNumberComplete(int num, JButton[] opt)
    {
    	int count = 0;
    	for(int r = 0; r < answer.length; r++)
        {
    		for(int c = 0; c < answer[r].length; c++)
            {
    			if(!fields[r][c].getText().equals("") && Integer.valueOf(fields[r][c].getText()) == num)
                	count++;
            }
        }
    	if(count == 9)
    		opt[num - 1].setEnabled(false);
    	else
    		opt[num - 1].setEnabled(true);
    }
	
	public void checkNumbersComplete(JButton[] opt)
    {
		for(int num = 1; num <= 9; num++)
		{
			int count = 0;
	    	for(int r = 0; r < answer.length; r++)
	        {
	    		for(int c = 0; c < answer[r].length; c++)
	            {
	    			if(!fields[r][c].getText().equals("") && Integer.valueOf(fields[r][c].getText()) == num)
	                	count++;
	            }
	        }
	    	if(count == 9)
	    		opt[num - 1].setEnabled(false);
	    	else
	    		opt[num - 1].setEnabled(true);
		}
    }
	
	public int anyWrong()
    {
    	int count = 0;
    	for(int r = 0; r < answer.length; r++)
        {
            for(int c = 0; c < answer[r].length; c++)
            {
            	if(!(fields[r][c]).getText().equals("") && answer[r][c].getValue() != (Integer.valueOf(fields[r][c].getText())))
            	{
            		count++;
            	}
            }
        }
    	return count;
    }
	
	public int anyEmpty()
    {
    	int count = 0;
    	for(int r = 0; r < answer.length; r++)
        {
            for(int c = 0; c < answer[r].length; c++)
            {
            	if((fields[r][c]).getText().equals(""))
            	{
            		count++;
            	}
            }
        }
    	return count;
    }
	
	public void setAnswer(Square[][] squares)
    {
		answer = squares;
    }
	
	public void setFlags(Board copy, JFrame frame, JLabel label,
    		JButton checkWrong, JButton hint, JButton solve, JButton restart, JButton undo) {
		this.copy = copy;
		this.frame = frame;
		this.label = label;
		this.checkWrong = checkWrong;
		this.hint = hint;
		this.solve = solve;
		this.restart = restart;
		this.undo = undo;
		nullFlags(true);
	}
	
	public void nullFlags (boolean next)
	{
		this.flags = next;
	}
	
	public boolean done() 
	{
  		 if(answer != null && anyWrong() == 0 && anyEmpty() == 0)
  		 {
  			 setAll(false);
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
			 flags = false;
			 return true;
  		 }
  		 return false;
  	}
	
	public boolean right()
	{
		boolean ret = true;
		for(int r = 0; r < fields.length; r++)
		{
			for(int c = 0; c < fields.length; c++)
			{
				fields[r][c].setBackground(background);
			}
		}
				
		for(int r = 0; r < fields.length; r++)
		{
			ArrayList<Integer> count = new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
			for(int c = 0; c < fields.length; c++)
			{
				if(fields[r][c].getText() != "")
				{
					int num = Integer.valueOf(fields[r][c].getText());
					count.set(num - 1, count.get(num - 1) + 1);
				}
			}
			for (int i = 0; i < count.size(); i++)
			{
				if(count.get(i) > 1)
				{
					for(int c = 0; c < fields.length; c++)
					{
						if(fields[r][c].getText() != "" && Integer.valueOf(fields[r][c].getText()) == i + 1)
						{
							fields[r][c].setBackground(Color.RED);
							ret = false;
						}
					}
				}
			}
		}
		
		for(int c = 0; c < fields[0].length; c++)
		{
			ArrayList<Integer> count = new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
			for(int r = 0; r < fields.length; r++)
			{
				if(fields[r][c].getText() != "")
				{
					int num = Integer.valueOf(fields[r][c].getText());
					count.set(num - 1, count.get(num - 1) + 1);
				}
			}
			for (int i = 0; i < count.size(); i++)
			{
				if(count.get(i) > 1)
				{
					for(int r = 0; r < fields.length; r++)
					{
						if(fields[r][c].getText() != "" && Integer.valueOf(fields[r][c].getText()) == i + 1)
						{
							fields[r][c].setBackground(Color.RED);
							ret = false;
						}
					}
				}
			}
		}
		
		for (int x = 0; x < 9; x++)
		{
			ArrayList<Integer> count = new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
			for(int r = (int)(x/3) * 3; r < (int)(x/3) * 3 + 3; r++)
			{
				for(int c = (x % 3) * 3; c < (x % 3) * 3 + 3; c++)
				{
					if(fields[r][c].getText() != "")
					{
						int num = Integer.valueOf(fields[r][c].getText());
						count.set(num - 1, count.get(num - 1) + 1);
					}
				}
			}
			
			for (int i = 0; i < count.size(); i++)
			{
				if(count.get(i) > 1)
				{
					for(int r = (int)(x/3) * 3; r < (int)(x/3) * 3 + 3; r++)
					{
						for(int c = (x % 3) * 3; c < (x % 3) * 3 + 3; c++)
						{
							if(fields[r][c].getText() != "" && Integer.valueOf(fields[r][c].getText()) == i + 1)
							{
								fields[r][c].setBackground(Color.RED);
								ret = false;
							}
						}
					}
				}
			}
		}
		return ret;
	}
}