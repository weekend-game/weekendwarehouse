package game.weekend.framework.core;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

/**
 * Интерфейс объекта реализующего меню приложения.
 */
public interface IMenuBar {

	/**
	 * Получить линейку меню приложения.
	 * 
	 * @return линейка меню приложения.
	 */
	public JMenuBar getJMenuBar();

	/**
	 * Получить меню "Редактирование".
	 * 
	 * @return меню "Редактирование".
	 */
	public JMenu getEditMenu();

	/**
	 * Создать меню "Редактирование" по умолчанию.
	 */
	public void defaultEditMenu();

	/**
	 * Получить меню "Просмотр".
	 * 
	 * @return меню "Просмотр".
	 */
	public JMenu getViewMenu();

	/**
	 * Создать меню "Просмотр" по умолчанию.
	 */
	public void defaultViewMenu();

	/**
	 * Реализация пустого меню приложения.
	 */
	public static final IMenuBar NULL = new IMenuBar() {

		public JMenuBar getJMenuBar() {
			return null;
		}

		public JMenu getEditMenu() {
			return null;
		}

		public void defaultEditMenu() {
		}

		public JMenu getViewMenu() {
			return null;
		}

		public void defaultViewMenu() {
		}
	};
}
