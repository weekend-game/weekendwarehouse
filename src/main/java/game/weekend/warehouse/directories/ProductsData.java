package game.weekend.warehouse.directories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import game.weekend.framework.core.DocData;
import game.weekend.framework.core.IEditable;
import game.weekend.framework.core.IntFrame;
import game.weekend.framework.core.Loc;
import game.weekend.framework.core.controls.ConСatBox.CatPos;

/**
 * Данные записи.
 */
public class ProductsData extends DocData {
	/**
	 * Создать объект данных для указанной записи.
	 * 
	 * @param id    идентификатор записи.
	 * @param mode  режим редактирования.
	 * @param frame окно редактирования.
	 * 
	 * @throws SQLException
	 */
	public ProductsData(int id, int mode, IntFrame frame) throws SQLException {
		super(frame);
		setValue("id", id);
		this.mode = mode;

		// Чтение документа
		if (id > 0 && mode != IEditable.ADD) {
			if (select == null)
				select = getDB().getConnection()
						.prepareStatement("SELECT group_id, article, name, unit, vat FROM products WHERE id = ?");
			select.setInt(1, id);
			ResultSet rs = select.executeQuery();

			if (rs.next()) {
				setValue("group_id", rs.getInt(1));
				setStringValue("article", rs.getString(2));
				setStringValue("name", rs.getString(3));
				setStringValue("unit", rs.getString(4));
				setBigDecimalValue("vat", rs.getBigDecimal(5));
			}
			rs.close();
		}

		// Умолчания при добавлении
		if (mode == IEditable.ADD) {
			setValue("name", Loc.get("new_product"));
			setValue("group_id", 1);
		}
	}

	/**
	 * Сохранить запись в таблицу.
	 * 
	 * @return true если данные сохранены.
	 */
	public boolean save() {
		PreparedStatement ps;
		try {
			if (mode == IEditable.EDIT) {
				if (update == null)
					update = getDB().getConnection().prepareStatement("UPDATE products SET "
							+ "group_id = ?, article = ?, name = ?, unit = ?, vat = ? WHERE id = ?");
				ps = update;
			} else {
				if (insert == null)
					insert = getDB().getConnection().prepareStatement(
							"INSERT INTO products (group_id, article, name, unit, vat) VALUES (?, ?, ?, ?, ?)",
							Statement.RETURN_GENERATED_KEYS);
				ps = insert;
			}
			ps.setInt(1, (Integer) getValue("group_id"));
			ps.setString(2, getStringValue("article"));
			ps.setString(3, getStringValue("name"));
			ps.setString(4, getStringValue("unit"));
			ps.setBigDecimal(5, getBigDecimalValue("vat"));

			if (mode == IEditable.EDIT) {
				ps.setInt(6, (Integer) getValue("id"));
			}

			ps.execute();

			if (mode != IEditable.EDIT) {
				setValue("id", getDB().getID(ps));
			}

		} catch (SQLException e) {
			switch (e.getSQLState()) {
			case "23502":
				getMes().err(Loc.get("specify_the_article") + ".");
				break;
			case "23505":
				getMes().err(Loc.get("specified_article_is_already_in_the_directory") + ".");
				break;
			default:
				getMes().err("ProductsData.save()\n" + e);
			}
			return false;
		} catch (Exception e) {
			getMes().err("ProductsData.save()\n" + e);
			return false;
		}
		return true;
	}

	/**
	 * Удалить запись из таблицы.
	 * 
	 * @return true если запись удалена.
	 */
	@Override
	public boolean delete() throws Exception {
		try {
			if (delete == null)
				delete = getDB().getConnection().prepareStatement("DELETE FROM products WHERE id = ?");
			delete.setInt(1, (int) getValue("id"));
			delete.execute();
		} catch (Exception e) {
			getMes().err("ProductsData.delete()\n" + e);
			return false;
		}
		return true;
	}

	public ArrayList<CatPos> getGroups() {
		ArrayList<CatPos> result = new ArrayList<>();
		try {
			if (groups == null)
				groups = getDB().getConnection().prepareStatement("SELECT id, name FROM products_groups");
			ResultSet rs = groups.executeQuery();
			while (rs.next())
				result.add(new CatPos(rs.getInt(1), rs.getString(2)));
			rs.close();
		} catch (Exception e) {
			getMes().err("ProductsData.getGroups()\n" + e);
		}

		return result;
	}

	private int mode = IEditable.ADD;

	private static PreparedStatement select = null;
	private static PreparedStatement update = null;
	private static PreparedStatement insert = null;
	private static PreparedStatement delete = null;
	private static PreparedStatement groups = null;
}
