package game.weekend.warehouse.documents;

import game.weekend.framework.core.FrameManager;
import game.weekend.framework.core.IEditable;
import game.weekend.framework.core.Journal;
import game.weekend.framework.core.Loc;

/**
 * Поступления.
 */
@SuppressWarnings("serial")
public class ReceiptsJournal extends Journal {

	public ReceiptsJournal(int id, int mode, FrameManager parentFrameMan) {
		super(null, id, mode, parentFrameMan);
		
		this.setTitle(Loc.get("receipts"));
	}

	/**
	 * Реализация пункта меню "Удалить...".
	 */
	@Override
	public void delete() {
		int id = getDocId();
		if (id > 0) {
			try {
				ReceiptsData data = new ReceiptsData(id, IEditable.DELETE, this);

				if (getMainFrame().getMes().conf2("Вы действительно желаете удалить документ номер "
						+ ((String) data.getValue("doc_numb")).trim() + "?")) {
					if (data.delete()) {
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
