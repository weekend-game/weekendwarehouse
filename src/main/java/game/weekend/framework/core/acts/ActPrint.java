package game.weekend.framework.core.acts;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import game.weekend.framework.core.Acts;
import game.weekend.framework.core.Loc;
import game.weekend.framework.core.MainFrame;

/**
 * "Печать...".
 */
@SuppressWarnings("serial")
public class ActPrint extends Act {

	public ActPrint(MainFrame mainFrame) {
		super(mainFrame);

		putValue(Action.NAME, Loc.get("print") + "...");
		putValue(Action.SHORT_DESCRIPTION, Loc.get("print"));
		putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource(Acts.IMAGE_PATH + "print.gif")));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
	}
}
