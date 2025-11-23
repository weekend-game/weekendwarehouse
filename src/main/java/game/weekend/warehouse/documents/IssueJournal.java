package game.weekend.warehouse.documents;

import game.weekend.framework.core.Loc;
import game.weekend.framework.core.IntFrame;
import game.weekend.framework.core.FrameManager;

/**
 * Отпуск.
 */
@SuppressWarnings("serial")
public class IssueJournal extends IntFrame {

	public IssueJournal(int id, int mode, FrameManager parentFrameMan) {
		super(id, mode, parentFrameMan);

		this.setTitle(Loc.get("issue"));
	}

}
