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
 * "Расположить рядом".
 */
@SuppressWarnings("serial")
public class ActTiled extends AbstractAction {

	public ActTiled(MainFrame mainFrame) {
		this.mainFrame = mainFrame;

		putValue(Action.NAME, Loc.get("tiled"));
		putValue(Action.SHORT_DESCRIPTION, Loc.get("tiled_the_app_windows"));
		putValue(Action.SMALL_ICON, new ImageIcon(getClass().getResource(Acts.IMAGE_PATH + "tiled.gif")));
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {

		JInternalFrame[] frames = mainFrame.getAllFrames();

		if (frames.length > 0) {
			Arrays.sort(frames);

			int cols = (int) Math.ceil(Math.sqrt(frames.length));
			int rows = (int) Math.ceil((double) frames.length / (double) cols);

			Dimension size = mainFrame.getDesktopSize();

			int h = (int) (size.height / rows);
			int norm = (int) (size.width / cols);
			int last = (int) (size.width / (cols - (cols * rows - frames.length)));

			if (h < MINHIGHT || norm < MINWШDTH) {
				mainFrame.getMes().err("Слишком мало места или очень много окон.");
				return;
			}

			int k = 0;
			for (int i = 0; i < rows; ++i) {
				for (int j = 0; j < cols; ++j) {
					if (k < frames.length) {

						int w = norm;
						if (i == (rows - 1)) {
							w = last;
						}

						((IntFrame) frames[k]).setLocation(j * w, i * h);

						if (j == (cols - 1) || k == (frames.length - 1)) {
							w = size.width - j * w;
						}

						((IntFrame) frames[k]).setSize(w, h);
						++k;
					}
				}
			}
		}
	}

	private static final int MINHIGHT = 100; // Минимальная высота окна
	private static final int MINWШDTH = 100; // Минимальная ширина окна

	private MainFrame mainFrame;
}
