package GUIs;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class gui {
    public static void addLabel(String text, Container container) {
	JLabel label = new JLabel(text);
	label.setAlignmentX(Component.CENTER_ALIGNMENT);
	container.add(label);
    }
    public static void addemptyrows(int j, Container container) {
	for(int i = 0; i<=j; i++) {
	    addLabel(" ", container);
	}
    }
    public static JTextField addTextField(String text, Container container) {
	JTextField text1 = new JTextField();
	text1.setAlignmentX(Component.CENTER_ALIGNMENT);
	text1.setMaximumSize(new Dimension(200, text1.getMinimumSize().height));
	text1.setColumns(25);
	JLabel label = new JLabel(text);
	label.setAlignmentX(Component.CENTER_ALIGNMENT);
	label.setLabelFor(text1);
	container.add(label);
	container.add(text1);
	return text1;
    }
    public static JPasswordField addPassField(String text, Container container) {
	JPasswordField text1 = new JPasswordField();
	text1.setAlignmentX(Component.CENTER_ALIGNMENT);
	text1.setMaximumSize(new Dimension(200, text1.getMinimumSize().height));
	text1.setColumns(25);
	JLabel label = new JLabel(text);
	label.setAlignmentX(Component.CENTER_ALIGNMENT);
	label.setLabelFor(text1);
	container.add(label);
	container.add(text1);
	return text1;
    }
    public static JButton bttn(String text) {
	JButton button = new JButton(text);
	button.setAlignmentX(Component.CENTER_ALIGNMENT);
	button.setMaximumSize(new Dimension(button.getMinimumSize().width, button.getMinimumSize().height));
	return button;
    }
    public static JScrollPane table(String[][] data, String[] cols) {
	JTable table = new JTable (data, cols);
	JScrollPane scrollPane = new JScrollPane(table);
	table.setFillsViewportHeight(true);
	return scrollPane;
    }
}
