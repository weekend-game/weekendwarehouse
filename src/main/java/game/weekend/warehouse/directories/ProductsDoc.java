package game.weekend.warehouse.directories;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import game.weekend.framework.core.Document;
import game.weekend.framework.core.FrameManager;
import game.weekend.framework.core.GBL;
import game.weekend.framework.core.Loc;
import game.weekend.framework.core.controls.BtnCancel;
import game.weekend.framework.core.controls.BtnOK;
import game.weekend.framework.core.controls.ConDecimal;
import game.weekend.framework.core.controls.ConString;
import game.weekend.framework.core.controls.ConСatBox;
import game.weekend.framework.core.controls.ConСatBox.CatPos;
import game.weekend.framework.core.controls.IControl;

/**
 * Документ.
 */
@SuppressWarnings("serial")
public class ProductsDoc extends Document {

	/**
	 * Создать окно документа.
	 * 
	 * @param id                 ID документа.
	 * @param mode               режим редактирования.
	 * @param parentFrameManager родительский менеджер расположения окон.
	 */
	public ProductsDoc(int id, int mode, FrameManager parentFrameManager) {
		super(id, mode, parentFrameManager);
	}

	@Override
	public void initComponents() {

		// Данные
		ProductsData docData;
		try {
			docData = new ProductsData(getId(), getMode(), this);
		} catch (Exception e) {
			getContentPane().add(new JLabel(e.toString(), JLabel.CENTER));
			return;
		}
		setDocData(docData);

		// Заголовок окна
		showTitle(Loc.get("product"), "name");

		// Расположение элементов управления
		GBL g = new GBL((JPanel) getContentPane(), true);
		IControl<String> conStr;
		IControl<Integer> conInt;
		IControl<BigDecimal> conDec;
		ArrayList<CatPos> groups = docData.getGroups();

		g.addFixL(new JLabel(Loc.get("article") + ":"), 10);
		conStr = new ConString(docData, "article", 32, 32);
		g.addExtH(conStr.getComp(), 20);

		g.addFixL(new JLabel(Loc.get("group") + ":"), 10);
		conInt = new ConСatBox(docData, "group_id", groups);
		g.addFixL(conInt.getComp(), 10);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("name") + ":"), 10);
		conStr = new ConString(docData, "name", 128, 128);
		g.addExtH(conStr.getComp(), 40);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("unit") + ":"), 10);
		conStr = new ConString(docData, "unit", 32, 32);
		g.addExtH(conStr.getComp(), 20);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("vat") + ":"), 10);
		conDec = new ConDecimal(docData, "vat", "#.00");
		g.addFixL(conDec.getComp(), 5);

		g.newLine();
		g.addVer(1);

		g.newLine();
		g.addHor(42);
		g.addFixR(new BtnOK(this).getComp(), 4);
		g.addFixR(new BtnCancel(this).getComp(), 4);
	}
}
