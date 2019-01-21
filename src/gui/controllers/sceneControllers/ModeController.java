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
	boolean isQuestion;
	boolean modeQuited;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setFontSizeToTexts();
		initializeBtns();
	}
	
	public void start() {
		modeQuited = false;
		if (isStationaryBicycle()) {
			StationaryBicycle sb = (StationaryBicycle) mode;
			Integer modeDuration = sb.getModeDurationInSecs();
			if (modeDuration != 0) {
				modeTimer = new WaitAndCallGuiMethod(modeDuration, () -> {
					if (itemDurationTimer != null) {
						itemDurationTimer.stop();
					}
					quit();
					return null;
				});
			}
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
		playSoundBtn.setOnMouseClicked(e -> playSound(null));
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
		modeQuited = true;
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
		isQuestion = true;
		showAnswerBtn.setVisible(true);
		rightBtn.setVisible(false);
		wrongBtn.setVisible(false);
		text.setText( (item.getQuestionText() == null) ? "" : item.getQuestionText() );
		setImage(item.getQuestionImg());
		handleDuration(item.getQuestionSound());
	}
	
	public void showAnswer() {
		isQuestion = false;
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
		setImage(item.getAnswerImg());
		handleDuration(item.getAnswerSound());
	}
	
	private void handleDuration(String soundPath) {
		// ak nemame zvuk
		if (soundPath == null) {
			// nie je tlacitko zvuku
			playSoundBtn.setVisible(false);
			// ak je stacionarny bicykel
			if (isStationaryBicycle()) {
				// caka sa tolko, kolko sa urcilo
				showAnswerBtn.setVisible(false);
				StationaryBicycle sb = (StationaryBicycle)mode;
				itemDurationTimer = new WaitAndCallGuiMethod(sb.getPauseDurationInSecs(), () -> {
					nextInStationaryBicycle();
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
				showAnswerBtn.setVisible(false);
				StationaryBicycle sb = (StationaryBicycle) mode;
				playSoundBtn.setDisable(true);
				Double soundDuration = Main.mainController.getSoundDuration(soundPath);
				Double pauseAfterSoundInSeconds = 2.0; 
				Double waitDuration = soundDuration + pauseAfterSoundInSeconds;
				playSoundRecursive(soundPath, sb.getNumberOfPlay(), waitDuration);
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
	
	private void nextInStationaryBicycle() {
		if (isQuestion) {
			showAnswer();
		}
		else {
			right();
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
		if (imagePath != null) {
	        File file = new File(imagePath);
	        Image image = new Image(file.toURI().toString());
	        imageView = new ImageView(image);
		}
		else {
			imageView = new ImageView();
		}
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(imageParent.getHeight());
		imageView.fitHeightProperty().bind(imageParent.heightProperty());
		imageParent.setCenter(imageView);
	}
	
	private String getSoundPathOfCurrentItem() {
		if (isQuestion) {
			return item.getQuestionSound();
		}
		return item.getAnswerSound();
	}
	
	private void playSound(String soundPath) {
		if ( soundPath == null ) {
			soundPath = getSoundPathOfCurrentItem();
		}
		if ( soundPath != null ) {
			Main.mainController.playSound(soundPath);
		}
	}
	
	private void playSoundRecursive(String soundPath, int countOfPlays, Double waitDuration) {
		if (modeQuited) {
			return;
		}
		if (countOfPlays == 0) {
			playSoundBtn.setDisable(false);
			nextInStationaryBicycle();
			return;
		}
		new WaitAndCallGuiMethod(waitDuration, () -> {
			playSoundRecursive(soundPath, countOfPlays-1, waitDuration);
			return null;
		});
		playSound(soundPath);
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
