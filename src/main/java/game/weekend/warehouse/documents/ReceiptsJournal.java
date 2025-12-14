package game.weekend.warehouse.documents;

import game.weekend.framework.core.FrameManager;
import game.weekend.framework.core.IEditable;
import game.weekend.framework.core.Journal;

/**
 * Поступления.
 */
@SuppressWarnings("serial")
public class ReceiptsJournal extends Journal {

	public ReceiptsJournal(int id, int mode, FrameManager parentFrameMan) {
		super("receipts", id, mode, parentFrameMan);
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
