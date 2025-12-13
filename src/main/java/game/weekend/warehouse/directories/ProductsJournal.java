package game.weekend.warehouse.directories;

import game.weekend.framework.core.FrameManager;
import game.weekend.framework.core.Journal;
import game.weekend.framework.core.Loc;

/**
 * Группы товаров.
 */
@SuppressWarnings("serial")
public class ProductsJournal extends Journal {

	public ProductsJournal(int id, int mode, FrameManager parentFrameMan) {
		super(null, id, mode, parentFrameMan);

		this.setTitle(Loc.get("products"));
	}

}
