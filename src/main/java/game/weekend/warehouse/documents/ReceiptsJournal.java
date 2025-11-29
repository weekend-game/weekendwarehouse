package game.weekend.warehouse.documents;

import game.weekend.framework.core.FrameManager;
import game.weekend.framework.core.Journal;
import game.weekend.framework.core.Loc;

/**
 * Поступления.
 */
@SuppressWarnings("serial")
public class ReceiptsJournal extends Journal {

	public ReceiptsJournal(int id, int mode, FrameManager parentFrameMan) {
		super(id, mode, parentFrameMan);

		this.setTitle(Loc.get("receipts"));
	}

}
