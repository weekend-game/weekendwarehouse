package game.weekend.framework.core.controls;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.ComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import game.weekend.framework.core.DocData;

public class ConСatBox implements IControl<Integer> {

	public ConСatBox(DocData data, String name, ArrayList<CatPos> list) {
		this.docData = data;
		this.name = name;
		data.setControl(name, this);

		comp = new JComboBox<CatPos>(list.toArray(new CatPos[0]));

		read();

		// Обрабатывать событя при нажатии клавиш ENTER и ESCAPE
		InputMap iMap = comp.getInputMap(JComponent.WHEN_FOCUSED);
		iMap.put(KeyStroke.getKeyStroke("pressed ENTER"), "");
		iMap.put(KeyStroke.getKeyStroke("pressed ESCAPE"), "");

		// Отредактированное будет проверено valid() при потере фокуса
		comp.addFocusListener(new Listener());
	}

	/**
	 * Будет нужно при автотестировании.
	 */
	@Override
	public String setValue(String value) {
		int id = Integer.valueOf(value);

		return setValue(id);
	}

	public String setValue(Integer value) {
		int id = value;

		ComboBoxModel<CatPos> model = comp.getModel();
		int itemCount = model.getSize();
		for (int i = 0; i < itemCount; i++)
			if (((CatPos) model.getElementAt(i)).id == id)
				comp.setSelectedIndex(i);

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
		CatPos cp = (CatPos) comp.getSelectedItem();
		if (cp == null)
			return null;

		return cp.id;
	}

	@Override
	public boolean hasChange() {
		return getValue().compareTo((int) docData.getValue(name)) != 0;
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
		Object o = docData.getValue(name);
		if (o == null)
			setValue(0);
		else
			setValue((Integer) o);
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
	private JComboBox<CatPos> comp;

	public static class CatPos {

		public CatPos(int id, String name) {
			this.id = id;
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}

		public final int id;
		public final String name;
	}
}
