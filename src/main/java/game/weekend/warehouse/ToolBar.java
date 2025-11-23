package game.weekend.warehouse;

import javax.swing.JToolBar;

import game.weekend.framework.core.Acts;

/**
 * Toolbar приложения.
 */
@SuppressWarnings("serial")
class ToolBar extends JToolBar {

	/**
	 * Создать Toolbar приложения.
	 **/
	public ToolBar(Acts acts) {
		setRollover(true);
		setFloatable(false);

		add(acts.getAct("Print"));
		addSeparator();
		add(acts.getAct("Add"));
		add(acts.getAct("AddCopy"));
		add(acts.getAct("Edit"));
		add(acts.getAct("Delete"));
		addSeparator();
		add(acts.getAct("Filter"));
		addSeparator();
		add(acts.getAct("Find"));
		add(acts.getAct("FindForward"));
		add(acts.getAct("FindBack"));
		addSeparator();
		add(acts.getAct("Receipts"));
		add(acts.getAct("Cards"));
		add(acts.getAct("Issue"));
	}
}
