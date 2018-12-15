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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
	Button backBtn, switchBtn, okBtn;
	
	@FXML
	Button qImageChooseBtn, qSoundChooseBtn, aImageChooseBtn, aSoundChooseBtn;
	
	@FXML
	ImageView qImageChooseImg, qSoundChooseImg, aImageChooseImg, aSoundChooseImg;
	
	@FXML
	Label qLabel, aLabel, qTextLabel, qImageLabel, qSoundLabel, aTextLabel, aImageLabel, aSoundLabel, lessonGroupNameLabel;
	
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
		setFontSizeToTexts();
		backBtn.setOnMouseClicked(e -> backToParentGroup(e));	
		okBtn.setOnMouseClicked(e -> saveItem(e));
		switchBtn.setOnMouseClicked(e -> switchQuestionAndAnswer());
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
				backBtn, switchBtn, okBtn, qTextLabel, qImageLabel, qSoundLabel, aTextLabel, aImageLabel, aSoundLabel, 
				qTextValue, qTextValue, qImageValue, qImageValue, qSoundValue, aTextValue, aTextValue, aImageValue,
				aImageValue, aSoundValue, qImageChooseBtn, qSoundChooseBtn, qSoundChooseBtn, aImageChooseBtn, aSoundChooseBtn,
				lessonGroupNameLabel
		);
		setFontSizeToAllNodes(nodes, fontSize);
	}

	public void setItem(Lesson lesson, Group group, Item item) {
		this.lesson = lesson;
		this.group = group;
		this.item = item;
		setTexts();
		isNewItem = false;		
		lessonGroupNameLabel.setText(lesson.getName() + " - " + group.getName());
	}
	
	public void createItem(Lesson lesson, Group group) {
		this.lesson = lesson;
		this.group = group;
		item = new Item();
		isNewItem = true;
		lessonGroupNameLabel.setText(lesson.getName() + " - " + group.getName());
	}
	
	private void switchQuestionAndAnswer() {
		item.switchQuestionAndAnswer();
		setTexts();
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
		backToParentGroup(event);
	}
	
	private void backToParentGroup(MouseEvent event) {
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