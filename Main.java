import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import GUIs.WelcomeGui;



public class Main {
    //The program has one main method
    public static void main(String[] arngs) {
	if (!Files.isDirectory(Paths.get("Program Files"))) 
	    new File("Program Files/").mkdir();
	WelcomeGui welcome = new WelcomeGui();
	
    }
}
