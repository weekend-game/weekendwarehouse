package game.weekend.framework.core;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.JFrame;

/**
 * Локально хранимые свойства приложения.
 */
public class Proper {

	/**
	 * Создать объект локального хранения свойств приложения.
	 * 
	 * @param name
	 *            - имя файла хранения (обычно название приложения).
	 */
	public Proper(String name) {
		fileName = name.toLowerCase() + ".properties";
		try {
			InputStream inp = new FileInputStream(fileName);
			properties.load(inp);
			inp.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Сохранить свойства приложения.
	 */
	public void save() {
		OutputStream out;
		try {
			out = new FileOutputStream(fileName);
			properties.store(out, "");
			out.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Сохранить свойство name с целым значением value.
	 * 
	 * @param name
	 *            имя свойства.
	 * @param value
	 *            целое значение.
	 */
	public void setProperty(String name, int value) {
		properties.setProperty(name, "" + value);
	}

	/**
	 * Получить целое свойство name.
	 * 
	 * @param name
	 *            имя свойства.
	 * @param value
	 *            значение свойства по умолчанию.
	 */
	public int getProperty(String name, int def) {
		return Integer.parseInt(properties.getProperty(name, "" + def));
	}

	/**
	 * Сохранить свойство name со строковым значением value.
	 * 
	 * @param name
	 *            имя свойства.
	 * @param value
	 *            строковое значение.
	 */
	public void setProperty(String name, String value) {
		properties.setProperty(name, value);
	}

	/**
	 * Получить строковое свойство name.
	 * 
	 * @param name
	 *            имя свойства.
	 * @param value
	 *            значение свойства по умолчанию.
	 */
	public String getProperty(String name, String def) {
		return properties.getProperty(name, def);
	}

	/**
	 * Сохранить расположение окна.
	 * 
	 * @param c
	 *            окно.
	 */
	public void saveBounds(Component c) {
		String name = c.getClass().getName();
		Point gl = c.getLocation();
		setProperty(name + "_X", gl.x);
		setProperty(name + "_Y", gl.y);
		Dimension gs = c.getSize();
		setProperty(name + "_W", gs.width);
		setProperty(name + "_H", gs.height);
	}

	/**
	 * Расположить окно в ранее сохранённой позиции.
	 * 
	 * @param c
	 *            окно.
	 * @param dx
	 *            х по умолчанию.
	 * @param dy
	 *            y по умолчанию.
	 * @param dw
	 *            ширина окна по умолчанию.
	 * @param dh
	 *            высота окна по умолчанию.
	 */
	public void setBounds(Component c, int dx, int dy, int dw, int dh) {
		String name = c.getClass().getName();
		int x = getProperty(name + "_X", dx);
		int y = getProperty(name + "_Y", dy);
		c.setLocation(x, y);
		int w = getProperty(name + "_W", dw);
		int h = getProperty(name + "_H", dh);
		c.setSize(w, h);
	}

	/**
	 * Сохранить расположение главного окна приложения.
	 * 
	 * @param frame
	 *            окно.
	 */
	public void saveBounds(JFrame frame) {
		saveBounds((Component) frame);
		setProperty(frame.getClass().getName() + "_STATE",
				frame.getExtendedState());
	}

	/**
	 * Расположить главное окно приложения в ранее сохранённой или умолчательной
	 * позиции.
	 */
	public void setBounds(JFrame frame) {
		final int INSET = 40;

		Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
		int def_x = INSET;
		int def_y = INSET;
		int def_w = ss.width - INSET * 2;
		int def_h = ss.height - INSET * 3;

		String name = frame.getClass().getName();
		int x = getProperty(name + "_X", def_x);
		int y = getProperty(name + "_Y", def_y);
		int w = getProperty(name + "_W", def_w);
		int h = getProperty(name + "_H", def_h);
		int st = getProperty(name + "_STATE", JFrame.NORMAL);

		if (st == JFrame.MAXIMIZED_BOTH) {
			frame.setBounds(def_x, def_y, def_w, def_h);
			frame.setExtendedState(st);
		} else {
			if (x < 0 || x >= ss.width || y < 0 || y >= ss.height || w < INSET
					|| h < INSET) {
				x = def_x;
				y = def_y;
				w = def_w;
				h = def_h;
			}
			frame.setBounds(x, y, w, h);
		}
	}

	private Properties properties = new Properties();
	private String fileName = "application.properties";
}
