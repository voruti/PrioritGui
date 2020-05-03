package voruti.prioritgui;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import voruti.priorit.Item;
import voruti.priorit.PrioritManager;

/**
 * @author voruti
 *
 */
public class App {

	private static final String CLASS_NAME = App.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

	private Frame frame;
	private ItemDetail itemDetail;
	private PrioritManager manager;

	public App() {
		final String METHOD_NAME = "<init>";
		LOGGER.entering(CLASS_NAME, METHOD_NAME);

		try {
			EventQueue.invokeAndWait(() -> {
				frame = new Frame(this);
				frame.setVisible(true);
			});
		} catch (InterruptedException | InvocationTargetException e) {
			e.printStackTrace();
			System.exit(1);
		}

		try {
			EventQueue.invokeAndWait(() -> {
				itemDetail = new ItemDetail(this);
			});
		} catch (InterruptedException | InvocationTargetException e) {
			e.printStackTrace();
			System.exit(1);
		}

		try {
			manager = new PrioritManager(new File("items"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		LOGGER.log(Level.FINE, "Putting items from manager to gui view");
		frame.putItemsInList(manager.getAllItems());

		LOGGER.exiting(CLASS_NAME, METHOD_NAME);
	}

	public void openItem(int index) {
		final String METHOD_NAME = "openItem";
		LOGGER.entering(CLASS_NAME, METHOD_NAME, index);

		frame.setVisible(false);

		itemDetail.setItem(manager.getAllItems()
				.get(index));

		itemDetail.setVisible(true);

		LOGGER.exiting(CLASS_NAME, METHOD_NAME);
	}

	public void newItem() {
		final String METHOD_NAME = "newItem";
		LOGGER.entering(CLASS_NAME, METHOD_NAME);

		Item item = new Item();
		if (manager.addItem(item)) {
			itemDetail.setItem(item);

			frame.setVisible(false);
			itemDetail.setVisible(true);
		} else {
			System.err.println("Error!");
		}

		LOGGER.exiting(CLASS_NAME, METHOD_NAME);
	}

	public void closingItem(Item item) {
		final String METHOD_NAME = "closingItem";
		LOGGER.entering(CLASS_NAME, METHOD_NAME, item);

		if (!manager.updateItem(item))
			LOGGER.log(Level.WARNING, "Error on updating item={0}", item);

		frame.putItemsInList(manager.getAllItems());
		frame.setVisible(true);
		frame.resetSelectionTrigger();

		LOGGER.exiting(CLASS_NAME, METHOD_NAME);
	}

	public static void main(String[] args) {
		final String METHOD_NAME = "main";
		LOGGER.entering(CLASS_NAME, METHOD_NAME, args);

		System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF_%1$tT][%2$-50.50s][%4$13.13s]: %5$s%n");
		Logger.getGlobal()
				.getParent()
				.setLevel(Level.CONFIG);
		iLog();
		Frame.iLog();
		ItemDetail.iLog();
		Item.iLog();
		PrioritManager.iLog();

		new App();

		LOGGER.exiting(CLASS_NAME, METHOD_NAME);
	}

	public static void iLog() {
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(Level.ALL);
		LOGGER.addHandler(consoleHandler);
	}

}
