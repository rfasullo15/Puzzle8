import java.awt.Color;
import java.util.ArrayList;
import java.util.Stack;

public class Model {

	Square[][] pieces;
	Square[][] winCon;
	private int size;
	private Color purple;
	


	public Model(){
		size = 500;
		purple = new Color(136, 22, 220);


		//peices gets passed around the program. These squares are what get altered
		pieces = new Square[3][3];

		pieces[0][0] = new Square(0,0, "1", Color.RED);
		pieces[0][1] = new Square(size,0, "2", Color.ORANGE);
		pieces[0][2] = new Square(2*size, 0, "3", Color.YELLOW);
		pieces[1][0] = new Square(0, size, "4", Color.GREEN);
		pieces[1][1] = new Square(size, size, "5", Color.CYAN);
		pieces[1][2] = new Square(2*size, size, "6", Color.BLUE);
		pieces[2][0] = new Square(0, 2*size, "7", purple);
		pieces[2][1] = new Square(size, 2*size, "8", Color.PINK);
		pieces[2][2] = new Square(2*size, 2*size, null, Color.GRAY);

		/*
		 * winCon is what the program checks against after every move to determine if the player has
		 * successfully solved the puzzle
		 */
		winCon = new Square[3][3];

		winCon[0][0] = new Square(0,0, "1", Color.RED);
		winCon[0][1] = new Square(size,0, "2", Color.ORANGE);
		winCon[0][2] = new Square(2*size, 0, "3", Color.YELLOW);
		winCon[1][0] = new Square(0, size, "4", Color.GREEN);
		winCon[1][1] = new Square(size, size, "5", Color.CYAN);
		winCon[1][2] = new Square(2*size, size, "6", Color.BLUE);
		winCon[2][0] = new Square(0, 2*size, "7", purple);
		winCon[2][1] = new Square(size, 2*size, "8", Color.PINK);
		winCon[2][2] = new Square(2*size, 2*size, null, Color.GRAY);

	}

	public boolean isTouchingNull(Square s){
		for(Square[] array: pieces){
			for(Square temp: array){
				if(temp.getNum() == null){
					if(s.isInside(temp.getX()-1, temp.getY() +1)){
						return true;
					} else if (s.isInside(temp.getX()+1, temp.getY() + size +1)){
						return true;
					} else if (s.isInside(temp.getX()+1, temp.getY() -1)){
						return true;
					} else if (s.isInside(temp.getX() + size + 1, temp.getY() +1)){
						return true;
					} else {
						return false;
					}
				}
			}
		}

		return false;
	}

	public Direction getDirection(Square s){
		for(Square[] array: pieces){
			for(Square temp: array){
				if(temp.getNum() == null){
					if(s.isInside(temp.getX()-1, temp.getY() +1)){
						return Direction.RIGHT;
					} else if (s.isInside(temp.getX()+1, temp.getY() + size +1)){
						return Direction.UP;
					} else if (s.isInside(temp.getX()+1, temp.getY() -1)){
						return Direction.DOWN;
					} else if (s.isInside(temp.getX() + size + 1, temp.getY() +1)){
						return Direction.LEFT;
					} else {
						return null;
					}
				}
			}

		}
		return null;
	}

	public Square[][] getPieces() {
		// TODO Auto-generated method stub
		return pieces;
	}

	public Square getNullSquare() {
		return pieces[2][2];
	}

	@Override
	public String toString() {
		return "Model [pieces=" + pieces[0][0] + "," + pieces[0][1] + ","+ pieces[0][2] + ","+ pieces[1][0] + ","+ pieces[1][1] 
				+ ","+ pieces[1][2] + ","+ pieces[2][0] + ","+ pieces[2][1] + ","+ pieces[2][2] + "]";
	}

	/**
	 * checkForWin compares every element in the pieces array to the winCon array and if any of the 
	 * X and Y values do not match, false is returned. If there are no mismatches true is returned. 
	 * @return
	 */
	public boolean checkForWin(Square[][] test) {
		for(int k = 0; k<test.length; k++){
			for(int j = 0; j<test.length; j++){
				if(!(test[k][j].getX() == winCon[k][j].getX() && test[k][j].getY() == winCon[k][j].getY())){
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * reset all of the squares so they are in their original positions
	 */

	public void reset() {

		pieces[0][0] = new Square(0,0, "1", Color.RED);
		pieces[0][1] = new Square(size,0, "2", Color.ORANGE);
		pieces[0][2] = new Square(2*size, 0, "3", Color.YELLOW);
		pieces[1][0] = new Square(0, size, "4", Color.GREEN);
		pieces[1][1] = new Square(size, size, "5", Color.CYAN);
		pieces[1][2] = new Square(2*size, size, "6", Color.BLUE);
		pieces[2][0] = new Square(0, 2*size, "7", purple);
		pieces[2][1] = new Square(size, 2*size, "8", Color.PINK);
		pieces[2][2] = new Square(2*size, 2*size, null, Color.GRAY);

	}

	public void moveSquare(Square square, Direction d) {
		if(square.getNum() == null){
			for(Square[] array: pieces){
				for(Square temp: array){
					if(temp.getNum() == null){
						temp.move(d);
					}
				}
			}
		} else {

			for(Square[] array: pieces){
				for(Square temp: array){
					if(temp.getNum() != null && temp.getNum().equals(square.getNum())){
						temp.move(d);
					}
				}
			}
		}
	}

	public void shuffleMove(Direction d) {

		for(Square[] array: pieces){
			for(Square temp: array){
				if(temp.getNum() == null){
					temp.instaMove(d);
				}
			}
		}

		Direction oppositeD = null;
		switch(d){
		case UP:
			oppositeD = Direction.DOWN;
			break;
		case DOWN:
			oppositeD = Direction.UP;
			break;
		case LEFT:
			oppositeD = Direction.RIGHT;
			break;
		case RIGHT:
			oppositeD = Direction.LEFT;
			break;
		}
		
		
		for(Square[] array: pieces){
			for(Square temp: array){
				if(temp.getNum() != null && temp.getX() ==  pieces[2][2].getX() && temp.getY() == pieces[2][2].getY()){
					temp.instaMove(oppositeD);
				}
			}
		}
		
	}
	
	public void printPieces(){
		for(Square[] array: pieces){
			for(Square temp: array){
				System.out.println(temp);
			}
		}
	}

	private boolean validMove(Direction d){
		switch(d){
			case UP:
				return pieces[2][2].getY() - size >= 0;
			case DOWN:
				return pieces[2][2].getY() + size <1500;
			case RIGHT:
				return pieces[2][2].getX() + size < 1500;
			case LEFT:
				return pieces[2][2].getX() - size >= 0;
			default:
				return false;
		}
	}
	public Stack<Direction> solve() {
		Stack<Direction> s = new Stack<Direction>();
		ArrayList<Square[][]> alreadyVisited = new ArrayList<Square[][]>();
		
		
		
		return solvePuzzle(s, pieces, alreadyVisited);
		
		
	}

	private Stack<Direction> solvePuzzle(Stack<Direction> s, Square[][] gameState, ArrayList<Square[][]> alreadyVisited) {
		Direction nextMove = null;
		
		Square[][] testCase = null;
		
		
		/*
		 * Check to see if we have won the game yet 
		 */
		if(validMove(Direction.DOWN)){
			testCase = solveMove(Direction.DOWN, gameState);
			nextMove = Direction.DOWN;
			System.out.println("Down");
			if(checkForWin(testCase)){
				s.push(nextMove);
				return s;
			} 

		} 
		if (validMove(Direction.UP)){
			testCase = solveMove(Direction.UP, gameState);
			nextMove = Direction.UP;
			System.out.println("Up");
			if(checkForWin(testCase)){
				s.push(nextMove);
				return s;
			} 

		} 
		if (validMove(Direction.RIGHT)){
			testCase = solveMove(Direction.RIGHT, gameState);
			nextMove = Direction.RIGHT;
			System.out.println("Right");
			if(checkForWin(testCase)){
				s.push(nextMove);
				return s;
			}

		} 
		
		if (validMove(Direction.LEFT)){
			testCase = solveMove(Direction.LEFT, gameState);
			nextMove = Direction.LEFT;
			System.out.println("Left");
			if(checkForWin(testCase)){
				s.push(nextMove);
				return s;
			}

		}
		
		/*
		 * If we havent won, then take the next step
		 */
		
		if(validMove(Direction.DOWN)){
			s.push(Direction.DOWN);
			testCase = solveMove(Direction.DOWN, gameState);
			alreadyVisited.add(gameState);
			return solvePuzzle(s, testCase, alreadyVisited);
		
		}
//		if (validMove(Direction.UP)){
//			s.push(Direction.UP);
//			testCase = solveMove(Direction.UP, gameState);
//			alreadyVisited.add(gameState);
//			return solvePuzzle(s, testCase, alreadyVisited);
//		} 
		
//		if (validMove(Direction.RIGHT)){
//			s.push(Direction.RIGHT);
//			testCase = solveMove(Direction.RIGHT, gameState);
//			alreadyVisited.add(gameState);
//			return solvePuzzle(s, testCase, alreadyVisited);
//		} 
		
//		if (validMove(Direction.LEFT)){
//			testCase = solveMove(Direction.LEFT, gameState);
//			s.push(Direction.LEFT);
//			alreadyVisited.add(gameState);
//			return solvePuzzle(s, testCase, alreadyVisited);
//		
//		}
		
		
		
		return s;
		
		
		
		
		
		
	}
	
	private Square[][] deepCopy(Square[][] original){
		Square[][] newState = new Square[3][3];
		newState[0][0] = new Square(original[0][0].getX(), original[0][0].getY(), original[0][0].getNum(), original[0][0].getColor());
		newState[0][1] = new Square(original[0][1].getX(), original[0][1].getY(), original[0][1].getNum(), original[0][1].getColor());
		newState[0][2] = new Square(original[0][2].getX(), original[0][2].getY(), original[0][2].getNum(), original[0][2].getColor());
		newState[1][0] = new Square(original[1][0].getX(), original[1][0].getY(), original[1][0].getNum(), original[1][0].getColor());
		newState[1][1] = new Square(original[1][1].getX(), original[1][1].getY(), original[1][1].getNum(), original[1][1].getColor());
		newState[1][2] = new Square(original[1][2].getX(), original[1][2].getY(), original[1][2].getNum(), original[1][2].getColor());
		newState[2][0] = new Square(original[2][0].getX(), original[2][0].getY(), original[2][0].getNum(), original[2][0].getColor());
		newState[2][1] = new Square(original[2][1].getX(), original[2][1].getY(), original[2][1].getNum(), original[2][1].getColor());
		newState[2][2] = new Square(original[2][2].getX(), original[2][2].getY(), original[2][2].getNum(), original[2][2].getColor());
		
		return newState;
		
	}

	private Square[][] solveMove(Direction d, Square[][] original) {
		Square[][] gameState = deepCopy(original);
		
		for(Square[] array: gameState){
			for(Square temp: array){
				if(temp.getNum() == null){
					temp.instaMove(d);
				}
			}
		}

		Direction oppositeD = null;
		switch(d){
		case UP:
			oppositeD = Direction.DOWN;
			break;
		case DOWN:
			oppositeD = Direction.UP;
			break;
		case LEFT:
			oppositeD = Direction.RIGHT;
			break;
		case RIGHT:
			oppositeD = Direction.LEFT;
			break;
		}
		
		
		for(Square[] array: gameState){
			for(Square temp: array){
				if(temp.getNum() != null && temp.getX() ==  gameState[2][2].getX() && temp.getY() == gameState[2][2].getY()){
					temp.instaMove(oppositeD);
				}
			}
		}
		
		return gameState;
		
	}

	public Square getSwitchSquare(Direction d) {
		Square[] surrounding = new Square[4];       //right, down, left, up 
		for(Square[] array: pieces){
			for(Square temp: array){
				if(isTouchingNull(temp)){
					if(pieces[2][2].isInside(temp.getX() - 1, temp.getY() +1)){             //left
						surrounding[2] = temp;
					} else if (pieces[2][2].isInside(temp.getX() + 1, temp.getY() - 1)){       //up
						surrounding[3] = temp;
					} else if (temp.isInside(pieces[2][2].getX()-1, pieces[2][2].getY() +1)){ //right
						surrounding[0] = temp;
					} else if (temp.isInside(pieces[2][2].getX() + 1, pieces[2][2].getY() - 1)){      //down
						surrounding[1] = temp;
					}
				}
			}
		}
		
		switch(d){
		case UP:
			return surrounding[1];
		case RIGHT:
			return surrounding[2];
		case DOWN: 
			return surrounding[3];
		case LEFT:
			return surrounding[0];
		}
		
		return null;
	}


}
