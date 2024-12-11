package github.zmilla93.modules.filemonitor;

/**
 * A listener for file change events.
 */
public interface FileChangeListener {

    void onFileChanged(FileChangeEvent event);

}
