package game.weekend.warehouse.documents;

import game.weekend.framework.core.FrameManager;
import game.weekend.framework.core.Journal;
import game.weekend.framework.core.Loc;

/**
 * Отпуск.
 */
@SuppressWarnings("serial")
public class IssueJournal extends Journal {

	public IssueJournal(int id, int mode, FrameManager parentFrameMan) {
		super(id, mode, parentFrameMan);

		this.setTitle(Loc.get("issue"));
	}

}
