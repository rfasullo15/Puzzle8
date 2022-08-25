import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class View {
	
	private Controller c;
	private PuzzlePanel puzzlePanel;
	private Font buttonFont = new Font("Comic Sans", Font.BOLD, 40);
	private JButton shuffleButton;
	private JFrame frame;

	public View(Controller controller) {
		c = controller;
		
		frame = new JFrame("Puzzle 8");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel eastPanel = new JPanel();
		eastPanel.setBackground(Color.GRAY);
		eastPanel.setPreferredSize(new Dimension(500, 1500));
		eastPanel.setLayout(new BorderLayout());
		
		JPanel eastNorthPanel = new JPanel();
		eastNorthPanel.setBackground(Color.GRAY);
		eastNorthPanel.setPreferredSize(new Dimension(500, 500));
		
		JLabel spacingLabel = new JLabel();
		spacingLabel.setFont(new Font("Comic Sans", Font.BOLD, 300));
		spacingLabel.setText("   ");
		
		eastNorthPanel.add(spacingLabel);
		eastPanel.add(eastNorthPanel, BorderLayout.NORTH);
		
		JPanel eastCenterPanel = new JPanel();
		eastCenterPanel.setBackground(Color.GRAY);
		shuffleButton = new JButton();
		shuffleButton.setFont(buttonFont);
		shuffleButton.setText("     Shuffle!     ");
		shuffleButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				shuffle();
			}
		});
		
		eastCenterPanel.add(shuffleButton);
		eastCenterPanel.add(spacingLabel);
		eastPanel.add(eastCenterPanel, BorderLayout.CENTER);
		
//		JPanel eastSouthPanel = new JPanel();
//		eastSouthPanel.setBackground(Color.BLACK);
		JButton solveButton = new JButton();
		solveButton.setFont(buttonFont);
		solveButton.setText("     Solve!     ");
		solveButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				solve();
			}
		});
		
		eastCenterPanel.add(solveButton);
//		eastSouthPanel.add(spacingLabel);
//		eastPanel.add(eastSouthPanel, BorderLayout.SOUTH);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setPreferredSize(new Dimension(1502,1502));
		centerPanel.setBackground(Color.DARK_GRAY);		
		
		puzzlePanel = new PuzzlePanel(c);
		centerPanel.add(puzzlePanel);
		
		frame.getContentPane().add(centerPanel);
		frame.getContentPane().add(eastPanel, BorderLayout.EAST);
		frame.pack();
		frame.setVisible(true);
	}

	protected void solve() {
		c.solve();
	}

	protected void shuffle() {
		c.shuffle();
	}

	public void flashSquare(Square flashingSquare) {
		puzzlePanel.flashSquare(flashingSquare);
	}

	public void winMethod() {
		int answer = JOptionPane.showConfirmDialog(frame, "Congratulations, you won! \n Would you like to play again?", "Congratulations!!!",
				JOptionPane.YES_NO_OPTION);
		
		c.getSolveTimer().stop();
		
		if(answer == JOptionPane.YES_OPTION){
			shuffle();
			
		} else {
			System.exit(0);
		}
		
		
	}

	public void repaint() {
		puzzlePanel.repaint();
		
	}

}
