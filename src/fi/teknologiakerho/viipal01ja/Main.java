package fi.teknologiakerho.viipal01ja;

import java.io.File;

import fi.teknologiakerho.viipal01ja.gui.Viipal01jaWindow;

public class Main {
	
	public static void main(String[] args) {
		CvUtil.loadLibs();

		Viipal01jaWindow w = new Viipal01jaWindow();
		w.setDefaultCloseOperation(Viipal01jaWindow.EXIT_ON_CLOSE);
		w.setLocationRelativeTo(null);
		w.setVisible(true);
		
		if(args.length > 0) {
			w.openFile(new File(args[0]));
		}
	}

}
