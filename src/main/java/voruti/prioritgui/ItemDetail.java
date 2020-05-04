package voruti.prioritgui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import voruti.priorit.Item;
import voruti.priorit.Priority;

/**
 * A frame to show detailed {@link Item} information. Also allows editing of
 * these information.
 * 
 * @author voruti
 */
public class ItemDetail extends JFrame {

	private static final long serialVersionUID = -2266535806311908702L;

	private static final String CLASS_NAME = ItemDetail.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

	private static final String TITLE_SUFFIX = " - PrioritGui";

	private final App app;

	private JTextField inpTitle;
	private JTextArea inpText;
	private JList<String> inpCategories;
	private JList<String> inpPriority;
	private UtilDateModel inpEstDateModel;
	private JDatePickerImpl inpEstDate;
	private JCheckBox inpDone;

	/**
	 * Initializes the/this frame.
	 * 
	 * @param app the {@link App} to notify when closing this frame; also enables
	 *            the frame to access data from {@link App}
	 */
	public ItemDetail(App app) {
		final String METHOD_NAME = "<init>";
		LOGGER.entering(CLASS_NAME, METHOD_NAME, app);

		this.app = app;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 650);
		setLocationRelativeTo(null);
		setTitle("[ItemUName] - PrioritGui");

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0x2F, 0x2F, 0x2F));
		panel.setLayout(new FormLayout(
				new ColumnSpec[] {
						FormSpecs.RELATED_GAP_COLSPEC,
						FormSpecs.DEFAULT_COLSPEC,
						FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
						FormSpecs.RELATED_GAP_COLSPEC, },
				new RowSpec[] {
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC,
						FormSpecs.RELATED_GAP_ROWSPEC,
						FormSpecs.DEFAULT_ROWSPEC, }));
		setContentPane(panel);

		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setFont(new Font("Arial", Font.PLAIN, 30));
		lblTitle.setForeground(Color.WHITE);
		panel.add(lblTitle, "2, 2");

		inpTitle = new JTextField();
		inpTitle.setBackground(new Color(0x2F, 0x2F, 0x2F));
		inpTitle.setFont(new Font("Arial", Font.PLAIN, 30));
		inpTitle.setForeground(Color.WHITE);
		inpTitle.setCaretColor(Color.WHITE);
		panel.add(inpTitle, "4, 2");

		JLabel lblText = new JLabel("Text:");
		lblText.setFont(new Font("Arial", Font.PLAIN, 30));
		lblText.setForeground(Color.WHITE);
		panel.add(lblText, "2, 4");

		inpText = new JTextArea();
		inpText.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		inpText.setBackground(new Color(0x2F, 0x2F, 0x2F));
		inpText.setFont(new Font("Arial", Font.PLAIN, 30));
		inpText.setForeground(Color.WHITE);
		inpText.setCaretColor(Color.WHITE);
		inpText.setLineWrap(true);
		panel.add(inpText, "4, 4");

		JLabel lblCategories = new JLabel("Categories:");
		lblCategories.setFont(new Font("Arial", Font.PLAIN, 30));
		lblCategories.setForeground(Color.WHITE);
		panel.add(lblCategories, "2, 6");

		inpCategories = new JList<>();
		inpCategories.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		inpCategories.setForeground(Color.WHITE);
		inpCategories.setFont(new Font("Arial", Font.PLAIN, 30));
		inpCategories.setBackground(new Color(0x2F, 0x2F, 0x2F));
		panel.add(inpCategories, "4, 6");

		JLabel lblPriority = new JLabel("Priority:");
		lblPriority.setFont(new Font("Arial", Font.PLAIN, 30));
		lblPriority.setForeground(Color.WHITE);
		panel.add(lblPriority, "2, 8");

		inpPriority = new JList<>();
		inpPriority.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		inpPriority.setForeground(Color.WHITE);
		inpPriority.setFont(new Font("Arial", Font.PLAIN, 30));
		inpPriority.setBackground(new Color(0x2F, 0x2F, 0x2F));
		inpPriority.setListData(new String[] { "VERY_LOW", "LOW", "MED", "HIGH", "VERY_HIGH" });
		panel.add(inpPriority, "4, 8");

		JLabel lblEstDate = new JLabel("est. Date:");
		lblEstDate.setFont(new Font("Arial", Font.PLAIN, 30));
		lblEstDate.setForeground(Color.WHITE);
		panel.add(lblEstDate, "2, 10");

		inpEstDateModel = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(inpEstDateModel, p);
		AbstractFormatter abstractFormatter = new AbstractFormatter() {

			private static final long serialVersionUID = 2786958155282406860L;

			private String datePattern = "yyyy-MM-dd";
			private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

			@Override
			public Object stringToValue(String text) throws ParseException {
				return dateFormatter.parseObject(text);
			}

			@Override
			public String valueToString(Object value) throws ParseException {
				if (value != null) {
					Calendar cal = (Calendar) value;
					return dateFormatter.format(cal.getTime());
				}

				return "";
			}
		};
		inpEstDate = new JDatePickerImpl(datePanel, abstractFormatter);
		inpEstDate.getJFormattedTextField()
				.setBackground(new Color(0x2F, 0x2F, 0x2F));
		inpEstDate.getJFormattedTextField()
				.setFont(new Font("Arial", Font.PLAIN, 30));
		inpEstDate.getJFormattedTextField()
				.setForeground(Color.WHITE);
		panel.add(inpEstDate, "4, 10");

		JLabel lblDone = new JLabel("Done:");
		lblDone.setFont(new Font("Arial", Font.PLAIN, 30));
		lblDone.setForeground(Color.WHITE);
		panel.add(lblDone, "2, 12");

		inpDone = new JCheckBox();
		inpDone.setBackground(new Color(0x2F, 0x2F, 0x2F));
		panel.add(inpDone, "4, 12");

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				final String METHOD_NAME = "windowClosing";
				LOGGER.entering(CLASS_NAME, METHOD_NAME, e);

				Item item = new Item();
				item.setuName(getTitle().substring(0, getTitle().length() - TITLE_SUFFIX.length()));
				item.setTitle(inpTitle.getText());
				item.setText(inpText.getText());
				List<String> categories = inpCategories.getSelectedValuesList();
				if (!categories.isEmpty())
					item.setCategories(categories);
				item.setPriority(Priority.valueOf(inpPriority.getSelectedValue()));
				item.setEtaDate(inpEstDateModel.getValue());
				item.setDone(inpDone.isSelected());

				app.closingItem(item);

				super.windowClosing(e);

				LOGGER.exiting(CLASS_NAME, METHOD_NAME);
			}
		});

		LOGGER.exiting(CLASS_NAME, METHOD_NAME);
	}

	/**
	 * Loads all information from {@link Item item} into this frame.
	 * 
	 * @param item the {@link Item} to load
	 */
	public void setItem(Item item) {
		final String METHOD_NAME = "setItem";
		LOGGER.entering(CLASS_NAME, METHOD_NAME, item);

		setTitle(item.getuName() + TITLE_SUFFIX);

		inpTitle.setText(item.getTitle());
		inpText.setText(item.getText());
		List<String> categoriesList = app.getAllCategories();
		String[] categories = categoriesList.toArray(new String[] {});
		inpCategories.setListData(categories);
		for (String category : item.getCategories())
			inpCategories.addSelectionInterval(categoriesList.indexOf(category), categoriesList.indexOf(category));
		inpPriority.setSelectedValue(item.getPriority()
				.toString(), true);
		inpEstDateModel.setValue(item.getEtaDate());
		inpDone.setSelected(item.isDone());

		LOGGER.exiting(CLASS_NAME, METHOD_NAME);
	}
}
