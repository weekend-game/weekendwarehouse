package game.weekend.warehouse.directories;

import javax.swing.JLabel;
import javax.swing.JPanel;

import game.weekend.framework.core.DocData;
import game.weekend.framework.core.Document;
import game.weekend.framework.core.FrameManager;
import game.weekend.framework.core.GBL;
import game.weekend.framework.core.Loc;
import game.weekend.framework.core.controls.BtnCancel;
import game.weekend.framework.core.controls.BtnOK;
import game.weekend.framework.core.controls.ConString;
import game.weekend.framework.core.controls.IControl;

/**
 * Документ.
 */
@SuppressWarnings("serial")
public class GroupsOfCompaniesDoc extends Document {

	/**
	 * Создать окно документа.
	 * 
	 * @param id                 ID документа.
	 * @param mode               режим редактирования.
	 * @param parentFrameManager родительский менеджер расположения окон.
	 */
	public GroupsOfCompaniesDoc(int id, int mode, FrameManager parentFrameManager) {
		super(id, mode, parentFrameManager);
	}

	@Override
	public void initComponents() {

		// Данные
		DocData docData;
		try {
			docData = new GroupsOfCompaniesData(getId(), getMode(), this);
		} catch (Exception e) {
			getContentPane().add(new JLabel(e.toString(), JLabel.CENTER));
			return;
		}
		setDocData(docData);

		// Заголовок окна
		showTitle(Loc.get("group_of_company"), "name");

		// Расположение элементов управления
		GBL g = new GBL((JPanel) getContentPane(), true);
		IControl<String> conStr;

		g.newLine();
		g.addFixL(new JLabel(Loc.get("name") + ":"), 1);
		conStr = new ConString(docData, "name", 60, 32);
		g.addExtH(conStr.getComp(), 4);

		g.newLine();
		g.addVer(1);

		g.newLine();
		g.addHor(3);
		g.addFixR(new BtnOK(this).getComp(), 1);
		g.addFixR(new BtnCancel(this).getComp(), 1);
	}
}
