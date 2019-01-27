package gui.controllers.sceneControllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBException;

import application.Main;
import gui.Scenes;
import gui.controllers.ControllerBase;
import gui.customCells.GroupListCell;
import data.Group;
import data.Lesson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LessonController extends ControllerBase {
	
	@FXML
	Button newGroupBtn, okBtn;
	
	@FXML
	Label nameLabel;
	
	@FXML
	TextField name;
	
	@FXML
	TextArea hintText;
	
	@FXML
	ListView<Group> groupsListView;
	
	Lesson lesson;
	boolean isNewLesson;
	ObservableList<Group> groupObservableList;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setFontSizeToTexts();
		okBtn.setOnMouseClicked(e -> {
			saveLesson(e);
			redirect(Scenes.LESSON_LIST, e);
		});
		newGroupBtn.setOnMouseClicked(e -> newGroup(e));
	}

	@Override
	protected void setFontSizeToTexts() {
		int fontSize = Main.mainController.getFontSize();
		setFontSizeToNode(newGroupBtn, fontSize);
		setFontSizeToNode(okBtn, fontSize);
		setFontSizeToNode(nameLabel, fontSize);
		setFontSizeToNode(name, fontSize);
		setFontSizeToNode(hintText, fontSize);
	}

	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
		isNewLesson = false;
		name.setText(lesson.getName());
		listGroups();
	}
	

	public void createLesson() {
		lesson = new Lesson("", new ArrayList<>());
		isNewLesson = true;
	}
	
	private void listGroups() {
		List<Group> groups = lesson.getGroupsInLesson();	
		Collections.sort(groups, (g1, g2) -> g1.getOrder().compareTo(g2.getOrder()));
		groupObservableList = FXCollections.observableArrayList(groups);
		groupsListView.setItems(groupObservableList);
		groupsListView.setCellFactory(group -> new GroupListCell(groupsListView, lesson));
	}
	
	private void saveLesson(MouseEvent event) {
		lesson.setName(name.getText());
		updateGroupsOrder();
		if (isNewLesson) {
			Main.mainController.addLesson(lesson);
		}
		try {
			Main.mainController.saveData();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	private void updateGroupsOrder() {
		for (Group group : lesson.getGroupsInLesson()) {
			for (Group group2 : groupsListView.getItems()) {
				if (group.getName().equals(group2.getName())) {
					group.setOrder(group2.getOrder());
				}
			}
		}
	}

	private void newGroup(MouseEvent event) {
		saveLesson(event);
		GroupController controller = (GroupController) redirect(Scenes.GROUP, event);
		controller.createGroup(lesson);
	}

}