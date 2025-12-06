package game.weekend.framework.core;

/**
 * Окно документа.
 */
@SuppressWarnings("serial")
public class Document extends IntFrame {

	/**
	 * Создать окно документа.
	 */
	public Document(int id, int mode, FrameManager parentFrameMan) {
		super(id, mode, parentFrameMan);
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
	public DocData getDocData() {
		return docData;
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
		}
		return result;
	}

	private DocData docData;
}
