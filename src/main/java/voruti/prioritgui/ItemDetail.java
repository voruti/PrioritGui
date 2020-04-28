package voruti.prioritgui;

import java.awt.Color;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import voruti.priorit.Item;

public class ItemDetail extends JFrame {

	private static final long serialVersionUID = -2266535806311908702L;

	public ItemDetail(App app) {
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
						FormSpecs.DEFAULT_COLSPEC, },
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

		JTextField inpTitle = new JTextField();
		panel.add(inpTitle, "4, 2");

		JLabel lblText = new JLabel("Text:");
		lblText.setFont(new Font("Arial", Font.PLAIN, 30));
		lblText.setForeground(Color.WHITE);
		panel.add(lblText, "2, 4");

		JTextArea inpText = new JTextArea();
		panel.add(inpText, "4, 4");

		JLabel lblCategories = new JLabel("Categories:");
		lblCategories.setFont(new Font("Arial", Font.PLAIN, 30));
		lblCategories.setForeground(Color.WHITE);
		panel.add(lblCategories, "2, 6");

		JList<String> inpCategories = new JList<>();
		panel.add(inpCategories, "4, 6");

		JLabel lblPriority = new JLabel("Priority:");
		lblPriority.setFont(new Font("Arial", Font.PLAIN, 30));
		lblPriority.setForeground(Color.WHITE);
		panel.add(lblPriority, "2, 8");

		JList<String> inpPriority = new JList<>();
		panel.add(inpPriority, "4, 8");

		JLabel lblEstDate = new JLabel("est. Date:");
		lblEstDate.setFont(new Font("Arial", Font.PLAIN, 30));
		lblEstDate.setForeground(Color.WHITE);
		panel.add(lblEstDate, "2, 10");

		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
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
		JDatePickerImpl inpEstDate = new JDatePickerImpl(datePanel, abstractFormatter);
		panel.add(inpEstDate, "4, 10");

		JLabel lblDone = new JLabel("Done:");
		lblDone.setFont(new Font("Arial", Font.PLAIN, 30));
		lblDone.setForeground(Color.WHITE);
		panel.add(lblDone, "2, 12");

		JCheckBox inpDone = new JCheckBox();
		panel.add(inpDone, "4, 12");
	}

	public void setItem(Item item) {
		setTitle(item.getuName() + " - PrioritGui");

	}

}
