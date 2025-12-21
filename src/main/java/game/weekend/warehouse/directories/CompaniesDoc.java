package game.weekend.warehouse.directories;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import game.weekend.framework.core.Document;
import game.weekend.framework.core.FrameManager;
import game.weekend.framework.core.GBL;
import game.weekend.framework.core.Loc;
import game.weekend.framework.core.controls.BtnCancel;
import game.weekend.framework.core.controls.BtnOK;
import game.weekend.framework.core.controls.ConBoolean;
import game.weekend.framework.core.controls.ConString;
import game.weekend.framework.core.controls.ConСatBox;
import game.weekend.framework.core.controls.ConСatBox.CatPos;
import game.weekend.framework.core.controls.IControl;

/**
 * Документ.
 */
@SuppressWarnings("serial")
public class CompaniesDoc extends Document {

	/**
	 * Создать окно документа.
	 * 
	 * @param id                 ID документа.
	 * @param mode               режим редактирования.
	 * @param parentFrameManager родительский менеджер расположения окон.
	 */
	public CompaniesDoc(int id, int mode, FrameManager parentFrameManager) {
		super(id, mode, parentFrameManager);
	}

	@Override
	public void initComponents() {

		// Данные
		CompaniesData docData;
		try {
			docData = new CompaniesData(getId(), getMode(), this);
		} catch (Exception e) {
			getContentPane().add(new JLabel(e.toString(), JLabel.CENTER));
			return;
		}
		setDocData(docData);

		// Заголовок окна
		showTitle(Loc.get("company"), "name");

		// Расположение элементов управления
		GBL g = new GBL((JPanel) getContentPane(), true);
		IControl<String> conStr;
		IControl<Boolean> conBool;
		IControl<Integer> conInt;
		ArrayList<CatPos> groups = docData.getGroups();

		g.addFixL(new JLabel(Loc.get("name") + ":"), 10);
		conStr = new ConString(docData, "name", 32, 32);
		g.addExtH(conStr.getComp(), 20);

		g.addFixL(new JLabel(Loc.get("group") + ":"), 10);
		conInt = new ConСatBox(docData, "group_id", groups);
		g.addFixL(conInt.getComp(), 10);

		g.newLine();
		g.addFixL(new JLabel(""), 10);

		conBool = new ConBoolean(docData, "customer", Loc.get("customer"));
		g.addFixL(conBool.getComp(), 10);

		conBool = new ConBoolean(docData, "supplier", Loc.get("supplier"));
		g.addFixL(conBool.getComp(), 10);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("full_name") + ":"), 10);
		conStr = new ConString(docData, "full_name", 60, 128);
		g.addExtH(conStr.getComp(), 40);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("address") + ":"), 10);
		conStr = new ConString(docData, "address", 64, 128);
		g.addExtH(conStr.getComp(), 40);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("telephone") + ":"), 10);
		conStr = new ConString(docData, "telephone", 64, 32);
		g.addExtH(conStr.getComp(), 20);

		g.addFixL(new JLabel(Loc.get("email") + ":"), 10);
		conStr = new ConString(docData, "email", 64, 32);
		g.addExtH(conStr.getComp(), 10);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("site") + ":"), 10);
		conStr = new ConString(docData, "site", 64, 128);
		g.addExtH(conStr.getComp(), 40);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("reg_no") + ":"), 10);
		conStr = new ConString(docData, "reg_no", 64, 32);
		g.addExtH(conStr.getComp(), 20);

		g.addFixL(new JLabel(Loc.get("town") + ":"), 10);
		conStr = new ConString(docData, "town", 64, 64);
		g.addExtH(conStr.getComp(), 10);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("inn") + ":"), 10);
		conStr = new ConString(docData, "inn", 64, 32);
		g.addExtH(conStr.getComp(), 20);

		g.addFixL(new JLabel(Loc.get("kpp") + ":"), 10);
		conStr = new ConString(docData, "kpp", 64, 32);
		g.addExtH(conStr.getComp(), 10);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("okato") + ":"), 10);
		conStr = new ConString(docData, "okato", 64, 32);
		g.addExtH(conStr.getComp(), 20);

		g.addFixL(new JLabel(Loc.get("okpo") + ":"), 10);
		conStr = new ConString(docData, "okpo", 64, 32);
		g.addExtH(conStr.getComp(), 10);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("account") + ":"), 10);
		conStr = new ConString(docData, "account", 64, 32);
		g.addExtH(conStr.getComp(), 10);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("bank") + ":"), 10);
		conStr = new ConString(docData, "bank", 64, 128);
		g.addExtH(conStr.getComp(), 40);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("bic") + ":"), 10);
		conStr = new ConString(docData, "bic", 64, 32);
		g.addExtH(conStr.getComp(), 20);

		g.addFixL(new JLabel(Loc.get("corr_acc") + ":"), 10);
		conStr = new ConString(docData, "corr_acc", 64, 32);
		g.addExtH(conStr.getComp(), 10);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("head") + ":"), 10);
		conStr = new ConString(docData, "head", 64, 64);
		g.addExtH(conStr.getComp(), 40);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("chief_accountant") + ":"), 10);
		conStr = new ConString(docData, "chief_accountant", 64, 64);
		g.addExtH(conStr.getComp(), 40);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("accountant") + ":"), 10);
		conStr = new ConString(docData, "accountant", 64, 64);
		g.addExtH(conStr.getComp(), 40);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("issued_by") + ":"), 10);
		conStr = new ConString(docData, "issued_by", 64, 64);
		g.addExtH(conStr.getComp(), 40);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("rem1") + ":"), 10);
		conStr = new ConString(docData, "rem1", 64, 96);
		g.addExtH(conStr.getComp(), 40);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("rem2") + ":"), 10);
		conStr = new ConString(docData, "rem2", 64, 96);
		g.addExtH(conStr.getComp(), 40);

		g.newLine();
		g.addVer(1);

		g.newLine();
		g.addHor(42);
		g.addFixR(new BtnOK(this).getComp(), 4);
		g.addFixR(new BtnCancel(this).getComp(), 4);
	}
}
