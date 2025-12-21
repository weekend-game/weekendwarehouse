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
 * "Автонумератор".
 */
@SuppressWarnings("serial")
public class AutonumbererAction extends ActFrame {

	public AutonumbererAction(MainFrame mainFrame) {
		super(mainFrame, "game.weekend.warehouse.directories.AutonumbererJournal");
		putValue(Action.NAME, Loc.get("autonumberer"));
		putValue(Action.SHORT_DESCRIPTION, Loc.get("autonumberer"));
		putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource(Acts.IMAGE_PATH + "empty.gif")));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.ALT_DOWN_MASK));
	}

}
