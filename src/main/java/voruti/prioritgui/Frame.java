package voruti.prioritgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;

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

	private JList<String> itemList;

	public Frame(App app) {
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
			if (!event.getValueIsAdjusting()) { // TODO is triggered to often
//				itemList.clearSelection();
				app.openItem(event.getFirstIndex());
			}
		});
		panel.add(itemList, BorderLayout.CENTER);
	}

	public void putItemsInList(List<Item> list) {
		Object[] items = list.stream()
				.map(i -> String.format("%s (%s)", i.getTitle(), i.getuName()))
				.toArray();
		itemList.setListData(Arrays.copyOf(items, items.length, String[].class));
	}
}
