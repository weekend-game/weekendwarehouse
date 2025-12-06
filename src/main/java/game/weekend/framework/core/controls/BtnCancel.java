package game.weekend.framework.core.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;

import game.weekend.framework.core.IntFrame;
import game.weekend.framework.core.Loc;

/**
 * Экранная кнопка "Отменить".
 */
public class BtnCancel {

	/**
	 * Создать экранную кнопку "Отменить".
	 * 
	 * @param frame окно на котором располагается кнопка.
	 */
	public BtnCancel(final IntFrame frame) {
		comp = new JButton(Loc.get("cancel"));
		comp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.cancel();
			}
		});
	}

	/**
	 * Получить JComponent.
	 * 
	 * @return объект класса JComponent.
	 */
	public JComponent getComp() {
		return comp;
	}

	private JButton comp;
}
