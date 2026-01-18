package game.weekend.framework.core;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

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
		initFrame(id, mode, parentFrameMan);
	}

	/**
	 * Создать внутреннее окно приложения.
	 */
	protected IntFrame(int id, int mode, FrameManager parentFrameMan, ArrayList<ListData> arrayListData) {
		super("", true, true, true, true);
		this.arrayListData = arrayListData;
		initFrame(id, mode, parentFrameMan);
	}

	/**
	 * Собственно создать внутреннее окно приложения.
	 */
	private void initFrame(int id, int mode, FrameManager parentFrameMan) {
		this.id = id;
		this.mode = mode;
		this.frameMan = new FrameManager(parentFrameMan, this, parentFrameMan.getMainFrame());

		setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
		getMainFrame().getPro().setBounds(this, (int) (Math.random() * 100), (int) (Math.random() * 100), 320, 240);

		addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameActivated(InternalFrameEvent e) {
				no = ++maxno; // Для сортировки окон в порядке их активизации

				// Тут могут быть определены меню, изменена активность действий
				activated();
			}

			public void internalFrameDeactivated(InternalFrameEvent e) {

				// Для каждого окна могут быть свои меню "Edit" и "View", при
				// деактивизации они сбрасываются в умолчательные.
				getMainFrame().getMenuBar().defaultEditMenu();
				getMainFrame().getMenuBar().defaultViewMenu();

				// Тут следует восстановить активность действий
				deactivated();
			}

			// Обработка нажатия крестика в правом верхнем углу
			// окна (WinXP), как неопределённое закрытие окна.
			public void internalFrameClosing(InternalFrameEvent e) {
				close();
			}
		});

		// Обработка ESC, как неопределённое закрытие окна.
		InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
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
	 * Вызывается при активизации окна (событие).
	 */
	public void activated() {
	}

	/**
	 * Вызывается при деактивизации окна (событие).
	 */
	public void deactivated() {
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
	 * Получить родительское окно.
	 * 
	 * @return родительское окно.
	 */
	public final IntFrame getParentFrame() {
		return frameMan.getParentFrame();
	}

	public ArrayList<ListData> getArrayListData() {
		return arrayListData;
	}

	/**
	 * Создать дочернее окно.
	 * 
	 * @param className имя класса дочернего окна.
	 * @param id        уникальный среди окон одного класса идентификатор.
	 * @param mode      режим редактирования отображаемой информации.
	 */
	public final void createFrame(String className, int id, int mode, ArrayList<ListData> al) {
		frameMan.createFrame(className, id, mode, al);
	}

	/**
	 * Создать дочернее окно.
	 * 
	 * @param className имя класса дочернего окна.
	 * @param id        уникальный среди окон одного класса идентификатор.
	 * @param mode      режим редактирования отображаемой информации.
	 */
	public final void createFrame(String className, int id, int mode) {
		frameMan.createFrame(className, id, mode);
	}

	/**
	 * Сообщение от изменении в таблице (если она конечно есть в данном окне).
	 */
	public void modified(int id, int mode) {
	}

	/*
	 * Это используется, например для отображения списка активных окон в окне выбора
	 * активного окна
	 */
	@Override
	public String toString() {
		return getTitle();
	}

	private static int maxno = 0;

	private int no = 0;
	private int id = 0;
	private int mode = 0;
	private FrameManager frameMan;
	private ArrayList<ListData> arrayListData;
}
