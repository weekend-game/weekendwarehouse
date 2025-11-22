package game.weekend.framework.core;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Выдача сообщений и запросов.
 */
public class Mes {

	public static final int YES = JOptionPane.YES_OPTION;
	public static final int NO = JOptionPane.NO_OPTION;
	public static final int CANCEL = JOptionPane.CANCEL_OPTION;

	/**
	 * Создать объект выдачи сообщений и запросов.
	 * 
	 * @param appName
	 *            имя приложения, которое будет отображаться в заголовке окон
	 *            сообщений и запросов..
	 * @param mainFrame
	 *            окно относительно которого будут центрироваться окна сообщений
	 *            и запросов.
	 */
	public Mes(String appName, JFrame mainFrame, StatusBar statusBar) {
		this.appName = appName;
		this.mainFrame = mainFrame;
		this.statusBar = statusBar;
	}

	/**
	 * Выдать информационное сообщение в статусной строке.
	 * 
	 * @param message
	 *            текст сообщения.
	 */
	public void notice(String message) {
		statusBar.showMessage(message);
	}

	/**
	 * Выдать информационное сообщение.
	 * 
	 * @param message
	 *            текст сообщения.
	 */
	public void inf(String message) {
		JOptionPane.showMessageDialog(mainFrame, message, appName,
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Выдать информационное сообщение с возможностью задать текст в заголовке
	 * окна.
	 * 
	 * @param title
	 *            текст отображаемый в заголовке окна.
	 * @param message
	 *            текст сообщения
	 */
	public void inf(String title, String message) {
		JOptionPane.showMessageDialog(mainFrame, message, title,
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Выдать сообщение об ошибке.
	 * 
	 * @param message
	 *            текст сообщения.
	 */
	public void err(String message) {
		JOptionPane.showMessageDialog(mainFrame, message, appName,
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Запросить подтверждения некоторого действия с двумя вариантами ответа (OK
	 * и Cancel).
	 * 
	 * @param message
	 *            текст описывающий действие.
	 * @return true, если пользователь нажал OK.
	 */
	public boolean conf2(String message) {
		int res;
		res = JOptionPane.showConfirmDialog(mainFrame, message, appName,
				JOptionPane.YES_NO_OPTION);
		return (res == JOptionPane.OK_OPTION);
	}

	/**
	 * Запросить подтверждения некоторого действия с тремя вариантами ответа
	 * (Yes, No и Cancel). Обычно используется при неопределённом закрытии окна,
	 * в запросе разрешения на сохранение информации. При этом, возможны три
	 * варианта: сохранение, отмена ввода и возврат в окно редактирования.
	 * 
	 * @param message
	 *            текст описывающий действие.
	 * @return YES, NO или CANCEL.
	 */
	public int conf3(String message) {
		int res;
		res = JOptionPane.showConfirmDialog(mainFrame, message, appName,
				JOptionPane.YES_NO_CANCEL_OPTION);
		if (res == -1) {
			res = CANCEL;
		}
		return res;
	}

	private String appName;
	private JFrame mainFrame;
	private StatusBar statusBar;
}
