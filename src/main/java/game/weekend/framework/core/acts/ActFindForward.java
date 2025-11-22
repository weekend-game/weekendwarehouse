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
 * "Продолжить поиск вперёд".
 */
@SuppressWarnings("serial")
public class ActFindForward extends Act {

	public ActFindForward(MainFrame mainFrame) {
		super(mainFrame);

		putValue(Action.NAME, Loc.get("continue_finding_forward"));
		putValue(Action.SHORT_DESCRIPTION, Loc.get("continue_finding_forward"));
		putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource(Acts.IMAGE_PATH + "findforward.gif")));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK));
	}
}
