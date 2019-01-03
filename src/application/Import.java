package application;

import data.Lesson;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Import {

    private FileManager fm;
    JFileChooser c;
    String folderName;File f;char first;
    String END_SAVE_PATH = "C:/Users/Lenovo/Documents/test";        //treba nastavit, popr. zmenit

    public Import() {
        fm = new FileManager();
    }

    private String packagePath;

    private void choosePackagePath() throws IOException {
        c = new JFileChooser();
        c.setDialogTitle("choose file");

        int x = c.showOpenDialog(null);
        if (x == JFileChooser.APPROVE_OPTION) {
            f = c.getSelectedFile();
            if(is_zip_check(f.getName()) == true) {
                first = f.toString().charAt(0);
                unzip(f.getPath(),END_SAVE_PATH);
            }
            else{
                System.out.println("exception");        //tu by to malo bud vratit chybu aleboupozornenie pre pouzivatela
            }

        }
    }

    private boolean is_zip_check(String name){
        if(name.toLowerCase().endsWith("zip")==true){
            return true;
        }
        return false;
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

    public void unzip(String path, String save_path) throws IOException {
        String fileZip = path;
        String end_path = save_path;
        if(fileZip.toLowerCase().endsWith("img.zip")){
            new File(save_path+"/img").mkdir();
            end_path = save_path+"/img";
        }
        else if (fileZip.toLowerCase().endsWith("sound.zip")){
            new File(save_path+"/sound").mkdir();
            end_path = save_path+"/sound";
        }
        else{
            end_path = save_path;
        }
        File destDir = new File(end_path);
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
        ZipEntry zipEntry = zis.getNextEntry();

        while (zipEntry != null) {
            File newFile = newFile(destDir, zipEntry);
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            if(zipEntry.getName().toLowerCase().endsWith("zip")==true){
                unzip(save_path+"/"+zipEntry.getName(),end_path);   //zmena destinacie
            }

            fos.close();
            zipEntry = zis.getNextEntry();
        }
        delete_zip("C:/Users/Lenovo/Documents/test");
        zis.closeEntry();
        zis.close();
    }

    private void delete_zip(String path){
        File file = new File(path+"/img.zip".toString());
        boolean deleted = file.delete();
        file = new File(path+"/sound.zip".toString());
        deleted = file.delete();
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

    public ArrayList<Lesson> loadLessonsFromFile() {
        return GetLessonsFromImportedXML(packagePath);
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