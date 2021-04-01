package controlador;

import java.io.File;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class OpenFile {

	JFileChooser fileChooser = new JFileChooser();
	StringBuilder sb = new StringBuilder();

	public String PickMe() throws Exception {
		if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			
			Scanner input = new Scanner(file);
			
			while(input.hasNext()) {
				sb.append(input.nextLine());
				sb.append("\n");
			}
			input.close();
			String ruta = sb.toString();
			return ruta;
		}
		else {
			String ruta = "";
			return ruta;
		}
	}
	
	
}
