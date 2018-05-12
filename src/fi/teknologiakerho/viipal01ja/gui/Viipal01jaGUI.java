package fi.teknologiakerho.viipal01ja.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import fi.teknologiakerho.viipal01ja.IoUtil;
import fi.teknologiakerho.viipal01ja.Siipaloija;
import fi.teknologiakerho.viipal01ja.hcode.HCode;

public class Viipal01jaGUI extends JComponent {
	
	private final CVImageComponent sourceImage, previewImage;
	private final SiipalointiOptions siOpt;
	
	private Siipaloija siipaloija;
	
	public Viipal01jaGUI() {
		setLayout(new BorderLayout());
		add(new GUIOptions(this), BorderLayout.NORTH);
		add(sourceImage = new CVImageComponent(), BorderLayout.WEST);
		add(previewImage = new CVImageComponent(), BorderLayout.EAST);
		add(siOpt = new SiipalointiOptions(), BorderLayout.SOUTH);
		siOpt.onOptionsChanged(s -> updateSiipalointiOptions());
		
		sourceImage.setPreferredSize(new Dimension(400, 400));
		previewImage.setPreferredSize(new Dimension(400, 400));
	}
	
	private void updateSiipalointiOptions() {
		if(siipaloija == null)
			return;

		siipaloija.cannyLowThres = siOpt.getCannyLowThres();
		siipaloija.cannyHighThres = siOpt.getCannyHighThres();
		siipaloija.bfSigmaColor = siOpt.getBFSigmaColor();
		siipaloija.bfSigmaSpace = siOpt.getBFSigmaSpace();
		siipaloija.apdpEpsilon = siOpt.getEpsilon();
		
		siipaloija.siipaloi();
		siipaloija.drawContours();
		previewImage.refreshImage();
		previewImage.repaint();
	}
	
	public void openFile(File file) {
		Mat img = Imgcodecs.imread(file.getAbsolutePath());
		System.out.println("[V01] read " + img.width() + "x" + img.height() + "x" + img.type());
		siipaloija = new Siipaloija(img);
		sourceImage.setImage(img);
		previewImage.setImage(siipaloija.getDestMat());
		updateSiipalointiOptions();
	}
	
	public void saveHCode(File file) {
		HCode hcode = generateHCode();
		if(hcode == null)
			return;

		IoUtil.saveHCode(file, hcode);
	}
	
	public HCode generateHCode() {
		int w = siOpt.getPictureWidth(), h = siOpt.getPictureHeight();
		if(w == -1 || h == -1) {
			JOptionPane.showMessageDialog(null, "Give width and height");
			return null;
		}
		
		int eps = siOpt.getMergeEpsilon();
		if(eps == -1) {
			JOptionPane.showMessageDialog(null, "Give epsilon");
			return null;
		}

		return siipaloija.generateHCode(w, h, eps);
	}
	
	public void updatePreviewImage() {
		previewImage.refreshImage();
		previewImage.repaint();
	}
	
	public CVImageComponent getSourceImage() {
		return sourceImage;
	}
	
	public CVImageComponent getPreviewImage() {
		return previewImage;
	}
	
	public Siipaloija getSiipaloija() {
		return siipaloija;
	}

}
