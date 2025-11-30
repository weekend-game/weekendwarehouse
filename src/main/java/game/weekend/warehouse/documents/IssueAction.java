package game.weekend.warehouse.documents;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import game.weekend.framework.core.Loc;
import game.weekend.framework.core.MainFrame;
import game.weekend.framework.core.acts.ActFrame;
import game.weekend.warehouse.WeekendWarehouse;

/**
 * "Отпуск".
 */
@SuppressWarnings("serial")
public class IssueAction extends ActFrame {

	public IssueAction(MainFrame mainFrame) {
		super(mainFrame, "game.weekend.warehouse.documents.IssueJournal");
		putValue(Action.NAME, Loc.get("issue"));
		putValue(Action.SHORT_DESCRIPTION, Loc.get("issue"));
		putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource(WeekendWarehouse.IMAGE_PATH + "issue.gif")));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_3, InputEvent.ALT_DOWN_MASK));
	}

}
