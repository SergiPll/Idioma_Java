package controlador;

import modelo.archivos;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PongController {

	@FXML private GridPane paneToolBar;
	@FXML private AnchorPane paneMenuNavigationDrawer;
	@FXML private AnchorPane paneMenuToolbar;
	@FXML private AnchorPane pantalla;
	
	@FXML private Button playBttn;
	@FXML private Button pauseBttn;
	@FXML private Button resetBttn;
	@FXML private Button bttnCargar;
	private AnimationTimer animationBall;
	private AnimationTimer animationPlayers;
	@FXML private Circle bola;
	@FXML private Rectangle player1, player2;
	@FXML private Label marcador;
	@FXML private Label labelTiempo;
	@FXML private Text playerwin;
	private int posX = 0;
	private int posY = 0;
	private int posY_pl1 = 0;
	private int posY_pl2 = 0;
	private int velociX = 9;
	private int velociY = velociX/2;
	private int velociYpaleta = 0;
	private int velociYpaleta2 = 0;
	private int puntosp1 = 0;
	private int puntosp2 = 0;
	private int maxPoints = 5;
	private int victoriaTiempo;
	private int seg = 0;
	private boolean partidaTiempo;
	private boolean partidaPuntos;
	private Timer tiempo;
	
	//Sacar numero random entre 0 y 50 por si se necesita crear un numero random.
    double random50 = Math.floor(Math.random() * 51);
	
//Extras
	private boolean pacman = false;
	//	@FXML private Pane paneEffectDisable;

	@FXML
	void initialize() {
		resetBttn.setDisable(true);
		pauseBttn.setDisable(true);
		//Cargar los 2 menus
		try {
			
// Para cargar las opciones previamente guardadas de la configuracion			
			try {
				cargar("Configuracion.txt");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			VBox menuNavigationDrawer = FXMLLoader.load(getClass().getResource("/vista/MenuNavigationDrawer.fxml"));
			paneMenuNavigationDrawer.getChildren().add(menuNavigationDrawer);

			VBox menuToolbar = FXMLLoader.load(getClass().getResource("/vista/MenuToolbar.fxml"));
			paneMenuToolbar.getChildren().add(menuToolbar);

		} catch (IOException ex) {
			Logger.getLogger(PongController.class.getName()).log(Level.SEVERE, null, ex);
		}
		//Al iniciar la aplicacion, no se muestran los paneles
		paneMenuNavigationDrawer.setVisible(false);
		paneMenuToolbar.setVisible(false);
		//		paneEffectDisable.setVisible(false);   
	
		
		player1.setFill(Color.WHITE);
		player2.setFill(Color.WHITE);
		
// Movimiento jugadores	
		animationPlayers = new AnimationTimer() {
			 @Override
			 public void handle(long now) {
				 posY_pl1 += velociYpaleta;
				 posY_pl2 += velociYpaleta2;

// Bordes y actualizar jugador 1
					 player1.setY(posY_pl1);
					 
				 if (player1.getY() <= -249) {
					 posY_pl1 = -248;
					 player1.setY(posY_pl1);
				 }
				 if(player1.getY() >= 235) {
					 posY_pl1 = 234;
					 player1.setY(posY_pl1);
				 }
				 
				 
// Bordes y actualizar jugador 2			 
					 player2.setY(posY_pl2);
					 
				 if (player2.getY() <= -249) {
					 posY_pl2 = -248;
					 player2.setY(posY_pl2);
				 }
				 if(player2.getY() >= 235) {
					 posY_pl2 = 234;
					 player2.setY(posY_pl2);
				 }

// Si se presionan las teclas
				 Scene scene= player1.getScene();
				 scene.setOnKeyPressed(e ->{
					 
					 if(e.getCode() == KeyCode.W){ velociYpaleta = -15; }
					 if(e.getCode() == KeyCode.S){ velociYpaleta = +15; }
					 if(e.getCode() == KeyCode.I){ velociYpaleta2 += -15;}
					 if(e.getCode() == KeyCode.K){ velociYpaleta2 = +15; }
				 });
				 scene.setOnKeyReleased(e ->{
					 if ( (e.getCode() == KeyCode.W) || (e.getCode() == KeyCode.S) ) { velociYpaleta = 0; }
					 if ( (e.getCode() == KeyCode.I) || (e.getCode() == KeyCode.K) ) { velociYpaleta2 = 0; }
				 });

			 }};
		
		animationBall = new AnimationTimer() {
			
			@Override
			public void handle(long arg0) {
				
// Base del movimiento
				posX += velociX;
				posY += velociY;
				
				bola.setCenterX(posX);
				bola.setCenterY(posY);
			
// Colisiones con jugadores
				Shape shapeCollision1 = Shape.intersect(player1, bola);
				boolean colisionVacia1 = shapeCollision1.getBoundsInLocal().isEmpty();
				
				Shape shapeCollision2 = Shape.intersect(player2, bola);
				boolean colisionVacia2 = shapeCollision2.getBoundsInLocal().isEmpty();							
				
				if (!colisionVacia1 && velociX < 0) {
					velociX = velociX * (-1);
				}
				
				if (!colisionVacia2 && velociX > 0) {
					velociX = velociX * (-1);
				}
				
				
// Borde arriba y abajo
				if (bola.getCenterY() >= 285 || bola.getCenterY() <= -270) {
					velociY = velociY * (-1);
				}
// Bordes laterales
				
				if (bola.getCenterX() >= 545 || bola.getCenterX() <= -545) {
					velociX = velociX * (-1);
				}
// Puntos
				if (bola.getCenterX() >= 545) {
					puntosp1++;
					marcador.setText(puntosp1 + " : " + puntosp2);
				} else if (bola.getCenterX() <= -545) {
					puntosp2++;
					marcador.setText(puntosp1 + " : " + puntosp2);
				}
// Sistema de Victoria
				if (partidaPuntos && puntosp1 == maxPoints) {
					reset(null);
					win1(); 
				} 
				if (partidaPuntos && puntosp2 == maxPoints) { 
					reset(null);
					win2();
				}

				if (partidaTiempo && seg == victoriaTiempo) {
					reset(null);
					if (puntosp1 > puntosp2) {
						win1();
					} else if (puntosp2 > puntosp1) {
						win2();
					} else {
						win3();
					} 
					 
				}
			}};

	}	    
    
	//**************************************************************************
	// Acciones para los menus
	//**************************************************************************
	@FXML
	private void onMouseExitedPaneNavigationDrawer(MouseEvent event) {
		paneMenuNavigationDrawer.setVisible(false);
		//		paneEffectDisable.setVisible(false);
	}

	@FXML
	private void onMouseExitedPaneToolbarMenu(MouseEvent event) {
		paneMenuToolbar.setVisible(false);
		//		paneEffectDisable.setVisible(false);
	}

	@FXML
	private void onMouseClickedMenuNavigationDrawer(MouseEvent event) {
		paneMenuNavigationDrawer.setVisible(!paneMenuNavigationDrawer.isVisible());
		paneMenuToolbar.setVisible(false);
		//		paneEffectDisable.setVisible(true);
	}

	@FXML
	private void onMouseClickedMenuToolbar(MouseEvent event) {
		paneMenuToolbar.setVisible(!paneMenuToolbar.isVisible());
		paneMenuNavigationDrawer.setVisible(false);
		//		paneEffectDisable.setVisible(true);
	}
	
	//**************************************************************************
	// Otras funciones
	//**************************************************************************
	
	@FXML
	private void play(MouseEvent event) {
		try {
			animationBall.start();
			animationPlayers.start();
			if (velociX > 0) {
				cargar();
			} else {
				cargar();
				velociX = -velociX;
			}
			tiempo = Timer();
			playBttn.setDisable(true);
			resetBttn.setDisable(false);
			pauseBttn.setDisable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	private void pause(ActionEvent event) {
		try {
			animationBall.stop();
			animationPlayers.stop();
			player1.setFill(Color.WHITE);
			player2.setFill(Color.WHITE);
			playBttn.setDisable(false);
			resetBttn.setDisable(false);
			pauseBttn.setDisable(true);
			tiempo.cancel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	private void reset(MouseEvent event) {
		try {
			posX = 0;
			posY = 0;
			posY_pl1 = 0;
			posY_pl2 = 0;
			bola.setCenterX(posX);
			bola.setCenterY(posY);
			player1.setY(posY_pl1);
			player2.setY(posY_pl2);
			player1.setFill(Color.WHITE);
			player2.setFill(Color.WHITE);
			puntosp1 = 0;
			puntosp2 = 0;
			marcador.setText(puntosp1 + " : " + puntosp2);
			animationBall.stop();
			animationPlayers.stop();
			cerrarTiempo(tiempo);
			seg = 0;
			playBttn.setDisable(false);
			resetBttn.setDisable(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void win1() {
		
		try {
			animationBall.stop();
			animationPlayers.stop();
			cerrarTiempo(tiempo);
			playBttn.setDisable(false);
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/Victoria.fxml"));
			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();

			stage.setTitle("Ganador");
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);

			stage.setScene(new Scene(root));

			//Mostrar el Stage (ventana)
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void win2() {
		
		try {
			animationBall.stop();
			animationPlayers.stop();
			cerrarTiempo(tiempo);
			playBttn.setDisable(false);
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/Victoria2.fxml"));
			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();

			stage.setTitle("Ganador");
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);

			stage.setScene(new Scene(root));

			//Mostrar el Stage (ventana)
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void win3() {
		
		try {
			animationBall.stop();
			animationPlayers.stop();
			cerrarTiempo(tiempo);
			playBttn.setDisable(false);
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/Victoria3_Empate.fxml"));
			Parent root = (Parent) fxmlLoader.load();

			Stage stage = new Stage();

			stage.setTitle("Empate");
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);

			stage.setScene(new Scene(root));

			//Mostrar el Stage (ventana)
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void getInfo() throws IOException {

		pause(null);
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vista/Informacion.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			
			Stage stage = new Stage();
			
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UNDECORATED);
			
			stage.setResizable(false);
			
			stage.initModality(Modality.APPLICATION_MODAL);
			
			stage.setScene(new Scene(root));
			//Mostrar el Stage (ventana)
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	void cargar(String archivo) throws IOException {
		BufferedReader buffRead = archivos.openReader(archivo);
		String linea = buffRead.readLine();
		
//Valores de Velocidad y colores
		velociX = (Integer.parseInt(linea));
		linea = buffRead.readLine();
		player1.setFill(Color.valueOf(linea));
		linea = buffRead.readLine();
		player2.setFill(Color.valueOf(linea));
		linea = buffRead.readLine();
		
//Valores del Checkbox y Spinner del tiempo		
		if (linea.contains("true")) {
			partidaTiempo = true;
		}else partidaTiempo = false;
		linea = buffRead.readLine();
		victoriaTiempo = Integer.parseInt(linea)*60;
		linea = buffRead.readLine();
		
//Valores del Checkbox y Spinner de los puntos			
		if (linea.contains("true")) {
			partidaPuntos = true;
			linea = buffRead.readLine();
			maxPoints = Integer.parseInt(linea);
		}else partidaPuntos = false;
		linea = buffRead.readLine();
		
//Valores del las checkbox Extra

		
		archivos.closeReader(buffRead);
	}

	@FXML
	void cargar() {
		try {
			cargar("Configuracion.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	Timer Timer() {
		Timer timer= new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						seg++;
						labelTiempo.setText(String.format("%02d:%02d", (seg % 3600) / 60, seg % 60));
						}
					});
				}
			
			}, 0, 1000);
		return timer;
	}
	
	void cerrarTiempo(Timer tiempo) {
		labelTiempo.setText("");
		tiempo.cancel();
	}
	
	
}