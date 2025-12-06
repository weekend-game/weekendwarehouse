package game.weekend.framework.core.controls;

import javax.swing.JComponent;

/**
 * Объект ввода.
 * 
 * @param <T> тип вводимого значения.
 */
public interface IControl<T> {

	/**
	 * Получить введёное значение.
	 * 
	 * @return введённое значение.
	 */
	T getValue();

	/**
	 * Проверить наличие изменений заначения.
	 * 
	 * @return true, если значение изменено, false, если значение не изменилось.
	 */
	boolean hasChange();

	/**
	 * Проверить правильность введённого значения.
	 * 
	 * @return null, если введено допустимое значение, строка с сообщением, если
	 *         нет.
	 */
	String valid();

	/**
	 * Сохранить введённое значение в объекте данных.
	 */
	void save();

	/**
	 * Установить активность компонента.
	 * 
	 * @param enabled активность компонента.
	 */
	void setEnable(boolean enabled);

	/**
	 * Определить активность компонента.
	 * 
	 * @param enabled активность компонента.
	 */
	boolean isEnable();

	/**
	 * Получить JComponent.
	 * 
	 * @return объект класса JComponent.
	 */
	JComponent getComp();
}
