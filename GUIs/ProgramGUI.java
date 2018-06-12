package GUIs;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Handling.AES;
import Handling.Program;


public class ProgramGUI {
    ArrayList<JButton> bttn;
    JFrame frame;
    String user;
    public ProgramGUI(String u){
	bttn = new ArrayList<JButton>();
	user = u;
	createAndShowGUI();
    }
    public void createAndShowGUI() {
        //Create and set up the window.
        frame = new JFrame("SSS® ("+user+")");
        frame.setPreferredSize(new Dimension(350, 800));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener( new WindowAdapter()
	    {
		public void windowClosing(WindowEvent e) { 
		    AES.loseSecretKey();
		    WelcomeGui welcome = new WelcomeGui();
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
	gui.addLabel("Welcome to Swift Safe Store SSS®", pane);
	gui.addLabel("Please choose your action:", pane);
	
	gui.addemptyrows(2, pane);
	bttn.add(gui.bttn("Finances"));
        pane.add(bttn.get(0));
        
        gui.addemptyrows(1, pane);
        bttn.add(gui.bttn("Document Safety"));
        pane.add(bttn.get(1));
	
        
        gui.addemptyrows(1, pane);
        bttn.add(gui.bttn("Help"));
        pane.add(bttn.get(2));
        
        bttn.get(0).addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    Program.fin(user, frame);
		}
	    });
        
        bttn.get(1).addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    Program.doc(user, frame);
		}
	    });
        bttn.get(2).addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    Program.text(frame);
		    
		}
            });
        gui.addemptyrows(1, pane);
        JLabel Image = new JLabel(new ImageIcon("Images/first.png"));
        Image.setAlignmentX(Component.CENTER_ALIGNMENT);
        pane.add(Image);
        gui.addLabel("©Pixabay Public Domain",pane);
        
        gui.addemptyrows(1, pane);
        bttn.add(gui.bttn("Log Out"));
        pane.add(bttn.get(3));
        
        bttn.get(3).addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    frame.dispose();
		    AES.loseSecretKey();
		    WelcomeGui welcome = new WelcomeGui();
		}
	    });
	
        gui.addemptyrows(1, pane);
        gui.addLabel("Thank you for your trust.", pane);
        gui.addLabel("©Ilija & Ray SSS®", pane);
	
        
    }
    
    public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub
	
    }
    
}

