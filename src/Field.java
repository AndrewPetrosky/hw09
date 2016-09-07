import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.*;

/**
 * @author petrosky
 * 
 * This class represents the Mine Field in which the tiles are stored. This class will store a 2D
 * array of Tile instances which will be clicked on by the user to reveal its degree (the number 
 * underneath the tile) or a bomb.
 *
 */
@SuppressWarnings("serial")
public class Field extends JPanel {

	private Tile[][] field;
	private final int gridSize;
	private final int numBombs;
	private int guesses;
	private int numFlags;
	private Set<String> alreadyCleared;
	private boolean playing = false;
	private boolean lostOrWon = false;
	
	private JLabel status;
	private Timer timer;
	private int currentTime;
	public static final int INTERVAL = 1000;
	
	public static final String instructions = "Hello! Welcome to Minesweeper. This is a simple"
			+ " game\nwith one goal: clear all tiles without hitting\na bomb. There 15 bombs on"
			+ " the board. When a\ntile is revealed, it will be either red if\nit is a bomb (then"
			+ " you lose), a number\ntelling you how many bombs are adjacent to that\ntile, "
			+ "or blank if no bombs are adjacent\nto that tile. If it is blank, all other blank\n"
			+ "tiles adjacent to that tile will be cleared. You\nmay also flag a tile if you "
			+ "think it is a bomb.\n\nTo play, click a tile to reveal it. Right click a tile\nto"
			+ " flag it. Click reset at any time to\ncreate a fresh board. Click the help button "
			+ "to flag any tiles\nwhich are definitely bombs. If you reveal"
			+ " a bomb,\nyou lose! You "
			+ "win if you clear all non-bomb\ntiles. Your score (time taken to complete) will "
			+ "appear and the\nbombs will be revealed.\n\nTA's: When you reveal a tile non-adjacent"
			+ " to bombs,\nit will recursively clear all others it touches.\nThe 'Help Me!' button "
			+ "is my complex search of the\ngame state, as it searches through all revealed tiles\n"
			+ "to see if it can spot any bombs.";
	
	/*
	 * Primary constructor for Field objects.
	 */
	public Field(int gridSize, int numBombs) {
		this.gridSize = gridSize;
		this.numBombs = numBombs;
		alreadyCleared = new TreeSet<String>();
		status = new JLabel();
	}
	
	/*
	 * Constructor for JUnit tests. 
	 */
	public Field(int gridSize, int numBombs, int[] x, int[] y) {
		this.gridSize = gridSize;
		this.numBombs = numBombs;
		field = new Tile[this.gridSize][this.gridSize];
		
		for (int z = 0; z < x.length; z++) {
			field[x[z]][y[z]] = new Tile(-1);
		}
		
		prepTestTiles();
		playing = true;
	}
	
	/*
	 * Prepare tiles for the test constructor.
	 */
	private void prepTestTiles() {
		
		for (int x = 0; x < gridSize; x++) {
			for (int y = 0; y < gridSize; y++) {
				
				if (field[x][y] == null) {
					int degree = checkBomb(x,y);
					field[x][y] = new Tile(degree);
				}
			}
		}
	}
	
	/*
	 * (Re-)Sets the game state. 
	 */
	public void reset() {
		
		super.removeAll();
		
		alreadyCleared.clear();
		
		guesses = 15;
		numFlags = 0;
		currentTime = 1;
		
		status.setText("Click a tile to begin!");
		
		playing = false;
		lostOrWon = false;
		
		field = new Tile[gridSize][gridSize];
		prepTiles();
				
		timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (playing) {
					status.setText("Bombs: " + guesses + "  ~~~  Time: " + currentTime++);
				}
			}
		});

		
	}
	
	/*
	 * Prepares the tiles in the field. First, it creates the bomb tiles (randomly). Secondly, it
	 * creates the tiles without bombs, assigning their degrees as appropriate for their
	 * surroundings. Thirdly, it adds mouse listeners to each tile in the field. Finally, it adds
	 * each tile to the JPanel.
	 */
	private void prepTiles() {
		
		Random r = new Random(); 
		
		for (int i = 0; i < numBombs; i++) {
			int x = r.nextInt(gridSize);
			int y = r.nextInt(gridSize);
			
			if (field[x][y] != null) {
				i--;
			} else {
				field[x][y] = new Tile(-1);
			}
		}
		
		for (int x = 0; x < gridSize; x++) {
			for (int y = 0; y < gridSize; y++) {
				
				if (field[x][y] == null) {
					int degree = checkBomb(x,y);
					field[x][y] = new Tile(degree);
				}
				final int x1 = x;
				final int y1 = y;
			
				field[x][y].addMouseListener(new MouseListener() {
					
					@Override
					public void mouseClicked(MouseEvent e) {
						if (SwingUtilities.isRightMouseButton(e)) {
							field[x1][y1].flag();
							if (field[x1][y1].getFlagged()) {
								if (guesses-- <= 0) {
									guesses = 0;
								}
								numFlags++;
							} else {
								if (guesses++ >= 15) {
									guesses = 15;
								}
								if (numFlags >= 16) {
									guesses = 0;
								}
								numFlags--;
							}
							
							if (!playing && !lostOrWon) {
								timer.start();
								playing = true;
								status.setText("Bombs: " + guesses + "  ~~~  Time: " + 0);
							}
							
						} else {
							if (!field[x1][y1].getFlagged()) {
								int x = field[x1][y1].reveal();
								if (!playing && !lostOrWon) {
									timer.start();
									playing = true;
									status.setText("Bombs: " + guesses + "  ~~~  Time: " + 0);
								}
								if (x == -1) {
									status.setText("You Lost!");
									revealBombs();
									timer.stop();
									playing = false;
									lostOrWon = true;
								} else if (tilesLeft() == 15 && playing) {
									timer.stop();
									status.setText("You Won! Your score was " + currentTime
											+ " seconds.");
									help();
									playing = false;
									lostOrWon = true;
								} else if (x == 0) {
									clear(x1,y1);
								}
							}
						}
					}

					@Override
					public void mousePressed(MouseEvent e) {/* Do nothing */}
					@Override
					public void mouseReleased(MouseEvent e) {/* Do nothing */}
					@Override
					public void mouseEntered(MouseEvent e) {/* Do nothing */}
					@Override
					public void mouseExited(MouseEvent e) {/* Do nothing */}
				});
			
			super.add(field[x][y]);	
			}
		}
	}
	
	
	/*
	 * Clears the area around tiles with no bombs around it.
	 * 
	 * 
	 * @param x The column of the tile in question.
	 * @param y The row of the tile in question.
	 */
	private void clear(int x, int y) {
		alreadyCleared.add("Tile: " + x + " " + y);
		
		for (int x1 = x - 1; x1 <= x + 1; x1++) {
    		for (int y1 = y - 1; y1 <= y + 1; y1++) {
    			if ((x1 >= 0 && x1 < gridSize) && (y1 >= 0 && y1 < gridSize)) {
    				int z = field[x1][y1].reveal();
    				if (z == 0 && !alreadyCleared.contains("Tile: " + x1 + " " + y1)) {
    					clear(x1,y1);
    				}
    			}
    		}
    	}
		
	}
	
	/*
	 * Helps the user by identifying any bombs, using only the information the user has (i.e. this
	 * does not cheat for the player).
	 * 
	 * 
	 */
	public void help() {
		
		//If playing, locate revealed tiles with bombs around them. Label bombs, if possible.
		if (playing) {
			for (int x = 0; x < gridSize; x++) {
				for (int y = 0; y < gridSize; y++) {
					if (!field[x][y].getState() && field[x][y].getDegree() != 0) {
						labelBombs(x, y, field[x][y].getDegree());
					}
				}
			}
			
		}
		
	}
	
	/*
	 * Helper for help. Determines if the tile in question has a bomb around it, and flags the bombs
	 * around it.
	 * 
	 * @param x The column of the tile in question.
	 * @param y The row of the tile in question.
	 * @param d The degree of the tile in question.
	 */
	private void labelBombs(int x, int y, int d) {
		
		//Create an array to store hidden tiles around the tile in question
		Tile[] u = new Tile[8];
		//Variable to keep track of the elements added to u
		int i = 0;
		
		//Search around tile in question, add any hidden tiles to u and increment i as added
		for (int x1 = x - 1; x1 <= x + 1; x1++) {
    		for (int y1 = y - 1; y1 <= y + 1; y1++) {
    			if ((x1 >= 0 && x1 < gridSize) && (y1 >= 0 && y1 < gridSize)) {
    				if (field[x1][y1].getState()) {
    					u[i] = field[x1][y1];
    					i++;
    				}
    			}
    		}
    	}
		
		//If the number of elements in u matches the degree, label (flag) the elements of u as bombs
		if (i == d) {
			for (int z = 0; z < i; z++) {
				if (!u[z].getFlagged()) {
					u[z].flag();
					numFlags++;
					guesses--;
				}
			}
		}

	}
	
	
	
	/*
	 * Returns number of hidden tiles
	 * 
	 * @return The number of hidden tiles left on the board
	 */
	private int tilesLeft() {
		int z = 0;
		
		for (int x = 0; x < gridSize; x++) {
			for (int y = 0; y < gridSize; y++) {
				if (field[x][y].getState()) {
					z++;
				}
			}
		}
		
		return z;
	}

	/*
	 * Returns the JLabel for the timer
	 * 
	 * @return A JLabel for the timer.
	 */
	public JLabel timerLabel() {
		return status;
	}
	
	/*
	 * Checks the surrounding 8 (or less) tiles for a bomb.
	 * 
	 * @return The number of bombs surrounding the specified tile.
	 */
	private int checkBomb(int x, int y) {
		int result = 0;
		
		for (int x1 = x - 1; x1 <= x + 1; x1++) {
    		for (int y1 = y - 1; y1 <= y + 1; y1++) {
    			if ((x1 >= 0 && x1 < gridSize) && (y1 >= 0 && y1 < gridSize)) {
    				if (!(field[x1][y1] == null) && field[x1][y1].getDegree() == -1) {
    					result++;
    				}
    			}
    		}
    	}
		
		return result;
	}
	
	/*
	 * Reveals all bombs
	 */
	public void revealBombs() {
		for (int x = 0; x < gridSize; x++) {
			for (int y = 0; y < gridSize; y++) {
				if (field[x][y].getDegree() == -1) {
					field[x][y].reveal();
				}
			}
		}
	}
	
	/*
	 * Flags all bombs
	 */
	public void flagBombs() {
		for (int x = 0; x < gridSize; x++) {
			for (int y = 0; y < gridSize; y++) {
				if (field[x][y].getDegree() == -1) {
					field[x][y].flag();
				}
			}
		}
	}
	
	/*
	 * Get the current time
	 * 
	 * @return The current time in the game
	 */
	public int getTime() {
		return currentTime;
	}
	
	/*
	 * Stops the timer
	 */
	public void stopTimer() {
		if (timer.isRunning()) {
			timer.stop();
		}
	}
	
	/*
	 * Returns whether the timer is running
	 * 
	 * @return Whether or not the timer is running
	 */
	public boolean running() {
		return timer.isRunning();
	}
	
	/*
	 * Returns whether or not the user is playing
	 * 
	 * @return Whether or not the user is playing
	 */
	public boolean isPlaying() {
		return playing;
	}
	
	/*
	 * Returns whether or not the user has lostOrWon
	 * 
	 * @return Whether or not the user is playing
	 */
	public boolean hasLostOrWon() {
		return lostOrWon;
	}
	
	/*
	 * Returns the field instance variable; for testing purposes ONLY.
	 */
	public Tile[][] testTiles() {
		return field;
	}
	
	/*
	 * Sets whether the game has started; for testing purposes ONLY.
	 */
	public void changePlay() {
		playing = !playing;
	}
	
	/*
	 * Draw the field
	 * 
	 * @param g The current graphics context.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		super.setLayout(new GridLayout(gridSize,gridSize));
	}
	
	/*
	 * Gets the preferred size of Field object.
	 * 
	 * @return The preferred Dimension of the Field object.
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(gridSize*Tile.SIZE, gridSize*Tile.SIZE);
	}
}
