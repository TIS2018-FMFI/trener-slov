package application;

import data.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)

public class DataController {

    private String dataFilePath;
    private Configuration config;

    @XmlElementWrapper(name = "lessons")
    @XmlElement(name = "lesson")
    private ArrayList<Lesson> lessons;

    public DataController(ArrayList<Lesson> lessons, String dataFilePath, Configuration config) {
        this.lessons = lessons;
        this.dataFilePath = dataFilePath;
        this.config = config;
    }

    public DataController() {
    }

    private ArrayList<Lesson> uniteLessonsSets(ArrayList<Lesson> lessons1, ArrayList<Lesson> lessons2) {
        lessons1.addAll(lessons2);
        return lessons1;
    }

    public void loadData() throws FileNotFoundException, JAXBException {
       setLessons(CreateDataControllerFromXML(dataFilePath).getLessons());
    }

    public ArrayList<Lesson> loadLessonsFromFile(String path) throws JAXBException, FileNotFoundException {
        return CreateDataControllerFromXML(path).getLessons();
    }

    public void saveData(ArrayList<Lesson> lessons, Configuration config) throws JAXBException {

    }

    public void saveData() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(DataController.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(this, System.out);

        // Write to File
        m.marshal(this, new File(dataFilePath));
    }

    public void saveLessonsToFile(String path, ArrayList<Lesson> lessons) {

    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }

    public ArrayList<Lesson> getLessons(String search) {
        return lessons;
    }

    public void addLesson(Lesson lessonToAdd) {
        lessons.add(lessonToAdd);
    }

    public void removeLesson(Lesson lessonToRemove) {
        lessons.remove(lessonToRemove);
    }

    public int getFontSize() {
        return config.getFontSize();
    }

    public void setFontSize(Integer fontSize) {
        config.setFontSize(fontSize);
    }

    private DataController CreateDataControllerFromXML(String path) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(DataController.class);
        Unmarshaller um = context.createUnmarshaller();
        return (DataController) um.unmarshal(new FileReader(path));
    }

}
