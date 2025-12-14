package game.weekend.warehouse.documents;

import javax.swing.JLabel;
import javax.swing.JPanel;

import game.weekend.framework.core.DocData;
import game.weekend.framework.core.Document;
import game.weekend.framework.core.FrameManager;
import game.weekend.framework.core.GBL;
import game.weekend.framework.core.controls.BtnCancel;
import game.weekend.framework.core.controls.BtnOK;
import game.weekend.framework.core.controls.ConString;
import game.weekend.framework.core.controls.IControl;

/**
 * Документ номер один.
 */
@SuppressWarnings("serial")
public class ReceiptsDoc extends Document {

	/**
	 * Создать окно документа.
	 * 
	 * @param id                 ID документа.
	 * @param mode               режим редактирования.
	 * @param parentFrameManager родительский менеджер расположения окон.
	 */
	public ReceiptsDoc(int id, int mode, FrameManager parentFrameManager) {
		super(id, mode, parentFrameManager);
	}

	@Override
	public void initComponents() {

		// Данные
		DocData data;
		try {
			data = new ReceiptsData(getId(), getMode(), this);
		} catch (Exception e) {
			getContentPane().add(new JLabel(e.toString(), JLabel.CENTER));
			return;
		}
		setDocData(data);

		// Заголовок окна
		showTitle("Поступление", "doc_numb");

		// Расположение элементов управления
		GBL g = new GBL((JPanel) getContentPane(), true);

		IControl<String> getStr;

		g.newLine();
		g.addFixL(new JLabel("Номер:"), 1);
		getStr = new ConString(data, "doc_numb", 60, 128);
		g.addExtH(getStr.getComp(), 4);

		g.newLine();
		g.addFixL(new JLabel("Комментарий:"), 1);
		getStr = new ConString(data, "rem", 60, 128);
		g.addExtH(getStr.getComp(), 4);

		g.newLine();
		g.addVer(5);

		g.newLine();
		g.addHor(3);
		g.addFixR(new BtnOK(this).getComp(), 1);
		g.addFixR(new BtnCancel(this).getComp(), 1);
	}
}
