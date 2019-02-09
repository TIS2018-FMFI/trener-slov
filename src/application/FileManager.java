
package application;

import data.Lesson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class FileManager {

    private String appDirName;
    private String dataDirName = "data";
    private String filesDirName = "files";
    private String imagesDirName = "images";
    private String soundsDirName = "sounds";

    private String XMLTEMPLATETEXT =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
        "<data xmlns:ns2=\"item\" xmlns:ns3=\"group\" xmlns:ns4=\"lesson\">\n" +
        "    <dataFilePath>data/data.xml</dataFilePath>\n" +
        "    <config>\n" +
        "        <fontSize>20</fontSize>\n" +
        "    </config>\n" +
        "    <fm/>\n" +
        "    <lessons>\n" +
        "    </lessons>\n" +
        "</data>\n";

    public String getDataDirName() { return dataDirName; }

    public String getFilesDirName() { return filesDirName; }

    public String getImagesDirName() { return imagesDirName; }

    public String getSoundsDirName() { return soundsDirName; }

    public FileManager() {
        appDirName = System.getProperty("user.dir");
        createNonExistingDirectories();
    }

    public void deleteFile(String filePath) {
        File fileToDelete = new File(filePath);
        if (fileToDelete.exists()){
            fileToDelete.delete();
        }
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

    public boolean filePathIsInApplication(String filePath) {
        return filePath.contains(getFullImagesDirName()) || filePath.contains(getFullSoundsDirName());
    }

    private void createNonExistingDirectories(){
        createFolderIfDoesntExist(dataDirName);
        createFileIfDoesntExist(dataDirName + "\\data.xml");

        createFolderIfDoesntExist(dataDirName + "\\" + filesDirName);
        createFolderIfDoesntExist(getFullImagesDirName());
        createFolderIfDoesntExist(getFullSoundsDirName());
    }

    private void createFolderIfDoesntExist(String path) {
        File checkedFile = new File(path);
        if (checkedFile.exists())
            return;

        checkedFile.mkdir();

    }

    private void createFileIfDoesntExist(String path) {
        File checkedFile = new File(path);
        if (checkedFile.exists())
            return;

        try  {

            if (checkedFile.createNewFile()) {

                if (checkedFile.getPath().contains("data.xml")) {
                    fillDataXMLWithTemplateData(checkedFile);
                }
            }

        }
        catch (IOException e) {
        	
        }
    }

    private void fillDataXMLWithTemplateData(File dataXML) throws IOException {
        Files.write(dataXML.toPath(), XMLTEMPLATETEXT.getBytes(), StandardOpenOption.APPEND);
    }

    public String getFullImagesDirName() {
        return getDataDirName() + "\\" + getFilesDirName() + "\\" + getImagesDirName();
    }

    public String getFullSoundsDirName() {
        return getDataDirName() + "\\" + getFilesDirName() + "\\" + getSoundsDirName();
    }

    public void copyFileFromTo(String srcPath, String destPath) {
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);
        try {
            Files.copy(srcFile.toPath(), destFile.toPath());
        }
        catch (IOException e) {
        	
        }
    }
}