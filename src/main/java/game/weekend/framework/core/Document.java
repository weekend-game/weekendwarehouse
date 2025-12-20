package game.weekend.framework.core;

/**
 * Окно документа.
 */
@SuppressWarnings("serial")
public class Document extends IntFrame {

	/**
	 * Отображаемая длина поля, использующегося в заголовке (метод showTitle()) для
	 * конкретизации документа.
	 */
	public final static int FIELD_LENGTH = 28;

	/**
	 * Создать окно документа.
	 */
	public Document(int id, int mode, FrameManager parentFrameMan) {
		super(id, mode, parentFrameMan);
	}

	/**
	 * Отобразить заголовок окна.
	 * 
	 * @param title - название документа.
	 * @param field - поле, использующееся для конеретизации документа.
	 */
	public void showTitle(String title, String field) {

		String name = (String) docData.getValue(field);
		if (name != null) {
			if (name.length() > FIELD_LENGTH) {
				name = name.substring(0, FIELD_LENGTH - 1) + "...";
			}
			title = title + " \"" + name + "\"";
		}

		switch (getMode()) {
		case IEditable.ADD:
			setTitle(title + " [добавление]");
			break;
		case IEditable.ADD_COPY:
			setTitle(title + " [добавление копии]");
			break;
		case IEditable.EDIT:
			setTitle(title + " [исправление]");
			break;
		}
	}

	/**
	 * Установить объект данных для окна документа.
	 * 
	 * @param data объект данных.
	 */
	public void setDocData(DocData docData) {
		this.docData = docData;
	}

	/**
	 * Получить объект данных.
	 * 
	 * @return объект данных.
	 */
	public DocData getData() {
		return docData;
	}

	@Override
	public void activated() {
		Acts acts = getMainFrame().getActs();
		acts.setEnabled("Print", true);
	}

	@Override
	public void deactivated() {
		Acts acts = getMainFrame().getActs();
		acts.setEnabled("Print", false);
	}

	@Override
	public boolean hasChanges() {
		boolean result = true;
		if (docData != null) {
			result = docData.hasChanges();
		}
		return result;
	}

	@Override
	public boolean save() {
		boolean result = true;
		if (docData != null) {
			if (!docData.isOK(getMainFrame().getMes())) {
				return false;
			}
			docData.update();
			result = docData.save();

			// Уведомляем журнал о изменении документа
			if (result) {
				Journal frame = (Journal) getParentFrame();
				if (frame != null) {
					frame.modified((Integer) docData.getValue("id"), getMode());
				}
			}
		}
		return result;
	}

	private DocData docData;
}
