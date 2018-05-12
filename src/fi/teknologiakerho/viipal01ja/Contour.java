package fi.teknologiakerho.viipal01ja;

import java.util.ArrayList;

public class Contour {
	
	public Contour next;
	
	public ArrayList<Point2i> points;
	
	public Contour() {
		this(new ArrayList<>());
	}
	
	public Contour(ArrayList<Point2i> points) {
		this.points = points;
	}
	
	public Point2i first() {
		return points.get(0);
	}
	
	public Point2i last() {
		return points.get(points.size()-1);
	}
	
	public double dist2Undirected(Contour other) {
		Point2i last = last();
		return Math.min(last.dist2(other.first()), last.dist2(other.first()));
	}
	
	public void merge(Contour other) {
		points.addAll(other.points);
	}
	
	public void mergeReverse(Contour other) {
		points.ensureCapacity(points.size() + other.points.size());
		for(int i=other.points.size()-1;i>=0;i--) {
			points.add(other.points.get(i));
		}
	}
}
