package game.weekend.framework.core;

import java.util.HashMap;
import java.util.Map;

import game.weekend.framework.core.controls.IControl;

public abstract class DocData {

	public DocData(IntFrame frame) {
		mes = frame.getMainFrame().getMes();
		pro = frame.getMainFrame().getPro();
	}

	/**
	 * Сохранить документ в БД или каком-то ещё месте хранения.
	 * 
	 * @return true если данные успешно сохранены.
	 */
	public abstract boolean save();

	/**
	 * Установить значение поля данных.
	 * 
	 * При чтении данных (обычно в конструкторе ) этот метод использунтся для
	 * размещения прочитанных данных.
	 * 
	 * @param name  имя поля.
	 * @param value значение поля.
	 */
	public void setValue(String name, Object value) {
		fields.remove(name);
		fields.put(name, value);
	}

	/**
	 * Получить значение поля.
	 * 
	 * Будет выдано мсходное значение поля, без возможно внесенных пользователем
	 * правок. Исправления пользователя будут учтены только после вызова метода
	 * update().
	 * 
	 * @param name имя поля.
	 * @return значение поля.
	 */
	public Object getValue(String name) {
		return fields.get(name);
	}

	/**
	 * Зарегистрировать объект ввода.
	 * 
	 * @param name    имя поля.
	 * @param control объект ввода.
	 */
	public void setControl(String name, IControl<?> control) {
		controls.remove(name);
		controls.put(name, control);
	}

	/**
	 * Получить объект ввода.
	 * 
	 * @param name имя поля.
	 * @return объект ввода.
	 */
	public IControl<?> getControl(String name) {
		return controls.get(name);
	}

	/**
	 * Проверить наличие изменений значений.
	 * 
	 * @return true если информация изменена.
	 */
	public boolean hasChanges() {
		for (IControl<?> g : controls.values()) {
			if (g.hasChange()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Проверить правильность ввода значений в компонентах ввода.
	 * 
	 * @return true если информация введена правильно.
	 */
	public boolean isOK(Mes mes) {
		String m;
		for (IControl<?> g : controls.values()) {
			m = g.valid();
			if (m != null) {
				mes.err(m);
				g.getComp().requestFocus();
				return false;
			}
		}
		return true;
	}

	/**
	 * Сохранить изменения компонентов ввода в полях данных.
	 */
	public void update() {
		for (IControl<?> g : controls.values()) {
			g.save();
		}
	}

	public final Mes getMes() {
		return mes;
	}

	public final Proper getPro() {
		return pro;
	}

	private final Mes mes;
	private final Proper pro;
	private Map<String, Object> fields = new HashMap<String, Object>();
	private Map<String, IControl<?>> controls = new HashMap<String, IControl<?>>();
}
