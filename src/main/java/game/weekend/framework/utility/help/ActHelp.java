package game.weekend.framework.utility.help;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import game.weekend.framework.core.Acts;
import game.weekend.framework.core.Loc;
import game.weekend.framework.core.MainFrame;
import game.weekend.framework.core.acts.ActFrame;

/**
 * "Справка".
 */
@SuppressWarnings("serial")
public class ActHelp extends ActFrame {

	public ActHelp(MainFrame mainFrame) {
		super(mainFrame, "game.weekend.framework.utility.help.HelpFrame");
		putValue(Action.NAME, Loc.get("help"));
		putValue(Action.SHORT_DESCRIPTION, Loc.get("description_of_the_program"));
		putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource(Acts.IMAGE_PATH + "help.gif")));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
	}
}
