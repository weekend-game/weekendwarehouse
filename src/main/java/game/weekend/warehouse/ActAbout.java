package game.weekend.warehouse;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import game.weekend.framework.core.Acts;
import game.weekend.framework.core.Loc;
import game.weekend.framework.core.Mes;

/**
 * "О программе...".
 */
@SuppressWarnings("serial")
public class ActAbout extends AbstractAction {

	public ActAbout(Mes mes) {
		this.mes = mes;

		putValue(Action.NAME, Loc.get("about") + "...");
		putValue(Action.SHORT_DESCRIPTION, Loc.get("about"));
		putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource(Acts.IMAGE_PATH + "empty.gif")));
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		String str = "\n" + WeekendWarehouse.APP_NAME + "\n\n" + Loc.get("version") + " " + WeekendWarehouse.APP_VERSION
				+ " " + Loc.get("from") + " " + WeekendWarehouse.APP_DATE + "\n" + WeekendWarehouse.APP_COPYRIGHT
				+ "\n\n" + Loc.get(WeekendWarehouse.APP_OTHER) + "\n\n";

		mes.inf(Loc.get("about"), str);
	}

	private Mes mes;
}
