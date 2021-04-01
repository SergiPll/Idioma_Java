package controlador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import modelo.archivos;

public class ConfigController {

	@FXML private Slider speed;
	@FXML private ColorPicker color1;
	@FXML private ColorPicker color2;
	@FXML private Spinner<Integer> spinTiempo;
	@FXML private Spinner<Integer> spinPuntos;
	@FXML private CheckBox checkTiempo;
	@FXML private CheckBox checkPuntos;
	@FXML private CheckBox extra1;
	@FXML private CheckBox extra2;
	@FXML private Button bttnCancelar;
	@FXML private Button bttnGuardar;
	
	@FXML
	void initialize() throws IOException {
		Cargar("Configuracion.txt");
	}
	
	void Cargar(String archivo) throws IOException {
		BufferedReader buffRead = archivos.openReader(archivo);
		String linea = buffRead.readLine();
		
		int Tiempo;
		int Puntos;
		
//Valores de Velocidad y colores		
		speed.setValue(Integer.parseInt(linea));
		linea = buffRead.readLine();
		color1.setValue(Color.valueOf(linea));
		linea = buffRead.readLine();
		color2.setValue(Color.valueOf(linea));
		linea = buffRead.readLine();

//Valores del Checkbox y Spinner del tiempo
		if (linea.contains("true")) {
			checkTiempo.setSelected(true);
		}else checkTiempo.setSelected(false);
		linea = buffRead.readLine();
		Tiempo = Integer.parseInt(linea);
		SpinnerValueFactory<Integer> valueTiempo = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, Tiempo);
		spinTiempo.setValueFactory(valueTiempo);
		linea = buffRead.readLine();

//Valores del Checkbox y Spinner de los puntos	
		if (linea.contains("true")) {
			checkPuntos.setSelected(true);
		}else checkPuntos.setSelected(false);
		linea = buffRead.readLine();
		Puntos = Integer.parseInt(linea);
		SpinnerValueFactory<Integer> valuePuntos = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, Puntos);
		spinPuntos.setValueFactory(valuePuntos);
		linea = buffRead.readLine();

//Valores del las checkbox Extra	
		if (linea.contains("true")) {
			extra1.setSelected(true);
		}else extra1.setSelected(false);
		linea = buffRead.readLine();
		if (linea.contains("true")) {
			extra2.setSelected(true);
		}else extra2.setSelected(false);
		
		archivos.closeReader(buffRead);
	}

	@FXML
	void cancelar(ActionEvent event) throws IOException {
		Stage stage = (Stage) bttnCancelar.getScene().getWindow();
		stage.close();
	}


	@FXML
	void guardar(ActionEvent event) throws IOException {
		BufferedWriter buffWrit = archivos.openWriter("Configuracion.txt");
		
//Valores de Velocidad y colores
		buffWrit.write("" + (int) speed.getValue());
		buffWrit.newLine();
		buffWrit.write(color1.getValue().toString());
		buffWrit.newLine();
		buffWrit.write(color2.getValue().toString());
		buffWrit.newLine();
		
//Valores del Checkbox y Spinner del tiempo
		if (checkTiempo.isSelected()){
			buffWrit.write("true");
		}else buffWrit.write("false");
		buffWrit.newLine();
		buffWrit.write(spinTiempo.getValue().toString());
		buffWrit.newLine();
		
//Valores del Checkbox y Spinner de los puntos
		if (checkPuntos.isSelected()){
			buffWrit.write("true");
		}else buffWrit.write("false");
		buffWrit.newLine();
		buffWrit.write(spinPuntos.getValue().toString());
		buffWrit.newLine();
		
//Valores del las checkbox Extra
		if (extra1.isSelected()) {
			buffWrit.write("true");
		} else buffWrit.write("false");
		buffWrit.newLine();
		if (extra2.isSelected()) {
			buffWrit.write("true");
		} else buffWrit.write("false");
		archivos.closeWriter(buffWrit);
		Stage stage = (Stage) bttnGuardar.getScene().getWindow();
		stage.close();
	}

}