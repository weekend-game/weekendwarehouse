package game.weekend.framework.core;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowAdapter;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

/**
 * Основное окно приложения.
 */
public class MainFrame {

	/**
	 * Создать основное окно приложения.
	 * 
	 * @param appName
	 *            название приложения.
	 * @param icon
	 *            пиктограмма приложения.
	 * @param pro
	 *            объект локального хранения свойств приложения.
	 * @param close
	 *            слушатель с методом вызыаемым при закрытии окна.
	 */
	public MainFrame(String appName, String icon, Proper pro,
			WindowAdapter close) {
		this.pro = pro;

		frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		pro.setBounds(frame);

		frame.setTitle(appName);

		if (icon != null) {
			ImageIcon i = new ImageIcon(frame.getClass().getResource(icon));
			frame.setIconImage(i.getImage());
		}

		Container cp = frame.getContentPane();
		cp.setLayout(new BorderLayout());

		deskTop = new JDesktopPane();
		cp.add(BorderLayout.CENTER, deskTop);

		if (close != null) {
			frame.addWindowListener(close);
		}
	}

	/**
	 * Отобразить основное окно приложения.
	 */
	public void show() {
		frame.setVisible(true);
	}

	/**
	 * Закрыть основное окно приложения.
	 */
	public boolean close() {
		pro.saveBounds(frame);
		pro.save();
		frame.dispose();
		return true;
	}

	/**
	 * Установить меню приложения.
	 * 
	 * @param menuBar
	 *            меню приложения.
	 */
	public void setMenuBar(JMenuBar menuBar) {
		if (menuBar != null) {
			frame.setJMenuBar(menuBar);
		}
	}

	/**
	 * Установить инструментальную линейку приложения.
	 * 
	 * @param toolBar
	 *            инструментальная линейка приложения.
	 */
	public void setToolBar(JToolBar toolBar) {
		Container cp = frame.getContentPane();
		cp.add(toolBar, BorderLayout.NORTH);
	}

	/**
	 * Установить статусную строку приложения.
	 * 
	 * @param statusBar
	 *            статусная строка приложения.
	 */
	public void setStatusBar(StatusBar statusBar) {
		if (statusBar != null) {
			Container cp = frame.getContentPane();
			cp.add(statusBar.getPanel(), BorderLayout.SOUTH);
		}
	}

	/**
	 * Получить объект локального хранения свойств приложения.
	 * 
	 * @return объект локального хранения свойств приложения.
	 */
	public Proper getPro() {
		return pro;
	}

	/**
	 * Установить объект выдачи сообщений и запросов.
	 * 
	 * @param mes
	 *            объект выдачи сообщений и запросов.
	 */
	public void setMes(Mes mes) {
		this.mes = mes;
	}

	/**
	 * Получить объект выдачи сообщений и запросов.
	 * 
	 * @return объект выдачи сообщений и запросов.
	 */
	public Mes getMes() {
		return mes;
	}

	/**
	 * Установить объект действий приложения.
	 * 
	 * @param act
	 *            объект действий приложения.
	 */
	public void setActs(Acts acts) {
		this.acts = acts;
	}

	/**
	 * Получить объект действий приложения.
	 * 
	 * @return действий приложения.
	 */
	public Acts getActs() {
		return acts;
	}

	/**
	 * Получить JFrame приложения.
	 * 
	 * @return JFrame приложения.
	 */
	public JFrame getFrame() {
		return frame;
	}

	private JFrame frame;
	private JDesktopPane deskTop;
	private Proper pro;
	private Mes mes;
	private Acts acts;
}
