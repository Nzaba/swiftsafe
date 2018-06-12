package Handling;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import PasswordEncrypt.*;
import GUIs.*;
public class Welcome {
    private static int count = 0;
    
    public static void CreateUser(JFrame frame) {
	frame.setVisible(false);
	CreateNewUserGui newuser = new CreateNewUserGui(frame);
    }
    public static boolean login(Container pane, String username, char[] AttPass) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String fileName = "Program Files/"+username +".file";
        String line = null;
	
        try {
	    FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            final String s = "AAAmherst CollegeAAA";
	    char[] chars = new char[20];
	    
	    for(int i = 0; i<20; i++)
		chars[i] = s.charAt(i);
	    
            byte[] qr = Base64.decode(bufferedReader.readLine());
            line = bufferedReader.readLine();
            byte[] salt = Base64.decode(bufferedReader.readLine());
            
            if(!Arrays.equals(qr,PasswordEncryptionService.getEncryptedPassword(chars, salt))) {
            	JOptionPane.showMessageDialog(pane, "Your Account was blocked due to the number of attemps. You can create a new password by using your Security Question!", "Account Blockage!", JOptionPane.ERROR_MESSAGE);
            	JOptionPane.showMessageDialog(pane, "We value your security! Your files are safe!", "Files Safe!", JOptionPane.INFORMATION_MESSAGE);
            	bufferedReader.close();
            	return false;
            }
	    
            String pass = bufferedReader.readLine();
            byte[] EncrPass = Base64.decode(pass);
	    
            boolean in = PasswordEncryptionService.authenticate(AttPass, EncrPass, salt);
            count++;
            if(!in && count==3) {
            	String sq = bufferedReader.readLine();
            	String sa = bufferedReader.readLine();
            	line = bufferedReader.readLine();
            	String keyA = bufferedReader.readLine();
            	bufferedReader.close();
            	File file = new File("Program Files/"+username+".file");
            	file.delete();
            	FakeUserStore(username, salt, sq, sa, pass, keyA);
            	JOptionPane.showMessageDialog(pane, "Your Account was blocked due to the number of attemps. You can create a new password by using your Security Question!", "Account Blocked!", JOptionPane.ERROR_MESSAGE);
            	JOptionPane.showMessageDialog(pane, "We value your security! Your files are safe!", "Files Safe!", JOptionPane.INFORMATION_MESSAGE);
            	//do something with the master key
            	
            	return false;
            }
            
            if(!in)
            	JOptionPane.showMessageDialog(pane, "Your password is incorrect. You have "+(3-count)+" attemps remaining.", "Password Mismatch!", JOptionPane.ERROR_MESSAGE);
            else
            	JOptionPane.showMessageDialog(pane, "Login Successful!", "LOGIN", JOptionPane.INFORMATION_MESSAGE);
            return in;
        }
        catch(FileNotFoundException ex) {
	    JOptionPane.showMessageDialog(pane, "Your username is incorrect or does not exist!", "Username Not Found!", JOptionPane.INFORMATION_MESSAGE);
	    return false;
        }
        catch(IOException ex) {
	    ex.printStackTrace();
	    return false;
        }
    }
    public static void FakeUserStore(String username, byte[] salt, String SQ, String SA, String pass, String key) throws NoSuchAlgorithmException, FileNotFoundException, UnsupportedEncodingException, InvalidKeySpecException {
	File file = new File("Program Files/"+username+".file");
	PrintWriter writer = new PrintWriter(file, "UTF-8");
      	final String s = "AAWilliams CollegeAA";
	char[] chars = new char[20];
	
	for(int i = 0; i<20; i++)
	    chars[i] = s.charAt(i);
	
      	writer.println(Base64.encodeBytes(PasswordEncryptionService.getEncryptedPassword(chars, salt)));
	writer.println(username);
	writer.println(Base64.encodeBytes(salt));
	writer.println(pass);
	writer.println(SQ);
	writer.println(SA);
	writer.println(SA+"re"+SA);
	writer.println(key);
	writer.println("-----------------------|$:@:$|-----------------------");
	writer.close();
    }
    public static void in(char[] pw, String username) throws IOException {
	count = 0;
	String fileName = "Program Files/"+username +".file";
        String line = null;
	
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        for (int i = 0; i<6; i++) 
	    line = bufferedReader.readLine();
        String key = bufferedReader.readLine();
        bufferedReader.close();
        
	AES.initSecretKey(new String(pw), key);
	
	ProgramGUI in = new ProgramGUI(username);
	
    }
    public static void passReset(JFrame frame) {
	frame.setVisible(false);
	PassResetGUI reset = new PassResetGUI(frame);
    }
    public static void resetCount() {
	count=0;
    }
}
