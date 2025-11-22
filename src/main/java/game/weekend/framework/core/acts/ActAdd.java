package game.weekend.framework.core.acts;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import game.weekend.framework.core.Acts;
import game.weekend.framework.core.Loc;
import game.weekend.framework.core.MainFrame;

/**
 * "Добавить...".
 */
@SuppressWarnings("serial")
public class ActAdd extends Act {

	public ActAdd(MainFrame mainFrame) {
		super(mainFrame);

		putValue(Action.NAME, Loc.get("add") + "...");
		putValue(Action.SHORT_DESCRIPTION, Loc.get("add_a_new_document_or_record"));
		putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource(Acts.IMAGE_PATH + "add.gif")));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('+'));
	}
}
