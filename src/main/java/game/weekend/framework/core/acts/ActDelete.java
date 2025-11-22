package game.weekend.framework.core.acts;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import game.weekend.framework.core.Acts;
import game.weekend.framework.core.Loc;
import game.weekend.framework.core.MainFrame;

/**
 * "Удалить...".
 */
@SuppressWarnings("serial")
public class ActDelete extends Act {

	public ActDelete(MainFrame mainFrame) {
		super(mainFrame);

		putValue(Action.NAME, Loc.get("delete") + "...");
		putValue(Action.SHORT_DESCRIPTION, Loc.get("delete_a_document_or_record"));
		putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource(Acts.IMAGE_PATH + "delete.gif")));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
	}
}
