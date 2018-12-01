package application;

import data.Lesson;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;

public class Export {

    private String targetPath;
    private FileManager fm;

    public Export() {
        fm = new FileManager();
    }

    private void chooseTargetPath() {

    }

    public Boolean hasWriteAccess() {

        return false;
    }

    public String getDataFilePath() {

        return "";
    }

    public String getFilesDirPath() {

        return "";
    }

    public boolean saveLessonsToFile(ArrayList<Lesson> lessons) {
        DataController dcToExport = CreateDCWithLessons(lessons);

        try {
            JAXBContext context = JAXBContext.newInstance(DataController.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(dcToExport, new File(targetPath));
            return true;
        }
        catch (JAXBException e) {
            System.out.println("FAILED EXPORT:\n" + e);
            return false;
        }
    }

    private DataController CreateDCWithLessons(ArrayList<Lesson> lessonsToExport) {
        DataController dcToExport = new DataController();
        dcToExport.setLessons(lessonsToExport);
        return dcToExport;
    }



}
