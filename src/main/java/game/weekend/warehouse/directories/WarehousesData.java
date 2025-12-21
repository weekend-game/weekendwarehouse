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
public class WarehousesData extends DocData {
	/**
	 * Создать объект данных для указанной записи.
	 * 
	 * @param id    идентификатор записи.
	 * @param mode  режим редактирования.
	 * @param frame окно редактирования.
	 * 
	 * @throws SQLException
	 */
	public WarehousesData(int id, int mode, IntFrame frame) throws SQLException {
		super(frame);
		setValue("id", id);
		this.mode = mode;

		// Чтение документа
		if (id > 0 && mode != IEditable.ADD) {
			if (select == null)
				select = getDB().getConnection()
						.prepareStatement("SELECT name, full_name FROM warehouses WHERE id = ?");
			select.setInt(1, id);
			ResultSet rs = select.executeQuery();

			if (rs.next()) {
				setStringValue("name", rs.getString(1));
				setStringValue("full_name", rs.getString(2));
			}
			rs.close();
		}

		// Умолчания при добавлении
		if (mode == IEditable.ADD)
			setValue("name", Loc.get("new_warehouse"));
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
							.prepareStatement("UPDATE warehouses SET name = ?, full_name = ? WHERE id = ?");
				ps = update;
			} else {
				if (insert == null)
					insert = getDB().getConnection().prepareStatement(
							"INSERT INTO warehouses (name, full_name) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
				ps = insert;
			}
			ps.setString(1, getStringValue("name"));
			ps.setString(2, getStringValue("full_name"));
			if (mode == IEditable.EDIT)
				ps.setInt(3, (Integer) getValue("id"));

			ps.execute();
			if (mode != IEditable.EDIT)
				setValue("id", getDB().getID(ps));

		} catch (SQLException e) {
			switch (e.getSQLState()) {
			case "23502":
				getMes().err(Loc.get("specify_the_name") + ".");
				break;
			case "23505":
				getMes().err(Loc.get("specified_name_is_already_in_the_directory") + ".");
				break;
			default:
				getMes().err("WarehousesData.save()\n" + e);
			}
			return false;
		} catch (Exception e) {
			getMes().err("WarehousesData.save()\n" + e);
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
				delete = getDB().getConnection().prepareStatement("DELETE FROM warehouses WHERE id = ?");
			delete.setInt(1, (int) getValue("id"));
			delete.execute();
		} catch (Exception e) {
			getMes().err("WarehousesData.delete()\n" + e);
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
