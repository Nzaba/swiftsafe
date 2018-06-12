package GUIs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import Handling.binarysearch;
import Handling.doc;

public class FinSearchGUI {
	ArrayList<JButton> bttn;
	ArrayList<JTextField> fields;
	JFrame frame;
	JFrame old;
	String user;
	File theFile;
	JScrollPane sp;
	
	public FinSearchGUI(String u,JFrame a, String f){
		bttn = new ArrayList<JButton>();
		user = u;
		fields = new ArrayList<JTextField>();
		old = a;
		String filename = "decrypted!_" +f;
		theFile = new File("Program Files/"+user+"/Finances/"+filename);
		sp = null;
		createAndShowGUI();
	}
	public void createAndShowGUI() {
        //Create and set up the window.
		frame = new JFrame("Finance Search for "+user);
        frame.setPreferredSize(new Dimension(600, 800));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener( new WindowAdapter()
        {
          public void windowClosing(WindowEvent e) { 
        	  doc.deleteFile(theFile);
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
		gui.addLabel("Welcome to the Welcome to the Vault Search!", pane);
		gui.addLabel("This is the Safe Finances Search Wizard", pane);
		gui.addemptyrows(5, pane);
		gui.addLabel("You can only search by name:", pane);
		String[] columnNames = {"Full name", "CSC Number","Expiration Date", "Type", "Bank"};

		fields.add(gui.addTextField("Name", pane));
		bttn.add(gui.bttn("Search"));
        bttn.get(0).addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	String name = fields.get(0).getText();
            	if(name != "") { 
            		if(sp != null) {
                		frame.getContentPane().remove(sp);
                		frame.pack();
                       	frame.setVisible(true);
                	}
            		if(binarysearch.checkname(theFile, name)) {
            		String[][] toShow;
            		toShow = binarysearch.getcarddetails(name, theFile);
                   	sp = gui.table(toShow, columnNames);
                   	frame.add(sp);
                   	frame.pack();
                   	frame.setVisible(true);
            		}
            		else
            			JOptionPane.showMessageDialog(pane, "Name not found. Name MUST be exact", "Name not found!", JOptionPane.ERROR_MESSAGE);
            	}
            }
        });
        pane.add(bttn.get(0));
        
        gui.addemptyrows(3, pane);
       
        
        gui.addemptyrows(1, pane);
        bttn.add(gui.bttn("Back"));
        bttn.get(1).addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	doc.deleteFile(theFile);
            	frame.dispose();
            	old.setVisible(true);
            }
        });
        pane.add(bttn.get(1));
        gui.addemptyrows(2, pane);
        gui.addLabel("Thank you for your trust.", pane);
        gui.addLabel("©Ilija & Ray SSS®", pane);
	}

}
