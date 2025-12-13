package game.weekend.warehouse.documents;

import game.weekend.framework.core.FrameManager;
import game.weekend.framework.core.Journal;

/**
 * Поступления.
 */
@SuppressWarnings("serial")
public class ReceiptsJournal extends Journal {

	public ReceiptsJournal(int id, int mode, FrameManager parentFrameMan) {
		super("receipts", id, mode, parentFrameMan);
	}

}
