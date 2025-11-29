## WeekendWarehouse

But before we start working with the DBMS, we need to implement MDI, or at least the basics.

##### Step 1

...

##### Step 2

...

##### Step 3

In the IntFrame class, I add empty methods activate() and deactivate(). They are called in the InternalFrameAdapter for window activation and deactivation events, respectively. I'll describe their use below.

The "Edit" and "View" menus can't be permanently active in an application. They contain items relevant to most windows, but not all. Therefore, their activity should be determined by the current window. Moreover, immediately after the application starts, there is no active window, meaning these menus, as well as all the items they contain, should be inactive.

Therefore, I create the **IMenuBar** interface, which the MenuBar class will implement. The MenuBar constructor will generate the application menu (JMenuBar), but without the Edit and View menus. Instead, it calls the following methods:
public void defaultEditMenu()
and
public void defaultViewMenu(),
which generate empty, inactive menus.

Any application window that inherits IntFrame must define these two menus from the available actions in the activated() method mentioned above. And in the deactivated() method, it must deactivate the actions used.

To display journals and document directories, I create a **Journal** class. To display the documents that make up the journals and directories, I create a **Document** class. These classes implement the most commonly used Edit and View menus. Naturally, each specific journal and document can override them in its own way.
