package game.weekend.framework.core;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JSeparator;

import game.weekend.framework.core.table.Table;
import game.weekend.framework.core.table.TableDefinition;

/**
 * Окно журнала документов.
 */
@SuppressWarnings("serial")
public class Journal extends IntFrame {

	/**
	 * Создать окно журнала документов.
	 */
	public Journal(String defName, int id, int mode, FrameManager parentFrameManager) {
		super(id, mode, parentFrameManager);

		if (defName == null)
			return;

		try {
			definition = getMainFrame().getDB().getTableDefinition(defName);
			setTitle(definition.title);

			table = new Table(definition, getMainFrame().getDB());
			getContentPane().add(table.getPane());
		} catch (Exception e) {
			getContentPane().add(new JLabel(e.toString(), JLabel.CENTER));
		}
	}

	@Override
	public void activated() {
		Acts acts = getMainFrame().getActs();
		acts.setEnabled("Print", true);

		acts.setEnabled("Add", true);
		acts.setEnabled("AddCopy", true);
		acts.setEnabled("Edit", true);
		acts.setEnabled("Delete", true);

		acts.setEnabled("Find", true);
		acts.setEnabled("FindForward", true);
		acts.setEnabled("FindBack", true);
		acts.setEnabled("Filter", true);

		// Создание меню Редактирование
		JMenu editMenu = getMainFrame().getMenuBar().getEditMenu();
		if (editMenu == null) {
			return;
		}
		editMenu.removeAll();

		editMenu.add(acts.getAct("Add"));
		editMenu.add(acts.getAct("AddCopy"));
		editMenu.add(acts.getAct("Edit"));
		editMenu.add(new JSeparator());
		editMenu.add(acts.getAct("Delete"));
		editMenu.setEnabled(true);

		// Создание меню Просмотр
		JMenu viewMenu = getMainFrame().getMenuBar().getViewMenu();
		if (viewMenu == null) {
			return;
		}
		viewMenu.removeAll();

		viewMenu.add(acts.getAct("Find"));
		viewMenu.add(acts.getAct("FindForward"));
		viewMenu.add(acts.getAct("FindBack"));
		viewMenu.add(new JSeparator());
		viewMenu.add(acts.getAct("Filter"));
		viewMenu.setEnabled(true);
	}

	@Override
	public void deactivated() {
		Acts acts = getMainFrame().getActs();

		acts.setEnabled("Print", false);

		acts.setEnabled("Add", false);
		acts.setEnabled("AddCopy", false);
		acts.setEnabled("Edit", false);
		acts.setEnabled("Delete", false);

		acts.setEnabled("Find", false);
		acts.setEnabled("FindForward", false);
		acts.setEnabled("FindBack", false);
		acts.setEnabled("Filter", false);
	}

	private TableDefinition definition;
	private Table table;
}
