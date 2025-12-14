package game.weekend.framework.core.table;

import java.sql.SQLException;

import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * Расширенная модель таблицы. Вводится отдельный метод для загрузки информации,
 * методы преобразования номера записи и её ID и методы обрабатывающие изменения
 * данных в таблице БД.
 */
public interface ITableModel extends TableModel {

	/**
	 * Загрузить информацию в модель.
	 * 
	 * @throws Exception
	 */
	public void load() throws Exception;

	/**
	 * Установить сортировщик.
	 * 
	 * @param s сортировщик.
	 */
	public void setSorter(TableRowSorter<ITableModel> sorter);

	/**
	 * Получить ID строки по её номеру в таблице.
	 * 
	 * @param row
	 * @return
	 */
	public int getIdByRow(int row);

	/**
	 * Получить номер строки таблицы по её ID.
	 * 
	 * @param id ID строки.
	 * @return номер строки в таблице.
	 */
	public int getRowById(int id);

	/**
	 * Обработать добавление а таблицу БД новой записи. Метод читает новую запись из
	 * БД и добавляет её в свой буфер хранения.
	 * 
	 * @param id ID добавленной строки.
	 * @return номер добавленной строки в таблице.
	 * @throws SQLException
	 */
	public int added(int id) throws SQLException;

	/**
	 * Обработать изменение в таблице БД записи. Метод читает изменённую запись из
	 * БД и обновляет её в свём буфере хранения.
	 * 
	 * @param id ID добавленной строки.
	 * @return номер добавленной строки в таблице.
	 * @throws SQLException
	 */
	public int edited(int id) throws SQLException;

	/**
	 * Обработать удаление записи в таблице БД. Метод удалят соответствующую запись
	 * из своего буфера хранения.
	 * 
	 * @param row
	 */
	public void deleted(int row);
}
