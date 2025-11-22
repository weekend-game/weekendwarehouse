package game.weekend.warehouse;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import game.weekend.framework.core.Acts;
import game.weekend.framework.core.Loc;

/**
 * "Выход".
 */
@SuppressWarnings("serial")
public class ActExit extends AbstractAction {

	public ActExit(WeekendWarehouse app) {
		this.app = app;

		putValue(Action.NAME, Loc.get("exit"));
		putValue(Action.SHORT_DESCRIPTION, Loc.get("exiting_the_program"));
		putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource(Acts.IMAGE_PATH + "empty.gif")));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		app.close();
	}

	private WeekendWarehouse app;
}
