package game.weekend.warehouse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import game.weekend.framework.core.IDB;
import game.weekend.framework.core.Loc;
import game.weekend.framework.core.Mes;
import game.weekend.framework.core.Proper;
import game.weekend.framework.core.table.TableDefinition;

/**
 * Реализация объекта работы с БД для СУБД Derby.
 */
public class DB implements IDB {

	/**
	 * Создать объект работы с БД для СУБД Derby.
	 * 
	 * @param mes - объект для выдачи сообщений.
	 * @param pro - объект локального хранения свойств приложения.
	 */
	public DB(Mes mes, Proper pro) {
		this.mes = mes;
		this.pro = pro;
	}

	@Override
	public Connection getConnection() {
		if (connection == null) {
			String driver = pro.getProperty("driver", "");
			String constr = pro.getProperty("constr", "");

			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				mes.err("DB.getConnection()\n" + Loc.get("class") + " " + driver + " " + Loc.get("not_found") + ".");
				return null;
			}

			try {
				connection = DriverManager.getConnection(constr, "finlet", "finlet");
			} catch (SQLException e) {
				mes.err("DB.getConnection()\n" + Loc.get("connection_string") + ": " + constr + "\n" + e);
				return null;
			}
		}
		return connection;
	}

	@Override
	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("DB.closeConnection() " + e);
			}
		}
	}

	@Override
	public int getID(PreparedStatement ps) {
		int id = 0;

		try {
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			System.out.println("DB.getID() " + e);
		}

		return id;
	}

	@Override
	public TableDefinition getTableDefinition(String name) {

		TableDefinition td = null;
		ResultSet rs;

		try {
			if (journalTitles == null)
				journalTitles = getConnection()
						.prepareStatement("SELECT id, title, fromView, orderBy FROM journal_titles WHERE name = ?");
			if (journalColumns == null)
				journalColumns = getConnection().prepareStatement("SELECT caption, source, display_class, width, sumup "
						+ " FROM journal_columns WHERE title_id = ? ORDER BY nono ");

			journalTitles.setString(1, name);
			rs = journalTitles.executeQuery();
			if (rs.next())
				td = new TableDefinition(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
			rs.close();

			if (td != null) {
				journalColumns.setInt(1, td.id);
				rs = journalColumns.executeQuery();
				while (rs.next())
					td.addColumnDefinition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getBoolean(5));
				rs.close();
			}
		} catch (SQLException e) {
			System.out.println("DB.getTableDefinition() " + e);
		}

		return td;
	}

	@Override
	public String getNumber(String document) {
		String result = "none";
		ResultSet rs;

		try {
			getConnection().setAutoCommit(false);

			if (numberUpdate == null)
				numberUpdate = getConnection()
						.prepareStatement("UPDATE autonumberer SET counter = counter + 1 WHERE document = ?");

			if (numberSelect == null)
				numberSelect = getConnection().prepareStatement(
						"SELECT COALESCE(prefix, '')||LPAD(''||(counter-1), LENGTH(''||last_value), '0')||COALESCE(suffix, ''), "
								+ "counter, first_value, last_value FROM autonumberer WHERE document = ?");

			if (numberReset == null)
				numberReset = getConnection()
						.prepareStatement("UPDATE autonumberer SET counter = ? WHERE document = ?");

			numberUpdate.setString(1, document);
			numberUpdate.execute();

			int counter = 0;
			int firstValue = 0;
			int lastValue = 0;

			numberSelect.setString(1, document);
			rs = numberSelect.executeQuery();
			if (rs.next()) {
				result = rs.getString(1);
				counter = rs.getInt(2);
				firstValue = rs.getInt(3);
				lastValue = rs.getInt(4);
			}
			rs.close();

			if (firstValue < lastValue && counter > lastValue) {
				numberReset.setInt(1, firstValue);
				numberReset.setString(2, document);
				numberReset.execute();
			}

			getConnection().commit();

		} catch (SQLException e) {
			System.out.println("DB.getNumber() " + e);
			try {
				getConnection().rollback();
			} catch (SQLException e1) {
				System.out.println("DB.getNumber()\n" + "getConnection().rollback()\n" + e1);
			}
		} finally {
			try {
				getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				System.out.println("DB.getNumber()\n" + "getConnection().setAutoCommit(true)\n" + e);
			}
		}

		return result;
	}

	private Mes mes;
	private Proper pro;
	private Connection connection;

	private static PreparedStatement journalTitles = null;
	private static PreparedStatement journalColumns = null;

	private static PreparedStatement numberUpdate = null;
	private static PreparedStatement numberSelect = null;
	private static PreparedStatement numberReset = null;

}
