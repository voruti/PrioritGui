package voruti.prioritgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import voruti.priorit.Item;

/**
 * The "main frame".
 * 
 * @author voruti
 */
public class Frame extends JFrame {

	private static final long serialVersionUID = 7468802781892800155L;

	private static final String CLASS_NAME = Frame.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

	private App app;

	private JList<String> itemList;

	private boolean itemSelectionTriggered;

	/**
	 * Initializes the/this frame.
	 * 
	 * @param app the {@link App} to notify when opening an {@link Item}; also
	 *            enables the frame to access data from {@link App}
	 */
	public Frame(App app) {
		final String METHOD_NAME = "<init>";
		LOGGER.entering(CLASS_NAME, METHOD_NAME, app);

		this.app = app;
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

		itemList.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				bundledKeyAction(e);
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
		btnNew.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				bundledKeyAction(e);
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});

		LOGGER.exiting(CLASS_NAME, METHOD_NAME);
	}

	/**
	 * Bundles key events for packed actions.
	 * 
	 * @param e the {@link KeyEvent}
	 */
	private void bundledKeyAction(KeyEvent e) {
		final String METHOD_NAME = "bundledKeyAction";
		LOGGER.entering(CLASS_NAME, METHOD_NAME, e);

		switch (e.getKeyCode()) {
		case 116: // F5
			app.reloadList();
			break;

		default:
			System.out.println(e);
			break;
		}

		LOGGER.exiting(CLASS_NAME, METHOD_NAME);
	}

	/**
	 * Loads all {@link Item items} from {@link List list} into the {@link JList} in
	 * this frame.
	 * 
	 * @param list the {@link Item} {@link List} to load
	 */
	public void putItemsInList(List<Item> list) {
		final String METHOD_NAME = "putItemsInList";
		LOGGER.entering(CLASS_NAME, METHOD_NAME, list);

		Object[] items = list.stream()
				.map(i -> i.toString())
				.toArray();
		itemList.setListData(Arrays.copyOf(items, items.length, String[].class));

		LOGGER.exiting(CLASS_NAME, METHOD_NAME);
	}

	/**
	 * Resets {@link #itemSelectionTriggered}, so another {@link JList} entry can be
	 * selected.
	 */
	public void resetSelectionTrigger() {
		final String METHOD_NAME = "resetSelectionTrigger";
		LOGGER.entering(CLASS_NAME, METHOD_NAME);

		itemSelectionTriggered = false;

		LOGGER.exiting(CLASS_NAME, METHOD_NAME);
	}
}
