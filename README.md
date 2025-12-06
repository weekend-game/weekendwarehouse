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

##### Step 5: Creating the First Document Window

A document is a product receipt, product card, or product shipment. A document is a row from any directory. However, all these documents are stored in the database. In this step, the "Program Setup" document will be created. It stores the name of the DB driver class and the DB connection string. It stores (and reads) this information not in the DB, but in the application's stored properties, i.e., it uses the Proper class.

The document must read the information, display it in a window for subsequent user editing, and be able to save the data or discard the changes. The Document class was created in the previous step. It had only two methods: activated() and deactivated(). These were used to control the activity of menu items and toolbar buttons. Now, additional methods are added: setDocData(), getDocData(), hasChanges(), and save().

A DocData class object is used to store the actual information. It was decided to store all document fields in a Map<String, Object> object. This isn't as fast as a class with a set of member variables that replicate the document structure, but it's acceptable for implementing a UI.

The document window requires elements to build the user interface. The game.weekend.framework.core.controls package is created for this purpose. This package contains the BtnOK and BtnCancel buttons—these are classes that contain regular JButtons. The IControl interface is implemented for input fields. It's currently implemented by a single ConString field. A larger set of fields will be needed later to build the interface.

The "Program Settings" window (game.weekend.framework.utility.progprop package) is created from the above. Its component classes are located here: ActProgProp (Action for including in the menu), ProgPropData (adapting DocData to the specifics of the window), and ProgPropDoc itself. A corresponding menu item (MenuBar) is added. ActProgProp is created in the WeekendWarehouse class.
