package application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	
	public static application.MainController mainController;

    public static void main(String[] args){
    	launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
    	mainController = new application.MainController();
    	
    	Parent mainMenu = FXMLLoader.load(getClass().getResource("/gui/fxml/mainMenu.fxml"));
		primaryStage.setScene(new Scene(mainMenu));
		primaryStage.show();
	}
}
