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
public class CompaniesData extends DocData {
	/**
	 * Создать объект данных для указанной записи.
	 * 
	 * @param id    идентификатор записи.
	 * @param mode  режим редактирования.
	 * @param frame окно редактирования.
	 * 
	 * @throws SQLException
	 */
	public CompaniesData(int id, int mode, IntFrame frame) throws SQLException {
		super(frame);
		setValue("id", id);
		this.mode = mode;

		// Чтение документа
		if (id > 0 && mode != IEditable.ADD) {
			if (select == null)
				select = getDB().getConnection()
						.prepareStatement("SELECT group_id, name, full_name, "
								+ " address, telephone, email, site, reg_no, town, inn, kpp, okato, okpo, "
								+ " account, bank, bic, corr_acc, head, chief_accountant, accountant, issued_by, "
								+ " rem1, rem2, customer, supplier FROM companies WHERE id = ?");
			select.setInt(1, id);
			ResultSet rs = select.executeQuery();

			if (rs.next()) {
				setValue("group_id", rs.getInt(1));
				setStringValue("name", rs.getString(2));
				setStringValue("full_name", rs.getString(3));
				setStringValue("address", rs.getString(4));
				setStringValue("telephone", rs.getString(5));
				setStringValue("email", rs.getString(6));
				setStringValue("site", rs.getString(7));
				setStringValue("reg_no", rs.getString(8));
				setStringValue("town", rs.getString(9));
				setStringValue("inn", rs.getString(10));
				setStringValue("kpp", rs.getString(11));
				setStringValue("okato", rs.getString(12));
				setStringValue("okpo", rs.getString(13));
				setStringValue("account", rs.getString(14));
				setStringValue("bank", rs.getString(15));
				setStringValue("bic", rs.getString(16));
				setStringValue("corr_acc", rs.getString(17));
				setStringValue("head", rs.getString(18));
				setStringValue("chief_accountant", rs.getString(19));
				setStringValue("accountant", rs.getString(20));
				setStringValue("issued_by", rs.getString(21));
				setStringValue("rem1", rs.getString(22));
				setStringValue("rem2", rs.getString(23));
				setBooleanValue("customer", rs.getBoolean(24));
				setBooleanValue("supplier", rs.getBoolean(25));
			}
			rs.close();
		}

		// Умолчания при добавлении
		if (mode == IEditable.ADD) {
			setValue("name", Loc.get("new_company"));
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
					update = getDB().getConnection()
							.prepareStatement("UPDATE companies SET "
									+ "group_id = ?, name = ?, full_name = ?, address = ?, telephone = ?, email = ?, "
									+ "site = ?, reg_no = ?, town = ?, inn = ?, kpp = ?, okato = ?, okpo = ?, "
									+ "account = ?, bank = ?, bic = ?, corr_acc = ?, head = ?, chief_accountant = ?, "
									+ "accountant = ?, issued_by = ?, rem1 = ?, rem2 = ?, customer = ?, supplier = ? "
									+ "WHERE id = ?");
				ps = update;
			} else {
				if (insert == null)
					insert = getDB().getConnection()
							.prepareStatement("INSERT INTO companies (	group_id, name, full_name, "
									+ " address, telephone, email, site, reg_no, town, inn, kpp, okato, okpo, "
									+ " account, bank, bic, corr_acc, head, chief_accountant, accountant, issued_by, "
									+ " rem1, rem2, customer, supplier ) VALUES ( "
									+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
									Statement.RETURN_GENERATED_KEYS);
				ps = insert;
			}
			ps.setInt(1, (Integer) getValue("group_id"));
			ps.setString(2, getStringValue("name"));
			ps.setString(3, getStringValue("full_name"));
			ps.setString(4, getStringValue("address"));
			ps.setString(5, getStringValue("telephone"));
			ps.setString(6, getStringValue("email"));
			ps.setString(7, getStringValue("site"));
			ps.setString(8, getStringValue("reg_no"));
			ps.setString(9, getStringValue("town"));
			ps.setString(10, getStringValue("inn"));
			ps.setString(11, getStringValue("kpp"));
			ps.setString(12, getStringValue("okato"));
			ps.setString(13, getStringValue("okpo"));
			ps.setString(14, getStringValue("account"));
			ps.setString(15, getStringValue("bank"));
			ps.setString(16, getStringValue("bic"));
			ps.setString(17, getStringValue("corr_acc"));
			ps.setString(18, getStringValue("head"));
			ps.setString(19, getStringValue("chief_accountant"));
			ps.setString(20, getStringValue("accountant"));
			ps.setString(21, getStringValue("issued_by"));
			ps.setString(22, getStringValue("rem1"));
			ps.setString(23, getStringValue("rem2"));
			ps.setBoolean(24, getBooleanValue("customer"));
			ps.setBoolean(25, getBooleanValue("supplier"));

			if (mode == IEditable.EDIT) {
				ps.setInt(26, (Integer) getValue("id"));
			}

			ps.execute();

			if (mode != IEditable.EDIT) {
				setValue("id", getDB().getID(ps));
			}
		} catch (SQLException e) {
			switch (e.getSQLState()) {
			case "23502":
				getMes().err(Loc.get("specify_the_company_name") + ".");
				break;
			case "23505":
				getMes().err(Loc.get("specified_name_is_already_in_the_directory") + ".");
				break;
			default:
				getMes().err("CompaniesData.save()\n" + e);
			}
			return false;
		} catch (Exception e) {
			getMes().err("CompaniesData.save()\n" + e);
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
				delete = getDB().getConnection().prepareStatement("DELETE FROM companies WHERE id = ?");
			delete.setInt(1, (int) getValue("id"));
			delete.execute();
		} catch (Exception e) {
			getMes().err("CompaniesData.delete()\n" + e);
			return false;
		}
		return true;
	}

	public ArrayList<CatPos> getGroups() {
		ArrayList<CatPos> result = new ArrayList<>();
		try {
			if (groups == null)
				groups = getDB().getConnection().prepareStatement("SELECT id, name FROM companies_groups");
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
