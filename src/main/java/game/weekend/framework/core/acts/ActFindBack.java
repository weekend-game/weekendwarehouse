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
 * "Продолжить поиск назад".
 */
@SuppressWarnings("serial")
public class ActFindBack extends Act {

	public ActFindBack(MainFrame mainFrame) {
		super(mainFrame);

		putValue(Action.NAME, Loc.get("continue_finding_backward"));
		putValue(Action.SHORT_DESCRIPTION, Loc.get("continue_finding_backward"));
		putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource(Acts.IMAGE_PATH + "findback.gif")));
		putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
	}
}
