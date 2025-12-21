package game.weekend.framework.core.controls;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.InternationalFormatter;

import game.weekend.framework.core.DocData;

/**
 * Компонент для ввода целого.
 */
public class ConInteger implements IControl<Integer> {

	/**
	 * Создать компонент ввода целого с указанным объектом данных.
	 * 
	 * @param docData объект данных.
	 * @param name    имя данного.
	 */
	public ConInteger(DocData docData, String name) {
		this.docData = docData;
		this.name = name;
		docData.setControl(name, this);

		Formatter formatter = new Formatter(NumberFormat.getIntegerInstance());
		comp = new JFormattedTextField(formatter);

		// Примерная ширина объекта на экране
		comp.setColumns(maxlength);
		comp.setMinimumSize(comp.getPreferredSize());

		// Отображение текущего значения редактируемого поля
		try {
			read();
		} catch (Exception e) {
			System.out.println("ConInteger(). Ошибка при отображении целого.\n" + e);
		}

		// Обрабатывать событя при нажатии клавиш ENTER и ESCAPE
		InputMap iMap = comp.getInputMap(JComponent.WHEN_FOCUSED);
		iMap.put(KeyStroke.getKeyStroke("pressed ENTER"), "");
		iMap.put(KeyStroke.getKeyStroke("pressed ESCAPE"), "");

		// Отредактированное будет проверено valid() при потере фокуса
		comp.addFocusListener(new Listener());
	}

	@Override
	public String setValue(String value) {
		comp.setValue(Integer.valueOf(value));
		String m = null;
		try {
			valid();
		} catch (Exception e) {
			m = e.getMessage();
		}
		return m;
	}

	@Override
	public Integer getValue() {
		if (comp.getText().trim().length() == 0) {
			return Integer.valueOf(0);
		}

		try {
			comp.commitEdit();
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "commit", "", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("ConInteger.getValue()\n" + e);
		}

		long l = (long) comp.getValue();

		Integer ret = (int) l;
		return ret;
	}

	@Override
	public boolean hasChange() {
		Integer oldValue = Integer.valueOf(0);
		Object o = docData.getValue(name);
		if (o != null)
			oldValue = (Integer) o;
		return getValue().compareTo(oldValue) != 0;
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
	}

	@Override
	public void read() {
		comp.setValue((Integer) docData.getValue(name));
	}

	//
	// Обработка вводимых символов.
	//
	@SuppressWarnings("serial")
	private class Formatter extends InternationalFormatter {

		public Formatter(NumberFormat numberFormat) {
			super(numberFormat);
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
	// Проверка методом valid() при потере фокуса.
	//
	private class Listener implements FocusListener {

		public void focusGained(FocusEvent arg) {
		}

		public void focusLost(FocusEvent arg) {
			String m = null;
			try {
				valid();
			} catch (Exception e) {
				m = e.getMessage();
			}
			if (m != null) {
				docData.getStatusBar().showMessage(m);
			}
		}
	}

	private DocData docData;
	private String name;
	private final int maxlength = 14;
	private JFormattedTextField comp;
}
