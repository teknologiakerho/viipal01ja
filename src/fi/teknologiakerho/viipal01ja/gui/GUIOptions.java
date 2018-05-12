package fi.teknologiakerho.viipal01ja.gui;

import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;

import org.opencv.core.Mat;

import fi.teknologiakerho.viipal01ja.Siipaloija;

public class GUIOptions extends JComponent {
	
	private final JComboBox<String> imgSelectBox;
	private final Viipal01jaGUI gui;
	
	public GUIOptions(Viipal01jaGUI gui) {
		this.gui = gui;
		setLayout(new FlowLayout(FlowLayout.LEFT));
		imgSelectBox = new JComboBox<>(new String[] { "Contours", "Bilateral filter" });
		setupControls();
		setupCallbacks();
	}
	
	private void setupControls() {
		add(new JLabel("Image"));
		add(imgSelectBox);
	}
	
	private void setupCallbacks() {
		imgSelectBox.addActionListener(e -> {
			Siipaloija s = gui.getSiipaloija();
			if(s != null) {
				Mat m = imgSelectBox.getSelectedIndex() == 0 ? s.getDestMat() : s.getFilteredMat();
				gui.getPreviewImage().setImage(m);
				gui.updatePreviewImage();
			}
		});
	}

}
