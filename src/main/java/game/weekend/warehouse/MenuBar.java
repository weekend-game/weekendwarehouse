package game.weekend.warehouse;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JSeparator;

import game.weekend.framework.core.Acts;
import game.weekend.framework.core.Loc;

/**
 * Меню приложения.
 */
@SuppressWarnings("serial")
class MenuBar extends JMenuBar {

	/**
	 * Создать меню приложения.
	 **/
	public MenuBar(Acts acts) {

		JMenu fileMenu = new JMenu(Loc.get("file"));
		fileMenu.add(acts.getAct("Print"));
		fileMenu.add(new JSeparator());
		fileMenu.add(acts.getAct("Exit"));
		add(fileMenu);

		JMenu editMenu = new JMenu(Loc.get("edit"));
		editMenu.add(acts.getAct("Add"));
		editMenu.add(acts.getAct("AddCopy"));
		editMenu.add(acts.getAct("Edit"));
		editMenu.add(new JSeparator());
		editMenu.add(acts.getAct("Delete"));
		add(editMenu);

		JMenu viewMenu = new JMenu(Loc.get("view"));
		viewMenu.add(acts.getAct("Find"));
		viewMenu.add(acts.getAct("FindForward"));
		viewMenu.add(acts.getAct("FindBack"));
		viewMenu.add(new JSeparator());
		viewMenu.add(acts.getAct("Filter"));
		add(viewMenu);

		JMenu documentsMenu = new JMenu(Loc.get("documents"));
		documentsMenu.add(acts.getAct("Receipts"));
		documentsMenu.add(new JSeparator());
		documentsMenu.add(acts.getAct("WarehouseAccountingCards"));
		documentsMenu.add(acts.getAct("GoodsMovement"));
		documentsMenu.add(new JSeparator());
		documentsMenu.add(acts.getAct("Issue"));
		add(documentsMenu);

		JMenu directoriesMenu = new JMenu(Loc.get("directories"));
		directoriesMenu.add(acts.getAct("GroupsOfCompanies"));
		directoriesMenu.add(acts.getAct("Companies"));
		directoriesMenu.add(new JSeparator());
		directoriesMenu.add(acts.getAct("GroupsOfProducts"));
		directoriesMenu.add(acts.getAct("Products"));
		add(directoriesMenu);

		JMenu helpMenu = new JMenu(Loc.get("help"));
		helpMenu.add(acts.getAct("About"));
		add(helpMenu);
	}
}
