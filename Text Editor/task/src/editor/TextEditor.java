package editor;

import javax.swing.*;
import javax.swing.text.JTextComponent;

public class TextEditor extends JFrame {
    public TextEditor() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setVisible(true);
        setLayout(null);
        setLocationRelativeTo(null);
        setTitle("The first stage");

        JTextComponent TextArea = new JTextArea();
        TextArea.setName("TextArea");
        TextArea.setBounds(10, 10, 200, 200);
        add(TextArea);
    }
}
