package gui.controllers.sceneControllers;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import application.Main;
import data.Lesson;
import gui.Scenes;
import gui.controllers.ControllerBase;
import gui.controllers.dialogControllers.LearningModeConfigDialogController;
import gui.controllers.dialogControllers.StationaryBicycleModeConfigDialogController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modes.Dictate;
import modes.Examination;
import modes.GameMode;
import modes.Learning;
import modes.StationaryBicycle;
import modes.UnsatisfactoryLessonException;

public class StartModeController extends ControllerBase {
	
	@FXML
	BorderPane pane;
	
	@FXML 
	Button backBtn, learningModeBtn, ExaminationModeBtn, DictateModeBtn, StationaryBicycleModeBtn;
	
	Lesson lesson;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setFontSizeToTexts();
		backBtn.setOnMouseClicked(e -> redirect(Scenes.START_LESSON, e));
		learningModeBtn.setOnMouseClicked(e -> catchExceptionAndStartMode(Learning.class, e));
		ExaminationModeBtn.setOnMouseClicked(e -> catchExceptionAndStartMode(Exception.class, e));
		DictateModeBtn.setOnMouseClicked(e -> catchExceptionAndStartMode(Dictate.class, e));
		StationaryBicycleModeBtn.setOnMouseClicked(e -> catchExceptionAndStartMode(StationaryBicycle.class, e));
	}

	@Override
	protected void setFontSizeToTexts() {
		int fontSize = Main.mainController.getFontSize();
		List<Node> nodes = Arrays.asList(backBtn, learningModeBtn, ExaminationModeBtn, DictateModeBtn, StationaryBicycleModeBtn);
		setFontSizeToAllNodes(nodes, fontSize);
	}

	public void setLesson(Lesson selectedLesson) {
		lesson = selectedLesson;	
	}
	
	@SuppressWarnings("rawtypes")
	private void catchExceptionAndStartMode(Class modeClass, MouseEvent e) {
		try {
			startMode(modeClass, e);
		} catch (UnsatisfactoryLessonException exeption) {
			showAlert(exeption);
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void startMode(Class modeClass, MouseEvent e) throws UnsatisfactoryLessonException {
		GameMode mode = null;
		String title = "";
		if (modeClass.equals(Learning.class)) {
			title = "Učenie";		
			int num = openLearningModeConfigDialog();
			if (num == 0) {
				return;
			}
			mode = new Learning(lesson, num);
		}
		else if (modeClass.equals(Exception.class)) {
			title = "Skúšanie";
			mode = new Examination(lesson);
		}
		else if (modeClass.equals(Dictate.class)) {
			title = "Diktát";
			mode = new Dictate(lesson);
		}
		else if (modeClass.equals(StationaryBicycle.class)) {
			title = "Stacinárny bicykel";
			List<Integer> configValues = openStationaryBicycleModeConfigDialog();
			if (configValues.get(0) == 0 || configValues.get(1) == 0) {
				// configValues.get(1) moze byt 0  - (trvanie modu = nekonecno)
				return;
			}
			mode = new StationaryBicycle(lesson, configValues.get(0), configValues.get(1), configValues.get(2));
		}
		
		title += " - " + lesson.getName();
		Scenes modeScene = Scenes.MODE;
		modeScene.setTitle(title);
		ModeController controller = (ModeController) redirect(modeScene, e);
		controller.setMode(mode);
		controller.start();
	}

	private int openLearningModeConfigDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/fxml/dialogs/learningModeConfigDialog.fxml"));
        Stage stage = createDialogStage(fxmlLoader);
        
		AtomicInteger number = new AtomicInteger(0);
        LearningModeConfigDialogController controller = fxmlLoader.getController();
        controller.setNumber(number);
        
        stage.showAndWait();
        return number.get();
	}
	
	private List<Integer> openStationaryBicycleModeConfigDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/fxml/dialogs/stationaryBicycleModeConfigDialog.fxml"));
        Stage stage = createDialogStage(fxmlLoader);
        stage.setMinWidth(500);
        stage.setMinHeight(500);
        
		AtomicInteger numberOfAnswersPlay = new AtomicInteger(3);
		AtomicInteger pauseDurationInSecs = new AtomicInteger(0);
		AtomicInteger modeDurationInSecs = new AtomicInteger(0);
		StationaryBicycleModeConfigDialogController controller = fxmlLoader.getController();
        controller.setNumbers(numberOfAnswersPlay, pauseDurationInSecs, modeDurationInSecs);
        
        stage.showAndWait();
        return Arrays.asList(numberOfAnswersPlay.get(), pauseDurationInSecs.get(), modeDurationInSecs.get());
	}
	
	private Stage createDialogStage(FXMLLoader loader) {
        Parent parent = null;
		try {
			parent = loader.load();
		} catch (IOException e)  { e.printStackTrace(); }

        Scene scene = new Scene(parent);
    	scene.getStylesheets().add(getClass().getResource("/gui/styles.css").toExternalForm());
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMinWidth(600);
        stage.setMinHeight(300);
        stage.setScene(scene);
        return stage;
	}
	
	private void showAlert(UnsatisfactoryLessonException exeption) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setHeaderText(exeption.getMessage());
		alert.showAndWait();
	}
}
