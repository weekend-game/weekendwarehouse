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
}
