package game.weekend.warehouse.directories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import game.weekend.framework.core.DocData;
import game.weekend.framework.core.IEditable;
import game.weekend.framework.core.IntFrame;
import game.weekend.framework.core.Loc;

/**
 * Данные записи.
 */
public class GroupsOfProductsData extends DocData {
	/**
	 * Создать объект данных для указанной записи.
	 * 
	 * @param id    идентификатор записи.
	 * @param mode  режим редактирования.
	 * @param frame окно редактирования.
	 * 
	 * @throws SQLException
	 */
	public GroupsOfProductsData(int id, int mode, IntFrame frame) throws SQLException {
		super(frame);
		setValue("id", id);
		this.mode = mode;

		// Чтение документа
		if (id > 0 && mode != IEditable.ADD) {
			if (select == null)
				select = getDB().getConnection().prepareStatement("SELECT name FROM products_groups WHERE id = ?");
			select.setInt(1, id);
			ResultSet rs = select.executeQuery();

			if (rs.next())
				setStringValue("name", rs.getString(1));
			rs.close();
		}

		// Умолчания при добавлении
		if (mode == IEditable.ADD)
			setValue("name", Loc.get("new_group"));
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
					update = getDB().getConnection()
							.prepareStatement("UPDATE products_groups SET name = ? WHERE id = ?");
				ps = update;
			} else {
				if (insert == null)
					insert = getDB().getConnection().prepareStatement("INSERT INTO products_groups (name) VALUES (?)",
							Statement.RETURN_GENERATED_KEYS);
				ps = insert;
			}
			ps.setString(1, getStringValue("name"));
			if (mode == IEditable.EDIT)
				ps.setInt(2, (Integer) getValue("id"));

			ps.execute();
			if (mode != IEditable.EDIT)
				setValue("id", getDB().getID(ps));

		} catch (SQLException e) {
			switch (e.getSQLState()) {
			case "23502":
				getMes().err("Укажите наименование группы.");
				break;
			case "23505":
				getMes().err("Указанное наименование уже имеется в справочнике.");
				break;
			default:
				getMes().err("ProductsData.save()\n" + e);
			}
			return false;
		} catch (Exception e) {
			getMes().err("GroupsOfProductsData.save()\n" + e);
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
				delete = getDB().getConnection().prepareStatement("DELETE FROM products_groups WHERE id = ?");
			delete.setInt(1, (int) getValue("id"));
			delete.execute();
		} catch (SQLException e) {
			if (e.getSQLState().equalsIgnoreCase("23503"))
				getMes().err("Указанная группа используется и не может быть удалена.");
			else
				getMes().err("GroupsOfProductsData.delete()\n" + e);
			return false;
		} catch (Exception e) {
			getMes().err("GroupsOfProductsData.delete()\n" + e);
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
