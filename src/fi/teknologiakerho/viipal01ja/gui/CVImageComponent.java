package fi.teknologiakerho.viipal01ja.gui;

import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import fi.teknologiakerho.viipal01ja.CvUtil;

public class CVImageComponent extends JComponent implements ComponentListener {
	
	private Mat image;
	private BufferedImage resImg;
	
	public CVImageComponent() {
		addComponentListener(this);
	}
	
	public void setImage(Mat image) {
		this.image = image;
		if(image != null)
			refreshImage();
		else
			resImg = null;
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		//System.out.println("repaint@"+resImg);
		g.drawImage(resImg, 0, 0, null);
	}
	
	public void refreshImage() {
		double scale = Math.min(
			((double)getWidth()) / ((double)image.width()),
			((double)getHeight()) / ((double)image.height())
		);
		
		int w = (int) (scale * image.width());
		int h = (int) (scale * image.height());

		System.out.println("rescale@" + getWidth() + "x" + getHeight() + ":"
				+ image.width() + "x" + image.height() + " --> " + w + "x" + h);
		
		if(w <= 0 || h <= 0) {
			// java why?
			return;
		}
		
		Mat tmp = new Mat(h, w, image.type());
		Imgproc.resize(image, tmp, new Size(w, h), 0, 0, Imgproc.INTER_CUBIC);
		
		resImg = CvUtil.mat2BufferedImage(tmp);
	}

	@Override
	public void componentResized(ComponentEvent e) {
		if(image != null) {
			refreshImage();
			repaint();
		}
	}

	@Override
	public void componentHidden(ComponentEvent arg0) { }

	@Override
	public void componentMoved(ComponentEvent arg0) { }

	@Override
	public void componentShown(ComponentEvent arg0) { }

}
