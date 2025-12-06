package game.weekend.framework.core.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;

import game.weekend.framework.core.IntFrame;
import game.weekend.framework.core.Loc;

/**
 * Экранная кнопка "Сохранить".
 */
public class BtnOK {

	/**
	 * Создать экранную кнопку "Сохранить".
	 * 
	 * @param frame окно на котором располагается кнопка.
	 */
	public BtnOK(final IntFrame frame) {
		comp = new JButton(Loc.get("ok"));
		frame.getRootPane().setDefaultButton(comp);
		comp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				frame.ok();
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
