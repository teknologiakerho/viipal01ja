package fi.teknologiakerho.viipal01ja.gui;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Viipal01jaWindow extends JFrame {
	
	private final Viipal01jaGUI gui;
	
	private final JFileChooser fileChooser;
	
	public Viipal01jaWindow() {
		super("Viipal01ja");
		setLayout(new BorderLayout());
		setSize(800, 640);
		setResizable(false);
		add(gui = new Viipal01jaGUI(), BorderLayout.CENTER);
		fileChooser = new JFileChooser();
		setupMenu();
	}
	
	private void setupMenu() {
		JMenuBar mb = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(new JMenuItem(GUIAction.wrap("Open picture", KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK,
				e -> openFile())));
		fileMenu.add(new JMenuItem(GUIAction.wrap("Save hcode", KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK,
				e -> saveHCode())));
		mb.add(fileMenu);
		
		setJMenuBar(mb);
	}
	
	public void openFile() {
		int res = fileChooser.showOpenDialog(this);
		if(res == JFileChooser.APPROVE_OPTION)
			openFile(fileChooser.getSelectedFile());
	}
	
	public void openFile(File file) {
		gui.openFile(file);
	}
	
	public void saveHCode() {
		int res = fileChooser.showSaveDialog(this);
		if(res == JFileChooser.APPROVE_OPTION)
			saveHCode(fileChooser.getSelectedFile());
	}
	
	public void saveHCode(File file) {
		gui.saveHCode(file);
	}

}
