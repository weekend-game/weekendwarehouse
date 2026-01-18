package game.weekend.framework.core;

import java.util.ArrayList;

import game.weekend.framework.core.table.Table;
import game.weekend.warehouse.documents.ReceiptsDocPosData;

@SuppressWarnings("serial")
public class DocumentWithAList extends Document implements IEditable {

	public DocumentWithAList(int id, int mode, FrameManager parentFrameMan, String classForEditing) {
		super(id, mode, parentFrameMan);
		this.classForEditing = classForEditing;
	}

	@Override
	public void add() {
		@SuppressWarnings("unchecked")
		ArrayList<ListData> al = (ArrayList<ListData>) getDocData().getValue("list");

		String className = this.getDocClass();
		createFrame(className, 0, IEditable.ADD, al);
	}

	@Override
	public void addCopy() {
	}

	@Override
	public void edit() {
		// Имя класса для редактирования списка
		String className = classForEditing;

		// ID редактируемой строки (отрицательные числа, если строки ещё нет в БД)
		int id = getDocId();

		// Это может быть и ADD
		int mode = (id == 0) ? IEditable.ADD : IEditable.EDIT;

		// Вариант метода создания окна с передачей списка докуента
		@SuppressWarnings("unchecked")
		ArrayList<ListData> al = (ArrayList<ListData>) getDocData().getValue("list");
		createFrame(className, id, mode, al);
	}

	@Override
	public void delete() {

		// ID текущего документа
		int id = getDocId();

		// Нулём он будет если текущая строка это итоговая строка
		if (id != 0) {
			// Операции с данными делает DocData
			@SuppressWarnings("unchecked")
			ArrayList<ListData> al = (ArrayList<ListData>) getDocData().getValue("list");
			DocData docData = new ReceiptsDocPosData(id, getMode(), this, al);

			// Уточняю у пользователя его намерения
			if (getMainFrame().getMes().conf2(Loc.get("are_you_sure_you_want_to_remove_the_position") + "?")) {

				// Прошу DocData удалить документ
				try {
					if (docData.delete())
						// Сообщаю Table об удалении, что бы она убрала с экрана этот документ
						modified(id, IEditable.DELETE);

				} catch (Exception e) {
					getMainFrame().getMes().err(e.toString());
				}
			}
		}
	}

	@Override
	public int getDocId() {
		return table.getCurrentId();
	}

	@Override
	public String getDocClass() {
		return classForEditing;
	}

	@Override
	public void modified(int id, int mode) {
		if (table == null)
			return;

		try {
			if (id == 0) {
				table.refresh();
			} else {
				table.changed(id, mode);
			}
		} catch (Exception ignored) {
		}
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public Table getTable() {
		return table;
	}

	String classForEditing;
	private Table table;
}
