package application;
import gui.Scenes;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Main extends Application{
	
	public static application.MainController mainController;

    public static void main(String[] args){
    	launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {		
    	mainController = new application.MainController();
    	mainController.loadData();
    	
    	Parent mainMenu = FXMLLoader.load(getClass().getResource("/gui/fxml/scenes/" + Scenes.MAIN_MENU + ".fxml"));
    	primaryStage.setMinWidth(900);
    	primaryStage.setMinHeight(700);
    	Scene scene = new Scene(mainMenu);
    	scene.getStylesheets().add(getClass().getResource("/gui/styles.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle(Scenes.MAIN_MENU.getTitle());
		primaryStage.show();
		
		setOnCloseEventListener(primaryStage);
	}
	
	private void setOnCloseEventListener(Stage primaryStage) {
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent e) {
		    	mainController.stopAllSoundThreads();
		        Platform.exit();
		        System.exit(0);
		    }
		});
	}
}

