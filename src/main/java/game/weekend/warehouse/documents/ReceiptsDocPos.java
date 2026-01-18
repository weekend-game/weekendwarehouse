package game.weekend.warehouse.documents;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import game.weekend.framework.core.DocData;
import game.weekend.framework.core.Document;
import game.weekend.framework.core.FrameManager;
import game.weekend.framework.core.GBL;
import game.weekend.framework.core.ListData;
import game.weekend.framework.core.Loc;
import game.weekend.framework.core.controls.BtnCancel;
import game.weekend.framework.core.controls.BtnOK;
import game.weekend.framework.core.controls.ConDecimal;
import game.weekend.framework.core.controls.ConString;
import game.weekend.framework.core.controls.IControl;

@SuppressWarnings("serial")
public class ReceiptsDocPos extends Document {

	public ReceiptsDocPos(int id, int mode, FrameManager parentFrameMan, ArrayList<ListData> arrayListData) {
		super(id, mode, parentFrameMan, arrayListData);
	}

	@Override
	public void initComponents() {

		// Данные
		DocData docData = new ReceiptsDocPosData(getId(), getMode(), this, getArrayListData());
		setDocData(docData);

		// Заголовок окна
		showTitle(Loc.get("product"), "name");

		// Расположение элементов управления
		GBL g = new GBL((JPanel) getContentPane(), true);
		IControl<String> conStr;
		IControl<BigDecimal> conDec;

		g.newLine();
		g.addFixL(new JLabel(Loc.get("article") + ":"), 10);
		conStr = new ConString(docData, "article", 10, 16);
		g.addFixL(conStr.getComp(), 10);

		g.addFixL(new JLabel(Loc.get("name") + ":"), 10);
		conStr = new ConString(docData, "name", 10, 16);
		g.addFixL(conStr.getComp(), 10);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("quantity") + ":"), 10);
		conDec = new ConDecimal(docData, "quantity", "#.000");
		g.addFixL(conDec.getComp(), 5);

		g.newLine();
		g.addHor(30);
		g.addFixR(new BtnOK(this).getComp(), 10);
		g.addFixR(new BtnCancel(this).getComp(), 10);
	}

	@Override
	public boolean save() {
		boolean result = true;
		DocData docData = getDocData();
		if (docData != null) {
			if (!docData.isOK(getMainFrame().getMes())) {
				return false;
			}

			docData.update();
			result = docData.save();

			// Уведомляем журнал о изменении документа
			if (result) {
				Document frame = (Document) getParentFrame();
				int id = (Integer) docData.getValue("id");
				frame.modified(id, getMode());
			}
		}
		return result;
	}
}
