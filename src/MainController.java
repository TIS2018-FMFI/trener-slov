import data.Lesson;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;

public class MainController {

    private DataController dataController;
    private SoundManager soundManager;
    private Import dataImport;
    private Export dataExport;

    public MainController() {
        dataController = new DataController();
        soundManager = new SoundManager();
        dataImport = new Import();
        dataExport = new Export();
    }

    public void importLesson() {

    }

    public void exportLesson(ArrayList<Lesson> lessons) {

    }

    public void removeLesson(Lesson lessonToRemove) {
        dataController.removeLesson(lessonToRemove);
    }

    public void addLesson(Lesson lessonToAdd) {
        dataController.removeLesson(lessonToAdd);
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

    public int getFontSize() {
        return dataController.getFontSize();
    }

    public void setFontSize(Integer fontSize) {
        dataController.setFontSize(fontSize);
    }

}
