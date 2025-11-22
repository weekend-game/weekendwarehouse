package game.weekend.warehouse;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import game.weekend.framework.core.Acts;
import game.weekend.framework.core.Loc;
import game.weekend.framework.core.MainFrame;
import game.weekend.framework.core.Mes;
import game.weekend.framework.core.Proper;
import game.weekend.framework.core.StatusBar;
import game.weekend.framework.core.acts.ActAdd;
import game.weekend.framework.core.acts.ActAddCopy;
import game.weekend.framework.core.acts.ActDelete;
import game.weekend.framework.core.acts.ActEdit;
import game.weekend.framework.core.acts.ActFilter;
import game.weekend.framework.core.acts.ActFind;
import game.weekend.framework.core.acts.ActFindBack;
import game.weekend.framework.core.acts.ActFindForward;
import game.weekend.framework.core.acts.ActPrint;

public class WeekendWarehouse {

	public static final String IMAGE_PATH = "/game/weekend/warehouse/images/";

	public static final String APP_NAME = "WeekendWarehouse";
	public static final String APP_VERSION = "00.01";
	public static final String APP_DATE = "22.11.2025";
	public static final String APP_COPYRIGHT = "(c) Weekend Game, 2025";
	public static final String APP_OTHER = "Weekend Warehouse";

	public final String appIcon = null;

	/**
	 * Создать объект-приложение.
	 * 
	 * @return объект-приложение.
	 */
	public WeekendWarehouse() {
		Proper pro = new Proper(APP_NAME);

		// Язык интерфейса
		Loc.setLanguage(pro.getProperty("Language", "en"));

		Acts acts = new Acts();

		mainFrame = new MainFrame(APP_NAME, appIcon, pro, new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				WeekendWarehouse.this.close();
			}
		});

		StatusBar statusBar = new StatusBar();
		Mes mes = new Mes(APP_NAME, mainFrame.getFrame(), statusBar);

		// Файл
		acts.putAct("Print", new ActPrint(mainFrame));
		acts.putAct("Exit", new ActExit(this));

		// Редактирование
		acts.putAct("Add", new ActAdd(mainFrame));
		acts.putAct("AddCopy", new ActAddCopy(mainFrame));
		acts.putAct("Edit", new ActEdit(mainFrame));
		acts.putAct("Delete", new ActDelete(mainFrame));

		// Просмотр
		acts.putAct("Find", new ActFind(mainFrame));
		acts.putAct("FindForward", new ActFindForward(mainFrame));
		acts.putAct("FindBack", new ActFindBack(mainFrame));
		acts.putAct("Filter", new ActFilter(mainFrame));

		// Документы
		acts.putAct("Receipts", new ActReceipts(mainFrame));
		acts.putAct("WarehouseAccountingCards", new ActWarehouseAccountingCards(mainFrame));
		acts.putAct("GoodsMovement", new ActGoodsMovement(mainFrame));
		acts.putAct("Issue", new ActIssue(mainFrame));

		// Справочники
		acts.putAct("GroupsOfCompanies", new ActGroupsOfCompanies(mainFrame));
		acts.putAct("Companies", new ActCompanies(mainFrame));
		acts.putAct("GroupsOfProducts", new ActGroupsOfProducts(mainFrame));
		acts.putAct("Products", new ActProducts(mainFrame));

		// Помощь
		acts.putAct("About", new ActAbout(mes));

		mainFrame.setMenuBar(new MenuBar(acts));
		mainFrame.setToolBar(new ToolBar(acts));
		mainFrame.setStatusBar(statusBar);
		mainFrame.setMes(mes);
		mainFrame.setActs(acts);
		mainFrame.show();
	}

	/**
	 * Запустить приложение.
	 */
	public static void main(String[] args) {
		new WeekendWarehouse();
	}

	/**
	 * Закрыть приложение.
	 * 
	 * @return true если приложение успешно закрылось.
	 */
	public boolean close() {
		return mainFrame.close();
	}

	private MainFrame mainFrame;
}
