package github.zmilla93.gui.listening;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * A more concise DocumentListener for when you want to do something anytime the text of a document changes.
 */
// TODO (Cleanup) : Replace existing DocumentListeners with this
public class TextChangeListener implements DocumentListener {

    @Override
    public void insertUpdate(DocumentEvent e) {
        onTextChange(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        onTextChange(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        // Do nothing, this is only called when attributes are changed.
    }

    /**
     * Called anytime text is changed.
     *
     * @param e DocumentEvent
     */
    public void onTextChange(DocumentEvent e) {
        // This should be overwritten by a subclass.
    }

}
