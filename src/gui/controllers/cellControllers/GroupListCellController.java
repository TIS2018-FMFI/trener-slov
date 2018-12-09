package gui.controllers.cellControllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBException;

import application.Main;
import data.Group;
import data.Lesson;
import gui.Scenes;
import gui.controllers.ControllerBase;
import gui.controllers.sceneControllers.GroupController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;

public class GroupListCellController extends ControllerBase {

	@FXML
	Pane pane;
	
	@FXML
	Label groupName;
	
	@FXML
	Button editBtn, deleteBtn;
	
	ListView<Group> listview;
	FXMLLoader loader;
	Lesson lesson;
	Group group;
	
	
	public GroupListCellController(ListView<Group> listview, Lesson lesson, Group group) {
		this.listview = listview;
		this.lesson = lesson;
		this.group = group;
        loader = new FXMLLoader(getClass().getResource("/gui/fxml/cells/groupListCell.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void update() {
		setFontSizeToTexts();
        groupName.setText(group.getName());	
        
        editBtn.setOnMouseClicked(e -> {
        	GroupController controller = (GroupController) redirect(Scenes.GROUP, e); 
        	controller.setGroup(lesson, group);
        });
        deleteBtn.setOnMouseClicked(e -> delete());
	}

	public Node getGraphics() {
		return pane;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@Override
	protected void setFontSizeToTexts() {
		int fontSize = Main.mainController.getFontSize();
		setFontSizeToNode(editBtn, fontSize);
		setFontSizeToNode(deleteBtn, fontSize);
		setFontSizeToNode(groupName, fontSize);
	}

	private void delete() {
    	if (!confirm()) {
    		return;
    	}
    	lesson.removeGroup(group);
    	try {
			Main.mainController.saveData();
		} catch (JAXBException e1) {e1.printStackTrace();}
    	listview.getItems().remove(group);
	}
	
	private boolean confirm() {
	      Alert alert = new Alert(AlertType.CONFIRMATION);
	      alert.setTitle("");
	      alert.setHeaderText("Naozaj chcete vymazať skupinu ?");
	      alert.setContentText(group.getName());

	      Optional<ButtonType> option = alert.showAndWait();
	      if (option.get() == ButtonType.OK) {
	         return true;
	      }
	      return false;
	}
}
