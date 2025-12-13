package game.weekend.framework.core.table;

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
	 * @param s
	 *            сортировщик.
	 */
	public void setSorter(TableRowSorter<ITableModel> sorter);
}
