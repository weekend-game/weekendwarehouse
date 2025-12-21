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
 * "Общее..."
 */
@SuppressWarnings("serial")
public class GeneralAction extends ActFrame {

	public GeneralAction(MainFrame mainFrame) {
		super(mainFrame, "game.weekend.warehouse.directories.GeneralDoc");
		putValue(Action.NAME, Loc.get("general"));
		putValue(Action.SHORT_DESCRIPTION, Loc.get("general_settings"));
		putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource(Acts.IMAGE_PATH + "empty.gif")));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.ALT_DOWN_MASK));

	}
}
