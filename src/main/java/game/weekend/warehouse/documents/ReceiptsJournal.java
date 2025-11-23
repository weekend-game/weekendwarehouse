package game.weekend.warehouse.documents;

import game.weekend.framework.core.Loc;
import game.weekend.framework.core.IntFrame;
import game.weekend.framework.core.FrameManager;

/**
 * Поступления.
 */
@SuppressWarnings("serial")
public class ReceiptsJournal extends IntFrame {

	public ReceiptsJournal(int id, int mode, FrameManager parentFrameMan) {
		super(id, mode, parentFrameMan);

		this.setTitle(Loc.get("receipts"));
	}

}
