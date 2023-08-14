// //used to store and manipulate data within an image for 
// that pixel is used to play with rgb values

import java.awt.Color;

public class Pixel {
	private int x; //horizontal position of pixel
	private int y; //vertical position of pixel
	private Color color;
	
	public Pixel(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	public int getX() { //getter that is returns info of coordinate X
		return x;
	}

	public int getY() {
		return y;
	}

	public Color getColor() {
		return color;
	}

	public void setX(int x) { //setter that is allows you to set the value
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	
}