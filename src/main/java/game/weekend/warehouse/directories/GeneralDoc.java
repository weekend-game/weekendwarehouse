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
		IControl<String> getStr;

		g.addFixL(new JLabel(Loc.get("content") + ":"), 1);
		getStr = new ConString(docData, "content", 64, 64);
		g.addExtH(getStr.getComp(), 4);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("version_db") + ":"), 1);
		getStr = new ConString(docData, "version", 5, 5);
		getStr.setEnable(false);
		g.addFixL(getStr.getComp(), 1);

		g.newLine();
		g.addVer(5);

		g.newLine();
		g.addHor(3);
		g.addFixR(new BtnOK(this).getComp(), 1);
		g.addFixR(new BtnCancel(this).getComp(), 1);
	}
}
