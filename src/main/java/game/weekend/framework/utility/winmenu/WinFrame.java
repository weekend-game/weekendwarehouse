package game.weekend.framework.utility.winmenu;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import game.weekend.framework.core.FrameManager;
import game.weekend.framework.core.GBL;
import game.weekend.framework.core.IntFrame;
import game.weekend.framework.core.Loc;

/**
 * Работа со списком активных окон приложения
 */
@SuppressWarnings("serial")
public class WinFrame extends IntFrame {

	public WinFrame(int id, int mode, FrameManager parentFrameManager) {
		super(id, mode, parentFrameManager);

		String winTitle = Loc.get("windows");
		setTitle(winTitle);

		model = new DefaultListModel<>();
		list = new JList<IntFrame>(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		pane = new JScrollPane(list);
		pane.setPreferredSize(new Dimension(220, 250));

//		frame = getMainFrame().getAllFrames();
//		Arrays.sort(frame);
//		model.clear();
//		for (int i = 0; i < frame.length; ++i) {
//			if (!frame[i].getTitle().equalsIgnoreCase(winTitle)) {
//				model.addElement((IntFrame) frame[i]);
//			}
//		}
//
//		list.setSelectedIndex(frame.length - 2);

		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() == 2) {
					cmdSelect();
				}
			}
		});

		btnSelect = new JButton(Loc.get("select"));
		btnSelect.setPreferredSize(new Dimension(100, 24));
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cmdSelect();
			}
		});

		btnExit = new JButton(Loc.get("close"));
		btnExit.setPreferredSize(new Dimension(100, 24));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});

		getRootPane().setDefaultButton(btnSelect);

		JPanel p = (JPanel) getContentPane();
		GBL g = new GBL(p, true);

		g.addFixL(new JLabel(Loc.get("select_window")), 8);

		g.newLine();
		g.addExtB(pane, 7, 7);
		g.addFixR(btnSelect, 1);

		g.newLine();
		g.addFixR(new JLabel(""), 7);
		g.addFixR(btnExit, 1);
	}

	public void activated() {
		frame = getMainFrame().getAllFrames();
		Arrays.sort(frame);

		String winTitle = Loc.get("windows");
		model.clear();
		for (int i = 0; i < frame.length; ++i) {
			if (!frame[i].getTitle().equalsIgnoreCase(winTitle)) {
				model.addElement((IntFrame) frame[i]);
			}
		}

		list.setSelectedIndex(frame.length - 2);
	}

	private void cmdSelect() {
		int index = list.getSelectedIndex();
		if (index >= 0) {
			IntFrame f = (IntFrame) model.get(index);
			try {
				f.setSelected(true);
			} catch (Exception e) {
			}
		}
		cancel();
	}

	private JList<IntFrame> list;
	private JScrollPane pane;
	private DefaultListModel<IntFrame> model;
	private JButton btnSelect;
	private JButton btnExit;
	private JInternalFrame[] frame;
}
