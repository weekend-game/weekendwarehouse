package game.weekend.warehouse.documents;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import game.weekend.framework.core.DocData;
import game.weekend.framework.core.IEditable;
import game.weekend.framework.core.IntFrame;
import game.weekend.framework.core.ListData;

/**
 * Создать объект данных.
 */
public class ReceiptsDocData extends DocData {
	/**
	 * Создать объект данных.
	 * 
	 * @throws SQLException
	 */
	public ReceiptsDocData(int id, int mode, IntFrame frame) throws SQLException {
		super(frame);
		setValue("id", id);
		this.mode = mode;

		// Чтение документа
		if (id > 0 && mode != IEditable.ADD) {
			if (selectDocument == null)
				selectDocument = getDB().getConnection()
						.prepareStatement("SELECT doc_numb, doc_date, amount, rem FROM receipts WHERE id = ?");
			selectDocument.setInt(1, id);
			ResultSet rs = selectDocument.executeQuery();

			if (rs.next()) {
				setValue("doc_numb", rs.getString(1).trim());
				setValue("doc_date", rs.getDate(2));
				setValue("amount", rs.getBigDecimal(3));
				setValue("rem", rs.getString(4).trim());
			}
			rs.close();

			setValue("list", getList(id));
		} else {
			setValue("list", new ArrayList<DocData>());
		}

		// Умолчания при добавлении
		if (mode == IEditable.ADD || mode == IEditable.ADD_COPY) {

			GregorianCalendar c = new GregorianCalendar();
			Date d = new Date(c.getTimeInMillis());
			setValue("doc_date", d);

			if (!(mode == IEditable.ADD_COPY)) {
				setValue("rem", "новый документ");
			}
			setValue("doc_numb", getDB().getNumber("Receipt"));
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
			// Сохранение заголовка
			if (mode == IEditable.EDIT) {
				if (updateDocument == null)
					updateDocument = getDB().getConnection().prepareStatement(
							"UPDATE receipts SET doc_numb = ?, doc_date = ?, amount = ?, rem = ? WHERE id = ?");
				ps = updateDocument;
			} else {
				if (insertDocument == null)
					insertDocument = getDB().getConnection().prepareStatement(
							"INSERT INTO receipts (" + "doc_numb, doc_date, amount, rem) VALUES (?, ?, ?, ?)",
							Statement.RETURN_GENERATED_KEYS);
				ps = insertDocument;
			}
			ps.setString(1, getStringValue("doc_numb"));
			ps.setDate(2, (Date) getValue("doc_date"));
			ps.setBigDecimal(3, getBigDecimalValue("amount"));
			ps.setString(4, getStringValue("rem"));
			if (mode == IEditable.EDIT) {
				ps.setInt(5, (Integer) getValue("id"));
			}

			ps.execute();
			if (mode != IEditable.EDIT) {
				setValue("id", getDB().getID(ps));
			}

			// Сохранение списка
			@SuppressWarnings("unchecked")
			ArrayList<DocData> al = (ArrayList<DocData>) getValue("list");
			if (!saveList(al, (int) getValue("id")))
				return false;

		} catch (Exception e) {
			getMes().err("ReceiptsDocData.save()\n" + e);
			return false;
		}
		return true;
	}

	@Override
	public boolean delete() throws Exception {
		try {
			// Удаление позиций из таблицы
			if (selectListForDelete == null)
				selectListForDelete = getDB().getConnection()
						.prepareStatement("SELECT id FROM v_list WHERE doc_type = 'A' AND doc_id = ?");

			if (deletePosition == null)
				deletePosition = getDB().getConnection()
						.prepareStatement("DELETE FROM lists WHERE doc_type = 'A' AND id = ?");

			int docId = (int) getValue("id");
			selectListForDelete.setInt(1, docId);
			ResultSet rs = selectList.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				deletePosition.setInt(1, id);
				deletePosition.execute();
			}
			rs.close();

			if (deleteDocument == null)
				deleteDocument = getDB().getConnection().prepareStatement("DELETE FROM receipts WHERE id = ?");
			deleteDocument.setInt(1, (int) getValue("id"));
			deleteDocument.execute();
		} catch (Exception e) {
			getMes().err("ReceiptsDocData.deleteDocument()\n" + e);
			return false;
		}
		return true;
	}

	private boolean saveList(ArrayList<DocData> arrayDocData, int docId) {
		PreparedStatement ps;
		try {
			// Удаление удалённых позиций из таблицы
			if (selectListForDelete == null)
				selectListForDelete = getDB().getConnection()
						.prepareStatement("SELECT id FROM v_list WHERE doc_type = 'A' AND doc_id = ?");

			if (deletePosition == null)
				deletePosition = getDB().getConnection()
						.prepareStatement("DELETE FROM lists WHERE doc_type = 'A' AND id = ?");

			selectListForDelete.setInt(1, docId);
			ResultSet rs = selectList.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				if (!InList(id, arrayDocData)) {
					deletePosition.setInt(1, id);
					deletePosition.execute();
				}
			}
			rs.close();

			// Добавление и изменение позиций в таблице
			if (updatePosition == null)
				updatePosition = getDB().getConnection().prepareStatement(
						"UPDATE lists SET doc_type = ?, doc_id = ?, product_id = ?, quantity = ?, price = ?, "
								+ " amount = ?, vat = ?, amount_vat = ?, total = ?, rest = ?, price_x = ? "
								+ " WHERE id = ?");

			if (insertPosition == null)
				insertPosition = getDB().getConnection().prepareStatement(
						"INSERT INTO lists (doc_type, doc_id, product_id, quantity, price, amount, vat, "
								+ " amount_vat, total, rest, price_x) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS);

			for (DocData dd : arrayDocData) {
				int id = (int) dd.getValue("id");
				if (id > 0)
					ps = updatePosition;
				else
					ps = insertPosition;

				ps.setString(1, "A");
				ps.setInt(2, docId);

				ps.setInt(3, (Integer) dd.getValue("product_id"));
				ps.setBigDecimal(4, dd.getBigDecimalValue("quantity"));
				ps.setBigDecimal(5, dd.getBigDecimalValue("price"));
				ps.setBigDecimal(6, dd.getBigDecimalValue("amount"));
				ps.setBigDecimal(7, dd.getBigDecimalValue("vat"));
				ps.setBigDecimal(8, dd.getBigDecimalValue("amount_vat"));
				ps.setBigDecimal(9, dd.getBigDecimalValue("total"));
				ps.setBigDecimal(10, dd.getBigDecimalValue("rest"));
				ps.setBigDecimal(11, dd.getBigDecimalValue("price_x"));
				if (id > 0) {
					ps.setInt(12, id);
				}

				ps.execute();
				if (id > 0)
					setValue("id", getDB().getID(ps));
			}
		} catch (Exception e) {
			getMes().err("ReceiptsDocData.saveList()\n" + e);
			return false;
		}
		return true;
	}

	private ArrayList<ListData> getList(int id) throws SQLException {
		if (selectList == null)
			selectList = getDB().getConnection()
					.prepareStatement("SELECT id, group_id, group_name, product_id, article, name, unit, "
							+ " quantity, price, amount, vat, amount_vat, total, rest, price_x "
							+ " FROM v_list WHERE doc_type = 'A' AND doc_id = ?");
		selectList.setInt(1, id);
		ResultSet rs = selectList.executeQuery();

		ArrayList<ListData> al = new ArrayList<>();
		while (rs.next()) {
			ListData listData = new ListData(getFrame());
			listData.setIntegerValue("id", rs.getInt(1));

			listData.setIntegerValue("group_id", rs.getInt(2));
			listData.setStringValue("group_name", rs.getString(3));

			listData.setIntegerValue("product_id", rs.getInt(4));
			listData.setStringValue("article", rs.getString(5));
			listData.setStringValue("name", rs.getString(6));
			listData.setStringValue("unit", rs.getString(7));

			listData.setBigDecimalValue("quantity", rs.getBigDecimal(8));
			listData.setBigDecimalValue("price", rs.getBigDecimal(9));
			listData.setBigDecimalValue("amount", rs.getBigDecimal(10));
			listData.setBigDecimalValue("vat", rs.getBigDecimal(11));
			listData.setBigDecimalValue("amount_vat", rs.getBigDecimal(12));
			listData.setBigDecimalValue("total", rs.getBigDecimal(13));
			listData.setBigDecimalValue("rest", rs.getBigDecimal(14));
			listData.setBigDecimalValue("price_x", rs.getBigDecimal(15));
			al.add(listData);
		}
		rs.close();

		return al;
	}

	private int mode = IEditable.ADD;

	private static PreparedStatement selectDocument = null;
	private static PreparedStatement updateDocument = null;
	private static PreparedStatement insertDocument = null;
	private static PreparedStatement deleteDocument = null;

	private static PreparedStatement selectList = null;
	private static PreparedStatement updatePosition = null;
	private static PreparedStatement insertPosition = null;
	private static PreparedStatement selectListForDelete = null;
	private static PreparedStatement deletePosition = null;
}
