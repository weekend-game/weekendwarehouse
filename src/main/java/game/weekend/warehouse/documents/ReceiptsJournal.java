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
		super("receipts", id, mode, parentFrameMan);
	}

	/**
	 * Реализация пункта меню "Удалить...".
	 */
	@Override
	public void delete() {
		// ID текущего документа
		int id = getDocId();

		// Нулём он будет если текущая строка это итоговая строка (отрицательных ID в
		// журналах не бывает)
		if (id != 0) {
			try {
				// Операции с данными делает DocData
				ReceiptsDocData data = new ReceiptsDocData(id, IEditable.DELETE, this);

				// Уточняю у пользователя его намерения
				if (getMainFrame().getMes().conf2(Loc.get("are_you_sure_you_want_to_remove_the_document") + " '"
						+ ((String) data.getValue("doc_numb")).trim() + "'?")) {

					// Прошу DocData удалить документ
					if (data.delete()) {

						// Сообщаю Table об удалении, что бы она убрала с экрана этот документ
						getTable().changed(id, IEditable.DELETE);

					}
				}
			} catch (Exception e) {
				getMainFrame().getMes().err(e.toString());
			}
		}
	}
}
