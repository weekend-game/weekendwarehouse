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
import game.weekend.framework.core.acts.ActRefresh;
import game.weekend.framework.utility.help.ActHelp;
import game.weekend.framework.utility.progprop.ActProgProp;
import game.weekend.warehouse.directories.AutonumbererAction;
import game.weekend.warehouse.directories.CompaniesAction;
import game.weekend.warehouse.directories.GeneralAction;
import game.weekend.warehouse.directories.GroupsOfCompaniesAction;
import game.weekend.warehouse.directories.GroupsOfProductsAction;
import game.weekend.warehouse.directories.ProductsAction;
import game.weekend.warehouse.directories.WarehousesAction;
import game.weekend.warehouse.documents.CardsAction;
import game.weekend.warehouse.documents.IssueAction;
import game.weekend.warehouse.documents.ReceiptsAction;

public class WeekendWarehouse {

	public static final String IMAGE_PATH = "/game/weekend/warehouse/images/";

	public static final String APP_NAME = "WeekendWarehouse";
	public static final String APP_VERSION = "00.10";
	public static final String APP_DATE = "21.12.2025";
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
		DB db = new DB(mes, pro);

		// Файл
		acts.putAct("Print", new ActPrint(mainFrame));
		acts.putAct("ProgProp", new ActProgProp(mainFrame));
		acts.putAct("Exit", new ActExit(this));

		acts.setEnabled("Print", false);

		// Редактирование
		acts.putAct("Add", new ActAdd(mainFrame));
		acts.putAct("AddCopy", new ActAddCopy(mainFrame));
		acts.putAct("Edit", new ActEdit(mainFrame));
		acts.putAct("Delete", new ActDelete(mainFrame));

		acts.setEnabled("Add", false);
		acts.setEnabled("AddCopy", false);
		acts.setEnabled("Edit", false);
		acts.setEnabled("Delete", false);

		// Просмотр
		acts.putAct("Find", new ActFind(mainFrame));
		acts.putAct("FindForward", new ActFindForward(mainFrame));
		acts.putAct("FindBack", new ActFindBack(mainFrame));
		acts.putAct("Filter", new ActFilter(mainFrame));
		acts.putAct("Refresh", new ActRefresh(mainFrame));

		acts.setEnabled("Find", false);
		acts.setEnabled("FindForward", false);
		acts.setEnabled("FindBack", false);
		acts.setEnabled("Filter", false);

		// Документы
		acts.putAct("Receipts", new ReceiptsAction(mainFrame));
		acts.putAct("Cards", new CardsAction(mainFrame));
		acts.putAct("Issue", new IssueAction(mainFrame));

		// Справочники
		acts.putAct("General", new GeneralAction(mainFrame));
		acts.putAct("Warehouses", new WarehousesAction(mainFrame));
		acts.putAct("GroupsOfCompanies", new GroupsOfCompaniesAction(mainFrame));
		acts.putAct("Companies", new CompaniesAction(mainFrame));
		acts.putAct("GroupsOfProducts", new GroupsOfProductsAction(mainFrame));
		acts.putAct("Products", new ProductsAction(mainFrame));
		acts.putAct("Autonumberer", new AutonumbererAction(mainFrame));

		// Помощь
		acts.putAct("Help", new ActHelp(mainFrame));
		acts.putAct("About", new ActAbout(mes));

		mainFrame.setMenuBar(new MenuBar(mainFrame, acts));
		mainFrame.setToolBar(new ToolBar(acts));
		mainFrame.setStatusBar(statusBar);
		mainFrame.setMes(mes);
		mainFrame.setDB(db);
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
