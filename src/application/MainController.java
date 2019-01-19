package application;
import data.Configuration;
import data.Item;
import data.Lesson;

import javax.xml.bind.JAXBException;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainController {

    private DataController dataController;
    private SoundManager soundManager;
    private Import dataImport;
    private Export dataExport;

    public MainController() {
        dataController = new DataController(new ArrayList<Lesson>(), "data/data.xml", new Configuration());
        soundManager = new SoundManager();
        dataImport = new Import();
        dataExport = new Export();
    }
    
    public void loadData() {
    	try {
			dataController.loadData();
		} catch (FileNotFoundException | JAXBException e) {
			e.printStackTrace();
		}
    }

    public void importLesson() {
        ArrayList<Lesson> importedLessons = dataImport.loadLessonsFromFile();

        if (importedLessons == null)
            return;

        dataController.addLessons(importedLessons);
    }

    public void exportLesson(ArrayList<Lesson> lessons) {
        dataExport.saveLessonsToFile(lessons);
    }

    public void removeLesson(Lesson lessonToRemove) {
        dataController.removeLesson(lessonToRemove);
    }

    public void addLesson(Lesson lessonToAdd) {
        dataController.addLesson(lessonToAdd);
    }

    public ArrayList<Lesson> getLessons() {
        return dataController.getLessons();
    }

    public ArrayList<Lesson> getLessons(String search) {
        return dataController.getLessons(search);
    }

    public void saveData() throws JAXBException {
        dataController.saveData();
    }

    public void playSound(String soundFilePath) {
        soundManager.PlaySound(soundFilePath);
    }
    
    public Double getSoundDuration(String soundFilePath) {
    	return soundManager.sound_time(soundFilePath);
    }

    public int getFontSize() {
        return dataController.getFontSize();
    }

    public void setFontSize(Integer fontSize) {
        dataController.setFontSize(fontSize);
    }

    public void saveFilesInItem(Item item, String newQImage, String newQSound, String newAImage, String newASound) {
        dataController.saveFilesInItem(item, newQImage, newQSound, newAImage, newASound);
    }

}