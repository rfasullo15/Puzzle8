import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.Stack;

import javax.swing.Timer;

public class Controller implements MouseListener, ActionListener {
	private Model m;
	private View v;
	private Timer t;
	private Square flashingSquare;
	private Square movingSquare;
	private Square nullSquare;
	private Direction movementDirection;
	private int count =0;
	//	private Direction solveDirection;
	private Square switchSquare;
	private Timer solveTimer = new Timer(100, null);



	public Timer getSolveTimer() {
		return solveTimer;
	}



	public void setSolveTimer(Timer solveTimer) {
		this.solveTimer = solveTimer;
	}



	public Controller(){
		m = new Model();
		v = new View(this);
		t = new Timer(25, this);
		nullSquare = m.getNullSquare();
	}



	@Override
	public void actionPerformed(ActionEvent e) {

		if(flashingSquare != null && count <=35){
			if(count%7 ==0){
				v.flashSquare(flashingSquare);
			}
			count ++;
			if(count == 36){
				flashingSquare = null;
				t.stop();
			}
		}
		if(movingSquare != null && count <50){
			m.moveSquare(nullSquare, getNullDirection());
			m.moveSquare(movingSquare, movementDirection);

			v.repaint();


			count ++;
			if(count == 50){
				movingSquare = null;
				t.stop();
				if(m.checkForWin(m.getPieces())){
					v.winMethod();
				}

			}
		} 
		//		if(solveDirection != null && count <50){
		//			m.moveSquare(nullSquare, solveDirection);
		//			m.moveSquare(switchSquare, getSwitchSquareDirection());
		//			
		//			v.repaint();
		//			
		//			count++;
		//			if(count == 50){
		//				t.stop();
		//				if(m.checkForWin(m.getPieces())){
		//					v.winMethod();
		//				}
		//			}
		//		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(!t.isRunning()){
			Square[][] pieces = getPieces();
			for (Square[] array: pieces){
				for(Square s: array){
					if(s.isInside(e.getX(), e.getY())){
						if(s == nullSquare){
							//do nothing
						} else if(!m.isTouchingNull(s)){
							flashingSquare = s;
							count = 0;
							t.start();
						} else if (m.isTouchingNull(s)){
							movingSquare = s;
							movementDirection = m.getDirection(s);
							count =0;
							t.start();
						} 
					}
				}
			}
		}

	}

	/*
	 * The shuffle method will shuffle the order of the squares through a series 
	 * of random moves. The user will see the squares briefly all fade to gray and 
	 * then come back randomized. There will be 20 randomized moves.
	 */
	public void shuffle() {
		System.out.println("CHANGE PLACES!!!!");

		m.reset();

		Random rand = new Random();
		int lastMove = -1;
		boolean moveComplete;
		for(int k =0; k<20; k++){
			moveComplete = false;
			nullSquare = m.getNullSquare();

			while (!moveComplete) {

				int num = rand.nextInt(4);

				while((lastMove == 0 && num ==1) || (lastMove == 1 && num == 0) || (lastMove == 2 && num ==3) || (lastMove == 3 && num == 2)){
					num = rand.nextInt(4);
				}

				if(num == 0 && nullSquare.getX() + nullSquare.getSize() < 1500){                 //go right immediately if you can
					m.shuffleMove(Direction.RIGHT);
					lastMove = 0;
					moveComplete = true;
				} else if (num == 1 && nullSquare.getX() - nullSquare.getSize()>= 0){                 //go left immediately if you can
					m.shuffleMove(Direction.LEFT);
					lastMove = 1;
					moveComplete = true;
				} else if (num == 2 && nullSquare.getY() + nullSquare.getSize() < 1500){             //go down immediately if you can
					m.shuffleMove(Direction.DOWN);
					lastMove = 2;
					moveComplete = true;
				} else if (num == 3 && nullSquare.getY() - nullSquare.getSize() >= 0){                 //go up immediately if you can
					m.shuffleMove(Direction.UP);
					lastMove = 3;
					moveComplete = true;
				}
			}
		}

		v.repaint();

	}

	/*
	 * The solve method will use an AI solving algorithm to systematically solve the puzzle
	 * from its current state. The user will see the squares slide around the board until 
	 * the win state is reached. 
	 */
	public void solve() {
		//		t.start();


		Stack<Direction> s = m.solve();
		
		

		solveTimer.addActionListener(new ActionListener(){
			Direction solveDirection = null;
			Square switchSquare = null;
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Solving...");
				if(m.getNullSquare().getX() % 500 == 0 && m.getNullSquare().getY() % 500 == 0){
					if(!s.isEmpty()){
						solveDirection = s.pop();
						switchSquare = m.getSwitchSquare(solveDirection);
						System.out.println(solveDirection);
					} else {
						solveDirection = null;
					}
				}
				if(solveDirection != null){
					m.moveSquare(m.getNullSquare(), solveDirection);
					m.moveSquare(switchSquare, getSwitchSquareDirection(solveDirection));
					v.repaint();
				} else {
					if (m.checkForWin(m.getPieces())){
						v.winMethod();
					}
				}

			}
		});
		solveTimer.start();

	}

	private Direction getSwitchSquareDirection(Direction solveDirection){
		switch(solveDirection){
		case UP:
			return Direction.DOWN;
		case DOWN:
			return Direction.UP;
		case RIGHT:
			return Direction.LEFT;
		case LEFT:
			return Direction.RIGHT;
		}
		return null;
	}


	private Direction getNullDirection(){
		switch(movementDirection){
		case UP:
			return Direction.DOWN;
		case DOWN:
			return Direction.UP;
		case RIGHT:
			return Direction.LEFT;
		case LEFT:
			return Direction.RIGHT;
		}
		return null;
	}

	public Square[][] getPieces() {
		// TODO Auto-generated method stub
		return m.getPieces();
	}

	@Override
	public String toString() {
		return "Controller [nullSquare=" + nullSquare + "]";
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}



}
