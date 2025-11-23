package game.weekend.warehouse.documents;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import game.weekend.framework.core.Acts;
import game.weekend.framework.core.Loc;
import game.weekend.framework.core.MainFrame;
import game.weekend.framework.core.acts.SimActFrame;
import game.weekend.warehouse.WeekendWarehouse;

/**
 * "Карточки складского учета".
 */
@SuppressWarnings("serial")
public class CardsAction extends SimActFrame {

	public CardsAction(MainFrame mainFrame) {
		super(mainFrame, "game.weekend.warehouse.documents.CardsJournal");
		putValue(Action.NAME, Loc.get("warehouse_accounting_cards"));
		putValue(Action.SHORT_DESCRIPTION, Loc.get("warehouse_accounting_cards"));
		putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource(WeekendWarehouse.IMAGE_PATH + "cards.gif")));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.ALT_DOWN_MASK));
	}

}
