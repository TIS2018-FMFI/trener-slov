package gui.controllers.sceneControllers;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javax.xml.bind.JAXBException;

import application.Main;
import data.Group;
import data.Item;
import data.Lesson;
import gui.Scenes;
import gui.controllers.ControllerBase;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ItemController extends ControllerBase {
	
	@FXML
	BorderPane pane;
	
	@FXML
	Button backBtn, createSwitchedItemBtn, okBtn;
	
	@FXML
	Button qImageChooseBtn, qSoundChooseBtn, aImageChooseBtn, aSoundChooseBtn;
	
	@FXML
	ImageView qImageChooseImg, qSoundChooseImg, aImageChooseImg, aSoundChooseImg;
	
	@FXML
	Label qLabel, aLabel, qTextLabel, qImageLabel, qSoundLabel, aTextLabel, aImageLabel, aSoundLabel, lessonGroupNameLabel;
	
	@FXML
	TextArea hintText;
	
	@FXML
	TextField qTextValue, qImageValue, qSoundValue, aTextValue, aImageValue, aSoundValue;
	
	final ExtensionFilter extensionsImages = new ExtensionFilter("images", Arrays.asList("*.jpg", "*.png"));
	final ExtensionFilter extensionsSounds = new ExtensionFilter("sounds", Arrays.asList("*.mp3", "*.wav"));
	
	boolean isNewItem;
	Lesson lesson;
	Group group;
	Item item;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		createSwitchedItemBtn.setVisible(false);
		setFontSizeToTexts();
		backBtn.setOnMouseClicked(e -> backToParentGroup(e));	
		okBtn.setOnMouseClicked(e -> saveItem(e));
		createSwitchedItemBtn.setOnMouseClicked(e -> createItemWithSwitchedQuestionAndAnswer());
		qImageChooseBtn.setOnMouseClicked(e -> {
			setFilePathToTextField(qImageValue, extensionsImages,  e);
		});
		qSoundChooseBtn.setOnMouseClicked(e -> {
			setFilePathToTextField(qSoundValue, extensionsSounds, e);
		});
		aImageChooseBtn.setOnMouseClicked(e -> {
			setFilePathToTextField(aImageValue, extensionsImages, e);
		});
		aSoundChooseBtn.setOnMouseClicked(e -> {
			setFilePathToTextField(aSoundValue, extensionsSounds, e);
		});
	}

	@Override
	protected void setFontSizeToTexts() {
		int fontSize = Main.mainController.getFontSize();
		aLabel.setStyle(String.format("-fx-font-size: %dpx; -fx-font-weight: bold;", fontSize));
		qLabel.setStyle(String.format("-fx-font-size: %dpx; -fx-font-weight: bold;", fontSize));
		List<Node> nodes = Arrays.asList(
				backBtn, createSwitchedItemBtn, okBtn, qTextLabel, qImageLabel, qSoundLabel, aTextLabel, aImageLabel, aSoundLabel, 
				qTextValue, qTextValue, qImageValue, qImageValue, qSoundValue, aTextValue, aTextValue, aImageValue,
				aImageValue, aSoundValue, qImageChooseBtn, qSoundChooseBtn, qSoundChooseBtn, aImageChooseBtn, aSoundChooseBtn,
				lessonGroupNameLabel, hintText
		);
		setFontSizeToAllNodes(nodes, fontSize);
	}

	public void setItem(Lesson lesson, Group group, Item item) {
		this.lesson = lesson;
		this.group = group;
		this.item = item;
		setTexts();
		isNewItem = false;	
		createSwitchedItemBtn.setVisible(true);
		lessonGroupNameLabel.setText(lesson.getName() + " - " + group.getName());
	}
	
	public void createItem(Lesson lesson, Group group) {
		this.lesson = lesson;
		this.group = group;
		item = new Item();
		isNewItem = true;
		lessonGroupNameLabel.setText(lesson.getName() + " - " + group.getName());
	}
	
	private void createItemWithSwitchedQuestionAndAnswer() {
		Item itemToBeSwitched = item.copy();
		itemToBeSwitched.switchQuestionAndAnswer();
		group.addItem(itemToBeSwitched);
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText("Nová položka s vymenenou otázkou a odpoveďou vytvorená.");
		alert.showAndWait();
	}

	private void setTexts() {
		qTextValue.setText(item.getQuestionText());
		qImageValue.setText(item.getQuestionImg());
		qSoundValue.setText(item.getQuestionSound());
		aTextValue.setText(item.getAnswerText());
		aImageValue.setText(item.getAnswerImg());
		aSoundValue.setText(item.getAnswerSound());
	}
	
	private void setFilePathToTextField(TextField textfield, ExtensionFilter extensions, MouseEvent e){
		textfield.setText(chooseFile(extensions, e));
	}
	
	private void saveItem(MouseEvent event) {
		if (!check()) {
			showAlert();
			return;
		}
		item.setQuestionText(qTextValue.getText());
		item.setAnswerText(aTextValue.getText());

		Main.mainController.saveFilesInItem(item, qImageValue.getText(), qSoundValue.getText(), aImageValue.getText(), aSoundValue.getText());
		
		if (isNewItem) {
			group.addItem(item);
		}
		try {
			Main.mainController.saveData();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		GroupController controller = (GroupController) redirect(Scenes.GROUP, event);
		controller.setGroup(lesson, group);
	}
	
	private boolean check() {
		boolean questionIsFilled = (!qTextValue.getText().isEmpty() || !qImageValue.getText().isEmpty() || !qSoundValue.getText().isEmpty());
		boolean answerIsFilled = (!aTextValue.getText().isEmpty() || !aImageValue.getText().isEmpty() || !aSoundValue.getText().isEmpty());;
		return (questionIsFilled && answerIsFilled);
	}
	
    private void showAlert() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText("Musí byť vyplnený aspoň jeden údaj z otázky a aspoň jeden údaj z odpovede.");
        alert.showAndWait();
    }

	private void backToParentGroup(MouseEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION, "Naozaj chcete íst späť bez uloženia zmien ?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.NO) {
		   return;
		}
		GroupController controller = (GroupController) redirect(Scenes.GROUP, event);
		controller.setGroup(lesson, group);
	}
	
	private String chooseFile(ExtensionFilter extensions, MouseEvent e) {
		Node node = (Node) e.getSource();
		Stage stage = (Stage) node.getScene().getWindow(); 
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(extensions);
		fileChooser.setTitle("Vyber súbor !");
		File file = fileChooser.showOpenDialog(stage);
		if (file != null) {
			return file.getAbsolutePath();
		}
		return null;
	}
}