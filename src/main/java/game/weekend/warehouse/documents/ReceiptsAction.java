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
 * "Поступления".
 */
@SuppressWarnings("serial")
public class ReceiptsAction extends ActFrame {

	public ReceiptsAction(MainFrame mainFrame) {
		super(mainFrame, "game.weekend.warehouse.documents.ReceiptsJournal");
		putValue(Action.NAME, Loc.get("receipts"));
		putValue(Action.SHORT_DESCRIPTION, Loc.get("receipt_of_goods"));
		putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource(WeekendWarehouse.IMAGE_PATH + "recipients.gif")));
		putValue(Action.ACCELERATOR_KEY,	KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.ALT_DOWN_MASK));
	}

}
