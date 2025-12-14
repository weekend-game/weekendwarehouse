package game.weekend.framework.core.acts;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import game.weekend.framework.core.Acts;
import game.weekend.framework.core.IEditable;
import game.weekend.framework.core.Loc;
import game.weekend.framework.core.MainFrame;

/**
 * "Добавить копию...".
 */
@SuppressWarnings("serial")
public class ActAddCopy extends Act {

	public ActAddCopy(MainFrame mainFrame) {
		super(mainFrame);

		putValue(Action.NAME, Loc.get("add_copy") + "...");
		putValue(Action.SHORT_DESCRIPTION, Loc.get("add_a_copy_of_a_document_or_record"));
		putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource(Acts.IMAGE_PATH + "addcopy.gif")));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('*'));
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		IEditable frame = (IEditable) getMainFrame().getSelectedFrame();
		if (frame != null) {
			frame.addCopy();
		}
	}
}
