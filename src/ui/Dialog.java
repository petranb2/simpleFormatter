/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import utils.FileOps;

/**
 *
 * @author kalogeros
 */
public class Dialog {

    /**
     *
     * @param parent the parent component to open the dialog
     * @param text the text to save in file
     * @return
     */
    public static boolean saveFile(Component parent, String text) {
        JFileChooser fileChooser = new JFileChooser();
        int option;
        option = fileChooser.showSaveDialog(parent);
        boolean success = true;
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                FileOps.saveFile(file, text);
            } catch (IOException ex) {
                success = false;
                Logger.getLogger(Dialog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return success;
    }

    /**
     *
     * @param parent the parent component to open the dialog
     * @return
     */
    public static String openFile(Component parent) {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(parent);
        String textFromFile = null;
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            textFromFile = FileOps.readFile(file);
        }
        return textFromFile;
    }
}
