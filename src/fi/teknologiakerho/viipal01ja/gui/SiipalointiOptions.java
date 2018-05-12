package fi.teknologiakerho.viipal01ja.gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.function.Consumer;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;

public class SiipalointiOptions extends JComponent {
	
	private final JSlider cannyLowSlider, cannyHighSlider;
	private final JSlider bfSigmaColorSlider, bfSigmaSpaceSlider;
	private final JSlider epsilonSlider;
	
	private final JTextField picWidthField, picHeightField;
	private final JTextField mergeEpsilonField;

	public SiipalointiOptions() {
		GridLayout gl = new GridLayout(0, 2);
		gl.setHgap(20);
		gl.setVgap(0);
		setLayout(gl);

		cannyLowSlider = new JSlider(0, 255, 127);
		cannyHighSlider = new JSlider(0, 255, 255);
		bfSigmaColorSlider = new JSlider(0, 255, 150);
		bfSigmaSpaceSlider = new JSlider(0, 255, 150);
		epsilonSlider = new JSlider(0, 10, 5);
		picWidthField = new JTextField("65", 4);
		picHeightField = new JTextField("65", 4);
		mergeEpsilonField = new JTextField("1", 2);
		setupControls();
	}
	
	private void setupControls() {
		JPanel p = new JPanel(new GridLayout(0, 2));
		p.add(new JLabel("Canny low thres"));
		p.add(cannyLowSlider);
		p.add(new JLabel("Canny high thres"));
		p.add(cannyHighSlider);
		p.add(new JLabel("Epsilon"));
		p.add(epsilonSlider);
		add(p);
		
		p = new JPanel(new GridLayout(0, 2));
		p.add(new JLabel("sigma_r"));
		p.add(bfSigmaColorSlider);
		p.add(new JLabel("sigma_s"));
		p.add(bfSigmaSpaceSlider);
		add(p);
		
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p2.add(new JLabel("Size"));
		p2.add(picWidthField);
		p2.add(new JLabel("x"));
		p2.add(picHeightField);
		p2.add(new JLabel("mm"));
		p.add(p2);
		
		p2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p2.add(new JLabel("merge epsilon"));
		p2.add(mergeEpsilonField);
		p.add(p2);
	}
	
	public void onOptionsChanged(Consumer<SiipalointiOptions> c) {
		ChangeListener cl = e -> c.accept(this);
		cannyLowSlider.addChangeListener(cl);
		cannyHighSlider.addChangeListener(cl);
		bfSigmaColorSlider.addChangeListener(cl);
		bfSigmaSpaceSlider.addChangeListener(cl);
		epsilonSlider.addChangeListener(cl);
	}
	
	public int getCannyLowThres() {
		return cannyLowSlider.getValue();
	}
	
	public int getCannyHighThres() {
		return cannyHighSlider.getValue();
	}
	
	public int getBFSigmaColor() {
		return bfSigmaColorSlider.getValue();
	}
	
	public int getBFSigmaSpace() {
		return bfSigmaSpaceSlider.getValue();
	}
	
	public int getEpsilon() {
		return epsilonSlider.getValue();
	}
	
	public int getPictureWidth() {
		return intValue(picWidthField);
	}
	
	public int getPictureHeight() {
		return intValue(picHeightField);
	}
	
	public int getMergeEpsilon() {
		return intValue(mergeEpsilonField);
	}
	
	private int intValue(JTextField field) {
		try {
			return Integer.parseInt(field.getText());
		} catch(NumberFormatException e) {
			return -1;
		}
	}
	
}
