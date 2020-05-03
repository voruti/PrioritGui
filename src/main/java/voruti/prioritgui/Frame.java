package voruti.prioritgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import voruti.priorit.Item;

/**
 * @author voruti
 *
 */
public class Frame extends JFrame {

	private static final long serialVersionUID = 7468802781892800155L;

	private static final String CLASS_NAME = Frame.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

	private JList<String> itemList;

	private boolean itemSelectionTriggered;

	public Frame(App app) {
		final String METHOD_NAME = "<init>";
		LOGGER.entering(CLASS_NAME, METHOD_NAME, app);

		this.itemSelectionTriggered = false;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 800);
		setLocationRelativeTo(null);
		setTitle("PrioritGui");

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		setContentPane(panel);

		itemList = new JList<>();
		itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		itemList.setForeground(Color.WHITE);
		itemList.setFont(new Font("Arial", Font.PLAIN, 30));
		itemList.setBackground(new Color(0x2F, 0x2F, 0x2F));
		itemList.addListSelectionListener(event -> {
			if (!event.getValueIsAdjusting()) {
				if (!itemSelectionTriggered) { // TODO triggering wrong item sometimes
					itemSelectionTriggered = true;

					app.openItem(event.getFirstIndex());
				}
				itemList.clearSelection();
			}
		});
		panel.add(itemList, BorderLayout.CENTER);

		JButton btnNew = new JButton("New item");
		btnNew.setForeground(Color.WHITE);
		btnNew.setFont(new Font("Arial", Font.PLAIN, 30));
		btnNew.setBackground(new Color(0x2F, 0x2F, 0x2F));
		btnNew.addActionListener(event -> {
			app.newItem();
		});
		panel.add(btnNew, BorderLayout.SOUTH);

		LOGGER.exiting(CLASS_NAME, METHOD_NAME);
	}

	public void putItemsInList(List<Item> list) {
		final String METHOD_NAME = "putItemsInList";
		LOGGER.entering(CLASS_NAME, METHOD_NAME, list);

		Object[] items = list.stream()
				.map(i -> String.format("%s (%s)", i.getTitle(), i.getuName()))
				.toArray();
		itemList.setListData(Arrays.copyOf(items, items.length, String[].class));

		LOGGER.exiting(CLASS_NAME, METHOD_NAME);
	}

	public void resetSelectionTrigger() {
		itemSelectionTriggered = false;
	}

	public static void iLog() {
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(Level.ALL);
		LOGGER.addHandler(consoleHandler);
	}
}
