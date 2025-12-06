package game.weekend.framework.utility.progprop;

import javax.swing.Action;

import game.weekend.framework.core.Loc;
import game.weekend.framework.core.MainFrame;
import game.weekend.framework.core.acts.ActFrame;

/**
 * "Настройка программы..."
 */
@SuppressWarnings("serial")
public class ActProgProp extends ActFrame {

	public ActProgProp(MainFrame mainFrame) {
		super(mainFrame, "game.weekend.framework.utility.progprop.ProgPropDoc");
		putValue(Action.NAME, Loc.get("setting_up_the_program") + "...");
		putValue(Action.SHORT_DESCRIPTION, Loc.get("setting_up_program_parameters"));
	}
}
