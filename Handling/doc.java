package Handling;


import java.awt.Component;
import java.awt.Container;
import java.io.File;
import java.io.FileFilter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import GUIs.FinSearchGUI;

public class doc{
    public static File FileChooser(JFrame frame, String what) {
	UIManager.put("FileChooser.readOnly", Boolean.TRUE); 
	final JFileChooser fc = new JFileChooser();
	
	if(what.equals("csv")) {
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV File", "csv");
	    fc.setFileFilter(filter);
	    fc.setAcceptAllFileFilterUsed(false);
	}
	if(what.equals("doc")) {
	    fc.addChoosableFileFilter(new FileNameExtensionFilter("CSV File", "csv"));
	    fc.addChoosableFileFilter(new FileNameExtensionFilter("Text File", "txt"));			
	    fc.setAcceptAllFileFilterUsed(false);
	}
	File file = null;
	int returnVal = fc.showOpenDialog(frame);
	if (returnVal == JFileChooser.APPROVE_OPTION) {
	    file = fc.getSelectedFile();
	    return file;
	}
	
	return file;
    }
    
    public static File FileChooser(JFrame frame, String user, String where, String what) {
	File place = new File("Program Files/"+user+"/"+where);
	UIManager.put("FileChooser.readOnly", Boolean.TRUE); 
	final JFileChooser fileChooser = new JFileChooser(place);
	disableNav(fileChooser);
	
	if(what.equals("csv")) {
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV File", "csv");
	    fileChooser.setFileFilter(filter);
	    fileChooser.setAcceptAllFileFilterUsed(false);
	}
	
	File file = null;
	
	int returnVal = fileChooser.showOpenDialog(frame);
	if (returnVal == JFileChooser.APPROVE_OPTION) {
	    file = fileChooser.getSelectedFile();
	    return file;
	}
	
	return file;
    }
    
    private static void disableNav(Container c) {
	for (Component x : c.getComponents())
	    if (x instanceof JComboBox)
		((JComboBox)x).setEnabled(false);
	    else if (x instanceof JButton) {
		String text = ((JButton)x).getText();
		if (text == null || text.isEmpty())
		    ((JButton)x).setEnabled(false);
	    }
	    else if (x instanceof Container)
		disableNav((Container)x);
    }
    
    public static void finGUI(JFrame frame, String user) {
	File theFile = FileChooser(frame, user, "Finances", "csv");
	if(theFile != null) {
	    AES.toDecrypt(theFile, ("Program Files/"+user+"/Finances/"));
	    frame.setVisible(false);
	    
	    FinSearchGUI fin = new FinSearchGUI(user, frame, theFile.getName());
	}
    }
    public static void deleteFile(File theFile) {
	theFile.delete();
    }
    
}
