package github.zmilla93.core.clipboard;

/**
 * Listens for the clipboard's current string to change. See {@link ClipboardMonitor}.
 */
public interface ClipboardListener {

    void onStringChange(String value);

}
