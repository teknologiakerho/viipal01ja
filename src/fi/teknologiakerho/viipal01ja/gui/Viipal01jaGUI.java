package fi.teknologiakerho.viipal01ja.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JComponent;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import fi.teknologiakerho.viipal01ja.IoUtil;
import fi.teknologiakerho.viipal01ja.Path;
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
		siipaloija.useBf = siOpt.getUseBilateralFilter();
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
		Path path = Path.fromContours(siipaloija.getContours());

		double w = siOpt.getPictureWidth(), h = siOpt.getPictureHeight();
		if(w != -1 && h != -1)
			path.transformCoords(w, h);
		
		double eps = siOpt.getMergeEpsilon();
		if(eps != -1)
			path.mergeContours(eps);
		
		double x = siOpt.getOriginX(), y = siOpt.getOriginY();
		if(x != -1 && y != -1)
			path.translate(-x, -y);
		
		return path.toHCode();
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
