package game.weekend.framework.utility.winmenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.ButtonGroup;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import game.weekend.framework.core.MainFrame;

/**
 * Меню "Окна".
 */
public class WinListener implements MenuListener {

	public WinListener(MainFrame mainFrame, JMenu windowMenu) {
		super();
		this.mainFrame = mainFrame;
		this.windowMenu = windowMenu;
		actCascade = new ActCascade(mainFrame);
		actTiled = new ActTiled(mainFrame);
	}

	@Override
	public void menuSelected(MenuEvent e) {

		windowMenu.removeAll();
		windowMenu.add(actCascade);
		windowMenu.add(actTiled);

		ButtonGroup btgWindow = new ButtonGroup();
		JInternalFrame[] frames = (JInternalFrame[]) mainFrame.getAllFrames();

		if (frames.length == 0) {
			actCascade.setEnabled(false);
			actTiled.setEnabled(false);
		} else {
			actCascade.setEnabled(true);
			actTiled.setEnabled(true);

			windowMenu.add(new JSeparator());

			Arrays.sort(frames);
			JInternalFrame curframe = mainFrame.getSelectedFrame();

			for (int i = 0; i < frames.length && i < MAXWIN; ++i) {
				JRadioButtonMenuItem itm = new JRadioButtonMenuItem();
				itm.setText(frames[i].getTitle());

				itm.setSelected(frames[i] == curframe);

				itm.addActionListener(new ALWindow(frames[i]));
				windowMenu.add(itm);
				btgWindow.add(itm);
			}

			if (frames.length > MAXWIN) {
				windowMenu.add(new ActMore(mainFrame));
			}
		}
	}

	@Override
	public void menuDeselected(MenuEvent e) {
	}

	@Override
	public void menuCanceled(MenuEvent e) {
	}

	class ALWindow implements ActionListener {

		ALWindow(JInternalFrame internalFrame) {
			this.internalFrame = internalFrame;
		}

		public void actionPerformed(ActionEvent actionEvent) {
			try {
				internalFrame.setSelected(true);
			} catch (java.beans.PropertyVetoException e) {
			}
		}

		JInternalFrame internalFrame;
	}

	private static final int MAXWIN = 5; // Количество окон в списке

	private MainFrame mainFrame;
	private JMenu windowMenu;
	private ActCascade actCascade;
	private ActTiled actTiled;
}
