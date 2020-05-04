package voruti.prioritgui;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JList;

import voruti.priorit.Item;
import voruti.priorit.PrioritManager;

/**
 * Program logic for this application.
 * 
 * @author voruti
 */
public class App {

	private static final String CLASS_NAME = App.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

	private Frame frame;
	private ItemDetail itemDetail;
	private PrioritManager manager;

	/**
	 * Initializes the necessary {@link PrioritManager} and frames.
	 */
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

		reloadList();

		LOGGER.exiting(CLASS_NAME, METHOD_NAME);
	}

	/**
	 * Reloads the {@link Item items} in the {@link JList} of {@link #frame}.
	 */
	public void reloadList() {
		final String METHOD_NAME = "reloadList";
		LOGGER.entering(CLASS_NAME, METHOD_NAME);

		LOGGER.log(Level.FINE, "Putting items from manager to gui view");
		frame.putItemsInList(manager.getAllItems());

		LOGGER.exiting(CLASS_NAME, METHOD_NAME);
	}

	/**
	 * Shows {@link Item} with {@code index} in {@link #itemDetail}.
	 * 
	 * @param index the {@link Item} position
	 */
	public void openItem(int index) {
		final String METHOD_NAME = "openItem";
		LOGGER.entering(CLASS_NAME, METHOD_NAME, index);

		frame.setVisible(false);

		itemDetail.setItem(manager.getAllItems()
				.get(index));

		itemDetail.setVisible(true);

		LOGGER.exiting(CLASS_NAME, METHOD_NAME);
	}

	/**
	 * Creates a new {@link Item} with default values and shows it in
	 * {@link #itemDetail}.
	 */
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

	/**
	 * Updates {@link Item item} and shows {@link #frame} again.
	 * 
	 * @param item the {@link Item} to update
	 */
	public void closingItem(Item item) {
		final String METHOD_NAME = "closingItem";
		LOGGER.entering(CLASS_NAME, METHOD_NAME, item);

		if (!manager.updateItem(item))
			LOGGER.log(Level.WARNING, "Error on updating item={0}", item);

		reloadList();
		frame.setVisible(true);
		frame.resetSelectionTrigger();

		LOGGER.exiting(CLASS_NAME, METHOD_NAME);
	}

	/**
	 * Redirects {@link PrioritManager#getAllCategories()}.
	 * 
	 * @return the redirected {@link List}
	 */
	public List<String> getAllCategories() {
		final String METHOD_NAME = "getFileToItem";
		LOGGER.entering(CLASS_NAME, METHOD_NAME);

		List<String> categories = manager.getAllCategories();

		LOGGER.exiting(CLASS_NAME, METHOD_NAME, categories);
		return categories;
	}

	public static void main(String[] args) {
		final String METHOD_NAME = "main";
		LOGGER.entering(CLASS_NAME, METHOD_NAME, args);

		new App();

		LOGGER.exiting(CLASS_NAME, METHOD_NAME);
	}
}
