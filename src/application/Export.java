package application;

import data.Group;
import data.Item;
import data.Lesson;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Export {

    private String targetPath;
    private ArrayList<Lesson> xml;
    private FileManager fm;

    public Export() {
        fm = new FileManager();
    }

    private void chooseTargetPath() throws IOException {
        try {
            JButton open = new JButton();
            JFileChooser fo = new JFileChooser();
            fo.setCurrentDirectory(new java.io.File("C:"));
            fo.setDialogTitle("chooser");
            fo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (fo.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) {
                //
            }
            if (fo == null) {
                targetPath = "";
            } else {
                targetPath = fo.getSelectedFile().getAbsolutePath();
            }
            System.out.println("you choose:" + fo.getSelectedFile().getAbsolutePath());
            zipFile("images");
            zipFile("sounds");
        }
        catch(Exception e){
            System.out.println("zavrel okno");
        }

    }

    private void delete_zip(String path){
        File file = new File(path+"/images.zip");
        boolean deleted = file.delete();
        file = new File(path+"/sounds.zip");
        deleted = file.delete();
    }


    private void zipFile(String type) throws IOException {
        String sourceDirectoryPath = "data/files/"+type;
        String zipFilePath = targetPath+"/"+type+".zip";
        zipDirectory(sourceDirectoryPath, zipFilePath,type);
    }

    public void zipDirectory(String sourceDirectoryPath, String zipPath, String type) throws IOException {
        Path zipFilePath = Files.createFile(Paths.get(zipPath));

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(zipFilePath))) {
            Path sourceDirPath = Paths.get(sourceDirectoryPath);

            Files.walk(sourceDirPath).filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(sourceDirPath.relativize(path).toString());
                        System.out.println(zipEntry.getName());
                        if(is_in_xml(zipEntry.getName(), type) == true) {

                            try {
                                zipOutputStream.putNextEntry(zipEntry);
                                zipOutputStream.write(Files.readAllBytes(path));
                                zipOutputStream.closeEntry();
                            } catch (Exception e) {
                                System.err.println(e);
                            }
                        }
                    });
        }
    }

    private boolean is_in_xml(String nazov, String type){

        if(type.equals("all") && (nazov.equals("images.zip") || nazov.equals("sounds.zip") || nazov.endsWith(".xml"))){
            return true;
        }
        Iterator<Lesson> it = xml.iterator();
        while(it.hasNext()) {
            Iterator<Group> iter =  it.next().getGroupsInLesson().iterator();
            while(iter.hasNext()) {
                Iterator<Item> i = iter.next().getItemsInGroup().iterator();
                while(i.hasNext()) {
                    Item fi = i.next();
                    //System.out.println(nazov+ "    " +fi.getQuestionImg());
                    if(type.equals("images")) {
                        if(fi.getAnswerImg()!=null){
                            if (fi.getAnswerImg().endsWith(nazov)){
                                return true;
                            }
                        }
                        else if(fi.getQuestionImg()!=null){
                            if (fi.getQuestionImg().endsWith(nazov)) {
                                return true;
                            }
                        }

                    }
                    else if(type.equals("sounds")){
                        if(fi.getAnswerSound()!=null){
                            if (fi.getAnswerSound().endsWith(nazov)){
                                return true;
                            }
                        }
                        if(fi.getQuestionSound() != null) {
                            if (fi.getQuestionSound().endsWith(nazov)) {
                                return true;
                            }
                        }
                    }

                }
            }
        }
       return false;
    }



    public Boolean hasWriteAccess() {
        File file = new File(targetPath+"/data.zip");
        if(file.exists()){
            return true;
        }
        return false;
    }

    public String getDataFilePath() {

        return "";
    }

    public String getFilesDirPath() {

        return "";
    }

    private void delete_xml(String name){
        File file = new File(targetPath+name);
        file.delete();
    }



    public boolean saveLessonsToFile(ArrayList<Lesson> lessons) {
        xml = lessons;
        try {
            chooseTargetPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataController dcToExport = CreateDCWithLessons(lessons);

        try {

            JAXBContext context = JAXBContext.newInstance(DataController.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            System.out.println(targetPath);
            m.marshal(dcToExport, new File(targetPath+"/random.xml"));

            try {
                zipDirectory(targetPath, targetPath+"/data.zip","all");
            } catch (IOException e) {
                e.printStackTrace();
            }

            delete_zip(targetPath);
            delete_xml("/random.xml");
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
