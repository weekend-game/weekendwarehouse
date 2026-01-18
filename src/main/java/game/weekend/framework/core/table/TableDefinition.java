package game.weekend.framework.core.table;

import java.util.ArrayList;

import game.weekend.framework.core.Loc;

/**
 * Определение отображения таблицы.
 */
public class TableDefinition {
	/** ID определения таблицы */
	public final int id;

	/** Название таблицы. Обычно отображается в заголовке окна. */
	public final String title;

	/** Представление для выборки информации. */
	public final String fromView;

	/** Номер колонки по которой таблица отсортирована. */
	public final int orderBy;

	/** Атрибуты колонок. */
	public final ArrayList<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();

	/** Атрибуты колонки. */
	public class ColumnDefinition {

		/** Источник. */
		public final String source;
		/** Заголовок. */
		public final String caption;
		/** Класс используемый для отображения в JTable. */
		public final String displayClass;
		/** Ширина. */
		public final int width;
		/** Признак необходимости расчитывать сумму по колонке. */
		public final boolean sumup;

		/** Создать объект атрибутов колонки. */
		public ColumnDefinition(String caption, String source, String displayClass, int width, boolean sumup) {
			this.caption = Loc.get(caption.trim());
			this.source = source.trim();
			this.displayClass = (displayClass == null) ? "java.lang.String" : displayClass.trim();
			this.width = width;
			this.sumup = sumup;
		}
	}

	/**
	 * Создать определение отображения таблицы.
	 */
	public TableDefinition(int id, String title, String fromView, int orderBy) {
		this.id = id;
		this.title = Loc.get(title);
		this.fromView = fromView;
		this.orderBy = orderBy;
	}

	/**
	 * Добавить колонку в определение отображения таблицы.
	 */
	public void addColumnDefinition(String caption, String source, String displayClass, int width, boolean sumup) {
		columns.add(new ColumnDefinition(caption, source, displayClass, width, sumup));
	}
}
