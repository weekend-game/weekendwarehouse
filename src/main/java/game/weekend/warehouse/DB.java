package game.weekend.warehouse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import game.weekend.framework.core.IDB;
import game.weekend.framework.core.Loc;
import game.weekend.framework.core.Mes;
import game.weekend.framework.core.Proper;

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

	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("DB.closeConnection() " + e);
			}
		}
	}

	private Mes mes;
	private Proper pro;
	private Connection connection;
}
