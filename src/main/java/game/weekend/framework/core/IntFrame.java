package game.weekend.framework.core;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 * Внутреннее окно приложения.
 */
@SuppressWarnings("serial")
public class IntFrame extends JInternalFrame implements Comparable<IntFrame> {

	/**
	 * Создать внутреннее окно приложения.
	 */
	protected IntFrame(int id, int mode, FrameManager parentFrameMan) {
		super("", true, true, true, true);

		this.id = id;
		this.mode = mode;
		this.frameMan = new FrameManager(parentFrameMan, this,
				parentFrameMan.getMainFrame());

		setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
		getMainFrame().getPro().setBounds(this, (int) (Math.random() * 100),
				(int) (Math.random() * 100), 320, 240);

		addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameActivated(InternalFrameEvent e) {
				no = ++maxno; // Для сортировки окон в порядке их активизации
			}

			public void internalFrameDeactivated(InternalFrameEvent e) {
			}

			// Обработка нажатия крестика в правом верхнем углу
			// окна (WinXP), как неопределённое закрытие окна.
			public void internalFrameClosing(InternalFrameEvent e) {
				close();
			}
		});

		// Обработка ESC, как неопределённое закрытие окна.
		InputMap inputMap = getRootPane().getInputMap(
				JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		inputMap.put(KeyStroke.getKeyStroke("pressed ESCAPE"), "Exit");
		ActionMap aMap = getRootPane().getActionMap();
		aMap.put("Exit", new AbstractAction() {
			public void actionPerformed(ActionEvent actionEvent) {
				close();
			}
		});

		initComponents();
		setVisible(true);
	}

	/**
	 * Расположить компоненты в окне.
	 * 
	 * Этот метод вызвается из конструктора, но до того как окно станет видимым.
	 */
	public void initComponents() {
	}

	/**
	 * Сравнивает два окна по порядку их активизации. Реализация интерфейса
	 * Comparable для сортировки окон в порядке активизации.
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public final int compareTo(IntFrame f) {
		if (this.no < f.no) {
			return -1;
		} else if (this.no > f.no) {
			return 1;
		}
		return 0;
	}

	/**
	 * Закрыть окно с сохранением информации.
	 * 
	 * @return true если окно и все дочерние окна закрылись.
	 */
	public final boolean ok() {
		try {
			setSelected(true);
		} catch (Exception e) {
		}

		if (!frameMan.closeChildFrames()) {
			return false;
		}

		if (!save()) {
			return false;
		}

		frameMan.closeFrame();
		return true;
	}

	/**
	 * Закрыть окно без сохранения информации.
	 * 
	 * @return true если окно и все дочерние окна закрылись.
	 */
	public final boolean cancel() {
		try {
			setSelected(true);
		} catch (Exception e) {
		}

		if (!frameMan.closeChildFrames()) {
			return false;
		}

		frameMan.closeFrame();
		return true;
	}

	/**
	 * Закрыть окно (неопределённое закрытие).
	 * 
	 * @return true если окно и все дочерние окна закрылись.
	 */
	public final boolean close() {
		try {
			setSelected(true);
		} catch (Exception e) {
		}

		int c = JOptionPane.NO_OPTION;
		if (hasChanges()) {
			c = getMainFrame().getMes().conf3("Сохранить изменения?");
		}

		if (c == JOptionPane.YES_OPTION) {
			return ok();
		}

		if (c == JOptionPane.NO_OPTION) {
			return cancel();
		}

		return false;
	}

	/**
	 * Получить признак наличия изменения в данных окна.
	 * 
	 * @return true если изменения в данных имеются.
	 */
	public boolean hasChanges() {
		return false;
	}

	/**
	 * Вызывается методами закрвытия окна для сохранения информации.
	 * 
	 * @return true если информация успешно сохранена.
	 */
	public boolean save() {
		return true;
	}

	/**
	 * Получить идентификатор окна.
	 * 
	 * @return идентификатор окна.
	 */
	public final int getId() {
		return id;
	}

	/**
	 * Получить режим окна.
	 * 
	 * @return режим окна.
	 */
	public final int getMode() {
		return mode;
	}

	/**
	 * Получить основное окно приложения.
	 * 
	 * @return идентификатор окна.
	 */
	public final MainFrame getMainFrame() {
		return frameMan.getMainFrame();
	}

	/**
	 * Создать дочернее окно.
	 * 
	 * @param className
	 *            имя класса дочернего окна.
	 * @param id
	 *            уникальный среди окон одного класса идентификатор.
	 * @param mode
	 *            режим редактирования отображаемой информации.
	 */
	public final void createFrame(String className, int id, int mode) {
		frameMan.createFrame(className, id, mode);
	}

	private static int maxno = 0;

	private int no = 0;
	private int id = 0;
	private int mode = 0;
	private FrameManager frameMan;
}
