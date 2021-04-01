package application;
	
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application  {
	@Override
	public void start(Stage primaryStage) {
		try {

			ResourceBundle res = ResourceBundle.getBundle("strings");
			
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/vista/Principal.fxml"), res);
			Scene scene = new Scene(root);
			
			primaryStage.setTitle("Formulario");
			primaryStage.getIcons().add(new Image("/vista/img/imagen.png"));
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//ResourceBundle res = ResourceBundle.getBundle("application.strings");
		//System.out.println(res.getObject("form.titulo"));
		
		launch(args);
	}
}
