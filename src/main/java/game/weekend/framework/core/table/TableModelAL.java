package game.weekend.framework.core.table;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import game.weekend.framework.core.DocData;
import game.weekend.framework.core.table.TableDefinition.ColumnDefinition;

@SuppressWarnings("serial")
public class TableModelAL extends AbstractTableModel implements ITableModel {

	public TableModelAL(TableDefinition definition, ArrayList<DocData> arrayDocData) throws Exception {
		this.definition = definition;
		this.arrayDocData = arrayDocData;
		load();
	}

	// Методы TableModelDB

	@Override
	public int getColumnCount() {
		return definition.columns.size();
	}

	@Override
	public String getColumnName(int column) {
		return definition.columns.get(column).caption;
	}

	@Override
	public Class<?> getColumnClass(int column) {
		return columnClasses[column];
	}

	@Override
	public int getRowCount() {
		return rows.size() + 1;
	}

	@Override
	public Object getValueAt(int row, int column) {
		Object[] o;
		if (row < rows.size()) {
			if (column == 0 && nonoColumn < 0) {
				return sorter.convertRowIndexToView(row) + 1;
			}
			o = rows.get(row);
		} else {
			o = sum;
		}
		return o[column];
	}

	// Дополнительные методы ITableModel

	@Override
	public void load() throws Exception {
		rows.clear();

		int colsNum = definition.columns.size();

		columnClasses = new Class[colsNum];
		for (int i = 0; i < columnClasses.length; ++i)
			columnClasses[i] = Class.forName(definition.columns.get(i).displayClass);

		nonoColumn = getNonoColumn();

		// Итоговая строка
		sum = new Object[colsNum + 1];
		sum[colsNum] = Integer.valueOf(0);

		// Собственно чтение
		for (DocData dd : arrayDocData) {
			Object[] o = new Object[colsNum + 1];
			readRow(dd, o);
			rows.add(o);
		}

		calculateSum();
		fireTableDataChanged();
	}

	/**
	 * Расчитать итоги.
	 * 
	 * Итоги считаются только для типов колонок BigDecimal и Integer.
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private void calculateSum() throws InstantiationException, IllegalAccessException {
		int colNumb = definition.columns.size();
		int[] colType = new int[colNumb];

		// Определение типов суммируемых колонок и обнуление итогов
		for (int i = 0; i < colNumb; ++i) {
			if (definition.columns.get(i).sumup) {
				String name = columnClasses[i].getSimpleName();
				if (name.equals("BigDecimal")) {
					colType[i] = 1;
					sum[i] = new BigDecimal(0);
				} else if (name.equals("Integer")) {
					colType[i] = 2;
					sum[i] = Integer.valueOf(0);
				}
			}
		}

		// Собствено суммирование
		for (Object[] o : rows) {
			for (int i = 0; i < colNumb; ++i) {
				if (colType[i] == 1) {
					sum[i] = ((BigDecimal) sum[i]).add((BigDecimal) o[i]);
				} else if (colType[i] == 2) {
					sum[i] = ((Integer) sum[i]) + ((Integer) o[i]);
				}
			}
		}
	}

	@Override
	public int getIdByRow(int row) {
		int id = 0;
		if (row < rows.size()) {
			Object[] o = rows.get(row);
			Object i = o[this.getColumnCount()];
			id = (Integer) i;
		}
		return id;
	}

	@Override
	public int getRowById(int id) {
		int row = -1;
		int i = 0;
		for (Object[] o : rows) {
			if ((Integer) o[o.length - 1] == id) {
				row = i;
				break;
			}
			++i;
		}
		return row;
	}

	@Override
	public void setSorter(TableRowSorter<ITableModel> sorter) {
		this.sorter = sorter;
	};

	@Override
	public int added(int id) throws SQLException {
		int row = -1;

		DocData docData = getDocData(id, arrayDocData);

		if (docData != null) {
			row = rows.size();
			Object[] o = new Object[definition.columns.size() + 1];
			readRow(docData, o);

			if (nonoColumn >= 0) {
				int nono = (int) ((Integer) o[nonoColumn]);
				renumberRows(nono, 1);
			}

			rows.add(o);
			fireTableRowsInserted(row, row);
			fireTableDataChanged();
		}

		return row;
	}

	@Override
	public int edited(int id) throws SQLException {
		int row = -1;

		DocData docData = getDocData(id, arrayDocData);

		if (docData != null) {
			row = getRowById(id);
			Object[] o = rows.get(row);
			readRow(docData, o);
			fireTableRowsUpdated(row, row);
		}

		return row;
	}

	@Override
	public void deleted(int row) {
		if (nonoColumn >= 0) {
			int nono = (int) ((Integer) getValueAt(row, nonoColumn));
			renumberRows(nono, -1);
		}

		rows.remove(row);
		fireTableRowsDeleted(row, row);
		fireTableDataChanged();
	}

	private int getNonoColumn() {
		int columnNumber = -1;
		for (int i = 0; i < definition.columns.size(); ++i) {
			if (definition.columns.get(i).source.equalsIgnoreCase(NONO)) {
				columnNumber = i;
				break;
			}
		}
		return columnNumber;
	}

	private void readRow(DocData docData, Object[] o) {
		o[definition.columns.size()] = (int) docData.getValue("id");

		int i = 0;
		for (ColumnDefinition cd : definition.columns)
			o[i++] = docData.getValue(cd.source);
	}

	private void renumberRows(int from, int value) {
		for (Object[] o : rows) {
			if ((Integer) o[nonoColumn] >= from) {
				o[nonoColumn] = (Integer) o[nonoColumn] + value;
			}
		}
	}

	private DocData getDocData(int id, ArrayList<DocData> arrayDocData) {
		DocData docData = null;
		for (DocData dd : arrayDocData)
			if ((int) dd.getValue("id") == id) {
				docData = dd;
				break;
			}
		return docData;
	}

	public final static String ID = "id";
	public final static String NONO = "nono";

	private TableDefinition definition;
	private ArrayList<DocData> arrayDocData;

	private ArrayList<Object[]> rows = new ArrayList<Object[]>(); // Собственно отображаемые данные
	private Object[] sum; // Массив для хранения итоговых сумм
	private Class<?>[] columnClasses;
	private TableRowSorter<ITableModel> sorter; // Сортировщик табличных данных

	private int nonoColumn = -1;
}
