package GUIs;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import Handling.AES;
import Handling.doc;

public class FinancesGUI {
	ArrayList<JButton> bttn;
	ArrayList<JTextField> fields;
	JFrame frame;
	JFrame old;
	String user;
	
	public FinancesGUI(String u, JFrame a){
		bttn = new ArrayList<JButton>();
		user = u;
		fields = new ArrayList<JTextField>();
		old = a;
		createAndShowGUI();
	}
	
	public void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("Finances for "+user);
        frame.setPreferredSize(new Dimension(350, 800));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener( new WindowAdapter()
        {
          public void windowClosing(WindowEvent e) { 
        	  old.setVisible(true);
          }
        });
        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
        
        
    }
	
	public void addComponentsToPane(Container pane) {
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		gui.addLabel("Welcome to the Vault!", pane);
		gui.addLabel("This is the Vault Secure Wizard.", pane);
		gui.addemptyrows(5, pane);
		
		gui.addLabel("Click here to choose an imported .csv file to search in:", pane);
		
		bttn.add(gui.bttn("Search Existing Files"));
        bttn.get(0).addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	doc.finGUI(frame, user);
            }
        });
        pane.add(bttn.get(0));
        
    	
		
        gui.addemptyrows(3, pane);
        gui.addLabel("To add new cards, please import an .csv", pane);
        gui.addLabel("file in the specified format:", pane);
        gui.addLabel(".csv file must contain all 6 columns and", pane);
        gui.addLabel("it needs to be sorted by the name column!", pane);
        bttn.add(gui.bttn("Import .csv file"));
        bttn.get(1).addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	File file = doc.FileChooser(frame, "csv");
            	if(file != null) {
            		AES.toEncrypt(file, "Program Files/"+user+"/Finances/");
            		JOptionPane.showMessageDialog(frame, "File Successfully imported and encrypted", "Success", JOptionPane.INFORMATION_MESSAGE);
            	}
            		
            }
        });
        pane.add(bttn.get(1));
        
        
        gui.addemptyrows(1, pane);
        
        JLabel Image = new JLabel(new ImageIcon("Images/finance.png"));
        Image.setAlignmentX(Component.CENTER_ALIGNMENT);
        pane.add(Image);
        gui.addLabel("©pvhc.net free icons",pane);
        gui.addemptyrows(1, pane);
        bttn.add(gui.bttn("Back"));
        bttn.get(2).addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	frame.dispose();
            	old.setVisible(true);
            }
        });
        pane.add(bttn.get(2));
        gui.addemptyrows(3, pane);
        gui.addLabel("Thank you for your trust.", pane);
        gui.addLabel("©Ilija & Ray SSS®", pane);

        
    }
	
}
