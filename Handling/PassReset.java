package Handling;

import PasswordEncrypt.*;

import java.awt.Container;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class PassReset {
    public static String SQ(String user) throws IOException {
	File file = new File("Program Files/"+user+".file");
        String line = null;
	
        try {
	    FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            for(int i=0; i<4;i++)
            	line = bufferedReader.readLine();
            String s = bufferedReader.readLine();
            bufferedReader.close();
            return s;
        }catch(FileNotFoundException ex) {
	    return "";
        }
        
    }
    public static boolean SAmatch(char[] sa, Container pane, String user) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
	File file = new File("Program Files/"+user+".file");
        String line = null;
        
        try {
	    FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            for(int i=0; i<2;i++)
            	line = bufferedReader.readLine();
            byte[] salt = Base64.decode(bufferedReader.readLine());
            for(int i=0; i<2;i++)
            	line = bufferedReader.readLine();
            byte[] encrSA = Base64.decode(bufferedReader.readLine());
            bufferedReader.close();
            boolean in = PasswordEncryptionService.authenticate(sa, encrSA, salt);
	    
            if(!in)
            	JOptionPane.showMessageDialog(pane, "Your security answer is incorrect.", "Security Answer Mismatch!", JOptionPane.ERROR_MESSAGE);
            
            return in;
            
        }catch(FileNotFoundException ex) {
	    return false;
        }
	
	
    }
    public static void reset(Container pane, JFrame frame, ArrayList<JTextField> fields, ArrayList<JPasswordField> fields1, String usrnm, JFrame old ) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
	if (!(fields1.get(1).getPassword().length == 0)&&!(fields1.get(2).getPassword().length == 0)&&Arrays.equals(fields1.get(1).getPassword(),fields1.get(2).getPassword())) {
	    if(CreateNewUser.isValid(fields1.get(1).getPassword())) {
		if(asOld(usrnm, fields1.get(1).getPassword(), "pass")) {
		    if(SQasOld(usrnm, fields.get(1).getText())) {
			if(asOld(usrnm, fields1.get(3).getPassword(), "sa")) {
			    if(fields.get(1).getText().length()>9 && fields.get(1).getText().length()<=30) {
				if(CreateNewUser.isValid(fields1.get(3).getPassword())) {
				    if(!Arrays.equals(fields1.get(1).getPassword(),fields1.get(3).getPassword())){
					try {
					    CreateNewUser.User(usrnm, fields1.get(1).getPassword(), fields.get(1).getText(), fields1.get(3).getPassword(), fields1.get(0).getPassword(), key(usrnm));
					    Welcome.resetCount();
					    JOptionPane.showMessageDialog(pane,"Password Successfully Changed");
					    old.setVisible(true);
					    frame.dispose();
					} catch (NoSuchAlgorithmException | FileNotFoundException | UnsupportedEncodingException
						 | InvalidKeySpecException e1) {
					    JOptionPane.showMessageDialog(pane,"Password NOT Created!");
					    // 		TODO Auto-generated catch block
					    e1.printStackTrace();
					}
				    }
				    else
					JOptionPane.showMessageDialog(pane, "Please provide different Sequrity Answer than your password!", "Redundant Passwords!", JOptionPane.ERROR_MESSAGE);	
				}
				else
				    JOptionPane.showMessageDialog(pane, "Please provide a valid security answer.", "Invalid Security Answer!", JOptionPane.ERROR_MESSAGE);	
			    }
			    else
				JOptionPane.showMessageDialog(pane, "Please provide a valid security question.", "Invalid Security Question!", JOptionPane.ERROR_MESSAGE);
			}
			else
			    JOptionPane.showMessageDialog(pane, "Your new security question must be different than your old one.", "Invalid Security Answer!", JOptionPane.ERROR_MESSAGE);
		    }
		    else
			JOptionPane.showMessageDialog(pane, "Your new security answer must be different than your old one.", "Invalid Security Answer!", JOptionPane.ERROR_MESSAGE);
		}	
		else
		    JOptionPane.showMessageDialog(pane, "Your new password must be different than your old one", "Invalid Password!", JOptionPane.ERROR_MESSAGE);
	    }
	    else
		JOptionPane.showMessageDialog(pane, "Please provide a valid password.", "Invalid Password!", JOptionPane.ERROR_MESSAGE);
	}
	else
	    JOptionPane.showMessageDialog(pane, "Your password must match.", "Password Mismatch!", JOptionPane.ERROR_MESSAGE);
    }
    
    public static boolean asOld(String usrnm, char[] pass, String type) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
	String fileName = "Program Files/"+usrnm +".file";
        String line = null;
	
        try {
	    FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            for(int i = 0; i<2; i++)
            	line = bufferedReader.readLine();
            byte[] salt = Base64.decode(bufferedReader.readLine());
            byte[] encrPass = Base64.decode(bufferedReader.readLine());
	    
            line = bufferedReader.readLine();
            byte[] sa = Base64.decode(bufferedReader.readLine());
            bufferedReader.close();
            if(type == "pass") {
            	if(Arrays.equals(encrPass, PasswordEncryptionService.getEncryptedPassword(pass, salt))) 
		    return false;
            	else
		    return true;
            }
            
            if(type == "sa") {
            	if(Arrays.equals(sa, PasswordEncryptionService.getEncryptedPassword(pass, salt)))
		    return false;
            	else
		    return true;
            }
            return false;
            
	}  catch(FileNotFoundException ex) {
	    return false;
	}
    }
    public static boolean SQasOld(String usrnm, String sq) throws IOException {
	String fileName = "Program Files/"+usrnm +".file";
        String line = null;
        try {
	    FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            for(int i = 0; i<4; i++)
            	line = bufferedReader.readLine();
            boolean truth = sq.equals(bufferedReader.readLine());
            bufferedReader.close();
            return !truth;
        }catch(FileNotFoundException ex) {
	    return false;
        }
    }
    public static String key(String usrnm) throws IOException {
	String fileName = "Program Files/"+usrnm +".file";
        String line = null;
        
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        for(int i=0; i<7;i++)
	    line = bufferedReader.readLine();
        String key = bufferedReader.readLine();
        bufferedReader.close();
        return key;
	
    } 
}

