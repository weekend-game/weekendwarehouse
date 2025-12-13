package game.weekend.framework.core.table;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

import game.weekend.framework.core.IDB;
import game.weekend.framework.core.table.TableDefinition.ColumnDefinition;

@SuppressWarnings("serial")
public class TableModelArrayList extends AbstractTableModel implements ITableModel {

	public TableModelArrayList(TableDefinition definition, IDB db) throws Exception {
		this.definition = definition;
		this.db = db;
		load();
	}

	// Методы TableModel

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

		// Получаем ResultSet отображаемых данных
		ResultSet rs = null;
		Statement st = db.getConnection().createStatement();
		rs = st.executeQuery("SELECT * FROM " + definition.fromView);

		int colNum = definition.columns.size();

		// cNames содержит список отображаемых источников колонок
		String[] cNames = new String[colNum];
		int ii = 0;
		for (ColumnDefinition d : definition.columns) {
			cNames[ii] = d.source;
			++ii;
		}

		fromIndex = new int[colNum];
		toIndex = new int[colNum];
		columnClasses = new Class[colNum];
		boolean[] cTest = new boolean[colNum];

		nonoColumn = getNonoColumn(cNames);

		ResultSetMetaData met = rs.getMetaData();

		// Первое поле в выборке должно быть ID
		if (!met.getColumnName(1).equalsIgnoreCase(ID)) {
			throw new Exception("Поле ID не обнаружено в выборке журнала.");
		}

		int k = 0;
		for (int i = 1; i <= met.getColumnCount(); ++i) { // Считаны поля
			for (int j = 0; j < colNum; ++j) { // Поля для отображения
				if (definition.columns.get(j).source.equalsIgnoreCase(met.getColumnName(i))) {
					fromIndex[k] = i; // номер считанного поля
					toIndex[k] = j; // номер поля к отображению
					cTest[j] = true; // Отметка "поле отображения нашлось"
					columnClasses[j] = Class.forName(met.getColumnClassName(i));
					++k;
					break;
				}
			}
		}

		// Отображаемые поля должны быть в выборке
		for (int i = 0; i < colNum; ++i) {
			if (!cTest[i]) {
				String s = "Поле " + definition.columns.get(i).source + " не обнаружено в выборке журнала.";
				throw new Exception(s);
			}
		}

		// Итоговая строка
		sum = new Object[colNum + 1];
		sum[definition.columns.size()] = Integer.valueOf(0);

		// Собственно чтение
		while (rs.next()) {
			Object[] o = new Object[colNum + 1];
			readRow(rs, o);
			rows.add(o);
		}
		rs.close();

		// Расчёт итоговых сумм
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
	public void setSorter(TableRowSorter<ITableModel> sorter) {
		this.sorter = sorter;
	};

	private int getNonoColumn(String[] names) {
		int col = -1;
		for (int i = 0; i < definition.columns.size(); ++i) {
			if (names[i].equalsIgnoreCase(NONO)) {
				col = i;
			}
		}
		return col;
	}

	private void readRow(ResultSet rs, Object[] o) throws SQLException {
		o[definition.columns.size()] = rs.getObject(1);
		for (int i = 0; i < definition.columns.size(); ++i) {
			if (fromIndex[i] > 0) {
				if (columnClasses[toIndex[i]].getSimpleName().equalsIgnoreCase("String"))
					o[toIndex[i]] = (String) rs.getObject(fromIndex[i]); // .trim();
				else
					o[toIndex[i]] = rs.getObject(fromIndex[i]);
			}
		}
	}

	public final static String ID = "id";
	public final static String NONO = "nono";

	// Собственно отображаемые данные
	private ArrayList<Object[]> rows = new ArrayList<Object[]>();

	private Object[] sum; // Массив объектов для хранения итоговых сумм

	private TableRowSorter<ITableModel> sorter; // Сортировщик табличных
													// данных

	private int nonoColumn = -1;

	private Class<?>[] columnClasses;

	private int[] fromIndex;
	private int[] toIndex;

	private TableDefinition definition;
	private IDB db;
}
