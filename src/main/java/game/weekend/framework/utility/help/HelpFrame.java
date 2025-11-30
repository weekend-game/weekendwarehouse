package game.weekend.framework.utility.help;

import java.awt.Container;
import java.awt.Cursor;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Document;

import game.weekend.framework.core.FrameManager;
import game.weekend.framework.core.IntFrame;
import game.weekend.framework.core.Loc;

/**
 * "Справка".
 * 
 * Файл справки должен называться help.html и должен быть расположен в текущей
 * папке запуска приложения.
 */
@SuppressWarnings("serial")
public class HelpFrame extends IntFrame {

	public final static String helpFileName = "help.html";

	/**
	 * Создать окно отображения справки.
	 */
	public HelpFrame(int id, int mode, FrameManager parentFrameManager) {
		super(id, mode, parentFrameManager);
		setTitle(Loc.get("help"));
		HelpPane hp = new HelpPane(helpFileName);
		setContentPane(hp);
	}

	/**
	 * Панель отображения справки.
	 */
	private class HelpPane extends JScrollPane implements HyperlinkListener {

		/**
		 * Создать панель отображения справки.
		 * 
		 * @param helpFile имя файла справки.
		 */
		public HelpPane(String helpFile) {
			try {
				File f = new File(helpFile);
				String s = "file:" + f.getPath();
				pane = new JEditorPane(new URL(s));
				pane.setEditable(false);
				pane.addHyperlinkListener(this);
				getViewport().add(pane);
			} catch (MalformedURLException e) {
				System.out.println("HelpPane()\n" + e);
			} catch (IOException e) {
				System.out.println("HelpPane()\n" + e);
			}
		}

		@Override
		public void hyperlinkUpdate(HyperlinkEvent e) {
			if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				Cursor cur = pane.getCursor();
				Cursor waitCur = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
				pane.setCursor(waitCur);
				SwingUtilities.invokeLater(new HelpLoader(e.getURL(), cur, pane));
			}
		}

		private JEditorPane pane;
	}

	/**
	 * Загрузка файла справки.
	 */
	private class HelpLoader implements Runnable {

		HelpLoader(URL url, Cursor cursor, JEditorPane pane) {
			this.url = url;
			this.cursor = cursor;
			this.pane = pane;
		}

		@Override
		public void run() {
			if (url == null) {
				pane.setCursor(cursor);
				Container parent = pane.getParent();
				parent.repaint();
			} else {
				Document doc = pane.getDocument();
				try {
					pane.setPage(url);
				} catch (IOException ioe) {
					pane.setDocument(doc);
				} finally {
					url = null;
					SwingUtilities.invokeLater(this);
				}
			}
		}

		private URL url;
		private Cursor cursor;
		private JEditorPane pane;
	}
}
