package fi.teknologiakerho.viipal01ja;

public class Point2i {
	
	public int x, y;
	
	public Point2i() {
		this(0, 0);
	}
	
	public Point2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public double dist2(Point2i other) {
		return dist2(other.x, other.y);
	}
	
	public double dist2(double x, double y) {
		x -= this.x;
		y -= this.y;
		return x*x + y*y;
	}
}
