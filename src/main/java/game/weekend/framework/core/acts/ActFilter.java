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
 * "Фильтр...".
 */
@SuppressWarnings("serial")
public class ActFilter extends Act {

	public ActFilter(MainFrame mainFrame) {
		super(mainFrame);

		putValue(Action.NAME, Loc.get("filter") + "...");
		putValue(Action.SHORT_DESCRIPTION, Loc.get("set_a_filtering_condition_for_the_displayed_records"));
		putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource(Acts.IMAGE_PATH + "filter.gif")));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.ALT_DOWN_MASK));
	}
}
