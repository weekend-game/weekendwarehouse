package game.weekend.framework.utility.progprop;

import game.weekend.framework.core.DocData;
import game.weekend.framework.core.IntFrame;
import game.weekend.framework.core.Proper;

/**
 * Объект данных "Настройка программы...".
 */
class ProgPropData extends DocData {

	/**
	 * Создать объект данных "Настройка программы...".
	 */
	public ProgPropData(IntFrame frame) {
		super(frame);

		Proper pro = getPro();
		setValue("id", 0);
		setValue("driver", pro.getProperty("driver", "org.apache.derby.jdbc.ClientDriver"));
		setValue("constr", pro.getProperty("constr", "jdbc:derby://localhost:1527/PrimroseDB"));
		setValue("language", pro.getProperty("language", "EN"));
	}

	@Override
	public boolean save() {
		Proper pro = getPro();
		pro.setProperty("driver", (String) getValue("driver"));
		pro.setProperty("constr", (String) getValue("constr"));
		pro.setProperty("language", (String) getValue("language"));
		pro.save();
		return true;
	}
}
