package game.weekend.framework.core;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JInternalFrame;

/**
 * Диспетчер окон.
 * 
 * Класс предназначен для создания и управления окнами приложения. Однако,
 * напрямую он не используется. Основное окно приложения и внутреннее окно
 * приложения делегируют обьекту этого класса функции создания и закрытия окон.
 */
public class FrameManager {

	/**
	 * Создать диспетчер окон.
	 * 
	 * @param parent    родительский диспетчер окон.
	 * @param frame     окно являющееся владельцем создаваемого диспетчера.
	 * @param mainFrame основное окно приложения.
	 */
	protected FrameManager(FrameManager parent, IntFrame frame, MainFrame mainFrame) {
		this.parentFrameMan = parent;
		this.frame = frame;
		this.mainFrame = mainFrame;
	}

	/**
	 * Создать дочернее окно.
	 * 
	 * @param className класс создаваемого окна.
	 * @param id        идентификатор окна.
	 * @param mode      режим окна.
	 */
	@SuppressWarnings("unchecked")
	protected void createFrame(String className, int id, int mode) {
		IntFrame newFrame = null;

		if (id >= 0 && mode != IEditable.ADD && mode != IEditable.ADD_COPY) {
			newFrame = getChildFrame(className, id);
		}

		try {
			if (newFrame == null) {
				@SuppressWarnings("rawtypes")
				Class c = Class.forName(className);
				Constructor<IntFrame> cnr = c.getConstructor(new Class[] { int.class, int.class, FrameManager.class });
				newFrame = cnr.newInstance(id, mode, this);
				mainFrame.addIntFrame(newFrame);
				childFrames.add(newFrame);
			}
			newFrame.setSelected(true);
		} catch (Exception e) {
			System.out.println("FrameManager.createFrame('" + className + "', " + id + ", " + mode + ")\n" + e);
		}
	}

	/**
	 * Создать дочернее окно.
	 * 
	 * @param className класс создаваемого окна.
	 * @param id        идентификатор окна.
	 * @param mode      режим окна.
	 * @param список    для редактирования
	 */
	@SuppressWarnings("unchecked")
	protected void createFrame(String className, int id, int mode, ArrayList<ListData> al) {
		IntFrame newFrame = null;

		newFrame = getChildFrame(className);

		try {
			if (newFrame == null) {
				@SuppressWarnings("rawtypes")
				Class c = Class.forName(className);
				Constructor<IntFrame> cnr = c
						.getConstructor(new Class[] { int.class, int.class, FrameManager.class, ArrayList.class });
				newFrame = cnr.newInstance(id, mode, this, al);
				mainFrame.addIntFrame(newFrame);
				childFrames.add(newFrame);
			}
			newFrame.setSelected(true);
		} catch (Exception e) {
			System.out.println("FrameManager.createFrame('" + className + "', " + id + ", " + mode + ", al)\n" + e);
		}
	}

	/**
	 * Закрыть все дочерние окна.
	 * 
	 * @return true если все окна закрылись.
	 */
	protected boolean closeChildFrames() {
		int len = childFrames.size();
		IntFrame[] frm = new IntFrame[len];
		childFrames.toArray(frm);

		for (int i = len - 1; i >= 0; --i) {
			if (!frm[i].close()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Закрыть окно.
	 */
	protected void closeFrame() {
		mainFrame.getPro().saveBounds(frame);
		frame.dispose();

		if (parentFrameMan != null) {
			parentFrameMan.childFrames.remove(frame);
			if (parentFrameMan.frame != null) {
				try {
					parentFrameMan.frame.setSelected(true);
				} catch (Exception e) {
				}
			} else {
				JInternalFrame[] frames = mainFrame.getAllFrames();
				if (frames.length > 0) {
					Arrays.sort(frames);
					try {
						frames[frames.length - 1].setSelected(true);
					} catch (Exception e) {
					}
				}
			}
		}
	}

	/**
	 * Получить родительское окно.
	 * 
	 * @return родительское окно.
	 */
	protected IntFrame getParentFrame() {
		IntFrame frame = null;
		if (parentFrameMan != null) {
			frame = parentFrameMan.frame;
		}
		return frame;
	}

	/**
	 * Получить основное окно приложения.
	 * 
	 * @return основное окно приложения.
	 */
	protected MainFrame getMainFrame() {
		return mainFrame;
	}

	/**
	 * Поиск окна указанного класса и ID в списке дочерних окон
	 * 
	 * @param className класс окна.
	 * @param id        иденификатор окна.
	 * @return ссылка на окно или null если не найдено.
	 */
	private IntFrame getChildFrame(String className, int id) {
		IntFrame frame = null;
		for (IntFrame cf : childFrames) {
			if (cf.getClass().getName().equals(className) && cf.getId() == id) {
				frame = cf;
				break;
			}
		}
		return frame;
	}

	/**
	 * Поиск окна указанного класса и ID в списке дочерних окон
	 * 
	 * @param className класс окна.
	 * @return ссылка на окно или null если не найдено.
	 */
	private IntFrame getChildFrame(String className) {
		IntFrame frame = null;
		for (IntFrame cf : childFrames) {
			if (cf.getClass().getName().equals(className)) {
				frame = cf;
				break;
			}
		}
		return frame;
	}

	private FrameManager parentFrameMan = null;
	private IntFrame frame = null;
	private MainFrame mainFrame;

	private ArrayList<IntFrame> childFrames = new ArrayList<IntFrame>();
}
