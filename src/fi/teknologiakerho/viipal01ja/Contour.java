package fi.teknologiakerho.viipal01ja;

import java.util.ArrayList;

import org.opencv.core.Point;

public class Contour {
	
	public Contour next;
	
	public ArrayList<Point> points;
	
	public Contour() {
		this(new ArrayList<>());
	}
	
	public Contour(ArrayList<Point> points) {
		this.points = points;
	}
	
	public Point first() {
		return points.get(0);
	}
	
	public Point last() {
		return points.get(points.size()-1);
	}
	
	public double dist2Undirected(Contour other) {
		Point last = last();
		return Math.min(CvUtil.distance2(last, other.first()), CvUtil.distance2(last, other.last()));
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
