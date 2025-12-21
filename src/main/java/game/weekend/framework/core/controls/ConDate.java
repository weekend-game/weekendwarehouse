package game.weekend.framework.core.controls;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.KeyStroke;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.InternationalFormatter;

import game.weekend.framework.core.DocData;

// Т.к. formatter.setOverwriteMode(true) не работает, то
// возможно надо будет переписать на основе MaskFormatter.

/**
 * Компонент для ввода даты.
 */
public class ConDate implements IControl<Date> {

	/**
	 * Создать компонент ввода даты с указанным объектом данных.
	 * 
	 * @param value текущее значение.
	 */
	public ConDate(DocData data, String name) {
		this.data = data;
		this.name = name;
		data.setControl(name, this);

		// JComponent компонента
		// DateFormat dateFormat = DateFormat.getDateInstance();
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		
		dateFormat.setLenient(false);
		Formatter formatter = new Formatter(dateFormat);
		formatter.setOverwriteMode(true); // Не работает почему-то
		comp = new JFormattedTextField(formatter);

		// Примерная ширина объекта на экране в символах
		comp.setColumns(width);
		comp.setMinimumSize(comp.getPreferredSize());

		// Отображение текущего значения редактируемого поля
		read();

		// Обрабатывать событя при нажатии клавиш ENTER и ESCAPE
		InputMap iMap = comp.getInputMap(JComponent.WHEN_FOCUSED);
		iMap.put(KeyStroke.getKeyStroke("pressed ENTER"), "");
		iMap.put(KeyStroke.getKeyStroke("pressed ESCAPE"), "");

		// Отредактированная дата будет проверена методом valid() при потере
		// фокуса
		comp.addFocusListener(new Listener());
	}

	@Override
	public String setValue(String value) {
		comp.setValue(Date.valueOf(value));
		String m = null;
		try {
			valid();
		} catch (Exception e) {
			m = e.getMessage();
		}
		return m;
	}

	@Override
	public Date getValue() {
		try {
			comp.commitEdit();
		} catch (ParseException e) {
			System.out.println("ConDate.getValue() " + e);
		}

		java.util.Date d = (java.util.Date) comp.getValue();
		Date ret = null;
		if (d != null) {
			ret = new Date(d.getTime());
		}

		return ret;
	}

	@Override
	public boolean hasChange() {
		return getValue().compareTo((Date) data.getValue(name)) != 0;
	}

	@Override
	public String valid() {
		return null;
	}

	@Override
	public void save() {
		data.setValue(name, getValue());
	}

	@Override
	public void setEnable(boolean enabled) {
		comp.setEditable(enabled);
	}

	@Override
	public boolean isEnable() {
		return comp.isEditable();
	}

	@Override
	public JComponent getComp() {
		return comp;
	}

	@Override
	public void clear() {
		// comp.setValue("");
	}

	@Override
	public void read() {
		comp.setValue((Date) data.getValue(name));
	}

	//
	// Обработка вводимых символов.
	//
	@SuppressWarnings("serial")
	private class Formatter extends InternationalFormatter {

		public Formatter(DateFormat nf) {
			super(nf);
		}

		protected DocumentFilter getDocumentFilter() {
			return new DocumentFilter() {
				public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
						throws BadLocationException {

					String str = process(string);
					super.insertString(fb, offset, str, attr);
				}

				public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr)
						throws BadLocationException {

					String str = process(string);
					super.replace(fb, offset, length, str, attr);
				}

				// Обработка вводимых символов
				private String process(String s) {

					// Ввод только цифр и символа '.'
					StringBuilder builder = new StringBuilder(s);
					for (int i = builder.length() - 1; i >= 0; i--) {
						int cp = builder.codePointAt(i);
						if (!Character.isDigit(cp) && cp != '.') {
							builder.deleteCharAt(i);
							if (Character.isSupplementaryCodePoint(cp)) {
								i--;
								builder.deleteCharAt(i);
							}
						}
					}

					s = builder.toString();

					// Запрет превышения установленной длинны
					if (maxlength > 0) {
						int curLen = comp.getText().length();
						if (curLen + s.length() > maxlength) {
							s = s.substring(0, maxlength - curLen);
						}
					}

					return s;
				}
			};
		}
	}

	//
	// Проверка отредактированной даты методом valid() при потере фокуса.
	//
	private class Listener implements FocusListener {

		public void focusGained(FocusEvent arg) {
		}

		public void focusLost(FocusEvent arg) {
		}
	}

	private DocData data;
	private String name;
	private final int width = 8;
	private final int maxlength = 14;
	private JFormattedTextField comp;
}
