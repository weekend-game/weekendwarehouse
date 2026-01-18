package game.weekend.framework.core;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import game.weekend.framework.core.controls.IControl;

public abstract class DocData {

	public DocData(IntFrame frame) {
		this.frame = frame;
		this.mes = frame.getMainFrame().getMes();
		this.statusBar = frame.getMainFrame().getStatusBar();
		this.db = frame.getMainFrame().getDB();
		this.pro = frame.getMainFrame().getPro();
	}

	/**
	 * Сохранить документ в БД или каком-то ещё месте хранения.
	 * 
	 * @return true если данные успешно сохранены.
	 */
	public abstract boolean save();

	/**
	 * Удалить документ.
	 * 
	 * @return true если данные успешно удалены.
	 */
	public abstract boolean delete() throws Exception;

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
	 * Установить строковое значение поля данных.
	 * 
	 * При чтении данных (обычно в конструкторе) этот метод использунтся для
	 * размещения прочитанных данных.
	 * 
	 * @param name  имя поля.
	 * @param value значение поля.
	 */
	public void setStringValue(String name, String value) {
		if (value == null) {
			value = "";
		} else {
			value = ((String) value).trim();
		}
		fields.remove(name);
		fields.put(name, value);
	}

	/**
	 * Получить строковое значение поля.
	 * 
	 * Будет выдано мсходное значение поля, без возможно внесенных пользователем
	 * правок. Исправления пользователя будут учтены только после вызова метода
	 * update().
	 * 
	 * @param name имя поля.
	 * @return значение поля.
	 */
	public String getStringValue(String name) {
		String s = (String) fields.get(name);
		if (s != null) {
			s = s.trim();
			if (s.length() == 0) {
				s = null;
			}
		}
		return s;
	}

	/**
	 * Установить логическое значение поля данных.
	 * 
	 * При чтении данных (обычно в конструкторе) этот метод использунтся для
	 * размещения прочитанных данных.
	 * 
	 * @param name  имя поля.
	 * @param value значение поля.
	 */
	public void setBooleanValue(String name, Boolean value) {
		if (value == null) {
			value = false;
		} else {
			value = (Boolean) value;
		}
		fields.remove(name);
		fields.put(name, value);
	}

	/**
	 * Получить логическое значение поля.
	 * 
	 * Будет выдано мсходное значение поля, без возможно внесенных пользователем
	 * правок. Исправления пользователя будут учтены только после вызова метода
	 * update().
	 * 
	 * @param name имя поля.
	 * @return значение поля.
	 */
	public Boolean getBooleanValue(String name) {
		Boolean b = (Boolean) fields.get(name);
		if (b == null)
			b = false;
		return b;
	}

	/**
	 * Установить значение поля данных типа Integer.
	 * 
	 * При чтении данных (обычно в конструкторе) этот метод использунтся для
	 * размещения прочитанных данных.
	 * 
	 * @param name  имя поля.
	 * @param value значение поля.
	 */
	public void setIntegerValue(String name, Integer value) {
		if (value == null) {
			value = 0;
		} else {
			value = (Integer) value;
		}
		fields.remove(name);
		fields.put(name, value);
	}

	/**
	 * Получить значение поля типа Integer.
	 * 
	 * Будет выдано мсходное значение поля, без возможно внесенных пользователем
	 * правок. Исправления пользователя будут учтены только после вызова метода
	 * update().
	 * 
	 * @param name имя поля.
	 * @return значение поля.
	 */
	public Integer getIntegerValue(String name) {
		Integer i = (Integer) fields.get(name);
		if (i == null)
			i = 0;
		return i;
	}

	/**
	 * Установить значение поля данных типа BigDecimal.
	 * 
	 * При чтении данных (обычно в конструкторе) этот метод использунтся для
	 * размещения прочитанных данных.
	 * 
	 * @param name  имя поля.
	 * @param value значение поля.
	 */
	public void setBigDecimalValue(String name, BigDecimal value) {
		if (value == null) {
			value = new BigDecimal(0);
		} else {
			value = (BigDecimal) value;
		}
		fields.remove(name);
		fields.put(name, value);
	}

	public void setBigDecimalValue(String name, double value) {
		BigDecimal b = null;
		b = new BigDecimal(value);
		setBigDecimalValue(name, b);
	}

	/**
	 * Получить значение поля типа BigDecimal.
	 * 
	 * Будет выдано мсходное значение поля, без возможно внесенных пользователем
	 * правок. Исправления пользователя будут учтены только после вызова метода
	 * update().
	 * 
	 * @param name имя поля.
	 * @return значение поля.
	 */
	public BigDecimal getBigDecimalValue(String name) {
		BigDecimal d = (BigDecimal) fields.get(name);
		if (d == null)
			d = new BigDecimal("0");
		return d;
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
		for (IControl<?> g : controls.values())
			g.save();
	}

	public boolean InList(int id, ArrayList<DocData> arrayListData) {
		boolean result = false;
		for (DocData dd : arrayListData) {
			if ((int) dd.getValue("id") == id) {
				result = true;
				break;
			}
		}
		return result;
	}

	public final Mes getMes() {
		return mes;
	}

	public final StatusBar getStatusBar() {
		return statusBar;
	}

	public final IDB getDB() {
		return db;
	}

	public final Proper getPro() {
		return pro;
	}

	/**
	 * Получить окно.
	 * 
	 * @return окно.
	 */
	public final IntFrame getFrame() {
		return frame;
	}

	private final IntFrame frame;
	private final Mes mes;
	private final StatusBar statusBar;
	private final IDB db;
	private final Proper pro;
	private Map<String, Object> fields = new HashMap<String, Object>();
	private Map<String, IControl<?>> controls = new HashMap<String, IControl<?>>();
}
