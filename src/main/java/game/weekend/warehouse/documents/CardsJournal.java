package game.weekend.warehouse.documents;

import game.weekend.framework.core.FrameManager;
import game.weekend.framework.core.Journal;
import game.weekend.framework.core.Loc;

/**
 * Карточки складского учета.
 */
@SuppressWarnings("serial")
public class CardsJournal extends Journal {

	public CardsJournal(int id, int mode, FrameManager parentFrameMan) {
		super(null, id, mode, parentFrameMan);

		this.setTitle(Loc.get("warehouse_accounting_cards"));
	}

}
