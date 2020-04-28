package voruti.prioritgui;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

import voruti.priorit.PrioritManager;

/**
 * @author voruti
 *
 */
public class App {

	private Frame frame;
	private ItemDetail itemDetail;
	private PrioritManager manager;

	/**
	 * {@link #openItem(int)}
	 */
	private int lastIndex;

	public App() {
		this.lastIndex = -1;

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

		frame.putItemsInList(manager.getItems());
	}

	public void openItem(int index) {
		if (lastIndex != index) {
			System.out.println(index);
			lastIndex = index;

			itemDetail.setItem(manager.getItems()
					.get(index));
			itemDetail.setVisible(true);
		} else {
			lastIndex = -1;
		}
	}

	public static void main(String[] args) {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF_%1$tT][%2$-50.50s][%4$13.13s]: %5$s%n");
		Logger.getGlobal()
				.getParent()
				.setLevel(Level.CONFIG);
		Logger.getGlobal()
				.getParent()
				.getHandlers()[0].setLevel(Level.ALL);

		new App();
	}

}
