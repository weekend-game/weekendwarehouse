package game.weekend.warehouse;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import game.weekend.framework.core.Acts;
import game.weekend.framework.core.Loc;
import game.weekend.framework.core.MainFrame;

/**
 * "Группы организаций".
 */
@SuppressWarnings("serial")
public class ActGroupsOfCompanies extends AbstractAction {

	public ActGroupsOfCompanies(MainFrame mainFrame) {
		this.mainFrame = mainFrame;

		putValue(Action.NAME, Loc.get("groups_of_companies"));
		putValue(Action.SHORT_DESCRIPTION, Loc.get("groups_of_companies"));
		putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource(Acts.IMAGE_PATH + "empty.gif")));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		mainFrame.getMes().notice((String) getValue(Action.SHORT_DESCRIPTION) + " - " + Loc.get("not_implemented"));
	}

	private MainFrame mainFrame;
}

