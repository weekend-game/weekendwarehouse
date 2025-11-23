package game.weekend.warehouse.documents;

import game.weekend.framework.core.Loc;
import game.weekend.framework.core.IntFrame;
import game.weekend.framework.core.FrameManager;

/**
 * Карточки складского учета.
 */
@SuppressWarnings("serial")
public class CardsJournal extends IntFrame {

	public CardsJournal(int id, int mode, FrameManager parentFrameMan) {
		super(id, mode, parentFrameMan);

		this.setTitle(Loc.get("warehouse_accounting_cards"));
	}

}
