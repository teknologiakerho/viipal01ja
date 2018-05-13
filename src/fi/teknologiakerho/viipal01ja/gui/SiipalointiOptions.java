package fi.teknologiakerho.viipal01ja.gui;

import java.awt.GridBagLayout;
import java.util.function.Consumer;

import javax.swing.JCheckBox;
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
	
	private final JCheckBox bilateralFilterBox;
	
	private final JTextField picWidthField, picHeightField;
	private final JTextField originXField, originYField;
	private final JTextField mergeEpsilonField;

	public SiipalointiOptions() {
		setLayout(new GridBagLayout());

		cannyLowSlider = new JSlider(0, 255, 127);
		cannyHighSlider = new JSlider(0, 255, 255);
		bfSigmaColorSlider = new JSlider(0, 255, 150);
		bfSigmaSpaceSlider = new JSlider(0, 255, 150);
		epsilonSlider = new JSlider(0, 10, 5);
		bilateralFilterBox = new JCheckBox("Use bilateral filter", true);
		picWidthField = new JTextField("75", 4);
		picHeightField = new JTextField("75", 4);
		mergeEpsilonField = new JTextField("0.1", 4);
		originXField = new JTextField("37.5", 4);
		originYField = new JTextField("-40", 4);
		
		setupControls();
	}
	
	private void setupControls() {
		add(new JLabel("Canny thres (low)"), UIUtil.gridbag(0, 0, 1, 1, 1, 0));
		add(cannyLowSlider, UIUtil.gridbag(1, 0, 1, 1, 1, 0));
		
		add(new JLabel("Canny thres (high)"), UIUtil.gridbag(0, 1));
		add(cannyHighSlider, UIUtil.gridbag(1, 1));
		
		add(new JLabel("RDP epsilon"), UIUtil.gridbag(0, 2));
		add(epsilonSlider, UIUtil.gridbag(1, 2));
		
		add(bilateralFilterBox, UIUtil.gridbag(0, 4, 2, 1));

		add(new JLabel("BF sigma color"), UIUtil.gridbag(0, 5));
		add(bfSigmaColorSlider, UIUtil.gridbag(1, 5));
		
		add(new JLabel("BF sigma space"), UIUtil.gridbag(0, 6));
		add(bfSigmaSpaceSlider, UIUtil.gridbag(1, 6));
		
		add(new JLabel("Drawing size"), UIUtil.gridbag(2, 0, 1, 1, 1, 0));
		JPanel jp = new JPanel();
		jp.add(picWidthField);
		jp.add(new JLabel("x"));
		jp.add(picHeightField);
		jp.add(new JLabel("mm"));
		add(jp, UIUtil.gridbag(3, 0, 1, 1, 1, 0));
		
		add(new JLabel("Origin"), UIUtil.gridbag(2, 1));
		jp = new JPanel();
		jp.add(originXField);
		jp.add(new JLabel(", "));
		jp.add(originYField);
		jp.add(new JLabel("mm"));
		add(jp, UIUtil.gridbag(3, 1));

		add(new JLabel("Merge epsilon"), UIUtil.gridbag(2, 2));
		jp = new JPanel();
		jp.add(mergeEpsilonField);
		jp.add(new JLabel("mm"));
		add(jp, UIUtil.gridbag(3, 2));
	}
	
	public void onOptionsChanged(Consumer<SiipalointiOptions> c) {
		ChangeListener cl = e -> c.accept(this);
		cannyLowSlider.addChangeListener(cl);
		cannyHighSlider.addChangeListener(cl);
		bfSigmaColorSlider.addChangeListener(cl);
		bfSigmaSpaceSlider.addChangeListener(cl);
		epsilonSlider.addChangeListener(cl);
		bilateralFilterBox.addChangeListener(cl);
	}
	
	public int getCannyLowThres() {
		return cannyLowSlider.getValue();
	}
	
	public int getCannyHighThres() {
		return cannyHighSlider.getValue();
	}
	
	public boolean getUseBilateralFilter() {
		return bilateralFilterBox.isSelected();
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
	
	public double getPictureWidth() {
		return doubleValue(picWidthField);
	}
	
	public double getPictureHeight() {
		return doubleValue(picHeightField);
	}
	
	public double getMergeEpsilon() {
		return doubleValue(mergeEpsilonField);
	}
	
	public double getOriginX() {
		return doubleValue(originXField);
	}
	
	public double getOriginY() {
		return doubleValue(originYField);
	}
	
	private double doubleValue(JTextField field) {
		try {
			return Double.parseDouble(field.getText());
		} catch(NumberFormatException e) {
			return -1;
		}
	}
	
}
