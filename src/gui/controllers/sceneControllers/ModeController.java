package gui.controllers.sceneControllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import application.Main;
import data.Item;
import gui.ModeTimer;
import gui.Scenes;
import gui.controllers.ControllerBase;
import gui.controllers.dialogControllers.ModeQuitDialogController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modes.GameMode;

public class ModeController extends ControllerBase {
	
	@FXML
	Button showAnswerBtn, wrongBtn, rightBtn, playSoundBtn, quitBtn;
	
	@FXML
	Label text;
	
	@FXML
	BorderPane imageParent;

	ImageView imageView;
	
	GameMode mode;
	ModeTimer timer;
	Item item;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setFontSizeToTexts();
		initializeBtns();
	}
	
	private void start() {
		timer = new ModeTimer(this);
		timer.start();

		item = mode.next(null);
		if (checkItem()) {
			showQuestion();
		}
	}
	
	private void initializeBtns() {
		showAnswerBtn.setOnMouseClicked(e -> showAnswer());
		wrongBtn.setOnMouseClicked(e -> wrong());
		rightBtn.setOnMouseClicked(e -> right());
		playSoundBtn.setOnMouseClicked(e -> playSound());
		quitBtn.setOnMouseClicked(e -> quit(e));
	}

	@Override
	protected void setFontSizeToTexts() {
		int fontSize = Main.mainController.getFontSize();
		List<Node> nodes = Arrays.asList(showAnswerBtn, text, quitBtn);
		setFontSizeToAllNodes(nodes, fontSize);
	}

	public void setMode(GameMode mode) {
		this.mode = mode;
		start();
	}
	
	private void showQuestion() {
		showAnswerBtn.setVisible(true);
		rightBtn.setVisible(false);
		wrongBtn.setVisible(false);
		text.setText( (item.getQuestionText() == null) ? "" : item.getQuestionText() );
		playSoundBtn.setVisible( (item.getQuestionSound() != null) );
		if (item.getQuestionImg() != null) {
			setImage(item.getQuestionImg());
		}
	}
	
	private void showAnswer() {
		showAnswerBtn.setVisible(false);
		rightBtn.setVisible(true);
		wrongBtn.setVisible(true);
		text.setText( (item.getAnswerText() == null) ? "" : item.getAnswerText() );
		playSoundBtn.setVisible( (item.getAnswerSound() != null) );
		if (item.getAnswerImg() != null) {
			setImage(item.getAnswerImg());
		}
	}
	
	private void right() {
		item = mode.next(true);
		if (checkItem()) {
			showQuestion();
		}
	}
	
	private void wrong() {
		item = mode.next(false);
		if (checkItem()) {
			showQuestion();
		}
	}
	
	private boolean checkItem() {
		if (item == null) {
			boolean startModeAgain = showQuitDialog();
			if (startModeAgain) {
				mode.reinitialize();
				start();
			}
			else {
				redirect(Scenes.START_LESSON, quitBtn);
			}
			return false;
		}
		return true;
	}

	private void setImage(String imagePath) {
        File file = new File(imagePath);
        Image image = new Image(file.toURI().toString());
        imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(imageParent.getHeight());
		imageView.fitHeightProperty().bind(imageParent.heightProperty());
		imageParent.setCenter(imageView);
	}
	
	private void playSound() {
		String soundPath = null;
		if (showAnswerBtn.isVisible()) {
			soundPath = item.getQuestionSound();
		}
		else {
			soundPath = item.getAnswerSound();
		}
		if (soundPath != null) {
			Main.mainController.playSound(soundPath);
		}
	}
	
	private boolean showQuitDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/fxml/dialogs/modeQuitDialog.fxml"));
        Stage stage = createDialogStage(fxmlLoader);
        
        AtomicBoolean startAgain = new AtomicBoolean();
		ModeQuitDialogController controller = fxmlLoader.getController();
        controller.setResultObject(startAgain);
        
        stage.showAndWait();
        return startAgain.get();
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
	
	private void quit(MouseEvent e) {
		redirect(Scenes.START_LESSON, e);
	}
}
