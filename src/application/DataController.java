package application;

import data.*;

import javax.xml.bind.*;
import javax.xml.bind.annotation.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)

public class DataController {

    private String dataFilePath;
    private Configuration config;
    private FileManager fm;

    @XmlElementWrapper(name = "lessons")
    @XmlElement(name = "lesson")
    private ArrayList<Lesson> lessons;

    public DataController(ArrayList<Lesson> lessons, String dataFilePath, Configuration config) {
        this.lessons = lessons;
        this.dataFilePath = dataFilePath;
        this.config = config;
        this.fm = new FileManager();
    }

    public DataController() {
        this.fm = new FileManager();
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

    public void addLessons(ArrayList<Lesson> lessonsToAdd) {
        lessons.addAll(lessonsToAdd);
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

    public Configuration getConfig() { return config; }

    public void setConfig(Configuration config) { this.config = config; }

    private ArrayList<Lesson> uniteLessonsSets(ArrayList<Lesson> lessons1, ArrayList<Lesson> lessons2) {
        lessons1.addAll(lessons2);
        return lessons1;
    }

    public void loadData() throws FileNotFoundException, JAXBException {
        DataController loadedDataController = CreateDataControllerFromXML(dataFilePath);
        config = loadedDataController.getConfig();
        setLessons(loadedDataController.getLessons());
    }

    public void saveData() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(DataController.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.marshal(this, System.out);

        // Write to File
        m.marshal(this, new File(dataFilePath));
    }


    private DataController CreateDataControllerFromXML(String path) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(DataController.class);
        Unmarshaller um = context.createUnmarshaller();
        return (DataController) um.unmarshal(new FileReader(path));
    }

    public void saveFilesInItem(Item item, String newQImage, String newQSound, String newAImage, String newASound) {
        if (item == null)
            return;

        if (newQImage != null) {
            item.setQuestionImg(saveFileAndReturnPath(newQImage, fm.getFullImagesDirName()));
        }

        if (newQSound != null) {
            item.setQuestionSound(saveFileAndReturnPath(newQSound, fm.getFullSoundsDirName()));
        }

        if (newAImage != null) {
           item.setAnswerImg(saveFileAndReturnPath(newAImage, fm.getFullImagesDirName()));
        }

        if (newASound != null) {
            item.setAnswerSound(saveFileAndReturnPath(newASound, fm.getFullSoundsDirName()));
        }
    }

    private String saveFileAndReturnPath(String fp, String fileTypePath) {
        String filePath = fp;
        File checkedFile;

        if (!fm.fileIsAlreadyInApplication(filePath)) {
            checkedFile = new File(filePath);
            filePath = createNewFileNameIfThisExists(checkedFile.getName(), fileTypePath);
            fm.copyFileFromTo(fp, filePath);
        }
        return filePath;
    }

    private String createNewFileNameIfThisExists(String fileNameToCheck, String fileTypeDirPath) {
        String newFilePath = fileTypeDirPath;
        String fileNameWithoutExtension = stringBeforeLastSeparator(fileNameToCheck, ".");

        newFilePath += "\\" + fileNameWithoutExtension;

        ArrayList<String> allFilesNameOfType = fm.getAllFilesFromType(fileTypeDirPath);

        if (allFilesNameOfType != null) {
            List<String> filteredFiles = allFilesNameOfType
                    .stream()
                    .filter(f -> f.contains(fileNameWithoutExtension))
                    .collect(Collectors.toList());

            if (filteredFiles.size() > 0) {
                newFilePath += "_" + filteredFiles.size();
            }
        }

        String fileExtension = stringAfterLastSeparator(fileNameToCheck, ".");

        return newFilePath + "." + fileExtension;
    }


    public String stringAfterLastSeparator(String fileName, String separator) {
        int i = fileName.lastIndexOf(separator);
        return fileName.substring(i + 1);
    }

    public String stringBeforeLastSeparator(String fileName, String separator) {
        int i = fileName.lastIndexOf(separator);
        return fileName.substring(0, i);
    }
}