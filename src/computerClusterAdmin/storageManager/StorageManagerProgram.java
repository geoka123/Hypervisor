package computerClusterAdmin.storageManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class StorageManagerProgram {
    private String filename;
    private File file;

    public StorageManagerProgram(String filename) {
        this.filename = filename;
        this.file = new File(filename);
    }

    public void restoreFromFile(StorableProgram sobj) throws IOException {
        String fileText = new String();
        BufferedReader inputStream = null;
        if (!file.exists()) {
            throw new FileNotFoundException("File " + filename + " does not exist");
        }
        try {
            inputStream = new BufferedReader(new FileReader(filename));
            String tempStr = inputStream.readLine();
            while (tempStr != null) {
                fileText += tempStr + "\n";
                tempStr = inputStream.readLine();
            }
            sobj.unMarshalProgram(fileText);
        } catch (UnMarshalingException ex) {
            System.out.println("Could not load from file. Error = " + ex.getMessage());
        } finally {
            if (inputStream != null)
                inputStream.close();
        }
    }
}
