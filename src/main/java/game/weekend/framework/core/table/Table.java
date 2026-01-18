package game.weekend.framework.core.table;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import game.weekend.framework.core.Acts;
import game.weekend.framework.core.DocData;
import game.weekend.framework.core.IDB;
import game.weekend.framework.core.IEditable;

/**
 * Таблица.
 */
public class Table {

	/**
	 * Создать таблицу с указанной моделью.
	 * 
	 * @param definition определение таблицы,
	 * @param acts       действия приложения,
	 * @param model      модель.
	 */
	protected Table(TableDefinition definition, Acts acts, ITableModel model) {
		this.model = model;

		table = new JTable(model);
		pane = new JScrollPane(table);
		turnBandingOn();

		turnKeyboardOn(acts);
		turnDoubleClickOn(acts);

		// Сортировщик
		sorter = new SummaryTableRowSorter<ITableModel>(model);
		sorter.setSortsOnUpdates(true);
		table.setRowSorter(sorter);
		model.setSorter(sorter);
		sorter.toggleSortOrder(definition.orderBy);

		// Установка размеров колонок
		TableColumnModel cm = table.getColumnModel();
		for (int i = 0; i < definition.columns.size(); ++i) {
			cm.getColumn(i).setPreferredWidth(definition.columns.get(i).width);
		}
	}

	/**
	 * Создать таблицу с моделью работающей с БД.
	 * 
	 * @param definition определение таблицы,
	 * @param acts       действия приложения,
	 * @param db         объект для работы с БД.
	 * 
	 * @throws Exception
	 */
	public Table(TableDefinition definition, Acts acts, IDB db) throws Exception {
		this(definition, acts, new TableModelDB(definition, db));

		// Позиционируемся в предпоследнюю строку в нулевую колонку
		int row = model.getRowCount() - 3;
		if (row < 0) {
			row = 0;
		}
		setRowCol(row, 0);
	}

	/**
	 * Создать таблицу с моделью работающей с ArrayList.
	 * 
	 * @param definition   определение таблицы,
	 * @param acts         действия приложения,
	 * @param arrayDocData ArrayList<DocData>.
	 * 
	 * @throws Exception
	 */

	public Table(TableDefinition definition, Acts acts, ArrayList<DocData> arrayDocData) throws Exception {
		this(definition, acts, new TableModelAL(definition, arrayDocData));

		// Позиционируемся в нулевую строку в нулевую колонку
		setRowCol(0, 0);
	}

	/**
	 * Установить текущей и отобразить указанную ячейку.
	 * 
	 * @param row строка,
	 * @param col колонка.
	 */
	public void setRowCol(int row, int col) {
		Rectangle rect = table.getCellRect(row, col, true);
		table.scrollRectToVisible(rect);

		table.clearSelection();
		table.setRowSelectionInterval(row, row);
		table.setColumnSelectionInterval(col, col);
	}

	/**
	 * Включить полоски.
	 */
	private void turnBandingOn() {
		for (int i = 0; i < model.getColumnCount(); ++i) {
			Class<?> c = table.getColumnClass(i);
			TableCellRenderer dr = table.getDefaultRenderer(c);
			TableCellRenderer r = new StripedRenderer(dr);
			table.setDefaultRenderer(c, r);
		}
	}

	/**
	 * Получить панель с таблицей.
	 * 
	 * @return панель с таблицей.
	 */
	public JScrollPane getPane() {
		return pane;
	}

	/**
	 * Получить ID текущей строки.
	 * 
	 * @return ID текущей строки.
	 */
	public int getCurrentId() {
		int id = 0;

		int[] sel = table.getSelectedRows();
		if (sel.length - 1 >= 0) {
			int row = sel[sel.length - 1];
			row = sorter.convertRowIndexToModel(row);
			id = model.getIdByRow(row);
		}

		return id;
	}

	public JTable getTable() {
		return table;
	}

	public TableRowSorter<ITableModel> getSorter() {
		return sorter;
	}

	public ITableModel getModel() {
		return model;
	}

	/**
	 * Обновить содержимое.
	 */
	public void refresh() {
		TableSaver gs = new TableSaver(this);
		try {
			model.load();
		} catch (Exception ignored) {
		}
		gs.restore();
	}

	/**
	 * Включить перехват клавиши Enter для редактирования документа.
	 * 
	 * @param acts действия приложения.
	 */
	private void turnKeyboardOn(Acts acts) {
		ActionMap aMap = table.getActionMap();
		aMap.put("editRec", acts.getAct("Edit"));
		InputMap iMap = table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		iMap.put(KeyStroke.getKeyStroke("pressed ENTER"), "editRec");
	}

	/**
	 * Включить перехват двойного клика на строке.
	 * 
	 * @param acts действия приложения.
	 */
	private void turnDoubleClickOn(final Acts acts) {
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(final MouseEvent e) {
				if (e.getClickCount() > 1 && e.getButton() == MouseEvent.BUTTON1)
					acts.getAct("Edit").actionPerformed(null);
			}
		});
	}

	/**
	 * Отработать изменение указанного документа.
	 * 
	 * @param id   ID документа.
	 * @param mode вид редактирования (IEditable.ADD, ADD_COPY, EDIT, DELETE).
	 * @throws SQLException
	 */
	public void changed(int id, int mode) throws SQLException {
		int modelRow = -1;

		switch (mode) {
		case IEditable.EDIT:
			modelRow = model.edited(id);
			break;

		case IEditable.ADD:
		case IEditable.ADD_COPY:
			modelRow = model.added(id);
			break;

		case IEditable.DELETE:
			modelRow = model.getRowById(id);
			model.deleted(modelRow);
			break;
		}
		int viewRow = sorter.convertRowIndexToView(modelRow);
		if (viewRow >= model.getRowCount()) {
			--viewRow;
		}
		if (viewRow >= 0) {
			setRowCol(viewRow, 0);
		}
	}

	private ITableModel model;
	private TableRowSorter<ITableModel> sorter;
	private JTable table;
	private JScrollPane pane;

}
