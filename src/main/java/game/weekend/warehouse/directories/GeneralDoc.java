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
 * "Общее..."
 */
@SuppressWarnings("serial")
public class GeneralDoc extends Document {

	/**
	 * Создать окно документа.
	 * 
	 * @param id             ID документа (не используется).
	 * @param mode           режим редактирования (не используется).
	 * @param parentFrameMan родительский менеджер расположения окон.
	 */
	public GeneralDoc(int id, int mode, FrameManager parentFrameManager) {
		super(id, mode, parentFrameManager);
		setTitle(Loc.get("general"));
	}

	@Override
	public void initComponents() {
		DocData docData = new GeneralData(this);
		setDocData(docData);

		GBL g = new GBL((JPanel) getContentPane(), true);
		IControl<String> conStr;

		g.addFixL(new JLabel(Loc.get("content") + ":"), 10);
		conStr = new ConString(docData, "content", 64, 64);
		g.addExtH(conStr.getComp(), 20);

		g.addFixL(new JLabel(Loc.get("version_db") + ":"), 10);
		conStr = new ConString(docData, "version", 5, 5);
		conStr.setEnable(false);
		g.addFixL(conStr.getComp(), 10);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("company") + ":"), 10);
		conStr = new ConString(docData, "company", 64, 128);
		g.addExtH(conStr.getComp(), 40);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("address") + ":"), 10);
		conStr = new ConString(docData, "address", 64, 128);
		g.addExtH(conStr.getComp(), 40);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("telephone") + ":"), 10);
		conStr = new ConString(docData, "telephone", 64, 128);
		g.addExtH(conStr.getComp(), 20);

		g.addFixL(new JLabel(Loc.get("email") + ":"), 10);
		conStr = new ConString(docData, "email", 64, 128);
		g.addExtH(conStr.getComp(), 10);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("site") + ":"), 10);
		conStr = new ConString(docData, "site", 64, 128);
		g.addExtH(conStr.getComp(), 40);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("reg_no") + ":"), 10);
		conStr = new ConString(docData, "reg_no", 64, 128);
		g.addExtH(conStr.getComp(), 20);

		g.addFixL(new JLabel(Loc.get("town") + ":"), 10);
		conStr = new ConString(docData, "town", 64, 128);
		g.addExtH(conStr.getComp(), 10);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("inn") + ":"), 10);
		conStr = new ConString(docData, "inn", 64, 128);
		g.addExtH(conStr.getComp(), 20);

		g.addFixL(new JLabel(Loc.get("kpp") + ":"), 10);
		conStr = new ConString(docData, "kpp", 64, 128);
		g.addExtH(conStr.getComp(), 10);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("okato") + ":"), 10);
		conStr = new ConString(docData, "okato", 64, 128);
		g.addExtH(conStr.getComp(), 20);

		g.addFixL(new JLabel(Loc.get("okpo") + ":"), 10);
		conStr = new ConString(docData, "okpo", 64, 128);
		g.addExtH(conStr.getComp(), 10);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("account") + ":"), 10);
		conStr = new ConString(docData, "account", 64, 128);
		g.addExtH(conStr.getComp(), 10);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("bank") + ":"), 10);
		conStr = new ConString(docData, "bank", 64, 128);
		g.addExtH(conStr.getComp(), 40);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("bic") + ":"), 10);
		conStr = new ConString(docData, "bic", 64, 128);
		g.addExtH(conStr.getComp(), 20);

		g.addFixL(new JLabel(Loc.get("corr_acc") + ":"), 10);
		conStr = new ConString(docData, "corr_acc", 64, 128);
		g.addExtH(conStr.getComp(), 10);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("head") + ":"), 10);
		conStr = new ConString(docData, "head", 64, 128);
		g.addExtH(conStr.getComp(), 40);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("chief_accountant") + ":"), 10);
		conStr = new ConString(docData, "chief_accountant", 64, 128);
		g.addExtH(conStr.getComp(), 40);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("accountant") + ":"), 10);
		conStr = new ConString(docData, "accountant", 64, 128);
		g.addExtH(conStr.getComp(), 40);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("issued_by") + ":"), 10);
		conStr = new ConString(docData, "issued_by", 64, 128);
		g.addExtH(conStr.getComp(), 40);

		g.newLine();
		g.addVer(1);

		g.newLine();
		g.addHor(42);
		g.addFixR(new BtnOK(this).getComp(), 4);
		g.addFixR(new BtnCancel(this).getComp(), 4);
	}
}
