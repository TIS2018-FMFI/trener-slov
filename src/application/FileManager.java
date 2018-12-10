
package application;

import data.Lesson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class FileManager {

    private String appDirName;
    private String dataDirName = "data";
    private String filesDirName = "files";
    private String imagesDirName = "images";
    private String soundsDirName = "sounds";

    public String getDataDirName() { return dataDirName; }

    public String getFilesDirName() { return filesDirName; }

    public String getImagesDirName() { return imagesDirName; }

    public String getSoundsDirName() { return soundsDirName; }

    public FileManager() {
        appDirName = System.getProperty("user.dir");
        createNonExistingDirectories();
    }

    public void moveFileToFilesDir(String filePath, String targetDir) {

    }

    public void deleteFile(String filePath) {

    }

    public void checkLessonsForNewFiles(ArrayList<Lesson> lessons) {

    }

    public ArrayList<String> getAllFilesFromType(String fileTypeDirPath) {
        File directory = new File(fileTypeDirPath);

        if (!directory.exists() && !directory.isDirectory())
            return null;

        ArrayList<String> filesName = new ArrayList<>();

        for (File file: directory.listFiles()) {
            filesName.add(file.getName());
        }

        return filesName;
    }

    public boolean fileIsAlreadyInApplication(String filePath) {
        return filePath.contains(getFullImagesDirName()) || filePath.contains(getFullSoundsDirName());
    }


    private void createNonExistingDirectories(){
        createFolderIfDoesntExist(  "\\" + appDirName + "\\" + dataDirName);
        createFileIfDoesntExist(    "\\" + appDirName + "\\" + dataDirName + "\\data.xml");

        createFolderIfDoesntExist("\\" + appDirName + "\\" + dataDirName + "\\" + filesDirName);
        createFolderIfDoesntExist(getFullImagesDirName());
        createFolderIfDoesntExist(getFullSoundsDirName());
    }

    private void createFolderIfDoesntExist(String path) {
        File checkedFile = new File(path);
        if (checkedFile.exists())
            return;

        if (checkedFile.mkdir())
            System.out.println("Directory: " + path + " was created");

        else
            System.out.println("!!! Directory: " + path + " WAS NOT created !!!");

    }

    private void createFileIfDoesntExist(String path) {
        File checkedFile = new File(path);
        if (checkedFile.exists())
            return;

        try  {

            if (checkedFile.createNewFile())
                System.out.println("File: " + path + " was created");

            else
                System.out.println("!!! File: " + path + " WAS NOT created !!!");


        }
        catch (IOException e) {
            System.out.println(path + " wasnt created due to error");
        }
    }

    public String getFullImagesDirName() {
        return  appDirName + "\\" + dataDirName + "\\" + filesDirName + "\\" + imagesDirName;
    }

    public String getFullSoundsDirName() {
        return appDirName + "\\" + dataDirName + "\\" + filesDirName + "\\" + soundsDirName;
    }

    public void copyFileFromTo(String srcPath, String destPath) {
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);
        try {
            Files.copy(srcFile.toPath(), destFile.toPath());
        }
        catch (IOException e) {
            System.out.println("error during copying from " + srcPath + " to " + destPath);
        }
    }
}