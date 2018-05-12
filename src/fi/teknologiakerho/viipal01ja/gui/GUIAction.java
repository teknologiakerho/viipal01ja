package fi.teknologiakerho.viipal01ja.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

public abstract class GUIAction extends AbstractAction {
	
	public GUIAction(String name) {
		super(name);
	}
	
	public GUIAction(String name, int key, int mods) {
		super(name);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(key, mods));
	}
	
	public static GUIAction wrap(String name, int key, int mods, ActionListener a) {
		return new GUIAction(name, key, mods) {
			@Override
			public void actionPerformed(ActionEvent e) {
				a.actionPerformed(e);
			}
		};
	}

}
