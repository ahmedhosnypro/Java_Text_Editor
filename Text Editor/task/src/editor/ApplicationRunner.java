package editor;


import javax.swing.*;

public class ApplicationRunner {
    static TextEditor textEditor;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> textEditor = new TextEditor());

    }
}