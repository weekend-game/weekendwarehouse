package game.weekend.warehouse.documents;

import java.sql.Date;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import game.weekend.framework.core.Acts;
import game.weekend.framework.core.DocData;
import game.weekend.framework.core.DocumentWithAList;
import game.weekend.framework.core.FrameManager;
import game.weekend.framework.core.GBL;
import game.weekend.framework.core.Loc;
import game.weekend.framework.core.controls.BtnCancel;
import game.weekend.framework.core.controls.BtnOK;
import game.weekend.framework.core.controls.ConDate;
import game.weekend.framework.core.controls.ConString;
import game.weekend.framework.core.controls.IControl;
import game.weekend.framework.core.table.Table;
import game.weekend.framework.core.table.TableDefinition;

/**
 * Документ номер один.
 */
@SuppressWarnings("serial")
public class ReceiptsDoc extends DocumentWithAList {

	/**
	 * Создать окно документа.
	 * 
	 * @param id                 ID документа.
	 * @param mode               режим редактирования.
	 * @param parentFrameManager родительский менеджер расположения окон.
	 */
	public ReceiptsDoc(int id, int mode, FrameManager parentFrameManager) {
		super(id, mode, parentFrameManager, "game.weekend.warehouse.documents.ReceiptsDocPos");
	}

	@Override
	public void initComponents() {

		// Данные
		DocData docData;
		try {
			docData = new ReceiptsDocData(getId(), getMode(), this);
		} catch (Exception e) {
			getContentPane().add(new JLabel(e.toString(), JLabel.CENTER));
			return;
		}
		setDocData(docData);

		// Заголовок окна
		showTitle(Loc.get("receipt"), "doc_numb");

		// Элементы управления
		GBL g = new GBL((JPanel) getContentPane(), true);
		IControl<String> conStr;
		IControl<Date> conDate;

		g.newLine();
		g.addFixL(new JLabel(Loc.get("document") + " N "), 10);
		conStr = new ConString(docData, "doc_numb", 10, 16);
		g.addFixL(conStr.getComp(), 10);

		g.addFixL(new JLabel(Loc.get("from")), 5);
		conDate = new ConDate(docData, "doc_date");
		g.addFixL(conDate.getComp(), 10);

		// Поставщик
		g.newLine();
		g.addFixL(new JLabel(Loc.get("supplier") + ":"), 10);

		JTextField txtSupName = new JTextField();
		txtSupName.setColumns(16);
		txtSupName.setMinimumSize(txtSupName.getPreferredSize());
		txtSupName.setText(docData.getStringValue("name"));
		g.addFixL(txtSupName, 10);

		g.newLine();
		g.addFixL(new JLabel("Комментарий:"), 10);
		conStr = new ConString(docData, "rem", 60, 128);
		g.addExtH(conStr.getComp(), 40);

		Table table = null;
		try {
			TableDefinition tableDefinition = getMainFrame().getDB().getTableDefinition("list");
			@SuppressWarnings("unchecked")
			ArrayList<DocData> arrayDocData = (ArrayList<DocData>) docData.getValue("list");

			table = new Table(tableDefinition, getMainFrame().getActs(), arrayDocData);
			setTable(table);

		} catch (Exception e) {
			System.out.println(e);
		}

		if (table != null) {
			g.newLine();
			g.addExtB(table.getPane(), 50, 1);
		}

		g.newLine();
		g.addHor(30);
		g.addFixR(new BtnOK(this).getComp(), 10);
		g.addFixR(new BtnCancel(this).getComp(), 10);
	}

	@Override
	public void activated() {
		Acts acts = getMainFrame().getActs();
		acts.setEnabled("Print", false);

		acts.setEnabled("Add", true);
		acts.setEnabled("AddCopy", false);
		acts.setEnabled("Edit", true);
		acts.setEnabled("Delete", true);
	}
}
