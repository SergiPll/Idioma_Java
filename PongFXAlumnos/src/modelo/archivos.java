package modelo;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;	

public class archivos {
	 
	public static BufferedReader openReader (String f1) throws FileNotFoundException {
		FileReader fileReader = new FileReader(f1);
		BufferedReader buff = new BufferedReader(fileReader);
		return buff;
	}

	public static void closeReader (BufferedReader buff) throws IOException {
		buff.close();
	}
	
	public static BufferedWriter openWriter (String fichero1) throws IOException {
		BufferedWriter buff = new BufferedWriter(new FileWriter("Configuracion.txt"));
		return buff;
	}

	public static void closeWriter (BufferedWriter buff) throws IOException {
		buff.close();
	}


}
