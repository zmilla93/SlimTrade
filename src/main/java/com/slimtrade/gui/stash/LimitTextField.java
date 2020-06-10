package com.slimtrade.gui.stash;

import com.slimtrade.gui.custom.CustomTextField;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class LimitTextField extends CustomTextField {

    private static final long serialVersionUID = 1L;
    private int limit;

    public LimitTextField(int limit) {
        super();
        this.limit = limit;
    }

    public LimitTextField(int limit, int columns) {
        super(columns);
        this.limit = limit;
    }

    @Override
    protected Document createDefaultModel() {
        return new LimitDocument();
    }

    private class LimitDocument extends PlainDocument {

        private static final long serialVersionUID = 1L;

        public void insertString(int offset, String string, AttributeSet attribute) throws BadLocationException {
            if (string == null) {
                return;
            } else if ((getLength() + string.length()) <= limit) {
                super.insertString(offset, string, attribute);
            }
        }
    }

}
