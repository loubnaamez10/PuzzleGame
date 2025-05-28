package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuScreen {
	private Stage stage;
	
	public MenuScreen (Stage stage) {
		this.stage=stage;
	}
	
	public void show() {
		VBox layout = new VBox (10);
		layout.setAlignment(Pos.CENTER);
		
		ComboBox<String> gridsize= new ComboBox<>();
		gridsize.getItems().addAll("3*3", "4*4", "5*5");
		gridsize.setValue("3*3");
		
		ComboBox<String> gamemode = new ComboBox<>();
		gamemode.getItems().addAll("Normal Mode", "Challenge Mode");
		gamemode.setValue("Normal Mode");
		
		Button startbutton = new Button("Start");
		startbutton.setOnAction(_ -> {
			int size = Integer.parseInt(gridsize.getValue().substring(0, 1));
			boolean isMode = gamemode.getValue().equals("Challenge Mode");
			GameController gamecontrolle = new GameController(stage, size, isMode);
			gamecontrolle.startGame();
			
		});
		
		layout.getChildren().addAll(gridsize, gamemode, startbutton);
		Scene scene = new Scene(layout, 400, 500);
		stage.setTitle("Welcome to the principal window");
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
}
