package game.weekend.framework.core;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
	 * Получить ID добавленной записи.
	 * 
	 * ps выполненная команда в результате которой было сгенерированно новое id.
	 */
	int getID(PreparedStatement ps);

	/**
	 * Получить определение отображения таблицы.
	 * 
	 * @param name наименованте определения.
	 * @return определение отображения таблицы.
	 */
	public TableDefinition getTableDefinition(String name);

	/**
	 * Получить автономер документа.
	 * 
	 * @param document наименование документа.
	 * @return автономер документа.
	 */
	String getNumber(String document);

}
