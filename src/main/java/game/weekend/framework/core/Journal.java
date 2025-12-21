package game.weekend.framework.core;

import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JSeparator;

import game.weekend.framework.core.table.Table;
import game.weekend.framework.core.table.TableDefinition;

/**
 * Окно журнала документов.
 */
@SuppressWarnings("serial")
public class Journal extends IntFrame implements IEditable {

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

			table = new Table(definition, getMainFrame().getActs(), getMainFrame().getDB());
			getContentPane().add(table.getPane());
		} catch (Exception e) {
			getContentPane().add(new JLabel(e.toString(), JLabel.CENTER));
		}
	}

	@Override
	public void activated() {
		Acts acts = getMainFrame().getActs();
		acts.setEnabled("Print", false);

		acts.setEnabled("Add", true);
		acts.setEnabled("AddCopy", true);
		acts.setEnabled("Edit", true);
		acts.setEnabled("Delete", true);

		acts.setEnabled("Find", false);
		acts.setEnabled("FindForward", false);
		acts.setEnabled("FindBack", false);
		acts.setEnabled("Filter", false);
		acts.setEnabled("Refresh", true);

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
		viewMenu.add(new JSeparator());
		viewMenu.add(acts.getAct("Refresh"));
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

	@Override
	public void add() {
		String className = getDocClass();
		if (className != null) {
			createFrame(className, getDocId(), IEditable.ADD);
		}
	}

	@Override
	public void addCopy() {
		String className = getDocClass();
		if (className != null) {
			int id = getDocId();
			int mode = (id > 0) ? IEditable.ADD_COPY : IEditable.ADD;
			createFrame(className, id, mode);
		}
	}

	@Override
	public void edit() {
		String className = getDocClass();
		if (className != null) {
			int id = getDocId();
			int mode = (id > 0) ? IEditable.EDIT : IEditable.ADD;
			createFrame(className, id, mode);
		}
	}

	@Override
	public void delete() {
	}

	@Override
	public int getDocId() {
		return table.getCurrentId();
	}

	/**
	 * Получить таблицу журнала.
	 * 
	 * @return таблица журнала.
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * Получить имя класса для редактирования документа.
	 * 
	 * Испольуется следующее правило. Если имя класса журнала включает Journal, то
	 * эти буквы заменяются на Doc. Это и есть имя класса для редактирования строки
	 * журнала. Если указанное сочетание букв не встречается то возвращается null.
	 * 
	 * @return имя класса для редактирования документа.
	 */
	@Override
	public String getDocClass() {
		String className = null;
		String jrnClass = getClass().getName();
		int i = jrnClass.lastIndexOf("Journal");
		if (i >= 0) {
			className = jrnClass.substring(0, i) + "Doc" + jrnClass.substring(i + 7);
		}
		return className;
	}

	public void refresh() {
		if (table != null)
			table.refresh();
	}

	/**
	 * Сообщение от документа о изменении или добавлении документа
	 * 
	 * @throws SQLException
	 */
	public void modified(int id, int mode) {
		try {
			if (id == 0) {
				table.refresh();
			} else {
				table.changed(id, mode);
			}
		} catch (Exception ignored) {
		}
	}

	private TableDefinition definition;
	private Table table;
}
