package game.weekend.warehouse.directories;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import game.weekend.framework.core.Acts;
import game.weekend.framework.core.Loc;
import game.weekend.framework.core.MainFrame;
import game.weekend.framework.core.acts.ActFrame;

/**
 * "Группы товаров".
 */
@SuppressWarnings("serial")
public class GroupsOfProductsAction extends ActFrame {

	public GroupsOfProductsAction(MainFrame mainFrame) {
		super(mainFrame, "game.weekend.warehouse.directories.GroupsOfProductsJournal");
		putValue(Action.NAME, Loc.get("groups_of_products"));
		putValue(Action.SHORT_DESCRIPTION, Loc.get("groups_of_products"));
		putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource(Acts.IMAGE_PATH + "empty.gif")));
		putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.SHIFT_DOWN_MASK | InputEvent.ALT_DOWN_MASK));
	}

}
