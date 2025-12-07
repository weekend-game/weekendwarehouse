package game.weekend.warehouse.directories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import game.weekend.framework.core.DocData;
import game.weekend.framework.core.IntFrame;
import game.weekend.warehouse.DB;

/**
 * Объект данных "Общее...".
 */
public class GeneralData extends DocData {

	private static PreparedStatement select = null;
	private static PreparedStatement update = null;

	/**
	 * Создать объект данных "Общее...".
	 */
	public GeneralData(IntFrame frame) {
		super(frame);

		try {
			if (select == null)
				select = ((DB) getDB()).getConnection().prepareStatement("SELECT value FROM general WHERE name = ?");

			readStringAttr(select, "content");
			readStringAttr(select, "version");

		} catch (Exception e) {
			getMes().err("GeneralData()\n" + e);
		}
	}

	private void readStringAttr(PreparedStatement ps, String name) throws SQLException {
		ResultSet rs;
		String value = "";

		ps.setString(1, name);
		rs = ps.executeQuery();
		if (rs.next()) {
			value = rs.getString(1);
			if (value == null)
				value = "";
		}
		setValue(name, value.trim());
		rs.close();
	}

	/**
	 * Сохранить данные в БД.
	 * 
	 * @return true если данные сохранены.
	 */
	public boolean save() {
		try {
			if (update == null)
				update = ((DB) getDB()).getConnection().prepareStatement("UPDATE general SET value = ? WHERE name = ?");

			saveStringAttr(update, "content");

		} catch (Exception e) {
			getMes().err("GeneralData.save()\n" + e);
			return false;
		}
		return true;
	}

	private void saveStringAttr(PreparedStatement ps, String name) throws SQLException {
		String s = (String) getValue(name);
		if (s != null && s.equals(""))
			s = null;
		ps.setString(1, s);
		ps.setString(2, name);
		ps.execute();
	}
}
