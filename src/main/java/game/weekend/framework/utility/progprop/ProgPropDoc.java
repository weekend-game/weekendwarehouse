package game.weekend.framework.utility.progprop;

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
 * "Настройка программы..."
 */
@SuppressWarnings("serial")
public class ProgPropDoc extends Document {

	/**
	 * Создать окно документа.
	 * 
	 * @param id             ID документа (не используется).
	 * @param mode           режим редактирования (не используется).
	 * @param parentFrameMan родительский менеджер расположения окон.
	 */
	public ProgPropDoc(int id, int mode, FrameManager parentFrameManager) {
		super(id, mode, parentFrameManager);
		setTitle(Loc.get("setting_up_the_program"));
	}

	@Override
	public void initComponents() {
		DocData docData = new ProgPropData(this);
		setDocData(docData);

		GBL g = new GBL((JPanel) getContentPane(), true);
		IControl<String> getStr;

		g.addFixL(new JLabel(Loc.get("database_driver") + ":"), 1);
		getStr = new ConString(docData, "driver", 30, 40) {
			public String valid() {
				if (getValue().length() == 0) {
					return Loc.get("specify_the_database_driver") + ".";
				}
				return null;
			}
		};
		g.addExtH(getStr.getComp(), 4);

		g.newLine();
		g.addFixL(new JLabel(Loc.get("connection_string") + ":"), 1);
		getStr = new ConString(docData, "constr", 30, 40) {
			public String valid() {
				if (getValue().length() == 0) {
					return Loc.get("specify_the_connection_string_to_the_database") + ".";
				}
				return null;
			}
		};
		g.addExtH(getStr.getComp(), 4);

		g.newLine();
		g.addVer(5);

		g.newLine();
		g.addHor(3);
		g.addFixR(new BtnOK(this).getComp(), 1);
		g.addFixR(new BtnCancel(this).getComp(), 1);
	}
}
