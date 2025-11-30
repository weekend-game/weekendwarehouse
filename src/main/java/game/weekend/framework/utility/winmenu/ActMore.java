package game.weekend.framework.utility.winmenu;

import javax.swing.Action;

import game.weekend.framework.core.Loc;
import game.weekend.framework.core.MainFrame;
import game.weekend.framework.core.acts.ActFrame;

/**
 * "Другие окна..."
 */
@SuppressWarnings("serial")
public class ActMore extends ActFrame {

	public ActMore(MainFrame mainFrame) {
		super(mainFrame, "game.weekend.framework.utility.winmenu.WinFrame");
		putValue(Action.NAME, Loc.get("other_windows") + "...");
		putValue(Action.SHORT_DESCRIPTION, Loc.get("display_a_list_of_active_application_windows"));
	}
}
