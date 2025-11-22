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
 * "Поиск...".
 */
@SuppressWarnings("serial")
public class ActFind extends Act {

	public ActFind(MainFrame mainFrame) {
		super(mainFrame);

		putValue(Action.NAME, Loc.get("find") + "...");
		putValue(Action.SHORT_DESCRIPTION, Loc.get("find_a_record_by_specified_criteria"));
		putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource(Acts.IMAGE_PATH + "find.gif")));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
	}
}
