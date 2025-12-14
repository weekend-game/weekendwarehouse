package game.weekend.framework.core.table;

public class TableSaver {

	public TableSaver(Table table) {
		this.table = table;
		id = table.getCurrentId();
		row = table.getTable().getSelectedRow();
		col = table.getTable().getSelectedColumn();
	}

	public void restore() {
		if (col < 0 || col >= table.getTable().getColumnCount()) {
			col = 0;
		}

		ITableModel m = table.getModel();
		int modelRow = m.getRowById(id);
		int viewRow = -1;
		if (modelRow >= 0) {
			viewRow = table.getSorter().convertRowIndexToView(modelRow);
		} else {
			viewRow = row;
			if (viewRow < 0) {
				viewRow = 0;
			}
			if (row >= m.getRowCount()) {
				viewRow = m.getRowCount() - 1;
			}
		}
		if (viewRow < 0) {
			viewRow = 0;
		}

		table.setRowCol(viewRow, col);
	}

	private Table table;
	private int id;
	private int row;
	private int col;
}
