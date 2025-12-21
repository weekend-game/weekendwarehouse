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
public class AutonumbererData extends DocData {
	/**
	 * Создать объект данных для указанной записи.
	 * 
	 * @param id    идентификатор записи.
	 * @param mode  режим редактирования.
	 * @param frame окно редактирования.
	 * 
	 * @throws SQLException
	 */
	public AutonumbererData(int id, int mode, IntFrame frame) throws SQLException {
		super(frame);
		setValue("id", id);
		this.mode = mode;

		// Чтение документа
		if (id > 0 && mode != IEditable.ADD) {
			if (select == null)
				select = getDB().getConnection()
						.prepareStatement("SELECT document, counter, first_value, last_value, prefix, suffix "
								+ " FROM autonumberer WHERE id = ?");
			select.setInt(1, id);
			ResultSet rs = select.executeQuery();

			if (rs.next()) {
				setStringValue("document", rs.getString(1));
				setIntegerValue("counter", rs.getInt(2));
				setIntegerValue("first_value", rs.getInt(3));
				setIntegerValue("last_value", rs.getInt(4));
				setStringValue("prefix", rs.getString(5));
				setStringValue("suffix", rs.getString(6));
			}
			rs.close();
		}

		// Умолчания при добавлении
		if (mode == IEditable.ADD) {
			setValue("name", Loc.get("new_document"));
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
					update = getDB().getConnection().prepareStatement("UPDATE autonumberer SET "
							+ "document = ?, counter = ?, first_value = ?, last_value = ?, prefix = ?, suffix = ? "
							+ "WHERE id = ?");
				ps = update;
			} else {
				if (insert == null)
					insert = getDB().getConnection().prepareStatement(
							"INSERT INTO autonumberer (document, counter, first_value, last_value, prefix, suffix"
									+ ") VALUES ( ?, ?, ?, ?, ?, ?)",
							Statement.RETURN_GENERATED_KEYS);
				ps = insert;
			}
			ps.setString(1, getStringValue("document"));
			ps.setInt(2, getIntegerValue("counter"));
			ps.setInt(3, getIntegerValue("first_value"));
			ps.setInt(4, getIntegerValue("last_value"));
			ps.setString(5, getStringValue("prefix"));
			ps.setString(6, getStringValue("suffix"));

			if (mode == IEditable.EDIT) {
				ps.setInt(7, (Integer) getValue("id"));
			}

			ps.execute();

			if (mode != IEditable.EDIT) {
				setValue("id", getDB().getID(ps));
			}
		} catch (SQLException e) {
			switch (e.getSQLState()) {
			case "23502":
				getMes().err(Loc.get("specify_the_name") + ".");
				break;
			case "23505":
				getMes().err(Loc.get("specified_name_is_already_in_the_directory") + ".");
				break;
			default:
				getMes().err("AutonumbererData.save()\n" + e);
			}
			return false;
		} catch (Exception e) {
			getMes().err("AutonumbererData.save()\n" + e);
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
				delete = getDB().getConnection().prepareStatement("DELETE FROM autonumberer WHERE id = ?");
			delete.setInt(1, (int) getValue("id"));
			delete.execute();
		} catch (Exception e) {
			getMes().err("AutonumbererData.delete()\n" + e);
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
