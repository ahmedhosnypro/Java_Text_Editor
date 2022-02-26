package editor;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextEditor extends JFrame {
    final JMenuBar mainMenuBar = new JMenuBar(); //main menu bar
    JTextComponent editingArea;     //to edit text file in

    //Java file chooser to open-save files
    JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

    //search utilities
    Matcher searchMatcher;
    List<MatchResult> searchMatchers;
    boolean searchFound = false;
    int currentFoundMatch = 0;

    //main panel comps for load-save files and search
    final FlowLayout mainFileSearchFlowLayout = new FlowLayout();
    final JPanel mainFileSearchPanel = new JPanel(); //main file search panel
    //icons
    Dimension iconsDimension = new Dimension(32, 32);
    final ImageIcon saveIcon = new ImageIcon("Text Editor/task/resources/save.png");
    final ImageIcon openIcon = new ImageIcon("Text Editor/task/resources/open.png");
    final ImageIcon searchIcon = new ImageIcon("Text Editor/task/resources/search.png");
    final ImageIcon nextIcon = new ImageIcon("Text Editor/task/resources/next.png");
    final ImageIcon previousIcon = new ImageIcon("Text Editor/task/resources/previous.png");
    //end icons
    //buttons
    final JButton saveButton = new JButton(saveIcon);
    final JButton openButton = new JButton(openIcon);
    final JButton startSearchButton = new JButton(searchIcon);
    final JButton previousMatchButton = new JButton(previousIcon);
    final JButton nextMatchButton = new JButton(nextIcon);
    //end buttons
    final JTextField searchField = new JTextField();
    final JCheckBox useRegExCheckbox = new JCheckBox();
    final JLabel useRegExLabel = new JLabel("Use regex");
    //

    //Menu Comps    //*File Menu Comps
    //file menu
    final JMenu fileMenu = new JMenu("File");
    final JMenuItem saveMenuItem = new JMenuItem("Save");
    final JMenuItem openMenuItem = new JMenuItem("Open");
    final JMenuItem exitMenuItem = new JMenuItem("Exit");
    //search menu
    final JMenu searchMenu = new JMenu("Search");
    final JMenuItem startSearchMenuItem = new JMenuItem("Start Search");
    final JMenuItem previousMatchMenuItem = new JMenuItem("Previous search");
    final JMenuItem nextMatchMenuUItem = new JMenuItem("Next match");
    final JMenuItem useRegExMenuItem = new JMenuItem("Use regular expressions");

    //create lambda action listeners
    final ActionListener loadFileActionListener = e -> {
        String path = searchField.getText();
        try {
            openFile(path, editingArea);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    };
    final ActionListener openFileActionListener = e -> {
        jfc.showOpenDialog(null);
        File selectedFile = jfc.getSelectedFile();
        try {
            openFile(selectedFile.getAbsolutePath(), editingArea);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    };
    final ActionListener saveFileActionListener = e -> {
        jfc.showOpenDialog(null);
        File selectedFile = jfc.getSelectedFile();
        saveFile(selectedFile.getAbsolutePath(), editingArea);
    };
    final ActionListener exitActionListener = e -> {
        dispose();
        System.exit(0);
    };
    final ActionListener startSearchActionListener = e -> {
//        new SearchWorker().execute();
        startSearch();
    };
    final ActionListener previousMatchActionListener = e -> {
        findPreviousMatch();
    };
    final ActionListener nextMatchActionListener = e -> {
        findNextMatch();
    };
    final ActionListener useRegExActionListener = e -> {
        useRegExCheckbox.doClick();
    };

    public TextEditor() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Text Editor");

        //add comps to content pane here
        setJMenuBar(mainMenuBar); //set main menu bar
        addFileChooser();
        addMainFileSearchPanel(getContentPane());
        addEditingArea(getContentPane());
        addFileMenu();
        addSearchesMenu();

        pack();
        setVisible(true);
    }

    private void addFileChooser() {
        //Java file chooser to open-save files
        jfc.setName("FileChooser");
        add(jfc);
    }

    private void addMainFileSearchPanel(final Container pane) {
        //setting file save-load comps
        openButton.setName("OpenButton");
        openButton.setMaximumSize(iconsDimension);
        openButton.setMinimumSize(iconsDimension);
        openButton.setPreferredSize(iconsDimension);

        saveButton.setName("SaveButton");
        saveButton.setMaximumSize(iconsDimension);
        saveButton.setMinimumSize(iconsDimension);
        saveButton.setPreferredSize(iconsDimension);

        startSearchButton.setName("StartSearchButton");
        startSearchButton.setMaximumSize(iconsDimension);
        startSearchButton.setMinimumSize(iconsDimension);
        startSearchButton.setPreferredSize(iconsDimension);

        previousMatchButton.setName("PreviousMatchButton");
        previousMatchButton.setMaximumSize(iconsDimension);
        previousMatchButton.setMinimumSize(iconsDimension);
        previousMatchButton.setPreferredSize(iconsDimension);

        nextMatchButton.setName("NextMatchButton");
        nextMatchButton.setMinimumSize(iconsDimension);
        nextMatchButton.setMinimumSize(iconsDimension);
        nextMatchButton.setPreferredSize(iconsDimension);

        searchField.setName("SearchField");
        Dimension searchFieldDimension = new Dimension(300, 32);
        searchField.setMinimumSize(searchFieldDimension);
        searchField.setMaximumSize(searchFieldDimension);
        searchField.setPreferredSize(searchFieldDimension);

        useRegExCheckbox.setName("UseRegExCheckbox");

        //file save-load panel
        mainFileSearchPanel.setLayout(mainFileSearchFlowLayout);
        mainFileSearchFlowLayout.setAlignment(FlowLayout.LEFT);
        mainFileSearchPanel.add(openButton);
        mainFileSearchPanel.add(saveButton);
        mainFileSearchPanel.add(searchField);
        mainFileSearchPanel.add(startSearchButton);
        mainFileSearchPanel.add(previousMatchButton);
        mainFileSearchPanel.add(nextMatchButton);
        mainFileSearchPanel.add(useRegExCheckbox);
        mainFileSearchPanel.add(useRegExLabel);
        //add actionListener to Buttons
        openButton.addActionListener(openFileActionListener);
        saveButton.addActionListener(saveFileActionListener);
        startSearchButton.addActionListener(startSearchActionListener);
        previousMatchButton.addActionListener(previousMatchActionListener);
        nextMatchButton.addActionListener(nextMatchActionListener);

        //add comps to pane
        pane.add(mainFileSearchPanel, BorderLayout.PAGE_START);
    }

    private void addEditingArea(final Container pane) {
        //setting editing area
        editingArea = new JTextArea(20, 20);
        editingArea.setName("TextArea");
        JScrollPane editorScrollPane = new JScrollPane(editingArea);
        editorScrollPane.setName("ScrollPane");
        editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        editorScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        pane.add(editorScrollPane);
    }

    private void addFileMenu() {
        fileMenu.setName("MenuFile");
        mainMenuBar.add(fileMenu);

        //set file menu items
        openMenuItem.setName("MenuOpen");
        saveMenuItem.setName("MenuSave");
        exitMenuItem.setName("MenuExit");
        //add shortcut to menu
        fileMenu.setMnemonic(KeyEvent.VK_F);

        //add action listener to menu items
        openMenuItem.addActionListener(openFileActionListener);
        saveMenuItem.addActionListener(saveFileActionListener);
        exitMenuItem.addActionListener(exitActionListener);

        //add file menu items to file menu
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
    }

    private void addSearchesMenu() {
        searchMenu.setName("MenuSearch");
        mainMenuBar.add(searchMenu);

        //set search menu items
        startSearchMenuItem.setName("MenuStartSearch");
        previousMatchMenuItem.setName("MenuPreviousMatch");
        nextMatchMenuUItem.setName("MenuNextMatch");
        useRegExMenuItem.setName("MenuUseRegExp");
        //add shortcut to menu
        searchMenu.setMnemonic(KeyEvent.VK_Q);

        //add search menu items to search menu
        searchMenu.add(startSearchMenuItem);
        searchMenu.add(previousMatchMenuItem);
        searchMenu.add(previousMatchMenuItem);
        searchMenu.add(nextMatchMenuUItem);
        searchMenu.add(useRegExMenuItem);

        //add actionListeners to menu items
        startSearchMenuItem.addActionListener(startSearchActionListener);
        previousMatchMenuItem.addActionListener(previousMatchActionListener);
        nextMatchMenuUItem.addActionListener(nextMatchActionListener);
        useRegExMenuItem.addActionListener(useRegExActionListener);
    }

    private void openFile(String path, JTextComponent editingArea) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            if (file.isFile()) {
                try (FileReader fileReader = new FileReader(file)) {
                    editingArea.read(fileReader, file);
                } catch (IOException ignored) {
                    System.out.println();
                }
            } else {
                editingArea.setText("");
            }
        } else {
            editingArea.setText("");
        }
    }

    private void saveFile(String path, JTextComponent editingArea) {
        File file = new File(path);
        try (FileWriter FileWriter = new FileWriter(file)) {
            editingArea.write(FileWriter);
        } catch (IOException ignored) {
        }
    }

    public void startSearch() {
        String regex = searchField.getText();

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        searchMatcher = pattern.matcher(editingArea.getText());
        //resetting match results
        searchMatchers = new ArrayList<>();
        currentFoundMatch = 0;
        searchFound = false;

        while (searchMatcher.find()) {
            searchMatchers.add(searchMatcher.toMatchResult());
            searchFound = true;
        }
        if (searchFound) {
            highlightMatchResult(currentFoundMatch);
        }
    }

    private void findPreviousMatch() {
        if (searchFound) {
            if (!(currentFoundMatch == 0)) {
                currentFoundMatch--;
                highlightMatchResult(currentFoundMatch);
            } else {
                currentFoundMatch = searchMatchers.size() - 1;
                highlightMatchResult(currentFoundMatch);
            }
        }
    }

    private void findNextMatch() {
        if (searchFound) {
            if (!(currentFoundMatch == searchMatchers.size() - 1)) {
                currentFoundMatch++;
                highlightMatchResult(currentFoundMatch);
            } else {
                currentFoundMatch = 0;
                highlightMatchResult(currentFoundMatch);
            }
        }
    }

    private void highlightMatchResult(int currentFoundMatchIndex) {
        editingArea.setCaretPosition(searchMatchers.get(currentFoundMatchIndex).end());
        editingArea.select(searchMatchers.get(currentFoundMatchIndex).start(),
                searchMatchers.get(currentFoundMatchIndex).end());
        editingArea.grabFocus();
    }
}
