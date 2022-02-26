package editor;

import javax.swing.*;

public class SearchWorker extends SwingWorker<Object, Object> {
    @Override
    protected Object doInBackground() throws Exception {
        ApplicationRunner.textEditor.startSearch();
        return null;
    }
}
