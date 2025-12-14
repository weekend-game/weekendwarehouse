package game.weekend.framework.core.acts;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import game.weekend.framework.core.Journal;
import game.weekend.framework.core.MainFrame;

/**
 * "Обновить".
 */
@SuppressWarnings("serial")
public class ActRefresh extends Act {

	public ActRefresh(MainFrame mainFrame) {
		super(mainFrame);

		putValue(Action.NAME, "Обновить");
		putValue(Action.SHORT_DESCRIPTION, "Обновить отображаемую информацию");
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		Journal frame = (Journal) getMainFrame().getSelectedFrame();
		if (frame != null) {
			frame.refresh();
		}
	}
}
