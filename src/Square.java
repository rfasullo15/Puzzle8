import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Square {

	private final int size = 500;
	private final int speed = 10;
	private final Font font = new Font("Comic Sans", Font.BOLD, 300);

	private Color color;
	private Color original;

	private String num;  //possibly replace this with an image
	private int x;
	private int y;


	public Square(int x, int y, String num, Color c){
		this.x= x;
		this.y = y;
		this.num = num;
		this.color = c;
		this.original = c;
	}
	
	
	public Color getColor(){
		return color;
	}

	@Override
	public String toString() {
		return "Square [color=" + color + ", num=" + num + ", x=" + x + ", y=" + y + "]";
	}

	/*
	 * The square will draw itself with its associated color and number
	 */
	public void draw(Graphics g){
		g.setColor(color);
		g.fillRect(x, y, size, size);
		if(num != null){
			g.setColor(Color.BLACK);
			g.setFont(font);
			g.drawString(num, x+175, y+350);
		}
	}

	/*
	 * Flash will be called repeatedly from the controller from the ActionPreformed method when the user 
	 * clicks on a square that cannot be moved. The color of the square will alternate between white and it's original
	 * color to indicate the user has made a mistake.
	 */
	public void flash(){
		if(color == original){
			color = Color.WHITE;
		} else {
			color = original;
		}
	}

	/*
	 * move will be called repeatedly from the controller from the ActionPreformed method 
	 * when the user clicks on a square that can be moved. The square will move up, down, left or right
	 * depending on the direction specified.
	 */
	public void move(Direction d){
		switch (d){
		case UP: 
			y-=speed;
			break;
		case DOWN:
			y+=speed;
			break;
		case RIGHT:
			x+=speed;
			break;
		case LEFT:
			x-=speed;
		}
	}
	
	public boolean isInside(int mouseX, int mouseY){
		return (mouseX>x) && (mouseX< x+size) && (mouseY>y) && (mouseY< y+size);
	}

	public String getNum() {
		return num;
	}


	public void setNum(String num) {
		this.num = num;
	}


	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public int getSize() {
		return size;
	}


	public int getSpeed() {
		return speed;
	}

	public void instaMove(Direction d) {
		switch (d){
		case UP: 
			y-=size;
			break;
		case DOWN:
			y+=size;
			break;
		case RIGHT:
			x+=size;
			break;
		case LEFT:
			x-=size;
		}
		
	}



}
