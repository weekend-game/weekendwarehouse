package game.weekend.framework.core.acts;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import game.weekend.framework.core.Acts;
import game.weekend.framework.core.IEditable;
import game.weekend.framework.core.Loc;
import game.weekend.framework.core.MainFrame;

/**
 * "Исправить...".
 */
@SuppressWarnings("serial")
public class ActEdit extends Act {

	public ActEdit(MainFrame mainFrame) {
		super(mainFrame);

		putValue(Action.NAME, Loc.get("edit") + "...");
		putValue(Action.SHORT_DESCRIPTION, Loc.get("edit_a_document_or_record"));
		putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource(Acts.IMAGE_PATH + "edit.gif")));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		IEditable frame = (IEditable) getMainFrame().getSelectedFrame();
		if (frame != null) {
			frame.edit();
		}
	}
}
