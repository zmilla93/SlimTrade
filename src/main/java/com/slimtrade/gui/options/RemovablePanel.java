package main.java.com.slimtrade.gui.options;

import javax.swing.JPanel;

public class RemovablePanel extends JPanel {

	boolean fresh = true;
	boolean unsaved;
	boolean delete;

	public RemovablePanel() {

	}

	public boolean isFresh() {
		return fresh;
	}

	public void setFresh(boolean fresh) {
		this.fresh = fresh;
	}

	public boolean isUnsaved() {
		return unsaved;
	}

	public void setUnsaved(boolean unsaved) {
		this.unsaved = unsaved;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

}
