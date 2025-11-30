package game.weekend.framework.utility.winmenu;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;

import game.weekend.framework.core.Acts;
import game.weekend.framework.core.IntFrame;
import game.weekend.framework.core.Loc;
import game.weekend.framework.core.MainFrame;

/**
 * "Расположить каскадом".
 */
@SuppressWarnings("serial")
public class ActCascade extends AbstractAction {

	public ActCascade(MainFrame mainFrame) {
		this.mainFrame = mainFrame;

		putValue(Action.NAME, Loc.get("cascade"));
		putValue(Action.SHORT_DESCRIPTION, Loc.get("cascade_the_app_windows"));
		putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource(Acts.IMAGE_PATH + "cascade.gif")));
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {

		JInternalFrame[] frames = mainFrame.getAllFrames();

		if (frames.length > 0) {
			Arrays.sort(frames);
			Dimension size = mainFrame.getDesktopSize();

			int n = (int) (size.width * 0.2);
			int w = size.width - n;
			int h = size.height - n;

			for (int i = 0; i < frames.length; ++i) {
				((IntFrame) frames[i]).setLocation(i * SHIFT, i * SHIFT);
				((IntFrame) frames[i]).setSize(w, h);
			}
		}
	}

	private static final int SHIFT = 20; // Смещение окон

	private MainFrame mainFrame;
}
