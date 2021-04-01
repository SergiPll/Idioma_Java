package controlador;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ToolBarController {

	@FXML
	private void abrirConfiguracion() {

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/Configuracion.fxml"));
			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();

			stage.setTitle("Configuracion");
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);

			stage.setScene(new Scene(root));

			//Mostrar el Stage (ventana)
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}