/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kalogeros
 */
public class FileOps {

    public static String readFile(File file) {

        FileReader fr;
        String fileText = null;
        try {
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            fileText = parseBufferReader(br);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileOps.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileOps.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileText;
    }

    public static void saveFile(File file, String text) throws IOException {

        try (FileWriter myWriter = new FileWriter(file.getAbsoluteFile())) {
            myWriter.write(text);
        }

    }

    private static String parseBufferReader(BufferedReader bufferedReader) throws IOException {
        String line;
        StringBuilder text
                = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            text.append(line);
            text.append("\n");
        }
        return text.toString();
    }
}
