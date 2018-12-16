package application;
import data.Configuration;
import data.Group;
import data.Item;
import data.Lesson;
import gui.Scenes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

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
    	primaryStage.setMinWidth(800);
    	primaryStage.setMinHeight(600);
    	Scene scene = new Scene(mainMenu);
    	scene.getStylesheets().add(getClass().getResource("/gui/styles.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.UTILITY);
		primaryStage.setTitle(Scenes.MAIN_MENU.getTitle());
		primaryStage.show();

//		testDataController();
	}

	private void testDataController() throws FileNotFoundException, JAXBException {
		DataController test = new DataController(new ArrayList<Lesson>(), "data/data.xml", new Configuration());
		test.loadData();

		Item testItem1 = new Item("textQ1","imgQ1","soundQ1","textA1","imgA1","soundA1");
		Item testItem2 = new Item("textQ2","imgQ2","soundQ2","textA2","imgA2","soundA2");
		Item testItem3 = new Item("textQ3","imgQ3","soundQ3","textA3","imgA3","soundA3");

		test.saveFilesInItem(
			testItem1,
			"C:\\Users\\Kjub\\Documents\\skola\\TIS\\test\\e9b29e55777900281dc1df02ff9c8c34e18c3eb5_full.jpg",
			"C:\\Users\\Kjub\\Documents\\skola\\TIS\\test\\Chookity Pah.wav",
			"C:\\Users\\Kjub\\Documents\\skola\\TIS\\trener-slov\\data\\files\\images\\ClassDiagram.jpeg",
			"C:\\Users\\Kjub\\Documents\\skola\\TIS\\test\\Chookity1.wav"
		);

        System.out.println(testItem1.getQuestionSound());
        System.out.println(testItem1.getQuestionImg());
        System.out.println(testItem1.getAnswerSound());
        System.out.println(testItem1.getAnswerImg());

		ArrayList<Item> testItems = new ArrayList<>();
		testItems.add(testItem1);
		testItems.add(testItem2);
		testItems.add(testItem3);

		Group testGroup1 = new Group("TestGroup1",1, testItems);
		Group testGroup2 = new Group("TestGroup2",2, testItems);
		Group testGroup3 = new Group("TestGroup3",3, testItems);

		ArrayList<Group> testGroups = new ArrayList<>();
		testGroups.add(testGroup1);
		testGroups.add(testGroup2);
		testGroups.add(testGroup3);

		test.addLesson(new Lesson("LessonTest1", testGroups));
		test.saveData();

		test.loadData();
	}
}

