import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author petrosky
 *
 * Minesweeper main class
 */
public class Game implements Runnable {
	public void run() {

		// Top-level frame
		final JFrame frame = new JFrame("Minesweeper");
		frame.setLocation(300, 300);
		
		// Make the mine field
		final Field field = new Field(10,15);
		frame.add(field, BorderLayout.CENTER);
		
		// Status panel
		final JPanel status_panel = new JPanel();
		frame.add(status_panel, BorderLayout.SOUTH);
		status_panel.add(field.timerLabel());
		
		// Panel for buttons
		final JPanel button_panel = new JPanel();
		frame.add(button_panel, BorderLayout.NORTH);
		
		// Reset button
		final JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (field.isPlaying() || field.hasLostOrWon()) {
					if (field.running()) {
						field.stopTimer();
					}
					field.reset();
				}
			}
		});
		button_panel.add(reset);
		
		// Instructions button
		final JButton instructions = new JButton("Instructions");
		instructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, Field.instructions, "Instructions",
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		button_panel.add(instructions);
		
		// "Help Me!" button
		final JButton help = new JButton("Help Me!");
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				field.help();
			}
		});
		button_panel.add(help);

		// Put the frame on the screen
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		// Start the game!
		field.reset();
	}

	/*
	 * Main method run to start and run the game Initializes the GUI elements
	 * specified in Game and runs it.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
}
