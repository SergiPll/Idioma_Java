package controlador;

import utilidades.I18N;
import java.util.Locale;

import javax.swing.JFileChooser;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class PrincipalController {
	
	JFileChooser fileChooser = new JFileChooser();
	StringBuilder sb = new StringBuilder();
	
	@FXML private Label lbl_formulario, lbl_nombre, lbl_apellidos, lbl_usuario, lbl_contrasenya, lbl_fecha;
	@FXML private RadioButton btn_programacio, btn_base_de_datos;
	@FXML private Button btn_registrar, btn_imagen;
	@FXML private ImageView img_castellano, img_valenciano, img_ingles, imagenCambiada;
	
	@FXML
	void initialize() {
		
		lbl_formulario.textProperty().bind(I18N.createStringBinding("form.titulo"));
		lbl_nombre.textProperty().bind(I18N.createStringBinding("form.nombre"));
		lbl_apellidos.textProperty().bind(I18N.createStringBinding("form.apellidos"));
		lbl_usuario.textProperty().bind(I18N.createStringBinding("form.usuario"));
		lbl_contrasenya.textProperty().bind(I18N.createStringBinding("form.password"));
		lbl_fecha.textProperty().bind(I18N.createStringBinding("form.fecha"));
		btn_programacio.textProperty().bind(I18N.createStringBinding("form.programacion"));
		btn_base_de_datos.textProperty().bind(I18N.createStringBinding("form.baseDeDatos"));
		btn_registrar.textProperty().bind(I18N.createStringBinding("form.registrar"));
		btn_imagen.textProperty().bind(I18N.createStringBinding("form.selectImagen"));
		
		
		
	}
	
	
	@FXML
	void setCastellano(MouseEvent event) {
		I18N.setLocale(new Locale("es"));
	}
	@FXML
	void setValenciano(MouseEvent event) {
		I18N.setLocale(new Locale("ca"));
	}
	@FXML
	void setIngles(MouseEvent event) {
		I18N.setLocale(new Locale("en"));
	}
	
	@FXML
	void prog(MouseEvent event) {
		if (btn_programacio.isSelected())
		{
			btn_programacio.setSelected(true);
			btn_base_de_datos.setSelected(false);
		}
		
	}
	
	@FXML
	void baseDatos(MouseEvent event) {
		if (btn_base_de_datos.isSelected())
		{
			btn_base_de_datos.setSelected(true);
			btn_programacio.setSelected(false);
		}
	}
	
	@FXML
	void selectImg(MouseEvent event) throws Exception{
		OpenFile of = new OpenFile();
		
		try {
			String ruta = of.PickMe();
			Image image = new Image(ruta);
			imagenCambiada.setImage(image);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}

