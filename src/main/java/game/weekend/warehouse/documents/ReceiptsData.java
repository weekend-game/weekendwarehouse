package game.weekend.warehouse.documents;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;

import game.weekend.framework.core.DocData;
import game.weekend.framework.core.IEditable;
import game.weekend.framework.core.IntFrame;

/**
 * Создать объект данных.
 */
public class ReceiptsData extends DocData {
	/**
	 * Создать объект данных.
	 * 
	 * @throws SQLException
	 */
	public ReceiptsData(int id, int mode, IntFrame frame) throws SQLException {
		super(frame);
		setValue("id", id);
		this.mode = mode;

		// Чтение документа
		if (id > 0 && mode != IEditable.ADD) {
			if (select == null)
				select = getDB().getConnection()
						.prepareStatement("SELECT doc_numb, doc_date, amount, rem FROM receipts WHERE id = ?");
			select.setInt(1, id);
			ResultSet res = select.executeQuery();

			if (res.next()) {
				setValue("doc_numb", res.getString(1).trim());
				setValue("doc_date", res.getDate(2));
				setValue("amount", res.getBigDecimal(3));
				setValue("rem", res.getString(4).trim());
			}
			res.close();
		}

		// Умолчания при добавлении
		if (mode == IEditable.ADD || mode == IEditable.ADD_COPY) {

			GregorianCalendar c = new GregorianCalendar();
			Date d = new Date(c.getTimeInMillis());
			setValue("doc_date", d);

			if (!(mode == IEditable.ADD_COPY)) {
				setValue("doc_numb", "12345/7");

				setBigDecimalValue("amount", 12345.67);

				setValue("rem", "новый документ");
			}
		}
	}

	/**
	 * Сохранить данные в БД.
	 * 
	 * @return true если данные сохранены.
	 */
	public boolean save() {
		PreparedStatement ps;
		try {
			if (mode == IEditable.EDIT) {
				if (update == null)
					update = getDB().getConnection().prepareStatement(
							"UPDATE receipts SET doc_numb = ?, doc_date = ?, amount = ?, rem = ? WHERE id = ?");
				ps = update;
			} else {
				if (insert == null)
					insert = getDB().getConnection().prepareStatement(
							"INSERT INTO receipts (" + "doc_numb, doc_date, amount, rem) VALUES (?, ?, ?, ?)",
							Statement.RETURN_GENERATED_KEYS);
				ps = insert;
			}
			ps.setString(1, (String) getValue("doc_numb"));
			ps.setDate(2, (Date) getValue("doc_date"));
			ps.setBigDecimal(3, (BigDecimal) getValue("amount"));
			ps.setString(4, (String) getValue("rem"));
			if (mode == IEditable.EDIT) {
				ps.setInt(5, (Integer) getValue("id"));
			}

			ps.execute();
			if (mode != IEditable.EDIT) {
				setValue("id", getDB().getID(ps));
			}

		} catch (Exception e) {
			getMes().err("ReceiptsData.save()\n" + e);
			return false;
		}
		return true;
	}

	@Override
	public boolean delete() throws Exception {
		try {
			if (delete == null)
				delete = getDB().getConnection().prepareStatement("DELETE FROM receipts WHERE id = ?");
			delete.setInt(1, (int) getValue("id"));
			delete.execute();
			delete.close();
		} catch (Exception e) {
			getMes().err("ReceiptsData.delete()\n" + e);
			return false;
		}
		return true;
	}

	private int mode = IEditable.ADD;

	private static PreparedStatement select = null;
	private static PreparedStatement update = null;
	private static PreparedStatement insert = null;
	private static PreparedStatement delete = null;

}
