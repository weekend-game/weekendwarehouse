package game.weekend.framework.core.acts;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import game.weekend.framework.core.Loc;
import game.weekend.framework.core.MainFrame;

/**
 * Заготовка для других Действий.
 */
@SuppressWarnings("serial")
public abstract class Act extends AbstractAction {

	public Act(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		mainFrame.getMes().notice((String) getValue(Action.SHORT_DESCRIPTION) + " - " + Loc.get("not_implemented"));
	}

	/**
	 * Получить основное окно приложения.
	 * 
	 * @return основное окно приложения.
	 */
	public MainFrame getMainFrame() {
		return mainFrame;
	}

	private MainFrame mainFrame;
}
