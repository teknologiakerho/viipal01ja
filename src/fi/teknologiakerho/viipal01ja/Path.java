package fi.teknologiakerho.viipal01ja;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;

import fi.teknologiakerho.viipal01ja.hcode.HCode;
import fi.teknologiakerho.viipal01ja.hcode.MoveCommand;
import fi.teknologiakerho.viipal01ja.hcode.MoveZCommand;

public class Path {
	
	public Contour head, tail;
	
	public void appendContour(Contour c) {
		if(head == null) {
			head = tail = c;
		}else {
			tail.next = c;
			tail = c;
		}
	}
	
	public HCode toHCode() {
		HCode ret = new HCode();
		
		for(Contour c=head;c!=null;c=c.next) {
			ret.commands.add(new MoveZCommand(true));
			Point2i p = c.first();
			ret.commands.add(new MoveCommand(p.x, p.y));
			ret.commands.add(new MoveZCommand(false));
			
			for(int i=1;i<c.points.size();i++) {
				p = c.points.get(i);
				ret.commands.add(new MoveCommand(p.x, p.y));
			}
		}
		
		System.out.println("Generated " + ret.commands.size() + " instructions");
		
		return ret;
	}
	
	// Ahne paska algoritmi
	public void mergeContours(int epsilon) {
		int e2 = epsilon*epsilon;

		// TODO tän pitäs olla joku quadtree niin tää toimis nopeesti
		HashSet<Contour> cnt = new HashSet<>();
		for(Contour c=head.next, cc;c!=null;cc=c.next,c.next=null,c=cc)
			cnt.add(c);
		
		int nc = 1;
		
		tail = head;
		while(!cnt.isEmpty()) {
			System.out.println("inner loop");
			Point2i last = tail.last();
			
			Contour c = null;
			double minDist2 = Double.MAX_VALUE;
			for(Contour cc : cnt) {
				double dist2 = Math.min(last.dist2(cc.first()), last.dist2(cc.last()));
				if(dist2 < minDist2) {
					minDist2 = dist2;
					c = cc;
				}
			}
			
			cnt.remove(c);
			if(minDist2 < e2) {
				System.out.println("Merging contours with dist: " + Math.sqrt(minDist2));
				if(minDist2 == last.dist2(c.first()))
					tail.merge(c);
				else
					tail.mergeReverse(c);
			} else {
				System.out.println("Failed to merge contour; min dist: " + Math.sqrt(minDist2));
				tail.next = c;
				tail = c;
				nc++;
			}
		}
		
		System.out.println("After merge: " + nc + " contours [epsilon=" + epsilon + "]");
	}
	
	public void transformCoords(int w, int h) {
		Point2i m = new Point2i(), M = new Point2i();
		bounds(m, M);
		System.out.printf("Transform [%d,%d]x[%d,%d] -> [0,0]x[%d,%d]\n",
				m.x, m.y, M.x, M.y, w, h);
		int rscale = Math.max(M.x-m.x, M.y-m.y);
		
		for(Contour c=head;c!=null;c=c.next) {
			for(Point2i p : c.points) {
				p.x = w * (p.x - m.x) / rscale;
				p.y = w * (p.y - m.y) / rscale; 
			}
		}
	}
	
	public void bounds(Point2i m, Point2i M) {
		m.x = Integer.MAX_VALUE;
		m.y = Integer.MAX_VALUE;
		M.x = Integer.MIN_VALUE;
		M.y = Integer.MIN_VALUE;
		
		for(Contour c=head;c!=null;c=c.next) {
			for(Point2i p : c.points) {
				if(p.x < m.x) m.x = p.x;
				if(p.y < m.y) m.y = p.y;
				if(p.x > M.x) M.x = p.x;
				if(p.y > M.y) M.y = p.y; 
			}
		}
	}
	
	public static Path fromContours(List<MatOfPoint> contours) {
		Path ret = new Path();
		
		int nc = 0, np = 0;

		for(MatOfPoint mp : contours) {
			Point[] ps = mp.toArray();
			if(ps.length == 1)
				System.out.println("Skipping length 1 contour @ (" + ps[0].x + "," + ps[0].y + ")");
			
			Contour c = new Contour(new ArrayList<>(ps.length));
			ret.appendContour(c);
			nc++;
			for(Point p : ps) {
				c.points.add(new Point2i((int)p.x, (int)p.y));
				np++;
			}
		}
		
		System.out.println(nc + " countours, " + np + " points.");

		return ret;
	}
}
