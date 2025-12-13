package game.weekend.framework.core;

import java.sql.Connection;

import game.weekend.framework.core.table.TableDefinition;

/**
 * Объект для работы с БД.
 */
public interface IDB {

	/**
	 * Получить соединение с БД.
	 * 
	 * @return Созденинение с БД.
	 */
	Connection getConnection();

	/**
	 * Завершить работу с БД.
	 */
	void closeConnection();

	/**
	 * Получить определение отображения таблицы.
	 * 
	 * @param name наименованте определения.
	 * @return определение отображения таблицы.
	 */
	public TableDefinition getTableDefinition(String name);

}
