package GUIs;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;


import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


import Handling.PassReset;
import Handling.Welcome;


public class PassResetGUI {
    ArrayList<JTextField> fields;
    ArrayList<JPasswordField> fields1;
    ArrayList<JButton> bttn;
    JFrame frame;
    JFrame old;
    
    public PassResetGUI(JFrame a){
	fields = new ArrayList<JTextField>();
	fields1 = new ArrayList<JPasswordField>();
	bttn = new ArrayList<JButton>();
	old = a;
	createAndShowGUI();
    }
    public void createAndShowGUI() {
        //Create and set up the window.
	frame = new JFrame("Password Reset Wizard");
        frame.setPreferredSize(new Dimension(400, 800));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener( new WindowAdapter()
	    {
		public void windowClosing(WindowEvent e) { 
		    old.setVisible(true);
		    Welcome.resetCount();
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
	gui.addLabel("©Ilija & Ray SSS®", pane);
	JButton back = new JButton("Back");
	back.setAlignmentX(Component.CENTER_ALIGNMENT);
	back.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    Welcome.resetCount();
		    frame.dispose();
		    old.setVisible(true);
		}
	    });
	pane.add(back);
	gui.addemptyrows(1, pane);
	gui.addLabel("Welcome to Password Reset Wizard.", pane);
	gui.addLabel("First, please please provide your username.", pane);
	
	fields.add(gui.addTextField("Username", pane));
	bttn.add(gui.bttn("Start"));
        
        bttn.get(0).addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    String urnm = fields.get(0).getText(); 
		    Path path = Paths.get("Program Files/"+urnm+".file");
		    if (!urnm.equals("")&&Files.exists(path)) {
			try {
			    answerComponents(pane, urnm);
			} catch (IOException e1) {
			    // TODO Auto-generated catch block
			    e1.printStackTrace();
			}
		    }
		    else 
			JOptionPane.showMessageDialog(pane, "Please provide a valid username.", "Invalid Username!", JOptionPane.ERROR_MESSAGE);
		}
            });
        pane.add(bttn.get(0));
	
        
    }
    
    public void answerComponents(Container pane, String ursnm) throws IOException {
	gui.addemptyrows(1, pane);
	gui.addLabel("Please answer your security question:", pane);
	gui.addemptyrows(1, pane);
	String SQ = PassReset.SQ(ursnm);
	gui.addLabel(SQ,pane);
	fields1.add(gui.addPassField("Answer:", pane));
	
	bttn.add(gui.bttn("Answer"));
	
	bttn.get(1).addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){ 	
		    try {
			if (PassReset.SAmatch(fields1.get(0).getPassword(), pane, ursnm)) {
			    resetComponents(pane, ursnm, SQ);
			}
		    } catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		    }
		}
            });
        pane.add(bttn.get(1));
        
	frame.pack();
        frame.setVisible(true);
    }
    
    public void resetComponents(Container pane, String usrnm, String SQ) {
	gui.addemptyrows(1, pane);
	gui.addLabel("Provide your new password and security details.", pane);
	gui.addLabel("You may want to refer to the instructions bellow.", pane);
	fields1.add(gui.addPassField("New Password", pane));
        fields1.add(gui.addPassField("Retype New Password", pane));
        
        fields.add(gui.addTextField("New Security Question", pane));
        fields1.add(gui.addPassField("New Answer to Security Question", pane));
        
        bttn.add(gui.bttn("Answer"));
        bttn.get(2).addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){ 	
		    try {
			PassReset.reset(pane, frame, fields, fields1, usrnm, old);
		    } catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		    }
		}
            });
        pane.add(bttn.get(2));
        
        gui.addemptyrows(1, pane);
	
        gui.addLabel("Remainder: Your password and sequrity answer must be", pane);
        gui.addLabel("between 8 and 25 characters, and contain:", pane);
        gui.addLabel(" - one capital letter", pane);
        gui.addLabel(" - one lowercase letter", pane);
        gui.addLabel(" - one special character", pane);
        gui.addLabel(" - one number.", pane);
        gui.addLabel("Your sequrity question must be between 10 and 30 characters.", pane);
        gui.addemptyrows(1, pane);
        gui.addLabel("Thank you!", pane);
        
        frame.pack();
        frame.setVisible(true);
	
    }
    
}

