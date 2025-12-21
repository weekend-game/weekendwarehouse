package game.weekend.framework.core.controls;

import javax.swing.InputMap;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import game.weekend.framework.core.DocData;

public class ConBoolean implements IControl<Boolean> {

	public ConBoolean(DocData data, String name, String text) {
		try {
			this.data = data;
			this.name = name;
			data.setControl(name, this);

			// JComponent компонента
			comp = new JCheckBox(text);

			// Отображение текущего значения редактируемого поля
			read();

			// Обрабатывать событя при нажатии клавиш ENTER и ESCAPE
			InputMap iMap = comp.getInputMap(JComponent.WHEN_FOCUSED);
			iMap.put(KeyStroke.getKeyStroke("pressed ENTER"), "");
			iMap.put(KeyStroke.getKeyStroke("pressed ESCAPE"), "");
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	@Override
	public String setValue(String value) {
		comp.requestFocus();
		comp.setSelected(Boolean.valueOf(value));
		String m = null;
		try {
			valid();
		} catch (Exception e) {
			m = e.getMessage();
		}
		return m;
	}

	@Override
	public Boolean getValue() {
		return comp.isSelected();
	}

	@Override
	public boolean hasChange() {
		return !getValue().equals((Boolean) data.getValue(name));
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
		comp.setEnabled(enabled);
	}

	@Override
	public boolean isEnable() {
		return comp.isEnabled();
	}

	@Override
	public JComponent getComp() {
		return comp;
	}

	@Override
	public void clear() {
		comp.setSelected(false);
	}

	@Override
	public void read() {
		Boolean b = (Boolean) data.getValue(name);
		comp.setSelected(b == null ? false : b);
	}

	private DocData data;
	private String name;
	private JCheckBox comp;
}
