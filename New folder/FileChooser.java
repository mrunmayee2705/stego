//aloows to choose a file from the system
import java.io.File;

import javax.swing.JFileChooser;

public class FileChooser {
	public static File MakeFileChooser() {
		JFileChooser fileChooser = new JFileChooser();
		
        int option = fileChooser.showOpenDialog(null); //This line displays the file chooser 
		// dialog to the user and waits for the user's input.
        if(option == JFileChooser.APPROVE_OPTION){ //open a file
        	File file = fileChooser.getSelectedFile(); //file selected retrun file
        	return file;
        }
        return null; //else null
	}
}