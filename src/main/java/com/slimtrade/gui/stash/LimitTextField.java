package main.java.com.slimtrade.gui.stash;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class LimitTextField extends JTextField {

	private static final long serialVersionUID = 1L;
	private int limit;
	
	public LimitTextField(int limit){
		super();
		this.limit = limit;
	}
	
	protected Document createDefaultModel(){
		return new LimitDocument();
	}

	private class LimitDocument extends PlainDocument{
		
		private static final long serialVersionUID = 1L;
		
		public void insertString(int offset, String string, AttributeSet attribute) throws BadLocationException {
			if(string == null){
				return;
			}else if ((getLength() + string.length()) <= limit){
				super.insertString(offset, string, attribute);
			}
		}
	}

}
