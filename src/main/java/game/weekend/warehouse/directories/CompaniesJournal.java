package game.weekend.warehouse.directories;

import game.weekend.framework.core.Loc;
import game.weekend.framework.core.IntFrame;
import game.weekend.framework.core.FrameManager;

/**
 * Организации.
 */
@SuppressWarnings("serial")
public class CompaniesJournal extends IntFrame {

	public CompaniesJournal(int id, int mode, FrameManager parentFrameMan) {
		super(id, mode, parentFrameMan);

		this.setTitle(Loc.get("companies"));
	}

}
