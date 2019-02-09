package application;

import data.Group;
import data.Item;
import data.Lesson;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class Import {

    private FileManager fm;
    JFileChooser filechooser;
    String folderName;
    char first;
    String END_SAVE_PATH = "data";
    private File choose_file;
    String name="";

    public Import() {
        fm = new FileManager();
    }

    private String packagePath;

    private void choosePackagePath() throws IOException {
        name="";
        boolean message_bad_zip = false;
       try {
           filechooser = new JFileChooser();
           filechooser.setDialogTitle("chooser");
           int return_value_chooser = filechooser.showOpenDialog(null);
           if (return_value_chooser == JFileChooser.APPROVE_OPTION) {
               choose_file = filechooser.getSelectedFile();
               if (is_zip_check(choose_file.getName()) == true) {
                   message_bad_zip = true;
                   packagePath = choose_file.getPath();

                   if(is_correct_zip(packagePath)) {
                       unzip(packagePath, END_SAVE_PATH);
                   }
                   else{
                       JOptionPane.showMessageDialog(null, "chybný zip súbor");
                   }
               } else {
                   JOptionPane.showMessageDialog(null, "je potrebné vybrať zip súbor");
               }

           }
       }
       catch(Exception e){
           if(message_bad_zip == true){
               JOptionPane.showMessageDialog(null, "chybný zip súbor");
           }
           else {
        	   
           }
        }
    }

    public boolean is_correct_zip(String path) throws IOException {
        int count_file = 0;
        boolean is_images = false;
        boolean is_sounds = false;
        boolean is_xml = false;
        ZipFile zipFile = new ZipFile(path);

        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        while(entries.hasMoreElements()){
            ZipEntry entry = entries.nextElement();
            if(entry.getName().equals("images.zip")){ is_images = true; }
            if(entry.getName().equals("sounds.zip")){ is_sounds = true; }
            if(entry.getName().toLowerCase().endsWith("xml")){ is_xml = true; }
            count_file++;
        }

        if(count_file==3 && is_images==true && is_sounds == true && is_xml==true){
            return true;
        }
        return false;
    }


    private boolean is_zip_check(String name){
        return name.toLowerCase().endsWith("zip");

    }

    public boolean isValidPackage(String name) throws IOException {
        packagePath="data/"+name;
        ArrayList<Lesson> pom = GetLessonsFromImportedXML(packagePath);
        Iterator<Lesson> lessonIterator = pom.iterator();
        while(lessonIterator.hasNext()) {
            Iterator<Group> groupIterator =  lessonIterator.next().getGroupsInLesson().iterator();
            while(groupIterator.hasNext()) {
                Iterator<Item> itemIterator = groupIterator.next().getItemsInGroup().iterator();
                while(itemIterator.hasNext()) {
                    Item item = itemIterator.next();
                    if(check_items(item) == false){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean check_items(Item item){
        return !(check_path(item.getQuestionSound()) == false || check_path(item.getQuestionImg()) == false ||
                check_path(item.getAnswerSound()) == false || check_path(item.getAnswerImg()) == false);
    }

    private boolean check_path(String path) {
        if (path != null) {
            File f = new File(path);
            return f.exists();

        }
        return true;
    }


    public void unzip(String path, String save_path) throws IOException {
        String fileZip = path;
        String end_path = save_path;
        if (fileZip.toLowerCase().endsWith("images.zip")) {
            new File(save_path + "/images").mkdir();
            end_path = save_path + "/images";
        } else if (fileZip.toLowerCase().endsWith("sounds.zip")) {
            new File(save_path + "/sounds").mkdir();
            end_path = save_path + "/sounds";
        } else {
            end_path = save_path;//+"/images"
        }
        File destDir = new File(end_path);
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
        ZipEntry zipEntry = zis.getNextEntry();
        FileOutputStream fos = null;
        try {
            while (zipEntry != null) {
                File newFile = newFile(destDir, zipEntry);
                fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                if (zipEntry.getName().toLowerCase().endsWith("zip") == true) {
                    unzip(save_path + "/" + zipEntry.getName(), end_path + "/files");   //zmena destinacie
                }
                if (zipEntry.getName().toLowerCase().endsWith("xml") == true) {
                    name = zipEntry.getName();
                }

                fos.close();
                zipEntry = zis.getNextEntry();
            }
        } catch (Exception e) {
            fos.close();
        }
        delete_zip(end_path);
        zis.closeEntry();
        zis.close();
        if (name != "") {
            if (isValidPackage(name) == false) {
                delete_xml(name);
            } else {
                packagePath = "data/" + name;
            }
        }
    }


    private void delete_xml(String name){
        File file = new File("data/"+name);
        file.delete();
    }

    private void delete_zip(String path){
       File file = new File(path+"/sounds.zip");
        if(file.exists()) {
           boolean je = file.delete();
        }
        file = new File(path+"/images.zip");
        if(file.exists()) {
            file.delete();
        }

    }

    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

    public ArrayList<Lesson> loadLessonsFromFile(){

        try {
            choosePackagePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(packagePath==null){
            packagePath="";
        }
        ArrayList<Lesson> lessons = GetLessonsFromImportedXML(packagePath);
        delete_xml(name);
        delete_zip("data");
        return lessons;
    }

    private ArrayList<Lesson> GetLessonsFromImportedXML(String path) {
        try {
            JAXBContext context = JAXBContext.newInstance(DataController.class);
            Unmarshaller um = context.createUnmarshaller();
            Reader reader = null;
    		try {
    			reader = new InputStreamReader(new FileInputStream(path), "UTF8");
    		} catch (UnsupportedEncodingException e) {
    			e.printStackTrace();
    		}
    		DataController dataController = (DataController) um.unmarshal(reader);
            return dataController.getLessons();
        }
        catch (JAXBException | FileNotFoundException e)  {
            return null;
        }
    }


}