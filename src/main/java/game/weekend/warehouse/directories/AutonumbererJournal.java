package game.weekend.warehouse.directories;

import game.weekend.framework.core.FrameManager;
import game.weekend.framework.core.IEditable;
import game.weekend.framework.core.Journal;
import game.weekend.framework.core.Loc;

/**
 * Организации.
 */
@SuppressWarnings("serial")
public class AutonumbererJournal extends Journal {

	public AutonumbererJournal(int id, int mode, FrameManager parentFrameMan) {
		super("autonumberer", id, mode, parentFrameMan);
	}

	/**
	 * Реализация пункта меню "Удалить...".
	 */
	@Override
	public void delete() {
		int id = getDocId();
		if (id > 0) {
			try {
				AutonumbererData docData = new AutonumbererData(id, IEditable.DELETE, this);

				if (getMainFrame().getMes().conf2(Loc.get("are_you_sure_you_want_to_remove_the_document") + " '"
						+ ((String) docData.getValue("document")).trim() + "'?")) {
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
