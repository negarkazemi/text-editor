package editor;

import javax.swing.*;

import java.io.*;
import java.util.Scanner;

public class FileManager {
    public static void openFile(TextEditor textEditor, JTextArea textArea) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(textEditor) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                Scanner scanner = new Scanner(file);
                String content = "";

                while (scanner.hasNextLine()) {
                    content = content.concat(scanner.nextLine()).concat("\n");
                }

                textArea.setText(content);
                scanner.close();
                textEditor.currentFile = file;
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(textEditor, "Error opening file: " + ex.getMessage());
            }
        }
    }

    public static void saveFile(TextEditor textEditor, JTextArea textArea) {
        if (textEditor.currentFile == null) {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(textEditor) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(textArea.getText());
                    textEditor.currentFile = file;
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(textEditor, "Error saving file: " + e.getMessage());
                }
            }
        } else {
            try (FileWriter writer = new FileWriter(textEditor.currentFile)) {
                writer.write(textArea.getText());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(textEditor, "Error saving file: " + e.getMessage());
            }
        }
    }

    public static void newFile(TextEditor textEditor, JTextArea textArea) {
        textArea.setText("");
        textEditor.currentFile = null;
    }
}
