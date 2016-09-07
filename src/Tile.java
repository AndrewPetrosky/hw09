import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * @author petrosky
 * 
 * This class creates the tiles which make up the mine field. All tiles are initially hidden.
 *
 */
@SuppressWarnings("serial")
public class Tile extends JPanel {

	public static final int SIZE = 53; // Size of the square
	private boolean hidden; // Whether or not the tile's degree is hidden
	private final int degree; // How bombs surround the tile in question; -1 if bomb.
	private boolean flagged; // Whether or not a tile has been flagged
	
	/*
	 * Constructs a new Tile.
	 * 
	 * @param degree The degree of the new Tile.
	 */
	public Tile(int degree) {
		this.degree = degree;
		flagged = false;
		hidden = true;	
	}
	
	/*
	 * Gets the degree of the tile.
	 * 
	 * @return The degree of the Tile. Returns -2 if the Tile's is not yet revealed.
	 */
	public int getDegree() {
		return degree;
	}
	
	/*
	 * Returns whether or not the Tile is hidden.
	 * 
	 * @return Whether or not the Tile's degree is hidden.
	 */
	public boolean getState() {
		return hidden;
	}
	
	/*
	 * Changes whether or not the Tile is hidden. Once the Tile is revealed, it should remain 
	 * revealed for the duration of the game.
	 * 
	 */
	public int reveal() {
		if (hidden) {
			hidden = false;
		}
		return degree;
	}
	
	/*
	 * Returns whether or not the Tile is flagged.
	 * 
	 * @return Whether or not the Tile is flagged.
	 */
	public boolean getFlagged() {
		return flagged;
	}
	
	/*
	 * Changes whether or not the Tile is flagged.
	 * 
	 */
	public void flag() {
		flagged = !flagged;
	}
	
	/*
	 * Sets the color of the int representing the degree to be drawn.
	 * 
	 * @param g The current graphics context.
	 */
	public void setNumColor(Graphics g) {
		if (degree == 1) {g.setColor(Color.BLUE);}
		else if (degree == 2) {g.setColor(Color.GREEN);}
		else if (degree == 3) {g.setColor(Color.RED);}
		else {g.setColor(Color.BLACK);}
	}
	
	/*
	 * Provides how the Tile should be drawn in the GUI.
	 * 
	 * @param g The current graphics context.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (hidden && flagged) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0, SIZE, SIZE);
			g.setColor(Color.ORANGE);
			Polygon p = new Polygon (new int[] {17, 47, 17}, new int[] {17, 26, 44}, 3);
			g.fillPolygon(p);
			g.drawPolygon(p);
		} else if (hidden) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0, SIZE, SIZE);
		} else {
			if (degree == 0) {
				g.setColor(Color.GRAY);
				g.fillRect(0, 0, SIZE, SIZE);
			} else if (degree == -1) {
				g.setColor(Color.RED);
				g.fillRect(0, 0, SIZE, SIZE);
			} else {
				g.setColor(Color.GRAY);
				g.fillRect(0, 0, SIZE, SIZE);
				setNumColor(g);
				g.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 30));
				g.drawString(Integer.toString(degree), 17, 33);
			}
		}
		
		Border border = BorderFactory.createLineBorder(Color.black);
		super.setBorder(border);
	}
	
	/*
	 * Gets the preferred size of the Tile object.
	 * 
	 * @return The preferred Dimension of the Tile object.
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(SIZE, SIZE);
	}
	
}
