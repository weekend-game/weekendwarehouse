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
			readStringAttr(select, "company");
			readStringAttr(select, "address");
			readStringAttr(select, "telephone");
			readStringAttr(select, "email");
			readStringAttr(select, "site");
			readStringAttr(select, "reg_no");
			readStringAttr(select, "town");
			readStringAttr(select, "inn");
			readStringAttr(select, "kpp");
			readStringAttr(select, "okato");
			readStringAttr(select, "okpo");
			readStringAttr(select, "account");
			readStringAttr(select, "bank");
			readStringAttr(select, "bic");
			readStringAttr(select, "corr_acc");
			readStringAttr(select, "head");
			readStringAttr(select, "chief_accountant");
			readStringAttr(select, "accountant");
			readStringAttr(select, "issued_by");

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
			// "version" не записываем, поле только для чтения
			saveStringAttr(update, "company");
			saveStringAttr(update, "address");
			saveStringAttr(update, "telephone");
			saveStringAttr(update, "email");
			saveStringAttr(update, "site");
			saveStringAttr(update, "reg_no");
			saveStringAttr(update, "town");
			saveStringAttr(update, "inn");
			saveStringAttr(update, "kpp");
			saveStringAttr(update, "okato");
			saveStringAttr(update, "okpo");
			saveStringAttr(update, "account");
			saveStringAttr(update, "bank");
			saveStringAttr(update, "bic");
			saveStringAttr(update, "corr_acc");
			saveStringAttr(update, "head");
			saveStringAttr(update, "chief_accountant");
			saveStringAttr(update, "accountant");
			saveStringAttr(update, "issued_by");

		} catch (Exception e) {
			getMes().err("GeneralData.save()\n" + e);
			return false;
		}
		return true;
	}

	@Override
	public boolean delete() throws Exception {
		return false;
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
