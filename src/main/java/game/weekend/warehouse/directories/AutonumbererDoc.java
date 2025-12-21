package game.weekend.warehouse.directories;

import javax.swing.JLabel;
import javax.swing.JPanel;

import game.weekend.framework.core.Document;
import game.weekend.framework.core.FrameManager;
import game.weekend.framework.core.GBL;
import game.weekend.framework.core.Loc;
import game.weekend.framework.core.controls.BtnCancel;
import game.weekend.framework.core.controls.BtnOK;
import game.weekend.framework.core.controls.ConInteger;
import game.weekend.framework.core.controls.ConString;
import game.weekend.framework.core.controls.IControl;

/**
 * Документ.
 */
@SuppressWarnings("serial")
public class AutonumbererDoc extends Document {

	/**
	 * Создать окно документа.
	 * 
	 * @param id                 ID документа.
	 * @param mode               режим редактирования.
	 * @param parentFrameManager родительский менеджер расположения окон.
	 */
	public AutonumbererDoc(int id, int mode, FrameManager parentFrameManager) {
		super(id, mode, parentFrameManager);
	}

	@Override
	public void initComponents() {

		// Данные
		AutonumbererData docData;
		try {
			docData = new AutonumbererData(getId(), getMode(), this);
		} catch (Exception e) {
			getContentPane().add(new JLabel(e.toString(), JLabel.CENTER));
			return;
		}
		setDocData(docData);

		// Заголовок окна
		showTitle(Loc.get("autonumberer"), "document");

		// Расположение элементов управления
		GBL g = new GBL((JPanel) getContentPane(), true);
		IControl<String> conStr;
		IControl<Integer> conInt;

		g.addFixL(new JLabel(Loc.get("document") + ":"), 10);
		conStr = new ConString(docData, "document", 10, 16);
		g.addFixL(conStr.getComp(), 10);
		g.addHor(30);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("counter") + ":"), 10);
		conInt = new ConInteger(docData, "counter");
		g.addFixL(conInt.getComp(), 5);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("first_value") + ":"), 10);
		conInt = new ConInteger(docData, "first_value");
		g.addFixL(conInt.getComp(), 5);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("last_value") + ":"), 10);
		conInt = new ConInteger(docData, "last_value");
		g.addFixL(conInt.getComp(), 5);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("prefix") + ":"), 10);
		conStr = new ConString(docData, "prefix", 10, 8);
		g.addFixL(conStr.getComp(), 10);
		
		g.newLine();
		g.addFixL(new JLabel(Loc.get("suffix") + ":"), 10);
		conStr = new ConString(docData, "suffix", 10, 8);
		g.addFixL(conStr.getComp(), 10);

		g.newLine();
		g.addVer(1);

		g.newLine();
		g.addHor(10);
		g.addFixR(new BtnOK(this).getComp(), 10);
		g.addFixR(new BtnCancel(this).getComp(), 10);
	}
}
