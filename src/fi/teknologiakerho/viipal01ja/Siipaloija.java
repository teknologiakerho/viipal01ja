package fi.teknologiakerho.viipal01ja;

import java.util.ArrayList;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import fi.teknologiakerho.viipal01ja.hcode.HCode;

public class Siipaloija {
	
	private final Mat src, filtered, dest;
	private final ArrayList<MatOfPoint> contours;
	
	public double cannyLowThres = 127, cannyHighThres = 255;
	public int cannyKernelSize = 3;
	public boolean cannyL2Gradient = false;
	
	public int bfD = 7;
	public double bfSigmaColor = 10, bfSigmaSpace = 10;
	
	public double apdpEpsilon = 5;
	
	public Siipaloija(Mat src) {
		this.src = src;
		this.filtered = new Mat();
		this.dest = new Mat(src.height(), src.width(), CvType.CV_8UC1);
		this.contours = new ArrayList<>();
	}
	
	public Mat getSourceMat() {
		return src;
	}
	
	public Mat getFilteredMat() {
		return filtered;
	}
	
	public Mat getDestMat() {
		return dest;
	}
	
	public void siipaloi() {
		Mat bw = new Mat();
		Imgproc.cvtColor(src, bw, Imgproc.COLOR_BGR2GRAY);

		System.out.printf("[Siipalointi] Bilateral Filter: d=%d, sigma_r=%f, sigma_d=%f\n",
				bfD, bfSigmaColor, bfSigmaSpace);
		Imgproc.bilateralFilter(bw, filtered, bfD, bfSigmaColor, bfSigmaSpace);

		System.out.printf("[Siipalointi] Canny: low=%f, high=%f, %dx%d kernel, L%d norm gradient\n",
				cannyLowThres, cannyHighThres, cannyKernelSize, cannyKernelSize, cannyL2Gradient ? 2 : 1);
		Imgproc.Canny(filtered, dest, cannyLowThres, cannyHighThres, cannyKernelSize, true);
		
		System.out.println("[Siipalointi] findContours");
		contours.clear();
		Mat hierarchy = new Mat();
		Imgproc.findContours(dest, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
		
		System.out.println("[Siipalointi] " + contours.size() + " contours");

		System.out.printf("[Siipalointi] Smoothing contours, epsilon=%f", apdpEpsilon);
		MatOfPoint2f mp2f = new MatOfPoint2f();
		for(int i=0;i<contours.size();i++) {
			MatOfPoint c = contours.get(i);
			c.convertTo(mp2f, CvType.CV_32FC2);
			Imgproc.approxPolyDP(mp2f, mp2f, apdpEpsilon, true);
			mp2f.convertTo(c, c.type());
		}
	}
	
	public void drawContours() {
		dest.setTo(Scalar.all(255));
		Imgproc.drawContours(dest, contours, -1, Scalar.all(0));
	}
	
	public HCode generateHCode(int width, int height, int epsilon) {
		Path p = Path.fromContours(contours);
		p.transformCoords(width, height);
		p.mergeContours(epsilon);
		return p.toHCode();
	}
	
}
