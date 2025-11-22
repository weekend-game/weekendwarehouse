package game.weekend.framework.core;

import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;

/**
 * Дествия (Actions).
 */
public class Acts {

	public static final String IMAGE_PATH = "/game/weekend/framework/images/";

	/**
	 * Поместить в список действий название действия и сответствующий ему объект
	 * Action.
	 * 
	 * @param name
	 *            название действия.
	 * @param act
	 *            сответствующий ему объект Action.
	 */
	public void putAct(String name, AbstractAction act) {
		acts.put(name, act);
	}

	/**
	 * Удалить из списка действий указанное действие.
	 * 
	 * @param name
	 *            наименование удаляемого действия.
	 */
	public void removeAct(String name) {
		acts.remove(name);
	}

	/**
	 * Поучить из списка действий указанное действие.
	 * 
	 * @param name
	 *            наименование получаемого действия.
	 * @return действие (Action).
	 */

	public AbstractAction getAct(String name) {
		return acts.get(name);
	}

	/**
	 * Установить активность действия.
	 * 
	 * @param name
	 *            наименование действия.
	 * @param value
	 *            состояние активности.
	 */
	public void setEnabled(String name, boolean value) {
		AbstractAction act = acts.get(name);
		if (act != null) {
			act.setEnabled(value);
		}
	}

	private Map<String, AbstractAction> acts = new HashMap<String, AbstractAction>();
}
