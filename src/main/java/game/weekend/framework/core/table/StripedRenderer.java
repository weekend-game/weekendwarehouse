package game.weekend.framework.core.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class StripedRenderer extends DefaultTableCellRenderer {

	public final static Color WHITE = Color.WHITE;
	public final static Color GREY = new Color(239, 239, 239);
	public final static Color SUMMARY = new Color(207, 207, 207);

	public StripedRenderer(TableCellRenderer arg) {
		this.tcr = arg;
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		Component c = tcr.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);

		int summaryRow = table.getModel().getRowCount() - 1;

		if (!isSelected) {
			if (summaryRow >= 0 && row == summaryRow) {
				c.setBackground(SUMMARY);
			} else {
				if (row % 2 == 0) {
					c.setBackground(WHITE);
				} else {
					c.setBackground(GREY);
				}
			}
		}

		return c;
	}

	private final TableCellRenderer tcr;
}
