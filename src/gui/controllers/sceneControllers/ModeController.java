package gui.controllers.sceneControllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import application.Main;
import application.MainController;
import data.Item;
import gui.WaitAndCallGuiMethod;
import gui.Scenes;
import gui.controllers.ControllerBase;
import gui.controllers.dialogControllers.ModeQuitDialogController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modes.GameMode;
import modes.StationaryBicycle;

public class ModeController extends ControllerBase {
	
	@FXML
	Button showAnswerBtn, wrongBtn, rightBtn, playSoundBtn, quitBtn;
	
	@FXML
	Label text;
	
	@FXML
	BorderPane imageParent;

	ImageView imageView;
	
	GameMode mode;
	WaitAndCallGuiMethod modeTimer;
	WaitAndCallGuiMethod itemDurationTimer;
	Item item;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setFontSizeToTexts();
		initializeBtns();
	}
	
	public void start() {
		if (isStationaryBicycle()) {
			StationaryBicycle sb = (StationaryBicycle) mode;
			modeTimer = new WaitAndCallGuiMethod(sb.getModeDurationInSecs(), () -> {
				if (itemDurationTimer != null) {
					itemDurationTimer.stop();
				}
				quit();
				return null;
			});
		}

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
		quitBtn.setOnMouseClicked(e -> quit());
	}

	@Override
	protected void setFontSizeToTexts() {
		int fontSize = Main.mainController.getFontSize();
		List<Node> nodes = Arrays.asList(showAnswerBtn, text, quitBtn);
		setFontSizeToAllNodes(nodes, fontSize);
	}

	public void setMode(GameMode mode) {
		this.mode = mode;
	}
	
	public void quit() {
		boolean startModeAgain = showQuitDialog();
		if (startModeAgain) {
			mode.reinitialize();
			start();
		}
		else {
			redirect(Scenes.START_LESSON, quitBtn);
		}
	}
	
	private void showQuestion() {
		showAnswerBtn.setVisible(true);
		rightBtn.setVisible(false);
		wrongBtn.setVisible(false);
		text.setText( (item.getQuestionText() == null) ? "" : item.getQuestionText() );
		if (item.getQuestionImg() != null) {
			setImage(item.getQuestionImg());
		}
		handleDuration();
	}
	
	public void showAnswer() {
		showAnswerBtn.setVisible(false);
		if (isStationaryBicycle()) {
			rightBtn.setVisible(false);
			wrongBtn.setVisible(false);
		}
		else {
			rightBtn.setVisible(true);
			wrongBtn.setVisible(true);
		}
		text.setText( (item.getAnswerText() == null) ? "" : item.getAnswerText() );
		if (item.getAnswerImg() != null) {
			setImage(item.getAnswerImg());
		}
		handleDuration();
	}
	
	private void handleDuration() {
		// ak nemame zvuk
		if (item.getAnswerSound() == null) {
			// nie je tlacitko zvuku
			playSoundBtn.setVisible(false);
			// ak je stacionarny bicykel
			if (isStationaryBicycle()) {
				// caka sa tolko, kolko sa urcilo
				StationaryBicycle sb = (StationaryBicycle)mode;
				itemDurationTimer = new WaitAndCallGuiMethod(sb.getPauseDurationInSecs(), () -> {
					right();
					return null;
				});
			}
		}
		// ak mame zvuk
		else {
			// tlacitko je viditelne
			playSoundBtn.setVisible(true);
			// aj je stacionarny bicykel
			if (isStationaryBicycle()) {
				// caka sa tolko, ako dlho trva prehrat zvuk zvoleny pocet krat s nejakou prestavkou medzi prehratiami
				StationaryBicycle sb = (StationaryBicycle) mode;
				playSoundBtn.setDisable(true);
				Double soundDuration = Main.mainController.getSoundDuration(getSoundPathOfCurrentItem());
				Double pauseAfterSoundInSeconds = 2.0; 
				Double waitDuration = soundDuration + pauseAfterSoundInSeconds;
				playSound();
				for (int i = 0; i < (sb.getNumberOfPlay() - 1); i++) {
					new WaitAndCallGuiMethod(waitDuration, () -> {
						playSound();
						return null;
					});
				}
				playSoundBtn.setDisable(false);
			}
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
			quit();
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
	
	private String getSoundPathOfCurrentItem() {
		if (showAnswerBtn.isVisible()) {
			return item.getQuestionSound();
		}
		return item.getAnswerSound();
	}
	
	private void playSound() {
		String soundPath = getSoundPathOfCurrentItem();
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
	
	private boolean isStationaryBicycle() {
		try {
			StationaryBicycle sb = (StationaryBicycle) mode;
			return true;
		}
		catch (ClassCastException e) {}
		return false;
	}
}
