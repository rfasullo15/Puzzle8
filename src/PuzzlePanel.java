import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class PuzzlePanel extends JPanel {
	
	Square[][] pieces;

	public PuzzlePanel(Controller c) {
		
		this.setBackground(Color.GRAY);
		this.setPreferredSize(new Dimension(1500, 1500));
		pieces = c.getPieces();
		
		this.addMouseListener(c);
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		
		for(int k =pieces.length-1; k>=0; k--){
			for(int j = pieces.length-1; j>=0; j--){
				pieces[k][j].draw(g);
			}
		}
	}

	public void flashSquare(Square s) {
		s.flash();
		repaint();
		
	}

}
