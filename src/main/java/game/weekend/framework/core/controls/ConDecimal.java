package game.weekend.framework.core.controls;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import game.weekend.framework.core.DocData;

public class ConDecimal implements IControl<BigDecimal> {

	public ConDecimal(DocData data, String name) {
		this(data, name, "#.00");
	}

	public ConDecimal(DocData data, String name, String format) {
		this.data = data;
		this.name = name;
		data.setControl(name, this);

		// JComponent компонента
		Object o = data.getValue(name);
		try {
			BigDecimal b = (o == null) ? BigDecimal.valueOf(0) : (BigDecimal) o;
			comp = new JFormattedTextField(b);

			DefaultFormatter fmt = new NumberFormatter(new DecimalFormat(format));
			fmt.setValueClass(comp.getValue().getClass());
			DefaultFormatterFactory fmtFactory = new DefaultFormatterFactory(fmt, fmt, fmt);
			comp.setFormatterFactory(fmtFactory);
		} catch (Exception e) {
			String s = "Попытка присвоения объекту ввода " + name + " (BigDecimal) значения типа "
					+ o.getClass().getName();
			System.out.println(s);
		}
		// Примерная ширина объекта на экране в символах
		comp.setColumns(15); // !!!
		comp.setMinimumSize(comp.getPreferredSize());

		// Отображение текущего значения редактируемого поля
		// restore();

		// Обрабатывать событя при нажатии клавиш ENTER и ESCAPE
		InputMap iMap = comp.getInputMap(JComponent.WHEN_FOCUSED);
		iMap.put(KeyStroke.getKeyStroke("pressed ENTER"), "");
		iMap.put(KeyStroke.getKeyStroke("pressed ESCAPE"), "");

		// Отредактированное число будет проверено методом valid() при потере
		// фокуса
		// comp.addFocusListener(new Listener());
	}

	@Override
	public String setValue(String value) {
		comp.setValue(new BigDecimal(value));
		String m = null;
		try {
			valid();
		} catch (Exception e) {
			m = e.getMessage();
		}
		return m;
	}

	@Override
	public BigDecimal getValue() {
		try {
			comp.commitEdit();
		} catch (ParseException e) {
			System.out.println("ConDecimal.getValue()\n" + e);
		}
		return (BigDecimal) comp.getValue();
	}

	@Override
	public boolean hasChange() {
		return getValue().compareTo((BigDecimal) data.getValue(name)) != 0;
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
	}

	@Override
	public void read() {
		comp.setValue((BigDecimal) data.getValue(name));
	}

	private DocData data;
	private String name;
	private JFormattedTextField comp;
}
