package game.weekend.framework.core.table;

import java.awt.Rectangle;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import game.weekend.framework.core.IDB;

/**
 * Таблица.
 */
public class Table {

	/**
	 * Создать таблицу.
	 * 
	 * @param definition Определение таблицы
	 * @param db         Объект для работы с БД.
	 * @throws Exception
	 */
	public Table(TableDefinition definition, IDB db) throws Exception {

		model = new TableModelArrayList(definition, db);
		table = new JTable(model);
		pane = new JScrollPane(table);
		turnBandingOn();

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

		// Позиционируемся в предпоследнюю (кроме итоговой) строку в нулевую
		// колонку
		int row = model.getRowCount() - 3;
		if (row < 0) {
			row = 0;
		}
		setRowCol(row, 0);
	}

	/**
	 * Установить текущей и отобразить указанную ячейку.
	 * 
	 * @param row строка
	 * @param col колонка
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
	 * 
	 * @param table
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

	private TableModelArrayList model;
	private TableRowSorter<ITableModel> sorter;
	private JTable table;
	private JScrollPane pane;
}
