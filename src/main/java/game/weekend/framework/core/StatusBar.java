package game.weekend.framework.core;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 * Статусная строка приложения.
 */
public class StatusBar {

	/**
	 * Создать статусную строку приложения.
	 */
	public StatusBar() {
		panel = new JPanel();
		panel.setLayout(new GridLayout(1, 1));
		text = new JTextField("");
		text.setEditable(false);
		panel.add(text);
	}

	/**
	 * Получить панель на основе которой реализована статусная строка.
	 * 
	 * @return панель статусной строки.
	 */
	protected JPanel getPanel() {
		return panel;
	}

	/**
	 * Вывести сообщение в статусной строке и отображать его в течении пяти
	 * секунд.
	 * 
	 * @param mes
	 *            текст сообщения.
	 */
	public void showMessage(String mes) {
		if (tmr != null && tmr.isRunning()) {
			tmr.stop();
		}
		text.setText(mes);
		tmr = new Timer(DELAY, new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				text.setText("");
			}
		});
		tmr.start();
	}

	/**
	 * Реализация пустой статусной строки.
	 */
	public static final StatusBar NULL = new StatusBar() {

		public JPanel getPanel() {
			return null;
		}

		public void showMessage(String mes) {
		}
	};

	private static final int DELAY = 5000; // Время отображения сообщения
	private JPanel panel;
	private JTextField text;
	private Timer tmr;
}
