package fi.teknologiakerho.viipal01ja;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.opencv.core.Core;
import org.opencv.core.Mat;

public class CvUtil {
	
	public static BufferedImage mat2BufferedImage(Mat m) {
		int type = m.channels() == 1 ? BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_3BYTE_BGR;
		BufferedImage ret = new BufferedImage(m.width(), m.height(), type);
		m.get(0, 0, ((DataBufferByte) ret.getRaster().getDataBuffer()).getData());
		return ret;
	}
	
	public static void loadLibs() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
}
