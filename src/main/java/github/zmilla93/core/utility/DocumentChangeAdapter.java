package github.zmilla93.core.utility;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

// FIXME: Should probably move this, maybe a util lib?
public class DocumentChangeAdapter implements DocumentListener {

    @Override
    public void insertUpdate(DocumentEvent e) {
        onDocumentChange();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        onDocumentChange();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        onDocumentChange();
    }

    public void onDocumentChange() {
        // Override this!
    }

}
