package game.weekend.warehouse.directories;

import game.weekend.framework.core.Loc;
import game.weekend.framework.core.IntFrame;
import game.weekend.framework.core.FrameManager;

/**
 * Группы товаров.
 */
@SuppressWarnings("serial")
public class ProductsJournal extends IntFrame {

	public ProductsJournal(int id, int mode, FrameManager parentFrameMan) {
		super(id, mode, parentFrameMan);

		this.setTitle(Loc.get("products"));
	}

}
