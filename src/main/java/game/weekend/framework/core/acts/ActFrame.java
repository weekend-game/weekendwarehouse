package game.weekend.framework.core.acts;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import game.weekend.framework.core.MainFrame;

/**
 * Создать/активизировать независимое внутреннее окно приложения.
 */
@SuppressWarnings("serial")
public class ActFrame extends AbstractAction {

	public ActFrame(MainFrame mainFrame, String className) {
		this.mainFrame = mainFrame;
		this.className = className;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		mainFrame.createFrame(className);
	}

	private MainFrame mainFrame;
	private String className;
}
