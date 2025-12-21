package game.weekend.framework.core.controls;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.KeyStroke;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DocumentFilter;

import game.weekend.framework.core.DocData;

/**
 * Поле для ввода строки текста.
 */
public class ConString implements IControl<String> {

	/**
	 * Создать поле для ввода строки текста с указанными объектом данных,
	 * наименованием поля, шириной и максимальным количеством символов.
	 * 
	 * @param docData объект данных.
	 * @param name    наименование поля.
	 * @param width   примерная ширина объекта на экране в символах.
	 * @param maxlen  максимальное количество символов доступное для ввода.
	 */
	public ConString(DocData docData, String name, int width, int maxlen) {
		this.docData = docData;
		this.name = name;

		// Регистрация поля в объекте данных
		docData.setControl(name, this);

		// JComponent компонента
		comp = new JFormattedTextField(new Formatter());

		// Примерная ширина объекта на экране в символах
		comp.setColumns(width);
		comp.setMinimumSize(comp.getPreferredSize());

		// Максимальное количество символов доступное для ввода
		maxlength = maxlen;

		// Отображение текущего значения редактируемого поля
		comp.setValue((String) docData.getValue(name));

		// Обрабатывать событя при нажатии клавиш ENTER и ESCAPE
		InputMap iMap = comp.getInputMap(JComponent.WHEN_FOCUSED);
		iMap.put(KeyStroke.getKeyStroke("pressed ENTER"), "");
		iMap.put(KeyStroke.getKeyStroke("pressed ESCAPE"), "");

		// Отредактированная строка будет проверена методом valid() при потере
		// фокуса
		comp.addFocusListener(new Listener());
	}

	@Override
	public String setValue(String value) {
		comp.setValue(value);
		String m = null;
		try {
			valid();
		} catch (Exception e) {
			m = e.getMessage();
		}
		return m;
	}

	@Override
	public String getValue() {
		try {
			comp.commitEdit();
		} catch (ParseException e) {
			System.out.println("ConString.getValue() " + e);
		}
		return (String) comp.getValue();
	}

	@Override
	public boolean hasChange() {
		return !getValue().equals((String) docData.getValue(name));
	}

	@Override
	public String valid() {
		return null;
	}

	@Override
	public void save() {
		docData.setValue(name, getValue());
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
		comp.setValue("");
	}

	@Override
	public void read() {
		comp.setValue((String) docData.getValue(name));
	}

	/**
	 * Установить режим ввода букв только в верхнем регистре.
	 */
	public void setUppercase() {
		uppercase = true;
	}

	//
	// Обработка вводимых символов.
	//
	@SuppressWarnings("serial")
	private class Formatter extends DefaultFormatter {

		public Formatter() {
			super();
		}

		protected DocumentFilter getDocumentFilter() {
			return new DocumentFilter() {
				public void insertString(FilterBypass fb, int offset, String str, AttributeSet attr)
						throws BadLocationException {

					str = process(str);
					super.insertString(fb, offset, str, attr);
				}

				public void replace(FilterBypass fb, int offset, int length, String str, AttributeSet attr)
						throws BadLocationException {

					str = process(str);
					super.replace(fb, offset, length, str, attr);
				}

				// Обработка вводимых символов
				private String process(String s) {

					// Запрет превышения максимальной длинны
					if (maxlength > 0) {
						int curLen = comp.getText().length();
						if (curLen + s.length() > maxlength) {
							s = s.substring(0, maxlength - curLen);
						}
					}

					// Преобразование к верхнему регистру если задан такой режим
					if (uppercase) {
						s = s.toUpperCase();
					}

					return s;
				}
			};
		}
	}

	//
	// Запись в поле объекта данных отредактированной строки.
	//
	private class Listener implements FocusListener {

		public void focusGained(FocusEvent focusEvent) {
		}

		public void focusLost(FocusEvent focusEvent) {
			String s = valid();
			if (s != null) {
				docData.getMes().notice(s);
			}
		}
	}

	private DocData docData;
	private String name;
	private int maxlength = 0;
	private boolean uppercase = false;
	private JFormattedTextField comp;
}