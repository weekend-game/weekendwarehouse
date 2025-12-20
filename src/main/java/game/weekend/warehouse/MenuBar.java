package game.weekend.warehouse;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JSeparator;

import game.weekend.framework.core.Acts;
import game.weekend.framework.core.IMenuBar;
import game.weekend.framework.core.Loc;
import game.weekend.framework.core.MainFrame;
import game.weekend.framework.utility.winmenu.WinListener;

/**
 * Меню приложения.
 */
class MenuBar implements IMenuBar {

	/**
	 * Создать меню приложения.
	 **/
	public MenuBar(MainFrame mainFrame, Acts acts) {

		menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu(Loc.get("file"));
		fileMenu.add(acts.getAct("Print"));
		fileMenu.add(new JSeparator());
		fileMenu.add(acts.getAct("ProgProp"));
		fileMenu.add(new JSeparator());
		fileMenu.add(acts.getAct("Exit"));
		menuBar.add(fileMenu);

		// Будет переопределено активным окном
		editMenu = new JMenu(Loc.get("edit"));
		defaultEditMenu();
		menuBar.add(editMenu);

		// Будет переопределено активным окном
		viewMenu = new JMenu(Loc.get("view"));
		defaultViewMenu();
		menuBar.add(viewMenu);

		JMenu documentsMenu = new JMenu(Loc.get("documents"));
		documentsMenu.add(acts.getAct("Receipts"));
		documentsMenu.add(new JSeparator());
		documentsMenu.add(acts.getAct("Cards"));
		documentsMenu.add(new JSeparator());
		documentsMenu.add(acts.getAct("Issue"));
		menuBar.add(documentsMenu);

		JMenu directoriesMenu = new JMenu(Loc.get("directories"));
		directoriesMenu.add(acts.getAct("General"));
		directoriesMenu.add(new JSeparator());
		directoriesMenu.add(acts.getAct("Warehouses"));
		directoriesMenu.add(new JSeparator());
		directoriesMenu.add(acts.getAct("GroupsOfCompanies"));
		directoriesMenu.add(acts.getAct("Companies"));
		directoriesMenu.add(new JSeparator());
		directoriesMenu.add(acts.getAct("GroupsOfProducts"));
		directoriesMenu.add(acts.getAct("Products"));
		menuBar.add(directoriesMenu);

		// Создается в момент его раскрытия
		JMenu windowMenu = new JMenu(Loc.get("windows"));
		windowMenu.addMenuListener(new WinListener(mainFrame, windowMenu));
		menuBar.add(windowMenu);

		JMenu helpMenu = new JMenu(Loc.get("help"));
		helpMenu.add(acts.getAct("Help"));
		helpMenu.add(new JSeparator());
		helpMenu.add(acts.getAct("About"));
		menuBar.add(helpMenu);
	}

	@Override
	public JMenuBar getJMenuBar() {
		return menuBar;
	}

	@Override
	public JMenu getEditMenu() {
		return editMenu;
	}

	@Override
	public void defaultEditMenu() {
		editMenu.removeAll();
		editMenu.setEnabled(false);
	}

	@Override
	public JMenu getViewMenu() {
		return viewMenu;
	}

	@Override
	public void defaultViewMenu() {
		viewMenu.removeAll();
		viewMenu.setEnabled(false);
	}

	private JMenuBar menuBar;
	private JMenu editMenu;
	private JMenu viewMenu;
}
