package game.weekend.warehouse.directories;

import game.weekend.framework.core.FrameManager;
import game.weekend.framework.core.IEditable;
import game.weekend.framework.core.Journal;
import game.weekend.framework.core.Loc;

/**
 * Журнал.
 */
@SuppressWarnings("serial")
public class GroupsOfProductsJournal extends Journal {

	public GroupsOfProductsJournal(int id, int mode, FrameManager parentFrameMan) {
		super("products_groups", id, mode, parentFrameMan);
	}

	/**
	 * Реализация пункта меню "Удалить...".
	 */
	@Override
	public void delete() {
		int id = getDocId();
		if (id > 0) {
			try {
				GroupsOfProductsData docData = new GroupsOfProductsData(id, IEditable.DELETE, this);

				if (getMainFrame().getMes().conf2(Loc.get("are_you_sure_you_want_to_remove_the_group") + " '"
						+ ((String) docData.getValue("name")).trim() + "'?")) {
					if (docData.delete()) {
						getTable().changed(id, IEditable.DELETE);
					}
				}
			} catch (Exception e) {
				getMainFrame().getMes().err(e.toString());
			}
		} else {
		}
	}
}
