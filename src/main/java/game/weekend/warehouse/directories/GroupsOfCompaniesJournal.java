package game.weekend.warehouse.directories;

import game.weekend.framework.core.FrameManager;
import game.weekend.framework.core.Journal;
import game.weekend.framework.core.Loc;

/**
 * Группы организаций.
 */
@SuppressWarnings("serial")
public class GroupsOfCompaniesJournal extends Journal {

	public GroupsOfCompaniesJournal(int id, int mode, FrameManager parentFrameMan) {
		super(id, mode, parentFrameMan);

		this.setTitle(Loc.get("groups_of_companies"));
	}

}
