package game.weekend.warehouse.directories;

import game.weekend.framework.core.FrameManager;
import game.weekend.framework.core.Journal;
import game.weekend.framework.core.Loc;

/**
 * Организации.
 */
@SuppressWarnings("serial")
public class CompaniesJournal extends Journal {

	public CompaniesJournal(int id, int mode, FrameManager parentFrameMan) {
		super(id, mode, parentFrameMan);

		this.setTitle(Loc.get("companies"));
	}

}
