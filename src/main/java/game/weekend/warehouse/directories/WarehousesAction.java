package game.weekend.warehouse.directories;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import game.weekend.framework.core.Acts;
import game.weekend.framework.core.Loc;
import game.weekend.framework.core.MainFrame;
import game.weekend.framework.core.acts.ActFrame;

/**
 * "Склады".
 */
@SuppressWarnings("serial")
public class WarehousesAction extends ActFrame {

	public WarehousesAction(MainFrame mainFrame) {
		super(mainFrame, "game.weekend.warehouse.directories.WarehousesJournal");
		putValue(Action.NAME, Loc.get("warehouses"));
		putValue(Action.SHORT_DESCRIPTION, Loc.get("warehouses"));
		putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource(Acts.IMAGE_PATH + "empty.gif")));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.ALT_DOWN_MASK));
	}
}
