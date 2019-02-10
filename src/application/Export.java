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
    private DataController controller;

    public Export() {
        fm = new FileManager();
    }

    private void chooseTargetPath() throws IOException {
        try {
            JButton open = new JButton();
            JFileChooser choose_folder = new JFileChooser();
            choose_folder.setCurrentDirectory(new java.io.File("C:"));
            choose_folder.setDialogTitle("Export");
            choose_folder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if(choose_folder.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) {}
            targetPath = choose_folder.getSelectedFile().getAbsolutePath();
            targetPath = check_rename_folder(targetPath);
            zipFile("images");
            zipFile("sounds");
        }
        catch(Exception e){
            
        }

    }

    private void delete_zip(String path){
        File file = new File(path+"/images.zip");
        file.delete();
        file = new File(path+"/sounds.zip");
        file.delete();
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
                        if(is_in_xml(zipEntry.getName(), type) == true) {

                            try {
                                zipOutputStream.putNextEntry(zipEntry);
                                zipOutputStream.write(Files.readAllBytes(path));
                                zipOutputStream.closeEntry();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            zipOutputStream.closeEntry();
            zipOutputStream.close();
        }

    }

    private boolean is_in_xml(String name, String type){
        if(type.equals("all") && (name.equals("images.zip") || name.equals("sounds.zip") || name.endsWith("lessons.xml"))){
            return true;
        }
        else if(type.equals("all")){
            return false;
        }
        Iterator<Lesson> it = xml.iterator();
        while(it.hasNext()) {
            Iterator<Group> iter =  it.next().getGroupsInLesson().iterator();
            while(iter.hasNext()) {
                Iterator<Item> i = iter.next().getItemsInGroup().iterator();
                while(i.hasNext()) {
                    Item fi = i.next();
                    if(type.equals("images")) {
                        if(fi.getAnswerImg()!=null){
                            if (fi.getAnswerImg().endsWith(name)){
                                return true;
                            }
                        }
                        else if(fi.getQuestionImg()!=null){
                            if (fi.getQuestionImg().endsWith(name)) {
                                return true;
                            }
                        }
                    }
                    else if(type.equals("sounds")){
                        if(fi.getAnswerSound()!=null){
                            if (fi.getAnswerSound().endsWith(name)){
                                return true;
                            }
                        }
                        if(fi.getQuestionSound() != null) {
                            if (fi.getQuestionSound().endsWith(name)) {
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
        return file.exists() && file.canWrite();
    }

    private String check_rename_folder(String path){
        File new_folder = new File(targetPath+"/lessons-export");
        if (new_folder.exists() == false){
            new_folder.mkdir();
            return path+"/lessons-export";
        }
        int new_index_file = 0;
        while (new_folder.exists()){
            new_index_file++;
            new_folder = new File(path+"/lessons-export"+new_index_file);
        }
        new_folder.mkdir();
        return  path+"/lessons-export"+new_index_file;
    }

    private String check_rename_filename(String path){
        File file = new File(path+".zip");
        if(file.exists()==false){
            return path+".zip";
        }
        int new_index_file = 0;
        while (file.exists()){
            new_index_file++;
            file = new File(path+new_index_file+".zip");


        }
       return  path+new_index_file+".zip";
    }


    private void delete_xml(String name){
        File file = new File(targetPath+name);
        if (file.exists()) {
            file.delete();
        }
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
            m.marshal(dcToExport, new File(targetPath+"/lessons.xml"));

            try {
                String export_zip_path = check_rename_filename(targetPath+ "/data");
                zipDirectory(targetPath, export_zip_path,"all");
            } catch (IOException e) {
                e.printStackTrace();
            }

            delete_zip(targetPath);
            delete_xml("/lessons.xml");
            return true;
        }
        catch (JAXBException e) {
            return false;
        }


    }

    private DataController CreateDCWithLessons(ArrayList<Lesson> lessonsToExport) {
        DataController dcToExport = new DataController();
        dcToExport.setLessons(lessonsToExport);
        return dcToExport;
    }

}
