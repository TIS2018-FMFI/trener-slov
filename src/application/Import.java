package application;

import data.Lesson;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Import {

    private FileManager fm;

    public Import() {
        fm = new FileManager();
    }

    private String packagePath;

    private void choosePackagePath() {

    }

    public boolean isValidPackage(String path) {

        return false;
    }

    public String getDataFilePath() {

        return "";
    }

    public String getFilesDirPath() {

        return "";
    }

    public ArrayList<Lesson> loadLessonsFromFile(String path) {
        return GetLessonsFromImportedXML(path);
    }

    private ArrayList<Lesson> GetLessonsFromImportedXML(String path) {
        try {
            JAXBContext context = JAXBContext.newInstance(DataController.class);
            Unmarshaller um = context.createUnmarshaller();
            return ((DataController) um.unmarshal(new FileReader(path))).getLessons();
        }
        catch (JAXBException | FileNotFoundException e)  {
            return null;
        }
    }

}