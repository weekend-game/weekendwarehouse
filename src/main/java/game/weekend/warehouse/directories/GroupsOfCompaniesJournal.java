package game.weekend.warehouse.directories;

import game.weekend.framework.core.Loc;
import game.weekend.framework.core.IntFrame;
import game.weekend.framework.core.FrameManager;

/**
 * Группы организаций.
 */
@SuppressWarnings("serial")
public class GroupsOfCompaniesJournal extends IntFrame {

	public GroupsOfCompaniesJournal(int id, int mode, FrameManager parentFrameMan) {
		super(id, mode, parentFrameMan);

		this.setTitle(Loc.get("groups_of_companies"));
	}

}
